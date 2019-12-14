package com.atguigu.mapreduce.partition;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author wststart
 * @create 2019-07-22 15:48
 */
public class FlowCountMapper extends Mapper <LongWritable, Text, FlowBean, Text > {
    FlowBean outKey = new FlowBean();
    Text outValue = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] splits = line.split("\t");
        outKey.setAll(Long.parseLong(splits[splits.length - 3]), Long.parseLong(splits[splits.length - 2]));
        outValue.set(splits[1]);
        context.write(outKey, outValue);
    }
}
