package com.wxw.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/4/27
 */
@Data
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer userId;
    private String userName;
    private Integer age;
    private Date birthday;
    private Date createDate;
}
