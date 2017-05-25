package com.smart.androidutils.activity.spider.modle;


import com.util.viewholder.CommonAdapter;

import java.util.List;

/**
 * @author xander on  2017/5/25.
 * @function  获取数据结果的接口
 */

public interface IHtmlModuleResult <T extends CommonAdapter.IBaseBean>{
    void result(List<T> resultList,String title);
    void failure(Exception e);
}
