package com.wxw.manager.helper;

import com.alibaba.fastjson.JSON;
import com.wxw.domain.Metric;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author weixiaowei
 * @desc: 往kafka中写数据可以使用这个main函数进行测试一下
 * @date: 2021/5/3
 */
@Slf4j
public class KafkaHelper {

    public static final String broker_list = "wxw.plus:9092";
    public static final String topic = "metric";  // kafka topic，Flink 程序中需要和这个统一


    public static void writeToKafka() throws InterruptedException, ExecutionException {
        Properties props = new Properties();
        props.put("bootstrap.servers", broker_list);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer"); //key 序列化
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer"); //value 序列化
        KafkaProducer producer = new KafkaProducer<String, String>(props);

        Metric metric = new Metric();
        metric.setTimestamp(System.currentTimeMillis());
        metric.setName("name-mem");
        Map<String, String> tags = new HashMap<>();
        Map<String, Object> fields = new HashMap<>();

        tags.put("cluster", "wxw-cluster");
        tags.put("host_ip", "wxw.plus");

        fields.put("used_percent", 90d);
        fields.put("max", 27244873d);
        fields.put("used", 17244873d);
        fields.put("init", 27244873d);

        metric.setTags(tags);
        metric.setFields(fields);

        ProducerRecord record = new ProducerRecord<String, String>(topic, null, null, JSON.toJSONString(metric));
        Future send = producer.send(record);
        log.info("send = {}",send.get());
        log.info("发送数据 = {}",JSON.toJSONString(metric));
        producer.flush();
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        while (true) {
            Thread.sleep(3000);
            writeToKafka();
        }
    }

}
