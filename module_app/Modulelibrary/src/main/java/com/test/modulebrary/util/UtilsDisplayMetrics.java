package com.test.modulebrary.util;

import android.content.Context;
import android.content.res.Resources;
import android.view.WindowManager;

import com.test.modulebrary.BaseApplication;


/**
 * 2017
 */
public class UtilsDisplayMetrics {
	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 *
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 *
	 * @param dpValue
	 * @return
	 */
	public static int dip2px(float dpValue) {
		final float scale = BaseApplication.getAppContext().getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 *
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 *
	 * @param pxValue
	 * @return
	 */
	public static int px2dip(float pxValue) {
		final float scale = BaseApplication.getAppContext().getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 将px值转换为sp值(sp一般用于文字大小调试)保证文字大小不变
	 *
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 将px值转换为sp值(sp一般用于文字大小调试)保证文字大小不变
	 *
	 * @param pxValue
	 * @return
	 */
	public static int px2sp(float pxValue) {
		final float fontScale = BaseApplication.getAppContext().getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，(sp一般用于文字大小调试)保证文字大小不变
	 *
	 * @param context
	 * @param spValue
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，(sp一般用于文字大小调试)保证文字大小不变
	 *
	 * @param spValue
	 * @return
	 */
	public static int sp2px(float spValue) {
		final float fontScale = BaseApplication.getAppContext().getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	/**
	 * 获取屏幕高度,方案一
	 *
	 * @param paramContext
	 * @return
	 */
	public static int getScreenHeight(Context paramContext) {
		return ((WindowManager) paramContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight();
	}

	/**
	 * 获取屏幕高度,方案一
	 *
	 * @return
	 */
	public static int getScreenHeight() {
		return ((WindowManager) BaseApplication.getAppContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight();
	}

	/**
	 * 获取屏幕宽度,方案一
	 *
	 * @param paramContext
	 * @return
	 */
	public static int getScreenWidth(Context paramContext) {
		return ((WindowManager) paramContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
	}

	/**
	 * 获取屏幕宽度,方案一
	 *
	 * @return
	 */
	public static int getScreenWidth() {
		return ((WindowManager) BaseApplication.getAppContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
	}

	/**
	 * 获取屏幕宽度像素
	 *
	 * @return
	 */
	public static float getWidthPixels() {
		return BaseApplication.getAppContext().getResources().getDisplayMetrics().widthPixels;
	}

	/**
	 * 获取屏幕宽度像素
	 *
	 * @param paramsContext
	 * @return
	 */
	public static float getWidthPixels(Context paramsContext) {
		return paramsContext.getResources().getDisplayMetrics().widthPixels;
	}

	/**
	 * 获取屏幕高度像素
	 *
	 * @return
	 */
	public static float getHeightPixels() {
		return BaseApplication.getAppContext().getResources().getDisplayMetrics().heightPixels;
	}

	/**
	 * 获取屏幕高度像素
	 *
	 * @param paramsContext
	 * @return
	 */
	public static float getHeightPixels(Context paramsContext) {
		return paramsContext.getResources().getDisplayMetrics().heightPixels;
	}

	/**
	 * 获取屏幕密度:屏幕密度（像素比例：0.75/1.0/1.5/2.0）
	 *
	 * @return
	 */
	public static float getScreenDensity() {
		return BaseApplication.getAppContext().getResources().getDisplayMetrics().density;
	}

	/**
	 * 获取屏幕密度:屏幕密度（像素比例：0.75/1.0/1.5/2.0）
	 *
	 * @param paramsContext
	 * @return
	 */
	public static float getScreenDensity(Context paramsContext) {
		return paramsContext.getResources().getDisplayMetrics().density;
	}

	/**
	 * 获取屏幕密度:屏幕密度（每寸像素：120/160/240/320）
	 *
	 * @return
	 */
	public static float getScreenDensityDpi() {
		return BaseApplication.getAppContext().getResources().getDisplayMetrics().densityDpi;
	}

	/**
	 * 获取屏幕密度:屏幕密度（每寸像素：120/160/240/320）
	 *
	 * @param paramsContext
	 * @return
	 */
	public static float getScreenDensityDpi(Context paramsContext) {
		return paramsContext.getResources().getDisplayMetrics().densityDpi;
	}

	//获取状态栏高度
	public static int getStatusBarHeight(Context context) {
		int result = 0;
		Resources resources = context.getResources();
		int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = resources.getDimensionPixelSize(resourceId);
		}
		return result;
	}
}
