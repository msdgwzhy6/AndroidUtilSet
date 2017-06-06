package com.util.dialog;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.util.R;


/**
 * Created by xu on 2017/3/3.
 */
public class UtilDialogDouble extends UtilDialogBase<UtilDialogDouble> {



    /*
    * 标题
     */
    private LinearLayout mLinearLayoutTitle;//标题父布局
    private TextView titleTv;//消息标题文本
    private ColorStateList titleTextColor;//标题字体颜色
    private String titleStr;//从外界设置的title文本
    private int titleSize;//标题大小
    private Drawable titleDrawable;//标题的背景


    private Button rightBtn;
    private Button leftBtn;
    private String rightBtnStr, leftBtnStr;
    private ColorStateList rightBtnColor;
    private ColorStateList  leftBtnColor;
    private Drawable rightBtnDrawable;//右侧按钮的背景
    private Drawable leftBtnDrawable;//左侧按钮的背景

    LinearLayout linearLayout_btn;
    /*
    * 监听器
    * */
    private OnDoubleBtnClickedListener doubleBtnClickedListener;//左右侧按钮被点击了的监听器

    public UtilDialogDouble(Activity activity) {
        super(activity, R.layout.layout_dialog_button);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitle();
        initButon();


        setTitleView();
        initMessageView();
        initListener();
    }

    private void initButon() {
        /*
        * 按钮
        * */
        linearLayout_btn = getView(R.id.ll_double_btn);
        rightBtn = getView(R.id.btn_right);
        leftBtn = getView(R.id.btn_left);

          /*
        * 如果没有设置左侧和右侧按钮的文字
        * 则使用默认 文字
        * */
        if (!TextUtils.isEmpty(leftBtnStr)&&!TextUtils.isEmpty(rightBtnStr)) {//两个都有
            leftBtn.setText(leftBtnStr);
            rightBtn.setText(rightBtnStr);
        }
        if (leftBtnColor != null) {
            leftBtn.setTextColor(leftBtnColor);
        }
        if (leftBtnDrawable!=null) {
            leftBtn.setBackground(leftBtnDrawable);
        }
        if (rightBtnColor != null) {
            rightBtn.setTextColor(rightBtnColor);
        }
        if (rightBtnDrawable!=null) {
            rightBtn.setBackground(rightBtnDrawable);
        }
    }

    /**
     * 初始化界面控件的控件
     */
    void initTitle() {
        /*
        * 标题
        * */
        mLinearLayoutTitle = getView(R.id.linear_layout_title);
        titleTv = getView(R.id.title);
        mLinearLayoutTitle.setVisibility(View.GONE);

    }
    /*
      * 设置标题信息
      * */
    void setTitleView() {

        if (!TextUtils.isEmpty(titleStr)) {
            mLinearLayoutTitle.setVisibility(View.VISIBLE);
            titleTv.setText(titleStr);
        }
        /*
        * 设置title文字大小
        * */
        if (titleSize != 0) {
            titleTv.setTextSize(titleSize);
        }
        /*
        * 设置title字体颜色
        * */
        if (titleTextColor != null) {
            titleTv.setTextColor(titleTextColor);
        }
        /*
        * 设置title背景
        * */
        if (titleDrawable!=null) {
            titleTv.setBackground(titleDrawable);
        }
    }

    /*
    * 设置消息信息
    * */
    void initMessageView() {

        if (mScrollViewSize != null) {
            mScrollViewMsg.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,mScrollViewSize));
        }
        /*
        * 如果没有消息内容，则抛出异常
        * */
        if (TextUtils.isEmpty(messageStr)) {
            messageStr = "您为配置信息！";
        }
        messageTv.setText(messageStr);
        messageTv.setGravity(Gravity.CENTER);
        //设置文字颜色
        if (messageTextSize != 0) {
            messageTv.setTextSize(messageTextSize);
        }
        //设置文字颜色
        if (messageTextColor != null) {
            messageTv.setTextColor(messageTextColor);
        }

        /*
        * 设置消息背景
        * */
        //  如果没有标题 则使用默认的无标题时的背景1：上面圆角，下面直角 ;

        if (mLinearLayoutTitle.getVisibility()!=View.VISIBLE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                messageTv.setBackground(getDrawable(R.drawable.message_default_bg1));
            }else {
                messageTv.setBackground(getDrawable(R.drawable.message_default_bg1));
            }
        }
        // 2、如果用户自己设置了标题 则使用用户自己的标题
        if (messageDrawable!=null) {
            mScrollViewMsg.setBackground(messageDrawable);
//            titleMsgLine.setVisibility(View.GONE);
        }

    }


    private Drawable getDrawable(int resId){
        Drawable drawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = res.getDrawable(resId,null);
        }else {
            drawable = res.getDrawable(resId);
        }
        return drawable;
    };
    /**
     * 设置title文字
     */
    public UtilDialogDouble setTitle(String title) {
        titleStr = title;
        return instance;
    }

    /**
     * 设置消息
     */
    public UtilDialogDouble setMessage(String message) {
        if (message != null) {
            messageStr = message;
        }
        return this;
    }
    /*
    * 设置title文字大小
    * */
    public UtilDialogDouble setTitleSize(int titleSize){
        this.titleSize = titleSize;
        return instance;
    }
    /*
    *设置title文字颜色
    * */
    public UtilDialogDouble setTitleColor(int titleTextColor) {
        this.titleTextColor =  res.getColorStateList(titleTextColor);
        return instance;
    }

    /**
     * drawableId :可以是drawable资源文件、color值、一张图片
     */
    public UtilDialogDouble setTitleBackgroundResId(int drawableId){
        titleDrawable = res.getDrawable(drawableId);
        return instance;
    }

    /**
     * 初始化界面的监听器
     */
    private void initListener() {
        //设置确定按钮被点击后，向外界提供监听
        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (doubleBtnClickedListener != null) {
                    if (clickedAnimation) {
                        // 透明度变化
                        AlphaAnimation alphaAni = new AlphaAnimation(0.05f, 1.0f);
                        alphaAni.setDuration(ANITIME);    // 设置动画效果时间
                        v.startAnimation(alphaAni);        // 添加光效动画到VIew

                        alphaAni.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }
                            @Override
                            public void onAnimationEnd(Animation animation) {
                                doubleBtnClickedListener.onRightBtnClick(instance);
                            }
                            @Override
                            public void onAnimationRepeat(android.view.animation.Animation animation) {

                            }
                        });
                    }else {
                        doubleBtnClickedListener.onRightBtnClick(instance);
                    }
                }
            }
        });
        //设置取消按钮被点击后，向外界提供监听接口
        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (doubleBtnClickedListener != null) {
                    if (clickedAnimation) {
                        // 透明度变化
                        AlphaAnimation alphaAni = new AlphaAnimation(0.05f, 1.0f);
                        alphaAni.setDuration(ANITIME);      // 设置动画效果时间
                        v.startAnimation(alphaAni);        // 添加光效动画到VIew

                        alphaAni.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                            }
                            @Override
                            public void onAnimationEnd(Animation animation) {
                                if (doubleBtnClickedListener != null) {//左侧按钮事件
                                    doubleBtnClickedListener.onLeftBtnClick(instance);
                                }

                            }
                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    }else {
                        doubleBtnClickedListener.onLeftBtnClick(instance);
                    }
                }
            }
        });
    }


    /*
    * 两个按钮的监听器*/
    public UtilDialogDouble setOnDoubleBtnClickedListener(OnDoubleBtnClickedListener doubleBtnClickedListener){
        this.doubleBtnClickedListener = doubleBtnClickedListener;
        return this;
    }


    /**
     * 设置按钮
     */
    public UtilDialogDouble setDoubleBtnText(String leftBtnStr, String rightBtnStr) {
        this.leftBtnStr = leftBtnStr;
        this.rightBtnStr = rightBtnStr;
        return this;
    }
    public UtilDialogDouble setLeftBtnTextColor(int colorId){
        this.leftBtnColor = res.getColorStateList(colorId);
        return this;
    }
    public UtilDialogDouble setRightBtnTextColor(int colorId){
        this.rightBtnColor = res.getColorStateList(colorId);
        return this;
    }
    public UtilDialogDouble setRightBtnBackgroungRes(int rightBtnDrawableId){
        rightBtnDrawable = res.getDrawable(rightBtnDrawableId);
        return this;
    }
    public UtilDialogDouble setLeftBtnBackgroungRes(int leftBtnDrawableId){
        leftBtnDrawable = res.getDrawable(leftBtnDrawableId);
        return this;
    }

    public UtilDialogDouble setDoubleBtnBackgroungRes(Integer leftBtnDrawableId, Integer rightBtnDrawableId) {
        if (leftBtnDrawableId!=null) {
            leftBtnDrawable = res.getDrawable(leftBtnDrawableId);
        }
        if (rightBtnDrawableId != null) {
            rightBtnDrawable = res.getDrawable(rightBtnDrawableId);
        }

        return this;
    }

}
