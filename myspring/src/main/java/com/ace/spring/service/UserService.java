package com.ace.spring.service;

import com.ace.spring.dao.UserDAO;
import com.ace.spring.model.User;

public class UserService{

    private UserDAO userDAO;

    public void add(User u) {
        userDAO.save(u);
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
