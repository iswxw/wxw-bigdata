package com.wxw.test.hbase;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.shaded.org.apache.commons.net.ntp.TimeStamp;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.Date;

/**
 * @author weixiaowei
 * @desc: hbase 测试连接
 * @date: 2021/7/3
 */
@Slf4j
public class HbaseConnectTest {
    private static TableName tableName = TableName.valueOf("test-wxw");
    private static final String cf = "ke";


    public static void main(String[] args) throws IOException {
        write2HBase("test");
    }

    public static void write2HBase(String value) throws IOException {
        Configuration config = HBaseConfiguration.create();

        config.set("hbase.zookeeper.quorum","localhost");
        config.set("hbase.zookeeper.property.clientPort","2181");

        System.out.println("开始连接hbase");
        Connection connect = ConnectionFactory.createConnection(config);
        System.out.println(connect.isClosed());
        Admin admin = connect.getAdmin();
        System.out.println("连接成功");
//        admin.listTableNames();
//        Table table = connect.getTable(TableName.valueOf("midas_ctr_test"));
        System.out.println("获取表数据成功");
//        for i :table.getScanner().iterator();


        if (!admin.tableExists(tableName)) {
            admin.createTable(new HTableDescriptor(tableName).addFamily(new HColumnDescriptor(cf)));
        }
        System.out.println("建表数据成功");

        Table table = connect.getTable(tableName);
        TimeStamp ts = new TimeStamp(new Date());
        Date date = ts.getDate();
        Put put = new Put(Bytes.toBytes(date.getTime()));
        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("test"), Bytes.toBytes(value));
        table.put(put);
        table.close();
        connect.close();
    }
}
