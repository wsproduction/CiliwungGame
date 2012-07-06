package com.wsp.ciliwunggame;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class ArenaActivity extends Activity implements OnClickListener {

	public Canvas _canvas;
	public boolean _ketLooping;

	public int _x, _y;
	public ImageView[] _ivSampah = new ImageView[5];
	private LinearLayout.LayoutParams[] p = new LinearLayout.LayoutParams[5];
	private int[] _top = new int[5];
	private int _time;

	Handler handlerMovingSampah = new Handler() {
		public void handleMessage(Message msg) {
			if (_top[0] == 10) {
				_top[0] = 500;
				// _time -= 10;
			} else {
				_top[0] -= 2;
			}
			// String perc = String.valueOf(_top).toString();
			p[0].setMargins(0, _top[0], 10, 10);
			_ivSampah[0].setLayoutParams(p[0]);

			if (_top[0] < 200) {
				if (_top[1] == 10) {
					_top[1] = 500;
					// _time -= 10;
				} else {
					_top[1] -= 2;
				}
				// String perc = String.valueOf(_top).toString();
				p[1].setMargins(0, _top[1], 10, 10);
				_ivSampah[1].setLayoutParams(p[1]);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.arena);

		_top[0] = 500;
		_top[1] = 510;

		_time = 50;

		_ivSampah[0] = new ImageView(this);
		_ivSampah[0].setImageResource(R.drawable.gs_bunga);

		_ivSampah[1] = new ImageView(this);
		_ivSampah[1].setImageResource(R.drawable.gs_kalengminuman);

		p[0] = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 60);
		p[0].setMargins(0, _top[0], 10, 10);
		_ivSampah[0].setLayoutParams(p[0]);

		p[1] = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 60);
		p[1].setMargins(0, _top[1], 10, 10);
		_ivSampah[1].setLayoutParams(p[1]);

		LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
		mainLayout.addView(_ivSampah[0]);
		mainLayout.addView(_ivSampah[1]);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

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
					while (true) {
						Thread.sleep(_time);
						handlerMovingSampah.sendMessage(handlerMovingSampah
								.obtainMessage());
					}
				} catch (Throwable t) {
					t.printStackTrace();
				} finally {
					// finish();
				}
			}
		});

		bg.start();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

}
