package ru.guryevav.notes;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import android.app.ActionBar;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Notes extends FragmentActivity {
	
	// ����� ��������� ��� Map
	final String ATTRIBUTE_NAME_TEXT = "text";
	final String ATTRIBUTE_NAME_DATE = "date";
	final String ATTRIBUTE_NAME_IMAGE = "image";
	
	int img = android.R.drawable.ic_menu_close_clear_cancel;//R.drawable.ic_launcher;
	
	ListView lvNotes;
	HttpGET httpGET;
	HttpPOST httpPOST;
	Intent intent;
	private static final int ACT_EDIT = 1;
	String userName;
	SimpleAdapter sAdapter;
	ArrayList<String> textArray, dateArray;
	ArrayList<Map<String, Object>> data;
	//Handler h;
	String[] from;
	int[] to;
	Map<String, Object> m;
	RelativeLayout rLayout;
	TextView tv;
	String[] dataPOST = {"", "", "", "", ""};
	Integer httpResult;
	DialogFragment dlgDelete;
	View myV;
	
	/*Handler.Callback hc = new Handler.Callback() {
        public boolean handleMessage(Message msg) {
        	myList();
        	return false;
        }
    };*/
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notes);
		
		//h = new Handler(hc);
					    
		textArray = new ArrayList<String>();
		dateArray = new ArrayList<String>();
		intent = getIntent();
		userName = intent.getStringExtra("user_name");
		
		ActionBar actionBar = getActionBar();
        actionBar.setTitle(userName);
		
		myList();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_exit) {
			finish();
		}
		return true;
	}
	
	public void onClick(View v) {
		switch (v.getId()) {
        case R.id.btnAdd:
			intent = new Intent(this, Edit.class);
			startActivityForResult(intent, ACT_EDIT);
			break;
		case R.id.btnDeleteNote:
			myV = v;
			dlgDelete = new DialogDelete();
			dlgDelete.show(getFragmentManager(), "dlgDelete");
		break;
		}
    }
	
	public void deleteNote() {
		rLayout = (RelativeLayout) myV.getParent();
		tv = (TextView) rLayout.getChildAt(0);
		dataPOST[0] = Urls.URL + Urls.URL_NOTE_REMOVE;
		dataPOST[1] = userName; 
		dataPOST[2] = tv.getText().toString();
		dataPOST[3] = "";
		httpPOST = new HttpPOST(this);
		httpPOST.execute(dataPOST);
		httpResult = 0;
		try {
			httpResult = httpPOST.get();
			if (httpResult == 200) {
				for (int i = 0; i < data.size(); i++) {
					if (data.get(i).get(ATTRIBUTE_NAME_TEXT) == tv.getText().toString()) {
						data.remove(i);
						sAdapter.notifyDataSetChanged();
					};
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void onBackPressed() {
		finish();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == ACT_EDIT) {
			if (resultCode == RESULT_OK) {
				//Toast.makeText(this, intent.getStringExtra("text"), Toast.LENGTH_SHORT).show();
				dataPOST[0] = Urls.URL + Urls.URL_NOTE;
				dataPOST[1] = userName; 
				dataPOST[2] = intent.getStringExtra("text");
				dataPOST[3] = "";
				httpPOST = new HttpPOST(this);
				httpPOST.execute(dataPOST);
				httpResult = 0;
				try {
					httpResult = httpPOST.get();
					if (httpResult == 200) {
						String currentDateTimeString = (String) DateFormat.format("yyyy-MM-dd kk:mm:ss",new Date());
						m = new HashMap<String, Object>();
					      m.put(ATTRIBUTE_NAME_TEXT, dataPOST[2]);
					      m.put(ATTRIBUTE_NAME_DATE, currentDateTimeString);
					      m.put(ATTRIBUTE_NAME_IMAGE, img);
					      data.add(m);
					      sAdapter.notifyDataSetChanged();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//h.sendEmptyMessageDelayed(1, 1000);
			}
		}
	}
	
	void myList() {
		httpGET = new HttpGET(this);
		httpGET.execute(Urls.URL + Urls.URL_NOTES + userName);
		
		if (!(httpGET == null)) {
			try {
		      String httpResult = "";
		      httpResult = httpGET.get();
		      textArray = JSONParsing.JSONNotes(httpResult, "text");
		      dateArray = JSONParsing.JSONNotes(httpResult, "created");
		      
		    } catch (InterruptedException e) {
		      e.printStackTrace();
		    } catch (ExecutionException e) {
		      e.printStackTrace();
		    }
			
			// ����������� ������ � �������� ��� �������� ���������
		    data = new ArrayList<Map<String, Object>>(textArray.size());
		    for (int i = 0; i < textArray.size(); i++) {
		      m = new HashMap<String, Object>();
		      m.put(ATTRIBUTE_NAME_TEXT, textArray.get(i));
		      m.put(ATTRIBUTE_NAME_DATE, dateArray.get(i));
		      m.put(ATTRIBUTE_NAME_IMAGE, img);
		      data.add(m);
		    }
		    
		    // ������ ���� ���������, �� ������� ����� �������� ������
			String[] from = { ATTRIBUTE_NAME_TEXT, ATTRIBUTE_NAME_DATE,
		        ATTRIBUTE_NAME_IMAGE };
		    // ������ ID View-�����������, � ������� ����� ��������� ������
		    int[] to = { R.id.tvNoteText, R.id.tvNoteDate, R.id.btnDeleteNote };
		    
		    // ������� �������
		    sAdapter = new SimpleAdapter(this, data, R.layout.item,
		        from, to);
		    // ���������� ������ � ����������� ��� �������
		    lvNotes = (ListView) findViewById(R.id.lvNotes);
		    lvNotes.setAdapter(sAdapter);
		}
	}
}
