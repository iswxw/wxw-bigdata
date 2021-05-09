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
public class Demo3Transform_Reduce {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        // 全局设置并行度
        environment.setParallelism(1);

        // source 从文件中读取数据 方式一
        String filePath = "wxw-flink/wxw-flink-java/src/main/resources/file/sensor.txt";
        DataStream<String> inputStream = environment.readTextFile(filePath);

        // lambada 表达式
        SingleOutputStreamOperator<SensorReading> sensorLambdaStream = inputStream.map(line -> {
            String[] fields = line.split(",");
            return new SensorReading(fields[0], new Long(fields[1]), new Double(fields[2]));
        });

        // 分组
        KeyedStream<SensorReading, Tuple> readingKeyedStream = sensorLambdaStream.keyBy("sensorId");
//        KeyedStream<SensorReading, String> readingKeyedStream2 = sensorLambdaStream.keyBy(SensorReading::getSensorId);

        //reduce 滚动聚合，取最大温度值以及当前最新的时间戳
//        SingleOutputStreamOperator<SensorReading> reduceStream = readingKeyedStream.reduce(new ReduceFunction<SensorReading>() {
//            @Override
//            public SensorReading reduce(SensorReading v1, SensorReading v2) throws Exception {
//                return new SensorReading(v1.getSensorId(),v2.getTimestamp(),Math.max(v1.getTemperature(),v2.getTemperature()));
//            }
//        });

        SingleOutputStreamOperator<SensorReading> reduceLambdaStream = readingKeyedStream.reduce((curState, newData)
                -> new SensorReading(curState.getSensorId(), newData.getTimestamp(), Math.max(curState.getTemperature(), newData.getTemperature())));

        // 打印输出
        reduceLambdaStream.print();

        // 执行起来
        environment.execute();
    }
}
