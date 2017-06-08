package com.util.manager;

import android.app.Activity;

import java.lang.ref.WeakReference;

/************************************************************
 * * 时间:2016/12/20 15:59
************************************************************/

public final class ManagerActivity {

    private static ManagerActivity sInstance = new ManagerActivity();
	  private WeakReference<Activity> sCurrentActivityWeakRef;
	 
	 
	  private ManagerActivity() {
	 
	  }
	 
	  public static ManagerActivity getInstance() {
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
