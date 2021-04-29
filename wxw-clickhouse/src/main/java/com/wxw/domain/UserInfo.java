package com.wxw.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/4/27
 */
@Data
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String userName;
    private String passWord;
    private String phone;
    private String email;
    private String createDay;
}
