package com.wxw.common;

import com.wxw.HbaseTestBase;
import com.wxw.common.helper.AssertHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/7/3
 */
@Slf4j
public class ParamsTest extends HbaseTestBase {

    @Test
    public void test_assert() {
        ArrayList<String> objects = new ArrayList<>();
        AssertHelper.notNullValues("test", objects);
    }
}
