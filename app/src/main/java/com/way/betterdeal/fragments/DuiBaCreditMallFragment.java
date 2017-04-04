package com.way.betterdeal.fragments;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.way.betterdeal.CreditActivity;
import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;
import com.way.betterdeal.duibasdk.CreditTool;

public class DuiBaCreditMallFragment extends Fragment {

	MainActivity ma;
	View creditMallView;
	WebView webView;
	String webUrl="http://www.baidu.com";
	public ImageView loadingImage;
	AnimationDrawable anim;
	
	private static String ua;
	private static Stack<CreditActivity> activityStack;
	
	
	public static final String VERSION="1.0.7";
    public static CreditsListener creditsListener;
    Handler handler;

    public interface CreditsListener{
        /**
         * 当点击分享按钮被点击
         * @param shareUrl 分享的地址
         * @param shareThumbnail 分享的缩略图
         * @param shareTitle 分享的标题
         * @param shareSubtitle 分享的副标题
         */
        public void onShareClick(WebView webView, String shareUrl,String shareThumbnail, String shareTitle,  String shareSubtitle);

        /**
         * 当点击登录
         * @param webView 用于登录成功后返回到当前的webview并刷新。
         * @param currentUrl 当前页面的url
         */
        public void onLoginClick(WebView webView,String currentUrl);

        /**
         * 当点复制券码时
         * @param webView webview对象。
         * @param code 复制的券码
         */
		public void onCopyCode(WebView mWebView, String code);

		/**
		 * 通知本地，刷新积分
		 * @param mWebView
		 * @param credits
		 */
		public void onLocalRefresh(WebView mWebView, String credits);
    }

    protected String url;
    protected String shareUrl;			//分享的url
    protected String shareThumbnail;	//分享的缩略图
    protected String shareTitle;		//分享的标题
    protected String shareSubtitle;		//分享的副标题
    protected Boolean ifRefresh = false;
    protected Boolean delayRefresh = false;
    protected String navColor="#FE3466";
    protected String titleColor="#ffffff";
    protected Long shareColor;
    
  //  protected WebView mWebView;
    protected LinearLayout mLinearLayout;
    protected RelativeLayout mNavigationBar;
    protected TextView mTitle;
    protected ImageView mBackView;
    protected TextView mShare;

    private int RequestCode=100;
	
	public DuiBaCreditMallFragment(){

		handler=new Handler();
	}
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		ma=(MainActivity)this.getActivity();

		creditMallView=(View)inflater.inflate(R.layout.credit_mall_fragment, container, false);
		mTitle=(TextView)creditMallView.findViewById(R.id.title);
		webView=(WebView)creditMallView.findViewById(R.id.webView);
		this.url=this.getMallUrl();

		loadingImage=(ImageView)creditMallView.findViewById(R.id.loadingImage);
		anim=(AnimationDrawable)loadingImage.getBackground();
	//	initWebView();
		// 管理匿名类栈，用于模拟原生应用的页面跳转。
		if (activityStack == null) {
			activityStack = new Stack<CreditActivity>();
		}
			//	activityStack.push(this);

		mTitle.setTypeface(Typeface.createFromAsset(ma.getAssets(),"fonts/huagang_girl.ttf"));
		webView.addJavascriptInterface(new Object(){
			 //用于跳转用户登录页面事件。
            @JavascriptInterface
            public void login(){
            	if(creditsListener!=null){
            		webView.post(new Runnable() {
						@Override
						public void run() {
							creditsListener.onLoginClick(webView, webView.getUrl());
						}
					});
            	}
            }
            
            //复制券码
            @JavascriptInterface
            public void copyCode(final String code){
            	if(creditsListener!=null){
            		webView.post(new Runnable() {
						@Override
						public void run() {
							creditsListener.onCopyCode(webView, code);
						}
					});
            	}
            }
            
            //客户端本地触发刷新积分。
            @JavascriptInterface
            public void localRefresh(final String credits){
            	if(creditsListener!=null){
            		webView.post(new Runnable() {
						@Override
						public void run() {
							creditsListener.onLocalRefresh(webView, credits);
						}
					});
            	}
            }
        
		}, "duibaApp");
	//	myInitWebView();
		initWebView();
		if (ua == null) {
			ua = webView.getSettings().getUserAgentString() + " Duiba/" + VERSION;
		}
		webView.getSettings().setUserAgentString(ua);

		webView.setWebViewClient(new WebViewClient(){

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				return shouldOverrideUrlByDuiba(view,url);
			//	return super.shouldOverrideUrlLoading(view, url);
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				
				handler.postDelayed(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						anim.stop();
						loadingImage.setVisibility(View.INVISIBLE);
					}
					
				}, 1400);
			}
			
			
		});
		
		webView.setWebChromeClient(new WebChromeClient(){
			
		});
		webView.loadUrl(getMallUrl());
		anim.start();
		
		if(StaticValueClass.isAfterKitKat)
			creditMallView.setPadding(0, StaticValueClass.statusBarHeight, 0, 0);
		return creditMallView;

	}
	private void loadurl(final WebView webView,final String url){
		ma.runOnUiThread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				webView.loadUrl(getMallUrl());
			}
			
		});
	}
    protected void initWebView(){

        WebSettings settings = webView.getSettings();

		// User settings
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			if (0 != (ma.getApplicationInfo().flags &= ApplicationInfo.FLAG_DEBUGGABLE)) {
				WebView.setWebContentsDebuggingEnabled(true);
			}
		}
		settings.setJavaScriptEnabled(true);	//设置webview支持javascript
		settings.setLoadsImagesAutomatically(true);	//支持自动加载图片
		settings.setUseWideViewPort(true);	//设置webview推荐使用的窗口，使html界面自适应屏幕
		settings.setLoadWithOverviewMode(true);
		settings.setSaveFormData(true);	//设置webview保存表单数据
		settings.setSavePassword(true);	//设置webview保存密码
		settings.setDefaultZoom(ZoomDensity.MEDIUM);	//设置中等像素密度，medium=160dpi
		settings.setSupportZoom(true);	//支持缩放

		CookieManager.getInstance().setAcceptCookie(true);

		if (Build.VERSION.SDK_INT > 8) {
			settings.setPluginState(PluginState.ON_DEMAND);
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
         /*
        // User settings
        settings.setJavaScriptEnabled(true);	//设置webview支持javascript
        settings.setLoadsImagesAutomatically(true);	//支持自动加载图片
        settings.setUseWideViewPort(true);	//设置webview推荐使用的窗口，使html界面自适应屏幕
        settings.setLoadWithOverviewMode(true); 
        settings.setSaveFormData(true);	//设置webview保存表单数据
        settings.setSavePassword(true);	//设置webview保存密码
        settings.setDefaultZoom(ZoomDensity.MEDIUM);	//设置中等像素密度，medium=160dpi
        settings.setSupportZoom(true);	//支持缩放

        CookieManager.getInstance().setAcceptCookie(true);

        if (Build.VERSION.SDK_INT > 8) {
            settings.setPluginState(PluginState.ON_DEMAND);
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
        */
    }
    
    /**
	 * 拦截url请求，根据url结尾执行相应的动作。 （重要）
	 * 用途：模仿原生应用体验，管理页面历史栈。
	 * @param view
	 * @param url
	 * @return
	 */
	protected boolean shouldOverrideUrlByDuiba(WebView view, String url) {
		Uri uri = Uri.parse(url);
		if (this.url.equals(url)) {
			view.loadUrl(url);
			return true;
		}
		// 处理电话链接，启动本地通话应用。
		if (url.startsWith("tel:")) {
			Intent intent = new Intent(Intent.ACTION_DIAL, uri);
			startActivity(intent);
			return true;
		}
		if (!url.startsWith("http://") && !url.startsWith("https://")) {
			return false;
		}
		// 截获页面分享请求，分享数据
		if ("/client/dbshare".equals(uri.getPath())) {
			String content = uri.getQueryParameter("content");
			if (creditsListener != null && content != null) {
				String[] dd = content.split("\\|");
				if (dd.length == 4) {
			//		setShareInfo(dd[0], dd[1], dd[2], dd[3]);
					mShare.setVisibility(View.VISIBLE);
					mShare.setClickable(true);
				}
			}
			return true;
		}
		// 截获页面唤起登录请求。（目前暂时还是用js回调的方式，这里仅作预留。）
		if ("/client/dblogin".equals(uri.getPath())) {
			if (creditsListener != null) {
				webView.post(new Runnable() {
					@Override
					public void run() {
						creditsListener.onLoginClick(webView, webView.getUrl());
					}
				});
			}
			return true;
		}
		if (url.contains("dbnewopen")) { // 新开页面
			Intent intent = new Intent();
			intent.setClass(ma,CreditActivity.class);
			intent.putExtra("navColor", navColor);
			intent.putExtra("titleColor", titleColor);
			url = url.replace("dbnewopen", "none");
			intent.putExtra("url", url);
			startActivityForResult(intent, RequestCode);
		} else if (url.contains("dbbackrefresh")) { // 后退并刷新
			url = url.replace("dbbackrefresh", "none");
			Intent intent = new Intent();
			intent.putExtra("url", url);
			intent.putExtra("navColor", navColor);
			intent.putExtra("titleColor", titleColor);
		//	setResult(RequestCode, intent);
		//	finishActivity(this);
		} else if (url.contains("dbbackrootrefresh")) { // 回到积分商城首页并刷新
			url = url.replace("dbbackrootrefresh", "none");
			if (activityStack.size() == 1) {
			//	finishActivity(this);
			} else {
			//	activityStack.get(0).ifRefresh = true;
				finishUpActivity();
			}
		} else if (url.contains("dbbackroot")) { // 回到积分商城首页
			url = url.replace("dbbackroot", "none");
			if (activityStack.size() == 1) {
		//		finishActivity(this);
			} else {
				finishUpActivity();
			}
		} else if (url.contains("dbback")) { // 后退
			url = url.replace("dbback", "none");
	//		finishActivity(this);
		} else {
			if (url.endsWith(".apk") || url.contains(".apk?")) { // 支持应用链接下载
				Intent viewIntent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(viewIntent);
				return true;
			}
			if (url.contains("autologin") && activityStack.size() > 1) { // 未登录用户登录后返回，所有历史页面置为待刷新
				// 将所有已开Activity设置为onResume时刷新页面。
				setAllActivityDelayRefresh();
			}
			view.loadUrl(url);
		}
		return true;
	}
	
	/**
     * 结束除了最底部一个以外的所有Activity
     */
    public void finishUpActivity() {
        int size = activityStack.size();
        for (int i = 0;i < size-1; i++) {
            activityStack.pop().finish();
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }
    
    /**
     * 设置栈内所有Activity为返回待刷新。
     * 作用：未登录用户唤起登录后，将所有栈内的Activity设置为onResume时刷新页面。
     */
    public void setAllActivityDelayRefresh(){
    	int size = activityStack.size();
    	/*
        for (int i = 0;i < size; i++) {
        	if(activityStack.get(i)!=this){
        		activityStack.get(i).delayRefresh = true;
        	}
        } */
    }

    
    
    @Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (ifRefresh) {
		//	this.url = getIntent().getStringExtra("url");
			this.url=this.getMallUrl();
			webView.loadUrl(this.url);
			ifRefresh = false;
		} else if (delayRefresh) {
			webView.reload();
			delayRefresh = false;
		} else {
	    	// 返回页面时，如果页面含有onDBNewOpenBack()方法,则调用该js方法。
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
				webView.evaluateJavascript("if(window.onDBNewOpenBack){onDBNewOpenBack()}", new ValueCallback<String>() {
					@Override
					public void onReceiveValue(String value) { }
				});
			} else {
				webView.loadUrl("javascript:if(window.onDBNewOpenBack){onDBNewOpenBack()}");
			}
		}
	}
	private String getMallUrl(){
 	   CreditTool tool=new CreditTool("3eeJQFh8kkAZgUrwjn1heGCAZXQC","2xbXBRU5jqr7Hg29n4LcxoPx4sbj");
 	   Map<String,String> params=new HashMap<String,String>();
		params.put("uid","tester");
		params.put("credits", "100");
 //	   params.put("uid", StaticValueClass.currentBuyer.tel);
 	//   params.put("credits", ""+StaticValueClass.currentBuyer.bonus);
 	   String redirect="http://www.duiba.com.cn/chome/index";
 	   if(redirect!=null){
 		    //redirect是目标页面地址，默认积分商城首页是：http://www.duiba.com.cn/chome/index
 		    //此处请设置成一个外部传进来的参数，方便运营灵活配置
 		    params.put("redirect",redirect);
 		}
 	   String url=tool.buildUrlWithSign("http://www.duiba.com.cn/autoLogin/autologin?", params);
		Log.d(StaticValueClass.logTag,"duiba url:"+url);
		StaticValueClass.tempStr=url;
		return url;
    }
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, intent);
		 if(resultCode==100){
	            if(intent.getStringExtra("url")!=null){
	                this.url=intent.getStringExtra("url");
	                webView.loadUrl(this.url);
	                ifRefresh = false;
	            }
	        }
	}
    
	
}
