package com.atguigu.mapreduce.flowcount;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author wststart
 * @create 2019-07-22 18:48
 */
public class FlowCountReducer extends Reducer <Text, FlowBean, Text, FlowBean> {
    FlowBean resultFlow = new FlowBean();

    @Override
    protected void reduce(Text key, Iterable <FlowBean> values, Context context) throws IOException, InterruptedException {
        long sumUpFlow = 0;
        long sumDownFlow = 0;
        for (FlowBean value : values) {
            sumUpFlow += value.getUpFlow();
            sumDownFlow += value.getDownFlow();
        }
        resultFlow.setAll(sumUpFlow, sumDownFlow);
        context.write(key, resultFlow);
    }
}
