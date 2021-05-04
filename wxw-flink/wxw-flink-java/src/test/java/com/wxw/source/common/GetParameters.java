package com.wxw.source.common;

import org.apache.flink.api.java.utils.ParameterTool;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/5/2
 */
public class GetParameters {
    public static void main(String[] args) {
        // 用Parameter tool 工具从程序启动参数中获取配置项
        ParameterTool parameterTool = ParameterTool.fromArgs(args);
        // 需要在 argvment中配置启动参数
        String host = parameterTool.get("host");
        int port = parameterTool.getInt("port");
        System.out.println("port = " + port);
        System.out.println("host = " + host);
    }
}
