package com.way.betterdeal.fragments;

import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MobileBandFragment extends Fragment {
	
	MainActivity ma;
	Button backBtn;
	View view;
	public MobileBandFragment(){

	}
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		ma=(MainActivity)this.getActivity();
		view=inflater.inflate(R.layout.mobile_band_fragment, container, false);
		backBtn=(Button)view.findViewById(R.id.backBtn);
		if(StaticValueClass.isAfterKitKat)
			view.setPadding(0, StaticValueClass.statusBarHeight, 0, 0);
		reMeasure();
		view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		backBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		return view;
	}
	
	private void reMeasure(){
		Bitmap backmark=BitmapFactory.decodeResource(ma.getResources(), R.mipmap.expand_icon);
    	Drawable leftDrawable=new BitmapDrawable(StaticValueClass.getBackIcon(backmark));
    	leftDrawable.setBounds(0, 0, backmark.getWidth(), backmark.getHeight());
    	backBtn.setCompoundDrawables(leftDrawable, null, null, null);
    //	backBtn.setBackground(leftDrawable);
	}
	

}
