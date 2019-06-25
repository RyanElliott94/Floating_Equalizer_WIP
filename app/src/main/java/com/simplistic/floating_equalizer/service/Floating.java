package com.simplistic.floating_equalizer.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.simplistic.floating_equalizer.EqualizerActivity;
import com.simplistic.floating_equalizer.MainActivity;
import com.simplistic.floating_equalizer.NotificationUtils;
import com.simplistic.floating_equalizer.R;
import com.simplistic.floating_equalizer.model.EqualizerApi;

public class Floating extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	ImageView chatHead;
	WindowManager windowManager;
	private EqualizerActivity mainEQ;
	PopupMenu popup;
	private NotificationUtils mNotificationUtils;
	NotificationCompat.Builder nb;
	String basicEQ;
	String advanceEQ;
	WindowManager.LayoutParams myParams;
	/*
	 * Enabled aggressive block sorting
	 * Enabled unnecessary exception pruning
	 */

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		basicEQ = intent.getStringExtra("basic_eq");
		advanceEQ = intent.getStringExtra("advance_eq");
		return Service.START_STICKY;
	}

	@SuppressLint("ClickableViewAccessibility")
	public void onCreate() {
		super.onCreate();

		mNotificationUtils = new NotificationUtils(this);

		chatHead = new ImageView(this);
		//a face floating bubble as imageView
		chatHead.setImageResource(R.mipmap.ic_launcher);
		windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
		//here is all the science of params

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			myParams = new WindowManager.LayoutParams(
					WindowManager.LayoutParams.WRAP_CONTENT,
					WindowManager.LayoutParams.WRAP_CONTENT,
					WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
					WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
					PixelFormat.TRANSLUCENT);
		} else {
			myParams = new WindowManager.LayoutParams(
					WindowManager.LayoutParams.WRAP_CONTENT,
					WindowManager.LayoutParams.WRAP_CONTENT,
					WindowManager.LayoutParams.TYPE_PHONE,
					WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
					PixelFormat.TRANSLUCENT);
		}

		myParams.gravity = Gravity.TOP | Gravity.LEFT;
		myParams.x = 0;
		myParams.y = 100;
		// add a floatingfacebubble icon in window
		windowManager.addView(chatHead, myParams);
		try {
			//for moving the picture on touch and slide
			chatHead.setOnTouchListener(new View.OnTouchListener() {
				WindowManager.LayoutParams paramsT = myParams;
				private int initialX;
				private int initialY;
				private float initialTouchX;
				private float initialTouchY;
				private long touchStartTime = 0;

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					//remove face bubble on long press
					if (System.currentTimeMillis() - touchStartTime > ViewConfiguration.getLongPressTimeout() && initialTouchX == event.getX()) {
						windowManager.removeView(chatHead);
						stopSelf();
						return false;
					}
					switch (event.getAction()) {
						case MotionEvent.ACTION_DOWN:
							touchStartTime = System.currentTimeMillis();
							initialX = myParams.x;
							initialY = myParams.y;
							initialTouchX = event.getRawX();
							initialTouchY = event.getRawY();
							break;
						case MotionEvent.ACTION_UP:
							break;
						case MotionEvent.ACTION_MOVE:
							myParams.x = initialX + (int) (event.getRawX() - initialTouchX);
							myParams.y = initialY + (int) (event.getRawY() - initialTouchY);
							windowManager.updateViewLayout(v, myParams);
							break;
					}
					return false;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		popup = new PopupMenu(getApplicationContext(), chatHead);
		//Inflating the Popup using xml file
		popup.getMenuInflater().inflate(R.menu.bubble_menu, popup.getMenu());

		//registering popup with OnMenuItemClickListener
		popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {
					case R.id.back_to_eq:
						backToEQ();
						break;
					case R.id.hide_icon:
						hideIcon();
						break;
					case R.id.stop:
						windowManager.removeView(chatHead);
						stopForeground(true);
						break;
				}
				return true;
			}
		});

		chatHead.setOnLongClickListener(new View.OnLongClickListener() {

			public boolean onLongClick(View view) {
//				windowManager.addView(popup, myParams);
				popup.show();
				return false;
			}
		});

	}

	public void hideIcon() {
		if (basicEQ != null && basicEQ.equals("isFromBasic")) {
			Toast.makeText(getApplicationContext(), "Hello basicEQ", Toast.LENGTH_LONG).show();
			nb = mNotificationUtils.
					getAndroidChannelNotification("Floating Equalizer", "Running", EqualizerActivity.class);
			mNotificationUtils.getManager().notify(101, nb.build());
			chatHead.setVisibility(View.GONE);
			stopForeground(true);
		} else if (advanceEQ != null && advanceEQ.equals("isFromAdvance")) {
			Toast.makeText(getApplicationContext(), "Hello advancedEQ", Toast.LENGTH_LONG).show();
			nb = mNotificationUtils.
					getAndroidChannelNotification("Floating Equalizer", "Running", MainActivity.class);
			mNotificationUtils.getManager().notify(101, nb.build());
			chatHead.setVisibility(View.GONE);
			stopForeground(true);
		}
	}

	public void backToEQ() {
		if (basicEQ != null && basicEQ.equals("isFromBasic")) {
			Intent i = new Intent(getApplicationContext(), EqualizerActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(i);
		} else if (advanceEQ != null && advanceEQ.equals("isFromAdvance")) {
			Intent i = new Intent(getApplicationContext(), MainActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(i);
		}
	}

	public void removeButton() {
		if (chatHead != null) {
			windowManager.removeView(chatHead);
		}

	}

	public void onDestroy() {
		super.onDestroy();
		if (chatHead != null) {
			windowManager.removeView(chatHead);

		}
		stopForeground(true);
	}
}