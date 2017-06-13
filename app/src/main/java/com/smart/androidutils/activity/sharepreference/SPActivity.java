package com.smart.androidutils.activity.sharepreference;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Button;
import android.widget.ImageView;

import com.smart.androidutils.BaseActivity;
import com.smart.androidutils.R;
import com.sdk.util.cache.DiskLruCacheHelper;
import com.sdk.util.convert.BitmapConvert;
import com.sdk.util.encript.UtilEncript;
import com.sdk.util.http.UtilHttpBitmap;
import com.sdk.util.http.core.callback.IBitmapCallback;
import com.sdk.util.logger.JJLogger;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.smart.androidutils.constant.ConMainUrl.urlImg;

public class SPActivity extends BaseActivity {

    @BindView(R.id.id_btn_img_net)
    Button mBtnImgNet;
    @BindView(R.id.id_btn_img_sp)
    Button mBtnImgSp;
    @BindView(R.id.id_img_view_net)
    ImageView mImgViewNet;
    @BindView(R.id.id_img_view_sp)
    ImageView mImgViewSp;

    private Context mContext;

    private DiskLruCacheHelper helper;

    @Override
    protected void initData() {
        setTitle(getResources().getString(R.string.control_sp));
        mContext = this;
        try {
            helper = new DiskLruCacheHelper(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

                        helper.put(UtilEncript.getMD5(urlImg),bitmap);

                    }

                    @Override
                    public void onBitmapFailure(Exception e) {
                       JJLogger.i("asadas"+e.getLocalizedMessage());
                    }

                });



    }

    @OnClick(R.id.id_btn_img_sp)
    public void onMBtnImgSpClicked() {

        Bitmap bitmap =helper.getAsBitmap(UtilEncript.getMD5(urlImg));
        if (bitmap == null) {
            return;
        }
        bitmap = BitmapConvert.toGray(bitmap);
//        bitmap = scale(bitmap,780,1280);
        mImgViewSp.setImageBitmap(bitmap);
    }

}
