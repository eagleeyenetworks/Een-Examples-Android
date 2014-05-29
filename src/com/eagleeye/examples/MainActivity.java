package com.eagleeye.examples;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.eagleeye.sdk.http.HttpAaa;

public class MainActivity extends Activity {

	Menu menu;
	
	boolean isLoggedIn = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.menu = menu;
		getMenuInflater().inflate(R.menu.menu_main, menu);
		updateMenuItems();
		return true;
	}

	private void updateMenuItems() {
		if(menu == null) {
			return;}
		
		MenuItem miLogin = menu.findItem(R.id.menu_main_login);
		MenuItem miLogout = menu.findItem(R.id.menu_main_logout);
		
		if(isLoggedIn) {
			miLogin.setVisible(false);
			miLogout.setVisible(true);
		}
		else {
			miLogin.setVisible(true);
			miLogout.setVisible(false);
		}
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
				}
			});
			return true;
		case R.id.menu_main_logout:
			Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
}
