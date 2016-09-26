package com.way.betterdeal.fragments;

import com.way.betterdeal.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class WebViewFragment extends Fragment {
	
	View view;
	WebView webView;

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.webview_fragment, container, false);
		return view;
	//	return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	public void loadWebView(String url){
		if(webView==null){
			webView=(WebView)view.findViewById(R.id.webView1);
			WebSettings webSettings=webView.getSettings();
			webSettings.setJavaScriptEnabled(true);
			webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		}
		webView.loadUrl(url);
	}
	
	

}
