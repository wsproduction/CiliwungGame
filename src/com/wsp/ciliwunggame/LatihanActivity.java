package com.wsp.ciliwunggame;

import java.io.IOException;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class LatihanActivity extends Activity implements OnClickListener {

	public int _screenWidth;
	public int _screenHeight;

	private String[] _sampah;
	private String[] _jenisSampah;
	private TypedArray _gambarSampah;
	private TypedArray _gambarKeterangan;

	private static final Random _rGenerator = new Random();
	private TextView _tvSampah, _tvNilai, _tvWaktu, _tvNyawa;
	private String _ketJenisSampah, _waktuMenitString, _waktuDetikString;
	private ImageView _ivSampah, _ivKeterangan;
	private boolean _ketMulai, _ketPeriksaJawaban, _ketLoopingWaktu,
			_statusWaktu;

	public int _nilai, _waktuMenit, _waktuDetik, _nyawa;
	public Thread wkt;
	public static Typeface _tf;

	private Thread dly;

	private int _idxSebelumnya, _idxSekarang;

	private boolean _ketSetLevel;
	final Context context = this;

	// Handler
	Handler handlerLanjutkanPertanyaan = new Handler() {
		public void handleMessage(Message msg) {
			try {
				acakSampah();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	};

	Handler handlerWaktu = new Handler() {
		public void handleMessage(Message msg) {
			if (_statusWaktu) {
				if (!_ketLoopingWaktu && _waktuMenit == 0 && _waktuDetik == 0) {
					AlertDialog.Builder alt = new AlertDialog.Builder(
							LatihanActivity.this);
					alt.setMessage(
							"Your Score : " + _nilai + " ( " + cekLevel()
									+ " )")
							.setIcon(R.drawable.timeout)
							.setTitle("Time out")
							.setCancelable(false)
							.setPositiveButton("OK",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub

											// Cek Score Ke database
											cekScore(_nilai);
											kondisiAwal();
											dialog.cancel();
										}
									}).show();
				}

				if (_waktuMenit == 0 && _waktuDetik == 0) {
					_ketLoopingWaktu = false;
				} else {
					if (_waktuMenit != 0 && _waktuDetik == 0) {
						_waktuMenit -= 1;
						_waktuDetik = 60;
					} else {
						_waktuDetik -= 1;
					}
				}

				outWaktu();
			}
		}
	};

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.latihan);
		
		Display display = getWindowManager().getDefaultDisplay();
		_screenWidth = display.getWidth();
		_screenHeight = display.getHeight();
		
		LinearLayout.LayoutParams param_LinearLayout1 = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
		param_LinearLayout1.gravity = Gravity.CENTER;
		param_LinearLayout1.setMargins(0, (int) (_screenHeight*0.315), 0, 0);
		LinearLayout _LinearLayout1 = (LinearLayout) findViewById(R.id.LinearLayout1);
		_LinearLayout1.setLayoutParams(param_LinearLayout1);
		_LinearLayout1.setPadding((int) (_screenWidth*0.095), 0,(int) (_screenWidth*0.095), 0);
		
		LinearLayout.LayoutParams param_LinearLayout2 = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
		param_LinearLayout2.gravity = Gravity.CENTER;
		param_LinearLayout2.setMargins(0, (int) (_screenHeight*0.04), 0, 0);
		LinearLayout _LinearLayout2 = (LinearLayout) findViewById(R.id.LinearLayout2);
		_LinearLayout2.setLayoutParams(param_LinearLayout2);
		
		LinearLayout.LayoutParams param_LinearLayout3 = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
		param_LinearLayout3.gravity = Gravity.CENTER;
		param_LinearLayout3.setMargins(0, (int) (_screenHeight*0.205), 0, 0);
		LinearLayout _LinearLayout3 = (LinearLayout) findViewById(R.id.LinearLayout3);
		_LinearLayout3.setLayoutParams(param_LinearLayout3);
		_LinearLayout3.setPadding((int) (_screenWidth*0.062), 0,(int) (_screenWidth*0.062), 0);

		_tf = Typeface.createFromAsset(getAssets(),
				"data/fonts/dk_crayon_crumble.ttf");

		_sampah = getResources().getStringArray(R.array.sampah);
		_jenisSampah = getResources().getStringArray(R.array.jenis_sampah);
		_gambarSampah = getResources().obtainTypedArray(R.array.gambar_sampah);
		_gambarKeterangan = getResources().obtainTypedArray(
				R.array.gambar_keterangan);

		LinearLayout.LayoutParams param_ivSampah = new LinearLayout.LayoutParams((int) (_screenWidth*0.29),(int) (_screenHeight*0.18));
		param_ivSampah.gravity = Gravity.CENTER;
		_ivSampah = (ImageView) findViewById(R.id.ivSampah);
		_ivSampah.setLayoutParams(param_ivSampah);
		
		LinearLayout.LayoutParams param_ivKeterangan = new LinearLayout.LayoutParams((int) (_screenWidth*0.29),(int) (_screenHeight*0.18));
		param_ivKeterangan.gravity = Gravity.CENTER;
		param_ivKeterangan.setMargins(0, -(int) (_screenHeight*0.18), 0, 0);
		_ivKeterangan = (ImageView) findViewById(R.id.ivKeterangan);
		_ivKeterangan.setLayoutParams(param_ivKeterangan);

		_tvSampah = (TextView) findViewById(R.id.tvSampah);
		_tvNilai = (TextView) findViewById(R.id.tvNilai);
		_tvWaktu = (TextView) findViewById(R.id.tvWaktu);
		_tvNyawa = (TextView) findViewById(R.id.tvNyawa);
		
		_tvSampah.setTextSize((float) (_screenWidth*0.035));
		_tvNilai.setTextSize((float) (_screenWidth*0.035));
		_tvNyawa.setTextSize((float) (_screenWidth*0.035));
		_tvWaktu.setPadding((int) (_screenWidth*0.02), 0, 0, 0);
		_tvWaktu.setTextSize((float) (_screenWidth*0.035));

		_tvSampah.setTypeface(_tf);
		_tvNilai.setTypeface(_tf);
		_tvWaktu.setTypeface(_tf);
		_tvNyawa.setTypeface(_tf);

		kondisiAwal();

		ImageView _bOrgnaik = (ImageView) findViewById(R.id.ivOrganik);
		ImageView _bNonOrganik = (ImageView) findViewById(R.id.ivNonOrganik);
		ImageView _bBackToMenu = (ImageView) findViewById(R.id.ivBackToMenu);
		
		LinearLayout.LayoutParams param_bOrgnaik = new LinearLayout.LayoutParams((int) (_screenWidth*0.335),(int) (_screenHeight*0.135));
		param_bOrgnaik.gravity = Gravity.CENTER;
		_bOrgnaik.setLayoutParams(param_bOrgnaik);
		
		LinearLayout.LayoutParams param_bBackToMenu = new LinearLayout.LayoutParams((int) (_screenWidth*0.18),(int) (_screenHeight*0.12));
		param_bBackToMenu.gravity = Gravity.CENTER;
		param_bBackToMenu.setMargins((int) (_screenWidth*0.015), 0, (int) (_screenWidth*0.015), 0);
		_bBackToMenu.setLayoutParams(param_bBackToMenu);
		
		LinearLayout.LayoutParams param_bNonOrganik = new LinearLayout.LayoutParams((int) (_screenWidth*0.335),(int) (_screenHeight*0.135));
		param_bNonOrganik.gravity = Gravity.CENTER;
		_bNonOrganik.setLayoutParams(param_bNonOrganik);
		
		_ivKeterangan.setOnClickListener(this);
		_bOrgnaik.setOnClickListener(this);
		_bNonOrganik.setOnClickListener(this);
		_bBackToMenu.setOnClickListener(this);

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivKeterangan:
			soundPlayer(R.raw.button_click);
			if (!_ketMulai) {
				AlertDialog.Builder alt = new AlertDialog.Builder(this);
				alt.setMessage("Are you ready?")
						.setCancelable(false)
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										soundPlayer(R.raw.button_click);
										_ketMulai = true;
										acakSampah();
										jalankanWaktu();

										showToast(cekLevel(), 180,
												Toast.LENGTH_LONG);
									}
								})
						.setNegativeButton("No",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										soundPlayer(R.raw.button_click);
										dialog.cancel();
									}
								}).show();
			}
			break;
		case R.id.ivOrganik:
			cekJenisSampah("Organik");
			break;
		case R.id.ivNonOrganik:
			cekJenisSampah("NonOrganik");
			break;
		case R.id.ivBackToMenu:
			if (_ketMulai) {
				soundPlayer(R.raw.button_click);
				_statusWaktu = false;
				AlertDialog.Builder alt = new AlertDialog.Builder(this);
				alt.setMessage("Do you want back to menu?").setTitle("Warning");
				alt.setIcon(R.drawable.ic_warning)
						.setCancelable(false)
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										_ketMulai = false;
										soundPlayer(R.raw.button_click);
										startActivity(new Intent(
												"com.wsp.ciliwunggame.MENU"));
										LatihanActivity.this.finish();
									}
								})
						.setNegativeButton("No",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										soundPlayer(R.raw.button_click);
										_statusWaktu = true;
										dialog.cancel();
									}
								}).show();
			} else {
				soundPlayer(R.raw.button_click);
				startActivity(new Intent("com.wsp.ciliwunggame.MENU"));
				LatihanActivity.this.finish();
			}
			break;
		}
	}

	public String cekLevel() {
		String txtToast = "";
		if (_nilai >= 0 && _nilai < 100) {
			txtToast = "Level 1";
		} else if (_nilai >= 100 && _nilai < 250) {
			txtToast = "Level 2";
		} else if (_nilai >= 250 && _nilai < 400) {
			txtToast = "Level 3";
		} else if (_nilai >= 400 && _nilai < 550) {
			txtToast = "Level 4";
		} else if (_nilai >= 550 && _nilai < 600) {
			txtToast = "Level 5";
		}

		return txtToast;
	}

	public void setLevel(final int minute, final int second,
			final boolean ketSet) {
		AlertDialog.Builder alt = new AlertDialog.Builder(this);
		alt.setIcon(R.drawable.cup);
		alt.setTitle("Excellent");
		alt.setMessage("Are you ready to " + cekLevel()).setPositiveButton(
				"Yes", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						_waktuMenit = minute;
						_waktuDetik = second;
						_ketSetLevel = ketSet;
						_ketMulai = true;
						acakSampah();
						jalankanWaktu();

						dialog.cancel();
					}
				});
		alt.setNegativeButton("No", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				kondisiAwal();
				dialog.cancel();
			}
		});
		alt.show();
	}

	public void showToast(String txt, int position, int length) {
		Toast toast = Toast.makeText(this, txt, length);
		toast.setGravity(Gravity.NO_GRAVITY, 0, position);
		toast.show();
	}

	public void cekJenisSampah(String js) {
		if (_ketMulai) {
			if (_ketPeriksaJawaban) {
				// String txtToast = "";
				int _ketSound;
				_ketPeriksaJawaban = false; // Keterangan Memeriksa jawaban di
											// disable
				if (js == "Organik") { // Untuk Mengecek Jenis Sampah Organik
					if (_ketJenisSampah.equals("Organik")) {
						_ivKeterangan.setImageDrawable(_gambarKeterangan
								.getDrawable(1));
						_nilai += 5;
						// txtToast = "Correct.";

						_ketSound = R.raw.corect_sound;
					} else {
						_ivKeterangan.setImageDrawable(_gambarKeterangan
								.getDrawable(0));
						_nyawa--;
						// txtToast = "Wrong.";
						_ketSound = R.raw.fold_sound;
					}
				} else { // Untuk Mengecek Jenis Sampah Non Organik
					if (_ketJenisSampah.equals("NonOrganik")) {
						_ivKeterangan.setImageDrawable(_gambarKeterangan
								.getDrawable(1));
						_nilai += 5;
						// txtToast = "Correct.";
						_ketSound = R.raw.corect_sound;
					} else {
						_ivKeterangan.setImageDrawable(_gambarKeterangan
								.getDrawable(0));
						_nyawa--;
						// txtToast = "Wrong.";
						_ketSound = R.raw.fold_sound;
					}
				}

				// Toast toast = Toast
				// .makeText(this, txtToast, Toast.LENGTH_SHORT);
				// toast.setGravity(Gravity.NO_GRAVITY, 0, 180);
				// toast.show();

				_tvNilai.setText("Score : " + _nilai);
				_tvNyawa.setText("X " + _nyawa);
				if (_nyawa == 0) {
					_ketLoopingWaktu = false;
					gameOver();
				} else {
					if ((_nilai == 100 && _ketSetLevel)
							|| (_nilai == 250 && _ketSetLevel)
							|| (_nilai == 400 && _ketSetLevel)
							|| (_nilai == 550 && _ketSetLevel)) {
						_ketLoopingWaktu = false;
						setLevel(2, 30, false);
					} else {
						if (_nilai == 105 || _nilai == 250 || _nilai == 400
								|| _nilai == 550) {
							_ketSetLevel = true;
						}
						soundPlayer(_ketSound);
						lanjutkanPertanyaan();
					}
				}

			}
		} else {
			// _ivKeterangan.setImageDrawable(_gambarKeterangan.getDrawable(0));
		}
	}

	public void gameOver() {
		AlertDialog.Builder alt = new AlertDialog.Builder(LatihanActivity.this);
		alt.setMessage("Your Score : " + _nilai + " ( " + cekLevel() + " )")
				.setIcon(R.drawable.ic_warning).setTitle("Game Over")
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// Cek score ke database
						cekScore(_nilai);
						kondisiAwal();
						dialog.cancel();
					}
				}).show();
	}

	public void cekScore(int score) {
		DatabaseHendlerScore db = new DatabaseHendlerScore(this);
		final int s = score;
		if (db.cekScore(score)) {
			// Log.d("Status", "Lebih besar");
			LayoutInflater li = LayoutInflater.from(context);
			View promptsView = li.inflate(R.layout.prompts, null);
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
			alertDialog.setView(promptsView);

			final EditText userInput = (EditText) promptsView
					.findViewById(R.id.editTextDialogUserInput);
			alertDialog.setTitle("High Score").setIcon(R.drawable.award);
			alertDialog
					.setCancelable(false)
					.setPositiveButton("Save",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									try {
										DatabaseHendlerScore dbx = new DatabaseHendlerScore(
												LatihanActivity.this);
										dbx.addScore(new Score(String
												.valueOf(userInput.getText()),
												s));

									} catch (Exception e) {
										e.printStackTrace();
									}

								}
							})
					.setNegativeButton("No",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							});

			// create alert dialog
			AlertDialog ad = alertDialog.create();

			// show it
			ad.show();
		}
	}

	public void acakSampah() {
		while (rGenerator()) {
		}

		_ivSampah.setImageDrawable(_gambarSampah.getDrawable(_idxSekarang));
		_tvSampah.setText(_sampah[_idxSekarang]);
		_ketJenisSampah = _jenisSampah[_idxSekarang];
		_ivKeterangan.setImageDrawable(null);
		_ketPeriksaJawaban = true; // Keterangan Memeriksa jawaban di enable
	}

	public boolean rGenerator() {
		_idxSekarang = _rGenerator.nextInt(_sampah.length);
		boolean ket;
		if (_idxSebelumnya == _idxSekarang) {
			ket = true;
		} else {
			ket = false;
			_idxSebelumnya = _idxSekarang;
		}
		return ket;
	}

	public void lanjutkanPertanyaan() {
		dly = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(800);
					handlerLanjutkanPertanyaan
							.sendMessage(handlerLanjutkanPertanyaan
									.obtainMessage());
				} catch (Throwable t) {
					t.printStackTrace();
				}

			}
		});

		dly.start();
	}

	public void jalankanWaktu() {
		_statusWaktu = true;
		wkt = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					_ketLoopingWaktu = true;
					while (_ketLoopingWaktu) {
						Thread.sleep(800);
						handlerWaktu.sendMessage(handlerWaktu.obtainMessage());
					}
				} catch (Throwable t) {
					t.printStackTrace();
				}

			}
		});

		wkt.start();
	}

	public void outWaktu() {
		_waktuMenitString = String.valueOf(_waktuMenit).toString();
		_waktuDetikString = String.valueOf(_waktuDetik).toString();

		if (_waktuMenitString.length() == 1) {
			_waktuMenitString = "0" + _waktuMenitString;
		}

		if (_waktuDetikString.length() == 1) {
			_waktuDetikString = "0" + _waktuDetikString;
		}

		_tvWaktu.setText("Time : " + _waktuMenitString + "."
				+ _waktuDetikString);
	}

	public void kondisiAwal() {
		_ketMulai = false;
		_ketSetLevel = true;
		_nilai = 0;
		_waktuMenit = 3;
		_waktuDetik = 0;
		_nyawa = 5;

		_ivSampah.setImageDrawable(null);
		_ivKeterangan.setImageDrawable(_gambarKeterangan.getDrawable(2));

		_tvSampah.setText("Klick Button to Begin");
		_tvNilai.setText("Score : " + _nilai);
		_tvNyawa.setText("x " + _nyawa);

		outWaktu();
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

	public String indexNilai(int nilai) {
		String idx;
		if (nilai >= 85) {
			idx = "Excelent";
		} else if (nilai >= 76 || nilai < 85) {
			idx = "Standard";
		} else {
			idx = "Bad";
		}

		return idx;
	}
}
