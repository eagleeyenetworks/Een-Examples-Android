package com.eagleeye.examples.util;

import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class UtilLogger {
	
	public static void logInfo(String tag, String info) {
		if(info.length() > 4000) {
			Log.d(tag, info.substring(0, 4000));
			logInfo(tag, info.substring(4000));} 
		else {
			Log.d(tag, info);}
	}
	
	public static void logInfo(String tag, List<String> listOfValues) {
		logInfo(tag, Arrays.toString(listOfValues.toArray()));
	}
	
	public static void logInfo(String tag, int value) {
		logInfo(tag, "" + value);
	}
	
	public static void logInfo(String tag, boolean value) {
		logInfo(tag, "" + value);
	}
	
	public static void logJson(String tag, JSONObject jsonObj) {
		try {
			logInfo(tag, jsonObj.toString(4));
		} catch (JSONException e) {
			logInfo(tag, "String cannot be represented as JSON");
			e.printStackTrace();
		}
	}
	
	public static void logJsonArray(String tag, JSONArray jsonArray) {
		try {
			logInfo(tag, jsonArray.toString(4));
		} catch (JSONException e) {
			logInfo(tag, "String cannot be represented as JSON Array");
			e.printStackTrace();
		}
	}
	
	public static void logError(String tag, String string) {
		Log.e(tag, string);
	}
}
