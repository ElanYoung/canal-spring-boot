# 更新日志

## 🌈 1.0.0 `2025-03-07` 

### 🚀 New Features

- 🎉 支持 Canal 客户端自动配置，提供开箱即用 Spring Boot Starter
- ⚡️ 支持异步消息处理，通过配置 `canal.async=true` 开启
- 🛠️ 支持自定义消息处理器，实现 `EntryHandler` 接口即可
- 🔧 支持多种表名注解（`@CanalTable`、`@Table`、`@TableName`）
- 📦 支持批量消息处理，可配置 `canal.batch-size` 参数
- ⏱️ 支持消息消费超时配置，可设置 `canal.timeout` 参数
- 🔍 支持表名过滤，通过 `canal.filter` 配置订阅指定表
