package com.wxw.domain;

import lombok.Data;

/**
 * @author weixiaowei
 * @desc: 传感器(sensor) 温度读数的数据类型
 * @date: 2021/5/4
 */
@Data
public class SensorReading {
    // 属性：id、时间戳、温度值
    private String sensorId;
    private Long timestamp;
    private Double temperature;

    public SensorReading() {
    }

    public SensorReading(String sensorId, Long timestamp, Double temperature) {
        this.sensorId = sensorId;
        this.timestamp = timestamp;
        this.temperature = temperature;
    }
}
