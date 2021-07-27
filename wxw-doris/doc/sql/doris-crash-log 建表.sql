
-- 使用库
use kenan;

-- 创建crash_log表
create table t_crash_log(
    crash_id            varchar(200)  null comment 'crashID',
    user_id             varchar(200)  null comment '用户ID',
    app_key             varchar(200)  null comment '客户端Key',
    app_value           varchar(200)  null comment '客户端Value',
    report_id           varchar(200)  null comment '上报ID',
    report_type         varchar(50)   null comment '上报类型',
    apm_version         varchar(100)  null comment '上报ID',
    crash_time_stamp    datetime           comment '发生奔溃时间',
    system_name         varchar(100)  null comment '系统名称',
    system_version      varchar(100)  null comment '系统版本',
    os_version          varchar(100)  null comment 'IOS 版本',
    brand               varchar(100)  null comment '商品名称',
    is_rooted           tinyint            comment '是否越狱(否= 0,是= 1)',
    bundle_id           varchar(100)  null comment '包 ID',
    bundle_version      varchar(100)  null comment '包版本',
    cpu_arch            varchar(50)   null comment 'CPU架构类型',
    app_launch_time     datetime      null comment 'APP 启动时间',
    memory_size         LARGEINT           comment '总内存大小',
    memory_free         LARGEINT           comment '空闲内存大小',
    crash_name          varchar(100)  null comment '崩溃名称',
    crash_message       varchar(300)  null comment '崩溃原因简述',
    module_name         varchar(100)  null comment '模块名称',
    handler_status      int           null comment '处理状态',
    create_time         datetime      null comment '创建记录时间',
    threads             varchar(4096) null comment '线程信息',
    binary_images       varchar(4096) null comment '镜像库信息',
    original_error_info varchar(4096) null comment '解析后原错误信息',
    process_duration    LARGEINT      null comment '处理耗时',
    log_upload_time     datetime      null comment 'crash上报时间',
    cpu_percent         varchar(20)   null comment 'CPU 占比',
    network             varchar(100)  null comment '网络类型',
    view_hierarchy      varchar(500)  null comment '视图层级',
    page_v_c            varchar(500)  null comment '页面视图控制器',
    page_trace          varchar(500)  null comment '页面跟踪',
    thread_state        varchar(500)  null comment '线程状态',
    tag_status          int           null comment 'crash类型'
) ENGINE=OLAP
UNIQUE KEY(`crash_id`,`report_id`, `crash_name`,`crash_message`,`create_time`)
comment "崩溃上报信息表"
partition by range(`create_time`)()
distributed by hash(`crash_id`) buckets 16
properties(
    "dynamic_partition.time_unit" = "MONTH",
    "dynamic_partition.time_zone" = "Asia/Shanghai",
    "dynamic_partition.start" = "-6",
    "dynamic_partition.end" = "6",
    "dynamic_partition.prefix" = "p",
    "dynamic_partition.buckets" = "16" 
);


--- 用 show create table t_crash_log 查看创建sql

CREATE TABLE `t_crash_log 查看` (
  `crash_id` varchar(200) NULL COMMENT "crashID",
  `report_id` varchar(200) NULL COMMENT "上报ID",
  `crash_name` varchar(100) NULL COMMENT "崩溃名称",
  `crash_message` varchar(300) NULL COMMENT "崩溃原因简述",
  `create_time` datetime NULL COMMENT "创建记录时间",
  `user_id` varchar(200) NULL COMMENT "用户ID",
  `app_key` varchar(200) NULL COMMENT "客户端Key",
  `app_value` varchar(200) NULL COMMENT "客户端Value",
  `report_type` varchar(50) NULL COMMENT "上报类型",
  `apm_version` varchar(100) NULL COMMENT "上报ID",
  `crash_time_stamp` datetime NULL COMMENT "发生奔溃时间",
  `system_name` varchar(100) NULL COMMENT "系统名称",
  `system_version` varchar(100) NULL COMMENT "系统版本",
  `os_version` varchar(100) NULL COMMENT "IOS 版本",
  `brand` varchar(100) NULL COMMENT "商品名称",
  `is_rooted` tinyint(4) NULL COMMENT "是否越狱(否= 0,是= 1)",
  `bundle_id` varchar(100) NULL COMMENT "包 ID",
  `bundle_version` varchar(100) NULL COMMENT "包版本",
  `cpu_arch` varchar(50) NULL COMMENT "CPU架构类型",
  `app_launch_time` datetime NULL COMMENT "APP 启动时间",
  `memory_size` largeint(40) NULL COMMENT "总内存大小",
  `memory_free` largeint(40) NULL COMMENT "空闲内存大小",
  `module_name` varchar(100) NULL COMMENT "模块名称",
  `handler_status` int(11) NULL COMMENT "处理状态",
  `threads` varchar(4096) NULL COMMENT "线程信息",
  `binary_images` varchar(4096) NULL COMMENT "镜像库信息",
  `original_error_info` varchar(4096) NULL COMMENT "解析后原错误信息",
  `process_duration` largeint(40) NULL COMMENT "处理耗时",
  `log_upload_time` datetime NULL COMMENT "crash上报时间",
  `cpu_percent` varchar(20) NULL COMMENT "CPU 占比",
  `network` varchar(100) NULL COMMENT "网络类型",
  `view_hierarchy` varchar(500) NULL COMMENT "视图层级",
  `page_v_c` varchar(500) NULL COMMENT "页面视图控制器",
  `page_trace` varchar(500) NULL COMMENT "页面跟踪",
  `thread_state` varchar(500) NULL COMMENT "线程状态",
  `tag_status` int(11) NULL COMMENT "crash类型"
) ENGINE=OLAP
UNIQUE KEY(`crash_id`, `report_id`, `crash_name`, `crash_message`, `create_time`)
COMMENT "崩溃上报信息表"
PARTITION BY RANGE(`create_time`)
()
DISTRIBUTED BY HASH(`crash_id`) BUCKETS 16
PROPERTIES (
"replication_num" = "3",
"dynamic_partition.enable" = "true",
"dynamic_partition.time_unit" = "MONTH",
"dynamic_partition.time_zone" = "Asia/Shanghai",
"dynamic_partition.start" = "-6",
"dynamic_partition.end" = "6",
"dynamic_partition.prefix" = "p",
"dynamic_partition.replication_num" = "3",
"dynamic_partition.buckets" = "16",
"dynamic_partition.start_day_of_month" = "1",
"in_memory" = "false",
"storage_format" = "V2"
);



















