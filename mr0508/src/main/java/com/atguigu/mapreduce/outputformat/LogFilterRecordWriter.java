package com.atguigu.mapreduce.outputformat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.io.IOException;

/**
 * @author wststart
 * @create 2019-07-26 18:31
 */
public class LogFilterRecordWriter extends RecordWriter <Text, NullWritable> {
    FSDataOutputStream fdos1 = null;
    FSDataOutputStream fdos2 = null;

    public LogFilterRecordWriter(TaskAttemptContext context) {
        try {
            FileSystem fs = FileSystem.get(context.getConfiguration());
            fdos1 = fs.create(new Path("d:\\wst\\atguigu.log"));
            fdos2 = fs.create(new Path("d:\\wst\\other.log"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(Text key, NullWritable value) throws IOException, InterruptedException {
        if (key.toString().contains("atguigu")) {
            fdos1.write(key.toString().getBytes());
        }else {
            fdos2.write(key.toString().getBytes());
        }
    }

    @Override
    public void close(TaskAttemptContext context) throws IOException, InterruptedException {
        IOUtils.closeStream(fdos1);
        IOUtils.closeStream(fdos2);
    }
}
