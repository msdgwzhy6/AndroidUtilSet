package com.smart.androidutils.activity.sharepreference;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.smart.androidutils.BaseCompatActivity;
import com.smart.androidutils.R;
import com.util.UtilEncript;
import com.util.file.UtilFile;
import com.util.file.UtilSPSingleInstance;
import com.util.http.UtilHttpBitmap;
import com.util.http.core.callback.IBitmapCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.smart.androidutils.constant.ConMainUrl.urlImg;

public class SPCompatActivity extends BaseCompatActivity {

    @BindView(R.id.id_btn_img_net)
    Button mBtnImgNet;
    @BindView(R.id.id_btn_img_sp)
    Button mBtnImgSp;
    @BindView(R.id.id_img_view_net)
    ImageView mImgViewNet;
    @BindView(R.id.id_img_view_sp)
    ImageView mImgViewSp;


    private Bitmap mBitmap;
    private Handler mHandler = new Handler();
    private Context mContext;


    @Override
    protected void initData() {
        setTitle(getResources().getString(R.string.control_sp));
        mContext = this;
    }

    @Override
    protected void bindDataToView() {

    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_sp;
    }

    @OnClick(R.id.id_btn_img_net)
    public void onMBtnImgNetClicked() {
        new UtilHttpBitmap().get(urlImg)
                .initHttpBitmapCallback(new IBitmapCallback() {
                    @Override
                    public void onBitmapSuccess(Bitmap bitmap) {
                        mImgViewNet.setImageBitmap(bitmap);
                      /*  UtilSPSingleInstance.getInstance(mContext)
                                .initSPFileName(TAG)
                                .putBitmap(UtilEncript.getMD5(urlImg),bitmap)
                                .submit();*/
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.i(TAG, "onFailure" +e.getMessage());
                    }
                });



    }

    @OnClick(R.id.id_btn_img_sp)
    public void onMBtnImgSpClicked() {

       /* mBitmap = UtilSPSingleInstance.getInstance(mContext)
                .initSPFileName(TAG)
                .getBitmap(UtilEncript.getMD5(urlImg))
                ;
        mImgViewSp.setImageBitmap(getBitmapDefault("cut"));*/
           new Thread(new Runnable() {
            @Override
            public void run() {
                mBitmap = UtilFile.getNetBitmap(R.mipmap.ios,urlImg);
                if (mBitmap == null) {
                    Looper.prepare();
                    Toast.makeText(mContext, "网络图片为空", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
                UtilSPSingleInstance.getInstance(mContext)
                        .initSPFileName(TAG)
                        .putBitmap(UtilEncript.getMD5(urlImg),mBitmap)
                        .submit();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mImgViewNet.setImageBitmap(mBitmap);
                    }
                });

            }
        }).start();
    }

}
