package com.goodhearted.smokebegone;

import java.math.BigDecimal;
import java.util.Date;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	SmokeDataSource DAO;
	TextView tv, tvsaved;
	Button plus, minus, info;

	MenuHandler handler;
	TextUpdate tu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		setContentView(R.layout.activity_main);

		readyMenu();

		DAO = new SmokeDataSource(this);
		plus = (Button) findViewById(R.id.plusbutton);
		plus.setOnClickListener(this);
		minus = (Button) findViewById(R.id.minusbutton);
		minus.setOnClickListener(this);
		tv = (TextView) findViewById(R.id.tvSMOKES);
		tvsaved = (TextView) findViewById(R.id.tvSAVED);
		updateTV();

	}

	@Override
	protected void onResume() {
		super.onResume();
		tu = new TextUpdate(this);
		tu.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		SlideHolder x = (SlideHolder) findViewById(R.id.bla);
		x.toggle();
		return true;

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.plusbutton:
			DAO.createSmoke();
			break;
		case R.id.minusbutton:
			if (DAO.getTotalSmokes() > 0) {
				DAO.removeLastSmoke(DAO.getLastSmoke());
			}
			break;

		}

		updateTV();
	}

	public void updateTV() {
		Period d, e;

		Smoke x = DAO.getLastSmoke();

		long quitsmoketime = PreferenceProvider.readLong(this,
				PreferenceProvider.keyQD, -1);
		if(x != null){
		e = new Period(x.getDateInt(), (new Date()).getTime());
		} else {
			e = new Period(quitsmoketime, (new Date()).getTime());
		}
		tv.setText("Tijd sinds laatste sigaret: \n" + e.getString());
		
		
		d = new Period(quitsmoketime, (new Date().getTime()));
		float savings = d.getSave(this, DAO.getTotalSmokes());
		int scale = 0;
		if (savings < 100) {
			scale = 2;
		}
		BigDecimal z = new BigDecimal(String.valueOf(savings)).setScale(scale,
				BigDecimal.ROUND_HALF_UP);
		tvsaved.setText("You saved: " + z.toString() + " euro");
	}

	private void readyMenu() {
		handler = new MenuHandler(this);
		for (int i = 0; i < MenuHandler.allMenuItems.length; i++) {
			findViewById(MenuHandler.allMenuItems[i]).setOnClickListener(handler);
		}
	}

}

class TextUpdate extends AsyncTask<Void, Void, Void> {

	MainActivity act;
	TextView tv;
	TextUpdater tu;

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

	public TextUpdate(MainActivity act) {
		this.act = act;
		tu = new TextUpdater(this.act);
		this.act.runOnUiThread(this.tu);
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				this.act.runOnUiThread(this.tu);
			}
		}
	}

}
