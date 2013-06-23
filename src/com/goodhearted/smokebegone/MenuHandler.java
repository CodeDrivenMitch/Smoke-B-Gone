package com.goodhearted.smokebegone;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class MenuHandler implements OnClickListener {

	public static final int[] allMenuItems = {R.id.bmHome, R.id.bmInfo, R.id.bmSettings, R.id.bmHealth, R.id.bmChart, R.id.bmMoney};
	public static final String[] allMenuActions = {"MainActivity", "InfoActivity", "SettingsActivity", "HealthActivity", "XYChartBuilder", "MoneyActivity"};
	
	private Activity motherActivity;
	
	public MenuHandler(Activity activity) {
		motherActivity = activity;
	}
	@Override
	public void onClick(View viewItem) {
		for(int i = 0; i < allMenuItems.length; i++)
		{
			if(viewItem.getId() == allMenuItems[i]) {
				MenuHandler.launchClass(motherActivity, allMenuActions[i]);
			}
		}
	}
	
	public static void launchClass(Activity motherActivity, String selectedClass)
	{
		Class<?> launchingClass = null;
		try {
			launchingClass = Class.forName("com.goodhearted.smokebegone." + selectedClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if(launchingClass != null) {
			Intent j = new Intent(motherActivity, launchingClass);
			motherActivity.startActivity(j);
		}
	}

}
