package com.atguigu.mapreduce.outputformat;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author wststart
 * @create 2019-07-26 18:19
 */
public class LogFilterOutputFormat extends FileOutputFormat<Text,NullWritable> {

    @Override
    public RecordWriter <Text, NullWritable> getRecordWriter(TaskAttemptContext context) throws IOException, InterruptedException {
        return new LogFilterRecordWriter(context);
    }
}
