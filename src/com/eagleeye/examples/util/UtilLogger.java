package com.eagleeye.examples.util;

import android.util.Log;

public class UtilLogger {
	
	public static void logInfo(String tag, String info) {

		if(info.length() > 4000) {
			Log.d(tag, info.substring(0, 4000));
			logInfo(tag, info.substring(4000));} 
		else {
			Log.d(tag, info);}
	}
	
	public static void logError(String tag, String string) {
		Log.e(tag, string);
	}
}
