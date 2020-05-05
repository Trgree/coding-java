package org.ace.springmvc.controller;

import org.ace.springmvc.pojo.User;
import org.ace.springmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author L
 * @date 2018/3/4
 */
@Controller
public class LoginController {

  //  @Autowired // 按类型注入
    @Resource // 选按name,再按type
    private UserService userService;

    @RequestMapping(value = "/loginIndex")
    public String index(){
        return "login";
    }

    @RequestMapping(value = "/login")
    public String  login(String username, String password, HttpServletRequest request) throws Exception {
        try{
            User user = userService.login(username, password);
            HttpSession session = request.getSession();
            session.setAttribute("currentUser", user);
        } catch (Exception e) {
            request.setAttribute("message", e.getMessage());
            return "login";
        }
        return "success";
    }
}
