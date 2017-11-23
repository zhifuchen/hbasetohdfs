package com.wysengine.fishing.export.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.*;
import java.net.URI;

/**
 * Created by chenzhifu on 2017/11/22.
 */
public class HDFSUtil {
    public static void writeFile(String srcFile,String destFile) throws IOException {
        InputStream in = new BufferedInputStream(new FileInputStream(srcFile));
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(destFile), conf);
        OutputStream out = fs.create(new Path(destFile));
        IOUtils.copyBytes(in, out, 4096, true);
    }
}
