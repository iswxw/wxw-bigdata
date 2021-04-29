package com.wxw.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wxw.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/4/27
 */
@Repository
public interface UserInfoMapper extends BaseMapper<UserInfo> {


    // 写入数据
    void saveData (UserInfo userInfo) ;

    // ID 查询
    UserInfo selectById (@Param("id") Integer id) ;

    // 查询全部
    List<UserInfo> selectList() ;

}
