package com.atguigu;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author wststart
 * @create 2019-07-01 11:47
 */
@Controller
public class BookHandle {
//    method：为限制当前匹配的请求的请求方式

    @RequestMapping(value = "/book/{bookid}", method = {RequestMethod.GET})
    public String handle1(@PathVariable(value = "bookid") Integer bookid) {
        System.out.println("收到查看请求");
        System.out.printf("查询到id为%d的图书", bookid);
        return "/WEB-INF/page/success.jsp";
    }

    @RequestMapping(value = "/book/{bookid}", method = {RequestMethod.DELETE})
    public String handle2(@PathVariable(value = "bookid") Integer bookid) {
        System.out.println("收到删除请求");
        System.out.printf("删除id为%d的图书", bookid);
        return "/WEB-INF/page/success.jsp";
    }

    @RequestMapping(value = "/book/{bookid}", method = {RequestMethod.PUT})
    public String handle3(@PathVariable(value = "bookid") Integer bookid) {
        System.out.println("收到修改请求");
        System.out.printf("修改id为%d的图书", bookid);
        return "/WEB-INF/page/success.jsp";
    }

    @RequestMapping(value = "/book", method = {RequestMethod.POST})
    public String handle4() {
        System.out.println("收到添加请求");
        return "/WEB-INF/page/success.jsp";
    }
}
