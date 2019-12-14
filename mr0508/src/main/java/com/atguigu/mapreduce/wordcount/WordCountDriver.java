package com.atguigu.mapreduce.wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import java.io.IOException;


/**
 * @author wststart
 * @create 2019-07-21 17:36
 */
public class WordCountDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
//        1.获取job对象
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
//        2.设置jar存储位置
        job.setJarByClass(WordCountDriver.class);
//        3.关联Map和Reduce
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);
//        4.设置Mapper阶段输出数据的key和value类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
//        5.设置最终数据输出的key和value类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
//        6.设置输入路径和输出路径
        FileInputFormat.setInputPaths(job, new Path("D:\\wst\\inputword"));
        FileSystem fileSystem = FileSystem.get(conf);
        if(fileSystem.exists(new Path("D:\\wst\\outputword"))){
            fileSystem.delete(new Path("D:\\wst\\outputword"));
        }
        FileOutputFormat.setOutputPath(job, new Path("D:\\wst\\outputword"));
//        7.提交job
//        job.submit();
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
}
