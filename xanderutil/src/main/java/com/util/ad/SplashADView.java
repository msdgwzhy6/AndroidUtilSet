package com.util.ad;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.util.R;
import com.util.core.InitSDK;
import com.util.file.UtilSPOnlyPead;
import com.util.file.UtilSPSingleInstance;
import com.util.http.UtilHttpBitmap;
import com.util.http.core.callback.IBitmapCallback;

import static com.util.UtilEncript.getMD5;
import static com.util.common.UtilCommon.useWebView;

/**
 * author xander on  2017/5/31.
 * function 提供一个view 供外界使用，缓存图片，从本地拿图片
 *  view : 一张图片，一个连接
 *  如果要做成轮播，就把控件改为轮播
 *
 */

public final class SplashADView implements ExitButtonListener{
    private final String TAG = this.getClass().getSimpleName();

    private ViewGroup mViewGroup;
    private ImageView mImageView;
    private SplashViewCallback mSplashViewCallback;
    private static SplashADView instance;
    private boolean bClickedAd;//是否点击了广告

    private final String url = "http://img.mukewang.com//551de0570001134f06000338-300-170.jpg";
    private int mAdTime = 5000;

    private SplashADView(){

    }

    public static SplashADView getInstance(){
        if (instance == null) {
            instance = new SplashADView();
        }
        return instance;
    }

    public SplashADView initADView(){
        mViewGroup = (ViewGroup) LayoutInflater.from(InitSDK.getContext()).inflate(R.layout.splash_ad_layout,null);

        mImageView = (ImageView) mViewGroup.findViewById(R.id.id_splash_view);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSplashViewCallback.onADFinish();//进入详情页之前先跳转到主界面,该方式，可以是该类不用单利模式，之前用单利是为了解决跳转后的回调问题（暂未移除单利模式）
                bClickedAd = true;
                String url = "http://www.geyanw.com/html/renshenggeyan/2012/0503/295.html";
                /*
                * 选择展示广告详情的方式
                * */
//                useDefaultBrowser(url);
                useWebView(url);
            }
        });
        return instance;
    }

    private void defaultEntry() {
        mImageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!bClickedAd) {
                    mSplashViewCallback.onADFinish();
                    bClickedAd = false;
                }
            }
        }, mAdTime);
    }

    /**
     * param adTime 广告时长
     * return
     */
    public SplashADView setADDismissTime(int adTime){
        mAdTime = adTime;
//        adTime = 5000;
        return this;
    }
    public SplashADView setSplashViewCallback(SplashViewCallback splashViewCallback){
        mSplashViewCallback = splashViewCallback;
        defaultEntry();
        /*
        * 先从本地拿数据；
        * 测试：暂时没有对比本地数据和网络数据的可靠性
        * */

        Bitmap bitmap = getLocal();
        if (bitmap != null) {
            mImageView.setImageBitmap(bitmap);
            mSplashViewCallback.onLoadAD(mViewGroup);
        }else {
              /*
                * 从网络拿数据
                * */
            new UtilHttpBitmap().get(url)
                    .initHttpBitmapCallback(new IBitmapCallback() {
                        @Override
                        public void onBitmapSuccess(Bitmap bitmap) {
                            /*
                            * 保存到本地xml,也可以选择保存到SD卡中
                            * */
                            String item = getMD5(url);
                            UtilSPSingleInstance.getInstance(InitSDK.getContext())
                                    .initSPFileName(TAG)
                                    .putBitmap(item,bitmap)
                                    .submit();
                        }

                        @Override
                        public void onBitmapFailure(Exception e) {
                            mSplashViewCallback.onADFailure(e);
                        }

                    });
        }
        return this;
    }

    private Bitmap getLocal() {
        return UtilSPOnlyPead.getInstance(InitSDK.getContext())
                .initSPFileName(TAG)
                .getBitmap(getMD5(url));
    }

    @Override
    public void exit() {
//        mSplashViewCallback.onADFinish();
    }


    public interface SplashViewCallback{

        /*
        * 广告加载失败的回调
        * */
        void onADFailure(Exception e);
        /*
        * 加载广告页
        * */
        void onLoadAD(View view);

        /*
        * 广告结束的回调函数
        * */
        void onADFinish();
    }

}
