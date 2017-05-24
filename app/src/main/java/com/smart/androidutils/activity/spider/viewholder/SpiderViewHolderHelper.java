package com.smart.androidutils.activity.spider.viewholder;

import android.content.Context;
import android.view.View;

import com.smart.androidutils.R;
import com.smart.androidutils.activity.spider.bean.SpiderBean;
import com.smart.holder_library.CommonAdapter;

import java.util.List;

import static com.util.view.UtilWidget.getView;

/**
 * @author xander on  2017/5/24.
 * @function
 */

public class SpiderViewHolderHelper implements CommonAdapter.IListHolderHelperCallback<SpiderViewHolder,SpiderBean> {
    @Override
    public CommonAdapter.IBaseViewHolder initViewHolder(SpiderViewHolder viewHolder, View convertView) {
        viewHolder = new SpiderViewHolder();
        viewHolder.mTextView = getView(convertView, R.id.id_spider_item);
        return null;
    }

    @Override
    public void bindListDataToView(Context context, List<SpiderBean> iBaseBeanList, SpiderViewHolder viewHolder, int position) {

    }
}
