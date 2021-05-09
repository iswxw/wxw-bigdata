package com.wxw.study.transform;

import com.wxw.domain.SensorReading;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author weixiaowei
 * @desc: 多流转换
 * @date: 2021/5/5
 */
public class Demo4Transform_RichMapFunction {
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
        // 2. 元组 实现自定义的 MapFunction
        DataStream<Tuple2<String,Integer>> resultStream01 = dataStream.map(new MyMapper01());

        // 3. 富函数 实现自定义 RichFunction
        DataStream<Tuple2<String,Integer>> resultStream02 = dataStream.map(new MyMapper02());

        // 打印输出
        resultStream01.print();
        resultStream02.print();

        // 执行起来
        environment.execute();
    }

    /**
     * 实现自定义的 MapFunction
     */
    public static class MyMapper01 implements MapFunction<SensorReading,Tuple2<String,Integer>> {
        @Override
        public Tuple2<String, Integer> map(SensorReading value) throws Exception {
            return new Tuple2<>(value.getSensorId(),value.getSensorId().length());
        }
    }

    /**
     * 实现自定义的 RichFunction
     */
    public static class MyMapper02 extends RichMapFunction<SensorReading,Tuple2<String,Integer>>{

        @Override
        public Tuple2<String, Integer> map(SensorReading value) throws Exception {
            return new Tuple2<>(value.getSensorId(),getRuntimeContext().getIndexOfThisSubtask());
        }

        /**
         * 很多rich 富函数方法 ...
         */
        @Override
        public void open(Configuration parameters) throws Exception {
            // 初始化工作，建立数据库连接、定义状态
            System.out.println("open(); ");
            super.open(parameters);
        }

        @Override
        public void close() throws Exception {
            // 一般是关闭连接、清空状态等收尾工作
            System.out.println("close();");
            super.close();
        }
    }
}
