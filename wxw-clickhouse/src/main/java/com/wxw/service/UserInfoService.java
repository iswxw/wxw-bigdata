package com.wxw.service;

import com.wxw.domain.UserInfo;

import java.util.List;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/4/27
 */
public interface UserInfoService{

    // 写入数据
    void saveData (UserInfo userInfo) ;

    // ID 查询
    UserInfo selectById (Integer id) ;

    // 查询全部
    List<UserInfo> selectList () ;
}
