/*
 * @ Project: Pokerclient
 * @ File Name: PokerButton.java
 * @ Date: 2012-7-19
 * @ Author: Song,Jie
 * Copyright 2012 NHNST. All Rights Reserved.
 */
package com.game.component;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Class Description
 * 
 * <pre>
 * Filename        : PokerButton.java , 2012-7-19
 * Creator         : SongJie
 * Revised Description
 * ----------------------------------------------
 * revised date    reviser   revised contents
 * ----------------------------------------------
 * 
 * </pre>
 */
public class PokerButton extends ImageView {
	private static final String TAG = "PokerButton";  

	private boolean allowSelect = true;
	
    private boolean selected = false;
    
	/**
	 * @param context
	 */
	public PokerButton(Context context) {
		super(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public PokerButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public PokerButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int eventaction = event.getAction();
		switch (eventaction) {
		case MotionEvent.ACTION_DOWN:
			Log.i(TAG, "MotionEvent.ACTION_DOWN~~");  
			break;
		case MotionEvent.ACTION_MOVE: {
			Log.i(TAG, "MotionEvent.ACTION_MOVE~~");  
			break;

		}
		case MotionEvent.ACTION_UP: {
			Log.i(TAG, "MotionEvent.ACTION_UP~~");  
			if (allowSelect) {
				setSelected(!this.selected);
			}
			break;
		}
		}
		this.invalidate();
		return true;

	}

	/**
	 * @return the allowSelect
	 */
	public boolean isAllowSelect() {
		return allowSelect;
	}

	/**
	 * @param allowSelect the allowSelect to set
	 */
	public void setAllowSelect(boolean allowSelect) {
		this.allowSelect = allowSelect;
	}

	public void setSelected(boolean selectedFlag) {
		int top = this.getTop();
		int bottom = this.getBottom();
		if (!allowSelect || this.selected == selectedFlag) {
			return;
		}
		this.selected = selectedFlag;
		if (this.selected) {
			top -= 10;
			bottom -= 10;
		} else {
			top += 10;
			bottom += 10;
		}
		this.layout(this.getLeft(), top, this.getRight(), bottom);
	}

	/**
	 * @return the selected
	 */
	public boolean isSelected() {
		return selected;
	}
	
	
}
