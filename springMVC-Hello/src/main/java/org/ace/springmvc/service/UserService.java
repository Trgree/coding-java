package org.ace.springmvc.service;

import org.ace.springmvc.pojo.User;

public interface UserService {
    User login(String name, String password);
}
