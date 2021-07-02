### wxw-doris



### 前言

Doris 是一个基于 MPP 的交互式 SQL 数据仓库，用于报告和分析。原名Palo，由百度开发。捐赠给 Apache 软件基金会后，它更名为 Doris。

### 环境

#### 1. docker 搭建doris环境

```bash
# 拉取镜像 构建doris环境
docker pull apachedoris/doris-dev:build-env-1.3

# 检查镜像是否存在
$ docker images

# 运行镜像
$ docker run -it apachedoris/doris-dev:build-env-1.3
```

