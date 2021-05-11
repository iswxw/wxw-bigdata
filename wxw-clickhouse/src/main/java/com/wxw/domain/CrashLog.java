package com.wxw.domain;

import lombok.Data;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/5/11
 */
@Data
public class CrashLog {
    private String userId;
    private String appKey;
    private String reportId;
    private String apmVersion;
    private String processName;
    private String reportType;
    private String crashTimeStamp;
    private String systemName;
    private String systemVersion;
    private String osVersion;
    private String brand;
    private String isRooted;
    private String executablePath;
    private String executableName;
    private String bundleId;
    private String bundleVersion;
    private String appUuid;
    private String cpuArch;
    private String deviceId;
    private String appLaunchTime;
    private String buildType;
    private String storage;
    private String memorySize;
    private String memoryUsed;
    private String memoryFree;
    private String crashError;
    private String crashName;
    private String moduleName;
    private String crashType;
    private String crashMessage;
    private String createTime;
    private String updateTime;
    private String threads;
}
