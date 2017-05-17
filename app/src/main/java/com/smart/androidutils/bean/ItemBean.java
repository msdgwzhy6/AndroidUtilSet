package com.smart.androidutils.bean;

import com.smart.holder_library.CommonAdapter;

/**
 * Created by smart on 2017/5/17.
 */

public class ItemBean implements CommonAdapter.IBaseBean {
    private String mName;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
