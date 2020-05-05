package org.ace.springframework.web.servlet;

import org.ace.demo.annotation.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author L
 * @date 2018/3/10
 */
public class DispatcherServlet extends HttpServlet {

    /** 存储springmvc配置文件内容，这里使用properties文件 */
    private Properties props = new Properties();

    /** 存储base-package下的所有类名 */
    private List<String> classNames = new ArrayList<>();

    /**存储加了@Controller或@Service的类，并且每个类的@Autowired属性注入好*/
    private Map<String, Object> ioc = new HashMap<>();

    /** 存储方法和url的映射 */
   private  List<Handler> handlerMappings = new ArrayList<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("spring MVC初始化开始");

        // 1.加载配置
        doLoadConfig(config);

        // 2.扫描所有相关的类
        doScanner(props.getProperty("base-package"));

        // 3.初始化相关类的实例，并保存在IOC容器中
        doInstance();

        // 4.自动化的依赖注入
        doAutowired();

        // 5.初始化HandlerMapping
        try {
            initHandlerMapping();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        System.out.println("spring MVC初始化完成");
    }

    private void doLoadConfig(ServletConfig config) {
        // 初始化
        String contextConfigLocation = config.getInitParameter("contextConfigLocation");
        if(!contextConfigLocation.startsWith("classpath:")){
            System.out.println("contextConfigLocation目前只支持classpath方式，请加上classpath:前缀");
            return;
        }
        contextConfigLocation = contextConfigLocation.replace("classpath:","");
        InputStream is = null;
        try {
            is = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
            props.load(is);
            System.out.println("配置加载完成");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(is !=null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void doScanner(String packageName) {
        URL url = this.getClass().getClassLoader().getResource(packageName.replaceAll("\\.", "/"));
        File classesDir = new File(url.getFile());
        for (File file : classesDir.listFiles()) {
            if(file.isDirectory()){
                doScanner(packageName + "." + file.getName());
            }else {
                classNames.add(packageName + "." + file.getName().replace(".class",""));
            }
        }
        System.out.println("扫描包完成");
    }

    private void doInstance() {
        if(classNames.isEmpty()) {
            return;
        }
        try {
            // 实例化加了Controller和Service注释的类，保存到ioc窗口中
            for(String className : classNames){
                Class<?> clazz = Class.forName(className);

                if(clazz.isAnnotationPresent(Controller.class)) {
                    String beanName = lowerFirst(clazz.getSimpleName());
                    ioc.put(beanName, clazz.newInstance());
                } else if(clazz.isAnnotationPresent(Service.class)) {
                    // map key：
                    // 1.默认使用名称首字母小写
                    // 2.如果定义了名称，优先使用名称
                    // 3.根据类型匹配，利用接口作为key
                    String beanName = lowerFirst(clazz.getSimpleName());
                    Service service = clazz.getAnnotation(Service.class);
                    if(!service.value().equals("")) {
                        beanName = service.value();
                    }
                    Object obj = clazz.newInstance();
                    ioc.put(beanName, obj);
                    Class<?>[] interfaces =  clazz.getInterfaces();
                    for(Class<?> i : interfaces){
                        ioc.put(i.getName(), obj);
                    }
                } else {
                    continue;
                }
            }
            System.out.println("实例化类完成");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }

    private void doAutowired() {
        if(ioc.isEmpty()) {
            System.out.println("ioc容器为空");
            return;
        }
        for(Map.Entry<String, Object> kv : ioc.entrySet()){
            Field[] fields =  kv.getValue().getClass().getDeclaredFields();
            for(Field field : fields){
                // 如果没加@Autowired 注解的，直接跳过
                if(!field.isAnnotationPresent(Autowired.class)){
                   continue;
                }
                Autowired autowired = field.getAnnotation(Autowired.class);
                String beanName = autowired.value();
                if(beanName.equals("")){
                    // 如果没有指定value,使用接口方式匹配
                    beanName = field.getType().getName();
                }
                field.setAccessible(true);// 暴力访问
                try {
                    field.set(kv.getValue(), ioc.get(beanName));// 设置字段值
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("自动注入完成");
    }

    private void initHandlerMapping() throws IllegalAccessException, InstantiationException {
        if(ioc.isEmpty()) {
            System.out.println("ioc容器为空");
            return;
        }

        // 遍历所有的Controller，映射方法
        for(Map.Entry<String, Object> kv : ioc.entrySet()){
            Class<?> clazz = kv.getValue().getClass();
            String url = "";
            RequestMapping classRequestMapping = clazz.getAnnotation(RequestMapping.class);

            if(classRequestMapping !=null && !classRequestMapping.value().equals("")) {
                url = classRequestMapping.value();
            }

            // 遍历Controller的所有方法，映射好Handler并加入handlerMappings
           Method[] methods = clazz.getMethods();
           for(Method method : methods) {
               if(!method.isAnnotationPresent(RequestMapping.class)){
                   continue;
               }
               RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);

               String mUrl = url + requestMapping.value();

               Pattern pattern= Pattern.compile(mUrl.replaceAll("/+","/"));
               handlerMappings.add(new Handler(kv.getValue(), method, pattern));
           }
        }
        System.out.println("initHandlerMapping完成");
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            doDispatcher(req, resp);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void doDispatcher(HttpServletRequest req, HttpServletResponse resp) throws InvocationTargetException, IllegalAccessException {
        String uri=  req.getRequestURI();
        Handler handler = getHandler(req);
        if(null == handler) {
            System.out.printf("%s 无法mapping", uri);
            try {
                resp.getWriter().write("404 - page not found");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        Method method = handler.getMethod();
        Class<?>[] paramTypes = method.getParameterTypes();
        Object[] paramValues = new Object[paramTypes.length];

        Map<String, String[]> paramMap = req.getParameterMap();
        for(Map.Entry<String, String[]> param : paramMap.entrySet()){
            String value = Arrays.toString(param.getValue()).replaceAll("\\[|\\]","").replaceAll(",\\s","");
            if(!handler.getParamIndexMapping().containsKey(param.getKey())){
                continue;
            }
            int index = handler.getParamIndexMapping().get(param.getKey());
            paramValues[index] = convert(paramTypes[index], value);
        }

        int reqIndex = handler.getParamIndexMapping().get(HttpServletRequest.class.getName());
        paramValues[reqIndex] = req;
        int respIndex = handler.getParamIndexMapping().get(HttpServletResponse.class.getName());
        paramValues[respIndex] = resp;

        try {
            method.invoke(handler.controller, paramValues);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            try {
                System.out.println("请求参数不对");
                resp.getWriter().write("404 - page not found");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    private Object convert(Class<?> paramType, String value) {
        if(Integer.class == paramType){
            return Integer.valueOf(value);
        }
        return value;
    }

    private String lowerFirst(String str){
        char[] chars = str.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

    private Handler getHandler(HttpServletRequest req){
        if(handlerMappings.isEmpty()) {
            return null;
        }
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replaceAll(contextPath, "").replaceAll("/+", "/");
       for(Handler handler : handlerMappings){

           try {
               if(handler.pattern.matcher(url).matches()){
                   return handler;
               }
           } catch (Exception e) {
              throw e;
           }
       }
       return null;
    }

    public static void main(String[] args) {
        System.out.println("org.ace.demo".replaceAll("\\.","/"));
    }
}

/**
 * Controll方法封装
 */
class Handler {
    Object controller;// Controller
    Method method;// 映射的方法
    Pattern pattern;// 用于方法映射，因为RequestMapping是使用正则，不是字符串equals
    Map<String, Integer> paramIndexMapping = new HashMap<>();// 参数顺序

    public Handler(Object controller, Method method, Pattern pattern) {
        this.controller = controller;
        this.method = method;
        this.pattern = pattern;
        putParamIndexMapping(method);
    }

    public void putParamIndexMapping(Method method){
        Annotation[][] anns = method.getParameterAnnotations();

        // 提取方法中带了注解的参数
        for (int i = 0; i < anns.length; i++) {
            for(Annotation an : anns[i]){
                if(an instanceof RequestParam) {
                    String paramName = ((RequestParam) an).value();
                    if(!paramName.equals("")) {
                        paramIndexMapping.put(paramName, i);
                    }
                }
            }
        }

        // 提取方法中Request和Response参数
        Class<?>[] paramTypes = method.getParameterTypes();
        for (int i = 0; i < paramTypes.length; i++) {
            Class<?> paramType = paramTypes[i];
            if(paramType == HttpServletRequest.class || paramType == HttpServletResponse.class) {
                paramIndexMapping.put(paramType.getName(), i);
            }
        }
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public Map<String, Integer> getParamIndexMapping() {
        return paramIndexMapping;
    }

    public void setParamIndexMapping(Map<String, Integer> paramIndexMapping) {
        this.paramIndexMapping = paramIndexMapping;
    }
}
