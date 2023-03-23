package org.example.mufiye.microSpring.test.service;

import org.example.mufiye.microSpring.test.bean.User;

import java.util.List;

public interface UserService {
	List<User> getAll();
	boolean add(User user);
}
