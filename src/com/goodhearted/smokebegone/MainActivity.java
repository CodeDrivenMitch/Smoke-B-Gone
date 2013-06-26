package com.goodhearted.smokebegone;

import java.math.BigDecimal;
import java.util.Date;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	SmokeDataSource DAO;
	TextView tvDays, tvHours, tvMinutes, tvSeconds, tvDaysSuffix, tvHoursSuffix, tvMinutesSuffix, tvSecondsSuffix, tvSaving;
	Button plus, info;

	MenuHandler handler;
	TextUpdate tu;
	
	/**
	 * Constructor for the acitivity, initializes variables, menu and button listeners
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		setContentView(R.layout.activity_main);
		
		initializeVariables();
	}
	
	/**
	 * Initializes the class variables
	 */
	public void initializeVariables()
	{
		DAO = new SmokeDataSource(this);
		
		plus = (Button) findViewById(R.id.btStillSmoked);
		plus.setOnClickListener(this);
		
		tvDays = (TextView) findViewById(R.id.tvDayCount);
		tvHours = (TextView) findViewById(R.id.tvHourCount);
		tvMinutes = (TextView) findViewById(R.id.tvMinuteCount);
		tvSeconds = (TextView) findViewById(R.id.tvSecondCount);
		tvSaving = (TextView) findViewById(R.id.tvMASavings);
		
		new MenuHandler(this);
	}
	
	/**
	 * When resuming the app, create the update asynctask and run it
	 */
	@Override
	protected void onResume() {
		super.onResume();
		tu = new TextUpdate(this);
		tu.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	}

	/**
	 * When pausing the app, cancel the asynctask
	 */
	@Override
	protected void onPause() {
		super.onPause();
		tu.cancel(true);
		tu = null;
	}
	
	/**
	 * Opens or closes the menu on clicking the logo
	 */
	public boolean onOptionsItemSelected(MenuItem item) {
		SlideHolder slideholder = (SlideHolder) findViewById(R.id.bla);
		slideholder.toggle();
		return true;

	}
	
	/**
	 * Closes menu (if open) on back button click
	 */	
	@Override
	public void onBackPressed() {
		SlideHolder slideholder = (SlideHolder) findViewById(R.id.bla);
		if(slideholder.isOpened()) slideholder.close();
		else super.onBackPressed();
	}

	/**
	 * Function for handling button actions
	 */

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btStillSmoked:
			Smoke x = DAO.getLastSmoke();
			if(x != null) {
				Period p = new Period(x.getDateInt(), (new Date()).getTime());
				if(p.getPeriod() > 1000) DAO.createSmoke();
			} else {
				DAO.createSmoke();
			}
			break;
		}

		updateTV();
	}
	
	/**
	 * Updates the text fields, used for the runnable in the AsyncTask
	 */

	public void updateTV() {
		Period d, e;

		Smoke smoke = DAO.getLastSmoke();

		long quitsmoketime = PreferenceProvider.readLong(this,
				PreferenceProvider.keyQD, -1);
		if(smoke != null){
		e = new Period(smoke.getDateInt(), (new Date()).getTime());
		} else {
			e = new Period(quitsmoketime, (new Date()).getTime());
		}
		
		tvDays.setText(Integer.toString(e.getDays()));
		tvHours.setText(Integer.toString(e.getHours()));
		tvMinutes.setText(Integer.toString(e.getMinutes()));
		tvSeconds.setText(Integer.toString(e.getSeconds()));
		
		
		d = new Period(quitsmoketime, (new Date().getTime()));
		float savings = d.getSave(this, DAO.getTotalSmokes());
		int scale = 0;
		if (savings < 100) {
			scale = 2;
		}
		BigDecimal z = new BigDecimal(String.valueOf(savings)).setScale(scale,
				BigDecimal.ROUND_HALF_UP);
		tvSaving.setText("In totaal heb je \u20ac" + z.toString() + " bespaard!");
		
	}

}
/**
 * Updates the text parts of the main activity every second after created and untill cancelled
 * 
 */

class TextUpdate extends AsyncTask<Void, Void, Void> {

	MainActivity act;
	TextView tv;
	TextUpdater tu;

	//runnable for executing on UI thread
	
	private class TextUpdater implements Runnable {

		MainActivity act;

		public TextUpdater(MainActivity z) {
			this.act = z;
		}

		@Override
		public void run() {
			
			this.act.updateTV();
		}

	}
	
	//constructor
	public TextUpdate(MainActivity act) {
		this.act = act;
		tu = new TextUpdater(this.act);
		this.act.runOnUiThread(this.tu);
	}
	//sleeping sequence
	@Override
	protected Void doInBackground(Void... arg0) {
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				Log.d("SMB_THREAD", "MainActivity info update interrupted!");
			} finally {
				//execute the declared runnable on the UI thread
				this.act.runOnUiThread(this.tu);
			}
		}
	}

}
