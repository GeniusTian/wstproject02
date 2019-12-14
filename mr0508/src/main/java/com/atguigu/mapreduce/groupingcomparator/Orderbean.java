package com.atguigu.mapreduce.groupingcomparator;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author wststart
 * @create 2019-07-24 20:07
 */
public class Orderbean implements WritableComparable <Orderbean> {
    private int order_id; // 订单id号
    private double price; // 价格

    public Orderbean() {
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    @Override
    public int compareTo(Orderbean o) {
        if (this.order_id != o.order_id) {
            return Integer.compare(this.order_id, o.order_id);
        } else {
            return -Double.compare(this.price, o.price);
        }

    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(order_id);
        out.writeDouble(price);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        order_id = in.readInt();
        price = in.readDouble();
    }

    @Override
    public String toString() {
        return "" + price;
    }
}
