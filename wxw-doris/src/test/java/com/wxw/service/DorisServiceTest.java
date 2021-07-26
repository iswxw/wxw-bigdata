package com.wxw.service;

import com.wxw.DorisTest;
import com.wxw.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/7/26
 */
@Slf4j
public class DorisServiceTest extends DorisTest {

    @Resource
    private UserService userService;

    @Test
    public void test_saveOrUpdate() {
        // 查询数据
        User user1 = userService.getById(1);
        log.info("user1 = {}",user1);

        // 修改数据
        user1.setCity("天津");

        // 通过 unique 更新数据
        boolean b = userService.save(user1);
        log.info("b = {}",b); // 更新成功返回 true

        // 查询数据
        User user2 = userService.getById(1);
        log.info("user2 = {}",user2);
    }
}
