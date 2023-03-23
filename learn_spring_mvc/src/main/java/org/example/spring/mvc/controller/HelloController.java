package org.example.spring.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {
    // "/" --> /WEB-INT/templates/index.html
    @RequestMapping(value = "/")
    public String index() {
        // 返回的是视图名称
        return "index";
    }

    @RequestMapping(value = "/target")
    public String toTarget() {
        return "target";
    }
}
