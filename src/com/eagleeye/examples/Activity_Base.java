package com.eagleeye.examples;

import android.app.Activity;
import android.os.Bundle;

import com.eagleeye.examples.util.UtilLogger;

public abstract class Activity_Base extends Activity {
	
	protected final String TAG = getClass().getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		UtilLogger.logInfo("Activity_Base", TAG + " onCreate");
	}
}
