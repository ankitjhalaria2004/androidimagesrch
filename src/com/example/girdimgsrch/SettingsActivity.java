package com.example.girdimgsrch;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
public class SettingsActivity extends Activity {
	
	public static final String PREFS_NAME = "SettingsFile";
	Button btnSettings;
	Spinner imgSize;
	Spinner colorFilter;
	Spinner imgType;
	EditText siteFilter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		String image_size = settings.getString("imgSize", "small");
		String color_filter = settings.getString("colorFilter", "blue");
		String image_type = settings.getString("imgType", "faces");
		String site_filter = settings.getString("siteFilter", "google.com");
		

		imgSize = (Spinner) findViewById(R.id.spImgSize);
		colorFilter = (Spinner) findViewById(R.id.spColorFilter);	
		imgType = (Spinner) findViewById(R.id.spImageType);	
		siteFilter = (EditText) findViewById(R.id.etSiteFilter);
		
		ArrayAdapter img_size_adapter = (ArrayAdapter) imgSize.getAdapter();
		int img_size_pos = img_size_adapter.getPosition(image_size);
		imgSize.setSelection(img_size_pos);

		ArrayAdapter color_filter_adapter = (ArrayAdapter) colorFilter.getAdapter();
		int color_filter_pos = color_filter_adapter.getPosition(color_filter);
		colorFilter.setSelection(color_filter_pos);
		
		ArrayAdapter img_type_adapter = (ArrayAdapter) imgType.getAdapter();
		int img_type_pos = img_type_adapter.getPosition(image_type);
		imgType.setSelection(img_type_pos);
		
		siteFilter.setText(site_filter);
		
	}

	public void saveSettings(View v) {
		Intent i = new Intent(getApplicationContext(), SearchActivity.class);
		startActivity(i);
		/**
		 * Get the settings value and store it in a shared preferences object
		 */
		imgSize = (Spinner) findViewById(R.id.spImgSize);
		String image_size = imgSize.getSelectedItem().toString();
		colorFilter = (Spinner) findViewById(R.id.spColorFilter);
		String color_filter = colorFilter.getSelectedItem().toString();		
		imgType = (Spinner) findViewById(R.id.spImageType);
		String image_type = imgType.getSelectedItem().toString();		
		siteFilter = (EditText) findViewById(R.id.etSiteFilter);
		String site_filter = siteFilter.getText().toString();
		SharedPreferences settings = getSharedPreferences(PREFS_NAME,0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("imgSize", image_size);
		editor.putString("colorFilter", color_filter);
		editor.putString("imgType", image_type);
		editor.putString("siteFilter", site_filter);
		editor.commit();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

}
