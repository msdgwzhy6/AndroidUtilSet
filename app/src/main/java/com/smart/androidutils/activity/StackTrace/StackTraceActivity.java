package com.smart.androidutils.activity.StackTrace;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdk.util.logger.JJLogger;
import com.smart.androidutils.BaseActivity;
import com.smart.androidutils.R;

import static com.sdk.util.view.UtilWidget.getView;

/**
 * @author xander on  2017/5/24.
 * @function
 */

public class StackTraceActivity extends BaseActivity {

    private LinearLayout mLinearLayout ;
    @Override
    protected int initLayout() {
        return R.layout.activity_default_layout;
    }

    @Override
    protected void initView() {
        mLinearLayout = getView(this,R.id.lnrlyt);
    }

    @Override
    protected void initData() {
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        JJLogger.i("call oncreate method");
        JJLogger.i("stacktrace len:" + stacktrace.length);
        for (int i = 0; i < stacktrace.length; i++) {
            JJLogger.i("----  the " + i + " element  ----toString: " + stacktrace[i].toString());
            JJLogger.i("ClassName: " + stacktrace[i].getClassName());
            JJLogger.i("FileName: " + stacktrace[i].getFileName());
            JJLogger.i("LineNumber: " + stacktrace[i].getLineNumber());
            JJLogger.i("MethodName: " + stacktrace[i].getMethodName());
        }
    }

    @Override
    protected void bindDataToView() {
        TextView textView = new TextView(this);

        textView.setRotationX(0.5f);
        textView.setTop(100);
        textView.setLeft(200);
        textView.setLayoutParams(new LinearLayout.LayoutParams(500,500));
        textView.setText("请看输出日志");

        mLinearLayout.addView(textView);
    }

    @Override
    protected void setActivityTitle() {
        super.setActivityTitle();
        mAvtivitytTitle = getString(R.string.StackTrace);
    }
}
