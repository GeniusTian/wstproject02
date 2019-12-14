package com.atguigu.consumer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * @author wststart
 * @create 2019-08-21 19:51
 */
public class CustomProducer {
    public static void main(String[] args) {
        Properties props = new Properties();
//        集群地址
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "hadoop102:9092,hadoop103:9092");
//        序列化
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
//        响应类型
        props.put(ProducerConfig.ACKS_CONFIG, "-1");
//        重试次数
        props.put(ProducerConfig.RETRIES_CONFIG, 1);
//        1.创建kafka的producer对象
        KafkaProducer <String, String> producer = new KafkaProducer <String, String>(props);
//        2.调用send方法
        ProducerRecord <String, String> record = new ProducerRecord <String, String>("first", "message");
        producer.send(record);
        producer.close();
    }
}
