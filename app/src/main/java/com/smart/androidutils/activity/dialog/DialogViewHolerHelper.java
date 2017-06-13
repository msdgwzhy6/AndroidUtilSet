package com.smart.androidutils.activity.dialog;

import android.app.Dialog;
import android.content.Context;

import com.smart.androidutils.BaseBean;
import com.smart.androidutils.ComHolderHelper;
import com.smart.androidutils.R;
import com.smart.androidutils.viewholder.BaseGridViewHolder;
import com.sdk.util.dialog.OnDoubleBtnClickedListener;
import com.sdk.util.dialog.UtilDialogDouble;
import com.sdk.util.dialog.UtilDialogToast;

import java.util.List;

import static com.smart.androidutils.constant.ConMainItem.MORE_TEXT_MSG;

/**
 * author xander on  2017/5/27.
 * function  对话框下的网格item的响应事件
 */

public class DialogViewHolerHelper extends ComHolderHelper {
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
        else if (mItemName.equals(context.getResources().getString(R.string.dialog_toast))) {
            new UtilDialogToast(mActivity)
                    .setToast("发撒好方法放假还是发生了疯狂就爱疯了",2000)
                    .show();
        }
    }
}
