package com.util.dialog;
import android.app.Activity;
import android.app.Dialog;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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

import com.util.R;

import static com.util.dialog.Constant.ANIM_FADE_IN_OUT;
import static com.util.dialog.Constant.ANIM_HYPERSPACE_IN_OUT;
import static com.util.dialog.Constant.ANIM_PUSH_LEFT_IN_OUT;
import static com.util.dialog.Constant.ANIM_PUSH_UP_IN_OUT;
import static com.util.dialog.Constant.ANIM_SCALE_IN_ALPHA_OUT;
import static com.util.dialog.Constant.ANIM_SLIDE_LEFT_RIGHT;
import static com.util.dialog.Constant.ANIM_SLIDE_UP_DOWN;


/**
 * author xander on  2017/3/3.
 * function  此类准备废除
 */
@Deprecated
public class UtilDialogSingle<T extends UtilDialogSingle> extends Dialog {
    protected Resources res;
    Activity mContext;
    T instance;

    View parentView;
    /*
    * dialog的一些属性
    * */
    boolean clickedAnimation;//是否开启动画，即视觉交互效果，开启后，体验更佳
    private int inOutAnimationStyle = 0x00;//动画的样式
    long ANITIME = 100;//动画效果持续的时间

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
    LinearLayout linearLayout_btn;

    private Button singleBtn;
    private String singleBtnStr;
    private ColorStateList singleBtnColor;
    private Drawable singleBtnDrawable;//右侧按钮的背景


    /*
    * 作为toast
    * */
    private Drawable toastDrawable;//整个对话框的背景
    private boolean notToast = true;//是否作为toast显示，默认为“不作为”,以是否缩放为主，其为次


    /*
    * 监听器
    * */
    private OnSingleBtnClickedListener singleBtnClickedListener;//左侧按钮被点击了的监听器


    @Deprecated
    public UtilDialogSingle(Activity context) {
        this(context, R.style.MyTechDialog);
    }

    @SuppressWarnings("unchecked")
    private UtilDialogSingle(Activity context, int themeResId) {
        super(context, themeResId);
        mContext = context;
        res = mContext.getResources();
        instance = (T) this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        parentView = mContext.getLayoutInflater().inflate(R.layout.layout_dialog_single,null);
        setContentView(parentView);

        initDialog();

        initIitltAndMessageView();

        initButon();



        initTitleView();
        initMessageView();

        setSingleBtnView();

        initToast();

        initListener();
    }

    private void initToast() {
        if (dialogScale) {//如果开启缩放,则以缩放为主
            notToast = true;
        }
         /*
        * 非吐司的情况下
        * 控制显示的宽高
        * */
        if (notToast) {
            Window dialogWindow = getWindow();
            WindowManager windowManager = mContext.getWindowManager();
            DisplayMetrics dm = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(dm);
            int screenWidth = dm.widthPixels;
            int screenHeight = dm.heightPixels;
            WindowManager.LayoutParams winlp; // 获取对话框当前的参数值
            if (dialogWindow != null) {
                winlp = dialogWindow.getAttributes();
                winlp.width = (int) (screenWidth * scaleWidth); // 宽度
                winlp.height = (int) (screenHeight * scaleHeight);// 高度

                if (offPosX != null) {
                    winlp.x = (int) (screenWidth*offPosX);
                }
                if (offPosY != null) {
                    winlp.y = (int) (screenHeight*offPosY);
                }
                dialogWindow.setAttributes(winlp);
            }

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
    private void setSingleBtnView() {

         /*
            * 这种情况下可能为toast，则将布局设置设置为弹窗布局
            * */
        if (toastDrawable == null) {
            mLinearLayoutTitle.setBackground(res.getDrawable(R.drawable.toast_dialog_bg));
        }else {
            mLinearLayoutTitle.setBackground(toastDrawable);
        }
        if (TextUtils.isEmpty(singleBtnStr)) {
            linearLayout_btn.setVisibility(View.GONE);

        }else if (!TextUtils.isEmpty(singleBtnStr)) {//单个按钮
            linearLayout_btn.setVisibility(View.VISIBLE);
            singleBtn.setVisibility(View.VISIBLE);

            singleBtn.setBackground(singleBtnDrawable);
            singleBtn.setGravity(Gravity.CENTER);//按钮设置居中
            singleBtn.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                    , ViewGroup.LayoutParams.MATCH_PARENT));//匹配父布局

            singleBtn.setText(singleBtnStr);
            if (singleBtnColor != null) {
                singleBtn.setTextColor(singleBtnColor);
            }
            if (singleBtnDrawable !=null) {
                singleBtn.setBackground(singleBtnDrawable);
            }
        }

    }


    /**
     * 初始化界面的监听器
     */
    private void initListener() {
        //设置确定按钮被点击后，向外界提供监听
        singleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (singleBtnClickedListener!= null) {
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
                                if (singleBtnClickedListener!= null) {//执行单个按钮的对话框操作
                                    singleBtnClickedListener.onRightBtnClick(instance);
                                }
                            }
                            @Override
                            public void onAnimationRepeat(android.view.animation.Animation animation) {

                            }
                        });
                    }else {
                        singleBtnClickedListener.onRightBtnClick(instance);
                    }
                }
            }
        });

    }
    /*
    * 设置按钮
    * 本例为单个按钮
    * */
    void initButon() {

        linearLayout_btn = (LinearLayout) findViewById(R.id.ll_single_btn);
//        linearLayout_btn.setVisibility(View.GONE);
        /*
        * 单个按钮
        * */
        singleBtn = (Button) findViewById(R.id.btn_single);
        singleBtn.setVisibility(View.GONE);
    }

    /*
    * 设置进入退出效果
    * */
    private void setInOutAnimation() {
        if (inOutAnimationStyle != 0x00) {
            Window dialogWindow = getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(inOutAnimationStyle);//设置对话框的进出效果
            }
        }
    }

    /**
     * 初始化界面控件的控件
     */
    void initIitltAndMessageView() {
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
    }

    /*
    * 通過 id 获取 view 的实例
    * */
    @SuppressWarnings("unchecked")
    <V extends View> V getView(int id){
        if (parentView == null) {
            throw new NullPointerException("parentView不能为空");
        }
        return (V) parentView.findViewById(id);
    }

    /**
     * 初始化对话框
     * 设置外部是否可以点击（点击外部可以消失）
     * 设置是否屏蔽对话框以外的事件
     * 设置进入退出效果
     */
    void initDialog() {

        setCanceledOnTouchOutside(outsideClickable);
        setCancelable(cancelable);//调用这个方法时，按对话框以外的地方不起作用。按返回键也不起作用
        setInOutAnimation();
    }

    /*
    * 设置标题信息
    * */
    void initTitleView() {

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



    /**
     * 设置title文字
     */
    public T setTitle(String title) {
        titleStr = title;
        return instance;
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

    /*
    * 设置title文字大小
    * */
    public T setTitleSize(int titleSize){
        this.titleSize = titleSize;
        return instance;
    }
    /*
    *设置title文字颜色
    * */
    public T setTitleColor(int titleTextColor) {
        this.titleTextColor =  res.getColorStateList(titleTextColor);
        return instance;
    }

    /**
     * drawableId :可以是drawable资源文件、color值、一张图片
     */
    public T setTitleBackgroundResId(int drawableId){
        titleDrawable = res.getDrawable(drawableId);
        return instance;
    }

    /**
     * 设置消息
     */
    public T setMessage(String message) {
        if (message != null) {
            messageStr = message;
        }
        return instance;
    }
    public T setMessageTextSize(int messageSize){
        this.messageTextSize = messageSize;
        return instance;
    }
    public T setMessageTextColor(int messageTextColor){
        this.messageTextColor =  res.getColorStateList(messageTextColor);
        return instance;
    }

    /*
    * 设置消息背景
    * */
    public T setMessageBackgroundResId(int resId){
        messageDrawable = res.getDrawable(resId);
        return instance;
    }

    /**
     * 设置按钮
     */
    public T setSingleBtnText(String rightBtnStr) {
        this.singleBtnStr = rightBtnStr;
        return instance;
    }
    public T setSingleBtnTextColor(int colorId){
        this.singleBtnColor = res.getColorStateList(colorId);
        return instance;
    }
    public T setSingleBtnBackgroungRes(int leftBtnDrawableId) {
        singleBtnDrawable = res.getDrawable(leftBtnDrawableId);
        return instance;
    }

    /*
    * 作为Toast使用的时候
    * */
    public T setToastDrawableId(int drawableId){
        toastDrawable = res.getDrawable(drawableId);
        return instance;
    }

    /*
    *设置Toast
    * */
    public T setToast(String messageStr, long timeDelay) {
        titleStr = "";
        singleBtnStr = "";
        notToast = false;//是toast,则要调整相应的布局
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


    /*
    * 设置消息内容的容器高度
    * */

     public T setMessageHight(int messageHight) {
        mScrollViewSize = messageHight;
        return instance;
    }


    public T setClickedAnimation(boolean clickedAnimation){
        this.clickedAnimation = clickedAnimation;
        return instance;
    }
    public T setInOutAnimationStyle(int  inOutAnimationStyle){
        switch (inOutAnimationStyle){
            case ANIM_SLIDE_UP_DOWN:
                this.inOutAnimationStyle = R.style.slide_up_down;
                break;
            case ANIM_FADE_IN_OUT:
                this.inOutAnimationStyle = R.style.fade_in_out;
                break;
            case ANIM_SCALE_IN_ALPHA_OUT:
                this.inOutAnimationStyle = R.style.scale_in_alpha_out;
                break;
            case ANIM_HYPERSPACE_IN_OUT:
                this.inOutAnimationStyle = R.style.hyperspace_in_out;
                break;
            case ANIM_PUSH_LEFT_IN_OUT:
                this.inOutAnimationStyle = R.style.push_left_in_out;
                break;
            case ANIM_PUSH_UP_IN_OUT:
                this.inOutAnimationStyle = R.style.push_up_in_out;
                break;
            case ANIM_SLIDE_LEFT_RIGHT:
                this.inOutAnimationStyle = R.style.slide_left_right;
                break;
            default:
                this.inOutAnimationStyle = inOutAnimationStyle;
                break;
        }

        return instance;
    }
    public T setInnerInOutAnimationStyle(int  inOutAnimationStyle){
        this.inOutAnimationStyle = inOutAnimationStyle;
        return instance;
    }


    /*按钮的监听器*/
    public T setOnSingleClicedkListener(OnSingleBtnClickedListener singleBtnClickedListener){
        this.singleBtnClickedListener = singleBtnClickedListener;
        return  instance;
    }
    /*
    * 设置宽高的缩放范围
    * 默认情况下：宽度为屏幕的3/4 ；高度为屏幕的1/3
    * */
    public T setDialogScale(Float scaleWidth, Float scaleHeight){
        dialogScale = true;
        if (scaleWidth != null) {
            this.scaleWidth = scaleWidth;
        }
        if (scaleHeight != null) {
            this.scaleHeight = scaleHeight;
        }
        return instance;
    }

    public T setDialogOffPos(Float offPosX, Float offPosY){
        if (offPosX != null) {
            this.offPosX = offPosX;
        }
        if (offPosY != null) {
            this.offPosY = offPosY;
        }
        return instance;
    }

}