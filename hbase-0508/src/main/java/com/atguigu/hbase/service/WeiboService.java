package com.atguigu.hbase.service;

import com.atguigu.hbase.constant.Names;
import com.atguigu.hbase.dao.WeiboDAO;
import org.apache.hadoop.hbase.TableName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wststart
 * @create 2019-08-16 10:14
 */
public class WeiboService {
    private WeiboDAO DAO = new WeiboDAO();

    /**
     * 初始化表和命名空间
     *
     * @throws IOException
     */
    public void init() throws IOException {
//        1.创建命名空间以及表名的定义
        DAO.createNameSpace(Names.NAMESPACE_WEIBO);
//        2.创建微博内容表
        DAO.createTable(Names.TABLE_WEIBO, Names.WEIBO_FAMILY_DATA);
//        3.创建用户关系表
        DAO.createTable(Names.TABLE_RELATION, Names.RELATION_FAMILY_DATA);
//        4.创建用户微博内容接受
        DAO.createTable(Names.TABLE_INBOX, Names.INBOX_VERSIONS, Names.INBOX_FAMILY_DATA);
    }

    /**
     * 发微博
     *
     * @param star
     * @param content
     */
    public void publish(String star, String content) throws IOException {
//        1.向微博表中插入数据
        String rowKey = star + "_" + System.currentTimeMillis();
        DAO.putCell(Names.TABLE_WEIBO, rowKey, Names.WEIBO_FAMILY_DATA, Names.WEIBO_COLUMN_CONTENT, content);
//        2.根据查找stat所有fansId
        String prefix = star + ":followedby:";
        List <String> list = DAO.getRowKeysByPrefix(Names.TABLE_RELATION, prefix);
//        3.将微博id插到所有粉丝的inbox表的stat列中
        if (list.size() == 0) return;
        List <String> fansIds = new ArrayList <>();
        for (String row : list) {
            String[] split = row.split(":");
            fansIds.add(split[2]);
        }
        DAO.putCells(Names.TABLE_INBOX, fansIds, Names.INBOX_FAMILY_DATA, star, rowKey);
    }

    /**
     * 添加关注
     *
     * @param fans
     * @param star
     */
    public void follow(String fans, String star) throws IOException {
//        1.向关系表插入两条数据
        String fansRowKey = fans + ":follow:" + star;
        String starRowKey = star + ":followedby:" + fans;
        String time = System.currentTimeMillis() + "";
        DAO.putCell(Names.TABLE_RELATION, fansRowKey, Names.RELATION_FAMILY_DATA, Names.RELATION_COLUMN_TIME, time);
        DAO.putCell(Names.TABLE_RELATION, starRowKey, Names.RELATION_FAMILY_DATA, Names.RELATION_COLUMN_TIME, time);
//        2.从微博表中查出star最近的微博id
        String startRow = star + "_";
        String stopRow = star + "_|";
        List <String> list = DAO.getRowKeysByRange(Names.TABLE_WEIBO, startRow, stopRow);
//        3.更新inbox表
        if (list.size() == 0) return;
        for (String recentWeiboId : list) {
            DAO.putCell(Names.TABLE_INBOX, fans, Names.INBOX_FAMILY_DATA, star, recentWeiboId);
        }
    }

    /**
     * 取关
     *
     * @param fans
     * @param star
     */
    public void unFollow(String fans, String star) throws IOException {
//        1.向关系表中删除两条数据
        String fansRowKey = fans + ":follow:" + star;
        String starRowKey = star + ":followedby:" + fans;
        DAO.deleteRow(Names.TABLE_RELATION, fansRowKey);
        DAO.deleteRow(Names.TABLE_RELATION, starRowKey);
//        3.更新inbox表
        DAO.deleteColumn(Names.TABLE_INBOX, fans, Names.INBOX_FAMILY_DATA, star);
    }

    /**
     * 获取某个star的所有weibo
     *
     * @param star
     * @return
     */
    public List <String> getWeiboStarIn(String star) throws IOException {
//        1.获取某个star的所有weibo
        String rowKey = star + "_";
        return DAO.getCellsByPrefix(Names.TABLE_WEIBO, rowKey, Names.WEIBO_FAMILY_DATA, Names.WEIBO_COLUMN_CONTENT);
    }

    /**
     * 获取某个fans的所有star的近期weibo
     * @param fans
     * @return
     */
    public List <String> getAllRecentWeibos(String fans) throws IOException {
        List<String> list = DAO.getRow(Names.TABLE_INBOX, fans);

        return DAO.getCellsByRowKeys(Names.TABLE_WEIBO,list,Names.WEIBO_FAMILY_DATA,Names.WEIBO_COLUMN_CONTENT);
    }
}
