/*
 * @ Project: myApp
 * @ File Name: DataBaseSQLActivity.java
 * @ Date: 2012-7-6
 * @ Author: Song,Jie
 * Copyright 2012 NHNST. All Rights Reserved.
 */
package com.game.pokerclient;

import java.util.List;
import java.util.Random;

import org.w3c.dom.Comment;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.game.model.database.PlayerDataSource;
import com.game.pokerclient.model.Player;

/**
 * Class Description
 * <pre>
 * Filename        : DataBaseSQLActivity.java , 2012-7-6
 * Creator         : SongJie
 * Revised Description
 * ----------------------------------------------
 * revised date    reviser   revised contents
 * ----------------------------------------------
 * 
 *</pre>
 */
public class PlayerActivity extends ListActivity {
	private PlayerDataSource datasource;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.user_main);
		datasource = new PlayerDataSource(this);
		datasource.open();
		List<Player> values = datasource.getAllPlayers();
		ArrayAdapter<Player> adapter = new ArrayAdapter<Player>(this, android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
		
	}
	
	public void onClick(View view) {
		ArrayAdapter<Player> adapter = (ArrayAdapter<Player>)getListAdapter();
		Player player = null;
		
		switch (view.getId()) {
		case R.id.btnAddUser:
			String[] comments  = new String[] {"Coll", "very nice", "Hate it"};
			int nextInt = new Random().nextInt(3);
			
			player = datasource.createPlayer(comments[nextInt]);
			adapter.add(player);
			break;
		case R.id.btnDeleteUser:
			if (getListAdapter().getCount() > 0) {
				player = (Player)getListAdapter().getItem(0);
				datasource.deletePlayer(player);
				adapter.remove(player);
				
			}
		default:
			break;
		}
		adapter.notifyDataSetChanged();
	}
	
	@Override
	protected void onResume() {
		datasource.open();
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		datasource.close();
		super.onPause();
	}
}
