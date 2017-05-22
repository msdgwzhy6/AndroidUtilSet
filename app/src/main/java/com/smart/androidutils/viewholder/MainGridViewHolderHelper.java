package com.smart.androidutils.viewholder;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.smart.androidutils.R;
import com.smart.androidutils.activity.reflect.ReflectActivity;
import com.smart.androidutils.activity.SPActivity;
import com.smart.androidutils.activity.SpiderActivity;
import com.smart.androidutils.activity.classactivity.ClassActivity;
import com.smart.androidutils.bean.ItemBean;
import com.smart.holder_library.CommonAdapter;

import java.util.List;

import static com.smart.androidutils.UtilWidget.getView;

/**
 * Created by smart on 2017/5/17.
 */

public class MainGridViewHolderHelper implements CommonAdapter.IListHolderHelperCallback<MainGridViewHolder,ItemBean> {
    @Override
    public CommonAdapter.IBaseViewHolder initViewHolder(MainGridViewHolder viewHolder, View convertView) {
        viewHolder = new MainGridViewHolder();
        viewHolder.mNameText = getView(convertView, R.id.main_grid_text);
        return viewHolder;
    }

    @Override
    public void bindListDataToView(final Context context, final List<ItemBean> iBaseBeanList, final MainGridViewHolder viewHolder, final int position) {
        viewHolder.mNameText.setText(iBaseBeanList.get(position).getName());
        viewHolder.mNameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.mNameText.getText().toString().equals(context.getResources().getString(R.string.act_sp))) {
                        context.startActivity(new Intent(context, SPActivity.class));
                }
                if (viewHolder.mNameText.getText().toString().equals(context.getResources().getString(R.string.act_spider))) {
                        context.startActivity(new Intent(context, SpiderActivity.class));
                }
                if (viewHolder.mNameText.getText().toString().equals(context.getResources().getString(R.string.act_class))) {
                        context.startActivity(new Intent(context, ClassActivity.class));
                }
                if (viewHolder.mNameText.getText().toString().equals(context.getResources().getString(R.string.act_reflect))) {
                        context.startActivity(new Intent(context, ReflectActivity.class));
                }

            }
        });
    }
}
