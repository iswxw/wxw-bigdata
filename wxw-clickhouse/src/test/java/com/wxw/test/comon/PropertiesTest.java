package com.wxw.test.comon;

import com.wxw.ClickHouseTest;
import com.wxw.manager.properties.JdbcProperties;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/4/27
 */
@Slf4j
public class PropertiesTest extends ClickHouseTest {

    @Resource
    private JdbcProperties jdbcProperties;

    @Test
    public void test_jdbc() {
        log.info("jdbcProperties = {}",jdbcProperties);
    }

}
