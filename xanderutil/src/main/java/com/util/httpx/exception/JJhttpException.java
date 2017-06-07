package com.util.httpx.exception;

/**
 * ================================================
 * 创建日期：16/8/28
 * ================================================
 */
public class JJhttpException extends Exception {

    public static JJhttpException INSTANCE(String msg) {
        return new JJhttpException(msg);
    }

    public JJhttpException() {
        super();
    }

    public JJhttpException(String detailMessage) {
        super(detailMessage);
    }

    public JJhttpException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public JJhttpException(Throwable throwable) {
        super(throwable);
    }
}
