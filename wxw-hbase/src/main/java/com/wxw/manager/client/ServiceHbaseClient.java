package com.wxw.manager.client;

import com.wxw.common.Constant;
import com.wxw.common.exception.ExceptionMessage;
import com.wxw.common.exception.RRException;
import com.wxw.common.helper.AssertHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.util.Bytes;
import javafx.util.Pair;
import org.apache.hadoop.security.UserGroupInformation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.security.PrivilegedExceptionAction;
import java.util.List;

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
        AssertHelper.notNullValues(tableName);
        boolean tableExistsFlag = false;
        try {
            tableExistsFlag = hBaseAdmin.tableExists(TableName.valueOf(tableName));
        } catch (IOException e) {
            log.error("IOException : {}", e.getMessage());
            e.printStackTrace();
        }
        return tableExistsFlag;
    }

    /**
     * getRow - [通过RowKey进行访问]
     *
     * @param tableName 表名
     * @param rowKey    主键
     * @return
     */
    public Result getRow(String tableName, String rowKey) {
        try {
            Table table = hbaseConnection.getTable(TableName.valueOf(tableName));
            Get get = new Get(Bytes.toBytes(rowKey));
            return table.get(get);
        } catch (IOException e) {
            log.error("query error, message:", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    /**
     * mutilScan - [多行扫描]
     *
     * @param tableName 表名
     * @param startKey  开始Key
     * @param endKey    结束Key
     * @return
     */
    public ResultScanner mutilScan(String tableName, String startKey, String endKey) {
        Scan scan = new Scan();
        scan.withStartRow(Bytes.toBytes(startKey));
        scan.withStopRow(Bytes.toBytes(endKey));
        return queryByScan(tableName, scan);
    }

    /**
     * prefixScan - [过滤扫描]
     *
     * @param tableName  表名
     * @param filterList 过滤列表
     * @return
     */
    public ResultScanner prefixScan(String tableName, FilterList filterList) {
        Scan scan = new Scan();
        scan.setFilter(filterList);
        return queryByScan(tableName, scan);
    }


    /**
     * 通过scan查询数据
     *
     * @param tableName 表名
     * @param scan      scan
     * @return 返回 ResultScanner
     */
    public ResultScanner queryByScan(String tableName, Scan scan) {
        AssertHelper.notNullValues(tableName, scan);
        ResultScanner resultScanner = null;
        Table table = null;
        try {
            table = hbaseConnection.getTable(TableName.valueOf(tableName));
            resultScanner = table.getScanner(scan);
        } catch (IOException e) {
            log.error("query error, message:", e.getMessage());
            e.printStackTrace();
        } finally {
            closeTable(table);
        }
        return resultScanner;
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
            log.info("table is not exists");
        }
    }

     /**
     * putRows - [批量插入数据]
     *
     * @param tableName        表名
     * @param rowKey           唯一标识
     * @param columnFamilyName 列族名
     * @param pairList         列标识和值的集合
     */
     public void putRows(String tableName, String rowKey, String columnFamilyName, List<Pair<String, String>> pairList) {
        AssertHelper.notNullValues(tableName,rowKey,columnFamilyName,pairList);
        Table table = null;
        if (tableExists(tableName)) {
            try {
                table = hbaseConnection.getTable(TableName.valueOf(tableName));
                Put put = new Put(Bytes.toBytes(rowKey));
                pairList.forEach(pair -> put.addColumn(Bytes.toBytes(columnFamilyName), Bytes.toBytes(pair.getKey()), Bytes.toBytes(pair.getValue())));
                table.put(put);
            } catch (IOException e) {
                log.error("data put error, message:{}", e.getMessage());
                e.printStackTrace();
            } finally {
                closeTable(table);
            }
        } else {
            throw new RRException(ExceptionMessage.TABLE_NOT_EXISTS_MSG);
        }
    }

    /**
     * putRow - [插入数据]
     *
     * @param tableName        表名
     * @param rowKey           唯一标识
     * @param columnFamilyName 列族名
     * @param qualifier        列标识
     * @param value            数据
     */
    public boolean putRow(String tableName, String rowKey, String columnFamilyName, String qualifier,String value) {
        Table table = null;
        try {
            table = hbaseConnection.getTable(TableName.valueOf(tableName));
            Put put = new Put(Bytes.toBytes(rowKey));
            put.addColumn(Bytes.toBytes(columnFamilyName), Bytes.toBytes(qualifier), Bytes.toBytes(value));
            table.put(put);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            closeTable(table);
        }
        return true;
    }


    /**
     * deleteRow - [删除指定行记录]
     *
     * @param tableName 表名
     * @param rowKey    唯一标识
     */
    public boolean deleteRow(String tableName, String rowKey) {
        AssertHelper.notNullValues(tableName,rowKey);
        Table table = null;
        try {
            table = hbaseConnection.getTable(TableName.valueOf(tableName));
            Delete delete = new Delete(Bytes.toBytes(rowKey));
            table.delete(delete);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            closeTable(table);
        }
        return true;
    }

    /**
     * 创建一张表
     *
     * @param tableName  表名
     * @param columnFamilies 列族名
     */
    public void create(String tableName, String... columnFamilies) throws IOException {
        AssertHelper.notNullValues(tableName,columnFamilies);
        TableName name = TableName.valueOf(tableName);
        if (hBaseAdmin.tableExists(name)) {
            log.info("table {} already exists",tableName);
            return;
        }
        TableDescriptorBuilder builder = TableDescriptorBuilder.newBuilder(name);
        for (String cf : columnFamilies) {
            builder.setColumnFamily(ColumnFamilyDescriptorBuilder.of(cf));
        }
        hBaseAdmin.createTable(builder.build());
        log.info("create table {} success",tableName);
    }


    /**
     * 初始化
     */
    @PostConstruct
    private void initHbase(){
        try {
            org.apache.hadoop.conf.Configuration config = HBaseConfiguration.create();
            config.set(HConstants.ZOOKEEPER_QUORUM, quorum);
            config.set(HConstants.ZOOKEEPER_ZNODE_PARENT,path);
            config.set(HConstants.ZOOKEEPER_CLIENT_PORT,port);

            // hbaseConnection = ConnectionFactory.createConnection(config);
            // ### 需要创建绑定的访问用户
            UserGroupInformation ugi = UserGroupInformation.createRemoteUser(Constant.HbaseTable.NAME_SPACE_TEST);
            hbaseConnection = ugi.doAs((PrivilegedExceptionAction<Connection>) ()
                    -> ConnectionFactory.createConnection(config));
            hBaseAdmin = hbaseConnection.getAdmin();
        } catch (IOException | InterruptedException e) {
            throw new RRException("获取HBase连接失败",e);
        }
    }
}
