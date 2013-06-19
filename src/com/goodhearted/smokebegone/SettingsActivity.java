package com.goodhearted.smokebegone;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class SettingsActivity extends Activity {

	MenuHandler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		readyMenu();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}
	private void readyMenu() {
		handler = new MenuHandler(this);
		for(int i = 0; i < MenuHandler.allitems.length; i ++) {
			findViewById(MenuHandler.allitems[i]).setOnClickListener(handler);
		}
	}
}
