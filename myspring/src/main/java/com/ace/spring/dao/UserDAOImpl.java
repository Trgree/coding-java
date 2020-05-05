package com.ace.spring.dao;

import com.ace.spring.model.User;

public class UserDAOImpl implements UserDAO{

    @Override
    public void save(User u) {
        System.out.println("save:" + u);
    }
}
