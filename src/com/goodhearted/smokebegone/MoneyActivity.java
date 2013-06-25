package com.goodhearted.smokebegone;

import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class MoneyActivity extends Activity {

	private MenuHandler handler;
	
	TextView day, week, year;
	SmokeDataSource DAO;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_money);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		readyMenu();
		
		day = (TextView) findViewById(R.id.tvSavePerDay);
		week = (TextView) findViewById(R.id.tvSavePerWeek);
		year = (TextView) findViewById(R.id.tvSavePerYear);
		DAO = new SmokeDataSource(this);
		
		calculate();
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		SlideHolder x = (SlideHolder) findViewById(R.id.bla);
		x.toggle();
		return true;

	}

	private void readyMenu() {
		handler = new MenuHandler(this);
		for (int i = 0; i < MenuHandler.allMenuItems.length; i++) {
			findViewById(MenuHandler.allMenuItems[i]).setOnClickListener(handler);
		}
	}
	
	private void calculate()
	{
		float saveperday = (new Period((long)((new Date()).getTime())-Period.day, new Date().getTime())).getSave(this, 0);
		day.setText("Wat je per dag bespaard: " + saveperday);
		week.setText("Wat je per maand bespaard: " + saveperday*7);
		year.setText("Wat je per jaar bespaard: " + saveperday*365);
	}
}
