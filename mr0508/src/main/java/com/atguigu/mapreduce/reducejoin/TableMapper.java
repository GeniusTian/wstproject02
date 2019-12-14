package com.atguigu.mapreduce.reducejoin;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wststart
 * @create 2019-07-26 19:23
 */
public class TableMapper extends Mapper <LongWritable, Text, OrderBean, NullWritable> {
    OrderBean order = new OrderBean();
    //pd表在内存中的缓存
    private Map <String, String> pMap = new HashMap <>();


    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
//        //从缓存文件中找到pd.txt
//        URI[] cacheFiles = context.getCacheFiles();
//        Path path = new Path(cacheFiles[0]);
//
//        //获取文件系统并开流
//        FileSystem fileSystem = FileSystem.get(context.getConfiguration());
//        FSDataInputStream fsDataInputStream = fileSystem.open(path);
//
//        //通过包装流转换为reader
//        BufferedReader bufferedReader = new BufferedReader(
//                new InputStreamReader(fsDataInputStream, "utf-8"));
//        //逐行读取，按行处理
//        String line;
//        while (StringUtils.isNotEmpty(line = bufferedReader.readLine())) {
//            String[] fields = line.split("\t");
//            pMap.put(fields[0], fields[1]);
//        }
//
//        //关流
//        IOUtils.closeStream(bufferedReader);
        BufferedReader br = new BufferedReader(new FileReader("d:/wst/tableinput/pd.txt"));
        String line ;
        while(( line = br.readLine())!=null) {
            // 01	小米
            String [] fields = line.split("\t");
            pMap.put(fields[0], fields[1]);
        }
        //关闭流
        br.close();

    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String leng = value.toString();
        String[] fields = leng.split("\t");
        String pname = pMap.get(fields[1]);
        if ("order.txt".equals(((FileSplit) (context.getInputSplit())).getPath().getName())) {
            order.setId(fields[0]);
            order.setPname(pname);
            order.setAmount(Integer.parseInt(fields[2]));
            context.write(order, NullWritable.get());
        }
    }
}
