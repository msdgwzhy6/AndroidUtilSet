package com.sdk.util.viewholder;

import android.content.Context;

import com.sdk.util.viewholder.callback.IDataItemViewHolderHelper;
import com.sdk.util.viewholder.callback.IListDataViewHolderHelper;

import java.io.Serializable;
import java.util.List;

/**
 * author xander on  2017/6/16.
 * function
 */

@SuppressWarnings("unchecked")
public class MutilTypeAdapter <BEAN extends Serializable> extends CommonAdapter {


    public MutilTypeAdapter(Context context, int listDataSize,  IDataItemViewHolderHelper dataItemViewHolderHelper) {
        super(context, null, listDataSize, 0, dataItemViewHolderHelper);
    }

    public MutilTypeAdapter(Context context, IListDataViewHolderHelper iListDataViewHolderHelper) {
        super(context, null, 0, iListDataViewHolderHelper);
    }

    /*
    * 设置布局，依次把布局添加进来
    * */
    public MutilTypeAdapter setLayout(int [] itemViewLayouts){

        return this;
    }
    /*
    * 设置数据，依次把对应布局所需要的数据添加进来
    * */
    public MutilTypeAdapter setData(Serializable[] iBaseBean){

        return this;
    }
    /*
    * 设置数据，依次把对应布局所需要的数据添加进来
    * */
    public MutilTypeAdapter setDataList(List<BEAN> iBaseBeanList){

        return this;
    }
    @Override
    public int getItemViewType(int position) {
       /* if (mListDataViewHolderHelper != null) {
            return mListDataViewHolderHelper.getViewType(position);
        }else {
            return mDataItemViewHolderHelper.getViewType(position);
        }*/
        return 0;
    }

}
