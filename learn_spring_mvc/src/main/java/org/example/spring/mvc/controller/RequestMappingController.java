package org.example.spring.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @RequestMapping  =>  All
 * @GetMapping  =>  GET
 * @PostMapping  =>  POST
 * @PutMapping   =>  PUT
 * @DeleteMapping  =>  DELETE
 */

@Controller
@RequestMapping("/test")  // 标识的是请求路径的初识信息
public class RequestMappingController {
    @RequestMapping("/testRequestMapping")  // 标识的是请求路径的具体信息
    public String success() {
        return "success";
    }

    @RequestMapping(value = {"/testMultiple1", "testMultiple2"})  // 将多个路径请求映射到一个方法进行处理
    public String multiPathRequest() {
        return "success";
    }

    @RequestMapping(value = "/testMethod", method = RequestMethod.GET)  // 要求请求方法是GET
    public String methodRequest() {
        return "success";
    }

    // 请求方法是GET和POST都可以
    @RequestMapping(value = {"/testMethod2", "/testMethod3"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String multipleMethodRequest() {
        return "success";
    }

    @RequestMapping(
            value = {"testParams"},
            params = {"username"}   // 表示必须携带username
    )
    public String paramsRequest() {
        return "success";
    }

    @RequestMapping(
            value = {"testParams1"},
            params = {"!username"}   // 表示不能携带username
    )
    public String paramsRequest1() {
        return "success";
    }

    @RequestMapping(
            value = {"testParams2"},
            params = {"username=admin"}   // 表示必须携带username, 且值为admin
    )
    public String paramsRequest2() {
        return "success";
    }

    @RequestMapping(
            value = {"testParams3"},
            params = {"username=admin", "password=123456"}   // 表示必须携带username和password, 且值分别为admin和123456
    )
    public String paramsRequest3() {
        return "success";
    }

    @RequestMapping(
            headers = {"Host!=localhost:8081", "Connection=keep-alive"}  // 和上面的params设置方式类似
    )
    public String headersRequest() {
        return "success";
    }

    @RequestMapping("/a?a/testAnt")   // ?表示任意符号
    public String testAnt() {
        return "success";
    }

    @RequestMapping("/a*a/testAnt")   // *表示任意0个或多个字符
    public String testAnt2() {
        return "success";
    }

    @RequestMapping("/**/testAnt")   // **表示一层或多层目录
    public String testAnt3() {
        return "success";
    }

    @RequestMapping("/testPath/{id}/{username}")
    public String testPath(@PathVariable("id") Integer id, @PathVariable("username") String username) {
        System.out.println("id: " + id);
        System.out.println("username: " + username);
        return "success";
    }
}
