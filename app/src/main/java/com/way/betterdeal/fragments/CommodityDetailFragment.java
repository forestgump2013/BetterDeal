package com.way.betterdeal.fragments;

import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;
import com.way.betterdeal.object.Commodity;

import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.InputStream;

public class CommodityDetailFragment extends Fragment {
	
	MainActivity ma;
	
	View view;
	Button backBtn,drawBtn;
	ProgressBar progressBar;
	LinearLayout webViewFrame;
	WebView webView;
	String webUrl="",resourceURL;
	Commodity currentCommodity;
	private CheckBox favouriteCheck;
	TextView likeText,partText11,partText12,partText13,timeText;
	RelativeLayout part2,part1;
	public CommodityDetailFragment(){

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		ma=(MainActivity)this.getActivity();
		view=inflater.inflate(R.layout.commodity_detail_fragment, container, false);
		backBtn=(Button)view.findViewById(R.id.backBtn);
		drawBtn=(Button)view.findViewById(R.id.drawBtn);
		progressBar=(ProgressBar)view.findViewById(R.id.progressBar);
		webViewFrame=(LinearLayout)view.findViewById(R.id.webViewFrame);
	//	webView=(WebView)view.findViewById(R.id.webView);
		webViewFrame.removeAllViews();
		webViewFrame.addView(webView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
		part2=(RelativeLayout)view.findViewById(R.id.part2);
		part1=(RelativeLayout)view.findViewById(R.id.part1);
		likeText=(TextView) view.findViewById(R.id.likeText);
		timeText=(TextView)view.findViewById(R.id.timeText);
		partText11=(TextView) view.findViewById(R.id.partText11);
		partText12=(TextView) view.findViewById(R.id.partText12);
		partText13=(TextView) view.findViewById(R.id.partText13);
		favouriteCheck=(CheckBox)view.findViewById(R.id.favouriteCheck);
		//
		//webView.loadUrl(currentCommodity.webLink);
	 //	initWebView();
		webView.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
			//	view.loadUrl(url);
				return shouldOverrideUrlByDUOSHOU(view,url);
			}



			@Override
			public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
				resourceURL=request.toString();
				Log.d("my Request","url:"+resourceURL);
				if (resourceURL.contains("common.css") && resourceURL.contains("loading.css") && resourceURL.contains("header.css")){
					Log.d("shouldInterceptRequest","local css:"+resourceURL);
					try{
						InputStream localCopy = ma.getAssets().open("html/taobao_index.css");
						WebResourceResponse webResourceResponse=new WebResourceResponse("text/css", "UTF-8",localCopy);
						return webResourceResponse;
					}catch (Exception e){
						e.printStackTrace();
					}
					return super.shouldInterceptRequest(view, request);
				}else if (resourceURL.contains("kissy") && resourceURL.contains("seed-min.js") && resourceURL.contains("polyfill")){
					Log.d("shouldInterceptRequest","local js:"+resourceURL);
					try{
						InputStream localCopy = ma.getAssets().open("html/taobao_index.js");
						WebResourceResponse webResourceResponse=new WebResourceResponse("text/javascript", "UTF-8",localCopy);
						return webResourceResponse;
					}catch (Exception e){
						e.printStackTrace();
					}
					return super.shouldInterceptRequest(view, request);
				}else if(resourceURL.contains("liuliangbao/track.do")){
					return null;
				} else if (resourceURL.contains("recommend.htm")){
					return null;
				}else if(resourceURL.contains("app-download-popup")&& resourceURL.contains(".css")){
					Log.d("shouldInterceptRequest","local css:"+resourceURL);
					try{
						InputStream localCopy = ma.getAssets().open("html/taobao_app_download_popup.css");
						WebResourceResponse webResourceResponse=new WebResourceResponse("text/css", "UTF-8",localCopy);
						return webResourceResponse;
					}catch (Exception e){
						e.printStackTrace();
					}
					return 	super.shouldInterceptRequest(view, request);
				}else  if(resourceURL.contains("app-download-popup")&& resourceURL.contains(".js")){
					Log.d("shouldInterceptRequest","local js:"+resourceURL);
					try{
						InputStream localCopy = ma.getAssets().open("html/taobao_app_download_popup.js");
						WebResourceResponse webResourceResponse=new WebResourceResponse("text/javascript", "UTF-8",localCopy);
						return webResourceResponse;
					}catch (Exception e){
						e.printStackTrace();
					}
					return 	super.shouldInterceptRequest(view, request);
				}
				return super.shouldInterceptRequest(view, request);
			}

			@Override
			public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
				Log.d("oldInterceptRequest","url:"+url);
				resourceURL=url;
				if (resourceURL.contains("common.css") && resourceURL.contains("loading.css") && resourceURL.contains("header.css")){
					Log.d("shouldInterceptRequest","local css:"+resourceURL);
					try{
						InputStream localCopy = ma.getAssets().open("html/taobao_index.css");
						WebResourceResponse webResourceResponse=new WebResourceResponse("text/css", "UTF-8",localCopy);
						return webResourceResponse;
					}catch (Exception e){
						e.printStackTrace();
					}
					return super.shouldInterceptRequest(view, url);
				}else if (resourceURL.contains("kissy") && resourceURL.contains("seed-min.js") && resourceURL.contains("polyfill")){
					Log.d("shouldInterceptRequest","local js:"+resourceURL);
					try{
						InputStream localCopy = ma.getAssets().open("html/taobao_index.js");
						WebResourceResponse webResourceResponse=new WebResourceResponse("text/javascript", "UTF-8",localCopy);
						return webResourceResponse;
					}catch (Exception e){
						e.printStackTrace();
					}
				   return 	super.shouldInterceptRequest(view, url);
				}else if(resourceURL.contains("liuliangbao/track.do")){
					return null;
				} else if (resourceURL.contains("recommend.htm")){
					return null;
				}else if(resourceURL.contains("app-download-popup")&& resourceURL.contains(".css")){
					Log.d("shouldInterceptRequest","local css:"+resourceURL);
					try{
						InputStream localCopy = ma.getAssets().open("html/taobao_app_download_popup.css");
						WebResourceResponse webResourceResponse=new WebResourceResponse("text/css", "UTF-8",localCopy);
						return webResourceResponse;
					}catch (Exception e){
						e.printStackTrace();
					}
					return 	super.shouldInterceptRequest(view, url);
				}else  if(resourceURL.contains("app-download-popup")&& resourceURL.contains(".js")){
					Log.d("shouldInterceptRequest","local js:"+resourceURL);
					try{
						InputStream localCopy = ma.getAssets().open("html/taobao_app_download_popup.js");
						WebResourceResponse webResourceResponse=new WebResourceResponse("text/javascript", "UTF-8",localCopy);
						return webResourceResponse;
					}catch (Exception e){
						e.printStackTrace();
					}
					return 	super.shouldInterceptRequest(view, url);
				}
				return super.shouldInterceptRequest(view, url);
			}
		});
		webView.setWebChromeClient(new WebChromeClient(){
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				progressBar.setProgress(newProgress);
				if(newProgress>90){
					progressBar.setVisibility(View.GONE);
				}

				super.onProgressChanged(view, newProgress);
			}
		});
		if(StaticValueClass.isAfterKitKat)
			view.setPadding(0, StaticValueClass.statusBarHeight, 0, 0);
		initFunction();
		freshView();
		return view;
	}

	public void setCurrentCommodity(Commodity commo, WebView tWebView){
		currentCommodity=commo;
		webView=tWebView;
		if (this.isAdded()){
			//fresh view.
			freshView();
		}
	}

	protected void initWebView(){

		WebSettings settings = webView.getSettings();

		// User settings
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			if (0 != (ma.getApplicationInfo().flags &= ApplicationInfo.FLAG_DEBUGGABLE)) {
				WebView.setWebContentsDebuggingEnabled(true);
			}
		}
	//	settings.setBlockNetworkImage(true);
		settings.setDomStorageEnabled(true);
		settings.setAppCacheEnabled(true);
		final String cachePath = ma.getApplicationContext().getDir("cache",ma.MODE_PRIVATE).getPath();
		settings.setAppCachePath(cachePath);
		settings.setAppCacheMaxSize(5*1024*1024);
		settings.setJavaScriptEnabled(true);	//设置webviouew支持javascript
		settings.setLoadsImagesAutomatically(true);	//支持自动加载图片
		settings.setUseWideViewPort(true);	//设置webview推荐使用的窗口，使html界面自适应屏幕
		settings.setLoadWithOverviewMode(true);
		settings.setSaveFormData(true);	//设置webview保存表单数据
		settings.setSavePassword(true);	//设置webview保存密码
		settings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);	//设置中等像素密度，medium=160dpi
		settings.setSupportZoom(true);	//支持缩放

		CookieManager.getInstance().setAcceptCookie(true);

		if (Build.VERSION.SDK_INT > 8) {
			settings.setPluginState(WebSettings.PluginState.ON_DEMAND);
		}

		// Technical settings
		settings.setSupportMultipleWindows(true);
		webView.setLongClickable(true);
		webView.setScrollbarFadingEnabled(true);
		webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		webView.setDrawingCacheEnabled(true);

		settings.setAppCacheEnabled(true);
		settings.setDatabaseEnabled(true);
		settings.setDomStorageEnabled(true);
	}

	protected boolean shouldOverrideUrlByDUOSHOU(WebView view, String url) {
		Uri uri = Uri.parse(url);
		Log.d("shouldOverrideUrl","url:"+url);
		if (currentCommodity.webLink.equals(url)) {
			view.loadUrl(url);
			return true;
		}else return false;
	}

	private void initFunction(){
		ma=(MainActivity)this.getActivity();
		Bitmap backmark= BitmapFactory.decodeResource(this.getActivity().getResources(), R.mipmap.expand_icon);
		Drawable leftDrawable=new BitmapDrawable(StaticValueClass.getBackIcon(backmark));
		leftDrawable.setBounds(0, 0, backmark.getWidth(), backmark.getHeight());
		backBtn.setBackground(leftDrawable);
		progressBar.setMax(100);
		backBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ma.onBackPressed();
			}
		});


		favouriteCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked) {
				//	Log.d("check box","isChecked 1:"+isChecked);
				//	if (!favouriteCheck.isPressed() )  return;
				//	Log.d("check box","isChecked 2:"+isChecked);
					if (!StaticValueClass.currentBuyer.favouriteItems.contains(currentCommodity)){
						StaticValueClass.currentBuyer.favouriteItems.add(currentCommodity);
						Log.d("check box","isChecked:"+isChecked+"add:"+currentCommodity.title);
					}

					favouriteCheck.startAnimation(AnimationUtils.loadAnimation(ma, R.anim.moveup1));
				}else{
					StaticValueClass.currentBuyer.favouriteItems.remove(currentCommodity);
					Log.d("check box","isChecked:"+isChecked+"remove:"+currentCommodity.title);
				//	favouriteCheck.startAnimation(AnimationUtils.loadAnimation(ma, R.anim.slide_top_out));
				}
				currentCommodity.isFavorited=isChecked;
			}
		});
		likeText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				favouriteCheck.performClick();
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		freshView();
	}

	@Override
	public void onPause() {
		super.onPause();
	//	webView.pauseTimers();
	//	webView.loadUrl("");
	//	webViewFrame.removeAllViews();
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
	}

	private void freshView(){
		favouriteCheck.setChecked(currentCommodity.isFavorited);
	//	progressBar.setVisibility(View.VISIBLE);
		if(currentCommodity!=null){
			//check left time.
			currentCommodity.computeLeftTime();
			if(currentCommodity.leftTime.equals("")){
				part2.setVisibility(View.GONE);
				RelativeLayout.LayoutParams params=(RelativeLayout.LayoutParams)part1.getLayoutParams();
				params.removeRule(RelativeLayout.ALIGN_PARENT_TOP);
				params.addRule(RelativeLayout.CENTER_VERTICAL);
				part1.setLayoutParams(params);

			}else {
				part2.setVisibility(View.VISIBLE);
				timeText.setText(currentCommodity.leftTime);
				RelativeLayout.LayoutParams params=(RelativeLayout.LayoutParams)part1.getLayoutParams();
				params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
				params.removeRule(RelativeLayout.CENTER_VERTICAL);
				part1.setLayoutParams(params);
				RelativeLayout.LayoutParams params1=(RelativeLayout.LayoutParams)part2.getLayoutParams();
				params1.addRule(RelativeLayout.ALIGN_LEFT,part1.getId());
				params1.addRule(RelativeLayout.ALIGN_RIGHT,part1.getId());
			}

			//check coupon.
			if (!currentCommodity.couponLink.equals("")){
				part1.setVisibility(View.VISIBLE);
				drawBtn.setVisibility(View.VISIBLE);
				RelativeLayout.LayoutParams params=(RelativeLayout.LayoutParams)drawBtn.getLayoutParams();
				params.width=StaticValueClass.dip2px(ma,80);
				partText13.setVisibility(View.GONE);
				partText11.setText("领券后购买");
				partText12.setText("仅"+currentCommodity.price+"元");
				drawBtn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						ma.loadDrawCouponFragment(currentCommodity.couponLink);
					}
				});
			}else {
				drawBtn.setVisibility(View.INVISIBLE);
				RelativeLayout.LayoutParams params=(RelativeLayout.LayoutParams)drawBtn.getLayoutParams();
				params.width=1;
				// check price differ.
				if(currentCommodity.auction_price>0){
					part1.setVisibility(View.VISIBLE);
					partText11.setText("拍下自动变成");
					partText13.setVisibility(View.VISIBLE);
					partText12.setText(currentCommodity.price+"元");
				}else{
					part1.setVisibility(View.INVISIBLE);
				}
			}

		}
	}


}
