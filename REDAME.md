# Micro Spring

这是一个我根据自己理解模仿Spring写的框架，实现了Spring IOC与Spring WebMVC的核心功能。框架的代码在

[micro_spring](https://github.com/mufiye/mufiye_microSpring/tree/master/micro_spring)模块下，而另外两个模块[learn_spring](https://github.com/mufiye/mufiye_microSpring/tree/master/learn_spring)和[learn_spring_mvc](https://github.com/mufiye/mufiye_microSpring/tree/master/learn_spring_mvc)是学习Spring和Spring MVC时留下的样例代码。

## How to run

使用console交互还是web交互需要在ApplicationContext类中的doStart方法中使用不同的事件类进行controller注册，console对应的是ConsoleInputEvent，web对应的是HttpInputEvent。

### Console Part

1. 进入我提供的[micro_spring样例目录](https://github.com/mufiye/mufiye_microSpring/tree/master/micro_spring/src/main/java/org/example/mufiye/microSpring/test)

2. 运行App.java，此时基于micro_spring的样例代码开始运行

3. 使用console进行交互 - save存入一个字段

   ```shell
   /user/save pdzhu&14
   ```

   ```shell
   INFO [Thread-RegeditEvent] com.alibaba.druid.pool.DruidDataSource.init:975 -{dataSource-1} inited
   saving user
   ```

4. 使用console进行交互 - getAll获取所有数据

   ```shell
   /user/getAll
   ```

   ```shell
   INFO [Thread-RegeditEvent] com.alibaba.druid.pool.DruidDataSource.init:975 -{dataSource-1} inited
   INFO [Thread-RegeditEvent] o.e.m.microSpring.framework.event.impl.ConsoleInputEvent.regedit:64 -result = [{"age":13,"id":1,"name":"mufiye"},{"age":22,"id":2,"name":"zx"},{"age":14,"id":3,"name":"pdzhu"},{"age":14,"id":4,"name":"pdzhu"}]
   ```

### Web Part

1. 进入我提供的[micro_spring样例目录](https://github.com/mufiye/mufiye_microSpring/tree/master/micro_spring/src/main/java/org/example/mufiye/microSpring/test)

2. 运行App.java，此时基于micro_spring的样例代码开始运行

3. web交互 - save存入一个字段

   ```
   在浏览器输入url：
   http://localhost/user/save?name=mufiye&age=23
   ```

4. web交互 - getAll获取所有数据

   ```
   在浏览器输入url：
   http://localhost/user/getAll
   ```

## Micro-Spring使用

* `@Config`：对应Spring框架的@Config注解
* `@Scan`：对应Spring框架的@ComponentScan注解，value设置要扫描的包
* `@Bean`：对应Spring框架的@Bean注解，在Config类中使用，实现组件注入
* `@Controller`：类注解，对应于Spring WebMVC的@Controller注解
* `@Service`：类注解，对应于Spring WebMVC的@Service注解
* `@Mapper`：类注解，对应于Spring WebMVC的@Repository注解
* `@AutoWare`：对应于Spring框架的@Autowired注解，根据类名实现自动装配
* `@Value`：字段注解，对应于Spring框架的@Value注解，使用注解中的value值对应的配置文件键值对字段进行装配，配置文件默认为framework.properties

### 程序启动：

```java
@Scan("${scan-package-name}")
public class App {
	public static void main(String[] args) {
		ApplicationContext.run(App.class, args);
	}
}
```

### 配置类：

```java
@Config
public class MyConfig {
  @Value("username")
	String username;
	@Value("password")
	String password;
	@Value("url")
	String url;
	@Value("driver")
	String driver;

	@Bean
	public DataSource dataSource(){
		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setUsername(username);
		druidDataSource.setPassword(password);
		druidDataSource.setUrl(url);
		druidDataSource.setDriverClassName(driver);
		return druidDataSource;
	}
}
```

### Bean：

```java
@Data
public class User {
	private int id;
	private String name;
	private int age;
}
```

### Controller层：

```java
@Controller("user")
public class UserController {

	@AutoWare
	UserService userService;

	public String getAll() {
		return JSONObject.toJSONString(userService.getAll());
	}

	public String save(String name, int age) {
		User user = new User();
		user.setAge(age);
		user.setName(name);
		return JSONObject.toJSONString(userService.add(user));
	}
}
```

### Service层：

```java
public interface UserService {
	List<User> getAll();
	boolean add(User user);
}
```

```java
@Service
public class UserServiceImpl implements UserService {

	@AutoWare
	UserMapper userMapper;

	@Override
	public List<User> getAll() {
		return userMapper.getAll();
	}

	@Override
	public boolean add(User user) {
		return userMapper.save(user);
	}
}
```
### Repository层：
```java
@Mapper
public class UserMapper {

	@AutoWare
	DataSource dataSource;

	private Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<User> getAll() {
		try (Connection connection = getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement("select * from user");
			ResultSet resultSet = preparedStatement.executeQuery();
			return BeanUtil.mapperToList(resultSet, User.class);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
			return null;
		}
	}

	public boolean save(User user) {
		try (Connection connection = getConnection()) {
			System.out.println("saving user");
			PreparedStatement preparedStatement = connection.prepareStatement("insert into user (name, age) values (?,?)");
			preparedStatement.setString(1, user.getName());
			preparedStatement.setInt(2, user.getAge());
			return preparedStatement.execute();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
			return false;
		}
	}
}
```

## Micro-Spring设计

### 包结构

* **annotation**：提供注解，已经在上面进行了介绍
* **event**：提供了事件处理类，包括console和web，web server使用netty框架书写
* **factory**：相当于ioc容器
* **scan**：用于组件扫描
* **util**：一些工具类
* **ApplicationContext类**：程序启动类

### 概述

该框架使用工厂类来模拟ioc容器创建、存储、管理实例（而对于`ApplicationContext`、`Scanner`扫描类和`ComponentFactory`组件工厂类，使用`InstanceUtil`类进行管理，提供单例支持）。对于工厂类中的实例创建，使用的是注解扫描和反射技术。自动装配（`@Value`，`@Autoware`）也是基于注解扫描和反射技术。具体的实现可以查阅`ApplicationContext`类下的`loadComponent`和`wareComponent`方法，分别对应的是加载和装配组件，`loadComponent`既可以使注解了`@Controller`、`@Service`、`@Mapper`的类被加载到容器中，还可以使配置类中的被`@Bean`注解的方法返回的实例被加载。

对于Controller的处理，我这里进行了简化实现，没有使用@RequestMapping之类的注解对方法进行注解，因为默认外界请求（console或者是http请求）的处理方式是将**command**和**url**对应到**"controller类名/方法名"**（hashmap将其存为key，对应的方法为value），同时根据次序或者参数名和请求参数名匹配参数进行方法的调用。

对于web请求的处理，我这里使用的是netty的nio模式，详细代码可以查看event.impl.http包下的代码和HttpInputEvent。

## Reference

1. [Spring framework](https://docs.spring.io/spring-framework/docs/current/reference/html/)
2. [Netty.io](https://netty.io/)
3. [User guide for netty4](https://netty.io/wiki/user-guide-for-4.x.html)
