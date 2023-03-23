package org.example.spring.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HandlerController {
    @RequestMapping("/testInterceptor")
    public String testInterceptor() {
        return "success";
    }
}
