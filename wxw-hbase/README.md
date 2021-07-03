## wxw-hbase

如果这是您第一次涉足分布式计算的奇妙世界，那么您将迎来一段有趣的时光。首先，分布式系统很难。使分布式系统嗡嗡作响需要跨越系统（硬件和软件）和网络的不同技能组合。

> 导读

- 官网学习：http://hbase.apache.org/book.html

## Hbase 基础

###  hbase快速入门

#### 1.1 环境准备

##### 1.1.1 普通安装hbase

- 安装教程：http://hbase.apache.org/book.html
- 下载地址：https://mirrors.tuna.tsinghua.edu.cn/apache/hbase/

##### 1.1.2 Docker 安装hbase

- 下载安装hbase镜像

  ```bash
  ##  查找Hbase
  docker search hbase 
  
  ## 拉取制定版本的镜像
  docker pull harisekhon/hbase:1.3
  ```

  注意：不要安装最新版本的，不稳定 (我安装的是1.3)

- 运行Hbase(运行时指定主机名，端口映射等)

  ```bash
  # 大写p,主机随机分配端口与宿主机上的端口进行映射
  docker run -d --name wxw-hbase -P harisekhon/hbase:1.3
  
  ## 【常用】小写P指定主机的端口 16010映射到宿主机上(容器)的开放端口 16010（[服务器(宿主机)开放端口]:[docker服务端口]）
  docker run -d --name wxw-hbase -p 16010:16010 harisekhon/hbase:1.3
  
  ## -d 后台运行 -h 守护进程连接的host  
  docker run -d -h wxw-hbase -p 2181:2181 -p 8085:8085 -p 9090:9090 -p 9095:9095 -p 16000:16000 -p 16010:16010 -p 16201:16201 -p 16301:16301 --name hbase harisekhon/hbase:1.3
  ```

  注意：hbase60010端口无法访问web页面，web端访问的接口变更为16010

  如果是选择 使用` -h ` 绑定host ip 则需要修改本地house

  - 修改虚拟机 `etc/hosts`  文件

    ```bash
    # 查看docker IP
    docker inspect [containerId]
    
    # 修改hosts
    sudo vi /etc/hosts
    
    # 添加 docker IP  hostname
    即：192.168.99.100  wxw-hbase
    
    -------
    # 如果是wins系统,在本地的C:\Windows\System32\drivers\etc下修改hosts文件
    # 添加 192.168.99.100  启动hbase时设置的主机名
     即：192.168.99.100  wxw-hbase
    ```

- 浏览器查看Hbase的web界面：

  - 修改host方式

  ```bash
  http://docker IP:宿主机上(容器)的开放端口 16010对应的指定主机的端口/master-status
  
  例：http://172.23.65.208:16010/master-status
  ```

  <img src="asserts/image-20210701132752708.png" alt="image-20210701132752708" style="zoom:50%;" />  

  - 直接启动的方式访问：http://localhost:16010

- 进入到hbase容器

  ```bash
  ## 进入hbase容器
  docker exec -it hbase bash
  
  ## 然后执行连接到正在运行的 HBase 实例
  hbase shell 
  ```

  <img src="asserts/image-20210701133638618.png" alt="image-20210701133638618" style="zoom:50%;" /> 

#### 1.2 单机Hbase 使用

> *首次使用 HBase*

使用`hbase shell`位于HBase 安装的*bin/*目录中的命令连接到正在运行的 HBase 实例

```bash
## 键入help 并按 Enter，以显示 HBase Shell 的一些基本用法信息, 请注意，表名、行、列都必须用引号引起来。
hbase(main):001:0> help
HBase Shell, version 1.3.2, r1bedb5bfbb5a99067e7bc54718c3124f632b6e17, Mon Mar 19 18:47:19 UTC 2018
Type 'help "COMMAND"', (e.g. 'help "get"' -- the quotes are necessary) for help on a specific command.
Commands are grouped. Type 'help "COMMAND_GROUP"', (e.g. 'help "general"') for help on a command group.

COMMAND GROUPS:
  Group name: general
  Commands: status, table_help, version, whoami

  Group name: ddl
  Commands: alter, alter_async, alter_status, create, describe, disable, disable_all, drop, drop_all, enable, enable_all, exists, get_table, is_disabled, is_enabled, list, locate_region, show_filters

  Group name: namespace
  Commands: alter_namespace, create_namespace, describe_namespace, drop_namespace, list_namespace, list_namespace_tables

  Group name: dml
  Commands: append, count, delete, deleteall, get, get_counter, get_splits, incr, put, scan, truncate, truncate_preserve

  Group name: tools
  Commands: assign, balance_switch, balancer, balancer_enabled, catalogjanitor_enabled, catalogjanitor_run, catalogjanitor_switch, close_region, compact, compact_rs, flush, major_compact, merge_region, move, normalize, normalizer_enabled, normalizer_switch, split, splitormerge_enabled, splitormerge_switch, trace, unassign, wal_roll, zk_dump

  Group name: replication
  Commands: add_peer, append_peer_tableCFs, disable_peer, disable_table_replication, enable_peer, enable_table_replication, get_peer_config, list_peer_configs, list_peers, list_replicated_tables, remove_peer, remove_peer_tableCFs, set_peer_tableCFs, show_peer_tableCFs

  Group name: snapshots
  Commands: clone_snapshot, delete_all_snapshot, delete_snapshot, delete_table_snapshots, list_snapshots, list_table_snapshots, restore_snapshot, snapshot

  Group name: configuration
  Commands: update_all_config, update_config

  Group name: quotas
  Commands: list_quotas, set_quota

  Group name: security
  Commands: grant, list_security_capabilities, revoke, user_permission

  Group name: procedures
  Commands: abort_procedure, list_procedures

  Group name: visibility labels
  Commands: add_labels, clear_auths, get_auths, list_labels, set_auths, set_visibility

SHELL USAGE:
Quote all names in HBase Shell such as table and column names.  Commas delimit
command parameters.  Type <RETURN> after entering a command to run it.
Dictionaries of configuration used in the creation and alteration of tables are
Ruby Hashes. They look like this:

  {'key1' => 'value1', 'key2' => 'value2', ...}

and are opened and closed with curley-braces.  Key/values are delimited by the
'=>' character combination.  Usually keys are predefined constants such as
NAME, VERSIONS, COMPRESSION, etc.  Constants do not need to be quoted.  Type
'Object.constants' to see a (messy) list of all constants in the environment.

If you are using binary keys or values and need to enter them in the shell, use
double-quote'd hexadecimal representation. For example:

  hbase> get 't1', "key\x03\x3f\xcd"
  hbase> get 't1', "key\003\023\011"
  hbase> put 't1', "test\xef\xff", 'f1:', "\x01\x33\x40"

The HBase shell is the (J)Ruby IRB with the above HBase-specific commands added.
For more on the HBase Shell, see http://hbase.apache.org/book.html
```

##### 1.2.0 hbase shell 和hbase实例连接

```bash
# hbase连接到hbase实例
hbase shell;
```

##### 1.2.1 创建一个表

使用该`create`命令创建一个新表。您必须指定表名称和 ColumnFamily 名称。

```bash
hbase(main):001:0> create 'wxw-hbase-table', 'cf'
0 row(s) in 0.4170 seconds

=> Hbase::Table - test
```

##### 1.2.2 查看创建的表

使用`list`命令确认你的表存在

```bash
hbase(main):002:0> list 'test'
TABLE
test
1 row(s) in 0.0180 seconds

=> ["test"]
```

##### 1.2.3 查看表的详细信息

使用`describe`命令查看详细信息，包括配置默认值

```bash
hbase(main):003:0> describe 'test'
Table test is ENABLED
test
COLUMN FAMILIES DESCRIPTION
{NAME => 'cf', VERSIONS => '1', EVICT_BLOCKS_ON_CLOSE => 'false', NEW_VERSION_BEHAVIOR => 'false', KEEP_DELETED_CELLS => 'FALSE', CACHE_DATA_ON_WRITE =>
'false', DATA_BLOCK_ENCODING => 'NONE', TTL => 'FOREVER', MIN_VERSIONS => '0', REPLICATION_SCOPE => '0', BLOOMFILTER => 'ROW', CACHE_INDEX_ON_WRITE => 'f
alse', IN_MEMORY => 'false', CACHE_BLOOMS_ON_WRITE => 'false', PREFETCH_BLOCKS_ON_OPEN => 'false', COMPRESSION => 'NONE', BLOCKCACHE => 'true', BLOCKSIZE
 => '65536'}
1 row(s)
Took 0.9998 seconds
```

##### 1.2.4 写入数据

要将数据放入表中，请使用该`put`命令。

```bash
hbase(main):003:0> put 'test', 'row1', 'cf:a', 'value1'
0 row(s) in 0.0850 seconds

hbase(main):004:0> put 'test', 'row2', 'cf:b', 'value2'
0 row(s) in 0.0110 seconds

hbase(main):005:0> put 'test', 'row3', 'cf:c', 'value3'
0 row(s) in 0.0100 seconds
```

在这里，我们插入三个值，一次一个。第一个插入是 at `row1`, column `cf:a`，值为`value1`。HBase 中的列由列族前缀组成，`cf`在本例中，后跟冒号，然后是列限定符后缀，`a`在本例中。

##### 1.2.5 查看表中所有数据

从 HBase 获取数据的方法之一是扫描。使用该`scan`命令扫描表中的数据。您可以限制扫描，但目前，所有数据都已获取。

```bash
hbase(main):006:0> scan 'test'
ROW                                      COLUMN+CELL
 row1                                    column=cf:a, timestamp=1421762485768, value=value1
 row2                                    column=cf:b, timestamp=1421762491785, value=value2
 row3                                    column=cf:c, timestamp=1421762496210, value=value3
3 row(s) in 0.0230 seconds
```

##### 1.2.6 查看表中单行数据

要一次获取一行数据，请使用该`get`命令。

```bash
hbase(main):007:0> get 'test', 'row1'
COLUMN                                   CELL
 cf:a                                    timestamp=1421762485768, value=value1
1 row(s) in 0.0350 seconds
```

##### 1.2.7 禁用表

如果要删除表或更改其设置，以及在某些其他情况下，您需要先使用该`disable`命令禁用该表。您可以使用该`enable`命令重新启用它。

```bash
hbase(main):008:0> disable 'test'
0 row(s) in 1.1820 seconds

hbase(main):009:0> enable 'test'
0 row(s) in 0.1770 seconds
```

##### 1.2.8 删除表

要删除（删除）表，请使用该`drop`命令。

```bash
hbase(main):011:0> drop 'test'
0 row(s) in 0.1370 seconds
```

##### 1.2.9 退出hbase shell

要退出 HBase Shell 并与集群断开连接，请使用该`quit`命令。HBase 仍在后台运行

#### 1.3 Java admin API 

HBase 使用 Java 语言开发，因而 HBase 原生提供了一个 Java 语言客户端。这篇文章介绍 HBase Admin API，包括创建、启用、禁用、删除表等。如果项目使用 Maven 进行依赖管理，只需添加如下依赖即可以使用 Java 客户端访问 HBase 集群：

> 如果要是遇到 Protobuf 等类冲突时，可以使用 HBase 提供的一个非常方便的 Jar：

```xml
<dependency>
  <groupId>org.apache.hbase</groupId>
  <artifactId>hbase-shaded-client</artifactId>
  <version>2.1.6</version>
</dependency>

<dependency>
  <groupId>org.apache.hbase</groupId>
  <artifactId>hbase-shaded-server</artifactId>
  <version>2.1.6</version>
</dependency>
```

`hbase-shaded-client` 和 `hbase-shaded-server` 是在无法以其他方式解决依赖冲突的场景下使用的。在没有冲突的情况下，我们应首选：`hbase-client` 和 `hbase-server`。不要在协处理器内部使用 `hbase-shaded-server`或 `hbase-shaded-client`，因为这样可能会发生不好的事情。

##### 1.3.1 连接HBase

构建一个 Configuration 示例，该示例包含了一些客户端配置，最重要的必须配置是 HBase 集群的 ZooKeeper 地址与端口。ConnectionFactory 根据 Configuration 示例创建一个 Connection 对象，该 Connection 对象线程安全，封装了连接到 HBase 集群所需要的所有信息，如元数据缓存等。由于 Connection 开销比较大，类似于关系数据库的连接池，因此实际使用中会将该 Connection 缓存起来重复使用：

```java
public class HBaseConn {
    private static final HBaseConn INSTANCE = new HBaseConn();
    private static Configuration config;
    private static Connection conn;

    private HBaseConn() {
        try {
            if (config == null) {
                config = HBaseConfiguration.create();
                config.set("hbase.zookeeper.quorum", "127.0.0.1:2181");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取连接
     * @return
     */
    private Connection getConnection() {
        if (conn == null || conn.isClosed()) {
            try {
                conn = ConnectionFactory.createConnection(config);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    /**
     * 关闭连接
     */
    private void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取连接
     * @return
     */
    public static Connection create() {
        return INSTANCE.getConnection();
    }

    /**
     * 关闭连接
     */
    public static void close() {
        INSTANCE.closeConnection();
    }
}
```

相关文章

1. [HBase Java Admin API](https://cloud.tencent.com/developer/article/1562121) 

### hbase 基础

#### 1. 命名空间管理

> http://hbase.apache.org/book.html#_namespace

```bash
#Create a namespace
create_namespace 'my_ns'

#create my_table in my_ns namespace
create 'my_ns:my_table', 'fam'

#drop namespace
drop_namespace 'my_ns'

#alter namespace
alter_namespace 'my_ns', {METHOD => 'set', 'PROPERTY_NAME' => 'PROPERTY_VALUE'}

# 查看命名空间下的表 test是命名空间
list_namespace_tables 'test'

# 查看所有命名空间
list_namespace
```



相关文章

1. [HBase 命名空间 Namespace](https://cloud.tencent.com/developer/article/1544735) 

#### 2. 建表

> http://hbase.apache.org/book.html#_table 

```bash
Creates a table. Pass a table name, and a set of column family
specifications (at least one), and, optionally, table configuration.
Column specification can be a simple string (name), or a dictionary
(dictionaries are described below in main help output), necessarily
including NAME attribute.
Examples:

Create a table with namespace=ns1 and table qualifier=t1
  hbase> create 'ns1:t1', {NAME => 'f1', VERSIONS => 5}

Create a table with namespace=default and table qualifier=t1
  hbase> create 't1', {NAME => 'f1'}, {NAME => 'f2'}, {NAME => 'f3'}
  hbase> # The above in shorthand would be the following:
  hbase> create 't1', 'f1', 'f2', 'f3'
  hbase> create 't1', {NAME => 'f1', VERSIONS => 1, TTL => 2592000, BLOCKCACHE => true}
  hbase> create 't1', {NAME => 'f1', CONFIGURATION => {'hbase.hstore.blockingStoreFiles' => '10'}}

Table configuration options can be put at the end.
Examples:

  hbase> create 'ns1:t1', 'f1', SPLITS => ['10', '20', '30', '40']
  hbase> create 't1', 'f1', SPLITS => ['10', '20', '30', '40']
  hbase> create 't1', 'f1', SPLITS_FILE => 'splits.txt', OWNER => 'johndoe'
  hbase> create 't1', {NAME => 'f1', VERSIONS => 5}, METADATA => { 'mykey' => 'myvalue' }
  hbase> # Optionally pre-split the table into NUMREGIONS, using
  hbase> # SPLITALGO ("HexStringSplit", "UniformSplit" or classname)
  hbase> create 't1', 'f1', {NUMREGIONS => 15, SPLITALGO => 'HexStringSplit'}
  hbase> create 't1', 'f1', {NUMREGIONS => 15, SPLITALGO => 'HexStringSplit', REGION_REPLICATION => 2, CONFIGURATION => {'hbase.hregion.scan.loadColumnFamiliesOnDemand' => 'true'}}
  hbase> create 't1', {NAME => 'f1', DFS_REPLICATION => 1}

You can also keep around a reference to the created table:

  hbase> t1 = create 't1', 'f1'
```

#### 3. 查看

- 查看已经存在的表集合

  ```bash
  list
  ```

- 查看表的详细信息

  ```bash
  describe
  ```

- 查看表中的数据

  ```bash
  # scan命令扫描表中的 所有数据
  scan 'test'
  
  # 查看表中的  单行数据
  get 'test', 'row1'
  ```

  

## 项目实践

### springboot和hbase集成

#### 1. maven 包依赖

对于springboot操作hbase来说，我们可以选择官方的依赖包`hbase-client`，但这个包的google类库很多时候会和你的项目里的google类库冲突，最后就是你的程序缺少类而无法启动，解决这个问题的方法很多，而最彻底的就是自己封装一个shade包，或者使用人家封装好的shade包，shade就是maven里的一个重写包的插件，非常好用。

- 之前的原始包

  ```xml
    <dependency>
      <groupId>org.apache.hbase</groupId>
      <artifactId>hbase-client</artifactId>
      <version>2.4.4</version>
    </dependency>
  ```

- 使用shade包

  ```xml
    <dependency>
      <groupId>org.apache.hbase</groupId>
      <artifactId>hbase-shaded-client</artifactId>
      <version>2.4.4</version>
    </dependency>
  ```

不使用shade包，在put操作时会有错误，在scan类里的newLimit位置会出现错误

```java
/**
 * 插入一条记录
 */
@Test
public void putTest() {
  String rowKey = UUID.randomUUID().toString();
  hBaseDao.put(TABLE_NAME, rowKey, COLUMN_FAMILY, "name", Bytes.toBytes("testData"));
}
```

**主要区别**

- hbase-client 依赖了太多第三方库，如果你没有构建工具，很容易造成jar版本冲突。。
- hbase-shaded-client 把所有的东西都打包了

相关文章

1. [HBase~hbase-shaded-client解决包冲突问题](https://www.cnblogs.com/lori/p/13523063.html) 

#### 2. 建表

> 概览

```bash
/**
 * Hbase2.x Test环境访问
 * <p>
 * 在测试环境 test命名空间，存在一张 wxw-test 表，列簇为cf，表中有10条数据
 * <p>
 * create 'test:wxw-test', {NAME => 'cf', COMPRESSION => 'SNAPPY'}
 * <p>
 * |rowkey |name	     |gender |birthday
 * |1      |周杰伦	     |男	    |1979-01-18
 * |2      |林俊杰	     |男	    |1981-03-27
 * |3      |范玮琪	     |女	    |1976-03-18
 * |4      |Angelababy |女	    |1989-02-28
 * |5      |佟丽娅	     |女	    |1983-08-08
 * |6      |张敏	      |女	   |1968-02-07
 * |7      |范冰冰	     |女	    |1981-09-16
 * |8      |周杰	      |男	   |1970-08-05
 * <p>
 * put 'test:wxw-test', '1', 'cf:name', '周杰伦'
 * put 'test:wxw-test', '1', 'cf:gender', '男'
 * put 'test:bwxw-test', '1', 'cf:birthday', '1979-01-18'
 */
```



- 创建命名空间

  ```bash
  #Create a namespace
  create_namespace 'test'
  ```

- 在指定的命名空间建表

  ```bash
  # 表名：wxw-test 列族：cf
  create 'test:wxw-test','cf'
  
  # 查看创建的表
  list
  
  ## 输出结果
  ----
  hbase(main):007:0> list
  TABLE
  test:wxw-test
  1 row(s) in 0.0260 seconds
  
  => ["test:wxw-test"]
  ```

- 

















