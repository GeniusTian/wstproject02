package com.atguigu;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author wststart
 * @create 2019-07-01 20:01
 */
@Controller
public class RepsonseHeader {
    @RequestMapping(value = "/response/handle1", produces = "text/html;charset=utf-8")//第二个参数应该告诉浏览器我返回的时一个文本并用utf-8帮我解析
    @ResponseBody//该注解可以将方法返回值直接返回给原页面
    public String handle1() {
        System.out.println("接到handle1请求");
        return "sucess成功";

    }

    //    如果页面时一个jsp页面，需要转发到jsp页面，传递数据
    @RequestMapping(value = "/response/handle2")
    public String handle2(Map map, Model model, ModelMap modelMap) {
        System.out.println("收到handle2请求");
        map.put("map", map.getClass().getName());
        model.addAttribute("model", model.getClass().getName());
        modelMap.addAttribute("modelMap",modelMap.getClass().getName());
        return "success.jsp";
    }
    @RequestMapping(value = "/response/sendAjax")
    @ResponseBody
    public Object handle3(String jsonstr) {
        System.out.println("收到handle3请求");
        return "success.jsp";
    }
}
