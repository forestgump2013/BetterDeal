package com.way.betterdeal.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

public class HuaKangTextView extends TextView {

	
	public HuaKangTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/huagang_girl.ttf"));
	//	this.setGravity(Gravity.BOTTOM);
		this.setTextColor(Color.WHITE);
		
	//	this.setPadding(left, top, right, bottom)
	}

	@Override
	public void setTextColor(int color) {
		// TODO Auto-generated method stub
		super.setTextColor(color);
	}
	
	

}
