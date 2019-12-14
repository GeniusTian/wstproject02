package com.atguigu.mapreduce.partition;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author wststart
 * @create 2019-07-22 18:48
 */
public class FlowCountReducer extends Reducer <FlowBean, Text, Text, FlowBean> {

    @Override
    protected void reduce(FlowBean key, Iterable <Text> values, Context context) throws IOException, InterruptedException {
        for (Text value : values) {
            context.write(value, key);

        }
    }
}
