/*
 * @ Project: myApp
 * @ File Name: CommentsDataSource.java
 * @ Date: 2012-7-6
 * @ Author: Song,Jie
 * Copyright 2012 NHNST. All Rights Reserved.
 */
package com.game.model.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.game.pokerclient.model.Player;

/**
 * Class Description
 * <pre>
 * Filename        : CommentsDataSource.java , 2012-7-6
 * Creator         : SongJie
 * Revised Description
 * ----------------------------------------------
 * revised date    reviser   revised contents
 * ----------------------------------------------
 * 
 *</pre>
 */
public class PlayerDataSource {
	private static final String COLUMN_PROFILE_ID = "PROFILE_ID";
	private static final String COLUMN_NUMBER = "NUMBER";
	private static final String COLUMN_NAME = "NAME";
	private static final String COLUMN_USER_ID = "USER_ID";
	private static final String COLUMN_PASSWORD = "PASSWORD";
	private static final String COLUMN_CURRENT_SCORE = "CURRENT_SCORE";
	private static final String COLUMN_INIT_LIMIT = "INIT_LIMIT";
	private static final String COLUMN_LEVEL = "LEVEL";
	private static final String COLUMN_RLS_PATH = "RLS_PATH";
	private static final String COLUMN_ROLE = "ROLE";
	private static final String COLUMN_STATUS = "STATUS";
	private static final String COLUMN_CREATE_TIME = "CREATE_TIME";
	private static final String COLUMN_CREATE_BY = "CREATE_BY";
	private static final String COLUMN_UPDATE_TIME = "UPDATE_TIME";
	private static final String COLUMN_UPDATE_BY = "UPDATE_BY";
	
	// Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
	private String[] allColumns = { COLUMN_PROFILE_ID, COLUMN_NUMBER,
			COLUMN_NAME, COLUMN_USER_ID, COLUMN_CURRENT_SCORE,COLUMN_INIT_LIMIT,COLUMN_LEVEL,COLUMN_RLS_PATH,COLUMN_ROLE,COLUMN_STATUS  };
    
    public PlayerDataSource(Context context) {
    	dbHelper = new MySQLiteHelper(context, MySQLiteHelper.DATABASE_NAME, null, MySQLiteHelper.DATABASE_VERSION);
    }
    
    public void open() {
    	database = dbHelper.getWritableDatabase();
    }
    
    public void close() {
    	dbHelper.close();
    }
    
    public Player createPlayer(String userName, String password, String name) {
    	ContentValues values = new ContentValues();
    	values.put(COLUMN_NUMBER, "");
    	values.put(COLUMN_NAME, name);
    	values.put(COLUMN_USER_ID, userName);
    	values.put(COLUMN_PASSWORD, password);
    	values.put(COLUMN_CURRENT_SCORE, 0);
    	values.put(COLUMN_INIT_LIMIT, "");
    	values.put(COLUMN_LEVEL, "");
    	values.put(COLUMN_RLS_PATH, "a");
    	values.put(COLUMN_ROLE, "r");
    	values.put(COLUMN_STATUS, "");
    	values.put(COLUMN_CREATE_TIME, "");
    	values.put(COLUMN_CREATE_BY, "");
    	values.put(COLUMN_UPDATE_TIME, "");
    	values.put(COLUMN_UPDATE_BY, "");
    	
    	long insertId = database.insert(MySQLiteHelper.TABLE_PLAYERS, null, values);
    	
    	Cursor cursor = database.query(MySQLiteHelper.TABLE_PLAYERS, allColumns, COLUMN_PROFILE_ID + " = " + insertId, null, null, null, null);
    	
    	cursor.moveToFirst();
    	Player newComment = cursorToPlayer(cursor);
    	cursor.close();
    	return newComment;
    }
    
    public Player cursorToPlayer(Cursor cursor) {
    	Player player = new Player();
    	player.setId(cursor.getString(0));
    	player.setName(cursor.getString(2));
    	return player;
    }
    
    public void deletePlayer(Player player) {
    	String id = player.getId();
    	System.out.println("Player deleted with id: " + id);
    	database.delete(MySQLiteHelper.TABLE_PLAYERS, COLUMN_PROFILE_ID + " = " + id, null);
    }
    
    public List<Player> getAllPlayers() {
    	List<Player> players = new ArrayList<Player>();
    	
    	Cursor cursor = database.query(MySQLiteHelper.TABLE_PLAYERS, allColumns, null, null, null, null, null);
    	
    	cursor.moveToFirst();
    	
    	while (!cursor.isAfterLast()) {
    		Player player = cursorToPlayer(cursor);
    		players.add(player);
    		cursor.moveToNext();
    	}
    	cursor.close();
    	return players;
    }
    
    public Player getPlayer(String userName, String password) {
    	Player player = null;
    	String selection = COLUMN_USER_ID + "=? and " + COLUMN_PASSWORD + "=?" ;  
    	String[] selectionArgs = new  String[]{ userName, password };  
    	Cursor cursor = database.query(MySQLiteHelper.TABLE_PLAYERS, allColumns, selection, selectionArgs, null, null, null);
    	
    	if (cursor.moveToFirst()) {
    		player = cursorToPlayer(cursor);
    	}
    	cursor.close();
    	return player;
    }
}
