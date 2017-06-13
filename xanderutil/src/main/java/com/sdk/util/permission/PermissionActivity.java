package com.sdk.util.permission;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
/**
 * author xander on  2017/5/23.
 */

public class PermissionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //不接受触摸屏事件
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        if (savedInstanceState == null) {
            UtilPermission.getInstance().checkRequestPermissionRationale(this);
        }
        UtilPermission.getInstance().currentActivtiy(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        UtilPermission.getInstance().checkRequestPermissionRationale(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults) {
        if (permissions == null||grantResults==null) {
            throw new NullPointerException("permissions or grantResults should not to be null !!");
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        UtilPermission.getInstance().onRequestPermissionsResult(requestCode, this, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        UtilPermission.getInstance().onActivityResult(requestCode, resultCode, data);
    }

    public interface CurrentActivityCallback{
        void currentActivtiy(Activity activity);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
