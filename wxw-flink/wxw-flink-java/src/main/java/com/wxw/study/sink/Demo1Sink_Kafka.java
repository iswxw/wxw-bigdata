package com.wxw.study.sink;

import com.wxw.domain.SensorReading;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/5/7
 */
public class Demo1Sink_Kafka {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        // 全局设置并行度
        environment.setParallelism(1);

        // source 从文件中读取数据 方式一
        String filePath = "wxw-flink/wxw-flink-java/src/main/resources/file/sensor.txt";
        DataStream<String> inputStream = environment.readTextFile(filePath);

        // lambada 表达式
        DataStream<String> dataStream = inputStream.map(line -> {
            String[] fields = line.split(",");
            return new SensorReading(fields[0], new Long(fields[1]), new Double(fields[2])).toString();
        });

        // sink 进入 kafka
        // ./kafka-console-consumer.sh --topic topic-sink-test --from-beginning --bootstrap-server wxw.plus:9092
        dataStream.addSink(new FlinkKafkaProducer<String>(
                        "wxw.plus:9092", "topic-sink-test", new SimpleStringSchema()));

        // 执行起来
        environment.execute();
    }
}
