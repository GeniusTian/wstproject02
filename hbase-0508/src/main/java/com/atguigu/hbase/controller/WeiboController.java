package com.atguigu.hbase.controller;

import com.atguigu.hbase.service.WeiboService;

import java.io.IOException;
import java.util.List;

/**
 * @author wststart
 * @create 2019-08-16 10:14
 */
public class WeiboController {
    private WeiboService service = new WeiboService();

    /**
     * 初始化方法
     *
     * @throws IOException
     */
    public void init() throws IOException {
        service.init();
    }

    /**
     * 发微博
     *
     * @param star
     * @param content
     */
    public void publish(String star, String content) throws IOException {
        service.publish(star, content);
    }

    /**
     * 添加关注用户
     *
     * @param fans
     * @param star
     */
    public void follow(String fans, String star) throws IOException {
        service.follow(fans, star);
    }

    /**
     * 取关
     *
     * @param fans
     * @param star
     */
    public void unFollow(String fans, String star) throws IOException {
        service.unFollow(fans, star);
    }
//获取关注的人的weibo

    /**
     * 获取某个star的所有weibo
     *
     * @param star
     * @return
     */
    public List <String> getWeiboStarIn(String star) throws IOException {

        return service.getWeiboStarIn(star);

    }

    /**
     * 获取某个fans的所有star的近期weibo
     *
     * @param fans
     * @return
     */
    public List <String> getAllRecentWeibos(String fans) throws IOException {

        return service.getAllRecentWeibos(fans);
    }
}
