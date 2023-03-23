package org.example.mufiye.microSpring.test.mapper;

import org.example.mufiye.microSpring.framework.annotation.AutoWare;
import org.example.mufiye.microSpring.framework.annotation.Mapper;
import org.example.mufiye.microSpring.framework.util.BeanUtil;
import org.example.mufiye.microSpring.test.bean.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @Date: 2022/12/11
 * @Author: mufiye
 * @Class: mapper
 * @Description: description...
 */

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
