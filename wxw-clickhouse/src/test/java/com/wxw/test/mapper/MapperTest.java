package com.wxw.test.mapper;

import com.wxw.ClickHouseTest;
import com.wxw.dao.UserInfoMapper;
import com.wxw.domain.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/5/1
 */
@Slf4j
public class MapperTest extends ClickHouseTest {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Test
    public void test_save() {
        List<UserInfo> userInfos = userInfoMapper.selectList();
        log.info("userInfos = {}",userInfos);
    }
}
