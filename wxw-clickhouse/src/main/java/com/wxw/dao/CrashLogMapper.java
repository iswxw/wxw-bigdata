package com.wxw.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wxw.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/5/11
 */
@Mapper
public interface CrashLogMapper extends BaseMapper<UserInfo> {

}
