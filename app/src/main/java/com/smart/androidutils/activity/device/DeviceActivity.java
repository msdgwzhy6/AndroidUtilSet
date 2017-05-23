package com.smart.androidutils.activity.device;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.smart.androidutils.R;
import com.smart.dialog_library.DialogCustom;
import com.smart.dialog_library.callback.OnSingleBtnClickedListener;
import com.util.phone.UtilDevice;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeviceActivity extends AppCompatActivity {

    @BindView(R.id.id_btn_device_status)
    Button mBtnDeviceStatus;
    @BindView(R.id.id_btn_device_sim)
    Button mBtnDeviceSim;
    @BindView(R.id.id_btn_device_contact)
    Button mBtnDeviceContact;
    @BindView(R.id.id_btn_device_sms)
    Button mBtnDeviceSms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        ButterKnife.bind(this);
        setTitle(getResources().getString(R.string.act_device));
    }

    @OnClick({R.id.id_btn_device_status, R.id.id_btn_device_sim, R.id.id_btn_device_contact, R.id.id_btn_device_sms})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_btn_device_status:
               /* new DialogCustom(this)
                        .setToast(UtilDevice.getPhoneStatus(),10000)
                        .setToastDrawableId(R.drawable.dialog_bg)
//                        .setDialogOffPos(null,0.2f)
                        .show();*/
                new DialogCustom(this).setTitle("默认背景色")
                        .setMessage(UtilDevice.getPhoneStatus())
                        .setSingleBtnTextR("one")
                        .setOnSingleClicedkListener(new OnSingleBtnClickedListener() {
                            @Override
                            public void onRightBtnClick(DialogCustom dialogCustom) {
                                dialogCustom.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.id_btn_device_sim:
                break;
            case R.id.id_btn_device_contact:
                break;
            case R.id.id_btn_device_sms:
                break;
        }
    }
}
