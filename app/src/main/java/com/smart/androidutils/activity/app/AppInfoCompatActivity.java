package com.smart.androidutils.activity.app;

import android.graphics.drawable.Drawable;
import android.widget.ListView;

import com.smart.androidutils.BaseCompatActivity;
import com.smart.androidutils.R;
import com.smart.androidutils.activity.app.bean.MyAppInfoBean;
import com.smart.androidutils.activity.app.viewholder.AppViewHolderHelper;
import com.util.app.AppBeanCallback;
import com.util.app.UtilAppInfo;
import com.util.viewholder.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

public class AppInfoCompatActivity extends BaseCompatActivity {
    private ListView mListView;
    private List<MyAppInfoBean> mItemBeanList;
    private MyAppInfoBean mAppInfoBean;

    @Override
    protected int initLayout() {
        return R.layout.activity_app_info;
    }

    @Override
    protected void initView() {
        mListView = (ListView) findViewById(R.id.id_app_info_list);
    }

    @Override
    protected void initData() {
        new UtilAppInfo(true, AppInfoCompatActivity.this.getPackageManager(), new AppBeanCallback<String,Drawable>() {
            @Override
            public void result(List<String> packageName, List<Drawable> iconDrawable) {
                mItemBeanList = new ArrayList<>();
                for (int i = 0; i < packageName.size(); i++) {
                    mAppInfoBean = new MyAppInfoBean();
                    mAppInfoBean.setAppName(packageName.get(i));
                    mAppInfoBean.setImage(iconDrawable.get(i));
                    mItemBeanList.add(mAppInfoBean);
                }

                mListView.setAdapter(new CommonAdapter<MyAppInfoBean>(AppInfoCompatActivity.this,mItemBeanList,R.layout.app_info_item_layout,new AppViewHolderHelper()));
            }
            @Override
            public void failure(Exception e) {

            }
        });
    }

}
