package com.wxw.study;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.util.Properties;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/5/3
 */
public class StreamJobWithKafka {
    public static void main(String[] args) throws Exception {

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        Properties props = new Properties();
        props.put("bootstrap.servers", "wxw.plus:9092");
        props.put("zookeeper.connect", "wxw.plus:2181");
        props.put("group.id", "metric-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");  //key 反序列化
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("auto.offset.reset", "latest"); //value 反序列化
        DataStreamSource<String> dataStreamSource = env.addSource(new FlinkKafkaConsumer<>(
                "metric",  //kafka topic
                new SimpleStringSchema(),  // String 序列化
                props)).setParallelism(1);
        dataStreamSource.print(); //把从 kafka 读取到的数据打印在控制台
        env.execute("Flink add data source");
    }
}
