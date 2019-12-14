package com.atguigu.mapreduce.wordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author wststart
 * @create 2019-07-21 17:21
 */
public class WordCountReducer extends Reducer <Text, IntWritable, Text, IntWritable> {
    IntWritable value = new IntWritable();

    @Override
    protected void reduce(Text key, Iterable <IntWritable> values, Reducer <Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
//        1.累加求和
        int sum = 0;
        for (IntWritable value : values) {
            sum += value.get();
        }
        value.set(sum);
//        2.写出
        context.write(key, value);
    }
}
