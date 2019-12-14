package com.atguigu.mapreduce.inputformat;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

/**
 * @author wststart
 * @create 2019-07-23 15:32
 */
public class SmallFileRecordReader extends RecordReader<Text, BytesWritable> {
    private FileSplit fileSplit;
    private Configuration conf;
    private Text currentKey = new Text();
    private BytesWritable currentValue = new BytesWritable();
    private boolean flag = true;

    public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        fileSplit = (FileSplit) split;
        conf = context.getConfiguration();
    }

    /**
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public boolean nextKeyValue() throws IOException, InterruptedException {
        if (flag) {
            // 获取文件的路径
            String path = fileSplit.getPath().toString();
            //封装key
            currentKey.set(path);

            //获取到文件系统对象
            FileSystem fs = FileSystem.get(conf);
            //获取输入流
            FSDataInputStream fis = fs.open(new Path(path));

            //读取整个文件的内容到字节数组中
            byte[] buf = new byte[(int) fileSplit.getLength()];
            IOUtils.readFully(fis, buf, 0, buf.length);

            //封装value
            currentValue.set(buf, 0, buf.length);

            flag = false;
            return true;
        }
        return false;
    }

    public Text getCurrentKey() throws IOException, InterruptedException {
        return currentKey;
    }

    public BytesWritable getCurrentValue() throws IOException, InterruptedException {
        return currentValue;
    }

    public float getProgress() throws IOException, InterruptedException {
        return 0;
    }

    public void close() throws IOException {

    }
}
