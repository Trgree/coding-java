package org.ace.springmvc.service.impl;

import org.ace.common.ThisSystemException;
import org.ace.springmvc.dao.UserDao;
import org.ace.springmvc.pojo.User;
import org.ace.springmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.ace.common.ExceptionUtil.*;

/**
 * @author L
 * @date 2018/3/5
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User login(String name, String password) {
        throwIfBlank("用户名不能为空", name);
        throwIfBlank("密码不能为空", password);
        User u = userDao.selectByName(name);
        if(u == null) {
            throw new ThisSystemException("用户不存在");
        }
        if(!u.getPassword().equals(password)) {
            throw new ThisSystemException("密码不正确");
        }
        return u;
    }
}
