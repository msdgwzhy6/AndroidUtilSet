package com.smart.androidutils.activity.spider.modle;

import com.smart.holder_library.CommonAdapter;

import java.util.List;

/**
 * @author xander on  2017/5/25.
 * @function
 */

public interface IHtmlModuleResult <T extends CommonAdapter.IBaseBean>{
    void result(List<T> resultList,String title);
    void failure(Exception e);
}
