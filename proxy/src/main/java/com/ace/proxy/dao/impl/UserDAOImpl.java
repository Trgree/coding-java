package com.ace.proxy.dao.impl;

import com.ace.proxy.dao.UserDAO;

public class UserDAOImpl implements UserDAO {
    @Override
    public void save(String name) {
        System.out.println(name + " saved");
    }

    @Override
    public void save(int id) {
        System.out.println(id + " id saved");
    }
}
