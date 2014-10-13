package ru.guryevav.notes;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	TextView tvText;
	Button btnReg, btnAuth;
	Intent intent;
	EditText etLogin, etPassword;
	HttpPOST httpPOST;
	HttpGET httpGET;
	String[] dataPOST = {"", "", "", "", "", ""};
	static HttpClient httpclient = new DefaultHttpClient();
	String[] httpResult = {"", ""};
	String httpResultStr;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		etLogin = (EditText) findViewById(R.id.etLogin); 
		etPassword = (EditText) findViewById(R.id.etPassword);
		
    }
	
	public void onClick(View v) {
		if (checkFields()) {
			try {
				switch (v.getId()) {
				case R.id.btnAuth:
					httpGET = new HttpGET(this);
					httpGET.execute(Urls.URL + Urls.URL_SALT + etLogin.getText().toString());
        		
					if (!(httpGET == null)) {
        				httpResultStr = httpGET.get(5, TimeUnit.SECONDS);
						
						if (!(httpResult.equals("Forbidden"))) {
		        		    dataPOST[0] = Urls.URL + Urls.URL_LOGIN_HASH;
		          			dataPOST[1] = etLogin.getText().toString(); 
		          			dataPOST[2] = "";
		          			dataPOST[3] = "";
		          			dataPOST[4] = Crypto.getHash(etPassword.getText().toString(), httpResultStr);
		          			dataPOST[5] = "";
		          			  
		          			httpPOST = new HttpPOST(this);
		          			httpPOST.execute(dataPOST);
		          			httpResult = httpPOST.get(5, TimeUnit.SECONDS);
		          				if (httpResult[0].equals("200")) {
		          					intent = new Intent(this, Notes.class);
		          					intent.putExtra("user_name", etLogin.getText().toString().trim());
		          					startActivity(intent);
		          				} else {
		          					Toast.makeText(this, getString(R.string.wrong_pass), Toast.LENGTH_LONG).show();
		          				}
	        		      } else {
	      					  Toast.makeText(this, getString(R.string.wrong_login), Toast.LENGTH_LONG).show();
	      				  }
					}
        		break;
        		case R.id.btnReg:
	            	String random = Math.random() + "";
	            	dataPOST[0] = Urls.URL + Urls.URL_LOGIN_HASH;
	            	dataPOST[1] = etLogin.getText().toString(); 
	            	dataPOST[2] = "";
	            	dataPOST[3] = random;
	            	dataPOST[4] = Crypto.getHash(etPassword.getText().toString(), random);
	            	dataPOST[5] = "";
	        			  
	            	httpPOST = new HttpPOST(this);
	            	httpPOST.execute(dataPOST);
	            	httpResult = httpPOST.get(5, TimeUnit.SECONDS);
	            	  	if (httpResult[0].equals("200")) {
	        				intent = new Intent(this, Notes.class);
	        				intent.putExtra("user_name", etLogin.getText().toString().trim());
	        				startActivity(intent);
	        			} else {
	        				Toast.makeText(this, getString(R.string.error_login), Toast.LENGTH_LONG).show();
	        			}
	            break;
				}
			} catch (TimeoutException e) {
				Log.d("1", "get timeout, result = " + httpResult);
				cancelTask();
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
		    } catch (ExecutionException e) {
		    	e.printStackTrace();
		    }
		}
	}
		
	public boolean checkFields() {
		boolean result = false;
		String toastString = "";
		
		if (TextUtils.isEmpty(etLogin.getText().toString())) {
			toastString = getString(R.string.login) + "\n";
			result = false;
		} else result = true;
		if (TextUtils.isEmpty(etPassword.getText().toString())) {
			toastString += getString(R.string.password);
			result = false;
		} else result = true;
		
		if (toastString.length() > 0) Toast.makeText(this, toastString, Toast.LENGTH_LONG).show();
		return result;
	}
	
	private void cancelTask() {
	    if (httpGET == null) return;
	    httpGET.cancel(false);
	    Toast.makeText(this, getString(R.string.error_wait), Toast.LENGTH_LONG).show();
	}
}

