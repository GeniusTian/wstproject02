package com.atguigu.mapreduce.friend;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author wststart
 * @create 2019-07-27 16:52
 */
public class FriendMapper extends Mapper <LongWritable, Text, Text, Text> {
    Map <String, String[]> map = new TreeMap <>();
    Text k = new Text();
    Text v = new Text();
    ArrayWritable ka = null;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        URI[] cacheFiles = context.getCacheFiles();
        Path path = new Path(cacheFiles[0]);
        FileSystem fs = FileSystem.get(context.getConfiguration());
        FSDataInputStream fdis = fs.open(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(fdis, "utf-8"));
        String line;
        while (StringUtils.isNotEmpty(line = br.readLine())) {
            if (!"".equals(line)) {
                String[] split = line.split(":");
//                String s = split[1].replaceAll(",", "");
                String[] s = split[1].split(",");
                map.put(split[0], s);
            }
        }
        IOUtils.closeStream(br);
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String leng = value.toString();
        if (!"".equals(leng)) {
            String[] split = leng.split(":");
            String friends = split[1].replaceAll(",", "");
            for (Map.Entry <String, String[]> entry : map.entrySet()) {
                if (!entry.getKey().equals(split[0])) {
                    List <String> collect = Arrays.stream(entry.getValue()).filter((str) -> !friends.contains(str)).collect(Collectors.toList());
                    String[] strs = (String[]) collect.toArray();
                    ka = new ArrayWritable(strs);
                    System.out.println(collect);
                }
//                    for (char friend : friends.toCharArray()) {
//                        if (entry.getValue().contains(friend + "")) {
//                            valueOutBuild.append(friend + "\t");
//                        }
//                    }
//                    keyOutBuild.append(entry.getKey() + "--" + split[0]);
//                    if (valueOutBuild.length() != 0) {
//                        k.set(keyOutBuild.toString());
//                        v.set(valueOutBuild.toString());
//                        context.write(k, v);
//                    }
//                }
//                keyOutBuild.delete(0, keyOutBuild.length());
//                valueOutBuild.delete(0, valueOutBuild.length());


//                List <String> collect = Stream.of(entry.getValue()).filter((String str) -> {
//                    boolean b = friends.contains(str);
//                    return b;
//                }).collect(Collectors.toList());

//                System.out.println("");
            }
        }
    }
}
