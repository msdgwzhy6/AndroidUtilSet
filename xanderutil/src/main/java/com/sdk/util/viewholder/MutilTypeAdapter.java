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


    public MutilTypeAdapter(Context context, Serializable iBaseBean, int listDataSize, int itemViewLayout, IDataItemViewHolderHelper dataItemViewHolderHelper) {
        super(context, iBaseBean, listDataSize, itemViewLayout, dataItemViewHolderHelper);
    }

    public MutilTypeAdapter(Context context, List<BEAN> iBaseBeanList, int itemViewLayout, IListDataViewHolderHelper iListDataViewHolderHelper) {
        super(context, iBaseBeanList, itemViewLayout, iListDataViewHolderHelper);
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
