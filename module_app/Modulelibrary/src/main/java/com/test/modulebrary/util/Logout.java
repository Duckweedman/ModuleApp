package com.test.modulebrary.util;

import android.util.Log;

import com.test.modulebrary.BuildConfig;

public class Logout {
	public static boolean DEBUG = BuildConfig.DEBUG;
	private final static String TAG = "DataOpt";
	public static void log(Object msg) {
		if (DEBUG) {
			if (null == msg) {
				return;
			}
			Log.d(TAG, msg.toString());
		}
	}
	public static void log(String tag, Object msg) {
		if (DEBUG) {
			if (null == msg) {
				return;
			}
			Log.d(tag, msg.toString());

		}
	}
}