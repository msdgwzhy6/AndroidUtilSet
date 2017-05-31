package com.util.dialog;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;

import com.util.R;

/**
 * Created by xu on 2017/3/3.
 */
public class UtilDialogDouble extends UtilDialogSingle<UtilDialogDouble>{

    private Button rightBtn;
    private Button leftBtn;
    private String rightBtnStr, leftBtnStr;
    private ColorStateList rightBtnColor;
    private ColorStateList  leftBtnColor;
    private Drawable rightBtnDrawable;//右侧按钮的背景
    private Drawable leftBtnDrawable;//左侧按钮的背景


    /*
    * 监听器
    * */
    private OnDoubleBtnClickedListener doubleBtnClickedListener;//左右侧按钮被点击了的监听器

    public UtilDialogDouble(Activity context) {
        this(context, R.style.MyTechDialog);
    }

    public UtilDialogDouble(Activity context, int themeResId) {
        super(context, themeResId);
        
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        parentView = mContext.getLayoutInflater().inflate(R.layout.layout_dialog_button,null);
        setContentView(parentView);

        initIitltAndMessageView();

        initButon();

        initTitleView();
        initMessageView();
        initDialog();
        initListener();

        initDialog();
        initDoubleBtnView();
        initListener();
    }
    @Override
    protected void initButon() {
        /*
        * 按钮
        * */
        linearLayout_btn = getView(R.id.ll_double_btn);
        rightBtn = getView(R.id.btn_right);
        leftBtn = getView(R.id.btn_left);
    }


    private void initDoubleBtnView() {
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
