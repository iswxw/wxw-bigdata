package com.wxw.study.transform;

import com.wxw.domain.SensorReading;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author weixiaowei
 * @desc: 滚动聚合
 * @date: 2021/5/5
 */
public class Demo2Transform_RollingAggregation {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        // 全局设置并行度
        environment.setParallelism(1);

        // source 从文件中读取数据 方式一
        String filePath = "wxw-flink/wxw-flink-java/src/main/resources/file/sensor.txt";
        DataStream<String> inputStream = environment.readTextFile(filePath);

        // 转换成sensor类型 方式一
//        DataStream<SensorReading> sensorStream = inputStream.map(new MapFunction<String, SensorReading>() {
//            @Override
//            public SensorReading map(String value) throws Exception {
//                String[] fields = value.split(",");
//                return new SensorReading(fields[0], new Long(fields[1]), new Double(fields[2]));
//            }
//        });

        // lambada 表达式
        SingleOutputStreamOperator<SensorReading> sensorLambdaStream = inputStream.map(line -> {
            String[] fields = line.split(",");
            return new SensorReading(fields[0], new Long(fields[1]), new Double(fields[2]));
        });

        // 分组
        KeyedStream<SensorReading, Tuple> readingKeyedStream1 = sensorLambdaStream.keyBy("sensorId");
//        KeyedStream<SensorReading, String> readingKeyedStream2 = sensorLambdaStream.keyBy(SensorReading::getSensorId);

        // 滚动聚合，取最大温度值
        DataStream<SensorReading> maxTemp = readingKeyedStream1.maxBy("temperature");


        // 打印输出
        maxTemp.print();

        // 执行起来
        environment.execute();
    }
}
