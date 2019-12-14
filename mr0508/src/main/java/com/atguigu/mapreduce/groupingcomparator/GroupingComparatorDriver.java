package com.atguigu.mapreduce.groupingcomparator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;


/**
 * @author wststart
 * @create 2019-07-24 20:06
 */
public class GroupingComparatorDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        job.setJarByClass(GroupingComparatorDriver.class);
        job.setMapperClass(GroupingComparatorMapper.class);
        job.setReducerClass(GroupingComparatorReducer.class);
        job.setMapOutputKeyClass(Orderbean.class);
        job.setMapOutputValueClass(NullWritable.class);
        job.setOutputKeyClass(Orderbean.class);
        job.setOutputValueClass(NullWritable.class);
        FileInputFormat.setInputPaths(job,new Path("D:\\wst\\groupingcomparatorinput"));
        FileOutputFormat.setOutputPath(job,new Path("D:\\wst\\groupingcomparatoroutput"));
        job.setGroupingComparatorClass(OrderGroupingComparator.class);
        job.waitForCompletion(true);

    }
}
