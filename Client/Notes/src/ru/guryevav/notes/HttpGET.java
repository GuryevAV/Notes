package ru.guryevav.notes;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

class HttpGET extends AsyncTask<String, Void, String> {
	
	private ProgressDialog dialog;
	private final Context mCtx;
	
		
	public HttpGET(Context ctx) {
		mCtx = ctx;
	}
	
    @Override
    protected String doInBackground(String... url) {
    	String line = "";
    	try {
    		HttpClient httpclient = new DefaultHttpClient();
    		HttpGet httpget = new HttpGet(url[0]);
    	    Log.d("1", "������ ���������");
    	    HttpResponse response = httpclient.execute(httpget);
    	    HttpEntity httpEntity = response.getEntity();
    	    line = EntityUtils.toString(httpEntity, "UTF-8");
    	    Log.d("1", line);
    	} catch (ClientProtocolException e) {
    		Log.d("1", "��������");
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		Log.d("1", "������ �� ���������");
    	}
        return line;
    }

    @Override
    protected void onPostExecute(String result) {
            dialog.dismiss();
            super.onPostExecute(result);
    }

    @Override
    protected void onPreExecute() {
            dialog = new ProgressDialog(mCtx);
            dialog.setMessage(mCtx.getString(R.string.data_get));
            dialog.setIndeterminate(true);
            dialog.setCancelable(true);
            dialog.show();
            super.onPreExecute();
    }
    
    
}
