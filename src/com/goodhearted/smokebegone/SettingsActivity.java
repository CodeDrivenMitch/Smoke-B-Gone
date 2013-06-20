package com.goodhearted.smokebegone;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SettingsActivity extends Activity {

	MenuHandler handler;
	EditText CPP, CPD, PPP;
	Button save;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		readyMenu();

		CPP = (EditText) findViewById(R.id.etsCPP);
		CPD = (EditText) findViewById(R.id.etsCPD);
		PPP = (EditText) findViewById(R.id.etsPPP);

		CPP.setText(""
				+ (PreferenceProvider.readInteger(this,
						PreferenceProvider.keyCPP, -1)));
		CPD.setText(""
				+ (PreferenceProvider.readInteger(this,
						PreferenceProvider.keyCPD, -1)));
		PPP.setText(""
				+ (PreferenceProvider.readFloat(this,
						PreferenceProvider.keyPPP, -1.0f)));

		save = (Button) findViewById(R.id.etsSave);
		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				saveSettings();

			}

		});
	}

	@SuppressWarnings("deprecation")
	private void saveSettings() {
		PreferenceProvider.writeInteger(this, PreferenceProvider.keyCPP,
				Integer.parseInt(CPP.getText().toString()));
		PreferenceProvider.writeFloat(this, PreferenceProvider.keyPPP,
				Float.parseFloat(PPP.getText().toString()));
		PreferenceProvider.writeInteger(this, PreferenceProvider.keyCPD,
				Integer.parseInt(CPD.getText().toString()));

		// display alert!

		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("Instellingen opgeslagen");
		alertDialog.setMessage("Al uw instellingen zijn opgeslagen!");
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		alertDialog.setIcon(R.drawable.ic_launcher);
		alertDialog.show();
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		SlideHolder x = (SlideHolder) findViewById(R.id.bla);
		x.toggle();
		return true;

	}

	private void readyMenu() {
		handler = new MenuHandler(this);
		for (int i = 0; i < MenuHandler.allitems.length; i++) {
			findViewById(MenuHandler.allitems[i]).setOnClickListener(handler);
		}
	}
}
