package com.atguigu.mapreduce.reducejoin;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author wststart
 * @create 2019-07-26 19:23
 */
public class OrderBean implements WritableComparable <OrderBean> {
    private String id;
    private int pid;
    private int amount;
    private String pname;

    public OrderBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    @Override
    public String toString() {
        return id + "\t" + pname + "\t" + amount;
    }

    @Override
    public int compareTo(OrderBean o) {
        if (Integer.compare(this.pid, o.pid) != 0) {
            return Integer.compare(this.pid, o.pid);
        } else {
            return pname.compareTo(o.pname);
        }
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(id);
        out.writeInt(pid);
        out.writeInt(amount);
        out.writeUTF(pname);

    }

    @Override
    public void readFields(DataInput in) throws IOException {
        id = in.readUTF();
        pid = in.readInt();
        amount = in.readInt();
        pname = in.readUTF();

    }
}
