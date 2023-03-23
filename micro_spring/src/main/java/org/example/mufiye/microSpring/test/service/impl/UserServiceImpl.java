package org.example.mufiye.microSpring.test.service.impl;

import org.example.mufiye.microSpring.test.service.UserService;
import org.example.mufiye.microSpring.test.bean.User;
import org.example.mufiye.microSpring.framework.annotation.AutoWare;
import org.example.mufiye.microSpring.framework.annotation.Service;
import org.example.mufiye.microSpring.test.mapper.UserMapper;

import java.util.List;

/**
 * @Date: 2022/12/11
 * @Author: mufiye
 * @Class: UserServiceImpl
 * @Description: description...
 */
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
