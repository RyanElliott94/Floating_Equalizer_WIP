/*
 * Decompiled with CFR 0_58.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 *  android.app.PendingIntent
 *  android.app.Service
 *  android.content.Context
 *  android.content.Intent
 *  android.database.sqlite.SQLiteDatabase
 *  android.os.IBinder
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Long
 *  java.lang.String
 */
package com.simplistic.floating_equalizer.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.simplistic.floating_equalizer.NotificationUtils;
import com.simplistic.floating_equalizer.model.EqualizerApi;
import com.simplistic.floating_equalizer.model.Profile;
import com.simplistic.floating_equalizer.model.lib.DbHelper;

public class EqualizerService extends Service {
	  public static int NOTIFY_ID = 1;
	    protected static Boolean mStarted = false;
	    protected Notification mNotice;
	    protected PendingIntent mPendingIntent;
	    private NotificationUtils mNotificationUtils;
	 @Override
	public IBinder onBind(Intent intent) {
		
		return null;
	}

	@SuppressLint("ResourceType")
	@Override
	public void onCreate() {
		super.onCreate();
		mNotificationUtils = new NotificationUtils(this);

//		NotificationCompat.Builder nb = mNotificationUtils.
//				getAndroidChannelNotification("Floating Equalizer", "");
//
//		mNotificationUtils.getManager().notify(101, nb.build());

		EqualizerApi.init(0);
		new Profile(this).loadSettings();
	}

	
	
	@SuppressLint("ResourceType")
	@Override
	public int onStartCommand(Intent intent, int intent2, int intent3) {
		Profile profile = new Profile((this));
		return 0;
	}
	
	@Override
	public void onDestroy() {
		new Profile(this).saveSettings();
		EqualizerApi.destroy();
		DbHelper.getInstance(this).closeAdapter();
		stopForeground(true);
		super.onDestroy();
	}

	
}
