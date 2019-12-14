package com.atguigu.hbase.api;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * @author wststart
 * @create 2019-08-14 9:34
 */
public class HBaseUtil {

    private static Connection conn = null;

    static {

        try {
//            获取连接，通过zookeeper来得到hbase上的各种变化
            Configuration conf = HBaseConfiguration.create();
            conf.set("hbase.zookeeper.quorum", "hadoop102,hadoop103,hadoop104");
            conf.set("hbase.zookeeper.property.clientPort", "2181");
            conn = ConnectionFactory.createConnection(conf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建表
     *
     * @param tableName
     * @param families
     * @throws IOException
     */
    public static void createTable(String tableName, String family, String... families) throws IOException {
//       1.创建表需要获取admin对象
        Admin admin = conn.getAdmin();
        try {
//            判断表是否存在
            if (admin.tableExists(TableName.valueOf(tableName))) {
                System.out.println(tableName + "already exist");
                return;
            }
//       2.获取表明
            HTableDescriptor tableDesc = new HTableDescriptor(TableName.valueOf(tableName));
            for (String f : families) {
                //            3.获取列族
                HColumnDescriptor familyDesc = new HColumnDescriptor(f);
                tableDesc.addFamily(familyDesc);
            }
            admin.createTable(tableDesc);
//        4.关闭连接
        } finally {
            admin.close();
        }

    }

    /**
     * 对列族进行操作
     *
     * @param tableName
     * @param family
     * @throws IOException
     */
    public static void modifyTable(String tableName, String family) throws IOException {
        Admin admin = null;
        try {
            admin = conn.getAdmin();
            HColumnDescriptor familyDesc = new HColumnDescriptor(family);
            familyDesc.setMaxVersions(3);
            admin.modifyColumn(TableName.valueOf(tableName), familyDesc);
        } finally {
            admin.close();

        }
    }

    /**
     * 删除表
     *
     * @param tableName
     */
    public static void dropTable(String tableName) throws IOException {
        Admin admin = null;
        try {
            admin = conn.getAdmin();
            if (!admin.tableExists(TableName.valueOf(tableName))) return;
            admin.disableTable(TableName.valueOf(tableName));
            admin.deleteTable(TableName.valueOf(tableName));
        } finally {
            admin.close();

        }
    }

    /**
     * 添加数据
     *
     * @param tableName
     * @param rowKey
     * @param family
     * @param column
     * @param value
     */
    public static void putCell(String tableName, String rowKey, String family, String column, String value) throws IOException {
        Table table = null;
        try {
            table = conn.getTable(TableName.valueOf(tableName));
            Put put = new Put(Bytes.toBytes(rowKey));
            put.addColumn(Bytes.toBytes(family), Bytes.toBytes(column), Bytes.toBytes(value));
            table.put(put);
        } finally {
            if (table != null) {
                table.close();
            }
        }
    }

    /**
     * 查询数据
     *
     * @param tableName
     * @param rowKey
     * @throws IOException
     */
    public static void getRow(String tableName, String rowKey) throws IOException {
        Table table = null;
        try {
            table = conn.getTable(TableName.valueOf(tableName));
            Get get = new Get(Bytes.toBytes(rowKey));
            Result result = table.get(get);
            Cell[] cells = result.rawCells();
            for (Cell cell : cells) {
                byte[] valueBytes = CellUtil.cloneValue(cell);
                byte[] columnBytes = CellUtil.cloneQualifier(cell);
                System.out.println(Bytes.toString(columnBytes) + "-" + Bytes.toString(valueBytes));
            }
        } finally {
            if (table != null)
                table.close();
        }
    }

    /**
     * 扫描表
     *
     * @param tableName
     * @param startRow
     * @param stopRow
     * @throws IOException
     */
    public static void scanRows(String tableName, String startRow, String stopRow) throws IOException {
        Table table = null;
        ResultScanner results = null;
        try {
            table = conn.getTable(TableName.valueOf(tableName));
            Scan scan = new Scan(Bytes.toBytes(startRow), Bytes.toBytes(stopRow));
            results = table.getScanner(scan);
            for (Result result : results) {
                Cell[] cells = result.rawCells();
                for (Cell cell : cells) {
                    byte[] values = CellUtil.cloneValue(cell);
                    byte[] columns = CellUtil.cloneQualifier(cell);
                    byte[] rows = CellUtil.cloneRow(cell);
                    System.out.println(Bytes.toString(rows) + "-" + Bytes.toString(columns) + "-" + Bytes.toString(values));
                }
            }
        } finally {
            if (results != null)
                results.close();
            if (table != null)
                table.close();
        }
    }

    /**
     * 利用过滤器以value做条件从表中过滤一些数据
     *
     * @param tableName
     * @param family
     * @param column
     * @param value
     * @throws IOException
     */
    public static void getRowByColumn(String tableName, String family, String column, String value) throws IOException {
        Table table = null;
        ResultScanner results = null;
        try {
            table = conn.getTable(TableName.valueOf(tableName));
            Scan scan = new Scan();
            SingleColumnValueFilter filter = new SingleColumnValueFilter(Bytes.toBytes(family), Bytes.toBytes(column), CompareFilter.CompareOp.EQUAL, Bytes.toBytes(value));
//            如果行中没有该列不返回
            filter.setFilterIfMissing(true);
            scan.setFilter(filter);
            results = table.getScanner(scan);
            for (Result result : results) {
                Cell[] cells = result.rawCells();
                for (Cell cell : cells) {
                    byte[] values = CellUtil.cloneValue(cell);
                    byte[] columns = CellUtil.cloneQualifier(cell);
                    byte[] rows = CellUtil.cloneRow(cell);
                    System.out.println(Bytes.toString(rows) + "-" + Bytes.toString(columns) + "-" + Bytes.toString(values));
                }
            }
        } finally {
            if (results != null)
                results.close();
            if (table != null)
                table.close();
        }
    }

    /**
     * 删除一行数据
     *
     * @param tableName
     * @param rowKey
     * @throws IOException
     */
    public static void deleteRow(String tableName, String rowKey) throws IOException {
        Table table = conn.getTable(TableName.valueOf(tableName));
        Delete delete = new Delete(Bytes.toBytes(rowKey));

        table.delete(delete);

        table.close();
    }

    /**
     * 删除指定列的数据
     *
     * @param tableName
     * @param rowKey
     * @param family
     * @param column
     * @throws IOException
     */
    public static void deleteColumn(String tableName, String rowKey, String family, String column) throws IOException {

        Table table = conn.getTable(TableName.valueOf(tableName));

        Delete delete = new Delete(Bytes.toBytes(rowKey));

        delete.addColumn(Bytes.toBytes(family), Bytes.toBytes(column));

        table.delete(delete);

        table.close();
    }
}
