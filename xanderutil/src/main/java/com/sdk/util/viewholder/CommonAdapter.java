package com.sdk.util.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.sdk.util.viewholder.callback.IBaseItemViewHolder;
import com.sdk.util.viewholder.callback.IDataItemViewHolderHelper;
import com.sdk.util.viewholder.callback.IListDataViewHolderHelper;

import java.io.Serializable;
import java.util.List;


/**
 * Created by xubinbin on 2017/4/24.
 * function 封装adapter，是viewholder和adapter分离
 *
 */

public class CommonAdapter<BEAN extends java.io.Serializable> extends BaseAdapter{
    protected final int mItemViewLayout;//item布局文件
    protected Context mContext;
    protected IBaseItemViewHolder mBaseViewHolder;
    protected IDataItemViewHolderHelper mDataItemViewHolderHelper;
    protected IListDataViewHolderHelper mListDataViewHolderHelper;
    protected Serializable mIBaseBean;
    protected List<BEAN> mIBaseBeanList;
    protected int listSize;

    /** 传过来一个数据实体类时，当你用Gson时，你可以不用写list
     * 直接将json数据，转换为bean对象；然后将bean对象传递进来
     * param context 上下文
     * param iBaseBean 数据集（内含list）
     * param itemViewLayout （item的布局文件）
     * param listDataSize(bean 中 包含的list的大小,如果该bean里含有list,则需将list的大小传递进来；
     * 如果没有，则传 1)
     * param dataItemViewHolderHelper （viewholder的接口）
     */
    public CommonAdapter(Context context, Serializable iBaseBean, int listDataSize, int itemViewLayout, IDataItemViewHolderHelper dataItemViewHolderHelper) {
        mContext = context;
        mIBaseBean = iBaseBean;
        mItemViewLayout = itemViewLayout;
        mDataItemViewHolderHelper = dataItemViewHolderHelper;
        listSize = listDataSize;
    }


    /**
     * param context 上下文
     * param iBaseBeanList 数据集（list的形式传递过来）
     * param itemViewLayout （item的布局文件）
     * param iListDataViewHolderHelper （viewholder的接口）
     */
    public CommonAdapter(Context context, List<BEAN> iBaseBeanList, int itemViewLayout, IListDataViewHolderHelper iListDataViewHolderHelper) {
        mContext = context;
        mIBaseBeanList = iBaseBeanList;
        mItemViewLayout = itemViewLayout;
        mListDataViewHolderHelper = iListDataViewHolderHelper;
    }

    @Override
    public int getCount() {
        return mIBaseBeanList==null?listSize:mIBaseBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    @SuppressWarnings("unchecked")
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mItemViewLayout,parent,false);
            if (mListDataViewHolderHelper == null) {
                mBaseViewHolder =  mDataItemViewHolderHelper.initItemViewHolder(mBaseViewHolder,convertView);
            }else {
                mBaseViewHolder =  mListDataViewHolderHelper.initItemViewHolder(mBaseViewHolder,convertView);
            }

            convertView.setTag(mBaseViewHolder);
        }else {
            mBaseViewHolder = (IBaseItemViewHolder)convertView.getTag();
        }
        if (mIBaseBeanList == null) {
            mDataItemViewHolderHelper.bindDataToView(mContext, mIBaseBean,mBaseViewHolder,position);
        }else {
            mListDataViewHolderHelper.bindListDataToView(mContext, mIBaseBeanList,mBaseViewHolder,position);
        }
        return convertView;
    }

}
