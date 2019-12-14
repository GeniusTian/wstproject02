package com.atguigu.mapreduce.groupingcomparator;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author wststart
 * @create 2019-07-24 20:06
 */
public class GroupingComparatorMapper extends Mapper <LongWritable, Text, Orderbean, NullWritable> {
    Orderbean keyOrder = new Orderbean();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] fields = value.toString().split("\t");
        keyOrder.setOrder_id(Integer.parseInt(fields[0]));
        keyOrder.setPrice(Double.parseDouble(fields[2]));
        context.write(keyOrder,NullWritable.get());
    }
}
