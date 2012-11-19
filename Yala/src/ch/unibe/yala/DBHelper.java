package ch.unibe.yala;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	public DBHelper(Context context) {
		super(context, "yala.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table yala (date String, point String, time String, alti String, pauseTime String, pausePoint String); ";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS yala");
		onCreate(db);
	}

	public void reset(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS yala");
		onCreate(db);
	}
}
