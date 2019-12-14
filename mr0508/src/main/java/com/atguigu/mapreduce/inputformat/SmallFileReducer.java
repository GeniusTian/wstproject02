package com.atguigu.mapreduce.inputformat;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author wststart
 * @create 2019-07-23 18:12
 */
public class SmallFileReducer extends Reducer <Text, BytesWritable, Text, BytesWritable> {
    @Override
    protected void reduce(Text key, Iterable <BytesWritable> values, Context context) throws IOException, InterruptedException {
        context.write(key, values.iterator().next());
    }
}
