package com.way.betterdeal.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class CustomVerticalScrollView extends LinearLayout {
	Scroller mScroller;
	ImageView frontImageView;
	int frontViewHeight,height;
	float yDis,curY,lastY;

	public CustomVerticalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mScroller=new Scroller(context);
	}
	
	public void setFrontView(ImageView img){
		if(img==null)
			return;
		
		frontImageView=img;
		frontViewHeight=frontImageView.getHeight();
		Log.d("setFrontView", "frontViewHeight"+frontViewHeight);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		Log.d("onInterceptTouchEvent", "ev:"+ev.getAction());
		switch(ev.getAction()){
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_MOVE:
			break;
	    case MotionEvent.ACTION_UP:
	    //		if(this.getScrollY()<0)
		//			this.scrollTo(0, 0);
		}
		return true;
	//	return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		frontViewHeight=frontImageView.getHeight();
		Log.d("onTouchEvent", "frontViewHeight:"+frontViewHeight);
		switch (event.getAction()){
		case MotionEvent.ACTION_DOWN:
			yDis=0;
			lastY=event.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			curY=event.getY();
			yDis=curY-lastY;
			lastY=curY;
			if(yDis>0){
				//move down
				if( this.getScrollY()<=0)
					return true;
				if(frontImageView.getScrollY()>0)
					if((frontImageView.getScrollY()-yDis)<0)
						frontImageView.scrollTo(0, 0);
					else frontImageView.scrollBy(0, (int)-yDis);
				
			}else {
				//move up
				if( this.getScrollY()>=height)
					return true;
				if(frontImageView.getScrollY()<frontViewHeight)
					frontImageView.scrollBy(0, (int)-yDis/3);
			}
			
		//	mScroller.startScroll(0, this.getScrollY(), 0, (int)(-yDis),(int)Math.abs(yDis) * 2);
			
			this.scrollBy(0, (int)(-yDis));
		//	invalidate();
			Log.d("ACTION_MOVE", "curY"+curY);
			break;
		case MotionEvent.ACTION_UP:
			Log.d("ACTION_UP", "this.getScrollY():"+this.getScrollY());
			if(this.getScrollY()<0)
				this.scrollTo(0, 0);
			else if(this.getScrollY()>height){
				this.scrollTo(0, height);
			}
			if(frontImageView.getScrollY()<0)
				frontImageView.scrollTo(0, 0);
			else if(frontImageView.getScrollY()>frontViewHeight)
				frontImageView.scrollTo(0, frontViewHeight);
			
			break;
		}
		return true;
		//return super.onTouchEvent(event);
	}

	@Override
	public void computeScroll() {
		// TODO Auto-generated method stub
		Log.d("computeScroll","is called");
		if(mScroller.computeScrollOffset()){
			this.scrollTo(0, mScroller.getCurrY());
			this.postInvalidate();
		}
	//	super.computeScroll();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
		height=this.getHeight();
	}

}
