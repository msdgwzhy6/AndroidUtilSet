package com.util.dialog;

/**
 * author xander on  2017/5/27.
 * function
 */

interface IDialogCallback {
    /**
     * 设置title文字
     */
    IDialogCallback setTitle(String title);

    /*
    * 设置外部是否可以点击
    * */
    IDialogCallback setOutsideClickable(boolean outsideClickable);

    /*
    * 禁用返回键
    * */
    IDialogCallback setForbiddenBackKey(boolean cancelable);

    /*
    * 设置title文字大小
    * */
    IDialogCallback setTitleSize(int titleSize);
    /*
    *设置title文字颜色
    * */
    IDialogCallback setTitleColor(int titleTextColor) ;

    /**
     * drawableId :可以是drawable资源文件、color值、一张图片
     */
    IDialogCallback setTitleBackgroundResId(int drawableId);
    /**
     * 设置消息
     */
    IDialogCallback setMessage(String message);
    IDialogCallback setMessageTextSize(int messageSize);
    IDialogCallback setMessageTextColor(int messageTextColor);
    /*
    * 设置消息背景
    * */
    IDialogCallback setMessageBackgroundResId(int resId);

    /**
     * 设置按钮
     */
    IDialogCallback setSingleBtnText(String rightBtnStr);
    IDialogCallback setSingleBtnTextColor(int colorId);
    IDialogCallback setSingleBtnBackgroungRes(int leftBtnDrawableId);
    /*
    * 作为Toast使用的时候
    * */
    IDialogCallback setToastDrawableId(int drawableId);

    /*
    *设置Toast
    * */
    IDialogCallback setToast(String messageStr, long timeDelay);

    /*
    * 设置消息内容的容器高度
    * */

    IDialogCallback setMessageHight(int messageHight);


    IDialogCallback setClickedAnimation(boolean clickedAnimation);
    IDialogCallback setInOutAnimationStyle(int  inOutAnimationStyle);
    IDialogCallback setInnerInOutAnimationStyle(int  inOutAnimationStyle);


    /*按钮的监听器*/
    IDialogCallback setOnSingleClicedkListener(OnSingleBtnClickedListener singleBtnClickedListener);
    /*
    * 设置宽高的缩放范围
    * 默认情况下：宽度为屏幕的3/4 ；高度为屏幕的1/3
    * */
    IDialogCallback setDialogScale(Float scaleWidth, Float scaleHeight);

    IDialogCallback setDialogOffPos(Float offPosX, Float offPosY);
    IDialogCallback setDoubleBtnText(String leftBtnStr, String rightBtnStr);
}
