package com.way.betterdeal.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class ControllingHoriontalLinearlayout extends LinearLayout {
	
	private Scroller mScroller;

	public ControllingHoriontalLinearlayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mScroller=new Scroller(context);
	}

	@Override
	public void computeScroll() {
		// TODO Auto-generated method stub
	//	super.computeScroll();
		if(mScroller.computeScrollOffset()){
			this.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			this.postInvalidate();
		}
	}
	
	public void horizontalMove(int dis){
		
		mScroller.startScroll(this.getScrollX(), 0, dis, 0, Math.abs(dis)*2);
		this.invalidate();
		
	}
	

}
