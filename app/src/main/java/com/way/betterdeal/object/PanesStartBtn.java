package com.way.betterdeal.object;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.Button;

public class PanesStartBtn extends Button {

	public PanesStartBtn(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		
		if(event.getAction()==MotionEvent.ACTION_DOWN){
			this.getParent().requestDisallowInterceptTouchEvent(true);
		}
		return super.onTouchEvent(event);
	}
	
	

}
