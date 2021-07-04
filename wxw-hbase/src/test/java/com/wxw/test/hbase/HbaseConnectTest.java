package com.wxw.test.hbase;

import com.wxw.common.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.ServerName;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author weixiaowei
 * @desc: hbase 测试连接
 * @date: 2021/7/3
 */
@Slf4j
public class HbaseConnectTest {
    private static TableName tableName = TableName.valueOf(Constant.HbaseTable.WXW_TEST);
    private static final String cf = "cf";


    public static void main(String[] args) throws IOException {
        write2HBase("test");
    }

    public static void write2HBase(String value) throws IOException {
        Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum","wxw-hbase");
        config.set("hbase.zookeeper.property.clientPort","2181");
        System.out.println("开始连接hbase");
        Connection connect = ConnectionFactory.createConnection(config);
        System.out.println(connect.isClosed());
        Admin admin = connect.getAdmin();
        Table table = connect.getTable(TableName.valueOf(Constant.HbaseTable.WXW_TEST));
        Get get = new Get(Bytes.toBytes("1"));
        System.out.println("连接成功"+ table.get(get));
        Collection<ServerName> regionServers = admin.getRegionServers();
        System.out.println("regionServers = " + regionServers);
        TableName[] tableNames = admin.listTableNames();
        System.out.println("tableNames = " + Arrays.asList(tableNames));

//        Table table = connect.getTable(TableName.valueOf("midas_ctr_test"));
        System.out.println("获取表数据成功");
//        for i :table.getScanner().iterator();

//        if (!admin.tableExists(tableName)) {
//            admin.createTable(new HTableDescriptor(tableName).addFamily(new HColumnDescriptor(cf)));
//        }

        table.close();
        connect.close();
    }
}
