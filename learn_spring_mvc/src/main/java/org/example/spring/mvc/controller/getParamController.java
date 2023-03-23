package org.example.spring.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class getParamController {
    @RequestMapping("/testServletAPI")
    public String testServletAPI(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        System.out.println(username);
        System.out.println(password);
        return "success";
    }

    @RequestMapping("/testParam")
    public String testParam(String username, String password) {  // 参数名与请求参数name一致，自动传参
        System.out.println(username);
        System.out.println(password);
        return "success";
    }

    @RequestMapping("/testParam2")
    public String testParam2(String username, String password, String[] hobbies) { // hobbies可以装载复选框的数据，形参为string则为逗号拼接的形式
        System.out.println(username);
        System.out.println(password);
        for(String hobby : hobbies){
            System.out.println(hobby);
        }
        return "success";
    }

    @RequestMapping("/testParam3")
    public String testParam3(
            @RequestParam("user_name") String username,
            // required属性表示该参数可以为空，不是必须要的, 默认为true; defaultValue就是设置默认值
            @RequestParam(value = "pass_word", required = false, defaultValue = "hehe") String password
    ) {
        System.out.println(username);
        System.out.println(password);
        return "success";
    }

    @RequestMapping("/testHeaders")
    public String testHeaders(
            @RequestHeader("Host") String host
    ) {
        System.out.println("host: " + host);
        return "success";
    }

    // related to cookie
    @RequestMapping("/testCookie")
    public String testCookie(
            @CookieValue("JSESSIONID") String JSESSIONID
    ) {
        System.out.println(JSESSIONID);
        return "success";
    }

    @RequestMapping("/testPOJO")
    public String testPOJO(User user) {
        System.out.println(user.getName());
        System.out.println(user.getPassword());
        System.out.println(user.getAge());
        return "success";
    }
}
