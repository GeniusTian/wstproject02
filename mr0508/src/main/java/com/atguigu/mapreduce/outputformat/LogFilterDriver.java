package com.atguigu.mapreduce.outputformat;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author wststart
 * @create 2019-07-26 18:19
 */
public class LogFilterDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Job job = Job.getInstance();
        job.setJarByClass(LogFilterDriver.class);
        job.setMapperClass(LogFilterMapper.class);
        job.setReducerClass(LogFilterReucer.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);
        job.setOutputFormatClass(LogFilterOutputFormat.class);
        FileInputFormat.setInputPaths(job,new Path("D:\\wst\\filterinput"));
        FileOutputFormat.setOutputPath(job,new Path("D:\\wst\\filteroutput"));
        job.waitForCompletion(true);
    }
}
