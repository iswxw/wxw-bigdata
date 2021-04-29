package com.wxw.manager.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.wxw.manager.properties.JdbcProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/4/27
 */
@Configuration
public class DruidConfig {

    @Resource
    private JdbcProperties jdbcProperties;

    @Bean
    public DataSource dataSource() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(jdbcProperties.getUrl());
        datasource.setDriverClassName(jdbcProperties.getDriverClassName());
        datasource.setInitialSize(jdbcProperties.getInitialSize());
        datasource.setMinIdle(jdbcProperties.getMinIdle());
        datasource.setMaxActive(jdbcProperties.getMaxActive());
        datasource.setMaxWait(jdbcProperties.getMaxWait());
        datasource.setUsername(jdbcProperties.getUserName());
        datasource.setPassword(jdbcProperties.getPassword());
        return datasource;
    }
}
