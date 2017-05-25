package com.smart.androidutils.activity.spider.presenter;

import com.smart.androidutils.activity.spider.bean.SpiderBean;
import com.smart.androidutils.activity.spider.modle.HtmlModle;
import com.smart.androidutils.activity.spider.modle.IHtmlModuleResult;
import com.smart.androidutils.activity.spider.view.IHandleHtmlData;

import java.util.List;

/**
 * @author xander on  2017/5/25.
 * @function
 */

public class HtmlSpiderPresenter {
    private IHandleHtmlData<SpiderBean> mIHandleHtmlData;
    private HtmlModle mHtmlModle;

    public HtmlSpiderPresenter(IHandleHtmlData<SpiderBean> handleHtmlData) {
        mIHandleHtmlData = handleHtmlData;
        mHtmlModle = new HtmlModle();
   }
    public void getHTMLFromNet(){
        mHtmlModle.acquireData(new IHtmlModuleResult<SpiderBean>() {

            @Override
            public void result(List<SpiderBean> resultList, String title) {
                mIHandleHtmlData.handleData(resultList,title);
            }

            @Override
            public void failure(Exception e) {
                mIHandleHtmlData.failure(e);
            }
        });
    }

}
