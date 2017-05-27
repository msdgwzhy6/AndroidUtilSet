package com.util.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.util.R;

/**
 * Created by xu on 2017/3/3.
 */
public class UtilDialogDouble extends Dialog {

    private final Resources res;
    private Activity mContext;
    private UtilDialogDouble instance;

    private View parentView;
    /*
    * dialog的一些属性
    * */
    private boolean clickedAnimation;//是否开启动画，即视觉交互效果，开启后，体验更佳
    private int inOutAnimationStyle = 0x00;//动画的样式
    private long ANITIME = 100;//动画效果持续的时间

    private boolean outsideClickable  ;//设置对话框白色区域是否可以点击,默认可以点击
    private boolean cancelable = true;//返回键可用

    private boolean dialogScale;//是否开启缩放
    private Float scaleHeight = 0.3f;//相对于屏幕的宽度的缩放大小
    private Float scaleWidth = 0.75f;

    private Float offPosX;
    private Float offPosY;


    /*
    * 标题
     */
    private LinearLayout mLinearLayoutTitle;//标题父布局
    private TextView titleTv;//消息标题文本
    private ColorStateList titleTextColor;//标题字体颜色
    private String titleStr;//从外界设置的title文本
    private int titleSize;//标题大小
    private Drawable titleDrawable;//标题的背景

    /*
    * 消息
    */
    private ScrollView mScrollViewMsg;//标题父布局
    private TextView messageTv;//消息提示文本
    private String messageStr="";//从外界设置的消息文本

    private Integer mScrollViewSize;
    private int messageTextSize;
    private ColorStateList messageTextColor;
    private Drawable messageDrawable;//消息的背景
    /*
    * 按钮
    * */
    private LinearLayout linearLayout_btn;
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
        super(context, R.style.MyTechDialog);
        mContext = context;
        res = mContext.getResources();
        instance = this;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentView = mContext.getLayoutInflater().inflate(R.layout.layout_dialog,null);
        setContentView(parentView);
        initView();
        initDialog();
        initListener();
    }

    private void setInOutAnimation() {
        if (inOutAnimationStyle != 0x00) {
            Window dialogWindow = getWindow();
            dialogWindow.setWindowAnimations(inOutAnimationStyle);//设置对话框的进出效果
        }
    }


    /**
     * 初始化界面控件,并设置界面控件不可见，是否可见，要根据用户的配置值
     */
    private void initView() {
        /*
        * 标题
        * */
        mLinearLayoutTitle = getView(R.id.linear_layout_title);
        titleTv = getView(R.id.title);
        mLinearLayoutTitle.setVisibility(View.GONE);

        /*
        * 消息
        * */
        mScrollViewMsg = getView(R.id.scroll_view_title);
        messageTv = getView(R.id.message_tv);


        /*
        * 按钮
        * */
        linearLayout_btn = getView(R.id.ll_btn);
        rightBtn = getView(R.id.btn_right);
        leftBtn = getView(R.id.btn_left);

    }


    private <V extends View> V getView(int id){
        return (V) parentView.findViewById(id);
    }

    /**
     * 初始化界面控件的显示数据
     */
    private void initDialog() {
        setCanceledOnTouchOutside(outsideClickable);
        setCancelable(cancelable);//调用这个方法时，按对话框以外的地方不起作用。按返回键也不起作用
        setInOutAnimation();

        setTitleView();
        setMessageView();
        setBtnView();
//        adjustWH();
    }

    private void adjustWH() {
        Window dialogWindow = getWindow();
        WindowManager windowManager = mContext.getWindowManager();
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        WindowManager.LayoutParams winlp = null; // 获取对话框当前的参数值
        if (dialogWindow != null) {
            winlp = dialogWindow.getAttributes();
        }
        if (winlp != null) {
            winlp.width = (int) (screenWidth * scaleWidth); // 宽度
            winlp.height = (int) (screenHeight * scaleHeight);// 高度
        }
        if (offPosX != null) {
            if (winlp != null) {
                winlp.x = (int) (screenWidth*offPosX);
            }
        }
        if (offPosY != null) {
            if (winlp != null) {
                winlp.y = (int) (screenHeight*offPosY);
            }
        }

        if (dialogWindow != null) {
            dialogWindow.setAttributes(winlp);
        }
    }

    private void setTitleView() {

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
    private void setMessageView() {

        if (mScrollViewSize != null) {
            mScrollViewMsg.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,mScrollViewSize));
        }
        /*
        * 如果没有消息内容，则抛出异常
        * */
        if (TextUtils.isEmpty(messageStr)) {
            Toast.makeText(mContext, "您的消息为空！", Toast.LENGTH_SHORT).show();
           throw new NullPointerException("使用 UtilDialogDouble 时，消息不能为空！");
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
    private void setBtnView() {
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
    * 设置宽高的缩放范围
    * 默认情况下：宽度为屏幕的3/4 ；高度为屏幕的1/3
    * */
    public UtilDialogDouble setDialogScale(Float scaleWidth, Float scaleHeight){
        dialogScale = true;
        if (scaleWidth != null) {
            this.scaleWidth = scaleWidth;
        }
        if (scaleHeight != null) {
            this.scaleHeight = scaleHeight;
        }
        return this;
    }

    public UtilDialogDouble setDialogOffPos(Float offPosX, Float offPosY){
        if (offPosX != null) {
            this.offPosX = offPosX;
        }
        if (offPosY != null) {
            this.offPosY = offPosY;
        }
        return this;
    }

    public UtilDialogDouble setClickedAnimation(boolean clickedAnimation){
        this.clickedAnimation = clickedAnimation;
        return this;
    }
    public UtilDialogDouble setInOutAnimationStyle(int  inOutAnimationStyle){
        switch (inOutAnimationStyle){
            case DragonConstant.ANIM_SLIDE_UP_DOWN:
                this.inOutAnimationStyle = R.style.slide_up_down;
                break;
            case DragonConstant.ANIM_FADE_IN_OUT:
                this.inOutAnimationStyle = R.style.fade_in_out;
                break;
            case DragonConstant.ANIM_SCALE_IN_ALPHA_OUT:
                this.inOutAnimationStyle = R.style.scale_in_alpha_out;
                break;
            case DragonConstant.ANIM_HYPERSPACE_IN_OUT:
                this.inOutAnimationStyle = R.style.hyperspace_in_out;
                break;
            case DragonConstant.ANIM_PUSH_LEFT_IN_OUT:
                this.inOutAnimationStyle = R.style.push_left_in_out;
                break;
            case DragonConstant.ANIM_PUSH_UP_IN_OUT:
                this.inOutAnimationStyle = R.style.push_up_in_out;
                break;
            case DragonConstant.ANIM_SLIDE_LEFT_RIGHT:
                this.inOutAnimationStyle = R.style.slide_left_right;
                break;
            default:
                this.inOutAnimationStyle = inOutAnimationStyle;
                break;
        }

        return this;
    }
    public UtilDialogDouble setInnerInOutAnimationStyle(int  inOutAnimationStyle){
        this.inOutAnimationStyle = inOutAnimationStyle;
        return this;
    }

    /*
    * 两个按钮的监听器*/
    public UtilDialogDouble setOnDoubleBtnClickedListener(OnDoubleBtnClickedListener doubleBtnClickedListener){
        this.doubleBtnClickedListener = doubleBtnClickedListener;
        return this;
    }

    /*
    * 设置消息内容的容器高度
    * */
    public UtilDialogDouble setMessageHight(int messageHight) {
        mScrollViewSize = messageHight;
        return instance;
    }
    /*
    * 设置外部是否可以点击取消
    * */
    public UtilDialogDouble setOutsideClickable(boolean outsideClickable) {
        this.outsideClickable = outsideClickable;
        return instance;
    }
    public UtilDialogDouble setForbiddenBackKey(boolean cancelable) {
        this.cancelable = cancelable;
        return instance;
    }
    /**
     * 设置title文字
     */
    public UtilDialogDouble setTitle(String title) {
        titleStr = title;
        return this;
    }

    /*
    * 设置title文字大小
    * */
    public UtilDialogDouble setTitleSize(int titleSize){
        this.titleSize = titleSize;
        return this;
    }
    /*
    *设置title文字颜色
    * */
    public UtilDialogDouble setTitleColor(int titleTextColor) {
        this.titleTextColor =  res.getColorStateList(titleTextColor);
        return this;
    }

    /**
     * drawableId :可以是drawable资源文件、color值、一张图片
     */
    public UtilDialogDouble setTitleBackgroundResId(int drawableId){
        titleDrawable = res.getDrawable(drawableId);
        return this;
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
    public UtilDialogDouble setMessageTextSize(int messageSize){
        this.messageTextSize = messageSize;
        return this;
    }
    public UtilDialogDouble setMessageTextColor(int messageTextColor){
        this.messageTextColor =  res.getColorStateList(messageTextColor);
        return this;
    }

    /*
    * 设置消息背景
    * */
    public UtilDialogDouble setMessageBackgroundResId(int resId){
        messageDrawable = res.getDrawable(resId);
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
