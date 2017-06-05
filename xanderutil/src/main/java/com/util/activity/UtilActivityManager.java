package com.util.activity;

import android.app.Activity;

import java.lang.ref.WeakReference;

/************************************************************
 * * 时间:2016/12/20 15:59
************************************************************/

public final class UtilActivityManager {

    private static UtilActivityManager sInstance = new UtilActivityManager();
	  private WeakReference<Activity> sCurrentActivityWeakRef;
	 
	 
	  private UtilActivityManager() {
	 
	  }
	 
	  public static UtilActivityManager getInstance() {
	    return sInstance;
	  }
	 
	  public Activity getCurrentActivity() {
	    Activity currentActivity = null;
	    if (sCurrentActivityWeakRef != null) {
	      currentActivity = sCurrentActivityWeakRef.get();
	    }
	    return currentActivity;
	  }
	 
	  public void setCurrentActivity(Activity activity) {
	    sCurrentActivityWeakRef = new WeakReference<Activity>(activity);
	  }
}
