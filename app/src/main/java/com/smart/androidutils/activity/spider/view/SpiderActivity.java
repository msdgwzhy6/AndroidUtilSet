package com.smart.androidutils.activity.spider.view;

import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.smart.androidutils.BaseActivity;
import com.smart.androidutils.R;
import com.smart.androidutils.activity.spider.bean.SpiderBean;
import com.smart.androidutils.activity.spider.presenter.HtmlSpiderPresenter;
import com.smart.androidutils.activity.spider.viewholder.SpiderViewHolderHelper;
import com.util.viewholder.CommonAdapter;

import java.util.List;

import static com.util.view.UtilWidget.getView;

public class SpiderActivity extends BaseActivity implements IHandleHtmlData<SpiderBean> {
    private TextView mIdTitle;
    private ListView mListView;

    @Override
    protected int initLayout() {
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
        new HtmlSpiderPresenter(this).getHTMLFromNet();
    }

    @Override
    public void handleData(List<SpiderBean> dataLists, String title) {
        mIdTitle.setText(title);
        mListView.setAdapter(new CommonAdapter<>(SpiderActivity.this,dataLists,R.layout.spider_item_layout,new SpiderViewHolderHelper())
        );
    }

    @Override
    public void failure(Exception e) {
        mIdTitle.setText("sss");
        e.printStackTrace();
        Log.i("xxx", "failure" +e.getMessage());
    }
}
