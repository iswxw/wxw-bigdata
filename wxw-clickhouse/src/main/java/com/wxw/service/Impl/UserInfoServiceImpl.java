package com.wxw.service.Impl;

import com.wxw.domain.UserInfo;
import com.wxw.service.UserInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/5/1
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Override
    public void saveData(UserInfo userInfo) {

    }

    @Override
    public UserInfo selectById(Integer id) {
        return null;
    }

    @Override
    public List<UserInfo> selectList() {
        return null;
    }
}
