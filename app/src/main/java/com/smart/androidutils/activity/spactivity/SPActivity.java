package com.smart.androidutils.activity.spactivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import com.smart.androidutils.R;
import com.xanderutillibrary.dao.util.UtilFile;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.smart.androidutils.constant.Constant.urlImg;

public class SPActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp);
        ButterKnife.bind(this);
        setTitle("SP工具类");
    }

    @OnClick(R.id.id_btn_img_net)
    public void onMBtnImgNetClicked() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mBitmap = UtilFile.saveBitmap(urlImg);
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
        mImgViewSp.setImageBitmap(mBitmap);
    }

}
