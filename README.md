HBase-toHDFS
-----------------------------

### Problem
We have a HBase table and we would like to export it to Text, Seq, Avro, or Parquet.

### How to Run

#### Exports the data to Parquet
* hadoop jar HBaseToHDFS.jar ExportHBaseTableToParquet gzip

####
* hadoop jar HBaseToHDFS.jar ParquetReader export.parquet/part-m-00000 10

