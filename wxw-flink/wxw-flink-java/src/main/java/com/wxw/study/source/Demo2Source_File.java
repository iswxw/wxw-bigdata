package com.wxw.study.source;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author weixiaowei
 * @desc: 从文件中读取数据
 * @date: 2021/5/4
 */
public class Demo2Source_File {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();

        //1. source 从文件中读取数据 方式一
        String filePath = "wxw-flink/wxw-flink-java/src/main/resources/file/sensor.txt";
        DataStream<String> dataStream = environment.readTextFile(filePath);

        //打印到控制台 currentStreamName 设置并行度
        dataStream.print("readTextFile").setParallelism(1);

        // 执行
        environment.execute("JobName");
    }
}
