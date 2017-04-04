package com.way.betterdeal.fragments;


import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.way.betterdeal.CreditActivity;
import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;
import com.way.betterdeal.duibasdk.CreditTool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * A simple {@link Fragment} subclass.
 */
public class DuibaFragment extends Fragment {
    private static String ua;
    private static Stack<CreditActivity> activityStack;
    public static final String VERSION="1.0.8";
    public static CreditActivity.CreditsListener creditsListener;
    public static boolean IS_WAKEUP_LOGIN = false;
    public static String INDEX_URI = "/chome/index";

    public interface CreditsListener{
        /**
         * 当点击分享按钮被点击
         * @param shareUrl 分享的地址
         * @param shareThumbnail 分享的缩略图
         * @param shareTitle 分享的标题
         * @param shareSubtitle 分享的副标题
         */
        public void onShareClick(WebView webView, String shareUrl, String shareThumbnail, String shareTitle, String shareSubtitle);

        /**
         * 当点击登录
         * @param webView 用于登录成功后返回到当前的webview并刷新。
         * @param currentUrl 当前页面的url
         */
        public void onLoginClick(WebView webView, String currentUrl);

        /**
         * 当点复制券码时
         * @param mWebView mWebView对象。
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
    protected String navColor="#0acbc1";
    protected String titleColor="#ffffff";
    protected Long shareColor;

    protected WebView mWebView;
    protected LinearLayout mLinearLayout;
    protected RelativeLayout mNavigationBar;
    protected TextView mTitle;
    protected ImageView mBackView;
 //   protected TextView mShare;

    private int RequestCode=100;

    View parent;
    public DuibaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout_duiba for this fragment
        Log.d("daemonone"," DuibaFragment  onCreateView ");
        parent= inflater.inflate(R.layout.layout_duiba, container, false);
        // 管理匿名类栈，用于模拟原生应用的页面跳转。
        url=getMallUrl();
        if (activityStack == null) {
            activityStack = new Stack<CreditActivity>();
        }
      // activityStack.push(this);

        initWebView();

        //js调java代码接口。
        mWebView.addJavascriptInterface(new Object(){

            //用于跳转用户登录页面事件。
            @JavascriptInterface
            public void login(){
                if(creditsListener!=null){
                    mWebView.post(new Runnable() {
                        @Override
                        public void run() {
                            creditsListener.onLoginClick(mWebView, mWebView.getUrl());
                        }
                    });
                }
            }

            //复制券码
            @JavascriptInterface
            public void copyCode(final String code){
                if(creditsListener!=null){
                    mWebView.post(new Runnable() {
                        @Override
                        public void run() {
                            creditsListener.onCopyCode(mWebView, code);
                        }
                    });
                }
            }

            //客户端本地触发刷新积分。
            @JavascriptInterface
            public void localRefresh(final String credits){
                if(creditsListener!=null){
                    mWebView.post(new Runnable() {
                        @Override
                        public void run() {
                            creditsListener.onLocalRefresh(mWebView, credits);
                        }
                    });
                }
            }

        },"duiba_app");

        if (ua == null) {
            ua = mWebView.getSettings().getUserAgentString() + " Duiba/" + VERSION;
        }
        mWebView.getSettings().setUserAgentString(ua);

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
            //    CreditActivity.this.onReceivedTitle(view, title);
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return shouldOverrideUrlByDuiba(view, url);
            }

        });

        mWebView.loadUrl(url);

        Log.d("daemonone","CreditActivity url"+url);
        return parent;
    }

    //初始化WebView配置
    protected void initWebView(){
        mWebView=(WebView)parent.findViewById(R.id.webView);
       // mWebView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        WebSettings settings = mWebView.getSettings();

        // User settings
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (0 != (getActivity(). getApplicationInfo().flags &= ApplicationInfo.FLAG_DEBUGGABLE)) {
                WebView.setWebContentsDebuggingEnabled(true);
            }
        }
        settings.setJavaScriptEnabled(true);	//设置webview支持javascript
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
        mWebView.setLongClickable(true);
        mWebView.setScrollbarFadingEnabled(true);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebView.setDrawingCacheEnabled(true);

        settings.setAppCacheEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);
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

        //判断非http连接时尝试发出系统级intent（比如tbopen等打开第三方应用）
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            final PackageManager packageManager = this.getActivity().getPackageManager();
            final Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);//Uri.parse("tbopen://m.taobao.com/tbopen/index.html?action=ali.open.nav&module=h5&appkey=23402401&backURL=&TTID=2014_0_23402401@baichuan_h5_0.0.3&bootImage=0&appName=&packageName=&source=bc&tag=&utdid=&scm=&pvid=&uri=https://taoquan.taobao.com/coupon/unify_apply.htm?sellerId=1114408582&activityId=c184815f21714efba5225887e75852cd&h5Url=https%3A%2F%2Ftaoquan.taobao.com%2Fcoupon%2Funify_apply.htm%3FsellerId%3D1114408582%26activityId%3Dc184815f21714efba5225887e75852cd%26params%3D%25257B%252522TTID%252522%25253A%2525222014_0_23402401%252540baichuan_h5_0.0.3%252522%25252C%252522source%252522%25253A%252522bc%252522%25252C%252522ybhpss%252522%25253A%252522dHRpZD0yMDE0XzBfMjM0MDI0MDElNDBiYWljaHVhbl9oNV8wLjAuMw%2525253D%2525253D%252522%25257D&params=%7B%22TTID%22%3A%222014_0_23402401%40baichuan_h5_0.0.3%22%2C%22source%22%3A%22bc%22%2C%22ybhpss%22%3A%22dHRpZD0yMDE0XzBfMjM0MDI0MDElNDBiYWljaHVhbl9oNV8wLjAuMw%253D%253D%22%7D"));
            List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            if(list.size() > 0){//可以处理此intent的情况
                startActivity(intent);
                return true;
            }

            return false;
        }


        // 截获页面分享请求，分享数据
        if ("/client/dbshare".equals(uri.getPath())) {
            String content = uri.getQueryParameter("content");
            if (creditsListener != null && content != null) {
                String[] dd = content.split("\\|");
                if (dd.length == 4) {
             //       setShareInfo(dd[0], dd[1], dd[2], dd[3]);
                 //   mShare.setVisibility(View.VISIBLE);
                //    mShare.setClickable(true);
                }
            }
            return true;
        }
        // 截获页面唤起登录请求。（目前暂时还是用js回调的方式，这里仅作预留。）
        if ("/client/dblogin".equals(uri.getPath())) {
            if (creditsListener != null) {
                mWebView.post(new Runnable() {
                    @Override
                    public void run() {
                        creditsListener.onLoginClick(mWebView, mWebView.getUrl());
                    }
                });
            }
            return true;
        }
        if (url.contains("dbnewopen")) { // 新开页面
            Intent intent = new Intent();
            intent.setClass(this.getActivity(),CreditActivity.class);
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
            getActivity().setResult(RequestCode, intent);
          //  finishActivity(this);
        } else if (url.contains("dbbackrootrefresh")) { // 回到积分商城首页并刷新
            url = url.replace("dbbackrootrefresh", "none");
            if (activityStack.size() == 1) {
         //       finishActivity(this);
            } else {
            //    activityStack.get(0).ifRefresh = true;
          //      finishUpActivity();
            }
        } else if (url.contains("dbbackroot")) { // 回到积分商城首页
            url = url.replace("dbbackroot", "none");
            if (activityStack.size() == 1) {
        //        finishActivity(this);
            } else {
            //    finishUpActivity();
            }
        } else if (url.contains("dbback")) { // 后退
            url = url.replace("dbback", "none");
        //    finishActivity(this);
        } else {
            if (url.endsWith(".apk") || url.contains(".apk?")) { // 支持应用链接下载
                Intent viewIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(viewIntent);
                return true;
            }
            view.loadUrl(url);
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(resultCode==100){
            if(intent.getStringExtra("url")!=null){
                this.url=intent.getStringExtra("url");
                mWebView.loadUrl(this.url);
                ifRefresh = false;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (ifRefresh) {
        //    this.url = getIntent().getStringExtra("url");
            mWebView.loadUrl(this.url);
            ifRefresh = false;
            //如果首页含有登录的入口，返回时需要同时刷新首页的话，
            // 需要把下面判断语句中的 && this.url.indexOf(INDEX_URI) > 0 去掉。
        } else if (IS_WAKEUP_LOGIN && this.url.indexOf(INDEX_URI) > 0) {
            mWebView.reload();
            IS_WAKEUP_LOGIN = false;
        } else {
            // 返回页面时，如果页面含有onDBNewOpenBack()方法,则调用该js方法。
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                mWebView.evaluateJavascript("if(window.onDBNewOpenBack){onDBNewOpenBack()}", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) { }
                });
            } else {
                mWebView.loadUrl("javascript:if(window.onDBNewOpenBack){onDBNewOpenBack()}");
            }
        }

    }

    private String getMallUrl(){
        CreditTool tool=new CreditTool("3eeJQFh8kkAZgUrwjn1heGCAZXQC","2xbXBRU5jqr7Hg29n4LcxoPx4sbj");
        Map<String,String> params=new HashMap<String,String>();
        params.put("uid","tester");
        params.put("credits", "100");
        String redirect="http://www.duiba.com.cn/chome/index";
        if(redirect!=null){
            //redirect是目标页面地址，默认积分商城首页是：http://www.duiba.com.cn/chome/index
            //此处请设置成一个外部传进来的参数，方便运营灵活配置
            params.put("redirect",redirect);
        }
        String url=tool.buildUrlWithSign("http://www.duiba.com.cn/autoLogin/autologin?", params);
        //	Log.d(StaticValueClass.logTag,"duiba url:"+url);
        //	editText3.setText(url);
        //	StaticValueClass.tempStr=url;
        return url;
    }

}
