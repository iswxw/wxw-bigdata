package com.wxw.manager.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/4/27
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.datasource.click")
public class JdbcProperties {

    private String driverClassName ;
    private String url ;
    private String userName;
    private String password;
    private Integer initialSize ;
    private Integer maxActive ;
    private Integer minIdle ;
    private Integer maxWait ;
}
