package com.way.betterdeal.object;

import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RadioButton;

public class MyRadioButton extends RadioButton {
	
	private Drawable drawableTop;  
    private int mTopWith, mTopHeight;
    public boolean redPointed;
    Paint paint;

    public MyRadioButton(Context context, AttributeSet attrs, int defStyle) {  
        super(context, attrs, defStyle);  
        initView(context, attrs);  
    }  
  
    public MyRadioButton(Context context, AttributeSet attrs) {  
        super(context, attrs);  
        initView(context, attrs);  
    }  
  
    public MyRadioButton(Context context) {  
        super(context);  
        initView(context, null);  
    }  
	
	private void initView(Context context, AttributeSet attrs) {  
        if (attrs != null) {  
            float scale = context.getResources().getDisplayMetrics().density;  
         //   R.styleable
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyRadioButton);  
            int n = a.getIndexCount();  
            for (int i = 0; i < n; i++) {  
                int attr = a.getIndex(i);  
                switch (attr) {  
                case R.styleable.MyRadioButton_drawableTop:  
                    drawableTop = a.getDrawable(attr);  
                    break;  
                case R.styleable.MyRadioButton_drawableTopWidth:  
                	
                //    mTopWith = (int) (a.getDimension(attr, 20) * scale + 0.5f);  
                	mTopWith = (int)a.getDimension(attr, 20) ; 
                	 Log.d("***** attr width", ""+a.getDimension(attr, 20));
                 //  mTopWith=30;
                    break;  
                case R.styleable.MyRadioButton_drawableTopHeight:  
                 //   mTopHeight = (int) (a.getDimension(attr, 20) * scale + 0.5f);  
                    mTopHeight =  (int)a.getDimension(attr, 20) ;
                    Log.d("***** attr height", ""+a.getDimension(attr, 20));
                  //  mTopHeight=30;
                    break;  
                }  
            }  
            a.recycle();  
            setCompoundDrawablesWithIntrinsicBounds(null, drawableTop, null, null);  
        }
        //.
        redPointed=true;
        paint=new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
    }  
	
	  // 设置Drawable定义的大小  
    @Override  
    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom) {  
        if (top != null) {  
            top.setBounds(0, 0, mTopWith <= 0 ? top.getIntrinsicWidth() : mTopWith, mTopHeight <= 0 ? top.getMinimumHeight() : mTopHeight);  
        }  
          
        setCompoundDrawables(left, top, right, bottom);  
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return super.getAccessibilityClassName();
    }




    public void setRedPointed(boolean flag){
        redPointed=flag;
        this.invalidate();
    }
}
