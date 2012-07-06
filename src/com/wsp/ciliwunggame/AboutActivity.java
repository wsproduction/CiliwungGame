package com.wsp.ciliwunggame;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class AboutActivity extends Activity implements OnClickListener {
	
	public int _screenWidth;
	public int _screenHeight;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.about);
		
		Display display = getWindowManager().getDefaultDisplay();
		_screenWidth = display.getWidth();
		_screenHeight = display.getHeight();
		
		LinearLayout.LayoutParams param_bBackToMenu = new LinearLayout.LayoutParams((int) (_screenWidth*0.18),(int) (_screenHeight*0.12));
		param_bBackToMenu.gravity = Gravity.RIGHT;
		param_bBackToMenu.setMargins(0, (int) (_screenHeight*0.2), (int) (_screenWidth*0.09), 0);
		ImageView _bBackToMenu = (ImageView) findViewById(R.id.ivBackToMenu);
		_bBackToMenu.setLayoutParams(param_bBackToMenu);
		_bBackToMenu.setOnClickListener(this);
		
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		//soundPlayer(R.raw.button_click);
		startActivity(new Intent("com.wsp.ciliwunggame.MENU"));
		AboutActivity.this.finish();
	}
	
	public void soundPlayer(int resid) {
		MediaPlayer _mySound = MediaPlayer.create(this, resid);
		try {
			_mySound.prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		_mySound.start();
	}

}
