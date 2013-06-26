package com.goodhearted.smokebegone;

import java.util.Date;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

public class HealthActivity extends Activity {

	MenuHandler handler;

	SmokeDataSource DAO;
	
	ProgressbarUpdate progressupdater;

	private static final int[] progress_sls_bid = { R.id.progressBar1,
			R.id.progressBar2, R.id.progressBar3, R.id.progressBar4,
			R.id.progressBar5, R.id.progressBar6, R.id.progressBar7,
			R.id.progressBar8, R.id.progressBar9, };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_health);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		new MenuHandler(this);
		DAO = new SmokeDataSource(this);

		updateProgressBars();

	}

	@Override
	protected void onPause() {
		super.onPause();
		progressupdater.cancel(true);
		progressupdater = null;
	}

	@Override
	protected void onResume() {
		super.onResume();
		progressupdater = new ProgressbarUpdate(this);
		progressupdater.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.health, menu);
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

	public boolean onOptionsItemSelected(MenuItem item) {
		SlideHolder x = (SlideHolder) findViewById(R.id.bla);
		x.toggle();
		return true;

	}

	public void updateProgressBars() {

		int[] progress_sls = new int[Period.benefits.length];

		Period sincelastsmoke;

		Smoke lastsmoke = DAO.getLastSmoke();

		if (lastsmoke != null) {
			sincelastsmoke = new Period(lastsmoke.getDateInt(),
					new Date().getTime());
		} else {
			sincelastsmoke = new Period(PreferenceProvider.readLong(this,
					PreferenceProvider.keyQD, -1), new Date().getTime());
		}

		for (int i = 0; i < Period.benefits.length; i++) {
			progress_sls[i] = sincelastsmoke.getBenefitProgress(i);

		}

		for (int i = 0; i < progress_sls_bid.length; i++) {
			((ProgressBar) findViewById(progress_sls_bid[i]))
					.setProgress(progress_sls[i]);

		}

	}
}

class ProgressbarUpdate extends AsyncTask<Void, Void, Void> {

	HealthActivity act;
	TextView tv;
	ProgressUpdater tu;

	//runnable for executing on UI thread
	
	private class ProgressUpdater implements Runnable {

		HealthActivity act;

		public ProgressUpdater(HealthActivity z) {
			this.act = z;
		}

		@Override
		public void run() {
			
			this.act.updateProgressBars();
		}

	}
	
	//constructor
	public ProgressbarUpdate(HealthActivity act) {
		this.act = act;
		tu = new ProgressUpdater(this.act);
		this.act.runOnUiThread(this.tu);
	}
	//sleeping sequence
	@Override
	protected Void doInBackground(Void... arg0) {
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				Log.d("SMB_THREAD", "Progress bar update interrupted!");
			} finally {
				//execute the declared runnable on the UI thread
				this.act.runOnUiThread(this.tu);
			}
		}
	}

}

