package com.eagleeye.examples.util;

import android.content.Context;
import android.widget.Toast;

public class UtilToast {
	public static void showToast(Context context, String text) {
		if(context == null)
			return;
		
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}
}