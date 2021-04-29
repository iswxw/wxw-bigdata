package com.wxw;

import com.wxw.service.HBaseService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

/**
 * @author: wxw
 * @date: 2021-03-30-23:30
 * @link:
 * @description:
 */
@SpringBootTest
public class hbaseTest {

    @Autowired
    private HBaseService hbaseService;


    /**
     * 测试获取所有表名
     */
    @Test
    public void testGetTableNameLists(){
        List<String> result = hbaseService.getAllTableNames();
        result.forEach(System.out::println);
    }
}
