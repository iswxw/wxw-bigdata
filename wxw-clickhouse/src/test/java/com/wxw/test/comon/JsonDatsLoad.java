package com.wxw.test.comon;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wxw.ClickHouseTest;
import com.wxw.common.helper.JsonHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/5/9
 */
@Slf4j
public class JsonDatsLoad extends ClickHouseTest {

    @Value("classpath:json/apm.json")
    private Resource apmJson;


    @Test
    public void test_apm() throws IOException {
        String apmStr = IOUtils.toString(apmJson.getInputStream(), StandardCharsets.UTF_8);
        JSONObject jsonObject = JSON.parseObject(apmStr);
        log.info("jsonObject = {}",jsonObject);
        JsonHelper.createJsonFile(apmStr,"src/test/resources/delete","newApm");
    }

}
