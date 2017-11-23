package com.wysengine.fishing.export;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by chenzhifu on 2017/11/23.
 */
public class ExportParquetMapper extends TableMapper<Void, GenericRecord> {
    ArrayList<String> columnNames = new ArrayList<>();
    Schema schema;

    @Override
    public void setup(Context context) throws IOException {
        Configuration configuration = context.getConfiguration();
        columnNames = generateColumnsFromSchema(configuration.get(ExportHBaseTableToParquet.AVRO_SCHEMA));
    }

    private ArrayList<String> generateColumnsFromSchema(String schemaStr) throws IOException {
        Schema.Parser parser = new Schema.Parser();
        schema = parser.parse(schemaStr);
        ArrayList<String> results = new ArrayList<String>();
        for (Schema.Field f : schema.getFields()) {
            results.add(f.name());
        }
        return results;
    }

    @Override
    public void map(ImmutableBytesWritable key, Result value, Context context) throws InterruptedException, IOException {
        HashMap<String, byte[]> columnValueMap = new HashMap<>();
        KeyValue[] kvs = value.raw();
        for (KeyValue kv : kvs) {
            String qualifier = Bytes.toString(kv.getQualifier());
            byte[] val = kv.getValue();
            columnValueMap.put(qualifier, val);
        }
        writeLine(context, columnValueMap, key.get());
    }

    private void writeLine(Context context, HashMap<String, byte[]> columnValueMap, byte[] rowKey) throws IOException, InterruptedException {
        if (columnValueMap.size() > 0) {
            GenericData.Record record = new GenericData.Record(schema);
            for (String columnName : columnNames) {
                byte[] columnValue = columnValueMap.get(columnName);
                if (columnValue != null) {
                    putValue(record, columnName, columnValue);
                } else if (columnName.equals(ExportHBaseTableToParquet.ROW_KEY)) {
                    putValue(record, columnName, rowKey);
                }
            }
            context.write(null, record);
        }
    }

    private void putValue(GenericData.Record record, String col, byte[] value) {
        Schema.Type schemaType = schema.getField(col).schema().getType();
        if (schemaType.equals(Schema.Type.STRING)) {
            String v = Bytes.toString(value);
//                v = replaceNull(v);
            record.put(col, v);
        } else if (schemaType.equals(Schema.Type.INT)) {
            record.put(col, Bytes.toInt(value));
        } else if (schemaType.equals(Schema.Type.LONG)) {
            record.put(col, Bytes.toLong(value));
        } else if (schemaType.equals(Schema.Type.UNION)) {
            String v = Bytes.toString(value);
//                v = replaceNull(v);
            record.put(col, v);
        } else {
            throw new RuntimeException("Unknown datatype: " + schemaType);
        }
    }

    private String replaceNull(String v) {
        if (null == v || "null".equals(v)) {
            v = "";
        }
        return v;
    }
}
