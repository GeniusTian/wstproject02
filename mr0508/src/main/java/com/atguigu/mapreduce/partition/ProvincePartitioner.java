package com.atguigu.mapreduce.partition;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * @author wststart
 * @create 2019-07-24 18:16
 */
public class ProvincePartitioner extends Partitioner <FlowBean, Text> {

    @Override
    public int getPartition(FlowBean flowBean, Text text, int numPartitions) {
        String phoneNumber = text.toString().substring(0, 3);
        switch (phoneNumber) {
            case "136":
                return 0;
            case "137":
                return 1;
            case "138":
                return 2;
            case "139":
                return 3;
            default:
                return 4;
        }
    }
}
