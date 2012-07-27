package com.game.pokerclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private Button btnGameStart;
	private Button btnGameExit;
	private Button btnGameOption;
	private Button btnGameHelp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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
			Intent intent = new Intent(this, FightLandLordActivity.class);
			startActivity(intent);
			break;
		case R.id.btnGameExit:
			finish();
			break;
		default:
			showToast("暂未开通！");
			break;
		}
	}
	
	public void showToast(final String text) {
		Toast.makeText(getApplicationContext(), text,	Toast.LENGTH_SHORT).show();
	}
	
}
