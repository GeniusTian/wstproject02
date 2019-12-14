package com.atguigu.flume.interceptor;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.util.List;

/**
 * @author wststart
 * @create 2019-08-11 14:54
 */
public class CustomInterceptor implements Interceptor {

    public void initialize() {

    }

    public Event intercept(Event event) {
        byte[] body = event.getBody();
        if (body[0] >= '1' && body[0] <= '9') {
            event.getHeaders().put("type", "number");
        } else if (body[0] >= 'a' && body[0] <= 'z') {
            event.getHeaders().put("type", "letter");
        }
        return event;
    }


    public List <Event> intercept(List <Event> list) {
        for (Event event : list) {
            intercept(event);
        }
        return list;
    }

    public void close() {

    }
    public static class Builder implements Interceptor.Builder{

        public Interceptor build() {
            return new CustomInterceptor();
        }

        public void configure(Context context) {

        }
    }
}
