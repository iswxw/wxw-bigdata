package com.wxw.dao;

import com.wxw.DorisTest;
import com.wxw.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/7/26
 */
@Slf4j
public class DaoDorisTest extends DorisTest {

    @Resource
    private UserDao userDao;

    @Test
    public void test_query() {
        User user = userDao.selectById(1);
        log.info("user = {}", user);
    }

    @Test
    public void test_save() {
        User user = new User();
        user.setUserName("IN-北京");
        user.setAge((short) 16);
        user.setSex(true); // true == 1
        user.setCity("北京");
        user.setPhone(15822345546L);
        user.setAddress("北京市海淀区中关村");
        user.setRegisterTime(new Date());
        int insert = userDao.insert(user);
        // 插入成功返回 1
        log.info("insert = {}", insert);
    }
}
