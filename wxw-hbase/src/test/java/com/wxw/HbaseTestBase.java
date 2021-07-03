package com.wxw;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

/**
 * @author: wxw
 * @date: 2021-03-30-23:30
 * @link:
 * @description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class HbaseTestBase {

    public static StopWatch watch = new StopWatch();

    @Test
    public void test_task() {
        System.out.println("单元测试 任务");
    }

    @Before
    public void test_before() {
        watch.start("开始计时...");
    }

    @After
    public void test_after() {
        watch.stop();
        System.out.println("总耗时 = " + watch.getTotalTimeSeconds() + "s");
        System.out.println("总耗时 = " + watch.getTotalTimeMillis() + "ms");
    }

}
