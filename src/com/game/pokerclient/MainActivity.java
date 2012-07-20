package com.game.pokerclient;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.game.component.PokerButton;

public class MainActivity extends Activity {

	private ScrollView scrollView;
	private Button btnRun;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        
        btnRun = (Button)findViewById(R.id.btnRun);
        btnRun.setOnClickListener(new View.OnClickListener() {
        	int time = 0;
			@Override
			public void onClick(View view) {
				time++;
				initPoker(time);
			}
		});
        
    }
    
    private void initPoker(int time) {
    	RelativeLayout layout = (RelativeLayout)findViewById(R.id.main_layout);
        for (int i = 0; i < layout.getChildCount(); i++) {
        	View view = layout.getChildAt(i);
        	if (view instanceof ScrollView) {
        		scrollView = (ScrollView)view;
        		LinearLayout ll = (LinearLayout) scrollView.getChildAt(0);
        		PokerButton pokerButton = new PokerButton(this);
                pokerButton.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
                pokerButton.setBackgroundResource(R.drawable.va1);
                ll.addView(pokerButton);
        	}
        }
        
        FrameLayout dealedLayout = (FrameLayout)findViewById(R.id.dealedLayout);
        for (int i = 0; i < dealedLayout.getChildCount(); i++) {
        	FrameLayout fl = (FrameLayout) dealedLayout.getChildAt(i);
    		PokerButton pokerButton = new PokerButton(this);
    		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
    		params.gravity = Gravity.TOP;
    		params.topMargin = time * 20;
            pokerButton.setLayoutParams(params);
            pokerButton.setBackgroundResource(R.drawable.va1);
            fl.addView(pokerButton);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    
}
