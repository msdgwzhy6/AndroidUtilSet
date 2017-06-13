package com.sdk.util.app;

import android.graphics.drawable.Drawable;

import java.util.List;

public interface AppBeanCallback <T extends String,D extends Drawable>{
//    void result(List<PackageInfo> packageInfos);
    void result(List<T> packageName, List<D> iconDrawable);
    void failure(Exception e);
}
