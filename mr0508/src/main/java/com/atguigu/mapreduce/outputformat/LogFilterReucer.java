package com.atguigu.mapreduce.outputformat;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author wststart
 * @create 2019-07-26 18:19
 */
public class LogFilterReucer extends Reducer <Text, NullWritable, Text, NullWritable> {
    @Override
    protected void reduce(Text key, Iterable <NullWritable> values, Context context) throws IOException, InterruptedException {
        context.write(key, NullWritable.get());
    }
}
