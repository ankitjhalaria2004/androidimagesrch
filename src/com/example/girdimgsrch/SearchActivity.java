package com.example.girdimgsrch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity {
	EditText etQuery;
	GridView gvResults;
	Button btnSearch;
	int i = 0;
	ArrayList<ImageResults> imgResults = new ArrayList<ImageResults>();
	ImageResultsArrayAdapter imageAdapter;
	public static final String PREFS_NAME = "SettingsFile";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setupViews();
		imageAdapter = new ImageResultsArrayAdapter(this, imgResults);
		gvResults.setAdapter(imageAdapter);
		gvResults.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View parent, int position,
					long arg3) {
				Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
				ImageResults imageResult = imgResults.get(position);
				i.putExtra("result", imageResult);
				startActivity(i);
			}
			
		});
	}

	private void setupViews() {
		etQuery = (EditText) findViewById(R.id.etQuery);
		btnSearch = (Button)findViewById(R.id.btnSearch);
		gvResults = (GridView)findViewById(R.id.gvImgPlcHolder);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}
	
	public void loadSettingsModule(MenuItem m){
		Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
		startActivity(i);
	}
	
	/**
	 * A very quick and dirty way to get more images.
	 * Simply incrementing the value of a global variable i and passing it to query google.
	 * @param v
	 */
	public void loadMore(View v) {
		loadImageData();
	}

	public void loadImageData() {
		/**
		 * Get the values from the SharedPreferences Object
		 */

		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		String image_size = settings.getString("imgSize", "small");
		String color_filter = settings.getString("colorFilter", "blue");
		String image_type = settings.getString("imgType", "faces");
		String site_filter = settings.getString("siteFilter", "yahoo.com");
		
		Log.d("DEBUG", image_size);
		Log.d("DEBUG", color_filter);	
		Log.d("DEBUG", image_type);
		Log.d("DEBUG", site_filter);
		
		String query = etQuery.getText().toString();
		Toast.makeText(this, "Searching for " + query, Toast.LENGTH_LONG)
			.show();
		AsyncHttpClient httpClient = new AsyncHttpClient();
		httpClient.get("https://ajax.googleapis.com/ajax/services/search/images?rsz=8&" + 
				"start=" + ++i + "&v=1.0&q=" + Uri.encode(query) + "&imgcolor=" + color_filter
				+ "&imgtype=" + image_type + "&imgsz=" + image_size + "&as_sitesearch=" + 
				Uri.encode(site_filter),
				new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				JSONArray imgJsonResults = null;
				try {
					imgJsonResults = response.getJSONObject("responseData").getJSONArray("results");
					imgResults.clear();
					imageAdapter.addAll(ImageResults.fromJSONArray(imgJsonResults));
					Log.d("DEBUG" , imgResults.toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void onImgSrch(View v) {		
		loadImageData();
	}
}
