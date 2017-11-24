package com.wysengine.fishing.export;

import com.wysengine.fishing.export.service.TurbineConfigService;
import com.wysengine.fishing.export.service.TurbineService;
import com.wysengine.fishing.export.util.*;
import org.apache.avro.Schema;
import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.io.compress.SnappyCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.parquet.avro.AvroParquetOutputFormat;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ExportHBaseTableToParquet {
    private static final Logger logger = LoggerFactory.getLogger(ExportHBaseTableToParquet.class);
    public static final String AVRO_SCHEMA = "custom.schema";
    public static final String ROW_KEY = "ROW_KEY";

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        if (args.length == 0) {
            System.out.println("ExportHBaseTableToParquet {compressionCodec snappy,gzip}");
            System.out.println("hadoop jar ./HBaseToHDFS.jar ExportHBaseTableToParquet gzip");
            return;
        }
        String compressionCodec = args[0];

        TurbineService turbineService = new TurbineService(PropertyUtil.getPropertyMap());
        List<Map<String, Object>> turbines = turbineService.getRunModelTurbines();
        for (Map<String,Object> turbine : turbines) {
            String turbineCode = String.valueOf(turbine.get("CODE_"));
            logger.info("------------export turbine {}------------", turbineCode);
            exportTable(turbineCode, compressionCodec);
        }
    }

    private static void exportTable(String turbineCode, String compressionCodec) throws IOException, InterruptedException, ClassNotFoundException {
        DateTime lastMonthDate = JodaTimeUtil.now().minusMonths(7);

        // TODO: 2017/11/23
//        DateTime.Property lastMonth = lastMonthDate.dayOfMonth();
//        String startRow = String.valueOf(lastMonth.withMinimumValue().withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0).toDate().getTime());
//        String stopRow = String.valueOf(lastMonth.withMaximumValue().withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).withMillisOfSecond(999).toDate().getTime());
        String startRow = "1508428800000";
        String stopRow = "1511107200000";

        String pathSuffix = turbineCode + "_" + lastMonthDate.toString("yyyyMM");
        String outputPath = PropertyUtil.getProperty("output.path");
        String parquetPath = outputPath + "parquet/" + pathSuffix;
        String schemaPath = outputPath + "schema/" + pathSuffix;
        String schemaFileName = schemaPath + "/schema.avsc";
        String schema = TurbineConfigService.getTurbineSchema(turbineCode);
        FileUtils.write(new File(schemaFileName), schema);
        HDFSUtil.writeFile(schemaFileName, schemaFileName);

        Job job = Job.getInstance();
        Configuration configuration = job.getConfiguration();
        configuration.set(AVRO_SCHEMA, schema);
        configuration.set(Const.hbaseZookeeperQuorum, PropertyUtil.getProperty(Const.hbaseZookeeperQuorum));
        configuration.set(Const.hbaseZookeeperPropertyClientPort, PropertyUtil.getProperty(Const.hbaseZookeeperPropertyClientPort));
        configuration.set(Const.zookeeperZnodeParent, PropertyUtil.getProperty(Const.zookeeperZnodeParent));
        HBaseConfiguration.addHbaseResources(configuration);

        job.setJarByClass(ExportHBaseTableToParquet.class);
        job.setJobName("ExportHBaseTableToParquet" + pathSuffix);

        Scan scan = new Scan();
        scan.setCaching(500); // 1 is the default in Scan, which will be bad for MapReduce jobs
        scan.setCacheBlocks(false); // don't set to true for MR jobs
        scan.addFamily(Bytes.toBytes(PropertyUtil.getProperty("hbase.columnFamly.realtime")));
        scan.setStartRow(Bytes.toBytes(startRow));
        scan.setStopRow(Bytes.toBytes(stopRow));

        String hbaseTableName = PropertyUtil.getProperty(Const.hbaseNamespace) + ":" + PropertyUtil.getProperty(Const.hbaseTableNamePrefix) + turbineCode;
        TableMapReduceUtil.initTableMapperJob(hbaseTableName, // input HBase table name
                scan, // Scan instance to control CF and attribute selection
                ExportParquetMapper.class, // mapper
                null, // mapper output key
                null, // mapper output value
                job);
        job.setOutputFormatClass(AvroParquetOutputFormat.class);
        AvroParquetOutputFormat.setOutputPath(job, new Path(parquetPath));

        Schema.Parser parser = new Schema.Parser();
        AvroParquetOutputFormat.setSchema(job, parser.parse(schema));

        switch (compressionCodec) {
            case "snappy":
                AvroParquetOutputFormat.setOutputCompressorClass(job, SnappyCodec.class);
                break;
            case "gzip":
                AvroParquetOutputFormat.setOutputCompressorClass(job, GzipCodec.class);
                break;
            default:
                AvroParquetOutputFormat.setOutputCompressorClass(job, GzipCodec.class);
                break;
        }
        job.setNumReduceTasks(0);
        boolean success = job.waitForCompletion(true);
        if (success) {
            try {
                // create hive table
                HiveUtil.createTable(pathSuffix);
                // delete hbase data todo
//                HbaseUtil.deleteRows(hbaseTableName, startRow, stopRow);
                // major compact
//                HbaseUtil.majorCompact(hbaseTableName);
            } catch (SQLException e) {
                logger.error(e.getLocalizedMessage(), e);
            }
        }

    }

}
