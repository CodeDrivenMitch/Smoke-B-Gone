package com.goodhearted.smokebegone;


import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class SplashActivity extends Activity {

	static boolean already_ran = false;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); // Hide title bar for full-screen splash
		setContentView(R.layout.activity_splash);
		
		if(PreferenceProvider.readLong(this, PreferenceProvider.keyQD, -1) == -1) {  		
			Thread timer = new Thread(){
				public void run() {
					try {
						sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						MenuHandler.launchClass(SplashActivity.this, "WelcomeActivity");
					}
				}
			};
			already_ran = true;
			timer.start();
			
		} else if(already_ran){
			MenuHandler.launchClass(SplashActivity.this, "MainActivity");
		} else {
			Thread timer = new Thread(){
				public void run() {
					try {
						sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						MenuHandler.launchClass(SplashActivity.this, "MainActivity");
					}
				}
			};
			already_ran = true;
			timer.start();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}
}
