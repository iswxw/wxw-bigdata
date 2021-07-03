package com.wxw.manager.client;

import com.wxw.common.SRException;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/7/3
 */
@Slf4j
@Component
public class ServiceHbaseClient {

    @Value("${hbase.zookeeper.quorum:localhost}")
    private String quorum;

    @Value("${hbase.zookeeper.property.clientPort:2181}")
    private String port;

    @Value("${hbase.zookeeper.path:/hbase}")
    private String path;

    /**
     * 内部已实现线程安全的连接池
     */
    private Connection hbaseConnection;

    private Admin hBaseAdmin;

    /**
     * 判断表名是否存在
     *
     * @param tableName 表名 String ,注意这里区分大小写
     * @return
     */
    public boolean tableExists(String tableName) {
        Table table = null;
        boolean tableExistsFlag = false;
        try {
            table = hbaseConnection.getTable(TableName.valueOf(tableName));
            tableExistsFlag = hBaseAdmin.tableExists(table.getName());
        } catch (IOException e) {
            log.error("IOException : {}", e.getMessage());
            e.printStackTrace();
        } finally {
            closeTable(table);
        }
        return tableExistsFlag;
    }


    /**
     * 关闭连接
     *
     * @param table 表名
     */
    private void closeTable(Table table) {
        if (table != null) {
            try {
                table.close();
            } catch (IOException e) {
                log.error("close table {} error {}", table.getName(), e.getMessage());
                e.printStackTrace();
            }
        } else {
            log.info("table is null");
        }
    }


    @PostConstruct
    private void initHbase(){
        try {
            org.apache.hadoop.conf.Configuration config = HBaseConfiguration.create();
            config.set(HConstants.ZOOKEEPER_QUORUM, quorum);
            config.set(HConstants.ZOOKEEPER_ZNODE_PARENT,path);
            config.set(HConstants.ZOOKEEPER_CLIENT_PORT,port);
            hbaseConnection = ConnectionFactory.createConnection(config);
            hBaseAdmin = hbaseConnection.getAdmin();
        } catch (IOException e) {
            throw new SRException("获取HBase连接失败",e);
        }
    }


}
