package org.example.spring.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;

@Controller
public class SessionController {
    @RequestMapping("/testSession")
    public String testSession(HttpSession httpSession) {
        httpSession.setAttribute("testSession", "hello, session");
        return "success";
    }

    // spring原生的session
    // @SessionAttribute
}
