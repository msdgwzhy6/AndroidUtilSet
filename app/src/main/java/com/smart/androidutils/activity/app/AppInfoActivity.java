package com.smart.androidutils.activity.app;

import android.graphics.drawable.Drawable;
import android.widget.ListView;

import com.smart.androidutils.BaseActivity;
import com.smart.androidutils.R;
import com.smart.androidutils.activity.app.bean.MyAppInfoBean;
import com.smart.androidutils.activity.app.viewholder.AppViewHolderHelper;
import com.sdk.util.app.AppBeanCallback;
import com.sdk.util.app.UtilAppInfo;
import com.sdk.util.viewholder.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

public class AppInfoActivity extends BaseActivity {
    private ListView mListView;
    private List<MyAppInfoBean> mItemBeanList;
    private MyAppInfoBean mAppInfoBean;

    @Override
    protected void setActivityTitle() {
        super.setActivityTitle();
        mAvtivitytTitle = "VersionName = "+UtilAppInfo.getVersionName();
    }

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
        new UtilAppInfo(true, AppInfoActivity.this.getPackageManager(), new AppBeanCallback<String,Drawable>() {
            @Override
            public void result(List<String> packageName, List<Drawable> iconDrawable) {
                mItemBeanList = new ArrayList<>();
                for (int i = 0; i < packageName.size(); i++) {
                    mAppInfoBean = new MyAppInfoBean();
                    mAppInfoBean.setAppName(packageName.get(i));
                    mAppInfoBean.setAppIcon(iconDrawable.get(i));
                    mItemBeanList.add(mAppInfoBean);
                }

                mListView.setAdapter(new CommonAdapter<MyAppInfoBean>(AppInfoActivity.this,mItemBeanList,R.layout.app_info_item_layout,new AppViewHolderHelper()));
            }
            @Override
            public void failure(Exception e) {

            }
        });
    }

}
