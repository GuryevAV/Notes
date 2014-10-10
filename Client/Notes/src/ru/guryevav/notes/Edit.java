package ru.guryevav.notes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Edit extends Activity {

	EditText etNote;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit);
		
		etNote = (EditText) findViewById(R.id.etNote);
	}
	
	public void onClick(View v) {
		if (TextUtils.isEmpty(etNote.getText())) {
			Toast.makeText(this, getString(R.string.note_is_empty), Toast.LENGTH_SHORT).show();
		} else {
			Intent intent = new Intent();
			intent.putExtra("text", etNote.getText().toString().trim());
			setResult(RESULT_OK, intent);
			finish();
		}
	}
}
