package com.goodhearted.smokebegone;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class MenuHandler implements OnClickListener {

	public static final int[] allitems = {R.id.bmHome, R.id.bmInfo, R.id.bmSettings, R.id.bmHealth, R.id.bmChart, R.id.bmMoney};
	public static final String[] actions = {"MainActivity", "InfoActivity", "SettingsActivity", "HealthActivity", "XYChartBuilder", "MoneyActivity"};
	
	private Activity mother_activity;
	
	public MenuHandler(Activity activity) {
		mother_activity = activity;
	}
	@Override
	public void onClick(View arg0) {
		for(int i = 0; i < allitems.length; i++)
		{
			if(arg0.getId() == allitems[i]) {
				Class<?> needed = null;
				try {
					needed = Class.forName("com.goodhearted.smokebegone." + actions[i]);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				if(needed != null) {
					Intent j = new Intent(mother_activity, needed);
					mother_activity.startActivity(j);
				}
			}
		}
	}

}
