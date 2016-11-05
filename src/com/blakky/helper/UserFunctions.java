package com.blakky.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

public class UserFunctions {

	private Json jsonParser;
	public static final String feedurl = "http://omega.uta.edu/~sas4798/food_get.php";

	public JSONArray getDetails() throws Exception{
		// getting JSON Object
		JSONArray json = jsonParser.getJson(feedurl);
		Log.i("tag", json+"");
		return json;
		
	}
	
}
