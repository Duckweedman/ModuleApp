package com.test.modulebrary.util;

import android.widget.Toast;

import com.test.modulebrary.BaseApplication;

/**
 * 弹toast
 * 2017年9月5日
 * @author Administrator
 *
 */
public class ToastUtil {
	public static long LAST_CLOCK_TIME;

	public static void toastShort(String text) {
		Toast.makeText(BaseApplication.getAppContext(), text, Toast.LENGTH_SHORT).show();
	}
	public static void toastShort(int resId) {
		Toast.makeText(BaseApplication.getAppContext(), resId, Toast.LENGTH_SHORT).show();
	}

	public static void toastLong(String text) {
		Toast.makeText(BaseApplication.getAppContext(), text, Toast.LENGTH_LONG).show();
	}

	public static void toastLong(int resId) {
		Toast.makeText(BaseApplication.getAppContext(), resId, Toast.LENGTH_LONG).show();
	}

	// 防误点
	public synchronized static boolean isFastClick() {
		long time = System.currentTimeMillis();
		if (time - LAST_CLOCK_TIME < 500) {
			return true;
		}
		LAST_CLOCK_TIME = time;
		return false;
	}
}
