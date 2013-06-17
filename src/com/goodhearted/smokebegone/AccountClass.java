package com.goodhearted.smokebegone;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class AccountClass extends SQLiteOpenHelper{

	public static final String TABLE_SMOKES = "smokes";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_DATE = "dat";
	
	public static final String DATABASE_NAME = "smokebegone.db";
	public static final int DATABASE_VERSION = 1;
	
	private static final String SMOKES_CREATE = "create table "
		      + TABLE_SMOKES + "(" + COLUMN_ID
		      + " integer primary key autoincrement, " + COLUMN_DATE
		      + " DATETIME not null);";
	
	SharedPreferences settings;
	
	public AccountClass(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(SMOKES_CREATE);
	}

	@Override
	  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_SMOKES);
	    onCreate(db);
	  }

}

