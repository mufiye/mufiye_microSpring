package org.example.spring.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class RestfulController {
    /**
     * 使用restful模拟用户资源的增删改查
     * /user      GET      查询所有用户信息
     * /user/id   GET      根据id查询用户信息
     * /user      POST     添加用户信息
     * /user/id   DELETE   根据id删除用户信息
     * /user      PUT      更新用户信息
     */

    @GetMapping("/user")
    public String getAllUsers() {
        // 查询所有用户信息
        return "success";
    }

    @GetMapping("/user/{id}")
    public String getUserById(String id) {
        // 根据id查询用户信息
        return "success";
    }

    @PostMapping("/user")
    public String addUser(String name, String password) {
        // 添加用户信息
        return "success";
    }

    /**
        PUT和DELETE请求需要HiddenHttpMethodFilter这个类
        表单中需要添加:
        <input type="hidden" name="_method" value="PUT">
        <input type="hidden" name="_method" value="DELETE">

        web.xml中需要配置HiddenHttpMethodFilter
     */
    @PutMapping("/user")
    public String updateUser(String name, String password) {
        // 更新用户信息
        return "success";
    }

    @DeleteMapping("/user/{id}")
    public String deleteUser(String id) {
        // 根据id删除用户信息
        return "success";
    }
}
