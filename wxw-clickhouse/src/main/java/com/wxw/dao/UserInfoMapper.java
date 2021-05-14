package com.wxw.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wxw.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/4/27
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    /**
     * 写入数据
     * @param userInfo
     */
    void saveData (UserInfo userInfo) ;

    /**
     * ID 查询
     * @param userId
     * @return
     */
    UserInfo selectById (@Param("userId") Integer userId) ;

    /**
     * 查询全部
     * @return
     */
    List<UserInfo> selectList() ;

}
