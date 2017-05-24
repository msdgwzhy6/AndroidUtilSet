package com.smart.androidutils.activity.sharepreference;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;

import com.smart.androidutils.BaseActivity;
import com.smart.androidutils.R;
import com.util.UtilEncript;
import com.util.file.UtilFile;
import com.util.file.UtilSP;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.smart.androidutils.constant.Constant.urlImg;

public class SPActivity extends BaseActivity {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp);
        mContext = this;
        ButterKnife.bind(this);
        setTitle(getResources().getString(R.string.act_sp));
    }

    @OnClick(R.id.id_btn_img_net)
    public void onMBtnImgNetClicked() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mBitmap = UtilFile.getNetBitmap(urlImg);
                UtilSP.getInstance(mContext)
                        .initSP(TAG)
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

    @OnClick(R.id.id_btn_img_sp)
    public void onMBtnImgSpClicked() {

        mBitmap = UtilSP.getInstance(mContext)
                .initSP(TAG)
                .getBitmap(UtilEncript.getMD5(urlImg))
                ;
        mImgViewSp.setImageBitmap(mBitmap);
    }

}