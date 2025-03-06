<h1 align="center"><a href="https://github.com/ElanYoung/canal-spring-boot" target="_blank">🚀 Canal Spring Boot Starter</a></h1>
<p align="center">
  <a href="https://doc.starimmortal.com"><img alt="author" src="https://img.shields.io/badge/author-ElanYoung-blue.svg"/></a>
  <a href="https://search.maven.org/search?q=g:com.starimmortal%20AND%20a:canal-spring-boot-starter"><img alt="Maven Central" src="https://img.shields.io/maven-central/v/com.starimmortal/canal-spring-boot-starter?label=Maven%20Central"/></a>
  <a href="https://docs.spring.io/spring-boot/docs/2.7.12/reference/html/"><img alt="Spring Boot" src="https://img.shields.io/badge/Spring Boot-2.7.18-brightgreen.svg"/></a>
  <a href="https://github.com/StarImmortal/canal-spring-boot/blob/master/LICENSE"><img alt="LICENSE" src="https://img.shields.io/github/license/StarImmortal/canal-spring-boot.svg"/></a>
</p>

<p align="center">
  <a href="https://github.com/StarImmortal/canal-spring-boot/stargazers"><img alt="star" src="https://img.shields.io/github/stars/StarImmortal/canal-spring-boot.svg?label=Stars&style=social"/></a>
  <a href="https://github.com/StarImmortal/canal-spring-boot/network/members"><img alt="star" src="https://img.shields.io/github/forks/StarImmortal/canal-spring-boot.svg?label=Fork&style=social"/></a>
  <a href="https://github.com/StarImmortal/canal-spring-boot/watchers"><img alt="star" src="https://img.shields.io/github/watchers/StarImmortal/canal-spring-boot.svg?label=Watch&style=social"/></a>
</p>

## 简介

Canal 是阿里巴巴开源的一个基于 MySQL 数据库增量日志解析，提供增量数据订阅和消费的组件。

Canal 的原理是基于 MySQL 的主从复制，模拟 MySQL Slave 的交互协议，将自己伪装成 MySQL Slave，向 MySQL Master 发送 dump 协议，MySQL Master 收到 dump 请求后，开始推送 binary log 给 Canal，Canal 解析 binary log 字节流对象并且可以通过实现 CanalEventListener 或继承 AbstractCanalEventListener 来实现数据处理。

[canal-spring-boot-starter](https://github.com/StarImmortal/canal-spring-boot) 是基于 Canal 封装的 Spring Boot Starter，可以帮助你更快地在 [Spring Boot](https://spring.io/projects/spring-boot) 项目中集成 Canal，实现数据库增量订阅和消费。

## 快速开始

### 引入依赖

```xml
<dependency>
  <groupId>io.github.elanyoung</groupId>
  <artifactId>canal-spring-boot-starter</artifactId>
  <version>1.0.0</version>
</dependency>
```

### 参数配置

```yaml
canal:
  server:
    # Canal 服务器地址
    ip: 127.0.0.1
    # Canal 服务器端口
    port: 11111
    # Canal 服务器用户名
    username: canal
    # Canal 服务器密码
    password: canal
  # 是否开启异步处理
  async: true
  # Canal 实例名称
  destination: example
```

#### 配置说明

| 属性                    | 描述                                                                                                    | 默认值       |
|-----------------------|-------------------------------------------------------------------------------------------------------|-----------|
| canal.mode            | 指定 Canal 客户端类型。支持的类型包括：`simple`（单节点）、`cluster`（集群）、`zk`（Zookeeper）、`kafka`（Kafka，仅支持 flatMessage 格式）。 | `default` |
| canal.filter          | 配置订阅的表名称。若配置，则仅订阅指定的表；若未配置，则订阅所有表。                                                                    | `""`      |
| canal.batch-size      | 每次消费的消息数量。当消息数量达到该值时，将触发一次消费操作。                                                                       | `1`       |
| canal.timeout         | 消费消息的时间间隔（单位：秒）。                                                                                      | `1`       |
| canal.server          | Canal 服务端的地址，多个地址以英文逗号分隔，格式为 `host:port`。                                                             | `null`    |
| canal.destination     | Canal 实例名称。在 Kafka 模式下表示 Topic 名称，在 RabbitMQ 模式下表示 Queue 名称。                                          | `null`    |
| canal.username        | 访问 Canal 或 RabbitMQ 的用户名。                                                                             | `null`    |
| canal.password        | 访问 Canal 或 RabbitMQ 的密码。                                                                              | `null`    |
| canal.group-id        | Kafka 消费者组 ID，用于 Kafka 客户端订阅消息。                                                                       | `null`    |
| canal.async           | 是否启用异步消费模式。若启用异步消费，消费时异常将不会触发消息回滚，且无法保证消息顺序消费。                                                        | `true`    |
| canal.partition       | Kafka 分区编号。                                                                                           | `null`    |
| canal.vhost           | RabbitMQ 虚拟主机名称。                                                                                      | `"/"`     |
| canal.accessKey       | RabbitMQ 访问密钥（Access Key）。                                                                            | `""`      |
| canal.secretKey       | RabbitMQ 密钥（Secret Key）。                                                                              | `""`      |
| canal.resourceOwnerId | RabbitMQ 资源所有者 ID。                                                                                    | `null`    |

### 创建监听器

```java
@Slf4j
@Component
@CanalTable(value = "stu")
public class StudentHandler implements EntryHandler<StudentDO> {

	@Override
	public void insert(StudentDO student) {
		log.info("insert message {}", student);
	}

	@Override
	public void update(StudentDO before, StudentDO after) {
		log.info("update before {} ", before);
		log.info("update after {}", after);
	}

	@Override
	public void delete(StudentDO student) {
		log.info("delete {}", student);
	}

}
```

## TODO

- [ ] RabbitMQ
- [ ] ZooKeeper
- [ ] Kafka

## 参考资料

- [Canal GitHub](https://github.com/alibaba/canal)
- [Canal 官方文档](https://github.com/alibaba/canal/wiki)
- [Canal 客户端文档](https://github.com/alibaba/canal/wiki/ClientExample)

## 项目趋势

[![Stargazers over time](https://starchart.cc/StarImmortal/canal-spring-boot.svg)](https://starchart.cc/StarImmortal/canal-spring-boot)

## 开源协议

[Apache License](https://opensource.org/license/apache-2-0/)

Copyright (c) 2023 ElanYoung