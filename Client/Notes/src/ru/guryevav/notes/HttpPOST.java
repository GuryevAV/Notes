package ru.guryevav.notes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

class HttpPOST extends AsyncTask<String, Void, Integer> {
	
	private ProgressDialog dialog;
	private final Context mCtx;
			
	public HttpPOST(Context ctx) {
		mCtx = ctx;
	}
	
    @Override
    protected Integer doInBackground(String... data) {
    	
    	Integer result = 0;
    	
    	// Create a new HttpClient and Post Header
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost(data[0]);

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
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"utf-8"));

	        // Выполняем HTTP Post Request
	        HttpResponse response = httpclient.execute(httppost);
	        result = response.getStatusLine().getStatusCode();
	        //HttpEntity httpEntity = response.getEntity();
    	    //line = EntityUtils.toString(httpEntity, "UTF-8");
    	    
	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    }
		return result;
    }

    @Override
    protected void onPostExecute(Integer result) {
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
}
