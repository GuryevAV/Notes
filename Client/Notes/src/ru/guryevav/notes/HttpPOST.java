package ru.guryevav.notes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

class HttpPOST extends AsyncTask<String, Void, String[]> {
	
	private ProgressDialog dialog;
	private final Context mCtx;
	HttpClient httpclient;
			
	public HttpPOST(Context ctx) {
		mCtx = ctx;
	}
	
    @Override
    protected String[] doInBackground(String... data) {
    	
    	// Create a new HttpClient and Post Header
	    HttpPost httppost = new HttpPost(data[0]);
	    HttpResponse response = null;
	    String[] result = {"", ""};
	    try {
	        //Добавляем свои данные
	    	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	    	if (!(data[1] == "")) {
		        nameValuePairs.add(new BasicNameValuePair("username", data[1]));
	    	}
	    	if (!(data[2] == "")) {
		        nameValuePairs.add(new BasicNameValuePair("text",  data[2]));
	    	}
	    	if (!(data[3] == "")) {
		        nameValuePairs.add(new BasicNameValuePair("salt",  data[3]));
	    	}
	    	if (!(data[4] == "")) {
		        nameValuePairs.add(new BasicNameValuePair("hash",  data[4]));
	    	}
	    	if (!(data[5] == "")) {
	    		nameValuePairs.add(new BasicNameValuePair("_id",  data[5]));
	    	}
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"utf-8"));

	        // Выполняем HTTP Post Request
	        httpclient = MainActivity.httpclient;
	        response = httpclient.execute(httppost);
	        result[0] = response.getStatusLine().getStatusCode() + "";
	        HttpEntity httpEntity = response.getEntity();
	        result[1] = EntityUtils.toString(httpEntity, "UTF-8");
    	    
	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    }
		return result;
    }

    @Override
    protected void onPostExecute(String[] result) {
            dialog.dismiss();
            super.onPostExecute(result);
    }

    @Override
    protected void onPreExecute() {
            dialog = new ProgressDialog(mCtx);
            dialog.setMessage(mCtx.getString(R.string.data_post));
            dialog.setIndeterminate(true);
            dialog.setCancelable(true);
            dialog.show();
            super.onPreExecute();
    }
    
    @Override
    protected void onCancelled() {
    	super.onCancelled();
    }
}
