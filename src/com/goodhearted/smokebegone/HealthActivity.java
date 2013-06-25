package com.goodhearted.smokebegone;

import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

public class HealthActivity extends Activity {

	MenuHandler handler;

	SmokeDataSource DAO;

	private static final int[] progress_sls_bid = { R.id.progressBar1, R.id.progressBar2,
			R.id.progressBar3, R.id.progressBar4, R.id.progressBar5,R.id.progressBar6, R.id.progressBar7,
			R.id.progressBar8, R.id.progressBar9, };


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_health);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		readyMenu();
		DAO = new SmokeDataSource(this);

		updateProgressBars();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.health, menu);
		return true;
	}

	private void readyMenu() {
		handler = new MenuHandler(this);
		for (int i = 0; i < MenuHandler.allMenuItems.length; i++) {
			findViewById(MenuHandler.allMenuItems[i]).setOnClickListener(handler);
		}
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		SlideHolder x = (SlideHolder) findViewById(R.id.bla);
		x.toggle();
		return true;

	}

	public void updateProgressBars() {

		int[] progress_sls = new int[Period.benefits.length];
		

		Period sincelastsmoke, sincequit;

		sincequit = new Period(PreferenceProvider.readLong(this,
				PreferenceProvider.keyQD, -1), new Date().getTime());

		Smoke lastsmoke = DAO.getLastSmoke();

		if (lastsmoke != null) {
			sincelastsmoke = new Period(lastsmoke.getDateInt(),
					new Date().getTime());
		} else {
			sincelastsmoke = sincequit;
		}

		for (int i = 0; i < Period.benefits.length; i++) {
			progress_sls[i] = (int) (sincelastsmoke.getPeriod() / (Period.benefits[i] * 0.01));
			
		}

		for (int i = 0; i < progress_sls_bid.length; i++) {
			((ProgressBar) findViewById(progress_sls_bid[i]))
					.setProgress(progress_sls[i]);

			
		}

	}
}
