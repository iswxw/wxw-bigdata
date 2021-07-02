package com.wxw.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/6/9
 */
@Data
@TableName("user_test")
public class UserTest {

    @TableId(type = IdType.AUTO)
    private Long LogId;
    private Date regDate;
    private String remark;
    private Integer handlerStatus;
}
