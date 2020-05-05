package org.ace.common;

/**
 * @author L
 * @date 2018/3/5
 */
public class ThisSystemException extends RuntimeException {

    public ThisSystemException() {
        super();
    }

    public ThisSystemException(String message) {
        super(message);
    }

    public ThisSystemException(String message, Throwable cause) {
        super(message, cause);
    }
}
