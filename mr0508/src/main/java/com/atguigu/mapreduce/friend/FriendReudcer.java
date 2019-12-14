package com.atguigu.mapreduce.friend;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author wststart
 * @create 2019-07-28 15:24
 */
public class FriendReudcer extends Reducer <Text, Text, Text, Text> {
    ArrayList <String> list = new ArrayList <>();
    Text text = new Text();

    @Override
    protected void reduce(Text key, Iterable <Text> values, Context context) throws IOException, InterruptedException {
        String kStr = key.toString();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(kStr);
        if (!list.contains(kStr) && !list.contains(stringBuilder.reverse().toString())) {
            list.add(kStr);
            context.write(key, values.iterator().next());

        }

    }
}
