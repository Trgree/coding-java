package test.com.ace.proxy;

import com.ace.proxy.UserDaoProxy;
import com.ace.proxy.dao.UserDAO;
import com.ace.proxy.dao.impl.UserDAOImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Test {
    public static void main(String[] args) {

        // 静态代理
        UserDAO proxy = new UserDaoProxy(new UserDAOImpl());
        proxy.save("ace1");

        // 动态代理
        UserDAO userDAO = (UserDAO) Proxy.newProxyInstance(UserDAO.class.getClassLoader(), new Class[]{UserDAO.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("before");
                method.invoke(new UserDAOImpl(), args);
                System.out.println("after");
                return null;
            }
        });

        userDAO.save("ace");

    }
}
