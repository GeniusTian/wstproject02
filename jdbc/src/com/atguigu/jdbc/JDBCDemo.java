package com.atguigu.jdbc;

import com.atguigu.jdbcUtils.JDBCUtils;
import com.atguigu.jdbcUtils.ReflectionUtils;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wststart
 * @create 2019-06-23 16:06
 */
public class JDBCDemo {

    /**
     * 利用反射技术从数据库中可以查询任意类的对象吗，只需要修改sql语句即可
     *
     * @param sql  sql数据库语句
     * @param args 填写sql占位符的可变参数
     *             根据传入的sql语句返回任意类的对象
     */

    public static <T> List <T> getObject(Class <T> clazz, String sql, Object... args) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        T t = null;
        List <T> ts = new ArrayList <>();
        try {
//            1.获取连接,并得到resultSet结果集
            conn = JDBCUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                pstmt.setObject(i + 1, args[i]);
            }
            resultSet = pstmt.executeQuery();
//            2.得到resultSetMetaData对象
            ResultSetMetaData metaData = resultSet.getMetaData();
//            3.创建一个Map<String,Object>对象.
//              键：查询的列的别名也即是对应的类的相应的属性名
//              值：相应类的属性的值
//            Map <String, Object> values = new HashMap <>();
//            ArrayList <Object> list = new ArrayList <>();
//            4.处理结果集，利用ResultSetMetaDate像集合里面填充对应的值
            while (resultSet.next()) {//判断结果集中是否存在数据
                t = clazz.newInstance();//
                for (int i = 0; i < metaData.getColumnCount(); i++) {//判断从数据库读出几列数据
                    String columnLabel = metaData.getColumnLabel(i + 1);//得到每列名到每列
                    Object columnLValue = resultSet.getObject(i + 1);//得到列值
                    ReflectionUtils.setFieldValue(t, columnLabel, columnLValue);
                }
                ts.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeAll(resultSet, pstmt, conn);
        }
        return ts;
    }
    public static <T> List <T> getObject(Class <T> clazz, String sql) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        T t = null;
        List <T> ts = new ArrayList <>();
        try {
//            1.获取连接,并得到resultSet结果集
            conn = JDBCUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            resultSet = pstmt.executeQuery();
//            2.得到resultSetMetaData对象
            ResultSetMetaData metaData = resultSet.getMetaData();
//            3.创建一个Map<String,Object>对象.
//              键：查询的列的别名也即是对应的类的相应的属性名
//              值：相应类的属性的值
//            Map <String, Object> values = new HashMap <>();
//            ArrayList <Object> list = new ArrayList <>();
//            4.处理结果集，利用ResultSetMetaDate像集合里面填充对应的值
            while (resultSet.next()) {//判断结果集中是否存在数据
                t = clazz.newInstance();//
                for (int i = 0; i < metaData.getColumnCount(); i++) {//判断从数据库读出几列数据
                    String columnLabel = metaData.getColumnLabel(i + 1);//得到每列名到每列
                    Object columnLValue = resultSet.getObject(i + 1);//得到列值
                    ReflectionUtils.setFieldValue(t, columnLabel, columnLValue);
                }
                ts.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeAll(resultSet, pstmt, conn);
        }
        return ts;
    }
}
