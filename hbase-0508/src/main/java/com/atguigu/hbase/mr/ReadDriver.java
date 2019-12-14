package com.atguigu.hbase.mr;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.HRegionPartitioner;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.mapreduce.Job;

import java.io.IOException;

/**
 * @author wststart
 * @create 2019-08-15 17:13
 */
public class ReadDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "hadoop102,hadoop103,hadoop104");
        Job job = Job.getInstance(conf);
        job.setJarByClass(ReadDriver.class);
        job.setMapperClass(ReadMapper.class);
        TableMapReduceUtil.initTableMapperJob("fruit", new Scan(), ReadMapper.class, ImmutableBytesWritable.class, Put.class, job);
        TableMapReduceUtil.initTableReducerJob("fruit_mr", ReadReducer.class, job, HRegionPartitioner.class);
        job.waitForCompletion(true);
    }
}
