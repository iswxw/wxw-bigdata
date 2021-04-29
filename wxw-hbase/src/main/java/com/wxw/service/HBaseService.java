package com.wxw.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: wxw
 * @date: 2021-03-30-23:19
 * @link: https://www.zifangsky.cn/1286.html
 * @description: HBase 相关操作
 */
public class HBaseService {

    private Logger log = LoggerFactory.getLogger(HBaseService.class);

    /**
     * 声明静态配置
     */
    private Configuration conf = null;
    // HBase 客户端连接
    private Connection connection = null;

    // 初始化 HBase 配置
    public HBaseService(Configuration conf) {
        this.conf = conf;
        try {
            connection = ConnectionFactory.createConnection(conf);
        } catch (IOException e) {
            log.error("获取HBase连接失败");
        }
    }

    // 获取Table
    private Table getTable(String tableName) throws IOException {
        return connection.getTable(TableName.valueOf(tableName));
    }

    // 查询库中所有表的表名
    public List<String> getAllTableNames() {
        List<String> result = new ArrayList<>();
        Admin admin = null;
        try {
            admin = connection.getAdmin();
            TableName[] tableNames = admin.listTableNames();
            for(TableName tableName : tableNames){
                result.add(tableName.getNameAsString());
            }
        }catch (IOException e) {
            log.error("获取所有表的表名失败",e);
        }finally {
            close(admin,null,null);
        }
        return result;
    }


    /**
     * 根据startRowKey和stopRowKey遍历查询指定表中的所有数据
     * @param tableName 表名
     * @param startRowKey 起始rowKey
     * @param stopRowKey  结束rowKey
     * @return
     */
    public Map<String, Map<String,String>> getResultScanner(String tableName, String startRowKey, String stopRowKey){
        Scan scan = new Scan();
        if(StringUtils.isNoneBlank(startRowKey) && StringUtils.isNoneBlank(stopRowKey)){
            scan.withStartRow(Bytes.toBytes(startRowKey));
            scan.withStopRow(Bytes.toBytes(stopRowKey));
        }

        return this.queryData(tableName,scan);
    }

    /**
     * 通过表名以及过滤条件查询数据
     * @param tableName 表名
     * @param scan 过滤条件
     * @return java.util.Map<java.lang.String,java.util.Map<java.lang.String,java.lang.String>>
     */
    private Map<String,Map<String,String>> queryData(String tableName,Scan scan){
        //<rowKey,对应的行数据>
        Map<String,Map<String,String>> result = new HashMap<>();

        ResultScanner rs = null;
        // 获取表
        Table table= null;
        try {
            table = getTable(tableName);
            rs = table.getScanner(scan);
            for (Result r : rs) {
                //每一行数据
                Map<String,String> columnMap = new HashMap<>();
                String rowKey = null;
                for (Cell cell : r.listCells()) {
                    if(rowKey == null){
                        rowKey = Bytes.toString(cell.getRowArray(),cell.getRowOffset(),cell.getRowLength());
                    }
                    columnMap.put(Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength()), Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()));
                }

                if(rowKey != null){
                    result.put(rowKey,columnMap);
                }
            }
        }catch (IOException e) {
            log.error(MessageFormat.format("遍历查询指定表中的所有数据失败,tableName:{0}",tableName),e);
        }finally {
            close(null,rs,table);
        }

        return result;
    }

    /**
     * 根据tableName和rowKey精确查询一行的数据
     * @param tableName 表名
     * @param rowKey 行键
     * @return java.util.Map<java.lang.String,java.lang.String> 返回一行的数据
     */
    public Map<String,String> getRowData(String tableName, String rowKey){
        //返回的键值对
        Map<String,String> result = new HashMap<>();
        Get get = new Get(Bytes.toBytes(rowKey));
        // 获取表
        Table table= null;
        try {
            table = getTable(tableName);
            Result hTableResult = table.get(get);
            if (hTableResult != null && !hTableResult.isEmpty()) {
                for (Cell cell : hTableResult.listCells()) {
                    result.put(Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength()), Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()));
                }
            }
        }catch (IOException e) {
            log.error(MessageFormat.format("查询一行的数据失败,tableName:{0},rowKey:{1}"
                    ,tableName,rowKey),e);
        }finally {
            close(null,null,table);
        }
        return result;
    }

    // 关闭流
    private void close(Admin admin, ResultScanner rs, Table table){
        if(admin != null){
            try {
                admin.close();
            } catch (IOException e) {
                log.error("关闭Admin失败",e);
            }
        }

        if(rs != null){
            rs.close();
        }

        if(table != null){
            try {
                table.close();
            } catch (IOException e) {
                log.error("关闭Table失败",e);
            }
        }
    }
}
