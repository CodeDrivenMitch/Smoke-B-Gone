package com.goodhearted.smokebegone;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SmokeDataSource {

	private SQLiteDatabase database;
	private AccountClass dbHelper;
	private String[] allColumns = { AccountClass.COLUMN_ID,
			AccountClass.COLUMN_DATE };

	public SmokeDataSource(Context context) {
		dbHelper = new AccountClass(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public void createSmoke() {
		Date d = new Date();
		long d_s = d.getTime();
		database.rawQuery("INSERT INTO " + AccountClass.TABLE_SMOKES + " (" + AccountClass.COLUMN_DATE + ") VALUES (" + d_s + ")", null);
	}
	
	public Smoke getLastSmoke() {
		Cursor c = database.rawQuery("SELECT * FROM " + AccountClass.TABLE_SMOKES, null);
		c.moveToLast();
		Smoke smoke = cursorToSmoke(c);
		c.close();
		return smoke;
	}
	
	public void removeLastSmoke(Smoke smoke) {
		long id = smoke.getId();
	    database.delete(AccountClass.TABLE_SMOKES, AccountClass.COLUMN_ID
	        + " = " + id, null);
	}
	
	public List<Smoke> getAllSmokes() {
	    List<Smoke> smokes = new ArrayList<Smoke>();

	    Cursor cursor = database.query(AccountClass.TABLE_SMOKES,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      Smoke smoke = cursorToSmoke(cursor);
	      smokes.add(smoke);
	      cursor.moveToNext();
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    return smokes;
	  }
	
	public int getTotalSmokes() {
		List<Smoke> s = getAllSmokes();
		return s.size();
	}
	private Smoke cursorToSmoke(Cursor c) {
		Smoke x = new Smoke();
		x.setId(c.getLong(0));
		x.setDate(c.getLong(1));
		return x;
	}
}
