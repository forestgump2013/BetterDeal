package com.way.betterdeal.fragments;

//import android.R;
import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

public class GameWelcomeFragment extends Fragment {
	
	MainActivity ma;
	
	View gameWelcomeView;
	ScrollView scrollView1;
//	LinearLayout backgroundView1;
	RelativeLayout backgroundView;
	ImageButton ninePanesBtn,slotMachineBtn;
	

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		gameWelcomeView=inflater.inflate(R.layout.game_welcome_fragment, container, false);
		scrollView1=(ScrollView)gameWelcomeView.findViewById(R.id.scrollView1);
		backgroundView=(RelativeLayout) gameWelcomeView.findViewById(R.id.backgroundView);
		ninePanesBtn=(ImageButton)gameWelcomeView.findViewById(R.id.ninePanesBtn);
		slotMachineBtn=(ImageButton)gameWelcomeView.findViewById(R.id.slotMachineBtn);
		scrollView1.setVerticalScrollBarEnabled(false);
		ma=(MainActivity)this.getActivity();
		ninePanesBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				ma.loadGameFragment(0);
			}
		});
		slotMachineBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				ma.loadGameFragment(1);
			//	ma.loadLoginFragment(true);
			}
		});
		initParams();
	//	if(StaticValueClass.isAfterKitKat)
	//		gameWelcomeView.setPadding(0, 0, 0, 0);
		return gameWelcomeView;
	}
	
	private void initParams(){

		ViewGroup.LayoutParams params1=(ViewGroup.LayoutParams)backgroundView.getLayoutParams();
		params1.height=StaticValueClass.screenWidth*1184/(720);
		backgroundView.setLayoutParams(params1);
		
		RelativeLayout.LayoutParams params2=(RelativeLayout.LayoutParams)ninePanesBtn.getLayoutParams();
		params2.width=StaticValueClass.screenWidth*318/720;
		params2.height=StaticValueClass.screenWidth*11/72;
		params2.topMargin=StaticValueClass.screenWidth*146/720;
		params2.leftMargin=StaticValueClass.screenWidth*25/720;
		
		RelativeLayout.LayoutParams params3=(RelativeLayout.LayoutParams)slotMachineBtn.getLayoutParams();
		params3.width=StaticValueClass.screenWidth*318/720;
		params3.height=StaticValueClass.screenWidth*11/72;
		params3.topMargin=StaticValueClass.screenWidth*146/720;
		params3.rightMargin=StaticValueClass.screenWidth*25/720;
		
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	//	scrollView1.setEnabled(false);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		scrollToTop();
	}

	private void scrollToTop(){

		scrollView1.post(new Runnable() {
			@Override
			public void run() {
					scrollView1.fullScroll(View.FOCUS_UP);
			}
		});

	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	

}
