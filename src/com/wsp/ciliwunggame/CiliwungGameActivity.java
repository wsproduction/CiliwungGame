package com.wsp.ciliwunggame;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class CiliwungGameActivity extends Activity implements OnClickListener {
	// SUMBER ICON : http://www.clker.com/clipart-127493.html
	final Context context = this;
	
	public int _screenWidth;
	public int _screenHeight;
	

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

		ImageView _bLatihan = (ImageView) findViewById(R.id.ivLatihan);
		ImageView _bAbout = (ImageView) findViewById(R.id.ivAbout);
		ImageView _bKeluar = (ImageView) findViewById(R.id.ivKeluar);
		ImageView _bScore = (ImageView) findViewById(R.id.ivScore);
		
		LinearLayout.LayoutParams param_bLatihan = new LinearLayout.LayoutParams((int) (_screenWidth*0.7),(int) (_screenHeight*0.12));
		param_bLatihan.gravity = Gravity.CENTER;
		param_bLatihan.setMargins(0, (int) (_screenHeight*0.289), 0, 0);
		_bLatihan.setLayoutParams(param_bLatihan);
		
		LinearLayout.LayoutParams param_bScore = new LinearLayout.LayoutParams((int) (_screenWidth*0.7),(int) (_screenHeight*0.12));
		param_bScore.gravity = Gravity.CENTER;
		param_bScore.setMargins(0, (int) (_screenHeight*0.038), 0, 0);
		_bScore.setLayoutParams(param_bScore);
		
		LinearLayout.LayoutParams param_bAbout = new LinearLayout.LayoutParams((int) (_screenWidth*0.7),(int) (_screenHeight*0.12));
		param_bAbout.gravity = Gravity.CENTER;
		param_bAbout.setMargins(0, (int) (_screenHeight*0.028), 0, 0);
		_bAbout.setLayoutParams(param_bAbout);
		
		LinearLayout.LayoutParams param_bKeluar = new LinearLayout.LayoutParams((int) (_screenWidth*0.7),(int) (_screenHeight*0.12));
		param_bKeluar.gravity = Gravity.CENTER;
		param_bKeluar.setMargins(0, (int) (_screenHeight*0.026), 0, 0);
		_bKeluar.setLayoutParams(param_bKeluar);

		_bLatihan.setOnClickListener(this);
		_bAbout.setOnClickListener(this);
		_bKeluar.setOnClickListener(this);
		_bScore.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivLatihan:
			soundPlayer(R.raw.button_click);
			startActivity(new Intent("com.wsp.ciliwunggame.LATIHAN"));
			CiliwungGameActivity.this.finish();
			break;
		case R.id.ivAbout:
			soundPlayer(R.raw.button_click);
			startActivity(new Intent("com.wsp.ciliwunggame.ABOUT"));
			CiliwungGameActivity.this.finish();
			break;
		case R.id.ivScore:
			soundPlayer(R.raw.button_click);
			startActivity(new Intent("com.wsp.ciliwunggame.SCORE"));
			CiliwungGameActivity.this.finish();
			break;
		case R.id.ivKeluar:
			soundPlayer(R.raw.button_click);
			AlertDialog.Builder alt = new AlertDialog.Builder(this);
			alt.setMessage("Do you want to exit?").setTitle("Warning");
			alt.setIcon(R.drawable.ic_warning);
			alt.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					soundPlayer(R.raw.button_click);
					CiliwungGameActivity.this.finish();
				}
			});
			alt.setNegativeButton("No", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					soundPlayer(R.raw.button_click);
					dialog.cancel();
				}
			});
			alt.show();
		}
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