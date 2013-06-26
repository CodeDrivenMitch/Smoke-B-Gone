package com.goodhearted.smokebegone;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SmokeDataSource {

	private SQLiteDatabase database;
	private AccountClass dbHelper;

	public SmokeDataSource(Context context) {
		dbHelper = new AccountClass(context);
		try {
			this.open();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	//Create a new smoke, uses has smoked
	public void createSmoke() {
		Date d = new Date();
		long d_s = d.getTime();
		ContentValues values = new ContentValues();
	    values.put(AccountClass.COLUMN_DATE, d_s);
	    database.insert(AccountClass.TABLE_SMOKES, null,
	        values);
		
	}
	
	//get the date of the last smoke
	public Smoke getLastSmoke() {
		if(getAllSmokes().size() == 0) return null;
		Cursor c = database.rawQuery("SELECT * FROM " + AccountClass.TABLE_SMOKES, null);
		c.moveToLast();
		Smoke smoke = cursorToSmoke(c);
		c.close();
		return smoke;
	}
	
	//Remove the last smoke from database
	public void removeLastSmoke(Smoke smoke) {
		long id = smoke.getId();
	    database.delete(AccountClass.TABLE_SMOKES, AccountClass.COLUMN_ID
	        + " = " + id, null);
	}
	
	//Get all smokes from database
	public List<Smoke> getAllSmokes() {
	    List<Smoke> smokes = new ArrayList<Smoke>();

	    Cursor cursor = database.rawQuery("SELECT * FROM " + AccountClass.TABLE_SMOKES, null);
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
	
	//Get the total number of smokes
	public int getTotalSmokes() {
		List<Smoke> s = getAllSmokes();
		return s.size();
	}
	//makes a smoke object from the database
	private Smoke cursorToSmoke(Cursor c) {
		Smoke x = new Smoke();
		x.setId(c.getLong(0));
		x.setDate(c.getLong(1));
		return x;
	}
}
