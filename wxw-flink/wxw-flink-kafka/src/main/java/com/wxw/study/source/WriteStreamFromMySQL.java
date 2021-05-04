package com.wxw.study.source;

import com.wxw.manager.source.SourceFromMySQL;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author weixiaowei
 * @desc: 从 MySQL作为Source源 读取数据
 * @date: 2021/5/3
 */
public class WriteStreamFromMySQL {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.addSource(new SourceFromMySQL()).print();
        env.execute("Flink add data sourc");
    }
}
