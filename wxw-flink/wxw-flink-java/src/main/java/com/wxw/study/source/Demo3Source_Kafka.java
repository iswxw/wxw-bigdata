package com.wxw.study.source;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.util.Properties;

/**
 * @author weixiaowei
 * @desc: 从集合中读取数据
 * @date: 2021/5/4
 */
public class Demo3Source_Kafka {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        // 全局设置并行度
        environment.setParallelism(1);

        //1. source 从kafka中读取数据 方式一
        Properties props = new Properties();
        props.put("bootstrap.servers","wxw.plus:9092");
        props.put("group.id","consumer-group");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer"); //key 序列化
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer"); //value 序列化
        props.put("auto.offset.reset","latest");

        FlinkKafkaConsumer<String> flinkKafkaConsumer = new FlinkKafkaConsumer<>("sensor", new SimpleStringSchema(), props);

        DataStreamSource<String> dataStreamSource = environment.addSource(flinkKafkaConsumer);

        //打印到控制台 currentStreamName 设置并行度
        dataStreamSource.print("FlinkKafkaConsumer").setParallelism(1);

        // 执行
        environment.execute("JobName");
    }
}
