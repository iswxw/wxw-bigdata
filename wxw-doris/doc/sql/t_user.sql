
-- 用户表
-- 相关信息：https://cloud.baidu.com/doc/DORIS/s/rkmealnj7
CREATE TABLE `t_user`(
    `user_id` largeint(40) NOT NULL COMMENT "用户id",
    `user_name`     varchar(50)  NOT NULL COMMENT "用户昵称",
    `city`          varchar(20)  NULL COMMENT "用户所在城市",
    `age`           smallint(6)  NULL COMMENT "用户年龄",
    `sex`           tinyint(4)   NULL COMMENT "用户性别",
    `phone` largeint(40) NULL COMMENT "用户电话",
    `address`       varchar(500) NULL COMMENT "用户地址",
    `register_time` datetime     NULL COMMENT "用户注册时间"
) ENGINE = OLAP UNIQUE KEY(`user_id`, `user_name`) COMMENT "OLAP"
DISTRIBUTED BY HASH(`user_id`) BUCKETS 17
PROPERTIES (
"replication_num" = "3",
"in_memory" = "false",
"storage_format" = "V2"
);