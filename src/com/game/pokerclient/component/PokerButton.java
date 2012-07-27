/*
 * @ Project: Pokerclient
 * @ File Name: PokerButton.java
 * @ Date: 2012-7-19
 * @ Author: Song,Jie
 * Copyright 2012 NHNST. All Rights Reserved.
 */
package com.game.pokerclient.component;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
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

	private String value;

	private Context context;
	
	private float scaleWidth = 1;
	private float scaleHeight = 1;
	/**
	 * @param context
	 */
	public PokerButton(Context context) {
		super(context);
		this.context = context;
		this.setScaleType(ScaleType.CENTER_INSIDE);
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.BOTTOM | Gravity.LEFT;
		this.setLayoutParams(params);
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

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int eventaction = event.getAction();
		switch (eventaction) {
			case MotionEvent.ACTION_DOWN:
				break;
			case MotionEvent.ACTION_MOVE: {
				break;
			}
			case MotionEvent.ACTION_UP: {
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
	 * @param allowSelect
	 *            the allowSelect to set
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

	public void setImage(String pk) {
		try {
			Bitmap bitmap = getBitmapFromAsset("poker/p_" + pk.toLowerCase()
					+ ".png");
			this.setImageBitmap(bitmap);
		} catch (IOException e) {
			Log.e(TAG, "setImage method error param is " + pk);
			e.printStackTrace();
		}
	}

	private Bitmap getBitmapFromAsset(String strName) throws IOException {
		AssetManager assetManager = this.context.getAssets();
		InputStream istr = assetManager.open(strName);
		Bitmap bitmap = BitmapFactory.decodeStream(istr);
		int bmpWidth=bitmap.getWidth();
	    int bmpHeight=bitmap.getHeight();
	    /* 设置图片缩小的比例 */
	    double scale=0.6;  
	    /* 计算出这次要缩小的比例 */
	    scaleWidth=(float) (scaleWidth*scale);
	    scaleHeight=(float) (scaleHeight*scale);
	    
	    /* 产生reSize后的Bitmap对象 */
	    Matrix matrix = new Matrix();  
	    matrix.postScale(scaleWidth, scaleHeight); 
	    Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bmpWidth,
                bmpHeight,matrix,true);
		return resizeBmp;
	}

}
