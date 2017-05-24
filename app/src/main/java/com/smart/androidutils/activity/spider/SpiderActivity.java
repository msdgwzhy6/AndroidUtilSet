package com.smart.androidutils.activity.spider;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.smart.androidutils.BaseActivity;
import com.smart.androidutils.R;
import com.smart.androidutils.activity.spider.bean.SpiderBean;
import com.smart.androidutils.activity.spider.viewholder.SpiderViewHolderHelper;
import com.smart.holder_library.CommonAdapter;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.smart.androidutils.constant.Constant.urlMR;
import static com.smart.androidutils.constant.Constant.userAgent;
import static com.util.view.UtilWidget.getView;

public class SpiderActivity extends BaseActivity {


    private Handler mHandler;

    private List<SpiderBean> mSpiderBeanList;
    private SpiderBean spiderBean;
    private TextView mIdTitle;
    private ListView mListView;

    @Override
    protected int initLayout() {
        mHandler = new Handler();
        return R.layout.activity_spider;
    }

    @Override
    protected void initView() {
        mIdTitle = getView(this,R.id.id_spider_title);
        mListView = getView(this,R.id.id_spider_list_view);

    }

    @Override
    protected void initData() {
        setTitle(getResources().getString(R.string.act_spider));
        getData();
    }

    private void getData() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                Connection conn = Jsoup.connect(urlMR);
                // 修改http包中的header,伪装成浏览器进行抓取
                conn.header("User-Agent", userAgent);
                // 下载url并解析成html DOM结构
                Document doc;
                try {
                    doc = conn.get();
                    Log.i("xxx", "run" +doc.title());
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
                        Log.i("xxx", "run" +element.text().trim().length());
                        Log.i("xxx", "run" +element.text().trim());
                    }
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mIdTitle.setText(finalDoc.title());
                            mListView.setAdapter(
                                    new CommonAdapter(SpiderActivity.this,mSpiderBeanList,R.layout.spider_item_layout,new SpiderViewHolderHelper())
                            );
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
