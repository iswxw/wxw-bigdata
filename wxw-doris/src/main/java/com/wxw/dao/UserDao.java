package com.wxw.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wxw.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/7/26
 */
@Mapper
public interface UserDao extends BaseMapper<User> {

}
