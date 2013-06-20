package com.goodhearted.smokebegone;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

public class MoneyActivity extends Activity {

	private MenuHandler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_money);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		readyMenu();
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
