package org.example.spring.mvc.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * DefaultHandlerExceptionResolver  默认的异常解析器
 * SimpleMappingExceptionResolver
 */

@ControllerAdvice
public class ExceptionController {
    // 处理异常，value表示对应的异常
    @ExceptionHandler(value = {ArithmeticException.class, NullPointerException.class})
    public String testException(Exception ex, Model model) {
        model.addAttribute("exception", ex);
        ex.printStackTrace();
        return "error";
    }
}
