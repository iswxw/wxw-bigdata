package com.wxw.test;

import com.wxw.HbaseTestBase;
import com.wxw.manager.client.ServiceHbaseClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/7/2
 */
@Slf4j
public class TestHbaseAPI extends HbaseTestBase {

    @Resource
    private ServiceHbaseClient hbaseClient;


    @Test
    public void test_exist() {
        boolean tableExists = hbaseClient.tableExists("hbase:meta");
        log.info("tableExists = {}",tableExists);
    }

}
