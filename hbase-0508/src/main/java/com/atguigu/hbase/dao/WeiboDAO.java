package com.atguigu.hbase.dao;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wststart
 * @create 2019-08-16 10:14
 */
public class WeiboDAO {
    private static Connection conn = null;
    private Admin admin = null;
    private Table table = null;

    static {
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "hadoop102,hadoop103,hadoop104");
        try {
            conn = ConnectionFactory.createConnection(conf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建命名空间
     *
     * @param nameSpace
     * @throws IOException
     */
    public void createNameSpace(String nameSpace) throws IOException {
        admin = conn.getAdmin();
        try {
            admin.getNamespaceDescriptor(nameSpace);
        } catch (NamespaceNotFoundException e) {
            NamespaceDescriptor build = NamespaceDescriptor.create(nameSpace).build();
            admin.createNamespace(build);
        } finally {
            admin.close();
        }
    }

    /**
     * 创建表
     *
     * @param tableName
     * @param families
     * @throws IOException
     */
    public void createTable(String tableName, String... families) throws IOException {
        createTable(tableName, 1, families);
    }

    /**
     * 创建表
     *
     * @param tableName
     * @param versions
     * @param families
     * @throws IOException
     */
    public void createTable(String tableName, Integer versions, String... families) throws IOException {
        try {
            admin = conn.getAdmin();
            if (admin.tableExists(TableName.valueOf(tableName))) {
                System.out.println("table" + tableName + "already exists");
                return;
            }
            HTableDescriptor hTableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
            for (String family : families) {

                hTableDescriptor.addFamily(new HColumnDescriptor(family).setMaxVersions(versions));
            }
            admin.createTable(hTableDescriptor);
        } finally {
            admin.close();
        }
    }

    /**
     * 发微博
     *
     * @param tableName
     * @param rowKey
     * @param value
     * @throws IOException
     */
    public void putCell(String tableName, String rowKey, String family, String column, String value) throws IOException {
        try {
            table = conn.getTable(TableName.valueOf(tableName));
            Put put = new Put(Bytes.toBytes(rowKey));
            put.addColumn(Bytes.toBytes(family), Bytes.toBytes(column), Bytes.toBytes(value));
            table.put(put);
        } finally {
            table.close();
        }
    }

    /**
     * 根据前缀查询
     *
     * @param tableName
     * @param rowKey
     * @return
     * @throws IOException
     */
    public List <String> getRowKeysByPrefix(String tableName, String rowKey) throws IOException {
        ResultScanner results = null;
        try {
            List <String> list = new ArrayList <>();
            table = conn.getTable(TableName.valueOf(tableName));

            Scan scan = new Scan();
            scan.setRowPrefixFilter(Bytes.toBytes(rowKey));
            results = table.getScanner(scan);
            for (Result result : results) {
                list.add(Bytes.toString(result.getRow()));
            }
            return list;
        } finally {
            if (results != null) {
                results.close();
            }
            table.close();
        }
    }

    public void putCells(String tableName, List <String> rowKeys, String family, String column, String value) throws IOException {
        try {
            Table table = conn.getTable(TableName.valueOf(tableName));
            List <Put> puts = new ArrayList <>();
            for (String rowKey : rowKeys) {
                Put put = new Put(Bytes.toBytes(rowKey));
                put.addColumn(Bytes.toBytes(family), Bytes.toBytes(column), Bytes.toBytes(value));
                puts.add(put);
            }
            table.put(puts);
        } finally {
            table.close();
        }
    }

    public List <String> getRowKeysByRange(String tableName, String startRow, String stopRow) throws IOException {
        ResultScanner results = null;
        try {
            List <String> list = new ArrayList <>();
            table = conn.getTable(TableName.valueOf(tableName));
            Scan scan = new Scan();
            scan.setStartRow(Bytes.toBytes(startRow));
            scan.setStopRow(Bytes.toBytes(stopRow));
            results = table.getScanner(scan);
            for (Result result : results) {
                list.add(Bytes.toString(result.getRow()));
            }
            return list;
        } finally {
            table.close();
            if (results != null) {
                results.close();
            }
        }
    }

    public void deleteRow(String tableName, String rowKey) throws IOException {
        try {
            table = conn.getTable(TableName.valueOf(tableName));
            Delete delete = new Delete(Bytes.toBytes(rowKey));
            table.delete(delete);
        } finally {
            table.close();
        }
    }

    public void deleteColumn(String tableName, String rowKey, String family, String column) throws IOException {
        try {
            table = conn.getTable(TableName.valueOf(tableName));
            Delete delete = new Delete(Bytes.toBytes(rowKey));
            delete.addColumn(Bytes.toBytes(family), Bytes.toBytes(column));
            table.delete(delete);
        } finally {
            table.close();
        }
    }

    public List <String> getCellsByPrefix(String tableName, String prefix, String family, String column) throws IOException {
        ResultScanner results = null;
        try {
            List <String> list = new ArrayList <>();
            table = conn.getTable(TableName.valueOf(tableName));
            Scan scan = new Scan();
            scan.setRowPrefixFilter(Bytes.toBytes(prefix));
            scan.addColumn(Bytes.toBytes(family), Bytes.toBytes(column));
//        1.行的集合
            results = table.getScanner(scan);
            for (Result result : results) {
                //            2.每一行内对应的column的数组
                Cell[] cells = result.rawCells();
                list.add(Bytes.toString(CellUtil.cloneQualifier(cells[0])));
            }
            return list;
        } finally {
            table.close();
            if (results != null) {
                results.close();
            }

        }
    }

    public List <String> getRow(String tableName, String rowKey) throws IOException {
        try {
            List <String> list = new ArrayList <>();
            table = conn.getTable(TableName.valueOf(tableName));
            Get get = new Get(Bytes.toBytes(rowKey));
            get.setMaxVersions();
            Result result = table.get(get);
            Cell[] cells = result.rawCells();
            for (Cell cell : cells) {
                list.add(Bytes.toString(CellUtil.cloneQualifier(cell)));
            }
            return list;
        } finally {
            table.close();
        }
    }

    public List <String> getCellsByRowKeys(String tableName, List <String> rowKeys, String family, String column) throws IOException {
        List <Get> gets = new ArrayList <>();
        List <String> list = new ArrayList <>();
        Get get = null;
        try {
            table = conn.getTable(TableName.valueOf(tableName));
            for (String rowKey : rowKeys) {
                get = new Get(Bytes.toBytes(rowKey));
                get.addColumn(Bytes.toBytes(family), Bytes.toBytes(column));
                gets.add(get);
            }
            Result[] results = table.get(gets);
            for (Result result : results) {
                Cell[] cells = result.rawCells();
                list.add(Bytes.toString(CellUtil.cloneValue(cells[0])));
            }
            return list;
        } finally {
            table.close();
        }
    }
}
