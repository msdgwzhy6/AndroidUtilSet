package com.smart.androidutils;


import java.io.Serializable;

/**
 * Created by smart on 2017/5/17.
 */

public class BaseBean implements Serializable {
    private String mName;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
