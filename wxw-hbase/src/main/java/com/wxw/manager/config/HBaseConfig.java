package com.wxw.manager.config;

import com.wxw.service.HBaseService;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: wxw
 * @date: 2021-03-30-23:27
 * @link:
 * @description:
 */
@Configuration
public class HBaseConfig {

    @Value("${hbase.nodes:192.168.197.130}")
    private String nodes;

    @Value("${hbase.maxsize:500000}")
    private String maxsize;

    @Bean
    public HBaseService getHbaseService(){
        org.apache.hadoop.conf.Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum",nodes );
        conf.set("hbase.client.keyvalue.maxsize",maxsize);
        return new HBaseService(conf);
    }
}
