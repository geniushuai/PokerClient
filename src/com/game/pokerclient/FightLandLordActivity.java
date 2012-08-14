/*
 * @ Project: Pokerclient
 * @ File Name: FightLandLordActivity.java
 * @ Date: 2012-7-25
 * @ Author: Song,Jie
 * Copyright 2012 NHNST. All Rights Reserved.
 */
package com.game.pokerclient;

import java.util.List;
import java.util.Map;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.game.model.fightlandlord.FightLandlordPoker;
import com.game.pokerclient.component.PokerButton;
import com.game.pokerclient.constant.GameConstant;
import com.game.pokerclient.model.FightLandlordGameStatus;
import com.game.pokerclient.state.PokerService;
import com.game.pokerclient.thread.PlayerHandler;
import com.game.pokerclient.thread.PlayerTask;

/**
 * Class Description
 * <pre>
 * Filename        : FightLandLordActivity.java , 2012-7-25
 * Creator         : SongJie
 * Revised Description
 * ----------------------------------------------
 * revised date    reviser   revised contents
 * ----------------------------------------------
 * 
 *</pre>
 */
public class FightLandLordActivity extends BaseActivity implements OnClickListener {

	private FrameLayout framelayout;
	private Button btnRun;
	private PokerService pokerService;

	private ServiceConnection serviceConnection;
	
	private int settingTimes = 0;
	
	private String settingLabel;
	
	private int firstUserId;
	
	private int playerUserId;
	

	public static final int[] CONTROL_BARS = {R.id.controlBarLeft, R.id.controlBarButtom, R.id.controlBarRight};
	public static final int[] CANDIDATED_BOXS = {R.id.candidatedLeft, R.id.candidatedDown, R.id.candidatedRight};
	public static final int[] DEALED_BOXS = {R.id.dealedLeft, R.id.dealedBottom, R.id.dealedRight};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fightlandlord_main);
		setContext(this);
		initServiceConnection();
		setupViews();
	}
	
	/**
	 * Method Description;
	 *
	 */
	private void initServiceConnection() {
		serviceConnection = new ServiceConnection() {
			public void onServiceConnected(ComponentName name, IBinder service) {
				pokerService = ((PokerService.PokerBinder) service).getService();
				Map<Integer, String> playerPokers = pokerService.getFightLandlordPoker();
				initPoker(playerPokers);
			}

			public void onServiceDisconnected(ComponentName name) {
				pokerService = null;
				
			}
		};
		
	}

	public void setupViews(){  
		final SharedPreferences prefs = this.getSharedPreferences(GameConstant.PREFS_NAME, 0);
		String userName = prefs.getString("userName", "");
		TextView textView = (TextView)findViewById(R.id.txtWelcome);
		textView.setText("Welcome," + userName);
		btnRun = (Button) findViewById(R.id.btnRun);
		btnRun.setOnClickListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View view) {
		if (view == btnRun) { 
			if (btnRun.getText().equals(getText(R.string.btn_start))) {
				Intent intent = new Intent(this, PokerService.class);
				startService(intent);
				bindService(intent, serviceConnection, BIND_AUTO_CREATE);
				btnRun.setText(R.string.btn_restart);
			} else {
				reset();
				Intent intent = new Intent(this, PokerService.class);
				unbindService(serviceConnection);
				bindService(intent, serviceConnection, BIND_AUTO_CREATE);
			}
		}

	}
	
	private void reset() {
		LinearLayout linearLayout = (LinearLayout)findViewById(R.id.candidatedTop);
		linearLayout.removeAllViews();
		
		TextView tvConsole = (TextView) findViewById(R.id.tvConsole);
		tvConsole.setText("");

		TextView tvPoint = (TextView) findViewById(R.id.tvPoint);
		tvPoint.setText("");
		
		for (int id : CONTROL_BARS) {
			ViewGroup view = (ViewGroup) findViewById(id);
			view.removeAllViews();
		}

		for (int id : DEALED_BOXS) {
			ViewGroup view = (ViewGroup) findViewById(id);
			view.removeAllViews();
		}

		for (int id : CANDIDATED_BOXS) {
			ViewGroup view = (ViewGroup) findViewById(id);
			view.removeAllViews();
		}
		
		setSettingTimes(0);
	}
	
	

	private void initPoker(Map<Integer, String> playerPokers) {
		
		int x = 0;
		for (int id : CANDIDATED_BOXS) {
			framelayout = (FrameLayout) findViewById(id);
			List<String> pks = FightLandlordPoker.sortPokers(playerPokers.get(x).replace(",first", ""));
			if (playerPokers.get(x).indexOf(",first") > 0) {
				Handler playerHandler = new PlayerHandler(getContext());
				PlayerTask playerTask = new PlayerTask(playerHandler);
				playerTask.execute(FightLandlordGameStatus.GAME_SETTING_UPDATE.name(), String.valueOf(x));
			}
			int y = 0;
			for (String pk : pks) {
				PokerButton pokerButton = new PokerButton(this);
				FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) pokerButton.getLayoutParams();
				switch (id) {
				case R.id.candidatedLeft:
					params.gravity = Gravity.TOP;
					params.topMargin = y+=GameConstant.OFFSET_SIZE;
					pokerButton.setUserId(0);
					break;
				case R.id.candidatedDown:
					params.leftMargin = y+=GameConstant.OFFSET_SIZE;
					pokerButton.setUserId(1);
					break;
				case R.id.candidatedRight:
					params.gravity = Gravity.BOTTOM;
					params.bottomMargin = y+=GameConstant.OFFSET_SIZE;
					pokerButton.setUserId(2);
					break;

				default:
					break;
				}
				pokerButton.setLayoutParams(params);
				pokerButton.setValue(pk);
				pokerButton.setImage(pk);
				framelayout.addView(pokerButton);
			}
			x++;
		}
		// 3张底牌
		LinearLayout linearLayout = (LinearLayout)findViewById(R.id.candidatedTop);
		String pks[] = {"back", "back", "back"};
		for (String pk : pks) {
			PokerButton pokerButton = new PokerButton(this);
			pokerButton.setUserId(-1);
			ViewGroup.LayoutParams params = pokerButton.getLayoutParams();
			params.width = 70;
			params.height = 100;
			pokerButton.setLayoutParams(params);
			pokerButton.setAllowSelect(false);
			pokerButton.setValue(pk);
			pokerButton.setImage(pk);
			linearLayout.addView(pokerButton);
		}
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		// unbind the service and null it out.
		if (serviceConnection != null) {
			if (btnRun.getText().equals(getText(R.string.btn_restart))) {
				unbindService(serviceConnection);
			}
			serviceConnection = null;
		}
	}

	/**
	 * @return the settingTimes
	 */
	public int getSettingTimes() {
		return settingTimes;
	}

	/**
	 * @param settingTimes the settingTimes to set
	 */
	public void setSettingTimes(int settingTimes) {
		this.settingTimes = settingTimes;
	}

	/**
	 * @return the settingLabel
	 */
	public String getSettingLabel() {
		return settingLabel;
	}

	/**
	 * @param settingLabel the settingLabel to set
	 */
	public void setSettingLabel(String settingLabel) {
		this.settingLabel = settingLabel;
	}

	/**
	 * @return the firstUserId
	 */
	public int getFirstUserId() {
		return firstUserId;
	}

	/**
	 * @param firstUserId the firstUserId to set
	 */
	public void setFirstUserId(int firstUserId) {
		this.firstUserId = firstUserId;
	}

	/**
	 * @return the pokerService
	 */
	public PokerService getPokerService() {
		return pokerService;
	}

	/**
	 * @return the playerUserId
	 */
	public int getPlayerUserId() {
		return playerUserId;
	}

	/**
	 * @param playerUserId the playerUserId to set
	 */
	public void setPlayerUserId(int playerUserId) {
		this.playerUserId = playerUserId;
	}
	
	

}


