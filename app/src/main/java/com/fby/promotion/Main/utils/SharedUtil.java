package com.fby.promotion.Main.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedUtil {

	public static final String data = "config";

	public static String getShared(Context context, String key,
								   String defaultValue) {
		SharedPreferences sp = context.getSharedPreferences(data,
				context.MODE_PRIVATE);
		String spData = sp.getString(key, defaultValue);
		return spData;

	}
	public static void putShared(Context context, String key,
								 String defaultValue) {
		SharedPreferences sp = context.getSharedPreferences(data,
				context.MODE_PRIVATE);
		sp.edit().putString(key, defaultValue).commit();

	}
}
