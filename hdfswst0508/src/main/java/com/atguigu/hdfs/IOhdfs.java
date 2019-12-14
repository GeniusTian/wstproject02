package com.atguigu.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author wststart
 * @create 2019-07-22 19:32
 */
public class IOhdfs {
    Configuration conf = null;
    FileSystem fs = null;
    String uri = "hdfs://hadoop102:9000";
    String user = "wst";

    @Before
    public void before() throws Exception {
        conf = new Configuration();
//        修改当前上传文件的副本数量
//        conf.set("dfs.replication", "2");
        fs = FileSystem.get(new URI(uri), conf, user);
    }

    @After
    public void after() throws IOException {
        fs.close();
    }

    @Test
    public void testIO() throws Exception {
        FileInputStream fis = new FileInputStream("D:\\0508\\bigData\\developme_tool\\hadoop-2.7.2.tar.gz");
        FSDataOutputStream fos = fs.create(new Path("/hadoop-2.7.2.tar.gz"));
        IOUtils.copyBytes(fis, fos, conf);
        IOUtils.closeStream(fis);
        IOUtils.closeStream(fos);

    }

    @Test
    public void testIO2() throws Exception {
        FSDataInputStream fdis = fs.open(new Path("/hadoop-2.7.2.tar.gz"));
        FileOutputStream fos = new FileOutputStream("D:\\wst\\hadoop-2.7.2.tar.gz.part1");
        byte[] buff = new byte[1024];
        for (int i = 0; i < 1024 * 128; i++) {
            fdis.read(buff);
            fos.write(buff);
        }
        IOUtils.closeStream(fdis);
        IOUtils.closeStream(fos);

    }
    @Test
    public void readFileSeek2() throws IOException, InterruptedException, URISyntaxException {


        // 2 打开输入流
        FSDataInputStream fis = fs.open(new Path("/hadoop-2.7.2.tar.gz"));

        // 3 定位输入数据位置
        fis.seek(1024*1024*128);

        // 4 创建输出流
        FileOutputStream fos = new FileOutputStream("d:\\wst\\hadoop-2.7.2.tar.gz.part2");

        // 5 流的对拷
        IOUtils.copyBytes(fis, fos, conf);

        // 6 关闭资源
        IOUtils.closeStream(fis);
        IOUtils.closeStream(fos);
    }

}
