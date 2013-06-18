package com.goodhearted.smokebegone;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	SmokeDataSource DAO;
	TextView tv;
	Button plus, minus;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        DAO = new SmokeDataSource(this);
        plus = (Button) findViewById(R.id.plusbutton);
        plus.setOnClickListener(this);
        minus = (Button) findViewById(R.id.minusbutton);
        minus.setOnClickListener(this);
        tv = (TextView) findViewById(R.id.tvSMOKES);
        updateTV();
    }

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Log.d("DBG", "CLICKED");
		switch(arg0.getId()) {
		case R.id.plusbutton:
			Log.d("DBG", " PLUS CLICKED");
			DAO.createSmoke();
			break;
		case R.id.minusbutton:
			Log.d("DBG", " MINUS CLICKED");
			if(DAO.getTotalSmokes() > 0) {
				DAO.removeLastSmoke(DAO.getLastSmoke());
			}
			break;
		}
		
		updateTV();
		Log.d("DBG", "TV UPDATED");
	}
	
	private void updateTV() {
		int total = DAO.getTotalSmokes();
		tv.setText("Total smokes: " + total);
	}
}
