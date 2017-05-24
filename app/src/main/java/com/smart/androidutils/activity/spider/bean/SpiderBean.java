package com.smart.androidutils.activity.spider.bean;

import com.smart.holder_library.CommonAdapter;

/**
 * @author xander on  2017/5/24.
 * @function
 */

public class SpiderBean implements CommonAdapter.IBaseBean {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
