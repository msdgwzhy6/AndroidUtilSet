package com.smart.androidutils.activity.spider;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.smart.androidutils.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SpiderActivity extends AppCompatActivity {

    @BindView(R.id.id_title)
    TextView mIdTitle;
    @BindView(R.id.id_spider)
    Button mIdSpider;

    private Handler mHandler;
    private String url="http://www.dianping.com/search/category/2/45";
//    private String url="http://192.168.200.187:8080/Act";
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
//                Connection conn = Jsoup.connect(url);
                // 下载url并解析成html DOM结构
                Document doc = null;
                try {
                    doc = Jsoup.connect(url).get();
                    Log.i("xxx", "run" +doc.title());
                    final Document finalDoc = doc;
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
