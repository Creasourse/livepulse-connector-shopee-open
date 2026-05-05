# Shopee Connector

Shopee 电商平台连接器，用于同步订单、产品和客户数据。

## 版本信息

- **版本**: v2.0
- **描述**: 同步 Shopee 订单、产品和客户数据，支持多店铺管理和实时 Webhook 事件订阅
- **分类**: E-commerce
- **端口**: 23011

## 功能特性

- 多店铺管理
- 订单数据同步
- 产品数据同步
- 客户数据同步
- 定时同步任务
- Webhook 事件订阅
- RESTful API 接口
- Swagger 文档支持

## 数据模型

### 核心表

- `shopee_shop` - 店铺配置表
- `shopee_order` - 订单表
- `shopee_product` - 产品表
- `shopee_customer` - 客户表
- `shopee_webhook_log` - Webhook 日志表
- `shopee_sync_log` - 同步日志表

## 快速开始

### 1. 数据库初始化

```bash
psql -U shopee_user -d livepulse_shopee -f docs/sql/shopee_schema.sql
```

### 2. 配置 Nacos

在 Nacos 中创建以下配置：

- `postgresql-config.yml` - 数据库配置
- `kafka-config.yml` - Kafka 配置

### 3. 启动应用

```bash
java -jar livepulse-connector-shopee-open.jar \
  --spring.profiles.active=prod \
  --NACOS_SERVER_ADDR=nacos-host:8848
```

### 4. 访问 API 文档

```
http://localhost:23011/swagger-ui.html
```

## API 端点

### 店铺管理

- `POST /shopee/shop` - 添加店铺配置
- `PUT /shopee/shop/{id}` - 更新店铺配置
- `DELETE /shopee/shop/{id}` - 删除店铺配置
- `GET /shopee/shop/{id}` - 获取店铺配置
- `GET /shopee/shop` - 分页查询店铺列表
- `POST /shopee/shop/{id}/enable` - 启用店铺
- `POST /shopee/shop/{id}/disable` - 禁用店铺

### 订单管理

- `GET /shopee/order/{id}` - 获取订单详情
- `GET /shopee/order` - 分页查询订单列表
- `POST /shopee/order/{id}/process` - 标记为已处理

### 产品管理

- `GET /shopee/product/{id}` - 获取产品详情
- `GET /shopee/product` - 分页查询产品列表
- `POST /shopee/product/{id}/process` - 标记为已处理

### 客户管理

- `GET /shopee/customer/{id}` - 获取客户详情
- `GET /shopee/customer` - 分页查询客户列表
- `POST /shopee/customer/{id}/process` - 标记为已处理

## 定时任务

### 订单同步

- **Cron**: `0 0 */4 * * ?`
- **说明**: 每4小时同步最近30天数据

### 产品同步

- **Cron**: `0 0 2 * * ?`
- **说明**: 每天凌晨2点同步最近30天数据

### 客户同步

- **Cron**: `0 0 3 * * ?`
- **说明**: 每天凌晨3点同步最近30天数据

## Docker 部署

```bash
# 构建镜像
docker build -t livepulse-connector-shopee:2.0 .

# 启动容器
docker-compose up -d

# 查看日志
docker-compose logs -f
```

## 配置说明

### bootstrap.yml

```yaml
server:
  port: 23011

spring:
  application:
    name: shopee-open-connector-server

shopee:
  api:
    version: 2.0
    timeout: 30
  sync:
    batch-size: 100
    max-retries: 3
```

## 环境要求

- Java 17
- Maven 3.8+
- PostgreSQL 15+
- Nacos 2.5.1+

## 文档

详细部署文档请参考：[部署指南.md](部署指南.md)

## 许可证

Copyright © 2025 Livepulse
