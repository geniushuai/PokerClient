/*
 * @ Project: myApp
 * @ File Name: DataBaseSQLActivity.java
 * @ Date: 2012-7-6
 * @ Author: Song,Jie
 * Copyright 2012 NHNST. All Rights Reserved.
 */
package com.game.pokerclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

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
public class PlayerActivity extends BaseActivity implements OnClickListener{
	private PlayerDataSource datasource;
	
	private Button btnAddUser;
	private Button btnLogin;
	
	private final static String PREFS_NAME = "com.game.pokerclient";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.user_main);
		setContext(this);
		datasource = new PlayerDataSource(this);
		datasource.open();
		
		btnAddUser = (Button) findViewById(R.id.btnAddUser);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnAddUser.setOnClickListener(this);
		btnLogin.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btnAddUser:
			EditText txtUserName = (EditText)findViewById(R.id.txtUserName);
			EditText txtPwd = (EditText)findViewById(R.id.txtPwd);
			EditText txtRePwd = (EditText)findViewById(R.id.txtRePwd);
			EditText txtName = (EditText)findViewById(R.id.txtName);
			if (txtUserName.getText().toString().length() == 0) {
				showToast("用户名不能为空！");
				break;
			} else if (txtPwd.getText().toString().length() == 0) {
				showToast("密码不能为空！");
				break;
			} else if (txtName.getText().toString().length() == 0) {
				showToast("昵称不能为空！");
				break;
			}
			if (txtPwd.getText().toString().equals(txtRePwd.getText().toString())) {
				Player player = datasource.createPlayer(txtUserName.getText().toString(), txtPwd.getText().toString(), txtName.getText().toString());
				showToast("注册成功！");
				startGame(player);
			} else {
				showToast("密码不一致！");
			}
			break;
		case R.id.btnLogin:
			EditText txtLoginUser = (EditText)findViewById(R.id.txtLoginUser);
			EditText txtLoginPwd = (EditText)findViewById(R.id.txtLoginPwd);
			if (txtLoginUser.getText().toString().length() == 0) {
				showToast("用户名不能为空！");
				break;
			} else if (txtLoginPwd.getText().toString().length() == 0) {
				showToast("密码不能为空！");
				break;
			}
			Player player = datasource.getPlayer(txtLoginUser.getText().toString(), txtLoginPwd.getText().toString());
			if (player != null) {
				startGame(player);
			} else {
				showToast("用户名或密码错误！");
			}
			break;
			
		default:
			break;
		}
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
	
	private void startGame(Player player) {
		final SharedPreferences prefs = getContext().getSharedPreferences(PREFS_NAME, 0);
		Editor editor = prefs.edit();
		editor.putString("userName", player.getName());
		editor.commit();
		Intent intent = new Intent(this, FightLandLordActivity.class);
		startActivity(intent);
	}
	
}
