package com.way.betterdeal.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.way.betterdeal.R;
import com.way.betterdeal.object.PicUtil;

/**
 * TODO: document your custom view class.
 */
public class EraseView extends View {
    Paint mPaint;
    String shapType="circle";
  //  Bitmap ovalBitmap,bitmap1;

    public EraseView(Context context) {
        super(context);
    }

    public EraseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
     //   ovalBitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.oval_trans);
        init(attrs, 0);
    }

    public EraseView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mPaint=new Paint();
        init(attrs, defStyle);
    }


    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
               attrs, R.styleable.EraseView, defStyle, 0);
        shapType = a.getString(  R.styleable.EraseView_shapeType);

        a.recycle();
        this.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int sc = canvas.saveLayer(0, 0, this.getWidth(), this.getHeight(), null, Canvas.ALL_SAVE_FLAG);
        canvas.drawColor(0xcc000000);

        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        Log.d("***EraseView","shapType:"+shapType);
        if(shapType.equals("oval")){
      //      bitmap1=PicUtil.zoomBitmap(ovalBitmap,this.getWidth(),this.getHeight());
           RectF oval2 = new RectF(0, 0, this.getWidth(), this.getHeight());
            canvas.drawOval(oval2,mPaint);
          //  canvas.drawBitmap(bitmap1,0,0,mPaint);
        }else  canvas.drawCircle(this.getWidth()/2,this.getHeight()/2,this.getHeight()/2,mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(sc);

    }
}
