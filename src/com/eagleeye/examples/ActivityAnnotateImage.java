package com.eagleeye.examples;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.eagleeye.examples.util.UtilLogger;
import com.eagleeye.sdk.enumerated.E_AssetClass;
import com.eagleeye.sdk.http.HttpAnnotation;
import com.eagleeye.sdk.http.HttpAsset;
import com.eagleeye.sdk.http.HttpDevice;
import com.eagleeye.sdk.pojo.PojoDeviceList;
import com.eagleeye.sdk.pojo.PojoDeviceList.Device;
import com.eagleeye.sdk.util.UtilHeader;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ActivityAnnotateImage extends Activity_Base{
	
	final E_AssetClass assetClass = E_AssetClass.PRE;

	String cameraId = "";
	String timestamp = "now";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_annotateimage);
		
		Button buttonPrev = (Button) findViewById(R.id.annotateimage_button_prev);
		buttonPrev.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				httpAssetPrevGet(cameraId, timestamp);
			}
		});
		
		Button buttonNext = (Button) findViewById(R.id.annotateimage_button_next);
		buttonNext.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				httpAssetAfterGet(cameraId, timestamp);
			}
		});
		
        httpDeviceListGet();
	}	
	
	// =========================
	// Http Methods
	// =========================
	public void httpDeviceListGet() {
		RequestParams rp = new RequestParams();
		rp.put("t", "camera");
		
		HttpDevice.listGet(rp, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
				UtilLogger.logInfo(TAG, "/device/list onSuccess()");
				UtilLogger.logJsonArray(TAG, response);
				
				PojoDeviceList pojoDeviceList = new PojoDeviceList(response);
				initializeSpinner(pojoDeviceList);
			}
			
			@Override 
			public void onFailure(int statusCode, Header[] headers, String response, Throwable error) {
				UtilLogger.logInfo(TAG, "/device/list GET onFailure()");
			}
		});
	}

	public void httpAssetAssetGet(String cameraId, String timestamp) {
		String[] allowedContentTypes = new String[] {"image/png", "image/jpeg" };
		HttpAsset.AssetGet(cameraId, timestamp, assetClass, new BinaryHttpResponseHandler(allowedContentTypes) {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] fileData) {
				UtilLogger.logInfo(TAG, "/asset/asset/image.jpeg GET onSuccess()");
				
				updateImageViewAndTimestamp(fileData, headers);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] fileData, Throwable error) {
				UtilLogger.logInfo(TAG, "/asset/asset/image.jpeg GET onFailure()");
			}
		});	
	}
	
	public void httpAssetPrevGet(String cameraId, String timestamp) {
		String[] allowedContentTypes = new String[] {"image/png", "image/jpeg" };
		HttpAsset.PrevGet(cameraId, timestamp, assetClass, new BinaryHttpResponseHandler(allowedContentTypes) {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] fileData) {
				UtilLogger.logInfo(TAG, "/asset/prev/image.jpeg GET onSuccess()");
				
				updateImageViewAndTimestamp(fileData, headers);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] fileData, Throwable error) {
				UtilLogger.logInfo(TAG, "/asset/prev/image.jpeg GET onFailure()");
			}
		});	
	}
	
	public void httpAssetAfterGet(String cameraId, String timestamp) {
		String[] allowedContentTypes = new String[] {"image/png", "image/jpeg" };
		HttpAsset.AfterGet(cameraId, timestamp, assetClass, new BinaryHttpResponseHandler(allowedContentTypes) {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] fileData) {
				UtilLogger.logInfo(TAG, "/asset/after/image.jpeg GET onSuccess()");
				
				updateImageViewAndTimestamp(fileData, headers);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] fileData, Throwable error) {
				UtilLogger.logInfo(TAG, "/asset/after/image.jpeg GET onFailure()");
			}
		});	
	}
	
	protected void updateImageViewAndTimestamp(final byte[] fileData, Header[] headers) {
		timestamp = UtilHeader.getValueFor_X_EE_Timestamp_asPreview(headers);
		
		(new AsyncTask<Void, Void, Bitmap>() {
			@Override
			protected Bitmap doInBackground(Void... params) {
				if(fileData.length != 0) {
					BitmapFactory.Options options = new BitmapFactory.Options();
					Bitmap bitmap = BitmapFactory.decodeByteArray(fileData, 0, fileData.length, options);
					return bitmap;
				}
				return null;
			}

			@Override
			protected void onPostExecute(Bitmap result) {
				if(result == null) {
					return;}
				
				ImageView imageView = (ImageView) findViewById(R.id.annotateimage_imageview);
				imageView.setImageBitmap(result);
				
				TextView tvTimestamp = (TextView) findViewById(R.id.annotateimage_tv_timestamp);
				tvTimestamp.setText(timestamp);
			}

		}).execute();
	}

	protected void initializeSpinner(PojoDeviceList pojoDeviceList) {
		final List<Device> devices = pojoDeviceList.devices;
		List<String> adapterList = new ArrayList<String>();
		
		for(Device device : devices) {
			String name = device.name;
			adapterList.add(name);
		}
		
		Spinner spinner = (Spinner) findViewById(R.id.annotateimage_spinner);
         
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, adapterList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);                     
        spinner.setAdapter(dataAdapter);
        
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				Device device = devices.get(position);
			
				cameraId = device.id;
				timestamp = "now";
				httpAssetAssetGet(cameraId, timestamp);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_annotateimage, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		
		switch(id) {
		case R.id.menu_annotateimage_annotate:
			createAndShowAnnotationDialog();
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}

	private void createAndShowAnnotationDialog() {
		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		
		final EditText etAnnotationText = new EditText(this);
		etAnnotationText.setHint("Type in the annotation you sexy beast.");

		alert.setView(etAnnotationText);
		
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String annotationText = etAnnotationText.getText().toString().trim();
				JSONObject data = new JSONObject();
				try {
					data.put("info", annotationText);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				HttpAnnotation.put(ActivityAnnotateImage.this, cameraId, timestamp, data, new JsonHttpResponseHandler(){
					
					@Override
					public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
						UtilLogger.logInfo(TAG, "/annotate PUT onSuccess()");
					}
					
					@Override
					public void onFailure(int statusCode, Header[] headers, String response, Throwable error) {
						UtilLogger.logInfo(TAG, "/annotate PUT onFailure(): " + statusCode);
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
