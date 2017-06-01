package com.util.ad;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;

import com.util.R;
import com.util.core.InitSDK;
import com.util.file.UtilSPOnlyPead;
import com.util.file.UtilSPSingleInstance;
import com.util.http.UtilHttpBitmap;
import com.util.http.core.callback.IBitmapCallback;

import static com.util.UtilEncript.getMD5;
import static com.util.view.UtilWidget.setWebView;

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
    private WebView mWebView;
    private SplashViewCallback mSplashViewCallback;
    private static SplashADView instance;
    private boolean enterAd;
    /*
    * 这些广告图片数据应该从服务器获取
    * 获取后，加密保存到本地
    * */
    private final String url = "http://img.mukewang.com//5523711700016d1606000338.jpg";

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
        mWebView = (WebView) mViewGroup.findViewById(R.id.id_splash_web_view);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterAd = true;
               /*
               * 加载webview
               * */
                /*
                * 隐藏图片，显示webview
                * */
                mImageView.setVisibility(View.GONE);
                String url = "http://www.geyanw.com/html/renshenggeyan/2012/0503/295.html";
//                new UtilNoAdWeb(mWebView,"gb2312", url).execute(url);
                setWebView(mWebView);
            }
        });
        return instance;
    }
    /**
     * param adTime 广告时长
     * return
     */
    public SplashADView setADTime(int adTime){
//        mAdTime = adTime;
        adTime = 5000;
        mImageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!enterAd) {
                    mSplashViewCallback.onFinish();
                }
            }
        }, adTime);
        return this;
    }
    public SplashADView setSplashViewCallback(SplashViewCallback splashViewCallback){
        mSplashViewCallback = splashViewCallback;
           /*
        * 先从本地拿数据；
        * 测试：暂时没有对比本地数据和网络数据的可靠性
        *
        * */

        Bitmap bitmap = getLocal();
        if (bitmap != null) {
            mImageView.setImageBitmap(bitmap);
            mSplashViewCallback.onLoadADView(mViewGroup);
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
                            UtilSPSingleInstance.getInstance(InitSDK.getContext())
                                    .initSPFileName(TAG)
                                    .putBitmap(getMD5(url),bitmap)
                                    .submit();
                        }

                        @Override
                        public void onBitmapFailure(Exception e) {
                            mSplashViewCallback.onSplashViewFailure(e);
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
    public void exit(Activity activity) {
        mSplashViewCallback.onFinish();
    }

    public interface SplashViewCallback{
        void onSplashViewFailure(Exception e);
        void onLoadADView(View view);
        void onFinish();
    }

}
