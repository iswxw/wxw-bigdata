package com.wxw.study.transform;

import com.wxw.domain.SensorReading;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author weixiaowei
 * @desc: 多流转换
 * @date: 2021/5/5
 */
public class Demo4Transform_MultipleStream {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        // 全局设置并行度
        environment.setParallelism(1);

        // source 从文件中读取数据 方式一
        String filePath = "wxw-flink/wxw-flink-java/src/main/resources/file/sensor.txt";
        DataStream<String> inputStream = environment.readTextFile(filePath);

        // lambada 表达式
        DataStream<SensorReading> dataStream = inputStream.map(line -> {
            String[] fields = line.split(",");
            return new SensorReading(fields[0], new Long(fields[1]), new Double(fields[2]));
        });

        // 1. 分流，按照温度为30度分成两条流

        // 打印输出
        dataStream.print();

        // 执行起来
        environment.execute();
    }
}
