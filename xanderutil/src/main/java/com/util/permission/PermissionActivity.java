package com.util.permission;

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
            Permission.getInstance(this).getPermissionHelper().checkRequestPermissionRationale(this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Permission.getInstance(this).getPermissionHelper().checkRequestPermissionRationale(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults) {
        if (permissions == null||grantResults==null) {
            throw new NullPointerException("permissions or grantResults should not to be null !!");
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Permission.getInstance(this).getPermissionHelper().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Permission.getInstance(this).getPermissionHelper().onActivityResult(requestCode, resultCode, data);
    }
}
