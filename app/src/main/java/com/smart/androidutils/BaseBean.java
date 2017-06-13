package com.smart.androidutils;


import com.sdk.util.viewholder.CommonAdapter;

/**
 * Created by smart on 2017/5/17.
 */

public class BaseBean implements CommonAdapter.IBaseBean {
    private String mName;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
