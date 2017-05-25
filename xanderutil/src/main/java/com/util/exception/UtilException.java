package com.util.exception;

/**
 * author xander on  2017/5/24.
 */

public class UtilException extends Exception {

    public UtilException(String message) {
        super(message);
         System.err.println(message);
    }

}
