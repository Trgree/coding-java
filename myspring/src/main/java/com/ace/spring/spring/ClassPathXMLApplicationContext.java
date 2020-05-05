package com.ace.spring.spring;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ClassPathXMLApplicationContext implements BeanFactory {

    private Map<String, Object> beans = new HashMap<>();

    public ClassPathXMLApplicationContext() throws Exception {

        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(ClassPathXMLApplicationContext.class.getClassLoader().getResourceAsStream("bean.xml"));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element root = document.getRootElement();
        for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
            Element element = it.next();
            // do something
            String id = element.attributeValue("id");
            String clazz = element.attributeValue("class");
            Object o = Class.forName(clazz).newInstance();
            beans.put(id, o);
            for(Object ele : element.elements("property")){
                Element e = (Element) ele;
                String name = e.attributeValue("name");
                String bean = e.attributeValue("bean");
                System.out.println(name + "-" + bean);
                String setMethod = "set" + name.substring(0, 1).toUpperCase() + name.substring(1, name.length());
                System.out.println(setMethod);
                Object obj = beans.get(name);
                Method method = o.getClass().getMethod(setMethod, obj.getClass().getInterfaces());
                method.invoke(o, obj);
            }

        }
    }

    @Override
    public Object getBean(String name) {
        return beans.get(name);
    }
}
