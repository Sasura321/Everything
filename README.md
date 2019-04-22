## MyEverything

### 目标：搭建项目
1. 创建maven项目
2. 配置pom
3. 创建包（按照功能分类）
4. 创建入口程序
5. 简单的公共代码开发
6. git

### 模型
1. 文件类型（FileType  比如：img，png，jpeg，bmp，gif，bmg，jpg...）
2. 检索条件（Condition name filetype limit order ...）
Thing （name，path，depth，fileType）

### Lombok
1. 在maven的配置中引入Lombok库
2. 在IDEA中安装Lombok Plugin
3. 在IDEA中启用注解处理器

### 数据库
1. 本地系统中的文件或者文件夹 ---> Java File ---> Java Thing ---> 数据库中的记录Table；
2. 数据库的创建
3. 数据库表的设计
4. JDBC ---> 数据库的驱动


### 检索
1. 据库的初始化工作
2. 数据库的访问操作（使用DataSource）
3. 实现检索业务（查询） =>search codition =>DAO

### 索引
1. 数据库的初始化工作
2. 数据库的访问工作（使用DataSource)
3. 实现索引业务（插入）
4. 如果遍历文件系统中的所有文件，并且将文件对象转换为Thing对象，调用数据库访问的插入操作。
5. 对一些特定的文件或者目录进行排除
6. 将文件对象转换为Thing对象


### 基本的模型类抽象：
1. 文件类型（png jpeg doc pdt exe msi jar zip rar ppt txt sh ）
2. 索引File的属性之后的信息 Thing
3. 检索的参数（条件）Condition

### 设计数据库的表：
1. 创建数据库（everything_plus）
2. 设计数据库的表（Thing类创建的对象的属性）

### 统一调度器
#### 索引
后台线程执行
#### 检索
立即执行返回结果
#### 配置
1. 索引目录：包含的目录，排除的目录
2. 通过参数设置是否开启索引线程
3. 查询时按照depth升序还是降序
4. 查询的结果数量是多少，最大的返回数量
#### 索引
1. 如果遍历文件系统中的所有文件
2. 对一些特定的文件或者目录进行排除
3. 将文件对象转换为Thing对象

### 命令行客户端
    1. 欢迎
    2. 帮助
    3. 退出
    4. 索引
    5. 检索

### 扩展功能文件监控
1. Index，Search
2. Search命令检索的数据是由Index存储
3. 本地的文件系统随时都在发生变化
    1) 文件创建（数据库中没有）
    2) 文件删除（数据库中还有）
    3) 文件修改（修改的是内容）----不关心
4. 如何实现文件的变化监控
    1) 遍历文件系统和数据库对比
    2) 依赖文件系统的通知事件


### 性能测试，优化
#### 测试
1. 数据的写入
2. 数据的查询
3. 机器信息，软件信息，多次测试和多组取平均值，图表展示
#### 优化
1. 数据库优化（索引-> 索引的特点，创建原则）
2 .代码优化



"# Everything" 
"# Everything" 
