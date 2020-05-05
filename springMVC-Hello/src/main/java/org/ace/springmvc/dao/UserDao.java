package org.ace.springmvc.dao;

import org.ace.springmvc.pojo.User;

public interface UserDao {

    User selectByName(String username);
}
