package com.way.betterdeal.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/*
 * CustomScrollView
 * 
 * does n't intercept horizonal slide
 * when sliding the bottom automatically loads new items 
 */
public class CustomScrollView extends ScrollView {
	private boolean canScroll,initFinished=false;
	private GestureDetector mGestureDetector;
	View.OnTouchListener mGestureListener;
	//--------------------------------------
	private float xDistance, yDistance, xLast, yLast;
	//View firstChild;
	static Handler handler;
	OnScrollToBottomListener onScrollToBottomListener;
	

	public CustomScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mGestureDetector=new GestureDetector(new YScrollDetector());
		canScroll=true;
		init();
	}
	
	@Override
	protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX,
			boolean clampedY) {
		// TODO Auto-generated method stub
		super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
		if(scrollY!=0 && clampedY && initFinished){
			onScrollToBottomListener.onScrollBottomListener(clampedY);
			//Log.d("CustomScrollveiw ***", "bottom");
		}
	}
    public void setInitFinish(boolean flag){
    	initFinished=flag;
    }
    
    public void setOnScrollToBottomListener(OnScrollToBottomListener listener){
    	onScrollToBottomListener=listener;
    }
    
	private void init(){
		handler=new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch(msg.what){
				case 1: 
				}
			}
			
		};
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		/*
		if(ev.getAction()==MotionEvent.ACTION_UP){
			canScroll=true;
		}
		return super.onInterceptTouchEvent(ev) && mGestureDetector.onTouchEvent(ev);
		*/
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
		xDistance = yDistance = 0f;
		xLast = ev.getX();
		yLast = ev.getY();
		break;
		case MotionEvent.ACTION_MOVE:
			Log.d("ACTION_MOVE 1", "");
		final float curX = ev.getX();
		final float curY = ev.getY();

		xDistance += Math.abs(curX - xLast);
		yDistance += Math.abs(curY - yLast);
		xLast = curX;
		yLast = curY;

		if (xDistance > yDistance) {
		return false;
		}
		case MotionEvent.ACTION_UP:
		//	handler.sendMessageDelayed(handler.obtainMessage(1), 200);
			break;
		}

		return super.onInterceptTouchEvent(ev);
		
	}
	
	
	public interface OnScrollToBottomListener{  
        public void onScrollBottomListener(boolean isBottom);  
    }  

	class YScrollDetector extends SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                        float distanceX, float distanceY) {
                if (canScroll)
                        if (Math.abs(distanceY) >= Math.abs(distanceX))
                                canScroll = true;
                        else
                                canScroll = false;
                return canScroll;
        }
}

}
