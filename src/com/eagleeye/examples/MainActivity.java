package com.eagleeye.examples;

import org.apache.http.Header;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

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
				Intent intent = new Intent(getApplicationContext(), ActivityAnnotateImage.class);
				startActivity(intent);
			}
		});

		Button buttonSearchAnnotation = (Button) findViewById(R.id.main_button_searchAnnotation);
		buttonSearchAnnotation.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), ActivitySearchAnnotation.class);
				startActivity(intent);
			}
		});
		
		Button buttonTimelapseVideo = (Button) findViewById(R.id.main_button_timelapseVideo);
		buttonTimelapseVideo.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), ActivityTimelapseVideo.class);
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateView();	
	}

	private void updateView() {
		if(menu == null) {
			return;}

		MenuItem miLogin = menu.findItem(R.id.menu_main_login);
		MenuItem miLogout = menu.findItem(R.id.menu_main_logout);
		Button buttonAnnotateImage = (Button) findViewById(R.id.main_button_annotateImage);
		Button buttonTimelapseVideo = (Button) findViewById(R.id.main_button_timelapseVideo);
		Button buttonSearchAnnotation = (Button) findViewById(R.id.main_button_searchAnnotation);

		if(isLoggedIn) {
			miLogin.setVisible(false);
			miLogout.setVisible(true);
			buttonAnnotateImage.setEnabled(true);
			buttonTimelapseVideo.setEnabled(true);
			buttonSearchAnnotation.setEnabled(true);}
		else {
			miLogin.setVisible(true);
			miLogout.setVisible(false);
			buttonAnnotateImage.setEnabled(false);
			buttonTimelapseVideo.setEnabled(false);
			buttonSearchAnnotation.setEnabled(false);}

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.menu = menu;
		getMenuInflater().inflate(R.menu.menu_main, menu);
		updateView();
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		switch(id) {
		case R.id.menu_main_login:
			createAndShowLoginDialog();
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

	private void createAndShowLoginDialog() {
		
		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		final LinearLayout llContainer = new LinearLayout(this);
		llContainer.setOrientation(LinearLayout.VERTICAL);
		
		final EditText etEmail = new EditText(this);
		etEmail.setHint("Email");
		final EditText etPassword = new EditText(this);
		etPassword.setHint("Password");
		etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		
		llContainer.addView(etEmail);
		llContainer.addView(etPassword);
		alert.setView(llContainer);
		
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String email = etEmail.getText().toString().trim();
				String password = etPassword.getText().toString().trim();
				
//				String email = "mobileappdemo@eagleeyenetworks.com";
//				String password = "fishfilet3200";
				
				HttpAaa.login(email, password, new Runnable() {
					@Override public void run() {
						Toast.makeText(MainActivity.this, "Login is successful", Toast.LENGTH_SHORT).show();
						isLoggedIn = true;
						updateView();
					}
				}, new Runnable() {
					@Override
					public void run() {
						Toast.makeText(MainActivity.this, "Login is unsuccessful", Toast.LENGTH_SHORT).show();
					}
				});
			}
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.cancel();
			}
		});
		alert.show();
	}
}
