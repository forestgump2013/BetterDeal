package com.way.betterdeal;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ManagerActivity extends Activity {
	
    
    TextView resultText;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manager_layout);
		resultText=(TextView)findViewById(R.id.resultText);
		testGetCommodity();
		
	}
	
	private void testGetCommodity(){
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				
			}
			
		}).start();
		
		
		
		
	}

}
