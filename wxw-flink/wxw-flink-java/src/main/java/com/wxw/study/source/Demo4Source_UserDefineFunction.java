package com.wxw.study.source;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author weixiaowei
 * @desc: 用户自定义source
 * @date: 2021/5/4
 */
public class Demo4Source_UserDefineFunction {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        // 全局设置并行度
        // environment.setParallelism(1);

        //1. source 从自定义数据源中读取数据
        DataStreamSource<SensorReading> dataStreamSource = environment.addSource(new MySensorSource());

        //打印到控制台 currentStreamName 设置并行度
        dataStreamSource.print("UserDefineFunction");

        // 执行
        environment.execute("JobName");
    }

    /**
     * 实现自定义 SourceFunction
     */
    private static class MySensorSource implements SourceFunction<SensorReading> {

        // 定义一个标识位，用来控制数据的产生
        private boolean running = true;

        @Override
        public void run(SourceContext<SensorReading> sourceContext) throws Exception {
            // 定义一个随机数发生器
            Random random = new Random();
            // 设置10个传感器初识温度
            Map<String, Double> sensorMap = new HashMap<>();
            for (int i = 0; i < 10; i++) {
                sensorMap.put("sensor_" + (i + 1), 60 + random.nextGaussian() * 20);
            }
            while (running) {
                for (String sensorId : sensorMap.keySet()) {
                    // 在当前温度基础上随机波动
                    Double newTemp = sensorMap.get(sensorId) + random.nextGaussian();
                    sensorMap.put(sensorId, newTemp);
                    sourceContext.collect(new SensorReading(sensorId, System.currentTimeMillis(), newTemp));
                }
                // 控制输出的频率 s/per
                Thread.sleep(1000L);
            }
        }

        @Override
        public void cancel() {
            running = false;
        }
    }
}
