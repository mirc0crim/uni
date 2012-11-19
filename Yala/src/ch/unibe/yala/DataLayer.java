package ch.unibe.yala;

import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DataLayer {
	private final DBHelper dbHelper;

	public DataLayer(Context c) {
		dbHelper = new DBHelper(c);
	}

	public void addRun(String d, String p, String t, String a, String pt, String pp) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		try {
			ContentValues values = new ContentValues();
			values.put("date", d);
			values.put("point", p);
			values.put("time", t);
			values.put("alti", a);
			values.put("pauseTime", pt);
			values.put("pausePoint", pp);

			db.insert("yala", "", values);
		} finally {
			if (db != null)
				db.close();
		}
	}

	public void resetDB() {
		dbHelper.reset(dbHelper.getWritableDatabase());
	}

	public String getStats(String s, int d) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		try {
			String results = "1";
			Cursor c = db.rawQuery("select * from yala", null);
			if (c.getCount() > 0) {
				c.moveToFirst();
				for (int i = 0; i < d; i++)
					c.moveToNext();
				results = c.getString(c.getColumnIndex(s));
			}
			return results;
		} finally {
			if (db != null)
				db.close();
		}
	}

	public void deleteRun(int d) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		try {
			String s = "";
			Cursor c = db.rawQuery("select * from yala", null);
			if (c.getCount() > 0) {
				c.moveToFirst();
				for (int i = 0; i < d; i++)
					c.moveToNext();
				s = c.getString(c.getColumnIndex("date"));
				Date fullDate = new Date();
				fullDate.setTime(Long.parseLong(s));
				db.delete("yala", "date" + " = " + s, null);
			}

		} finally {
			if (db != null)
				db.close();
		}
	}

	public String getAllDates() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		try {
			String results = "1";
			Cursor c = db.rawQuery("select * from yala", null);
			if (c.getCount() > 0) {
				c.moveToFirst();
				results = c.getString(c.getColumnIndex("date"));
				while (c.moveToNext())
					results += "," + c.getString(c.getColumnIndex("date"));
			}
			return results;
		} finally {
			if (db != null)
				db.close();
		}
	}
}
