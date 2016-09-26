package com.way.betterdeal.view;

import com.cmad.swipe.SwipeRefreshLayout;

import android.content.Context;
//import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class CustomSwipeRefreshLayout extends SwipeRefreshLayout {
	
	private float xDistance, yDistance, xLast, yLast;
	
//	private int maxLength=280;

	public CustomSwipeRefreshLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
		xDistance = yDistance = 0f;
		xLast = ev.getX();
		yLast = ev.getY();
		break;
		case MotionEvent.ACTION_MOVE:
		//	Log.d("ACTION_MOVE 1", "");
		final float curX = ev.getX();
		final float curY = ev.getY();

		xDistance += Math.abs(curX - xLast);
		yDistance += Math.abs(curY - yLast);
		xLast = curX;
		yLast = curY;

		//neglect move in  X dicrection.
		if (xDistance > yDistance) {
			return false;
		}
		
		
		case MotionEvent.ACTION_UP:
		//	handler.sendMessageDelayed(handler.obtainMessage(1), 200);
			break;
		}
		return super.onInterceptTouchEvent(ev);
	}
	
	public void setMaxDistance(int dis){
	//	this.setMaxDistance(dis);
	//	super.setMaxDistanceY(dis);
	}
	

}
