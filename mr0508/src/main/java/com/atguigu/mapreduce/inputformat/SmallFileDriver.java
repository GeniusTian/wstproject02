package com.atguigu.mapreduce.inputformat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

import java.io.IOException;

/**
 * @author wststart
 * @create 2019-07-23 18:12
 */
public class SmallFileDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        job.setJarByClass(SmallFileDriver.class);
        job.setJarByClass(SmallFileMapper.class);
        job.setJarByClass(SmallFileReducer.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(BytesWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(BytesWritable.class);
        job.setInputFormatClass(SmallFileInputFormat.class);
        job.setOutputFormatClass(SequenceFileOutputFormat.class);
        FileInputFormat.setInputPaths(job, new Path("D:\\wst\\input"));

        FileSystem fileSystem = FileSystem.get(conf);
        if(fileSystem.exists(new Path("D:\\wst\\output"))){
            fileSystem.delete(new Path("D:\\wst\\output"));
        }


        FileOutputFormat.setOutputPath(job, new Path("D:\\wst\\output"));
        job.waitForCompletion(true);
    }
}
