package com.example.girdimgsrch;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ImageResults implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7986743285626021180L;
	private String fullUrl;
	private String thumbUrl;
	
	public ImageResults(JSONObject json) {
		try {
			this.fullUrl = json.getString("url");
			this.thumbUrl = json.getString("tbUrl");
		} catch (JSONException e) {
			this.fullUrl = null;
			this.thumbUrl = null;
		}
	}
	
	public String getThumbUrl() {
		return thumbUrl;
	}
	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}
	public String getFullUrl() {
		return fullUrl;
	}
	public void setFullUrl(String fullUrl) {
		this.fullUrl = fullUrl;
	}
	
	public String toString() {
		return fullUrl + thumbUrl;
	}

	public static ArrayList<ImageResults> fromJSONArray(
			JSONArray array) {
		ArrayList<ImageResults> results = new ArrayList<ImageResults>();
		for (int i = 0; i < array.length(); i++) {
			try {
				results.add(new ImageResults(array.getJSONObject(i)));
			} catch (JSONException e) {
				e.getStackTrace();
			}
		}
		return results;
	}
	
}
