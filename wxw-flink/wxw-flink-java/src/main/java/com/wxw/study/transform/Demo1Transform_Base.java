package com.wxw.study.transform;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/5/5
 */
public class Demo1Transform_Base {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        // 全局设置并行度
        //environment.setParallelism(1);

        // source 从文件中读取数据 方式一
        String filePath = "wxw-flink/wxw-flink-java/src/main/resources/file/sensor.txt";
        DataStream<String> inputStream = environment.readTextFile(filePath);

        // Map String 转换为 Integer
        DataStream<Integer> mapStream = inputStream.map(new MapFunction<String, Integer>() {
            @Override
            public Integer map(String value) throws Exception {
                return value.length();
            }
        });

        // FlatMap, 按逗号分割字段
        DataStream<String> flatMapStream = inputStream.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public void flatMap(String value, Collector<String> collector) throws Exception {
                String[] fields = value.split(",");
                for (String field : fields) {
                    collector.collect(field);
                }
            }
        });

        // Filter 筛选sensor_1开头 id 对应的数据
        DataStream<String> filterStream = inputStream.filter(new FilterFunction<String>() {
            @Override
            public boolean filter(String value) throws Exception {
                return value.startsWith("sensor_1");
            }
        });


        // 打印输出
        mapStream.print("map");
        flatMapStream.print("flatMap");
        filterStream.print("filter");

        // 执行起来
        environment.execute();
    }
}
