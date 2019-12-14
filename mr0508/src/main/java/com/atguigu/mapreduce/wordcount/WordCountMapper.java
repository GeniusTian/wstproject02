package com.atguigu.mapreduce.wordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author wststart
 * @create 2019-07-21 17:02
 */
//map阶段操作
//   KEYIN输入数据的key的类型,偏移量  VALUEIN输入数据的value的类型
//   KEYOUT输出数据的key的类型  VAULEOUT输出数据的value类型
public class WordCountMapper extends Mapper <LongWritable, Text, Text, IntWritable> {
    Text key = new Text();
    IntWritable value = new IntWritable(1);

    @Override
    protected void map(LongWritable key, Text value, Mapper <LongWritable, Text, Text, IntWritable>.Context context) throws IOException, InterruptedException {
//        1.获取一行
        String line = value.toString();
//        2.将行按照空格切割成单词
        String[] words = line.split(" ");
//        3.循环写出
        for (String word : words) {
            this.key.set(word);
            context.write(this.key, this.value);
        }
    }
}
