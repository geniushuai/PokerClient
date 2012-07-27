/*
 * @ Project: myApp
 * @ File Name: MySQLiteHelper.java
 * @ Date: 2012-7-6
 * @ Author: Song,Jie
 * Copyright 2012 NHNST. All Rights Reserved.
 */
package com.game.model.database;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Class Description
 * 
 * <pre>
 * Filename        : MySQLiteHelper.java , 2012-7-6
 * Creator         : SongJie
 * Revised Description
 * ----------------------------------------------
 * revised date    reviser   revised contents
 * ----------------------------------------------
 * 
 * </pre>
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

	public static final String TABLE_PLAYERS = "PLAYER_PROFILE";

	public static final String DATABASE_NAME = "games.db";
	
	public static final int DATABASE_VERSION = 1;
	// Database creation sql statement
	private static final String DATABASE_CREATE = "db/game.sql";
	
	private Context context;
	
	/**
	 * @return the context
	 */
	public Context getContext() {
		return context;
	}

	/**
	 * @param context the context to set
	 */
	public void setContext(Context context) {
		this.context = context;
	}

	/**
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 */
	public MySQLiteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite
	 * .SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		executeSQLScript(db, DATABASE_CREATE);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite
	 * .SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySQLiteHelper.class.getName(),
				"Upgrading database form version " + oldVersion + " to "
						+ newVersion + ", which will destory all old data");

		db.execSQL("DROP TABLE IF EXITS " + TABLE_PLAYERS);
		onCreate(db);

	}

	private void executeSQLScript(SQLiteDatabase database, String dbname) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte buf[] = new byte[1024];
		int len;
		AssetManager assetManager = context.getAssets();
		InputStream inputStream = null;

		try {
			inputStream = assetManager.open(dbname);
			while ((len = inputStream.read(buf)) != -1) {
				outputStream.write(buf, 0, len);
			}
			outputStream.close();
			inputStream.close();

			String[] createScript = outputStream.toString().split(";");
			for (int i = 0; i < createScript.length; i++) {
				String sqlStatement = createScript[i].trim();
				// TODO You may want to parse out comments here
				if (sqlStatement.length() > 0) {
					database.execSQL(sqlStatement + ";");
				}
			}
		} catch (IOException e) {
			// TODO Handle Script Failed to Load
		}
	}

}
