package com.wxw.test.hbase;


import com.wxw.HbaseTestBase;
import com.wxw.manager.client.ServiceHbaseClient;
import javafx.util.Pair;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/7/4
 */

public class TestHbase2Api extends HbaseTestBase {


    @Resource
    private ServiceHbaseClient hbaseClient;

    // ### Hbase表名
    public static String HBASE_TABLE_NAME = "test:bdg_hbase2_access_demo";

    @Test
    public void testHbaseGetRow() {
        Result result = hbaseClient.getRow(HBASE_TABLE_NAME, "1");
        for (Cell cell : result.listCells()) {
            System.out.println("qualifier:" + Bytes.toString(CellUtil.cloneQualifier(cell)));
            System.out.println("value:" + Bytes.toString(CellUtil.cloneValue(cell)));
            System.out.println("-------------finished------------------");
        }
    }

    @Test
    public void testHbaseMutilScan() {
        ResultScanner scanner = hbaseClient.mutilScan(HBASE_TABLE_NAME, "1", "3");
        if (scanner != null) {
            for (Iterator<Result> it = scanner.iterator(); it.hasNext(); ) {
                Result result = it.next();
                for (Cell cell : result.listCells()) {
                    System.out.println("qualifier:" + Bytes.toString(CellUtil.cloneQualifier(cell)));
                    System.out.println("value:" + Bytes.toString(CellUtil.cloneValue(cell)));
                }
                System.out.println("-------------finished------------------");
            }
        }
    }

    @Test
    public void testHbasePrefixScan() {
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
        filterList.addFilter(new PrefixFilter("1".getBytes()));
        ResultScanner scanner = hbaseClient.prefixScan(HBASE_TABLE_NAME, filterList);
        if (scanner != null) {
            for (Iterator<Result> it = scanner.iterator(); it.hasNext(); ) {
                Result result = it.next();
                for (Cell cell : result.listCells()) {
                    System.out.println("qualifier:" + Bytes.toString(CellUtil.cloneQualifier(cell)));
                    System.out.println("value:" + Bytes.toString(CellUtil.cloneValue(cell)));
                }
                System.out.println("-------------------------------");
            }
        }
    }

    @Test
    public void testPutRow() {
        hbaseClient.putRow(HBASE_TABLE_NAME, "4", "cf", "name", "Angelababy");
        hbaseClient.putRow(HBASE_TABLE_NAME, "4", "cf", "gender", "女");
        hbaseClient.putRow(HBASE_TABLE_NAME, "4", "cf", "birthday", "1989-02-28");
        System.out.println("-------------finished------------------");
    }

    @Test
    public void testPutRows() {
        List<Pair<String, String>> pairList = new ArrayList<>();

        // ### 第1组
        Pair<String, String> pair1 = new Pair<String, String>("name", "佟丽娅");
        Pair<String, String> pair2 = new Pair<String, String>("gender", "女");
        Pair<String, String> pair3 = new Pair<String, String>("birthday", "1983-08-08");
        pairList.add(pair1);
        pairList.add(pair2);
        pairList.add(pair3);
        hbaseClient.putRows(HBASE_TABLE_NAME, "5", "cf", pairList);

        // ### 第2组
        Pair<String, String> pair4 = new Pair<String, String>("name", "张敏");
        Pair<String, String> pair5 = new Pair<String, String>("gender", "女");
        Pair<String, String> pair6 = new Pair<String, String>("birthday", "1968-02-07");
        pairList.add(pair4);
        pairList.add(pair5);
        pairList.add(pair6);
        hbaseClient.putRows(HBASE_TABLE_NAME, "6", "cf", pairList);

        // ### 第3组
        Pair<String, String> pair7 = new Pair<String, String>("name", "范冰冰");
        Pair<String, String> pair8 = new Pair<String, String>("gender", "女");
        Pair<String, String> pair9 = new Pair<String, String>("birthday", "1981-09-16");
        pairList.add(pair7);
        pairList.add(pair8);
        pairList.add(pair9);
        hbaseClient.putRows(HBASE_TABLE_NAME, "7", "cf", pairList);

        // ### 第4组
        Pair<String, String> pair10 = new Pair<String, String>("name", "周杰");
        Pair<String, String> pair11 = new Pair<String, String>("gender", "男");
        Pair<String, String> pair12 = new Pair<String, String>("birthday", "1970-08-05");
        pairList.add(pair10);
        pairList.add(pair11);
        pairList.add(pair12);
        hbaseClient.putRows(HBASE_TABLE_NAME, "8", "cf", pairList);

        System.out.println("-------------finished------------------");
    }

    @Test
    public void testDeleteRow() {
        hbaseClient.deleteRow(HBASE_TABLE_NAME, "10");
        System.out.println("-------------finished------------------");
    }
}

