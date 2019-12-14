package com.atguigu.mapreduce.flowcount;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author wststart
 * @create 2019-07-22 15:48
 */
public class FlowCountMapper extends Mapper <LongWritable, Text, Text, FlowBean> {
    Text outKey = new Text();
    FlowBean outValue = new FlowBean();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] splits = line.split("\t");
        outKey.set(splits[1]);
        outValue.setAll(Long.parseLong(splits[splits.length - 3]), Long.parseLong(splits[splits.length - 2]));
        context.write(outKey, outValue);
    }
}
