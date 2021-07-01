
-- 测试表
CREATE TABLE kenan.user_test(
    `log_id`        UInt64,
    `reg_date`      SimpleAggregateFunction(anyLast, DateTime),
    `remark`        SimpleAggregateFunction(anyLast,String),
    `handlerStatus` SimpleAggregateFunction(anyLast,UInt8) DEFAULT 0
)ENGINE = AggregatingMergeTree
 PARTITION BY toYYYYMM(reg_date)
 PRIMARY KEY log_id
 ORDER BY (log_id, reg_date)
 SETTINGS index_granularity = 8192