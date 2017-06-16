package com.smart.androidutils.activity.spider.bean;


import java.io.Serializable;

/**
 * @author xander on  2017/5/24.
 * @function
 */

public class SpiderBean implements Serializable {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
