package com.game.pokerclient;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.game.pokerclient.constant.GameConstant;

public class MainActivity extends BaseActivity implements OnClickListener {

	private Button btnGameStart;
	private Button btnGameExit;
	private Button btnGameOption;
	private Button btnGameHelp;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setContext(this);
		setupViews();
	}
	
	public void setupViews(){  
		btnGameStart = (Button)findViewById(R.id.btnGameStart);
		btnGameStart.setOnClickListener(this);
		
		btnGameExit = (Button)findViewById(R.id.btnGameExit);
		btnGameExit.setOnClickListener(this);
		
		btnGameOption = (Button)findViewById(R.id.btnGameOption);
		btnGameOption.setOnClickListener(this);
		
		btnGameHelp = (Button)findViewById(R.id.btnGameHelp);
		btnGameHelp.setOnClickListener(this);
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btnGameStart:
			final SharedPreferences prefs = getContext().getSharedPreferences(GameConstant.PREFS_NAME, 0);
			if (prefs.getString("userName", "").length() > 0) {
				Intent intent = new Intent(this, FightLandLordActivity.class);
				startActivity(intent);
			} else {
				Intent intent = new Intent(this, PlayerActivity.class);
				startActivity(intent);
			}
			break;
		case R.id.btnGameExit:
			dialog("Exit Game", "是否退出？", new DialogInterface.OnClickListener()
			{
				@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();    
					}
			});
			break;
		case R.id.btnGameOption:
			Intent intent = new Intent(this, GameOptionActivity.class);
			startActivity(intent);
			break;
		default:
			showToast("暂未开通！");
			break;
		}
	}
	
}
