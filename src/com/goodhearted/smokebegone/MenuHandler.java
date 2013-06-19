package com.goodhearted.smokebegone;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class MenuHandler implements OnClickListener {

	public static final int[] allitems = {R.id.bmHome, R.id.bmInfo, R.id.bmSettings};
	public static final Class[] actions = {MainActivity.class, Info_Activity.class, WelcomeActivity.class};
	
	private Activity mother_activity;
	
	public MenuHandler(Activity activity) {
		mother_activity = activity;
	}
	@Override
	public void onClick(View arg0) {
		for(int i = 0; i < allitems.length; i++)
		{
			if(arg0.getId() == allitems[i]) {
				Intent j = new Intent(mother_activity, actions[i]);
				mother_activity.startActivity(j);
			}
		}
	}

}
