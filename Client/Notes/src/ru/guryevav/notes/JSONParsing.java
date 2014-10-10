package ru.guryevav.notes;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParsing {
	public static ArrayList<String> JSONNotes(String jsonText, String jsonId) {
    	ArrayList<String> result = new ArrayList<String>();
    	JSONArray jArray;
		try {
			jArray = new JSONArray(jsonText);
			for (int i=0; i<jArray.length(); i++) {
		        JSONObject json_data = jArray.getJSONObject(i);
		        result.add(json_data.getString(jsonId));
		        //Log.d("1", json_data.getString(jsonId));
		    }
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return result;
    }
}
