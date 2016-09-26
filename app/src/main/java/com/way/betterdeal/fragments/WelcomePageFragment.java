package com.way.betterdeal.fragments;

import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;
import com.way.betterdeal.view.LetterSpacingTextView;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class WelcomePageFragment extends Fragment {

	
	View view;
	ImageView imageView,sloganImage;
	LetterSpacingTextView textView;
//	TextView statusbar;
	int background=0,resID1,resID2,height1,height2;
	String guide;
	Typeface typeface;
	Button enterBtn;
	boolean last=false,showStatusBar=false;
	MainActivity ma;
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		view=inflater.inflate(R.layout.welcome_page_fragment, container, false);
		imageView=(ImageView)view.findViewById(R.id.imageView);
		sloganImage=(ImageView)view.findViewById(R.id.sloganImage);
	//	statusbar=(TextView)view.findViewById(R.id.statusbar);
		textView=(LetterSpacingTextView)view.findViewById(R.id.textView);
		typeface =Typeface.createFromAsset(this.getActivity().getAssets(),"fonts/hanyi_thin_round1.ttf");
		textView.setTypeface(typeface);
		textView.setSpaceing(2);
		enterBtn=(Button)view.findViewById(R.id.enterBtn);
		enterBtn.setVisibility(View.GONE);
		setCustomView();
		return view;
	//	return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	private void setCustomView(){
		
		if(background>0){
			view.setBackgroundResource(background);
		}
		
		imageView.setImageResource(resID1);
		RelativeLayout.LayoutParams params1=(RelativeLayout.LayoutParams)imageView.getLayoutParams();
		params1.height=StaticValueClass.screenWidth*height1/720;
		
		//
		sloganImage.setImageResource(resID2);
		RelativeLayout.LayoutParams params2=(RelativeLayout.LayoutParams)sloganImage.getLayoutParams();
		params2.height=StaticValueClass.screenWidth*height2/720;
		Handler handler=new Handler();
		handler.postDelayed(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				textView.setText(guide);
			}
			
		}, 20);
		//
		if(last){
			 ma=(MainActivity)WelcomePageFragment.this.getActivity();
			enterBtn.setVisibility(View.VISIBLE);
			enterBtn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					ma.removeWelcomeFragment();
				}
			});
		}
	
		
	}
	
	public void setLastPage(boolean flag){
		last=flag;
	}
	
	public void setMainImage(int resID,int height){
		resID1=resID;
		height1=height;
	}
	
	public void setSloganImage(int resID,int height){
		resID2=resID;
		height2=height;
	}
	
	public void setSloganText(String text){
		guide=text;
	}
	
	public void setBackground(int resID){
		background=resID;
	}

	public void setStatusBar(){
		showStatusBar=true;
	}
	
}
