package com.atguigu.mapreduce.flowcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author wststart
 * @create 2019-07-22 19:00
 */
public class FlowCountDriver {
    public static void main(String[] args) throws Exception {
//        1.获取job对象
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
//        2.设置jar存储位置
        job.setJarByClass(FlowCountDriver.class);
//        3.设置Mapper和Reducer关联
        job.setMapperClass(FlowCountMapper.class);
        job.setReducerClass(FlowCountReducer.class);
//        4.设置Mapper和最终输出输出k,v类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);
//        5.设置输出输入路径
        FileInputFormat.setInputPaths(job, new Path("d:\\wst\\flowinput"));
        FileOutputFormat.setOutputPath(job, new Path("d:\\wst\\flowoutput"));
//        6.提交job
        boolean b = job.waitForCompletion(true);
    }
}
