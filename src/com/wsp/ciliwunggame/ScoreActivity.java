package com.wsp.ciliwunggame;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

public class ScoreActivity extends Activity implements OnClickListener {
	
	public int _screenWidth;
	public int _screenHeight;
	
	TableLayout tableLayout;
	TableRow row;
	TextView firstCol;
	TextView secondCol;
	TextView thirdCol;
	TextView fourthCol;
	int bgColor;
	
	DatabaseHendlerScore db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		Display display = getWindowManager().getDefaultDisplay();
		_screenWidth = display.getWidth();
		_screenHeight = display.getHeight();

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.score);
		
		LinearLayout.LayoutParams param_bBackToMenu = new LinearLayout.LayoutParams((int) (_screenWidth*0.18),(int) (_screenHeight*0.12));
		param_bBackToMenu.gravity = Gravity.RIGHT;
		param_bBackToMenu.setMargins(0, (int) (_screenHeight*0.2), (int) (_screenWidth*0.09), 0);
		ImageView _bBackToMenu = (ImageView) findViewById(R.id.ivBackToMenu);
		_bBackToMenu.setLayoutParams(param_bBackToMenu);
		_bBackToMenu.setOnClickListener(this);
		
		LinearLayout.LayoutParams param_LinearLayout1 = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
		param_LinearLayout1.gravity = Gravity.CENTER;
		param_LinearLayout1.setMargins(0, (int) (_screenHeight*0.06), 0, 0);
		LinearLayout _LinearLayout1 = (LinearLayout) findViewById(R.id.LinearLayout1);
		_LinearLayout1.setLayoutParams(param_LinearLayout1);
		_LinearLayout1.setPadding((int) (_screenWidth*0.095), 0,(int) (_screenWidth*0.095), 0);
		
		TableRow tableRow1 = (TableRow) findViewById(R.id.tableRow1);
		tableRow1.setPadding(0, (int)(_screenHeight*0.003), 0, (int)(_screenHeight*0.003));
		
		int pad = (int) (_screenWidth*0.005);
		

		tableLayout = (TableLayout) findViewById(R.id.tableLayout1);

		
		TextView textView1 =  (TextView)tableRow1.findViewById(R.id.textView1);
		TextView textView2 =  (TextView)tableRow1.findViewById(R.id.textView2);
		TextView textView3 =  (TextView)tableRow1.findViewById(R.id.textView3);
		TextView textView4 =  (TextView)tableRow1.findViewById(R.id.textView4);
		
		textView1.setTextSize((float) (_screenWidth*0.03));
		textView1.setPadding(pad, pad, pad, pad);
		textView1.setWidth((int)(_screenWidth*0.13));

		textView2.setTextSize((float) (_screenWidth*0.03));
		textView2.setPadding(pad, pad, pad, pad);
		textView2.setWidth((int)(_screenWidth*0.43));
		
		textView3.setTextSize((float) (_screenWidth*0.03));
		textView3.setPadding(pad, pad, pad, pad);
		textView3.setWidth((int)(_screenWidth*0.13));
		
		textView4.setTextSize((float) (_screenWidth*0.03));
		textView4.setPadding(pad, pad, pad, pad);
		textView4.setWidth((int)(_screenWidth*0.13));
		
		
		db = new DatabaseHendlerScore(this);
		
		List<Score> score = db.getAllScore();
		int rangking = 1;

		for (Score cn : score) {
			// String log = " ID : " + cn.getId() + ", NAME : " + cn.getName()
			// + ", PHONE : " + cn.getScore();
			// Log.d("PB->", log);

			if (rangking % 2 == 0) {
				bgColor = Color.parseColor("#ffffff");
			} else {
				bgColor = Color.parseColor("#e6eed5");
			}
			addRows(String.valueOf(rangking), String.valueOf(cn.getName()),
					cekLevel(cn.getScore()), String.valueOf(cn.getScore()));
			rangking++;
		}

		if (db.getScoreCount() < 12) {
			for (int i = 0; i < 12 - db.getScoreCount(); i++) {
				if (rangking % 2 == 0) {
					bgColor = Color.parseColor("#ffffff");
				} else {
					bgColor = Color.parseColor("#e6eed5");
				}
				addRows(String.valueOf(rangking), "---", "-", "---");
				rangking++;
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		//soundPlayer(R.raw.button_click);
		startActivity(new Intent("com.wsp.ciliwunggame.MENU"));
		db.close();
		ScoreActivity.this.finish();
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

	public void addRows(String valCol1, String valCol2, String valCol3,
			String valCol4) {
		
		int pad = (int) (_screenWidth*0.005);
		row = new TableRow(this);
		row.setId(100);
		row.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));

		/* Setting up the first coloumn parameters */
		firstCol = new TextView(this);
		firstCol.setText(valCol1 + ".");
		firstCol.setTextSize((float) (_screenWidth*0.03));
		firstCol.setTextColor(Color.BLACK);
		firstCol.setGravity(Gravity.CENTER);
		firstCol.setBackgroundColor(bgColor);
		firstCol.setPadding(pad, pad, pad, pad);
		firstCol.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		row.addView(firstCol); // adding coloumn to row

		/* Setting up the second coloumn parameters */
		secondCol = new TextView(this);
		secondCol.setText(valCol2);
		secondCol.setTextColor(Color.BLACK);
		secondCol.setTextSize((float) (_screenWidth*0.03));
		secondCol.setBackgroundColor(bgColor);
		secondCol.setPadding(pad, pad, pad, pad);
		secondCol.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		row.addView(secondCol); // adding coloumn to row

		/* Setting up the third coloumn parameters */
		thirdCol = new TextView(this);
		thirdCol.setText(valCol3);
		thirdCol.setTextColor(Color.BLACK);
		thirdCol.setTextSize((float) (_screenWidth*0.03));
		thirdCol.setGravity(Gravity.CENTER);
		thirdCol.setBackgroundColor(bgColor);
		thirdCol.setPadding(pad, pad, pad, pad);
		thirdCol.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		row.addView(thirdCol); // adding coloumn to row

		/* Setting up the third coloumn parameters */
		fourthCol = new TextView(this);
		fourthCol.setText(valCol4);
		fourthCol.setTextColor(Color.BLACK);
		fourthCol.setGravity(Gravity.CENTER);
		fourthCol.setTextSize((float) (_screenWidth*0.03));
		fourthCol.setBackgroundColor(bgColor);
		fourthCol.setPadding(pad, pad, pad, pad);
		fourthCol.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		row.addView(fourthCol); // adding coloumn to row

		/* Adding the row to the tablelayout */
		tableLayout.addView(row, new TableLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
	}

	public String cekLevel(int _nilai) {
		String txt = "";
		if (_nilai >= 0 && _nilai < 100) {
			txt = "1";
		} else if (_nilai >= 100 && _nilai < 250) {
			txt = "2";
		} else if (_nilai >= 250 && _nilai < 400) {
			txt = "3";
		} else if (_nilai >= 400 && _nilai < 550) {
			txt = "4";
		} else if (_nilai >= 550 && _nilai < 600) {
			txt = "5";
		}

		return txt;
	}

}
