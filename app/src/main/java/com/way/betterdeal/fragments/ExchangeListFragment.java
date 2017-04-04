package com.way.betterdeal.fragments;

import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ExchangeListFragment extends Fragment {
	MainActivity ma;
	View view;
	Button doneBtn;
	
	public ExchangeListFragment(){

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		ma=(MainActivity)this.getActivity();
		/*
		view=inflater.inflate(R.layout.exchange_list_fragment, container, false);
		doneBtn=(Button)view.findViewById(R.id.doneBtn);
		doneBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ma.onBackPressed();
				ma.onBackPressed();
				ma.showShareDialog("兑换分享");
			}
		});
		*/
		return view;
	}
	

}
