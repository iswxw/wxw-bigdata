package com.wxw.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/7/26
 */
@Data
@TableName(value = "t_user")
public class User {

    @TableId(type = IdType.ASSIGN_ID)
    private Long userId;
    private String userName;
    private String city;
    private short age;
    private boolean sex;
    private Long phone;
    private String address;
    private Date registerTime;
}
