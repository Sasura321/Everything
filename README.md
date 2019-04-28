### 1. 项目名称
MyEverything
### 2. 意义
+ 接却Windous命令行下文件搜索问题
+ 基于Java开发的工具可以在Windows和Linux平台上无差异使用
+ 锻炼编码能力

### 4. 技术
+ Java技术
+ Java多线程，线程池
+ JDBC编程
+ 嵌入式数据库H2
+ Apache Commons IO库
+ 接口编程设计模式
+ 插件Lombok

### 5. 实现
+ 从上文下分层结构：
```
客户端->统一调度器->索引，检索，监控，拦截器->数据库访问->数据库
```
+ 文字描述
### 6. 使用
+ 程序发布包：MyEverything.zip
    + lib(依赖库)
    + MyEverything-1.0.0.jar
    + everything_config.peroperties
+ 解压程序发布包
+ 参考如下说明，配置文件
```
#最大检索返回结果数
everything.max_return = 40
#是否开启构建索引
everything.enable_build_index = false
#检索时depth深度的排序规则
everything.order_by_desc = dalse
#文件监控的时间间隔
everything.interval = 60000
#索引目录
#索引排除目录
everything.handle_path.exclude_path = C:\\Windows;C:\\$Recycle.Bin
#索引包含目录
everything.handle_path.include_path = C:\\;D:\\;E:\\;F:\\

```
+ 启动程序
```
java -jar MyEverything-1.0.0.jar [配置文件路径]
```
### 7. 总结
+ 掌握了Java的网络的常用的API和步骤
+ 熟练使用数据库
+ 熟练使用maven工具
