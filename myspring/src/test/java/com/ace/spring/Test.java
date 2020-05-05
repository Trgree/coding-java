package com.ace.spring;

import com.ace.spring.dao.UserDAO;
import com.ace.spring.dao.UserDAOImpl;
import com.ace.spring.model.User;
import com.ace.spring.service.UserService;
import com.ace.spring.spring.ClassPathXMLApplicationContext;

public class Test {
    public static void main(String[] args) throws Exception {
        UserService userService = new UserService();
        UserDAO userDAO = new UserDAOImpl();
        userService.setUserDAO(userDAO);
        userService.add(new User());

        /**
         * 根据配置文件自动注入
         */
        ClassPathXMLApplicationContext context = new ClassPathXMLApplicationContext();
        UserService service = (UserService)context.getBean("userService");
        service.add(new User());
        System.out.println();
    }
}
