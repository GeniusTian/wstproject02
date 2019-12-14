package com.atguigu.mapreduce.groupingcomparator;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * @author wststart
 * @create 2019-07-24 21:06
 */
public class OrderGroupingComparator extends WritableComparator {
    public OrderGroupingComparator() {
        super(Orderbean.class, true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        Orderbean aBean = (Orderbean) a;
        Orderbean bBean = (Orderbean) b;
        return Integer.compare(aBean.getOrder_id(), bBean.getOrder_id());
    }
}
