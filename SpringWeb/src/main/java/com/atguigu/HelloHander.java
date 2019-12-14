package com.atguigu;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author wststart
 * @create 2019-06-29 15:30
 */
//用来声明是一个控制器
@Controller
public class HelloHander {
    @RequestMapping(value = "/hello")
    public String handle1() {
        System.out.println("处理了helloworld请求");
        return "success.jsp";
    }

    @RequestMapping(value = "/testRequest")
    public String handle2(String username, int age) {
        System.out.println("收到testRequest请求");
        System.out.println(username + age);
        return "success.jsp";
    }
}
