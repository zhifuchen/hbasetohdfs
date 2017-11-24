package com.wysengine.fishing.export.utils;

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

    public static void createTable(String tableName) throws SQLException {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        try (Connection con = DriverManager.getConnection("jdbc:hive2://localhost:10000", "hdfs", "");
             Statement stmt = con.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS " + tableName);
            String avroTable = "avro_" + tableName;
            String avroSchemaUrl = hdfsUrl +  outputPath + "schema/" + tableName + "/schema.avsc";
            stmt.execute("CREATE TABLE " + avroTable + " ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.avro.AvroSerDe' STORED AS AVRO TBLPROPERTIES ('avro.schema.url'='" + avroSchemaUrl + "')");
            String parquetTable = "parquet_" + tableName;
            String parquetUrl = hdfsUrl + outputPath + "parquet/" + tableName;
            stmt.execute("CREATE EXTERNAL TABLE " + parquetTable + " LIKE " + avroTable + " STORED AS PARQUET LOCATION '"+ parquetUrl +"'");
        }
    }
}
