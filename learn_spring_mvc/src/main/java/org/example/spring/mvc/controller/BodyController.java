package org.example.spring.mvc.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * HttpMessageConverter
 * @RequestBody      用于获取请求体
 * @ResponseBody     使得返回值为报文响应体内容
 * RequestEntity     封装请求报文的一种类型
 * ResponseEntity    对应于响应报文类型
 *
 * @RestController   标识在控制器的类上，该注解相当于给该类的每个方法添加了@ResponseBody注解
 */
@Controller
public class BodyController {
    @RequestMapping("/testRequestBody")
    public String testRequestBody(@RequestBody String requestBody) {
        System.out.println(requestBody);
        return "success";
    }

    @RequestMapping("/testRequestEntity")
    public String testRequestEntity(RequestEntity<String> requestEntity) {
        // 当起requestEntity表示整个请求报文的信息
        System.out.println("requestHeader: " + requestEntity.getHeaders());
        System.out.println("requestBody: " + requestEntity.getBody());
        return "success";
    }

    @RequestMapping("/testResponse")
    public void testResponse(HttpServletResponse response) throws IOException {
        response.getWriter().print("hello, response");
    }

    // @ResponseBody使得返回值为报文响应体内容
    @RequestMapping("/testResponseBody")
    @ResponseBody
    public String testResponseBody() {
        return "the content of response";
    }

    // java对象数据转换为json格式的字符串作为响应体返回
    @RequestMapping("/testResponseUser")
    @ResponseBody
    public User testResponseUser() {
        User user = new User("mufiye", "123456", 22);
        return user;
    }

    @RequestMapping("testdown")
    public ResponseEntity<byte[]> testResponseEntity(HttpSession session) throws IOException {
        ServletContext servletContext = session.getServletContext();
        String realPath = servletContext.getRealPath("/static/img/1.jpg");
        // 创建输入流
        InputStream is = new FileInputStream(realPath);
        // 创建字节数组
        byte[] bytes = new byte[is.available()];
        // 将流读入到字节数组中
        is.read(bytes);
        // 创建响应头信息
        MultiValueMap<String, String> headers = new HttpHeaders();
        // 设置下载方式以及下载文件的名字
        headers.add("Content-Disposition", "attachment;filename=1.jpg");
        // 设置响应码
        HttpStatus statusCode = HttpStatus.OK;
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(bytes, headers, statusCode);
        is.close();
        return responseEntity;
    }

    @RequestMapping("/testUp")
    public String testUp(MultipartFile photo, HttpSession httpSession) throws IOException {
        String fileName = photo.getOriginalFilename();
        ServletContext servletContext = httpSession.getServletContext();
        String photoPath = servletContext.getRealPath("photo");
        File file = new File(photoPath);
        if(file.exists()){
            file.mkdir();
        }
        String finalPath = photoPath + File.separator + fileName;
        photo.transferTo(new File(finalPath));
        return "success";
    }
}
