package com.sdk.util.viewholder.callback;

import android.content.Context;
import android.view.View;

import java.util.List;

public interface IListDataViewHolderHelper<BASEVIEWHOLDER extends BaseItemViewHolder, BASEBEAN extends java.io.Serializable> extends BaseItemViewHolder {
        /** 用于初始化ViewHolder
         * param convertView
         */
        BaseItemViewHolder initItemViewHolder(BASEVIEWHOLDER viewHolder, View convertView);

        
        /**用于将集合中的数据设置 item中 的每一个控件
         * param position
         */
        void bindListDataToView(Context context, List<BASEBEAN> iBaseBeanList, BASEVIEWHOLDER viewHolder, int position);
    }
