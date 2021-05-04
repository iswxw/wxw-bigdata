package com.wxw;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/5/2
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class FlinkApplicationTest {

    /**
     * DataSource 数据源
     */
    @Test
    public void test_args() {
        // 加载数据源的环境
        StreamExecutionEnvironment env1 = StreamExecutionEnvironment.getExecutionEnvironment();
        ExecutionEnvironment env2 = ExecutionEnvironment.getExecutionEnvironment();

        // 数据源
        env1.addSource(null);
    }

    /**
     * DataSink 数据存储 Collector
     */
    @Test
    public void test_sink() {
       // PrintSinkFunction
    }
}
