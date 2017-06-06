package com.smart.androidutils.activity.permission;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.smart.androidutils.BaseCompatActivity;
import com.smart.androidutils.R;
import com.util.permission.PermissionCallback;
import com.util.permission.UtilPermission;

import java.io.File;
import java.util.List;

public class PActivity extends BaseCompatActivity {

    @Override
    protected void setActivityTitle() {
        super.setActivityTitle();
        mAvtivitytTitle = getString(R.string.permission);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_permission;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {


    }

    public void onClickFragment(View v) {
//        BlankActivity.gotoAct(this);
    }

    public void onClickAll(View v) {
        UtilPermission.getInstance().initPermissionTypes(Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.READ_PHONE_STATE
                , Manifest.permission.SEND_SMS).setPermissionCallback( new PermissionCallback() {
            @Override
            public void onGranted() {
                writeSD();
//                        getIMEI();
            }

            @Override
            public void onDenied(List<String> permissions) {
                makeText(permissions.toString() + "权限拒绝");
            }
        });
    }

    public void onClickSd(View v) {
        //写sd
//                if (checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                    writeSD();
//                } else {
//                    ActivityCompat.requestPermissions(this,
//                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
//                }
        UtilPermission.getInstance().initPermissionTypes(Manifest.permission.WRITE_EXTERNAL_STORAGE).setPermissionCallback(
                new PermissionCallback() {
                    @Override
                    public void onGranted() {
                        writeSD();
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                        makeText(permissions.toString() + "权限拒绝");
                    }
                });
    }

    public void onClickImei(View v) {
        //读imei
//                //如果权限允许则执行运输和，否则请求权限
//                if (checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)) {
//                    getIMEI();
//                } else {
//                    //处理“不再提醒”，判断是否需要 向用户解释，为什么要申请该权限
//                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
//                        new AlertDialog.Builder(this).setMessage("需要申请imei")
//                                .setPositiveButton("申请", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        ActivityCompat.requestPermissions(MainActivity.this,
//                                                new String[]{Manifest.permission.READ_PHONE_STATE}, READ_PHONE_STATE_REQUEST_CODE);
//                                    }
//                                }).create().show();
//                    } else {
//                        //请求权限
//                        ActivityCompat.requestPermissions(this,
//                                new String[]{Manifest.permission.READ_PHONE_STATE}, READ_PHONE_STATE_REQUEST_CODE);
//                    }
//                }
        UtilPermission.getInstance().initPermissionTypes(Manifest.permission.READ_PHONE_STATE)
        .setPermissionCallback( new PermissionCallback() {
            @Override
            public void onGranted() {
//                        getIMEI();
            }

            @Override
            public void onDenied(List<String> permissions) {
                makeText(permissions.toString() + "权限拒绝");
            }
        });

    }

    public void onClickCallPhone(View view) {
        UtilPermission.getInstance().initPermissionTypes(Manifest.permission.CALL_PHONE)
        .setPermissionCallback(
                new PermissionCallback() {
                    @Override
                    public void onGranted() {
                        //注意：不用用带参的构造方法 否则 android studio 环境出错，提示要你检查授权

/*
                        Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:13800138000"));
                        intentCall.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intentCall);
*/

                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:18221869775"));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                        makeText(permissions.toString() + "权限拒绝");
                    }
                });

    }

//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        //用户选择允许或拒绝后，都会回调此方法
//        switch (requestCode) {
//            case READ_PHONE_STATE_REQUEST_CODE:
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {// 权限允许 Granted
//                    getIMEI();
//                } else {// 权限拒绝 Denied
//                    makeText("读imei权限拒绝");
//                }
//                break;
//            case WRITE_EXTERNAL_STORAGE_REQUEST_CODE:
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    writeSD();
//                } else {
//                    makeText("写SD权限拒绝");
//                }
//                break;
//        }
//    }

//    private boolean checkSelfPermission(Context context, String permission) {
//        //检查权限
//        int permissionCheck = ActivityCompat.checkSelfPermission(context, permission);
//        return PackageManager.PERMISSION_GRANTED == permissionCheck;
//    }

    private void writeSD() {
        File acpDir = getCacheDir("acp", PActivity.this);
        if (acpDir != null)
            makeText("写SD成功：" + acpDir.getAbsolutePath());
    }


    public static File getCacheDir(String dirName, Context context) {
        File result;
        if (existsSdcard()) {
            File cacheDir = context.getExternalCacheDir();
            if (cacheDir == null) {
                result = new File(Environment.getExternalStorageDirectory(),
                        "Android/data/" + context.getPackageName() + "/cache/" + dirName);
            } else {
                result = new File(cacheDir, dirName);
            }
        } else {
            result = new File(context.getCacheDir(), dirName);
        }
        if (result.exists() || result.mkdirs()) {
            return result;
        } else {
            return null;
        }
    }

    public static Boolean existsSdcard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    private void makeText(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}