package com.wysengine.fishing.export.utils;

import com.wysengine.fishing.export.util.Const;
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

    public static Configuration getHbaseConfig() {
        Configuration configuration = new Configuration();
        configuration.set(Const.hbaseZookeeperQuorum, PropertyUtil.getProperty(Const.hbaseZookeeperQuorum));
        configuration.set(Const.hbaseZookeeperPropertyClientPort, PropertyUtil.getProperty(Const.hbaseZookeeperPropertyClientPort));
        configuration.set(Const.zookeeperZnodeParent, PropertyUtil.getProperty(Const.zookeeperZnodeParent));
        return configuration;
    }

    public void deleteRows(String tableName, String startRow, String endRow) throws IOException {
        Connection connection = ConnectionFactory.createConnection(getHbaseConfig());
        Table table = connection.getTable(TableName.valueOf(tableName));
        try {
            Scan scan = new Scan();
            scan.setStartRow(Bytes.toBytes(startRow));
            scan.setStartRow(Bytes.toBytes(endRow));
            ResultScanner resultScanner = table.getScanner(scan);
            List<Delete> deletes = new ArrayList<>();
            for (Result result : resultScanner) {
                byte[] row = result.getRow();
                deletes.add(new Delete(row));
            }
            table.delete(deletes);
        } finally {
            table.close();
            connection.close();
        }
    }
}
