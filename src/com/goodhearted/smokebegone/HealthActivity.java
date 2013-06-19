package com.goodhearted.smokebegone;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

public class HealthActivity extends Activity {

	MenuHandler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_health);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		readyMenu();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.health, menu);
		return true;
	}

	private void readyMenu() {
		handler = new MenuHandler(this);
		for (int i = 0; i < MenuHandler.allitems.length; i++) {
			findViewById(MenuHandler.allitems[i]).setOnClickListener(handler);
		}
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		SlideHolder x = (SlideHolder) findViewById(R.id.bla);
		x.toggle();
		return true;

	}
}
