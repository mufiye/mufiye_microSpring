package org.example.mufiye.microSpring.test.controller;

import com.alibaba.fastjson.JSONObject;
import org.example.mufiye.microSpring.framework.annotation.AutoWare;
import org.example.mufiye.microSpring.framework.annotation.Controller;
import org.example.mufiye.microSpring.test.bean.User;
import org.example.mufiye.microSpring.test.service.UserService;

/**
 * @Date: 2022/12/11
 * @Author: mufiye
 * @Class: UserController
 * @Description: description...
 */
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
