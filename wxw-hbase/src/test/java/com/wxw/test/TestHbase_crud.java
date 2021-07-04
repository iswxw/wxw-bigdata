package com.wxw.test;

import com.wxw.HbaseTestBase;
import com.wxw.common.Constant;
import com.wxw.manager.client.ServiceHbaseClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.client.Result;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/7/2
 */
@Slf4j
public class TestHbase_crud extends HbaseTestBase {

    @Resource
    private ServiceHbaseClient hbaseClient;

    @Test
    public void test_get_row() {
        Result row = hbaseClient.getRow(Constant.HbaseTable.WXW_TEST, "1");
        log.info("row = {}",row.toString());
    }

    @Test
    public void test_exist() {
        boolean tableExists = hbaseClient.tableExists("test:wxw-test");
        log.info("tableExists = {}",tableExists);
    }

    @Test
    public void test_create() throws IOException {
        hbaseClient.create(Constant.HbaseTable.WXW_TEST,"cf");
    }

}
