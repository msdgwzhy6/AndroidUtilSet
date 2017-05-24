package com.smart.androidutils.activity.spider;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.smart.androidutils.R;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.smart.androidutils.constant.Constant.urlMR;
import static com.smart.androidutils.constant.Constant.userAgent;

public class SpiderActivity extends AppCompatActivity {

    @BindView(R.id.id_title)
    TextView mIdTitle;
    @BindView(R.id.id_spider)
    Button mIdSpider;

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spider);
        setTitle(getResources().getString(R.string.act_spider));
        ButterKnife.bind(this);
        mHandler = new Handler();
    }

    @OnClick(R.id.id_spider)
    public void onViewClicked() {
        mIdSpider.setAlpha(0.5f);
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
                    for (Element element:eles){
                        Log.i("xxx", "run" +element.text());
                    }
                    mHandler.post(new Runnable() {
                       @Override
                       public void run() {
                           mIdTitle.setText(finalDoc.title());
                           mIdSpider.setAlpha(1.0f);
                       }
                   });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
