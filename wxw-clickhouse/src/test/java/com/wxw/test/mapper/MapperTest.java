package com.wxw.test.mapper;

import com.wxw.ClickHouseTest;
import com.wxw.dao.UserInfoMapper;
import com.wxw.domain.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/5/1
 */
@Slf4j
public class MapperTest extends ClickHouseTest {

    @Resource
    private UserInfoMapper userInfoMapper;

    /**
     * clickhouse添加操作
     */
    @Test
    public void test_save() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(100);
        userInfo.setUserName("小伟");
        userInfo.setAge(18);
        userInfo.setBirthday(new Date());
        userInfo.setCreateDate(new Date());
        userInfoMapper.saveData(userInfo);
    }

    /**
     * clickhouse 查询操作
     */
    @Test
    public void test_queryById() {
        UserInfo userInfo = userInfoMapper.selectById(100);
    }

    /**
     *  分页查询
     */


}
