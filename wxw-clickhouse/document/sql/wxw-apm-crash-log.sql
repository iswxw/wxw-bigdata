CREATE TABLE IF NOT EXISTS apm_crash_log
(
    userId              SimpleAggregateFunction(anyLast,String),
    appKey              SimpleAggregateFunction(anyLast,String),
    reportId            SimpleAggregateFunction(anyLast,String),
    apmVersion          SimpleAggregateFunction(anyLast,String),
    processName         SimpleAggregateFunction(anyLast,String),
    reportType          SimpleAggregateFunction(anyLast,String),
    crashTimeStamp      SimpleAggregateFunction(anyLast,UInt64),
    systemName          SimpleAggregateFunction(anyLast,String),
    systemVersion       SimpleAggregateFunction(anyLast,String),
    osVersion           SimpleAggregateFunction(anyLast,String),
    brand               SimpleAggregateFunction(anyLast,String),
    isRooted            SimpleAggregateFunction(anyLast,Enum('否'=0,'是'=1)),
    executablePath      SimpleAggregateFunction(anyLast,String),
    executableName      SimpleAggregateFunction(anyLast,String),
    bundleId            SimpleAggregateFunction(anyLast,String),
    bundleVersion       SimpleAggregateFunction(anyLast,String),
    appUuid             SimpleAggregateFunction(anyLast,String),
    cpuArch             SimpleAggregateFunction(anyLast,String),
    deviceId            SimpleAggregateFunction(anyLast,String),
    appLaunchTime       SimpleAggregateFunction(anyLast,UInt64),
    buildType           SimpleAggregateFunction(anyLast,String),
    storage             SimpleAggregateFunction(anyLast,UInt64),
    memorySize          SimpleAggregateFunction(anyLast,UInt64),
    memoryUsed          SimpleAggregateFunction(anyLast,UInt64),
    memoryFree          SimpleAggregateFunction(anyLast,UInt64),
    crashError          SimpleAggregateFunction(anyLast,String),
    crashName           SimpleAggregateFunction(anyLast,String),
    moduleName          SimpleAggregateFunction(anyLast,String),
    crashType           SimpleAggregateFunction(anyLast,String),
    crashMessage        SimpleAggregateFunction(anyLast,String),
    createTime          SimpleAggregateFunction(anyLast,DateTime),
    updateTime          SimpleAggregateFunction(anyLast,DateTime),
    threads             SimpleAggregateFunction(anyLast,Array(String))
)ENGINE = AggregatingMergeTree()
     PARTITION BY toYYYYMM(createTime)
     ORDER BY (reportId, createTime,crashTimeStamp,appLaunchTime)
