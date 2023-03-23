package org.example.spring.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

@Controller
public class ApplicationController {
    @RequestMapping("/testApplication")
    public String testApplication(HttpSession httpSession) {
        ServletContext application = httpSession.getServletContext();
        application.setAttribute("testApplication", "hello, application");
        return "success";
    }
}
