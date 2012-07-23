package com.game.pokerclient;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.game.model.fightlandlord.FightLandlordPoker;
import com.game.pokerclient.component.PokerButton;
import com.game.pokerclient.state.PokerService;

public class MainActivity extends Activity implements OnClickListener {

	private FrameLayout framelayout;
	private Button btnRun;
	private PokerService pokerService;

	private ServiceConnection serviceConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName name, IBinder service) {
			pokerService = ((PokerService.PokerBinder) service).getService();
			Map<Integer, String> playerPokers = pokerService.getFightLandlordPoker();
			initPoker(playerPokers);
		}

		public void onServiceDisconnected(ComponentName name) {

		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		setupViews();
	}
	
	public void setupViews(){  
		btnRun = (Button) findViewById(R.id.btnRun);
		btnRun.setOnClickListener(this);
		
		LinearLayout btnBar = (LinearLayout) findViewById(R.id.buttonsBar);
		for (int i = 0; i < btnBar.getChildCount(); i++) {
			if (btnBar.getChildAt(i) instanceof Button) {
				Button button = (Button)btnBar.getChildAt(i);
				button.setOnClickListener(this);
			}
		}
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

		if (view == findViewById(R.id.btnOut)) {
			outPoker();
		} else if (view == findViewById(R.id.btnReset)) {
			resetPoker();
		}
	}
	
	private void reset() {
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.main_layout);
		removeChild(layout);
		LinearLayout linearLayout = (LinearLayout)findViewById(R.id.candidatedTop);
		linearLayout.removeAllViews();
	}
	
	private void removeChild(ViewGroup layout) {
		for (int i = 0; i < layout.getChildCount(); i++) {
			View view = layout.getChildAt(i);
			if (view instanceof FrameLayout) {
				framelayout = (FrameLayout)view;
				removeChild(framelayout);
			} else {
				framelayout.removeAllViews();
			}
		}
	}
	
	private void resetPoker() {
		FrameLayout candidatedDown = (FrameLayout) findViewById(R.id.candidatedDown);
		for (int i = 0; i < candidatedDown.getChildCount(); i++) {
			PokerButton pokerButton = (PokerButton) candidatedDown.getChildAt(i);
			pokerButton.setSelected(false);
		}
	}
	
	private void outPoker() {
		int y = 0;
		FrameLayout dealedBottom = (FrameLayout) findViewById(R.id.dealedBottom);
		FrameLayout candidatedDown = (FrameLayout) findViewById(R.id.candidatedDown);
		List<PokerButton> dealedCards = new ArrayList<PokerButton>();
		List<PokerButton> unDealedCards = new ArrayList<PokerButton>();
		// 记录出牌和未出牌
		for (int i = 0; i < candidatedDown.getChildCount(); i++) {
			PokerButton pokerButton = (PokerButton) candidatedDown.getChildAt(i);
			if (pokerButton.isSelected()) {
				pokerButton.setAllowSelect(false);
				dealedCards.add(pokerButton);
			} else {
				unDealedCards.add(pokerButton);
			}
		}
		// 重截手中牌区
		candidatedDown.removeAllViews();
		for (PokerButton pb : unDealedCards) {
			FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.gravity = Gravity.LEFT;
			params.leftMargin = y+=20;
			pb.setLayoutParams(params);
			candidatedDown.addView(pb);
		}
		// 更新出牌区
		y = 0;
		dealedBottom.removeAllViews();
		for (PokerButton pb : dealedCards) {
			FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.gravity = Gravity.LEFT;
			params.leftMargin = y+=20;
			pb.setLayoutParams(params);
			dealedBottom.addView(pb);
		}
	}

	private void initPoker(Map<Integer, String> playerPokers) {
		int x = 0;
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.main_layout);
		for (int i = 0; i < layout.getChildCount(); i++) {
			View view = layout.getChildAt(i);
			if (view instanceof FrameLayout) {
				if (view != findViewById(R.id.dealedLayout)) {
					framelayout = (FrameLayout)view;
					List<String> pks = FightLandlordPoker.sortPokers(playerPokers.get(x).replace(",first", ""));
					int y = 0;
					for (String pk : pks) {
						PokerButton pokerButton = new PokerButton(this);
						FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
								LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
						if (view == findViewById(R.id.candidatedDown)) {
							params.gravity = Gravity.LEFT;
							params.leftMargin = y+=20;
						} else {
							params.gravity = Gravity.TOP;
							params.topMargin = y+=20;
							pokerButton.setAllowSelect(false);
						}
						pokerButton.setLayoutParams(params);
						pokerButton.setValue(pk);
						try {
							if (pk.equalsIgnoreCase("first")) {
								continue;
							}
							Bitmap bitmap = getBitmapFromAsset("poker/p_" + pk.toLowerCase() + ".png");
							pokerButton.setImageBitmap(bitmap);
						} catch (IOException e) {
							e.printStackTrace();
						}
						framelayout.addView(pokerButton);
					}
					x++;
				}
			}
		}
		// 3张底牌
		LinearLayout linearLayout = (LinearLayout)findViewById(R.id.candidatedTop);
		String pks[] = {"back", "back", "back"};
		for (String pk : pks) {
			PokerButton pokerButton = new PokerButton(this);
			pokerButton.setAllowSelect(false);
			pokerButton.setValue(pk);
			try {
				Bitmap bitmap = getBitmapFromAsset("poker/p_" + pk.toLowerCase() + ".png");
				pokerButton.setImageBitmap(bitmap);
			} catch (IOException e) {
				e.printStackTrace();
			}
			linearLayout.addView(pokerButton);
		}
	}
	
	private Bitmap getBitmapFromAsset(String strName) throws IOException
	{
	    AssetManager assetManager = getAssets();
	    InputStream istr = assetManager.open(strName);
	    Bitmap bitmap = BitmapFactory.decodeStream(istr);
	    return bitmap;
	 }
}
