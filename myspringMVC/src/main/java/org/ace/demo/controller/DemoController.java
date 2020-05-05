package org.ace.demo.controller;

import org.ace.demo.annotation.Autowired;
import org.ace.demo.annotation.Controller;
import org.ace.demo.annotation.RequestMapping;
import org.ace.demo.annotation.RequestParam;
import org.ace.demo.service.DemoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author L
 * @date 2018/3/10
 */
@Controller
@RequestMapping("/test")
public class DemoController {

    @Autowired
    private DemoService service;

    @RequestMapping("/get")
    public void get(HttpServletRequest request, HttpServletResponse response, @RequestParam("name") String name){
        System.out.printf("DemoController get方法,name=%s\n", name);
        String result = service.get(name);
        try {
            response.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
