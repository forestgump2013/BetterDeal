package com.way.betterdeal;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;

//import org.apache.http.protocol.HTTP;

import android.app.ActionBar;
import android.app.Activity;

import com.jauker.widget.BadgeView;
import  com.way.betterdeal.CreditActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.jpush.android.api.JPushInterface;

import com.alibaba.sdk.android.AlibabaSDK;
import com.alibaba.sdk.android.callback.CallbackContext;
import com.alibaba.sdk.android.callback.InitResultCallback;
import com.alibaba.sdk.android.trade.ItemService;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
/*
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.TmcMessage;
import com.taobao.api.internal.tmc.Message;
import com.taobao.api.internal.tmc.MessageHandler;
import com.taobao.api.internal.tmc.MessageStatus;
import com.taobao.api.internal.tmc.TmcClient;
import com.taobao.api.request.TmcMessagesConfirmRequest;
import com.taobao.api.request.TmcMessagesConsumeRequest;
import com.taobao.api.response.TmcMessagesConfirmResponse;
import com.taobao.api.response.TmcMessagesConsumeResponse;
*/
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.way.betterdeal.adapters.FragmentTabAdapter;
import com.way.betterdeal.fragments.AboutFragment;
import com.way.betterdeal.fragments.BonusRecordFragment;
import com.way.betterdeal.fragments.Buyer_Edit_fragment;
import com.way.betterdeal.fragments.CategoryFragment;
import com.way.betterdeal.fragments.ChooseAddressFragment;
import com.way.betterdeal.fragments.CoinFragment;
import com.way.betterdeal.fragments.CommodityFragment;
import com.way.betterdeal.fragments.CommodityDetailFragment;
import com.way.betterdeal.fragments.ConfigrationFragment;
import com.way.betterdeal.fragments.CreateAddressFragment;
import com.way.betterdeal.fragments.DrawCouponFragment;
import com.way.betterdeal.fragments.DuiBaCreditMallFragment;
import com.way.betterdeal.fragments.DuibaFragment;
import com.way.betterdeal.fragments.EarnsFragment;
import com.way.betterdeal.fragments.ExchangeListFragment;
import com.way.betterdeal.fragments.FavouriteFragment;
import com.way.betterdeal.fragments.FeedBackFragment;
import com.way.betterdeal.fragments.FootTraceFragment;
import com.way.betterdeal.fragments.GameFragment;
import com.way.betterdeal.fragments.GameWelcomeFragment;
import com.way.betterdeal.fragments.HelpFragment;
import com.way.betterdeal.fragments.InfoEditFragment;
import com.way.betterdeal.fragments.MobileBandFragment;
import com.way.betterdeal.fragments.PersonalCenterFragment;
import com.way.betterdeal.fragments.PrizeFragment;
import com.way.betterdeal.fragments.ProtocolFragment;
import com.way.betterdeal.fragments.RawFragment;
import com.way.betterdeal.fragments.RegisterFragment;
import com.way.betterdeal.fragments.SearchFragment;
import com.way.betterdeal.fragments.ExchangeFragment;
import com.way.betterdeal.fragments.SignFragment;
import com.way.betterdeal.fragments.SuperDiscoutFragment;
import com.way.betterdeal.fragments.TaskFragment;
import com.way.betterdeal.fragments.UserLoginFragment;
import com.way.betterdeal.fragments.WelcomeFragment;
import com.way.betterdeal.fragments.WorldShopingFragment;
import com.way.betterdeal.object.AsynImageLoader;
import com.way.betterdeal.object.BetterDealDB;
import com.way.betterdeal.object.Commodity;
import com.way.betterdeal.object.PicUtil;
import com.way.betterdeal.object.GameBonusRecord;
import com.way.betterdeal.object.ShareController;
//import com.way.betterdeal.view.CustomHorizontalScrollView;
import com.way.betterdeal.view.CustomDialog;
import com.way.betterdeal.view.HomeCenterLayout;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends FragmentActivity implements AMapLocationListener,IUiListener,WeiboAuthListener{
	
	public static int weiXinCallBackRequestCode=210;
	
	WelcomeFragment welcomeFragment;
	private HomeCenterLayout centerLayout;
	private FrameLayout frontPage;
	private SharedPreferences sp;
	private BetterDealDB betterDealDB;
	public  ShareController shareController;
	private MediaPlayer mediaPlayer;
	//------------------------------------
	 private RadioGroup rgs;
	RadioButton tab_rb_a,tab_rb_b,tab_rb_c,tab_rb_d,tab_rb_e;
     Button maskBtn1,maskBtn2,maskBtn3,maskBtn4,maskBtn5;
	BadgeView badgeView1,badgeView3;
     public List<Fragment> fragments = new ArrayList<Fragment>();

     public String hello = "hello ";
     TextView pinkLines[];
     EditText searchText,locationText;
     public  ItemService itemService=null;
     RelativeLayout hintView;
	WebView tWebView;
	 FrameLayout darkMask;
     FragmentManager fragmentManager;
	Stack<Fragment> fragmentStack;
	 Fragment currentFragment,lastFragment;
     public static CommodityFragment commodityFragment;

     SuperDiscoutFragment superDiscoutFragment;
     SignFragment signFragment;
	EarnsFragment earnsFragment;
	WorldShopingFragment worldShopingFragment;
     GameFragment gameFragment;
     PrizeFragment prizeFragment;
     ChooseAddressFragment chooseAddressFragment;
     CreateAddressFragment createAddressFragment;
     BonusRecordFragment bonusRecordFragment;
      Buyer_Edit_fragment buyer_edit_fragment;
	 InfoEditFragment infoEditFragment;
     public PersonalCenterFragment personalCenterFragment;
	HelpFragment helpFragment;
	FeedBackFragment feedBackFragment;
	ProtocolFragment protocolFragment;
	AboutFragment aboutFragment;
     ConfigrationFragment configrationFragment;
     FavouriteFragment favouriteFragment;
     CoinFragment coinFragment;
     ExchangeFragment exchangeFragment;
     ExchangeListFragment exchangeListFragment;
     UserLoginFragment loginFragment;
     CategoryFragment categoryFragment;
     SearchFragment searchFragment;
     FragmentTabAdapter tabAdapter;
     RegisterFragment registerFragment;
     TaskFragment taskFragment;
     CommodityDetailFragment commodityDetailFragment;
	 DrawCouponFragment drawCouponFragment;
     MobileBandFragment mobileBandFragment;
	FootTraceFragment footTraceFragment;
	RawFragment rawFragment;
     
     private int backCount=0,backFlag=0,pinkLinePosition=0,clearFlag=0;
     //------------------------------
     public static final String APP_ID="wxe8ffa219f8bac45b";
     public IWXAPI api;
     Button unLoginBtn;
     boolean game_share=false;
     String resourceURL;
     AuthInfo weiboAuthInfo;
     SsoHandler weiboSsoHandler;
     
     //concerns loaction
     AMapLocationClient mLocationClient;
     //concerns share.


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		//透明状态栏  
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  
        //透明导航栏  
     //  getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		//get global values
		sp=this.getSharedPreferences("Deal", Context.MODE_PRIVATE);
		StaticValueClass.latestUseDate=sp.getString("LatestDate", "2016/01/01");
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		StaticValueClass.initNetConnect();
		StaticValueClass.asynImageLoader =new AsynImageLoader(this,StaticValueClass.latestUseDate);
	//	StaticValueClass.screenWidth = metric.widthPixels;
	//	StaticValueClass.screenHeight = metric.heightPixels;
	//	StaticValueClass.screenDensity=metric.densityDpi;
	//	StaticValueClass.density=metric.density;
	//	StaticValueClass.scaleDensity=metric.scaledDensity;
	//	StaticValueClass.buyer=sp.getString("BUYRE", "none");
		StaticValueClass.hanYiThinFont=Typeface.createFromAsset(getAssets(),"fonts/hanyi_thin_round1.ttf");
		StaticValueClass.huangKangFont=Typeface.createFromAsset(getAssets(),"fonts/huagang_girl.ttf");
        fragmentStack=new Stack<Fragment>();
		currentFragment=null;
		centerLayout = (HomeCenterLayout) findViewById(R.id.homeCenter);
		frontPage=(FrameLayout)findViewById(R.id.frontPage);
		darkMask=(FrameLayout)findViewById(R.id.darkMask);
		hintView=(RelativeLayout)findViewById(R.id.hintView);
		centerLayout.setSubViewIsFling(true);
		centerLayout.setMenuWidth(80*StaticValueClass.screenDensity/240);
		//-------------------------------------------------
		//concerned tabhost.
		commodityFragment=new CommodityFragment();
		categoryFragment=new CategoryFragment();
		personalCenterFragment=new PersonalCenterFragment();
		fragments.add(commodityFragment);
        fragments.add(categoryFragment);
        fragments.add(new GameWelcomeFragment());
       fragments.add(new DuiBaCreditMallFragment());
		fragments.add(personalCenterFragment);
        // check login status
        StaticValueClass.currentBuyer.tel=sp.getString("BUYER", "none");
        Log.d("***Buyer.tel", StaticValueClass.currentBuyer.tel);
        rgs = (RadioGroup) findViewById(R.id.tabs_rg);
        tabAdapter = new FragmentTabAdapter(this, fragments, R.id.tab_content, rgs);
        tabAdapter.setOnRgsExtraCheckedChangedListener(new FragmentTabAdapter.OnRgsExtraCheckedChangedListener(){
            @Override
            public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index) {
                System.out.println("Extra---- " + index + " checked!!! ");
            //    pinkLineFloatMove(index+1);
				if(index!=pinkLinePosition){
					pinkLines[pinkLinePosition].setBackgroundColor(Color.WHITE);
					pinkLines[index].setBackgroundResource(R.color.ds_routine_color);
					pinkLinePosition=index;
				}

            }
        });
	    
       AlibabaSDK.asyncInit(this, new InitResultCallback(){
    	   public void onSuccess(){
    		//   Toast.makeText(MainActivity.this, "init successfully", Toast.LENGTH_LONG).show() ;
    	   }
    	   public void onFailure(int code ,String message){
    		//   Toast.makeText(MainActivity.this, "init failed!"+message, Toast.LENGTH_LONG).show() ;
    		   Log.d("AlibabaSDK.asyncInit", "failed!" +
    		   		"1"+message);
    	   }
    	   
       });
       lazyPerform();

      
	}

	
	private void checkFirstTime(){
		String first=sp.getString("First", "0");
		if(first.equals("0")){
			welcomeFragment=new WelcomeFragment();
		//	load WelcomeFragment.
			if(fragmentManager==null){
				fragmentManager=this.getSupportFragmentManager();
			}
			FragmentTransaction ft=fragmentManager.beginTransaction();
		//	ft.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out);
			ft.replace(R.id.frontPage, welcomeFragment);
		//	ft.addToBackStack(null);
			ft.commit();
			//backFlag++;
			// modify flag.
			Editor editor=sp.edit();
			editor.putString("First", "1");
			editor.commit();
			StaticValueClass.firstUseed=true;
			hintView.setVisibility(View.VISIBLE);
		}else{
			StaticValueClass.firstUseed=false;
			hintView.setVisibility(View.GONE);
		}
	}
	
	public void removeWelcomeFragment(){
		FragmentTransaction ft=fragmentManager.beginTransaction();
		ft.remove(welcomeFragment);
		ft.commit();
	}
	
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		// 用户绘制区域   
        Rect outRect = new Rect();  
     //   this.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(outRect);  
        this.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
        StaticValueClass.screenWidth = outRect.width() ;  
        StaticValueClass.screenHeight = outRect.height();  
		
	}
	
	private void initRadioButtons(){
		 tab_rb_a=(RadioButton)findViewById(R.id.tab_rb_a);
		 tab_rb_b=(RadioButton)findViewById(R.id.tab_rb_b);
		 tab_rb_c=(RadioButton)findViewById(R.id.tab_rb_c);
		 tab_rb_d=(RadioButton)findViewById(R.id.tab_rb_d);
		 tab_rb_e=(RadioButton)findViewById(R.id.tab_rb_e);

		maskBtn1=(Button)findViewById(R.id.maskBtn1);
		maskBtn2=(Button)findViewById(R.id.maskBtn2);
		maskBtn3=(Button)findViewById(R.id.maskBtn3);
		maskBtn4=(Button)findViewById(R.id.maskBtn4);
		maskBtn5=(Button)findViewById(R.id.maskBtn5);
		maskBtn1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				tabAdapter.switchTab(0);
				badgeView1.setVisibility(View.GONE);
			}
		});
		maskBtn2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				tabAdapter.switchTab(1);
			}
		});
		maskBtn3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				tabAdapter.switchTab(2);
				badgeView3.setVisibility(View.GONE);
			}
		});
		maskBtn4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				tabAdapter.switchTab(3);
			}
		});
		maskBtn5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				tabAdapter.switchTab(4);
			}
		});
		int scale1;
		scale1=StaticValueClass.screenWidth/90;
		badgeView1=new BadgeView(this);
		badgeView1.setTargetView(maskBtn1);
		badgeView1.setBackground(10,Color.RED);
		badgeView1.setBadgeGravity(Gravity.TOP|Gravity.RIGHT);
		badgeView1.setBadgeCount(1);
		badgeView1.setBadgeMargin(0,scale1,scale1+2,0);
		badgeView1.setTextColor(Color.RED);
		badgeView1.getLayoutParams().height=badgeView1.getLayoutParams().width=scale1*2;
		//.
		badgeView3=new BadgeView(this);
		badgeView3.setTargetView(maskBtn3);
		badgeView3.setBackground(12,Color.RED);
		badgeView3.setBadgeGravity(Gravity.RIGHT|Gravity.TOP);
		badgeView3.setClickable(true);
		badgeView3.setTextColor(Color.RED);
		badgeView3.setBadgeCount(2);
		badgeView3.setBadgeMargin(0,scale1,scale1+2,0);
		badgeView3.getLayoutParams().height=badgeView3.getLayoutParams().width=scale1*2;
		//.
		if (StaticValueClass.currentBuyer.isLogined() && (StaticValueClass.currentBuyer.checkNinePanePermission() || StaticValueClass.currentBuyer.checkGamePermission()==1 ) ){
			badgeView3.setVisibility(View.VISIBLE);
		}else  badgeView3.setVisibility(View.GONE);

	}
	
	private void initRadiaButton(final RadioButton radioButton,final int rsID){
	//	int l1,width,height;
		radioButton.setTypeface(Typeface.createFromAsset(MainActivity.this.getAssets(),"fonts/hanyi_thin_round1.ttf"));


	}
	
	private void initLocation(){
		//设置定位回调监听
		mLocationClient=new AMapLocationClient(this);
		mLocationClient.setLocationListener(this);
		AMapLocationClientOption mLocationOption=new AMapLocationClientOption();
		mLocationOption.setLocationMode(AMapLocationMode.Battery_Saving);
		mLocationOption.setOnceLocation(true);
		mLocationClient.setLocationOption(mLocationOption);
		mLocationClient.startLocation();
	}
	
	private void initShares(){
	}
	
	public  void reLocation(EditText edit){
		Log.d("****ma", "relocation");
		locationText=edit;
		mLocationClient.startLocation();
	}

	private void lazyPerform(){
		
		this.runOnUiThread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				// main page
				FrameLayout.LayoutParams params=(FrameLayout.LayoutParams)rgs.getLayoutParams();
				initConcernedStatusBar();

				hintView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						hintView.setVisibility(View.GONE);
					}
				});
				//init Fragments
				initFragments();
				initWebView();
				checkFirstTime();
				//concerned right window.
				betterDealDB=new BetterDealDB(MainActivity.this);

				if(StaticValueClass.currentBuyer.isLogined()){
					loadLoginData();
					loadLocalData();
				}
				shareController=new ShareController(MainActivity.this);

				StaticValueClass.ma=MainActivity.this;
				StaticValueClass.networkState=checkNetState();
				// versoin info.
				try{
					PackageManager manager=MainActivity.this.getPackageManager();
					PackageInfo info=manager.getPackageInfo(MainActivity.this.getPackageName(),0);
					StaticValueClass.currentVersion=info.versionName;
				}catch (Exception e){
					e.printStackTrace();
				}

				initMediaPlayer();
				initLocation();
				initShares();
				initDuiBaListener();
			}
			
		});
		initTabSlidingLine();
		initRadioButtons();
	}

	private void initFragments(){
		superDiscoutFragment=new SuperDiscoutFragment();
		signFragment=new SignFragment();
		earnsFragment=new EarnsFragment();
		worldShopingFragment=new WorldShopingFragment();
		commodityDetailFragment= new CommodityDetailFragment();
		drawCouponFragment=new DrawCouponFragment();
		buyer_edit_fragment=new Buyer_Edit_fragment();
		footTraceFragment=new FootTraceFragment();
		helpFragment=new HelpFragment();
		feedBackFragment=new FeedBackFragment();
		protocolFragment =new ProtocolFragment();
		aboutFragment= new AboutFragment();
		rawFragment=new RawFragment();
		if(fragmentManager==null){
			fragmentManager=this.getSupportFragmentManager();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (CallbackContext.loginCallback!=null)
			CallbackContext.onActivityResult(requestCode, resultCode, data);
		shareController.mTencent.onActivityResultData(requestCode, resultCode, data, this);
		if (weiboSsoHandler != null) {
			weiboSsoHandler.authorizeCallBack(requestCode, resultCode, data);
		    }
	
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
       Log.d(StaticValueClass.logTag,"onbackpressed:"+backFlag);
		if(gameFragment!=null  && gameFragment.gameIsRunning()) return;
		if (currentFragment!=null){
			FragmentTransaction ft=fragmentManager.beginTransaction();
			if(currentFragment.equals(commodityDetailFragment))
				tWebView.loadUrl("");
		//		ft.remove(currentFragment);
		     ft.hide(currentFragment);
			if (fragmentStack.size()>0){
				currentFragment=fragmentStack.lastElement();
				ft.show(currentFragment);
				fragmentStack.pop();
			}else currentFragment=null;
			ft.commit();

			if(fragmentStack.size()==0){
				tabAdapter.getCurrentFragment().onResume();
			}
			backFlag--;
			return;
		}
		/*
		if(backFlag>0){
			backFlag--;
			super.onBackPressed();
			return;
		}  */
		//check the current tab.
		if(tabAdapter.switchTab(0))
			return;
		
		if(backCount==0){
			backCount++;
			Toast.makeText(this, "再按一次退出剁手联盟。", Toast.LENGTH_SHORT).show();
			doneBeforeLeave();
				new Thread(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						backCount=0;
					}
					
				}).start();
				
		}else if(backCount==1){
			leaveApplication();
			this.finish();
			android.os.Process.killProcess(android.os.Process.myPid());
			StaticValueClass.firstActiviy.finish();
		//	super.onBackPressed();
		}
	}
	public  void switchTabFragment(int pos){
		tabAdapter.switchTab(pos);
	}

	public void showShareDialog(String title){
		if(title.compareTo("")!=0)
			shareController.setTitle(title);
		shareController.show();
	}
	
	public void weixinGameShareOperation(String text,int flag){
		game_share=true;
		shareController.setShareInfo("不以发福利为目地的小游戏都是耍流氓~","福利天天有，今天特别多，你领了吗？",StaticValueClass.serverAddress+"BetterDeal/images/game_share.jpg",StaticValueClass.serverAddress+"BetterDeal/share_page.php?sharer="+"13581675438");
		shareController.weixinShareInfo(flag);
	//	shareController.weixinShare(text, flag);
	}
	
	
	public void tencentShare(String str){
		Bundle params = new Bundle();
		/*
		//这条分享消息被好友点击后的跳转URL。
		bundle.putString(Constants.PARAM_TARGET_URL, "http://connect.qq.com/");
		//分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_	SUMMARY不能全为空，最少必须有一个是有值的。
		bundle.putString(Constants.PARAM_TITLE, "我在测试");
		//分享的图片URL
		bundle.putString(Constants.PARAM_IMAGE_URL, 
		"http://img3.cache.netease.com/photo/0005/2013-03-07/8PBKS8G400BV0005.jpg");
		//分享的消息摘要，最长50个字
		bundle.putString(Constants.PARAM_SUMMARY, "测试");
		//手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
		bundle.putString(Constants.PARAM_APPNAME, "??我在测试");
		//标识该消息的来源应用，值为应用名称+AppId。
		bundle.putString(Constants.PARAM_APP_SOURCE, "星期几" +);
		
		params.putString(Constants.PARAM_TITLE,
				"AndroidSdk_1_3:UiStory title");
		params.putString(Constants.PARAM_COMMENT,
				"AndroidSdk_1_3: UiStory comment");
		params.putString(Constants.PARAM_IMAGE,
				"http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
		params.putString(Constants.PARAM_SUMMARY,
				"AndroidSdk_1_3: UiStory summary");
		params.putString(
				Constants.PARAM_PLAY_URL,
				"http://player.youku.com/player.php/Type/Folder/"
						+ "Fid/15442464/Ob/1/Pt/0/sid/XMzA0NDM2NTUy/v.swf");
		mTencent.story(MainActivity.this, params, new BaseUiListener());
		
    params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
    params.putString(QQShare.SHARE_TO_QQ_TITLE, "要分享的标题");
    params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  "要分享的摘要");
    params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  "http://www.qq.com/news/1.html");
    params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,"http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
    params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  "测试应用222222");
    params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,  "其他附加功能");		
    mTencent.shareToQQ(MainActivity.this, params, new BaseUiListener());
    
       //qq zone
    * //分享类型
 　　params.putString(QzoneShare.SHARE_TO_QQ_KEY_TYPE,SHARE_TO_QZONE_TYPE_IMAGE_TEXT );
    params.putString(QzoneShare.SHARE_TO_QQ_TITLE, "标题");//必填
    params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "摘要");//选填
    params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, "跳转URL");//必填
    params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, "图片链接ArrayList");
    mTencent.shareToQzone(activity, params, new BaseUiListener());
		*/
		 int shareType = QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT;
	   params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE,shareType );
	   
		params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,  QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
		params.putString(QQShare.SHARE_TO_QQ_TITLE, "剁手联盟");
		params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  "剁手联盟 测试");
		params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  StaticValueClass.serverAddress+"BetterDeal/share_page.php?sharer="+"13581675438");
		ArrayList<String> imageUrls=new ArrayList<String>();
	    imageUrls.add(StaticValueClass.serverAddress+"BetterDeal/app_icon.png");
	    params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
		shareController.mTencent.shareToQzone(this, params , this);
	}
	
	public void weiboAuth(){
       weiboAuthInfo=new AuthInfo(this,StaticValueClass.weiboAppKey,StaticValueClass.weiboREDIRECT_URL,StaticValueClass.weiboSCOPE);
		weiboSsoHandler=new SsoHandler(this,weiboAuthInfo);
       weiboSsoHandler.authorize(this);
	}
	
	private void initTabSlidingLine(){

		pinkLines=new TextView[5];
		pinkLines[0]=(TextView)findViewById(R.id.pinkLine1);
		pinkLines[1]=(TextView)findViewById(R.id.pinkLine2);
		pinkLines[2]=(TextView)findViewById(R.id.pinkLine3);
		pinkLines[3]=(TextView)findViewById(R.id.pinkLine4);
		pinkLines[4]=(TextView)findViewById(R.id.pinkLine5);

		pinkLines[0].setBackgroundColor(Color.rgb(240, 44, 92));
	
				
	}
	
	 @Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	//	this.initHorizonalScrollView();
		 
	}

	
	private void initJPush(){
		JPushInterface.init(getApplicationContext());
	}
	
	private void initAliBaiChuanCloudChannel(Context context){
	//	CloudPushService cloudPushService=AlibabaSDK.getService(CloudPushService.class);
		/*
		if(cloudPushService!=null){
			cloudPushService.register(context, new RunnableCallbackAdapter<Void>(){
				@Override
                public void onSuccess(Void result) {
                    Log.d("initAliBaiChuanCloudChannel", "init cloudchannel success");
                }


                @Override
                public void onFailed(Exception exception){
                    Log.d("initAliBaiChuanCloudChannel", "init cloudchannel failed");
                }
			});
		} */
		
	}

	private  void initWebView(){
		tWebView=new WebView(MainActivity.this);
		/*
		tWebView.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				super.shouldOverrideUrlLoading(view, url);
				return true;
			}
		});  */
		WebSettings settings = tWebView.getSettings();

		// User settings
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			if (0 != (getApplicationInfo().flags &= ApplicationInfo.FLAG_DEBUGGABLE)) {
				WebView.setWebContentsDebuggingEnabled(true);
			}
		}
		//	settings.setBlockNetworkImage(true);
		settings.setDomStorageEnabled(true);
		settings.setAppCacheEnabled(true);
		final String cachePath = getApplicationContext().getDir("cache",MODE_PRIVATE).getPath();
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
		tWebView.setLongClickable(true);
		tWebView.setScrollbarFadingEnabled(true);
		tWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		tWebView.setDrawingCacheEnabled(true);

		settings.setAppCacheEnabled(true);
		settings.setDatabaseEnabled(true);
		settings.setDomStorageEnabled(true);
		//-------------


	}
	
	
	public void replaceFragment(int idx,Fragment fragment){
		
		tabAdapter.replacefragment(idx, fragment);
	}
	
	public void loadLoginData(){
		if(loginFragment==null){
			loginFragment=new UserLoginFragment( );
		}
		loginFragment.loginIn(this,true);
	}

	private void loadLocalData(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				// from local database.
				betterDealDB.getCommodityData(StaticValueClass.currentBuyer.favouriteItems,1);
				betterDealDB.getCommodityData(StaticValueClass.currentBuyer.tracingItems,2);
			}
		}).start();

	}

	private void leaveApplication(){
		Editor editor=sp.edit();
		editor.putString("LatestDate", StaticValueClass.dateFormat.format(new Date()));
		editor.commit();
	}
	
	public void loginWithTel(String tel){
		StaticValueClass.currentBuyer.tel=tel;
		Editor editor=sp.edit();
		editor.putString("BUYER", tel);
		editor.putString("LOGIN_DATE", StaticValueClass.dateFormat.format(new Date()));
		editor.commit(); 
	//	this.betterDealDB.buyerLoginIn(StaticValueClass.currentBuyer);
	//	this.personalCenterFragment.refreashLoginStatus();
		
	}
	
	
	public void unLogin(){
	//	StaticValueClass.logined=false;
		StaticValueClass.currentBuyer.loginOut();
		Editor editor=sp.edit();
		editor.putString("BUYER", "none");
		editor.commit();
		//
		//this.personalCenterFragment.loginOut();
		this.betterDealDB.buyerLoginOut();
	}
	
	public FragmentManager getMyFragmentManager(){
		if(fragmentManager==null){
			fragmentManager=this.getSupportFragmentManager();
		}
		return fragmentManager;
	}
	
	public void loadSuperDiscountFragment(){

		if(fragmentManager==null){
			fragmentManager=this.getSupportFragmentManager();
		}
		FragmentTransaction ft=fragmentManager.beginTransaction();
	//	ft.replace(R.id.frontPage, superDiscoutFragment);
	//	ft.addToBackStack(null);
		if(currentFragment!=null) {
			fragmentStack.push(currentFragment);
			ft.hide(currentFragment);
		}
		currentFragment =superDiscoutFragment;
		if (!superDiscoutFragment.isAdded())
			ft.add(R.id.frontPage, superDiscoutFragment,"superDiscoutFragment");
		ft.show(currentFragment);

		ft.commit();
		backFlag++;
	}

	public void loadEarnsFragment(){

		if(fragmentManager==null){
			fragmentManager=this.getSupportFragmentManager();
		}
		FragmentTransaction ft=fragmentManager.beginTransaction();
	//	ft.replace(R.id.frontPage, earnsFragment);
	//	ft.addToBackStack(null);
		if(currentFragment!=null) {
			fragmentStack.push(currentFragment);
			ft.hide(currentFragment);
		}
		currentFragment =earnsFragment;
		if (!earnsFragment.isAdded())
			ft.add(R.id.frontPage, earnsFragment,"earnsFragment");
		ft.show(currentFragment);

		ft.commit();
		backFlag++;
	}

	public void loadWorldShppingFragment(){

		if(fragmentManager==null){
			fragmentManager=this.getSupportFragmentManager();
		}
		FragmentTransaction ft=fragmentManager.beginTransaction();
	//	ft.replace(R.id.frontPage, worldShopingFragment);
	//	ft.addToBackStack(null);

		if(currentFragment!=null) {
			fragmentStack.push(currentFragment);
			ft.hide(currentFragment);
		}
		currentFragment =worldShopingFragment;
		if (!worldShopingFragment.isAdded())
			ft.add(R.id.frontPage, worldShopingFragment,"worldShopingFragment");
		ft.show(currentFragment);

		ft.commit();
		backFlag++;
	}
	
	public void loadSignFragment(){
		
		if(fragmentManager==null){
			fragmentManager=this.getSupportFragmentManager();
		}
		FragmentTransaction ft=fragmentManager.beginTransaction();
	//	ft.replace(R.id.frontPage, signFragment);
	//	ft.addToBackStack(null);

		if(currentFragment!=null) {
			fragmentStack.push(currentFragment);
			ft.hide(currentFragment);
		}
		currentFragment =signFragment;
		if (!currentFragment.isAdded())
			ft.add(R.id.frontPage, signFragment,"signFragment");
		ft.show(currentFragment);

		ft.commit();
		backFlag++;
	}
	
	//Buyer_Edit_fragment
	public void loadBuyer_Edit_fragment(){

		if(fragmentManager==null){
			fragmentManager=this.getSupportFragmentManager();
		}
		FragmentTransaction ft=fragmentManager.beginTransaction();
	//	ft.replace(R.id.frontPage, buyer_edit_fragment);
	//	ft.addToBackStack(null);
		if(currentFragment!=null) {
			fragmentStack.push(currentFragment);
			ft.hide(currentFragment);
		}
		currentFragment =buyer_edit_fragment;
		if (!buyer_edit_fragment.isAdded())
			ft.add(R.id.frontPage, buyer_edit_fragment,"buyer_edit_fragment");
		ft.show(currentFragment);

		ft.commit();
		backFlag++;
	}

	public void loadInfoEditFragment(int flag){
		if(infoEditFragment==null){
			infoEditFragment=new InfoEditFragment();
			infoEditFragment.setEditListener(buyer_edit_fragment);
		} ;
		infoEditFragment.setDirect(flag);
		FragmentTransaction ft=getSupportFragmentManager().beginTransaction();

	//	ft.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out);
	//	ft.replace(R.id.frontPage, infoEditFragment);
	//	ft.addToBackStack(null);
		if(currentFragment!=null) {
			fragmentStack.push(currentFragment);
			ft.hide(currentFragment);
		}
		currentFragment =infoEditFragment;
		if (!infoEditFragment.isAdded())
			ft.add(R.id.frontPage, infoEditFragment,"infoEditFragment");
		ft.show(currentFragment);

		ft.commit();
		backFlag++;
	}
	/*
	public void loadLoginFragment(FragmentManager mFragmentManager, boolean flag){
		if(loginFragment==null){
			loginFragment=new UserLoginFragment();
		}
		loginFragment.setSpecialPath(flag);
		if(fragmentManager==null){
			fragmentManager=this.getSupportFragmentManager();
		}
		FragmentTransaction ft=mFragmentManager.beginTransaction();
		if(currentFragment!=null)
				ft.remove(currentFragment);
		ft.replace(R.id.frontPage, loginFragment);
		ft.addToBackStack("loginFragment");


		ft.commit();
		backFlag++;
	} */

	public void loadLoginFragment( boolean flag){
		if(loginFragment==null){
			loginFragment=new UserLoginFragment();
		}
		loginFragment.setSpecialPath(flag);
		if(fragmentManager==null){
			fragmentManager=this.getSupportFragmentManager();
		}
		FragmentTransaction ft=fragmentManager.beginTransaction();

	//	ft.replace(R.id.frontPage, loginFragment);
	//	ft.addToBackStack("loginFragment");
		if(currentFragment!=null) {
			fragmentStack.push(currentFragment);
			ft.hide(currentFragment);
		}
		currentFragment =loginFragment;
		if (!loginFragment.isAdded())
			ft.add(R.id.frontPage, loginFragment,"loginFragment");
		ft.show(currentFragment);
		ft.commit();
		backFlag++;
	}
	
	public void loadMobileBandFragment(){
		if(mobileBandFragment==null){
			mobileBandFragment=new MobileBandFragment();
		}
		FragmentTransaction ft=fragmentManager.beginTransaction();
	//	ft.replace(R.id.frontPage, mobileBandFragment);
	//	ft.addToBackStack(null);

		if(currentFragment!=null) {
			fragmentStack.push(currentFragment);
			ft.hide(currentFragment);
		}
		currentFragment =mobileBandFragment;
		if (!mobileBandFragment.isAdded())
			ft.add(R.id.frontPage, mobileBandFragment,"mobileBandFragment");
		ft.show(currentFragment);

		ft.commit();
		backFlag++;
	}
	/*
	public void loadExchangeFragment(){
		if(exchangeFragment==null){
			exchangeFragment=new ExchangeFragment();
		}
		if(fragmentManager==null){
			fragmentManager=this.getSupportFragmentManager();
		}
		FragmentTransaction ft=fragmentManager.beginTransaction();
		ft.replace(R.id.frontPage, exchangeFragment);
		ft.addToBackStack(null);
		ft.commit();
		backFlag++;
		
	}
	
	public void loadExchangeListFragment(){
		if(exchangeListFragment==null){
			exchangeListFragment=new ExchangeListFragment();
		}
		if(fragmentManager==null){
			fragmentManager=this.getSupportFragmentManager();
		}
		FragmentTransaction ft=fragmentManager.beginTransaction();
		ft.replace(R.id.frontPage, exchangeListFragment);
		ft.addToBackStack(null);
		ft.commit();
		backFlag++;
		
	}  */
	
	public void loadChooseAddressFragment(GameBonusRecord record){
		if(chooseAddressFragment==null){
			chooseAddressFragment=new ChooseAddressFragment();
		}else chooseAddressFragment.setConcernsBonusRecord(record);
		if(fragmentManager==null){
			fragmentManager=this.getSupportFragmentManager();
		}
		FragmentTransaction ft=fragmentManager.beginTransaction();
	//	ft.replace(R.id.frontPage, chooseAddressFragment);
	//	ft.addToBackStack(null);

		if(currentFragment!=null) {
			fragmentStack.push(currentFragment);
			ft.hide(currentFragment);
		}
		currentFragment =chooseAddressFragment;
		if (!chooseAddressFragment.isAdded())
			ft.add(R.id.frontPage, chooseAddressFragment,"chooseAddressFragment");
		ft.show(currentFragment);
		ft.commit();
		backFlag++;
	}
	//createAddressFragment
	public void loadcreateAddressFragment(GameBonusRecord record){
		if(createAddressFragment==null){
			createAddressFragment=new CreateAddressFragment();
		};
		createAddressFragment.setGameBonusRecord(record);
		if(fragmentManager==null){
			fragmentManager=this.getSupportFragmentManager();
		}
		FragmentTransaction ft=fragmentManager.beginTransaction();
	//	ft.replace(R.id.frontPage, createAddressFragment);
	//	ft.addToBackStack(null);
		if(currentFragment!=null) {
			fragmentStack.push(currentFragment);
			ft.hide(currentFragment);
		}
		currentFragment =createAddressFragment;
		if (!createAddressFragment.isAdded())
			ft.add(R.id.frontPage, createAddressFragment,"createAddressFragment");
		ft.show(currentFragment);

		ft.commit();
		backFlag++;
	}
	
	public void loadPrizeFragment(GameBonusRecord record){
		if(prizeFragment==null){
			prizeFragment=new PrizeFragment();
		}
		prizeFragment.setGameBonusRecord(record);
		if(fragmentManager==null){
			fragmentManager=this.getSupportFragmentManager();
		}
		FragmentTransaction ft=fragmentManager.beginTransaction();
	 //	ft.replace(R.id.frontPage, prizeFragment);
	//	ft.addToBackStack(null);
		if(currentFragment!=null) {
			fragmentStack.push(currentFragment);
			ft.hide(currentFragment);
		}
		currentFragment =prizeFragment;
		if (!prizeFragment.isAdded())
			ft.add(R.id.frontPage, prizeFragment,"prizeFragment");
		ft.show(currentFragment);

		ft.commit();
		backFlag++;
	}
	
	public void loadGameFragment(int flag){
		if(gameFragment==null){
			gameFragment=new GameFragment();
		} ;
		if(fragmentManager==null){
			fragmentManager=this.getSupportFragmentManager();
		}
		gameFragment.setGameFlag(flag);
		FragmentTransaction ft=fragmentManager.beginTransaction();
		if(flag==0)
			ft.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out);
		else ft.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_right_out);

	//	ft.replace(R.id.frontPage, gameFragment);
	//	ft.addToBackStack(null);
		if(currentFragment!=null) {
			fragmentStack.push(currentFragment);
			ft.hide(currentFragment);
		}
		currentFragment =gameFragment;
		if (!gameFragment.isAdded())
			ft.add(R.id.frontPage, gameFragment,"gameFragment");
		ft.show(currentFragment);

		ft.commit();
		backFlag++;
	}
	
	public void clearSubViews(){
		//.
		FragmentTransaction ft=fragmentManager.beginTransaction();
		ft.hide(currentFragment);
		ft.commit();
		//
		currentFragment=null;
		fragmentStack.clear();
	//	backFlag=0;
	}

	
	public void loadCoinFragment(){
		if(coinFragment==null){
			coinFragment=new CoinFragment();
		}
		if(fragmentManager==null){
			fragmentManager=this.getSupportFragmentManager();
		}
		FragmentTransaction ft=fragmentManager.beginTransaction();
	//	ft.replace(R.id.frontPage, coinFragment);
	//	ft.addToBackStack(null);
		if(currentFragment!=null) {
			fragmentStack.push(currentFragment);
			ft.hide(currentFragment);
		}
		currentFragment =coinFragment;
		if (!coinFragment.isAdded())
			ft.add(R.id.frontPage, coinFragment,"coinFragment");
		ft.show(currentFragment);
		ft.commit();
		backFlag++;
	}
	//FavouriteFragment
	public void loadFavouriteFragment(int direct){
		if(favouriteFragment==null){
			favouriteFragment=new FavouriteFragment();
		}
		if(fragmentManager==null){
			fragmentManager=this.getSupportFragmentManager();
		}
		favouriteFragment.setDirect(direct);
		FragmentTransaction ft=fragmentManager.beginTransaction();
	//	ft.replace(R.id.frontPage, favouriteFragment);
	//	ft.addToBackStack(null);

		if(currentFragment!=null) {
			fragmentStack.push(currentFragment);
			ft.hide(currentFragment);
		}
		currentFragment =favouriteFragment;
		if (!favouriteFragment.isAdded())
			ft.add(R.id.frontPage, favouriteFragment,"favouriteFragment");
		ft.show(currentFragment);

		ft.commit();
		backFlag++;
	}

	public void loadFootTraceFragment(){

		if(fragmentManager==null){
			fragmentManager=this.getSupportFragmentManager();
		}
		FragmentTransaction ft=fragmentManager.beginTransaction();
	//	ft.replace(R.id.frontPage, footTraceFragment);
	//	ft.addToBackStack(null);

		if(currentFragment!=null) {
			fragmentStack.push(currentFragment);
			ft.hide(currentFragment);
		}
		currentFragment =footTraceFragment;
		if (!footTraceFragment.isAdded())
			ft.add(R.id.frontPage, footTraceFragment,"footTraceFragment");
		ft.show(currentFragment);

		ft.commit();
		backFlag++;
	}

	public void loadConfigrationFragment(){
		if(configrationFragment==null){
			configrationFragment=new ConfigrationFragment();
		}
		if(fragmentManager==null){
			fragmentManager=this.getSupportFragmentManager();
		}
		FragmentTransaction ft=fragmentManager.beginTransaction();
	//	ft.replace(R.id.frontPage, configrationFragment);
	//	ft.addToBackStack(null);
		if(currentFragment!=null) {
			fragmentStack.push(currentFragment);
			ft.hide(currentFragment);
		}
		currentFragment =configrationFragment;
		if (!configrationFragment.isAdded())
			ft.add(R.id.frontPage, configrationFragment,"configrationFragment");
		ft.show(currentFragment);

		ft.commit();
		backFlag++;
	}

	public void loadHelpFragment(){
		if(helpFragment==null){
			helpFragment=new HelpFragment();
		}
		if(fragmentManager==null){
			fragmentManager=this.getSupportFragmentManager();
		}
		FragmentTransaction ft=fragmentManager.beginTransaction();
	//	ft.replace(R.id.frontPage, helpFragment);
	//	ft.addToBackStack(null);
		if(currentFragment!=null) {
			fragmentStack.push(currentFragment);
			ft.hide(currentFragment);
		}
		currentFragment =helpFragment;
		if (!helpFragment.isAdded())
			ft.add(R.id.frontPage, helpFragment,"helpFragment");
		ft.show(currentFragment);
		ft.commit();
		backFlag++;
	}

	public void loadFeedBackFragment(){

		if(fragmentManager==null){
			fragmentManager=this.getSupportFragmentManager();
		}
		FragmentTransaction ft=fragmentManager.beginTransaction();
	//	ft.replace(R.id.frontPage, feedBackFragment);
	//	ft.addToBackStack(null);
		if(currentFragment!=null) {
			fragmentStack.push(currentFragment);
			ft.hide(currentFragment);
		}
		currentFragment =feedBackFragment;
		if (!feedBackFragment.isAdded())
			ft.add(R.id.frontPage, feedBackFragment,"feedBackFragment");
		ft.show(currentFragment);

		ft.commit();
		backFlag++;
	}

	public void loadAboutFragment(){

		if(fragmentManager==null){
			fragmentManager=this.getSupportFragmentManager();
		}
		FragmentTransaction ft=fragmentManager.beginTransaction();
	//	ft.replace(R.id.frontPage, aboutFragment);
	//	ft.addToBackStack(null);

		if(currentFragment!=null) {
			fragmentStack.push(currentFragment);
			ft.hide(currentFragment);
		}
		currentFragment =aboutFragment;
		if (!aboutFragment.isAdded())
			ft.add(R.id.frontPage, aboutFragment,"aboutFragment");
		ft.show(currentFragment);

		ft.commit();
		backFlag++;
	}

	public void loadRawFragment(){

		if(fragmentManager==null){
			fragmentManager=this.getSupportFragmentManager();
		}
		FragmentTransaction ft=fragmentManager.beginTransaction();
		ft.replace(R.id.frontPage, rawFragment);
		ft.addToBackStack(null);
		ft.commit();
		backFlag++;
	}



	public void loadBonusRecordFragment(int direct){
		if(bonusRecordFragment==null){
			bonusRecordFragment=new BonusRecordFragment();
		}
		
		bonusRecordFragment.setDirect(direct);
		if(fragmentManager==null){
			fragmentManager=this.getSupportFragmentManager();
		}
		FragmentTransaction ft=fragmentManager.beginTransaction();
	//	ft.replace(R.id.frontPage, bonusRecordFragment);
	//	ft.addToBackStack(null);
		if(currentFragment!=null) {
			fragmentStack.push(currentFragment);
			ft.hide(currentFragment);
		}
		currentFragment =bonusRecordFragment;
		if (!bonusRecordFragment.isAdded())
			ft.add(R.id.frontPage, bonusRecordFragment,"bonusRecordFragment");
		ft.show(currentFragment);

		ft.commit();
		backFlag++;
	}
	
	public void loadRegisterFragment(int d,boolean flag){
		if(registerFragment==null){
			registerFragment=new RegisterFragment();
		}
		registerFragment.setDirect(d);
		registerFragment.setSpecialPath(flag);
		if(fragmentManager==null){
			fragmentManager=this.getSupportFragmentManager();
		}
		FragmentTransaction ft=fragmentManager.beginTransaction();
	//	ft.replace(R.id.frontPage, registerFragment);
	//	ft.addToBackStack(null);
		if(currentFragment!=null) {
			fragmentStack.push(currentFragment);
			ft.hide(currentFragment);
		}
		currentFragment =registerFragment;
		if (!registerFragment.isAdded())
			ft.add(R.id.frontPage, registerFragment,"registerFragment");
		ft.show(currentFragment);


		ft.commit();
		backFlag++;
		
	}

	public void loadProtocolFragment(){

		if(fragmentManager==null){
			fragmentManager=this.getSupportFragmentManager();
		}
		FragmentTransaction ft=fragmentManager.beginTransaction();
	//	ft.replace(R.id.frontPage, protocolFragment);
	//	ft.addToBackStack(null);
		if(currentFragment!=null) {
			fragmentStack.push(currentFragment);
			ft.hide(currentFragment);
		}
		currentFragment =protocolFragment;
		if (!protocolFragment.isAdded())
			ft.add(R.id.frontPage, protocolFragment,"protocolFragment");
		ft.show(currentFragment);
		ft.commit();
		backFlag++;

	}


	
	
	public void loadSearchFragment(){
		if(searchFragment==null){
			searchFragment=new SearchFragment();
		}
		if(fragmentManager==null){
			fragmentManager=this.getSupportFragmentManager();
		}
		FragmentTransaction ft=fragmentManager.beginTransaction();
	//	ft.replace(R.id.frontPage, searchFragment);
	//	ft.addToBackStack(null);
		if(currentFragment!=null) {
			fragmentStack.push(currentFragment);
			ft.hide(currentFragment);
		}
		currentFragment =searchFragment;
		if (!searchFragment.isAdded())
			ft.add(R.id.frontPage, searchFragment,"searchFragment");
		ft.show(currentFragment);
		ft.commit();
		backFlag++;
		
	}
	/*
	public void loadTaskFragment(){
		if(taskFragment==null){
			taskFragment= new TaskFragment();
		}
		if(fragmentManager==null){
			fragmentManager=this.getSupportFragmentManager();
		}
		FragmentTransaction ft=fragmentManager.beginTransaction();
		ft.replace(R.id.frontPage, taskFragment);
		ft.addToBackStack(null);
		ft.commit();
		backFlag++;
	}  */

	public  void setMaskView(boolean flag){
		if (flag)
			darkMask.setVisibility(View.VISIBLE);
		else  darkMask.setVisibility(View.GONE);
	}
	
	public void loadCommodityDetailFragment(Commodity commo){

		tWebView.loadUrl(commo.webLink);
		commodityDetailFragment.setCurrentCommodity(commo,tWebView);

		FragmentTransaction ft=fragmentManager.beginTransaction();
	//	ft.replace(R.id.frontPage, commodityDetailFragment);
	//	ft.addToBackStack(null);

		if(currentFragment!=null) {
			fragmentStack.push(currentFragment);
			ft.hide(currentFragment);
		}
		currentFragment =commodityDetailFragment;
		if (!commodityDetailFragment.isAdded())
			ft.add(R.id.frontPage, commodityDetailFragment,"commodityDetailFragment");
		ft.show(currentFragment);

		ft.commit();
		backFlag++;
	}

	public  void loadDrawCouponFragment(String couponLink){
		drawCouponFragment.setCouponLink(couponLink);
		FragmentTransaction ft=fragmentManager.beginTransaction();
	//	ft.replace(R.id.frontPage, drawCouponFragment);
	//	ft.addToBackStack(null);

		if(currentFragment!=null) {
			fragmentStack.push(currentFragment);
			ft.hide(currentFragment);
		}
		currentFragment =drawCouponFragment;
		if (!drawCouponFragment.isAdded())
			ft.add(R.id.frontPage, drawCouponFragment,"drawCouponFragment");
		ft.show(currentFragment);

		ft.commit();
		backFlag++;
	}


	
	public void loadCategoryFragment(int cateGoryIndex){
		if(categoryFragment==null){
			categoryFragment= new CategoryFragment();
		}
		if(fragmentManager==null){
			fragmentManager=this.getSupportFragmentManager();
		}
	
		FragmentTransaction ft=fragmentManager.beginTransaction();
		ft.replace(R.id.frontPage, categoryFragment);
		ft.addToBackStack(null);
		ft.commit();
		backFlag++;
		
	}
	/*
	public void loadBuyerFragment(){
		replaceFragment(4,this.buyer_fragment);
	} */
	
	public void toHomePage(){
		tabAdapter.switchTab(0);
	}
	/*
	public void updateBuyerBonus(){
	//	this.buyer_fragment.updateBuyerBonus();
	}  */
	
	private void testTaoBaoMesg(){
	//	betterDealDB.insertTaoBaoInfo("topic1", "content1");
	//	betterDealDB.insertTaoBaoInfo("topic2", "content2");
		betterDealDB.showTaoBaoInfo();
	}
	
	
	public void showAlertDiag(String title,String msg){
		  AlertDialog.Builder diag= new AlertDialog.Builder(this);
		  diag.setTitle(title);  
		  diag.setMessage(msg);  
		  diag.setPositiveButton("Button1",  
	                new DialogInterface.OnClickListener() {  
	                    @Override
						public void onClick(DialogInterface dialog, int whichButton) {  
	                        setTitle("点击了对话框上的Button1");  
	                    }  
	                });  
		  diag.show();
		 
		
	}
	
	
	private void initMediaPlayer(){
		mediaPlayer=new MediaPlayer();
		
	//	File audioFile=new File("file:///android_asset/music/game_background_music.mp3");
	//	mediaPlayer.reset();
		
		try {
			AssetFileDescriptor fileDescriptor=this.getAssets().openFd("game_background_music.mp3");
			mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
			//mediaPlayer.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void playGameBackgroundMusic(){
		try {
			mediaPlayer.reset();
			mediaPlayer.prepare();
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mediaPlayer.start();
	}
	
	private void initConcernedStatusBar(){
		if(StaticValueClass.isAfterKitKat){
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  
		//	getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
			//get height of status bar.
			Class<?> c=null;
			Object obj=null;
			Field field=null;
			int x=0,sbar=0;
			try{
				c=Class.forName("com.android.internal.R$dimen");
				obj=c.newInstance();
				field=c.getField("status_bar_height");
				x=Integer.parseInt(field.get(obj).toString());
				sbar=getResources().getDimensionPixelSize(x);
				StaticValueClass.statusBarHeight=sbar;
				Log.d("****status bar", ""+sbar);
				c=Class.forName("com.android.internal.R$attr");
				
			}catch(Exception e){
				e.printStackTrace();
			}
			//set concerned params.
			FrameLayout frontPage=(FrameLayout)this.findViewById(R.id.frontPage);
			FrameLayout.LayoutParams params=(FrameLayout.LayoutParams)frontPage.getLayoutParams();
			params.topMargin=-StaticValueClass.statusBarHeight;
			
		}
	}
	
	private int getDefaultStatusColor(){
		int id=this.getResources().getIdentifier("statusBarColor", "attr", "android");
		int statusBarColor=-1;
		if(id>0){
			statusBarColor=this.getResources().getColor(id);
		}
		ViewGroup view=(ViewGroup)this.getWindow().getDecorView().findViewById(android.R.id.content);
		if(view!=null){
			//view.setBackgroundResource(R.drawable.slot_outter_frame1);
		}
	//	android.R.id.s
		return statusBarColor;
	}
	private int getStatusBarHeight(Context context) {
		getDefaultStatusColor();
		int id = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		int dimen = 0;
		if (id > 0)
		dimen = getResources().getDimensionPixelSize(id);
		return dimen;
		}

	
	public void returnFromWeixin(){
	//	Toast.makeText(this, "分享奖励20积分！", Toast.LENGTH_LONG).show();
	//	this.addBuyerBonus(20);
	}
	public boolean checkNetState(){
		ConnectivityManager cm=(ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null) {   
        } else {
          // 如果仅仅是用来判断网络连接 则可以使用
        //	return cm.getActiveNetworkInfo().isAvailable();  
        	
            NetworkInfo[] info = cm.getAllNetworkInfo();   
            if (info != null) {   
                for (int i = 0; i < info.length; i++) {   
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {   
                        return true;   
                    }   
                }   
            }   
        }   
        return false;   
	}
	
	public boolean shareToPlayGame(){
		if(game_share){	
			StaticValueClass.currentBuyer.shareToPlayGame();
		//	updateBuyerInfo();
			game_share=false;
			return true;
		}else return false;
	}
	
	public void addNewGameBonusRecord(GameBonusRecord record,boolean isNew){
		if(!StaticValueClass.currentBuyer.isLogined()) return;
		this.betterDealDB.insertBonusRecord(record);
		StaticValueClass.currentBuyer.bonusRecords.add(record);
		if(isNew){
			betterDealDB.addNewGameBonusRecordToServer(record);
		}
	}
	public void clearGameBonusData(){
		betterDealDB.clearBonusData();
	}
	public void updateGameBonusRecord(GameBonusRecord record){
		if(!StaticValueClass.currentBuyer.isLogined()) return;
		betterDealDB.updateBonusRecord(record);
		betterDealDB.updateBonusRecordInServer(record);
	}
	public void updateBuyerInfo(){
		if(!StaticValueClass.currentBuyer.isLogined()) return;
		this.betterDealDB.updateBuyerInfo(StaticValueClass.currentBuyer);
	}
	public void updateHeadIcon(Drawable icon){
	//	this.buyer_fragment.setHeadIcon(icon);
		this.personalCenterFragment.updateHeadIcon(icon);
	}

	@Override
	public void onLocationChanged(AMapLocation location) {
		// TODO Auto-generated method stub
		Log.d("****onLocationChanged", location.toString());
	//	Toast.makeText(this, location.toString(), Toast.LENGTH_SHORT).show();
		if(location!=null){
			if(location.getErrorCode()==0){
				StaticValueClass.cProvience=location.getProvince();
				StaticValueClass.cCity=location.getCity();
				StaticValueClass.cDistrict=location.getDistrict();
				if(locationText!=null){
					String tag=(String)locationText.getTag();
					if(tag!=null && tag.equals("1")){
						if(StaticValueClass.cProvience.compareTo(StaticValueClass.cCity)==0){
							locationText.setText(StaticValueClass.cProvience.substring(0, StaticValueClass.cProvience.length()-1)+" "+StaticValueClass.cCity);
						} else locationText.setText(StaticValueClass.cProvience+" "+StaticValueClass.cCity);
					}else locationText.setText(StaticValueClass.cProvience+" "+StaticValueClass.cCity+" "+" "+StaticValueClass.cDistrict);
				}
			}
		}
	}


	@Override
	public void onCancel() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onComplete(Object arg0) {
		// TODO Auto-generated method stub
		StaticValueClass.currentBuyer.shareToPlayGame();
	}

	@Override
	public void onError(UiError arg0) {
		// TODO Auto-generated method stub
		
	}
	
	private void testBitmapSave(){
		 Bitmap bitmap=BitmapFactory.decodeResource(this.getResources(), R.mipmap.game_prize3);
		  
		 Uri imageUri=Uri.parse("file://"+getExternalFilesDir("BetterDeal").getPath()+"/"+StaticValueClass.getGB2312Code("好好好")+".jpg");

		 String basePath=StaticValueClass.getPath(this, imageUri);
		 PicUtil.saveBitmapToDisk(bitmap, basePath);
		 /*
		 File f = new File( basePath);  // + ".jpg"
	        FileOutputStream fOut = null;
	        try {
	            	if(!f.exists()) f.createNewFile();
	                fOut = new FileOutputStream(f);
	        } catch (FileNotFoundException e) {
	                e.printStackTrace();
	        }catch (IOException e) {
	            e.printStackTrace();
	          }
	        if(bitmap!=null )   //&& fOut!=null
	        	bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
	        try {
	                fOut.flush();
	        } catch (IOException e) {
	                e.printStackTrace();
	        }
	        try {
	                fOut.close();
	        } catch (IOException e) {
	                e.printStackTrace();
	        }
	        
	        */
	}


	@Override
	public void onComplete(Bundle values) {
		// TODO Auto-generated method stub
		Oauth2AccessToken mAccessToken = Oauth2AccessToken.parseAccessToken(values); // 从 Bundle 中解析 Token
		if (mAccessToken.isSessionValid()) { 
			//AccessTokenKeeper.writeAccessToken(WBAuthActivity.this, mAccessToken); //保存Token
			Log.d("***weibo auth", "auth successful!");
	//	.........
		} else {
		// 当您注册的应用程序签名不正确时,就会收到错误Code,请确保签名正确
		String code = values.getString("code", "");
		}
	}


	@Override
	public void onWeiboException(WeiboException arg0) {
		// TODO Auto-generated method stub
		
	}

	public void doneBeforeLeave(){
		if (StaticValueClass.currentBuyer.isLogined()){
			new Thread(new Runnable() {
				@Override
				public void run() {
					// update favourite info.
                   for (int i=0;i<StaticValueClass.currentBuyer.favouriteItems.size();i++){
					   betterDealDB.insertCommodity(StaticValueClass.currentBuyer.favouriteItems.get(i),1);
				   }
					for (int i=0;i<StaticValueClass.currentBuyer.tracingItems.size();i++){
						betterDealDB.insertCommodity(StaticValueClass.currentBuyer.tracingItems.get(i),2);
					}
				}
			}).start();
		}
	}

	private void initDuiBaListener(){
		CreditActivity.creditsListener = new CreditActivity.CreditsListener() {
			/**
			 * 当点击分享按钮被点击
			 * @param shareUrl 分享的地址
			 * @param shareThumbnail 分享的缩略图
			 * @param shareTitle 分享的标题
			 * @param shareSubtitle 分享的副标题
			 */
			public void onShareClick(WebView webView, String shareUrl,String shareThumbnail, String shareTitle,  String shareSubtitle) {
				//当分享按钮被点击时，会调用此处代码。在这里处理分享的业务逻辑。
                /*
				new AlertDialog.Builder(webView.getContext())
						.setTitle("分享信息")
						.setItems(new String[] {"标题："+shareTitle,"副标题："+shareSubtitle,"缩略图地址："+shareThumbnail,"链接："+shareUrl}, null)
						.setNegativeButton("确定", null)
						.show();
                     */

				Log.v("credit share",""+shareTitle+":"+shareThumbnail+":"+shareUrl);
				ShareController oneShareDialog=new ShareController(webView.getContext(),R.style.customDialog);
				oneShareDialog.setShareInfo(shareTitle,"我领的福利！不领白不领~",shareThumbnail,shareUrl);
				oneShareDialog.show();

			}

			/**
			 * 当点击“请先登录”按钮唤起登录时，会调用此处代码。
			 * 用户登录后，需要将CreditsActivity.IS_WAKEUP_LOGIN变量设置为true。
			 * @param webView 用于登录成功后返回到当前的webview刷新登录状态。
			 * @param currentUrl 当前页面的url
			 */
			public void onLoginClick(WebView webView, String currentUrl) {
				//当未登录的用户点击去登录时，会调用此处代码。
				//用户登录后，需要将CreditsActivity.IS_WAKEUP_LOGIN变量设置为true。
				//为了用户登录后能回到未登录前的页面（currentUrl）。
				//当用户登录成功后，需要重新请求一次服务端，带上currentUrl。
				//用该方法中的webview变量加载请求链接。
				//服务端收到请求后在生成免登录url时，将currentUrl放入redirect参数，通知客户端302跳转到新生成的免登录URL。
				new AlertDialog.Builder(webView.getContext())
						.setTitle("跳转登录")
						.setMessage("跳转到登录页面？")
						.setPositiveButton("是", null)
						.setNegativeButton("否", null)
						.show();
			}

			/**
			 * 当点击“复制”按钮时，触发该方法，回调获取到券码code
			 * @param webView webview对象。
			 * @param code 复制的券码
			 */
			public void onCopyCode(WebView webView, String code) {
				//当未登录的用户点击去登录时，会调用此处代码。
				new AlertDialog.Builder(webView.getContext())
						.setTitle("复制券码")
						.setMessage("已复制，券码为："+code)
						.setPositiveButton("是", null)
						.setNegativeButton("否", null)
						.show();
			}

			/**
			 * 积分商城返回首页刷新积分时，触发该方法。
			 */
			public void onLocalRefresh(WebView mWebView, String credits) {
				//String credits为积分商城返回的最新积分，不保证准确。
				//触发更新本地积分，这里建议用ajax向自己服务器请求积分值，比较准确。
				Toast.makeText(getApplicationContext(), "触发本地刷新积分："+credits,Toast.LENGTH_SHORT).show();
			}
		};
	}

	public void getVersionInfo(){
		new Thread(new Runnable(){
			String version,content;
			@Override
			public void run() {
				// TODO Auto-generated method stub
				ArrayList<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("app_id","1"));
				Looper.prepare();
				try{
					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost(StaticValueClass.serverAddress+"get_versioninfo.php");
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
					HttpResponse response = httpclient.execute(httppost);
					Log.d("get_versioninfo", " recode:"+response.getStatusLine().getStatusCode());
					if(response.getStatusLine().getStatusCode()==200){
						String   mResult= EntityUtils.toString(response.getEntity());
						Log.d("get_versioninfo", "mResult:"+mResult);

						JSONObject jsonObject;
						JSONArray jsonArray = new JSONArray(mResult);
						jsonObject=(JSONObject)jsonArray.opt(0);
						version=(String)jsonObject.getString("version");
						content=(String)jsonObject.getString("content");
						MainActivity.this.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								if(version.compareTo(StaticValueClass.currentVersion)>0){
									showVersionInfo(version,content);
								}
							}
						});

					}
				} catch(JSONException e){
					// Toast.makeText(ma, "密码有误!", Toast.LENGTH_LONG).show();
				}
				catch(Exception e){
					Log.e("log_tag", "Error in http connection"+e.toString());
					//  Toast.makeText(ma, "网络异常，请稍后再试!", Toast.LENGTH_LONG).show();
				}

			}

		}).start();


	}

	private  void showVersionInfo(String version,String content){
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setTitle("V"+version);
		builder.setMessage(content);
		builder.setPositiveButton("下载",new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		builder.create().show();
	}



}