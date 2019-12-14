package com.atguigu;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author wststart
 * @create 2019-08-06 13:47
 */
public class ETLMapper extends Mapper <LongWritable, Text, Text, NullWritable> {
    Text k = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String resultLine = ETLUtil.StringETL(line);
        if (resultLine == null) return;
        k.set(resultLine);
        context.write(k, NullWritable.get());
    }
}
