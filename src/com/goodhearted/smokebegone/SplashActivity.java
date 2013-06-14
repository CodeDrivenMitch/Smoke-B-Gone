package com.goodhearted.smokebegone;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Window;

public class SplashActivity extends Activity {

	static boolean already_ran = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		if(already_ran) {
			startActivity(new Intent(SplashActivity.this, MenuActivity.class));
		} else {
			Thread timer = new Thread(){
				public void run() {
					try {
						sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						startActivity(new Intent(SplashActivity.this, MenuActivity.class));
					}
				}
			};
			already_ran = true;
			timer.start();
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
}
