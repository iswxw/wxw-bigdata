## wxw-doris



## Doris 基础

Doris 是一个基于 MPP 的交互式 SQL 数据仓库，用于报告和分析。原名Palo，由百度开发。捐赠给 Apache 软件基金会后，它更名为 Doris。

- 百度官方文档：https://cloud.baidu.com/doc/DORIS/index.html
- Apache官方文档：http://doris.apache.org/master/zh-CN/

### Doris初识

#### 1.DDL

```sql
## 查看建表语句
show  create table [table_name]

-- 输出如下
CREATE TABLE `t_user`(
    `user_id` largeint(40) NOT NULL COMMENT "用户id",
    `user_name`     varchar(50)  NOT NULL COMMENT "用户昵称",
    `city`          varchar(20)  NULL COMMENT "用户所在城市",
    `age`           smallint(6)  NULL COMMENT "用户年龄",
    `sex`           tinyint(4)   NULL COMMENT "用户性别",
    `phone` largeint(40) NULL COMMENT "用户电话",
    `address`       varchar(500) NULL COMMENT "用户地址",
    `register_time` datetime     NULL COMMENT "用户注册时间"
) ENGINE = OLAP UNIQUE KEY(`user_id`, `user_name`) ；

## 查看某个数据库下的表
SHOW TABLES;

## 查看表的属性信息
desc [table_name]

-- 比如如下所示：
Field        |Type        |Null|Key  |Default|Extra  |
-------------+------------+----+-----+-------+-------+
user_id      |LARGEINT    |No  |true |       |       |
user_name    |VARCHAR(50) |No  |true |       |       |
city         |VARCHAR(20) |Yes |false|       |REPLACE|
age          |SMALLINT    |Yes |false|       |REPLACE|
sex          |TINYINT     |Yes |false|       |REPLACE|
phone        |LARGEINT    |Yes |false|       |REPLACE|
address      |VARCHAR(500)|Yes |false|       |REPLACE|
register_time|DATETIME    |Yes |false|       |REPLACE|
```



### Doris 核心知识

#### 1. Doris 分区

> Doris支持支持 **单分区**和 **复合分**区两种建表方式





## Doris 环境

#### 1. docker 搭建doris环境

```bash
# 拉取镜像 构建doris环境
docker pull apachedoris/doris-dev:build-env-1.3

# 检查镜像是否存在
$ docker images

# 运行镜像
$ docker run -it apachedoris/doris-dev:build-env-1.3
```

## Doris实战

