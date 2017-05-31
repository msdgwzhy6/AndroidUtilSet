package com.smart.androidutils.activity.dialog;

import android.app.Dialog;
import android.content.Context;

import com.smart.androidutils.BaseBean;
import com.smart.androidutils.CommonViewHolderHelper;
import com.smart.androidutils.R;
import com.smart.androidutils.viewholder.BaseGridViewHolder;
import com.util.dialog.OnDoubleBtnClickedListener;
import com.util.dialog.OnSingleBtnClickedListener;
import com.util.dialog.UtilDialogDouble;
import com.util.dialog.UtilDialogSingle;

import java.util.List;

import static com.smart.androidutils.constant.ConMainItem.MORE_TEXT_MSG;

/**
 * author xander on  2017/5/27.
 * function  对话框下的网格item的响应事件
 */

public class DialogViewHolerHelper extends CommonViewHolderHelper {
    @Override
    protected void setOnItemViewClickedCallback(Context context, List<BaseBean> iBaseBeanList, BaseGridViewHolder viewHolder, int position) {
        mItemName = iBaseBeanList.get(position).getName();
        if (mItemName.equals(context.getResources().getString(R.string.dialog_double))) {
            new UtilDialogDouble(mActivity)
                    .setDoubleBtnText("取消", "确定")
                    .setTitle("设置了背景色")
                    .setMessage(MORE_TEXT_MSG+MORE_TEXT_MSG+MORE_TEXT_MSG)
                    .setOutsideClickable(true)
                    .setOnDoubleBtnClickedListener(new OnDoubleBtnClickedListener() {
                        @Override
                        public void onLeftBtnClick(Dialog utilDialogDouble) {
                            utilDialogDouble.dismiss();
                        }
                        @Override
                        public void onRightBtnClick(Dialog utilDialogDouble) {
                             utilDialogDouble.dismiss();
                        }
                    }).show();
        }
        else if (mItemName.equals(context.getResources().getString(R.string.dialog_single))) {
            /*new UtilDialogDouble(mActivity).setTitle("默认背景色")
                    .setMessage("默认背景色")
                    .setSingleBtnTextR("one")
                    .setOnSingleClicedkListener(new OnSingleBtnClickedListener() {
                        @Override
                        public void onRightBtnClick(UtilDialogDouble utilDialogDouble) {
                            utilDialogDouble.dismiss();
                        }
                    })
                    .show();*/
            new UtilDialogSingle(mActivity)
                    .setTitle("设置了背景色")
                    .setMessage(MORE_TEXT_MSG+MORE_TEXT_MSG+MORE_TEXT_MSG)
                    .setOutsideClickable(true)
                    .setOnSingleClicedkListener(new OnSingleBtnClickedListener() {
                        @Override
                        public void onRightBtnClick(Dialog dialog) {
                            dialog.dismiss();
                        }
                    }).show();
        }
        else if (mItemName.equals(context.getResources().getString(R.string.dialog_download_data))) {
            String apkUrl = "http://hiao.com/android/bus/QingDaoBus.apk";
           /* new UtilDownloadUtilDialog(mActivity)
                    .setTitle("您确定要下载么？")
                    .setTitleBackgroundResId(R.mipmap.ic_launcher)
                    .setDownloadBtnText("取消", "确定")
                    .setInOutAnimationStyle(R.style.slide_left_right)
                    .setLeftBtnTextColor(R.color.white)
//                    .setDoubleBtnBackgroungRes(R.drawable.left_btn_bg, R.drawable.right_btn_bg)
                    .setMessage("cccccccc")
                    .setDownloadUrl(apkUrl, "")
                    .setClickedAnimation(true)
                    .setOnDownloadListener(new OnDownloadApkListener() {
                        @Override
                        public void result(UtilDialogDouble dialogCustom) {
                            dialogCustom.dismiss();
                        }

                        @Override
                        public void failure(Exception e, UtilDialogDouble dialogCustom) {
                            dialogCustom.dismiss();
                        }
                    }).show();*/
        }
        else if (mItemName.equals(context.getResources().getString(R.string.dialog_toast))) {
            /*new UtilDialogDouble(mActivity)
                    .setToast("发撒好方法放假还是发生了疯狂就爱疯了",2000)
                    .setToastDrawableId(R.drawable.dialog_bg)
                    .setDialogOffPos(null,0.2f)
                    .show();*/
        }
    }
}
