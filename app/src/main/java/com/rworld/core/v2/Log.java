package com.rworld.core.v2;


public class Log {
	
	public static final String TAG = "rworldcore";
	
	public static void d(String msg) {
		if(GameActivity.Debug) {
			android.util.Log.d(TAG, msg);
		}
	}
	
	public static void d(String msg, Throwable tr) {
		if(GameActivity.Debug) {
			android.util.Log.d(TAG, msg, tr);
		}
	}
	
	public static void i(String msg) {
		android.util.Log.i(TAG, msg);
	}
	
	public static void i(String msg, Throwable tr) {
		android.util.Log.i(TAG, msg, tr);
	}
	
	public static void e(String msg) {
		android.util.Log.e(TAG, msg);
	}
	
	public static void e(String msg, Throwable tr) {
		android.util.Log.e(TAG, msg, tr);
	}
}
