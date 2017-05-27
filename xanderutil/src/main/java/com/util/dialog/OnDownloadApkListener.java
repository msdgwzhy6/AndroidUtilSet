package com.util.dialog;

/**
 * Created by xu on 2017/3/11.
 */

public interface OnDownloadApkListener {
    void result(UtilDialogDouble utilDialogDouble);
    void failure(Exception e,UtilDialogDouble utilDialogDouble);//给外界一个接口，返回用户错误信息
}
