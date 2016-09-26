package com.way.betterdeal.fragments;

import java.util.Date;

import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;
import com.way.betterdeal.view.ProgressView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class TaskFragment extends Fragment {
	
	MainActivity ma;
	View signView;
	ProgressView progressView;
	Button signBtn,complementBtn,shareBtn;
	int differ;
	public TaskFragment(){

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		ma=(MainActivity)this.getActivity();
		signView=inflater.inflate(R.layout.task_fragment, container, false);
	//	return super.onCreateView(inflater, container, savedInstanceState);
		progressView=(ProgressView)signView.findViewById(R.id.progressView);
		signBtn=(Button)signView.findViewById(R.id.signBtn); 
		shareBtn=(Button)signView.findViewById(R.id.shareBtn); 
		complementBtn=(Button)signView.findViewById(R.id.complementBtn); 
	//	initProgressView();
		signBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			//	signBtn.setText(""+differ);
				if(differ==0){
					Toast.makeText(ma, "今日已签到!", Toast.LENGTH_LONG).show();
					return;
				}else if(differ==1){
					StaticValueClass.currentBuyer.consecutive_sign_days++;
					if(StaticValueClass.currentBuyer.consecutive_sign_days%7==0){
						StaticValueClass.currentBuyer.bonus+=50;
					}
				}else if(differ>1){
					StaticValueClass.currentBuyer.consecutive_sign_days=1;
				}
				StaticValueClass.currentBuyer.bonus+=20;
				StaticValueClass.currentBuyer.last_sign_date=StaticValueClass.dateFormat.format(new Date());
				progressView.signOperation();
				ma.updateBuyerBonus();
				StaticValueClass.updateBuyerInfo(ma, StaticValueClass.currentBuyer);
				return;
				
			}
		});
		complementBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			//	ma.loadComplementFragment();
			}
		});
		shareBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			//	ma.loadShareFragment();
				ma.showShareDialog("");
			}
		});
		return signView;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}
	

}
