package com.atguigu.mapreduce.groupingcomparator;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author wststart
 * @create 2019-07-24 20:06
 */
public class GroupingComparatorReducer extends Reducer <Orderbean, NullWritable, Orderbean, NullWritable> {
    @Override
    protected void reduce(Orderbean key, Iterable <NullWritable> values, Context context) throws IOException, InterruptedException {
        context.write(key, NullWritable.get());
    }
}
