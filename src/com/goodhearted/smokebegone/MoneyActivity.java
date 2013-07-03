package com.goodhearted.smokebegone;

import java.math.BigDecimal;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class MoneyActivity extends Activity {

	TextView day, week, year;
	SmokeDataSource DAO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_money);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		new MenuHandler(this);

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

	/**
	 * Closes menu (if open) on back button click
	 */
	@Override
	public void onBackPressed() {
		SlideHolder slideholder = (SlideHolder) findViewById(R.id.bla);
		if (slideholder.isOpened())
			slideholder.close();
		else
			super.onBackPressed();
	}

	private void calculate() {
		float saveperday = (new Period((long) ((new Date()).getTime())
				- Period.day, new Date().getTime())).getSaveMoney(this, 0);

		BigDecimal z = new BigDecimal(String.valueOf(saveperday)).setScale(2,
				BigDecimal.ROUND_HALF_UP);
		day.setText("Wat je per dag bespaart:\n \u20ac" + z.toString());
		z = new BigDecimal(String.valueOf(saveperday*31)).setScale(2,
				BigDecimal.ROUND_HALF_UP);
		week.setText("\nWat je per maand bespaart:\n \u20ac" + z.toString());
		z = new BigDecimal(String.valueOf(saveperday*365)).setScale(2,
				BigDecimal.ROUND_HALF_UP);
		year.setText("\nWat je per jaar bespaart:\n \u20ac" + z.toString());
	}
}
