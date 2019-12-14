package com.atguigu.mapreduce.inputformat;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

import java.io.IOException;

/**
 * @author wststart
 * @create 2019-07-23 15:25
 * 自定义inputformat
 */
public class SmallFileInputFormat extends FileInputFormat <Text, BytesWritable> {
    /**
     * 我们希望将读取的一个文件内容完整的写到sequenceFile中，所以我们不允许文件被切割
     *
     * @param context
     * @param filename
     * @return
     */
    @Override
    protected boolean isSplitable(JobContext context, Path filename) {
        return false;
    }

    public RecordReader <Text, BytesWritable> createRecordReader(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        SmallFileRecordReader smallFileRecordReader = new SmallFileRecordReader();
        smallFileRecordReader.initialize(split, context);
        return smallFileRecordReader;
    }
}
