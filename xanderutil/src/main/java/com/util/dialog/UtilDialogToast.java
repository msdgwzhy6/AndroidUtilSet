package com.util.dialog;

import android.app.Activity;
import android.os.Handler;
import android.text.TextUtils;

import com.util.R;

/**
 * author xander on  2017/6/1.
 * function
 */


public class UtilDialogToast extends UtilDialogBase<UtilDialogToast> {

    @SuppressWarnings("unchecked")
    public UtilDialogToast(Activity activity) {
        super(activity,R.layout.layout_dialog_base);
        mBackgroundDrawable = res.getDrawable(R.drawable.toast_dialog_bg);
    }


    /*
    *设置Toast
    * */
    public UtilDialogToast setToast(String messageStr, long timeDelay) {
   
        if (TextUtils.isEmpty(messageStr)) {
            this.messageStr = "没有提示信息！";
        }else {
            this.messageStr = messageStr;
        }
        if (timeDelay<1000) {
            timeDelay = 1000;
        }

        new Handler().postDelayed(new Runnable(){
            public void run() {
                instance.dismiss();
            }
        }, timeDelay);

        return instance;
    }

}
