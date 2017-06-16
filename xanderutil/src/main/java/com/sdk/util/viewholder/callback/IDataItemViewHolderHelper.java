package com.sdk.util.viewholder.callback;

import android.content.Context;
import android.view.View;

/*
    * 当你的数据只有一个bean对象而不是一个list的时候，你的viewholderhelper需要实现这个接口
    * */
    public interface IDataItemViewHolderHelper<BASEVIEWHOLDER extends BaseItemViewHolder, BASEBEAN extends java.io.Serializable>  extends BaseItemViewHolder {
        /** 用于初始化ViewHolder
         * param convertView
         */
        BaseItemViewHolder initItemViewHolder(BASEVIEWHOLDER viewHolder, View convertView);

        /**用于设置 item中 的每一个控件
         * param position
         */
        void bindDataToView(Context context, BASEBEAN basebean, BASEVIEWHOLDER viewHolder, int position);
    }
