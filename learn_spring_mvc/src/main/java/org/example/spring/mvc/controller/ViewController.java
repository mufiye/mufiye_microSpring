package org.example.spring.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 视图的作用是渲染数据
 * 视图解析器很关键，但是现在基本视图都交给view了吧？
 */

@Controller
public class ViewController {
    @RequestMapping("/testThymeleafView")
    public String testThymeleafView() {  // ThymeleafView
        return "success";
    }

    // InternalResourceView，属于同一个request域
    @RequestMapping("/testForward")
    public String testForward() {
        return "forward:/testThymeleafView";  // 相当于服务器内部发送了"/testThymeleafView"请求
    }

    // RedirectView, redirect属于不同的request域
    @RequestMapping("/testRedirectView")
    public String testRedirect() {
        return "redirect:/testThymeleafView";
    }
}
