package com.wxw.study;

import com.wxw.manager.source.SourceFromMySQL;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author weixiaowei
 * @desc: 从 MySQL作为Source源 读取数据
 * @date: 2021/5/3
 */
public class StreamJobWithMySQL {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.addSource(new SourceFromMySQL()).print();

        env.execute("Flink add data sourc");
    }
}
