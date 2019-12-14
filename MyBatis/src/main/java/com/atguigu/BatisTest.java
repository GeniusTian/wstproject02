package com.atguigu;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author wststart
 * @create 2019-07-01 15:35
 */
public class BatisTest {
//    创建sqlsessionFactory实例，用工厂类的实例去获取sqlsession的实例来操作数据库
    private static SqlSessionFactory ssf;

    /**
     * 该静态代码块通过流将配置好的mybatis配置文件读入SqlSessionFactory生产SqlSessionFactory的实例
     */
    static {

        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
            ssf = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test1() {
//        通过openSession方法得到SqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = ssf.openSession();
            Employee e = sqlSession.selectOne("myemployees.selectEmp", 100);
            System.out.println(e);
        } finally {
            try {
                sqlSession.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
