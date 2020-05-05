package org.ace.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @author L
 * @date 2018/3/4
 */
@Controller
public class HelloController {

    @RequestMapping(value = "/hello")
    public String hello(){
        System.out.println("进入hello方法");
        return "success";
    }


    @RequestMapping(value="/testModelAndView")
    public ModelAndView testModelAndView(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("success");
        mv.addObject("name", "tom");
        return mv;
    }

    @RequestMapping(value="/testMap")
    public String testMap(Map<String, Object> map){
        map.put("name", "tom");
        return "success";
    }

    @RequestMapping(value="/testModel")
    public String testModel(Model model){
        model.addAttribute("name", "tom");
        return "success";
    }

    @RequestMapping(value="/testModelMap")
    public String testModelMap(ModelMap modelMap){
        modelMap.addAttribute("name", "tom");
        return "success";
    }
}
