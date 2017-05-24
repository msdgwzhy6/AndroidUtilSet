package com.util.exception;

/**
 * @author xander on  2017/5/24.
 * @function
 */

public class UtilException extends Exception {
    public static final int EXCEPTION_TYPE_NULL = 0X00;

    public UtilException(int type) {
        switch (type){
            case EXCEPTION_TYPE_NULL:
              throw  new  NullPointerException("空指针异常");
        }

    }

    public UtilException(String message) {
        super(message);
    }

    public UtilException(String message, Throwable cause) {
        super(message, cause);
    }

    public UtilException(Throwable cause) {
        super(cause);
    }

}
