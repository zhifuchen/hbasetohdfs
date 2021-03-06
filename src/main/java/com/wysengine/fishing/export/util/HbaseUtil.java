package com.wysengine.fishing.export.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenzhifu on 2017/11/23.
 */
public class HbaseUtil {
    private static final Configuration configuration = new Configuration();
    static {
        configuration.set(Const.hbaseZookeeperQuorum, PropertyUtil.getProperty(Const.hbaseZookeeperQuorum));
        configuration.set(Const.hbaseZookeeperPropertyClientPort, PropertyUtil.getProperty(Const.hbaseZookeeperPropertyClientPort));
        configuration.set(Const.zookeeperZnodeParent, PropertyUtil.getProperty(Const.zookeeperZnodeParent));
    }

    public static void deleteRows(String tableName, String startRow, String stopRow) throws IOException {
        Scan scan = new Scan();
        scan.setStartRow(Bytes.toBytes(startRow));
        scan.setStopRow(Bytes.toBytes(stopRow));
        try (Connection connection = ConnectionFactory.createConnection(configuration);
             Table table = connection.getTable(TableName.valueOf(tableName));
             ResultScanner resultScanner = table.getScanner(scan)) {
            List<Delete> deletes = new ArrayList<>();
            for (Result result : resultScanner) {
                byte[] row = result.getRow();
                deletes.add(new Delete(row));
            }
            table.delete(deletes);
        }
    }

    public static void majorCompact(String tableName) throws IOException {
        try (Connection connection = ConnectionFactory.createConnection(configuration);
             Admin admin = connection.getAdmin()) {
            admin.majorCompact(TableName.valueOf(tableName));
        }
    }

}
