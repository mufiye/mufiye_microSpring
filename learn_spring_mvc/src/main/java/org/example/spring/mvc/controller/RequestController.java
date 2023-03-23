package org.example.spring.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Request：一次请求
 * Session：一次会话（cookie存在周期）
 * Application：
 */
// Request域
@Controller
public class RequestController {
    @RequestMapping("testServletAPI")
    public String testServletAPI(HttpServletRequest request) {
        request.setAttribute("testScope", "hello, servlet API");  // 原生servlet api
        // getAttribute, removeAttribute
        return "success";
    }

    @RequestMapping("/testModelAndView")
    public ModelAndView testModelAndView() {
        /*
            ModelAndView有model和view的功能
            model主要用于向请求域共享数据，其也属于request域
            view主要用于设置视图，实现页面跳转
         */
        ModelAndView mav = new ModelAndView();
        mav.addObject("testModelAndView", "hello, model and view");
        mav.setViewName("success");
        return mav;
    }

    /**
     * Model
     * ModelMap
     * Map
     * 这三者都是BindingAwareModelMap
     */
    @RequestMapping("/testModel")
    public String testModel(Model model) {  // request域
        model.addAttribute("testModel", "hello, model");
        return "success";
    }

    @RequestMapping("/testMap")
    public String testMap(Map<String, Object> map) {  // request域
        map.put("testMap", "hello, map");
        return "success";
    }

    @RequestMapping("/testModelMap")
    public String testModelMap(ModelMap modelMap) {  // request域
        modelMap.addAttribute("testModelMap", "hello, modelMap");
        return "success";
    }
}
