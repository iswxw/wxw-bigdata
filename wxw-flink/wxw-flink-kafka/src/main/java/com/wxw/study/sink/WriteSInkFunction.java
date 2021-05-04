package com.wxw.study.sink;

import com.alibaba.fastjson.JSON;
import com.wxw.domain.Student;
import com.wxw.manager.sink.SinKToMySQL;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.PrintSinkFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.util.Properties;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/5/4
 */
public class WriteSInkFunction {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        Properties props = new Properties();
        props.put("bootstrap.servers", "wxw.plus:9092");
        props.put("zookeeper.connect", "wxw.plus:2181");
        props.put("group.id", "metric-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");  //key 反序列化
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("auto.offset.reset", "latest"); //value 反序列化
        SingleOutputStreamOperator<Student> dataStreamSource = env.addSource(new FlinkKafkaConsumer<>(
                "student",  //kafka topic
                new SimpleStringSchema(),  // String 序列化
                props)).setParallelism(1).map(string-> JSON.parseObject(string,Student.class));

        dataStreamSource.addSink(new PrintSinkFunction<>()); //把从 kafka 读取到的数据打印在控制台
        dataStreamSource.print(); //把从 kafka 读取到的数据打印在控制台

        // 自定义 Sink 并 toMySQL
        dataStreamSource.addSink(new SinKToMySQL());
        env.execute("Flink add data source");
    }
}
