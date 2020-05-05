package org.ace.common;

/**
 * @author L
 * @date 2018/3/5
 */
public class ExceptionUtil {

    public static String throwIfBlank(String message, String target) {
        if(target == null || (target = target.trim()).length() == 0){
            throw new ThisSystemException(message);
        }
        return target;
    }

    public static String $(String message, String target) {
        return throwIfBlank(message, target);
    }
}
