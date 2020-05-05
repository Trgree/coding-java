package org.ace.demo.service;

import org.ace.demo.annotation.Service;

/**
 * @author L
 * @date 2018/3/10
 */
@Service
public class DemoServiceImpl implements DemoService {
    @Override
    public String get(String name) {
        System.out.printf("进入service get方法,name=%s\n", name);
        return "name is " + name;
    }
}
