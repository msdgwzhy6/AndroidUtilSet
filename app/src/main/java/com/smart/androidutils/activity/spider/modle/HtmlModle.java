package com.smart.androidutils.activity.spider.modle;

import android.os.Handler;

import com.sdk.util.logger.JJLogger;
import com.smart.androidutils.activity.spider.bean.SpiderBean;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.smart.androidutils.constant.ConMainUrl.urlMRSP;
import static com.smart.androidutils.constant.ConMainUrl.userAgent;

/**
 * @author xander on  2017/5/25.
 * @function 遵循业务接口 来完成业务
 */

public class HtmlModle implements IHtmlModle {
    private Handler mHandler;
    private List<SpiderBean> mSpiderBeanList;
    private SpiderBean spiderBean;
    public HtmlModle() {
        mHandler = new Handler();
    }

    @Override
    public void acquireData(final IHtmlModuleResult htmlModuleResult) {

        new Thread(){
            @Override
            public void run() {
                super.run();
                Connection conn = Jsoup.connect(urlMRSP);
                // 修改http包中的header,伪装成浏览器进行抓取
                conn.header("User-Agent", userAgent);
                // 下载url并解析成html DOM结构
                Document doc;
                try {
                    doc = conn.get();
                    JJLogger.i("xxx", "run" +doc.title());
                    final Document finalDoc = doc;
                    Elements elements = doc.getElementsByClass("content");
                    Elements eles = elements.first().getElementsByTag("p");
                    mSpiderBeanList = new ArrayList<>();
                    for (Element element:eles){
                        if (element != null &&element.text().length()>1) {
                            spiderBean = new SpiderBean();
                            spiderBean.setText(element.text().trim());
                            mSpiderBeanList.add(spiderBean);
                        }
                        assert element != null;
                        JJLogger.e("xxx", "run" +element.text().trim());
                    }
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            htmlModuleResult.result(mSpiderBeanList,finalDoc.title());
                        }
                    });
                } catch (IOException e) {
                    htmlModuleResult.failure(e);
                }
            }
        }.start();
    }
}
