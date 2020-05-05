import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 获取  Class对象的方法
 * 1.Class.forName
 * 2.class.class
 * 3.Object.getClass
 * 4.ClassLoader.loadClass()
 */
public class ReflectApp {

    @Test
    public void test0() throws ClassNotFoundException {
        Class<?> clazz = Class.forName("java.lang.Object");
        System.out.println(clazz);

        Method[] methods = clazz.getDeclaredMethods();// 所有方法
        Arrays.stream(methods).forEach(System.out::println);
        System.out.println("~~~~~~~~~~~~~~");
        methods = clazz.getMethods(); // public 方法
        Arrays.stream(methods).forEach(System.out::println);
    }

    @Test
    public void test1() {
        Class<?> clazz = String.class;
        System.out.println(clazz);

        String a = "xxx";
        clazz = a.getClass();
        System.out.println(clazz);
    }

    @Test
    public void test2() {
        Class<?> clazz = Integer.class;
        System.out.println(clazz);//  class java.lang.Integer
        System.out.println(Integer.TYPE); // int
    }

    @Test
    public void test3() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("aaa");
        Class<?> clazz = list.getClass();
        Method method = clazz.getMethod("add", Object.class);
        method.invoke(list, 1000);
        for (Object obj : list) {
            System.out.println(obj);
        }
    }

    @Test
    public void test4() throws Exception {
        Class<?> clazz = ReflectApp.class.getClassLoader().loadClass("java.lang.Object");
        System.out.println(clazz);

    }

    @Test
    public void test5() throws Exception {
        Foo foo =new Foo();
        Method method = foo.getClass().getDeclaredMethod("test", null);
        System.out.println(method);
        if(!method.isAccessible()){
            method.setAccessible(true);
        }
        method.invoke(foo,null);// 调用 method.setAccessible(true)后可以访问private方法
    }

    @Test
    public void test6() throws Exception {
        Foo foo =new Foo();
        Field field = foo.getClass().getDeclaredField("age");
        System.out.println(field);
        if(!field.isAccessible()){
            field.setAccessible(true);
        }
        field.set(foo,"6");// 调用 method.setAccessible(true)后可以设置private属性
        System.out.println(field.get(foo));
    }

    @Test
    public void test7() throws Exception {
        Class<?> clazz = Class.forName("Foo");
        Constructor<?> constructor = clazz.getDeclaredConstructor(String.class);
        Object obj = constructor.newInstance("a");
        System.out.println(obj);
    }

    @Test
    public void test8() throws Exception {
        Class<?> clazz = Class.forName("Foo");
        Constructor<?> constructor = clazz.getDeclaredConstructor();
        Object obj = constructor.newInstance();
        System.out.println(obj);
        obj = clazz.newInstance();
        System.out.println(obj);
    }

}

class Foo {
    private String age;

    public Foo(String age) {
        this.age = age;
    }
    public Foo() {
    }
    private void test(){
        System.out.println("private method test");
    }
}