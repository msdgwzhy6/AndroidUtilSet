package com.util.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.util.R;

/**
 * author xander on  2017/6/1.
 * function  弹窗、单（不准备做这个了，需求不大）、双按钮对话框的基础类
 */

public class UtilDialogBase<T extends UtilDialogBase> extends Dialog {
    Resources res;//资源管理器
    T instance;//该基础类的实现类

    private Activity mActivity;
    private View parentView;//contentView

    /*
    * 消息
    */
    ScrollView mScrollViewMsg;//标题父布局
    TextView messageTv;//消息提示文本
    String messageStr="";//从外界设置的消息文本
    Integer mScrollViewSize;
    int messageTextSize;
    ColorStateList messageTextColor;
    Drawable messageDrawable;//消息的背景
    boolean clickedAnimation;//是否开启动画，即视觉交互效果，开启后，体验更佳
    private int inOutAnimationStyle = 0x00;//动画的样式
    long ANITIME = 100;//动画效果持续的时间
    private boolean outsideClickable  ;//设置对话框白色区域是否可以点击,默认可以点击
    private boolean cancelable = true;//返回键可用
    boolean dialogScale;//是否开启缩放
    Float scaleHeight = 0.3f;//相对于屏幕的宽度的缩放大小
    Float scaleWidth = 0.75f;
    Float offPosX;
    Float offPosY;
    private int layoutId;
    Drawable mBackgroundDrawable;//整个对话框的背景
    @SuppressWarnings("unchecked")
    UtilDialogBase(Activity activity, int layoutDialog) {
        super(activity, R.style.MyTechDialog);
        layoutId = layoutDialog;
        mActivity= activity;
        res = activity.getResources();
        instance = (T) this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //加载布局
        parentView = mActivity.getLayoutInflater().inflate(layoutId,null);
        setContentView(parentView);

        //实例化控件
        initMessageView();
        initDialog();
    }

    /*
    * 通過 id 获取 view 的实例
    * */
    @SuppressWarnings("unchecked")
    public <V extends View> V getView(int id){
        if (parentView == null) {
            throw new NullPointerException("parentView不能为空");
        }
        return (V) parentView.findViewById(id);
    }

    private void initMessageView() {
        /*
        * 消息
        * */
        mScrollViewMsg = getView(R.id.scroll_view_title);
        messageTv = (TextView) findViewById(R.id.message_tv);
    }
    /**
     * 初始化界面控件的显示数据
     */
    private void initDialog() {
        setCanceledOnTouchOutside(outsideClickable);
        setCancelable(cancelable);//调用这个方法时，按对话框以外的地方不起作用。按返回键也不起作用
        setInOutAnimation();
        setMessageView();
    }

    private void setInOutAnimation() {
        if (inOutAnimationStyle != 0x00) {
            Window dialogWindow = getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(inOutAnimationStyle);//设置对话框的进出效果
            }
        }

    }
    void setMessageView() {
        if (mScrollViewSize != null) {
            mScrollViewMsg.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,mScrollViewSize));
        }
        messageTv.setText(messageStr);
        messageTv.setGravity(Gravity.CENTER);
        //设置文字大小
        if (messageTextSize != 0) {
            messageTv.setTextSize(messageTextSize);
        }
        //设置文字颜色
        if (messageTextColor != null) {
            messageTv.setTextColor(messageTextColor);
        }

        // 2、如果用户自己设置了标题 则使用用户自己的标题
        if (mBackgroundDrawable !=null) {
            mScrollViewMsg.setBackground(mBackgroundDrawable);
        }
    }
    /*
  * 设置外部是否可以点击
  * */
    public T setOutsideClickable(boolean outsideClickable) {
        this.outsideClickable = outsideClickable;
        return instance;
    }

    /*
    * 禁用返回键
    * */
    public T setForbiddenBackKey(boolean cancelable) {
        this.cancelable = cancelable;
        return instance;
    }

}
