package org.example.mufiye.microSpring;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.Test;

import javax.sql.DataSource;

/**
 * @Date: 2022/12/11
 * @Author: mufiye
 * @Class: MyTest
 * @Description: description...
 */
public class MyTest {
	@Test
	public void testAssignable(){
		Class<DataSource> a = DataSource.class;
		Class<DruidDataSource> b = DruidDataSource.class;
		boolean assignableFrom = a.isAssignableFrom(b);
		System.out.println(assignableFrom);
	}
}
