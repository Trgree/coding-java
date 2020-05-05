package com.ace.proxy;

import com.ace.proxy.dao.UserDAO;
import com.ace.proxy.dao.impl.UserDAOImpl;

/**
 * 静态代理
 *
 * 若UserDAO增加方法，代理类也要增加
 */
public class UserDaoProxy implements UserDAO{

    private UserDAO userDAO;

    public UserDaoProxy(UserDAOImpl userDAO) {
        this.userDAO = userDAO;
    }



    @Override
    public void save(String name) {
        System.out.println("before");
        userDAO.save(name);
        System.out.println("after");
    }

    @Override
    public void save(int id) {

    }
}
