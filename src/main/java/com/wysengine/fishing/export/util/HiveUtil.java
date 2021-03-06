package com.wysengine.fishing.export.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by chenzhifu on 2017/11/23.
 */
public class HiveUtil {
    private static final Logger logger = LoggerFactory.getLogger(HiveUtil.class);
    private static final String driverName = "org.apache.hive.jdbc.HiveDriver";
    private static final String hdfsUrl = PropertyUtil.getProperty("hdfs.default");
    private static final String outputPath = PropertyUtil.getProperty("output.path");
    private static final String avroTablePrefix = "avro_";
    private static final String parquetTablePrefix = "parquet_";

    public static void createTable(String turbineMonth) throws SQLException, ClassNotFoundException {
        Class.forName(driverName);
        try (Connection con = DriverManager.getConnection("jdbc:hive2://localhost:10000", "hdfs", "");
             Statement stmt = con.createStatement()) {
            String avroTable = avroTablePrefix + turbineMonth;
//            stmt.execute("DROP TABLE IF EXISTS " + avroTable);
            String avroSchemaUrl = hdfsUrl +  outputPath + "schema/" + turbineMonth + "/schema.avsc";
            stmt.execute("CREATE TABLE " + avroTable + " ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.avro.AvroSerDe' STORED AS AVRO TBLPROPERTIES ('avro.schema.url'='" + avroSchemaUrl + "')");

            String parquetTable =  parquetTablePrefix + turbineMonth;
//            stmt.execute("DROP TABLE IF EXISTS " + parquetTable);
            String parquetUrl = hdfsUrl + outputPath + "parquet/" + turbineMonth;
            stmt.execute("CREATE EXTERNAL TABLE " + parquetTable + " LIKE " + avroTable + " STORED AS PARQUET LOCATION '"+ parquetUrl +"'");
        }
    }

}
