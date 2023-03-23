# Spring Boot Demo
正确的学习方式：结合文档阅读别人整合好的代码示例以及一些springboot项目进行学习！！！文字没有意义，代码才是真谛！！！
## Spring Boot 特点
### 依赖管理
* springboot有一个父项目，父项目中声明了dependency management，定义好了各个包的版本号，做好了依赖管理
* 对于具体项目，只需要在dependency标签中导入一个starter，如spring-boot-starter-web
* 也可以自定义个别包的依赖版本号
### 自动配置
拿web举例子
* 自动引入并配置好了tomcat，
* 自动引入和配置好了springmvc的常用组件
* 默认的包扫描方式
* 各种配置拥有默认值，默认配置映射到multipartProperties,配置文件的值最终会绑定每个类上，这个类在容器中创建对象
* 按需加载所有自动配置项，自动配置功能在spring-boot-autoconfigure包里面
## Spring Boot 最佳实践
1. 首先引入场景依赖, 也就是[starters](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.build-systems.starters)
2. 查看自动配置了哪些东西（选做），可以在application.properties中使debug=true，之后可以在terminal看到自动配置报告
3. 按照需要修改
   * 参考[文档](https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html#appendix.application-properties)修改
   * 自定义加入或者替换组件, @Bean, @Component(用户定义的组件优先)
   * 自定义器件(...Customizer)
## Spring Boot 一些小技巧
### LomBok简化开发
[features of lombok](https://projectlombok.org/features/)
### Dev Tools
[doc of developer tools](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.devtools)，
该dev tool的作用是热更新，本质是重新启动（restart）而不是reload。
### Spring Initializer
Idea创建project时的Spring Initializer，该工具是项目的初始化向导，通过选择对应的starter进行配置。
## YAML
* key:value, kv之间有空格
* 大小写敏感
* 使用缩进表示层级关系
* 缩进不允许使用tab，只允许空格
* 缩进的空格数不重要，只要相同层级的元素左对齐即可
* #表示注释
* ''与""表示字符串内容，会被转义/不转义

* 字面量：单个、不可分的值。date，boolean，string，number，null
```
k: v
```
* 对象：键值对的集合。map，hash，set，object
```
k: {k1: v2, k2: v2, k3: v3}
# 或者
k: 
  k1: v1
  k2: v2
  k3: v3
```
* 数组：一组按序排列的值。array，list，queue
```
k: [v1, v2, v3]
# 或者
k: 
  k1: v1
  k2: v2
  k3: v3
```
## Spring Boot Web开发
[doc related](https://docs.spring.io/spring-boot/docs/current/reference/html/web.html)
### 静态资源
* resources目录下的/static、/public、/META-INF/resources、/resources中的静态资源都能够被读取,之后访问url根路径+资源名就能够得到。
推荐在配置文件中加上静态资源访问前缀配置。
* index.html默认作为根url页面，但是spring.mvc.static-path-pattern会使其失效。
* favicon小图标
### mvc的注解
