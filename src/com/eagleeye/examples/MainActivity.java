package com.eagleeye.examples;

import org.apache.http.Header;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.eagleeye.examples.util.UtilToast;
import com.eagleeye.sdk.http.HttpAaa;
import com.loopj.android.http.TextHttpResponseHandler;

public class MainActivity extends Activity {

	Menu menu;
	
	boolean isLoggedIn = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button buttonAnnotateImage = (Button) findViewById(R.id.main_button_annotateImage);
		buttonAnnotateImage.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {
				UtilToast.showToast(MainActivity.this, "Annotate Image");
			}
		});
		
		Button buttonTimelapseVideo = (Button) findViewById(R.id.main_button_timelapseVideo);
		buttonTimelapseVideo.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {
				UtilToast.showToast(MainActivity.this, "Timelapse Video");
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateView();	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.menu = menu;
		getMenuInflater().inflate(R.menu.menu_main, menu);
		updateView();
		return true;
	}

	private void updateView() {
		if(menu == null) {
			return;}
		
		MenuItem miLogin = menu.findItem(R.id.menu_main_login);
		MenuItem miLogout = menu.findItem(R.id.menu_main_logout);
		Button buttonAnnotateImage = (Button) findViewById(R.id.main_button_annotateImage);
		Button buttonTimelapseVideo = (Button) findViewById(R.id.main_button_timelapseVideo);
		
		if(isLoggedIn) {
			miLogin.setVisible(false);
			miLogout.setVisible(true);
			buttonAnnotateImage.setEnabled(true);
			buttonTimelapseVideo.setEnabled(true);}
		else {
			miLogin.setVisible(true);
			miLogout.setVisible(false);
			buttonAnnotateImage.setEnabled(false);
			buttonTimelapseVideo.setEnabled(false);}
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		
		switch(id) {
		case R.id.menu_main_login:
			String username = "mobileappdemo@eagleeyenetworks.com";
			String password = "fishfilet3200";
			
			HttpAaa.login(username, password, new Runnable() {
				@Override public void run() {
					Toast.makeText(MainActivity.this, "Login is successful", Toast.LENGTH_SHORT).show();
					isLoggedIn = true;
					updateView();
				}
			});
			return true;
		case R.id.menu_main_logout:
			
			HttpAaa.logoutPost(new TextHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, Header[] headers, String response) {
					Toast.makeText(MainActivity.this, "Logout is successful", Toast.LENGTH_SHORT).show();
					isLoggedIn = false;
					updateView();
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, String response, Throwable error) {
				}
			});
			
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
}
