package com.wxw.common.helper;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/4/27
 */
@Slf4j
public class CodeGenerator {

    // 基础数据微服务 指定生成路径
    public static String generatorPath = "/wxw-clickhouse/src/main/java";

    /**
     * 读取控制台内容
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {

        // 1,全局配置
        GlobalConfig gc = new GlobalConfig();
        //获取程序当前路径  F:\Project\code\Server\wxw-tools
        String projectPath = System.getProperty("user.dir");
        // log.info("路径：RemoteCodeGenerator==>程序当前路径:{}",projectPath);
        gc.setOutputDir(projectPath + generatorPath)
                .setAuthor("weixiaowei@baojia.com") //作者
                .setEnableCache(false)
                .setFileOverride(true)
                .setOpen(false)
                .setIdType(IdType.NONE)
                .setBaseResultMap(true)
                .setDateType(DateType.TIME_PACK)
                .setBaseColumnList(true);

        // 2，数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL)
                .setDriverName("")
                .setUrl("")
                .setUsername("")
                .setPassword("")
                // 自定义数据库表字段类型转换【可选】
                .setTypeConvert(new MySqlTypeConvert() {
                    @Override
                    public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                        log.info("转换类型对应的表字段：{}", fieldType);
                        //tinyint转换成Boolean
                        if (fieldType.toLowerCase().contains("tinyint")) {
                            return DbColumnType.BOOLEAN;
                        }
                        //将数据库中datetime转换成date
                        if (fieldType.contains("datetime")) {
                            return DbColumnType.DATE;
                        }
                        // 将数据库中timestamp 转换成date
                        if (fieldType.contains("Timestamp")) {
                            return DbColumnType.DATE;
                        }
                        //其它字段采用默认转换（非mysql数据库可以使用其它默认的数据库转换器）
                        return new MySqlTypeConvert().processTypeConvert(globalConfig, fieldType);
                    }
                });

        // 3,策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setCapitalMode(false)
                .setColumnNaming(NamingStrategy.no_change)
                .setNaming(NamingStrategy.underline_to_camel)
                .setRestControllerStyle(false)
                .setSkipView(false)
                .setEntityLombokModel(true)
                .setTablePrefix("t_")
                .setEntityTableFieldAnnotationEnable(false) // 是否生成实体时，生成字段注解
                .setControllerMappingHyphenStyle(false)
                .setInclude(scanner("表名，多个英文逗号分割").split(","));

        // 4，包名策略配置  注意不同的模块生成时要修改对应模块包名
        PackageConfig pc = new PackageConfig();
        pc.setParent(null)
                .setEntity("com.wxw.domain")
                .setController("com.wxw.controller")
                .setMapper("com.wxw.dao")
                .setXml("mapper")
                .setService("com.wxw.service")
                .setServiceImpl("com.wxw.service.impl");


        // 5,整合配置
        AutoGenerator mbg = new AutoGenerator();
        mbg.setGlobalConfig(gc)
                .setDataSource(dsc)
                .setStrategy(strategy)
                .setPackageInfo(pc)
                .setTemplate(null);

        // 6，执行
        mbg.execute();
        log.info("===========RemoteCodeGenerator 日志信息 Game Over===========");
    }
}
