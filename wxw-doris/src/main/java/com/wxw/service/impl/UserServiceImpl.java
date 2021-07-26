package com.wxw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxw.dao.UserDao;
import com.wxw.domain.User;
import com.wxw.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/7/26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

}
