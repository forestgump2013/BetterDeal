package com.way.betterdeal.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class ProgressView extends View {
	
	Paint mPaint,textPaint;
	int viewWidth,viewHeight,space,vCenter;
	int signCount=3,spaceNums=7,radius,offset,hBack;
	boolean signAction=false;

	public ProgressView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mPaint=new Paint();
		textPaint=new Paint();
		/*
		//TypedArray是一个用来存放由context.obtainStyledAttributes获得的属性的数组   
        //在使用完成后，一定要调用recycle方法   
        //属性的名称是styleable中的名称+“_”+属性名称   
        TypedArray array = context.obtainStyledAttributes(attrs, R.style.AppBaseTheme);   
        int textColor = array.getColor(R.styleable.MyView_textColor, 0XFF00FF00); //提供默认值，放置未指定   
        float textSize = array.getDimension(R.styleable.MyView_textSize, 36);   
        mPaint.setColor(textColor);   
        mPaint.setTextSize(textSize);   
           
        array.recycle(); //一定要调用，否则这次的设定会对下次的使用造成影响
        */
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		/*
		if(signAction){
			mPaint.setColor(Color.BLUE);
			if(signCount==1){
				canvas.drawCircle(25, vCenter, 20, mPaint);
			}else if(signCount%spaceNums==1){
				
			}else{
				int count=signCount%spaceNums,offset=(count-1)*space;
				canvas.drawRect(offset-space, viewHeight/3, offset, viewHeight*2/3, mPaint);
				canvas.drawCircle(offset, vCenter, 20, mPaint);
				
			}
			signAction=false;
			return;
		} */
		mPaint.setStyle(Style.FILL);
		mPaint.setColor(Color.GRAY);
		textPaint.setColor(Color.YELLOW);
		
		viewWidth=this.getWidth();
		viewHeight=this.getHeight();
		offset=viewWidth/10;
		space=(viewWidth-offset*2)/6;
		vCenter=viewHeight/2;
		radius=viewHeight/6+10;
		
		textPaint.setTextSize(radius*4/5);
		hBack=radius/6;
		//draw background.
		 canvas.drawRect(offset, viewHeight/3, viewWidth-offset, viewHeight*2/3, mPaint); //绘制矩形 
		 for(int i=0;i<7;i++){
			 canvas.drawCircle(offset+i*space, vCenter, radius, mPaint);
			 
		 }
		 
		 //draw colored view.
		 if(signCount>0){
			 int count=signCount%spaceNums;
			 int mul=signCount/spaceNums;
			 if(count==0) {
				 count=7;
				 mul--;
			 }
			 String str;
			 mPaint.setColor(Color.BLUE);
		//	 canvas.drawCircle(25, vCenter, 20, mPaint);
			 if(count>1)
				 canvas.drawRect(offset, viewHeight/3, offset+space*(count-1), viewHeight*2/3, mPaint);
			 for(int i=0;i<count;i++){
				 canvas.drawCircle(offset+i*space, vCenter,radius, mPaint);
				 str=Integer.toString(mul*spaceNums+i+1);
				 canvas.drawText(""+(mul*spaceNums+i+1), offset+i*space-radius/3-hBack*(str.length()-1), vCenter+5, textPaint);
			 }
		 }
		 
		
         
	     //   mPaint.setColor(Color.BLUE);   
	     //   canvas.drawText("我是被画出来的", 10, 120, mPaint);   
	}
	
	public void signOperation(){
		signCount++;
	//	signAction=true;
		this.postInvalidate();
	}
	
	public void init(int signDays){
		signCount=signDays;
		this.postInvalidate();
	}

}
