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



### Doris 设计原理

> 导读：http://doris.apache.org/master/zh-CN/internal/metadata-design.html

**名词解释**

- FE：Frontend，即 Doris 的前端节点。主要负责接收和返回客户端请求、元数据以及集群管理、查询计划生成等工作。
- BE：Backend，即 Doris 的后端节点。主要负责数据存储与管理、查询计划执行等工作。
- bdbje：[Oracle Berkeley DB Java Edition](http://www.oracle.com/technetwork/database/berkeleydb/overview/index-093405.html)。在 Doris 中，我们使用 bdbje 完成元数据操作日志的持久化、FE 高可用等功能。

<img src="asserts/palo_architecture.jpg" alt="img" style="zoom:50%;" /> 

如上图，Doris 的整体架构分为两层。多个 FE 组成第一层，提供 FE 的横向扩展和高可用。多个 BE 组成第二层，负责数据存储与管理。本文主要介绍 FE 这一层中，元数据的设计与实现方式。

1. FE 节点分为 follower 和 observer 两类。各个 FE 之间，通过 bdbje（[BerkeleyDB Java Edition](http://www.oracle.com/technetwork/database/database-technologies/berkeleydb/overview/index-093405.html)）进行 leader 选举，数据同步等工作。
2. follower 节点通过选举，其中一个 follower 成为 leader 节点，负责元数据的写入操作。当 leader 节点宕机后，其他 follower 节点会重新选举出一个 leader，保证服务的高可用。
3. observer 节点仅从 leader 节点进行元数据同步，不参与选举。可以横向扩展以提供元数据的读服务的扩展性。

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

