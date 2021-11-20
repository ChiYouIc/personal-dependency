# personal-dependency

#### 介绍

开发依赖，主要包含一些复用性较强的代码，例如 mybatis 的包扫描、异步管理、web开发的统一数据格式返回、单点登陆等等。

#### 子模块

| 模块名                      | 描述                                                         |
| --------------------------- | ------------------------------------------------------------ |
| personal-start-dependencies | 项目的总体依赖                                               |
| personal-start-web          | web 开发模块，统一异常处理，统一接口数据格式返回             |
| personal-start-security     | 安全模块，使用的是 Spring Security                           |
| personal-start-mybatis      | Mybatis 模块，实现了自定义的类路径扫描加载，自定义拦截器做权限控制（开发中） |
| personal-start-redis        | Redis 模块，简单封装                                         |
| personal-start-sync         | 异步模块，异步事件的管理                                     |
| personal-start-log          | 日志，日志收集功能                                           |
| personal-start-sso          | 单点登陆核心包                                               |
| personal-start-framework    | 项目整体框架依赖模块（主要封装的是与Web相关的代码）          |
| personal-start-common       | 工具集，包含了 Hutool                                        |
| personal-start-accessLimit  | 接口的控制访问                                               |
| personal-start-websocket    | WebSocket 连接模块                                               |

