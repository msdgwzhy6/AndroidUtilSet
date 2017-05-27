package com.util.permission;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * author xander on  2017/5/23.
 * 权限帮助类
 */

class PermissionHelper {
    private static final String TAG = "PermissionHelper";
    private static final int REQUEST_CODE_PERMISSION = 0x38;
    private static final int REQUEST_CODE_SETTING = 0x39;
    private Activity mActivity;
    private PermissionTypes mOptions;
    private PermissionCallback mCallback;
    private final List<String> mDeniedPermissions = new LinkedList<>();
    private final Set<String> mManifestPermissions = new HashSet<>(1);

    PermissionHelper(Activity activity) {
        mActivity = activity;
        getManifestPermissions();
    }

    private synchronized void getManifestPermissions() {
        PackageInfo packageInfo = null;
        try {
            packageInfo = mActivity.getPackageManager().getPackageInfo(
                    mActivity.getPackageName(), PackageManager.GET_PERMISSIONS);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo != null) {
            String[] permissions = packageInfo.requestedPermissions;
            if (permissions != null) {
                Collections.addAll(mManifestPermissions, permissions);
            }
        }
    }

    /**
     * 开始请求
     *  options
     *  permissionCallback
     */
    synchronized void request(PermissionTypes options, PermissionCallback permissionCallback) {
        mCallback = permissionCallback;
        mOptions = options;
        checkSelfPermission();
    }

    /**
     * 检查权限
     */
    private synchronized void checkSelfPermission() {
        mDeniedPermissions.clear();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Log.i(TAG, "Build.VERSION.SDK_INT < Build.VERSION_CODES.M");
            if (mCallback != null)
                mCallback.onGranted();
            onDestroy();
            return;
        }
        String[] permissions = mOptions.getPermissions();
        for (String permission : permissions) {
            //检查申请的权限是否在 AndroidManifest.xml 中
            if (mManifestPermissions.contains(permission)) {
                int checkSelfPermission = checkSelfPermission(mActivity, permission);
                Log.i(TAG, "checkSelfPermission = " + checkSelfPermission);
                //如果是拒绝状态则装入拒绝集合中
                if (checkSelfPermission == PackageManager.PERMISSION_DENIED) {
                    mDeniedPermissions.add(permission);
                }
            }
        }
        //检查如果没有一个拒绝响应 onGranted 回调
        if (mDeniedPermissions.isEmpty()) {
            Log.i(TAG, "mDeniedPermissions.isEmpty()");
            if (mCallback != null)
                mCallback.onGranted();
            onDestroy();
            return;
        }
        startAcpActivity();
    }

    /**
     * 启动处理权限过程的 Activity
     */
    private synchronized void startAcpActivity() {
        Intent intent = new Intent(mActivity, PermissionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mActivity.startActivity(intent);
    }

    /**
     * 检查权限是否存在
     * 拒绝不再提示
     *  activity
     */
    synchronized void checkRequestPermissionRationale(Activity activity) {
        mActivity = activity;
        boolean rationale = false;
        //如果有拒绝则提示申请理由提示框，否则直接向系统请求权限
        for (String permission : mDeniedPermissions) {
            rationale = rationale || shouldShowRequestPermissionRationale(mActivity, permission);
        }
        Log.i(TAG, "rationale = " + rationale);
        String[] permissions = mDeniedPermissions.toArray(new String[mDeniedPermissions.size()]);
        if (rationale) showRationalDialog(permissions);
        else requestPermissions(mActivity, permissions, REQUEST_CODE_PERMISSION);;
    }

    /**
     * 申请理由对话框
     *
     *  permissions
     */
    private synchronized void showRationalDialog(final String[] permissions) {
        new AlertDialog.Builder(mActivity)
                .setMessage(mOptions.getRationalMessage())
                .setPositiveButton(mOptions.getRationalBtnText(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestPermissions(mActivity, permissions, REQUEST_CODE_PERMISSION);
                    }
                }).show();
    }


    /**
     * 响应向系统请求权限结果
     *
     *  requestCode
     *  permissions
     *  grantResults
     */
    synchronized void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION:
                LinkedList<String> grantedPermissions = new LinkedList<>();
                LinkedList<String> deniedPermissions = new LinkedList<>();
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
                        grantedPermissions.add(permission);
                    else deniedPermissions.add(permission);
                }
                //全部允许才回调 onGranted 否则只要有一个拒绝都回调 onDenied
                if (!grantedPermissions.isEmpty() && deniedPermissions.isEmpty()) {
                    if (mCallback != null)
                        mCallback.onGranted();
                    onDestroy();
                } else if (!deniedPermissions.isEmpty()) showDeniedDialog(deniedPermissions);
                break;
        }
    }

    /**
     * 拒绝权限提示框
     *
     *  permissions
     */
    private synchronized void showDeniedDialog(final List<String> permissions) {
        new AlertDialog.Builder(mActivity)
                .setMessage(mOptions.getDeniedMessage())
                .setCancelable(false)
                .setNegativeButton(mOptions.getDeniedCloseBtn(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (mCallback != null)
                            mCallback.onDenied(permissions);
                        onDestroy();
                    }
                })
                .setPositiveButton(mOptions.getDeniedSettingBtn(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startSetting();
                    }
                }).show();
    }

    /**
     * 摧毁本库的 PermissionActivity
     */
    private void onDestroy() {
        if (mActivity != null) {
            mActivity.finish();
            mActivity = null;
        }
        mCallback = null;
    }

    /**
     * 跳转到设置界面
     */
    private void startSetting() {
        if (MiuiOs.isMIUI()) {
            Intent intent = MiuiOs.getSettingIntent(mActivity);
            if (MiuiOs.isIntentAvailable(mActivity, intent)) {
                mActivity.startActivityForResult(intent, REQUEST_CODE_SETTING);
                return;
            }
        }
        try {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    .setData(Uri.parse("package:" + mActivity.getPackageName()));
            mActivity.startActivityForResult(intent, REQUEST_CODE_SETTING);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
                mActivity.startActivityForResult(intent, REQUEST_CODE_SETTING);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

    }

    /**
     * 响应设置权限返回结果
     *
     *  requestCode
     *  resultCode
     *  data
     */
    synchronized void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mCallback == null || mOptions == null
                || requestCode != REQUEST_CODE_SETTING) {
            onDestroy();
            return;
        }
        checkSelfPermission();
    }
    /**
     * 检查权限授权状态
     *
     * param context
     * param permission
     * return
     */
    int checkSelfPermission(Context context, String permission) {
        try {
            final PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            int targetSdkVersion = info.applicationInfo.targetSdkVersion;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (targetSdkVersion >= Build.VERSION_CODES.M) {
                    Log.i(TAG, "targetSdkVersion >= Build.VERSION_CODES.M");
                    return context.checkSelfPermission(permission);
                } else {
                    return context.checkSelfPermission(permission);
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.checkSelfPermission(permission);
        }
        return -1000;
    }

    /**
     * 向系统请求权限
     *
     * param activity
     * param permissions
     * param requestCode
     */
    private synchronized void requestPermissions(Activity activity, String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.requestPermissions( permissions, requestCode);
        }
    }

    /**
     * 检查权限是否存在拒绝不再提示
     *
     * param activity
     * param permission
     * return
     */
    boolean shouldShowRequestPermissionRationale(Activity activity, String permission) {
        boolean shouldShowRational = false;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            shouldShowRational = activity.shouldShowRequestPermissionRationale( permission);
        }
        Log.i(TAG, "shouldShowRational = " + shouldShowRational);
        return shouldShowRational;
    }
}
