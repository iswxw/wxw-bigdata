package com.wxw.study.source;

import com.wxw.domain.SensorReading;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Arrays;

/**
 * @author weixiaowei
 * @desc: 从集合中读取数据
 * @date: 2021/5/4
 */
public class Demo1Source_Collection {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        // 全局设置并行度
        environment.setParallelism(1);

        //1. source 从集合中读取数据 方式一
        DataStreamSource<SensorReading> dataStreamSource1 = environment.fromCollection(Arrays.asList(
                new SensorReading("sensor_1", 1547718201L, 36.0),
                new SensorReading("sensor_2", 1547718202L, 23.7),
                new SensorReading("sensor_3", 1547718205L, 18.8)
        ));

        // 1. source 从集合中读取数据 方式二
        DataStreamSource<Integer> dataStreamSource2 = environment.fromElements(1,2,12,32,34,3,4,5,6);

        //打印到控制台 currentStreamName
        dataStreamSource1.print("fromCollection");
        // 设置并行度
        dataStreamSource2.print("fromElements").setParallelism(1);

        // 执行
        environment.execute("JobName");
    }
}
