package com.atguigu.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author wststart
 * @create 2019-07-19 15:15
 */
public class HdfsAPI {
    Configuration conf = null;
    FileSystem fs = null;
    String uri = "hdfs://hadoop102:9000";
    String user = "wst";

    /**
     * 在hdfs新建目录
     *
     * @throws IOException
     * @throws URISyntaxException
     * @throws InterruptedException
     */
    @Test
    public void testmkdir() throws IOException, URISyntaxException, InterruptedException {
        // 1 获取文件系统
        Configuration configuration = new Configuration();
        // 配置在集群上运行
        // configuration.set("fs.defaultFS", "hdfs://hadoop102:9000");
        // FileSystem fs = FileSystem.get(configuration);

        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop102:9000"), configuration, "wst");

        // 2 创建目录
        fs.mkdirs(new Path("/0508/daxian/banzhang/lyh/"));

        // 3 关闭资源
        fs.close();
        System.out.println("over");
    }

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

    /**
     * 向hdfs上传文件和put功能一样
     *
     * @throws Exception
     */
    @Test
    public void testCopyFromLocalFile() throws Exception {
        fs.copyFromLocalFile(new Path("D:\\banzhang.txt"), new Path("/"));
    }

    /**
     * 从hdfs上下载文件和get功能类似
     *
     * @throws Exception
     */
    @Test
    public void testCopyToLocalFile() throws Exception {
//          四个参数分别代表的含义：1.代表是否删除原路径上的文件 2.原路径   3.本地路径  4.是否开启文件校验检验文件是否安全
        fs.copyToLocalFile(false, new Path("/banzhang.txt"), new Path("D:/xiaohua.txt"), true);
    }

    /**
     * 删除hdfs上的文件
     * 第一个参数是文件的地址，第二个参数是否采用递归删除
     *
     * @throws IOException
     */
    @Test
    public void testDelete() throws IOException {
//        fs.delete(new Path("/banzhang.txt"),true);
        fs.delete(new Path("/0508/"), true);
    }

    /**
     * 文件重命名
     *
     * @throws IOException
     */
    @Test
    public void testRename() throws IOException {
        fs.mkdirs(new Path("/0508/shuaige"));
        fs.rename(new Path("/0508/shuaige"), new Path("/0508/choubi"));
    }

    /**
     * 获取文件信息
     *
     * @throws IOException
     */
    @Test
    public void testListFiles() throws IOException {
        RemoteIterator <LocatedFileStatus> listFiles = fs.listFiles(new Path("/"), true);
        while (listFiles.hasNext()) {
            LocatedFileStatus fileStatus = listFiles.next();
            System.out.println(fileStatus.getGroup());
            BlockLocation[] blockLocations = fileStatus.getBlockLocations();
            for (BlockLocation blockLocation : blockLocations) {
                System.out.println(blockLocation);
                String[] hosts = blockLocation.getHosts();
                for (String host : hosts) {
                    System.out.println(host);
                }
            }
            System.out.println(fileStatus.getPermission());
            System.out.println(fileStatus.getPath().getName());
        }
    }

    /**
     * 判断是否为文件还是目录
     *
     * @throws IOException
     */
    @Test
    public void testFileStatus() throws IOException {


        printFileOrDir("/",fs);
    }

    public void printFileOrDir(String path, FileSystem fs) throws IOException {
        FileStatus[] fileStatuses = fs.listStatus(new Path(path));
        for (FileStatus fileStatus : fileStatuses) {
            if (fileStatus.isDirectory()) {
                String currentPath = fileStatus.getPath().toString().substring(uri.length());
                System.out.println("dir:" + currentPath);
                printFileOrDir(currentPath, fs);
            } else {
                System.out.println("file:" + fileStatus.getPath().getName());
            }
        }
    }

}
