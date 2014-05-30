package com.eagleeye.examples;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.eagleeye.examples.util.UtilString;
import com.eagleeye.examples.util.UtilToast;

public class ActivitySearchAnnotation extends Activity_Base{


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_searchannotation);
		
		Button buttonValue = (Button) findViewById(R.id.searchannotation_button_value);
		buttonValue.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				createValueDialog();
			}
		});
		
		Button buttonDateTimePicker = (Button) findViewById(R.id.searchannotation_button_datetimepicker);
		buttonDateTimePicker.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				createDateTimePickerDialog();
			}
		});
		
		Button buttonSearch = (Button) findViewById(R.id.searchannotation_button_search);
		buttonSearch.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {
				UtilToast.showToast(ActivitySearchAnnotation.this, "Search has not yet been implemented");
			}
		});
	}
	
	protected void createValueDialog() {
		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		
		final EditText etValue = new EditText(this);
		etValue.setHint("Input Search Query");

		alert.setView(etValue);
		
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String valueString = etValue.getText().toString().trim();
			}
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.cancel();
			}
		});
		alert.show();
	}

	protected void createDateTimePickerDialog() {
		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.dialog_datetimepicker, null);
		alert.setView(layout);
		
		final DatePicker datePicker = (DatePicker) layout.findViewById(R.id.datePicker1);
		final TimePicker timePicker = (TimePicker) layout.findViewById(R.id.timePicker1);
		
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				int year = datePicker.getYear();
				String yearAsString = UtilString.convertIntToString(year, 4);
				
				int month_ZeroIndexed = datePicker.getMonth();
				int month_OneIndexed = month_ZeroIndexed + 1;
				String monthAsString = UtilString.convertIntToString(month_OneIndexed, 2);
				
				int day = datePicker.getDayOfMonth();
				String dayAsString = UtilString.convertIntToString(day, 2);
				
				int hour = timePicker.getCurrentHour();
				String hourAsString = UtilString.convertIntToString(hour, 2);
				
				int minute = timePicker.getCurrentMinute();
				String minuteAsString = UtilString.convertIntToString(minute, 2);
				
				String timestampTarget_WithUtcOffset = String.format("%s%s%s%s%s00.000", yearAsString, monthAsString, dayAsString, hourAsString, minuteAsString);
				UtilToast.showToast(ActivitySearchAnnotation.this, timestampTarget_WithUtcOffset);
				
				Button buttonDateTimePicker = (Button) findViewById(R.id.searchannotation_button_datetimepicker);
				buttonDateTimePicker.setText("Date/Time: " + timestampTarget_WithUtcOffset);
				
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
