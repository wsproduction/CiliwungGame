package com.wsp.ciliwunggame;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHendlerScore extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 2;
	private static final String DATABASE_NAME = "HighScore";
	private static final String TABLE_SCORE = "score";

	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_HS = "high_score";

	public DatabaseHendlerScore(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_SCORE + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
				+ KEY_HS + " INTEGER)";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORE);
		onCreate(db);
	}

	public void addScore(Score score) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues val = new ContentValues();
		val.put(KEY_NAME, score.getName());
		val.put(KEY_HS, score.getScore());
		db.insert(TABLE_SCORE, null, val);
		db.close();
	}

	public List<Score> getAllScore() {
		List<Score> scoreList = new ArrayList<Score>();
		String selectQuery = "SELECT * FROM " + TABLE_SCORE + " ORDER BY "
				+ KEY_HS + " DESC LIMIT 12";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Score score = new Score();
				score.setId(Integer.parseInt(cursor.getString(0)));
				score.setName(cursor.getString(1));
				score.setScore(Integer.parseInt(cursor.getString(2)));
				scoreList.add(score);
			} while (cursor.moveToNext());
		}

		return scoreList;
	}
	
	public Boolean cekScore(int newScore) {
		String countQuery = "SELECT * FROM " + TABLE_SCORE
				+ " ORDER BY " + KEY_HS + " DESC LIMIT 1";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);

		boolean ket = true;
		if (cursor.getCount() > 0) {
			if (cursor.moveToFirst()) {
				if (newScore > Integer.parseInt(cursor.getString(2))) {
					ket = true;
				} else {
					ket = false;
				}
			}
		}

		return ket;
	}

	public int getScoreCount() {
		String countQuery = "SELECT * FROM " + TABLE_SCORE;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int count = cursor.getCount();
		cursor.close();
		return count;
	}

}
