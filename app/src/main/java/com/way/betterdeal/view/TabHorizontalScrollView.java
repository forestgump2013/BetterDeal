package com.way.betterdeal.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

public class TabHorizontalScrollView extends HorizontalScrollView {
	
	private int left,right,tabCount,tabWidth;

	public TabHorizontalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	//	left=right=tabCount=tabWidth=0;
	}
	
	public void  initTab(int count,int width){
		tabCount=count;
		tabWidth=width;
		left=0;
		right=count-1;
	}
	
	public void checkScroll(int currentTab){
		if(currentTab>=left && currentTab<=right){
			return;
		}else if(currentTab<left){
			this.scrollBy(-tabWidth, 0);
			left--;
			right--;
		} else if(currentTab>right){
			this.scrollBy(tabWidth, 0);
			left++;
			right++;
		}
		
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		return false;
	//	return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		return false;
	//	return super.onTouchEvent(ev);
	}

}
