package com.smart.androidutils.activity.spider.view;

import java.util.List;

/**
 * @author xander on  2017/5/25.
 * @function 处理数据的接口
 */

public interface IHandleHtmlData<T> {
    void handleData(List<T> dataLists,String title);
    void failure(Exception e);
}
