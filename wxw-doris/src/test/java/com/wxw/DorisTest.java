package com.wxw;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/7/26
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DorisTest {

    private static StopWatch watch = new StopWatch();

    @Before
    public void test_before() {
        watch.start("测试任务开始...");
    }

    @Test
    public void test_after() {
       watch.stop();
       System.err.println("总耗时：" + watch.getTotalTimeMillis() + " ms");
       System.err.println("总耗时：" + watch.getTotalTimeSeconds() + " s");
    }

}
