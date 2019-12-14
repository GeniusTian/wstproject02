package com.atguigu;

import org.junit.Test;

/**
 * @author wststart
 * @create 2019-07-30 14:07
 */
public class StringTest {
    @Test
    public void test1(){
        String str = "1,2,3,4,5";
        String[] split = str.split(",");
        System.out.println(split.length);
    }
}
