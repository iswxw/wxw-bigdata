package com.wxw.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/5/3
 */
@Setter
@Getter
@ToString
public class Student {

    public int id;
    public String name;
    public String password;
    public int age;

    public Student() {
    }

    public Student(int id, String name, String password, int age) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.age = age;
    }
}
