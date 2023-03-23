package org.example.mufiye.microSpring.test.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.example.mufiye.microSpring.framework.annotation.Bean;
import org.example.mufiye.microSpring.framework.annotation.Config;
import org.example.mufiye.microSpring.framework.annotation.Value;

import javax.sql.DataSource;

/**
 * @Date: 2022/12/11
 * @Author: mufiye
 * @Class: MyConfig
 * @Description: description...
 */
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
