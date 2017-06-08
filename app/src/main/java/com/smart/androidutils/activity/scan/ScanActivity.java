package com.smart.androidutils.activity.scan;

import android.content.Intent;

import com.smart.androidutils.BaseActivity;
import com.smart.androidutils.R;
import com.util.logger.JJLogger;
import com.util.scan.UtilScan;

public class ScanActivity extends BaseActivity {

    private static final int REQUEST_CODE_SCAN = 0x0000;

    @Override
    protected int initLayout() {
        return R.layout.activity_scan;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setActivityTitle() {
        super.setActivityTitle();
        mAvtivitytTitle = getString(R.string.qr_code);
    }

    @Override
    protected void handleIntent() {
        super.handleIntent();
        UtilScan.scanQRCode(REQUEST_CODE_SCAN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        JJLogger.i("requestCode = "+requestCode);
        JJLogger.i("resultCode = "+resultCode);
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            String code = UtilScan.handleQRCode(data);
            JJLogger.i("REQUEST_CODE_SCAN"+code);
        }
    }
}
