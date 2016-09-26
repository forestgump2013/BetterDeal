package com.way.betterdeal.fragments;

import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

public class CommodityDetailFragment extends Fragment {
	
	MainActivity ma;
	
	View view;
	Button backBtn;
	WebView webView;
	String webUrl="";
	public CommodityDetailFragment(){

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		ma=(MainActivity)this.getActivity();
		view=inflater.inflate(R.layout.commodity_detail_fragment, container, false);
		backBtn=(Button)view.findViewById(R.id.backBtn);
		webView=(WebView)view.findViewById(R.id.webView);
		//
		Bitmap backmark= BitmapFactory.decodeResource(this.getActivity().getResources(), R.mipmap.expand_icon);
		Drawable leftDrawable=new BitmapDrawable(StaticValueClass.getBackIcon(backmark));
		leftDrawable.setBounds(0, 0, backmark.getWidth(), backmark.getHeight());
		backBtn.setBackground(leftDrawable);
		//
		WebSettings webSettings=webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		webView.loadUrl(webUrl);
		if(StaticValueClass.isAfterKitKat)
			view.setPadding(0, StaticValueClass.statusBarHeight, 0, 0);
		initFunction();
		return view;
	}

	public void setWebUrl(String url){
		webUrl=url;
	}

	private void initFunction(){
		ma=(MainActivity)this.getActivity();
		backBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ma.onBackPressed();
			}
		});
	}
	

}
