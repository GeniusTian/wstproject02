package com.atguigu.mapreduce.friend;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author wststart
 * @create 2019-07-27 18:05
 */
public class FriendDriver {
    public static void main(String[] args) throws IOException, URISyntaxException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        job.setJarByClass(FriendDriver.class);
        job.setMapperClass(FriendMapper.class);
        job.setReducerClass(FriendReudcer.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        job.addCacheFile(new URI("file:///d:/wst/friendinput/friends.txt"));
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.setInputPaths(job, new Path("d:\\wst\\friendinput"));
        FileSystem fileSystem = FileSystem.get(conf);
        if (fileSystem.exists(new Path("d:\\wst\\friendoutput"))) {
            fileSystem.delete(new Path("d:\\wst\\friendoutput"));
        }
        FileOutputFormat.setOutputPath(job, new Path("d:\\wst\\friendoutput"));
        job.waitForCompletion(true);
    }
}
