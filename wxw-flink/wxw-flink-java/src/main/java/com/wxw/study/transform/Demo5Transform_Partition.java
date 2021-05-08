package com.wxw.study.transform;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author weixiaowei
 * @desc: 多流转换
 * @date: 2021/5/5
 */
public class Demo5Transform_Partition {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        // 全局设置并行度
//        environment.setParallelism(1);

        // source 从文件中读取数据 方式一
        String filePath = "wxw-flink/wxw-flink-java/src/main/resources/file/sensor.txt";
        DataStream<String> inputStream = environment.readTextFile(filePath);

        // lambada 表达式
        DataStream<SensorReading> dataStream = inputStream.map(line -> {
            String[] fields = line.split(",");
            return new SensorReading(fields[0], new Long(fields[1]), new Double(fields[2]));
        });

        // 0. global 全局汇总会一条流
        DataStream<String> globalStream = inputStream.global();

        // 1. shuffle 洗牌重发|| 使得均匀分布
        DataStream<String> shuffleStream = inputStream.shuffle();

        // 2. keyBy 分组
        KeyedStream<SensorReading, String> KeyedByStream = dataStream.keyBy(SensorReading::getSensorId);

        // 打印输出
        globalStream.print("globalStream");
        inputStream.print("inputStream");
        shuffleStream.print("shuffleStream");
        KeyedByStream.print("KeyedByStream");

        // 执行起来
        environment.execute();
    }

}
