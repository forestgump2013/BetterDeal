package com.way.betterdeal.fragments;


import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrawCouponFragment extends Fragment {

    MainActivity ma;
    View homeView;
    WebView webView;
    String couponLink;
    int first;

    public DrawCouponFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ma=(MainActivity)this.getActivity();
        homeView=inflater.inflate(R.layout.fragment_draw_coupon, container, false);
        webView=(WebView)homeView.findViewById(R.id.webView);
        initWebView();
        webView.loadUrl(couponLink);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //    return super.shouldOverrideUrlLoading(view, url);
               return  myShouldOverrideUrl(view,url);
            }
        });
        inits();
        return homeView;
    }

    boolean myShouldOverrideUrl(WebView view, String url){
        Log.d("myShouldOverrideUrl","first:"+first+" url:"+url);
        first++;
        if (url.equals(couponLink)){
            view.loadUrl(url);
            return true;
        }else if ( url.contains("taobao://uland")  ){
          //  view.loadUrl(url);
            return true;
        }else   {
            view.loadUrl(url);
            return true;
        }

    }

    public void setCouponLink(String webURL){
        couponLink=webURL;
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
        settings.setDomStorageEnabled(true);
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
    private  void inits(){
        first=1;
    }

}
