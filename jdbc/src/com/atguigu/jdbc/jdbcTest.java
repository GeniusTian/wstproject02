package com.atguigu.jdbc;

import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author wststart
 * @create 2019-06-21 14:14
 */
public class jdbcTest {
    //    驱动的全类名
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/mysql0508";
    private static final String USER = "root";
    private static final String PASSWORD = "root";


    public static void main(String[] args) {
        Connection connection = null;
        Statement stmt = null;
        try {
//          1.加载驱动
            Class.forName(DRIVER);
//          2.创建一个数据库连接对象
//            需要三个参数url user password
//            url:127.0.0.1
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
//            3.创建statement对象 用于执行sql语句
            stmt = connection.createStatement();
            /*
             * 执行sql的方法
             * DQL 有返回结果executeQuery
             * DML 只返回改变了多少行 executeUpdate
             * 其他SQL execute
             *
             * */
            boolean flag = stmt.execute("INSERT INTO books (bname,author,quantity) VALUES ('哈利波特','罗琳',6)");
            System.out.println(flag);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Connection getConnection() throws IOException, ClassNotFoundException, SQLException {
//        1.准备连接数据库的4个字符串
        String user = null;
        String password = null;
        String jdbcUrl = null;
        String driver = null;
//        1).创建Properties对象
        Properties prop = new Properties();
//        2).加载相应配置文件
        prop.load(this.getClass().getClassLoader().getResourceAsStream("jdbc.properties"));
//        3).具体决定四个字符串的值
         user = prop.getProperty("user");
        password = prop.getProperty("password");
        jdbcUrl = prop.getProperty("jdbcUrl");
        driver = prop.getProperty("driver");
//        2.加载数据库驱动程序(对应的Driver 实现类中有注册驱动的静态代码块)
        Class.forName(driver);
//        3.通过DriverManager的getConnection()方法获取数据库的连接
        return  DriverManager.getConnection(jdbcUrl,user,password);
    }
    @Test
    public void test1() throws SQLException, IOException, ClassNotFoundException {

        System.out.println(getConnection());
    }

}
