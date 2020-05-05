package org.ace.demo.annotation;

import java.lang.annotation.*;

/**
 * @author L
 * @date 2018/3/10
 */
@Target({ElementType.TYPE}) // 指定注解能修饰的目标(Class, interface (including annotation type), or enum declaration)
@Retention(RetentionPolicy.RUNTIME) // 指定该注解可以保留多久
@Documented // 定该元Annotation修饰的Annotation类将被javadoc工具提取成文档
public @interface Service {
    String value() default "";
}
