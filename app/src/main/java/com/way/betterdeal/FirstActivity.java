package com.way.betterdeal;

import java.util.Timer;
import java.util.TimerTask;

import com.way.betterdeal.adapters.GameBonusRecordsAdapter.Holder;
import com.way.betterdeal.object.AsynImageLoader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class FirstActivity extends Activity {

	Button button1;
	Handler handler;
	ImageView imageView1,imageView2,imageView3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//透明状态栏  
	       getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  
	        //透明导航栏  
	      getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);  
	      //
	      DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		StaticValueClass.screenWidth = metric.widthPixels;
		StaticValueClass.screenHeight = metric.heightPixels;
		StaticValueClass.screenDensity=metric.densityDpi;
		StaticValueClass.density=metric.density;
		StaticValueClass.scaleDensity=metric.scaledDensity;  
		setContentView(R.layout.first_activity);
		imageView1=(ImageView)findViewById(R.id.imageView1);
		imageView2=(ImageView)findViewById(R.id.imageView2);
		imageView3=(ImageView)findViewById(R.id.imageView3);
		initParams();
        handler=new Handler();
		new Timer().schedule(new TimerTask(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent intent=new Intent(FirstActivity.this,MainActivity.class);
				FirstActivity.this.startActivity(intent);
				
				try {
					Thread.sleep(800);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finish();
			}
			
		}, 800);
		
		StaticValueClass.firstActiviy=this;
	
	}
	
	private void initParams(){
		int scale=StaticValueClass.screenWidth;
		RelativeLayout.LayoutParams params1=(RelativeLayout.LayoutParams)imageView1.getLayoutParams();
		params1.width=scale*360/720;
		params1.height=scale*190/720;
		RelativeLayout.LayoutParams params2=(RelativeLayout.LayoutParams)imageView2.getLayoutParams();
		params2.width=scale*480/720;
		params2.height=scale*120/720;
		RelativeLayout.LayoutParams params3=(RelativeLayout.LayoutParams)imageView3.getLayoutParams();
		params3.width=scale*320/720;
		params3.height=scale*88/720;
	}

}
