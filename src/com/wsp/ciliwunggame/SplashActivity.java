package com.wsp.ciliwunggame;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class SplashActivity extends Activity {
	
	public int _screenWidth;
	public int _screenHeight;
	
	TextView textProgressBar;
	int total = 0;
	boolean isRunning = false;
	public static Typeface _tf;

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			total += 5;
			String perc = String.valueOf(total).toString();
			textProgressBar.setText(perc + "% Loading...");
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);
		
		Display display = getWindowManager().getDefaultDisplay();
		_screenWidth = display.getWidth();
		_screenHeight = display.getHeight();
		
		_tf = Typeface.createFromAsset(getAssets(),
				"data/fonts/dk_crayon_crumble.ttf");
		
		setContentView(R.layout.splash);
		
		LinearLayout.LayoutParams param_textProgressBar = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
		param_textProgressBar.gravity = Gravity.CENTER;
		param_textProgressBar.setMargins(0, (int) (_screenHeight*0.5), 0, 0);
		textProgressBar = (TextView) findViewById(R.id.tvProgressBars);
		textProgressBar.setTextSize((float) (_screenWidth*0.035));
		textProgressBar.setLayoutParams(param_textProgressBar);
		textProgressBar.setGravity(Gravity.CENTER);
		textProgressBar.setTypeface(_tf);

	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStart() {
		super.onStart();

		Thread bg = new Thread(new Runnable() {

			public void run() {
				try {
					for (int i = 0; i < 20 && isRunning; i++) {
						Thread.sleep(500);
						handler.sendMessage(handler.obtainMessage());
					}
					Thread.sleep(500);
				} catch (Throwable t) {
					t.printStackTrace();
				} finally {
					startActivity(new Intent("com.wsp.ciliwunggame.MENU"));
					SplashActivity.this.finish();
				}
			}
		});

		isRunning = true;
		bg.start();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		isRunning = false;
	}
}
