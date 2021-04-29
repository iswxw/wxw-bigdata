package com.wxw;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/4/27
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class ClickHouseTest {

    @Test
    public void test_succss() {
        // ClickHouseConnection
        log.info("测试没问题！！！");
    }
}
