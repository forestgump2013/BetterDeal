package com.way.betterdeal;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import org.apache.http.protocol.HTTP;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
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
import com.way.betterdeal.fragments.BonusRecordFragment;
import com.way.betterdeal.fragments.Buyer_Edit_fragment;
import com.way.betterdeal.fragments.CategoryFragment;
import com.way.betterdeal.fragments.ChooseAddressFragment;
import com.way.betterdeal.fragments.CoinFragment;
import com.way.betterdeal.fragments.CommodityFragment;
import com.way.betterdeal.fragments.CommodityDetailFragment;
import com.way.betterdeal.fragments.ConfigrationFragment;
import com.way.betterdeal.fragments.CreateAddressFragment;
import com.way.betterdeal.fragments.DuiBaCreditMallFragment;
import com.way.betterdeal.fragments.ExchangeListFragment;
import com.way.betterdeal.fragments.FavouriteFragment;
import com.way.betterdeal.fragments.GameFragment;
import com.way.betterdeal.fragments.GameWelcomeFragment;
import com.way.betterdeal.fragments.MobileBandFragment;
import com.way.betterdeal.fragments.PersonalCenterFragment;
import com.way.betterdeal.fragments.PrizeFragment;
import com.way.betterdeal.fragments.RegisterFragment;
import com.way.betterdeal.fragments.SearchFragment;
import com.way.betterdeal.fragments.ExchangeFragment;
import com.way.betterdeal.fragments.SignFragment;
import com.way.betterdeal.fragments.SuperDiscoutFragment;
import com.way.betterdeal.fragments.TaskFragment;
import com.way.betterdeal.fragments.UserLoginFragment;
import com.way.betterdeal.fragments.WelcomeFragment;
import com.way.betterdeal.object.AsynImageLoader;
import com.way.betterdeal.object.BetterDealDB;
import com.way.betterdeal.object.Commodity;
import com.way.betterdeal.object.PicUtil;
import com.way.betterdeal.object.GameBonusRecord;
import com.way.betterdeal.object.ShareController;
//import com.way.betterdeal.view.CustomHorizontalScrollView;
import com.way.betterdeal.view.HomeCenterLayout;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends FragmentActivity implements AMapLocationListener,IUiListener,WeiboAuthListener{
	
	public static int weiXinCallBackRequestCode=210;
	
	WelcomeFragment welcomeFragment;
	private HomeCenterLayout centerLayout;
	private FrameLayout frontPage;
	private SharedPreferences sp;
	private BetterDealDB betterDealDB;
	private  ShareController shareController;
	private MediaPlayer mediaPlayer;
	//------------------------------------
	 private RadioGroup rgs;
	 
//	 ControllingHoriontalLinearlayout horizontalScrollView;
     public List<Fragment> fragments = new ArrayList<Fragment>();

     public String hello = "hello ";
     TextView pinkLines[];
     EditText searchText,locationText;
     public  ItemService itemService=null;
     RelativeLayout hintView;
     FragmentManager fragmentManager;
     static CommodityFragment commodityFragment;
     SuperDiscoutFragment superDiscoutFragment;
     SignFragment signFragment;
     
     GameFragment gameFragment;
     PrizeFragment prizeFragment;
     ChooseAddressFragment chooseAddressFragment;
     CreateAddressFragment createAddressFragment;
     BonusRecordFragment bonusRecordFragment;
      Buyer_Edit_fragment buyer_edit_fragment;
     PersonalCenterFragment personalCenterFragment;
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
     MobileBandFragment mobileBandFragment;
     
     private int backCount=0,backFlag=0,pinkLinePosition=0,clearFlag=0;
     //------------------------------
     public static final String APP_ID="wxe8ffa219f8bac45b";
     public IWXAPI api;
     Button unLoginBtn;
     boolean game_share=false;
     
     AuthInfo weiboAuthInfo;
     SsoHandler weiboSsoHandler;
     
     //concerns loaction
     AMapLocationClient mLocationClient;
     //concerns share.


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//透明状态栏  
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  
        //透明导航栏  
     //  getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		//get global values
		sp=this.getSharedPreferences("Deal", Context.MODE_PRIVATE);
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		StaticValueClass.initNetConnect();
		StaticValueClass.asynImageLoader =new AsynImageLoader(this);
	//	StaticValueClass.screenWidth = metric.widthPixels;
	//	StaticValueClass.screenHeight = metric.heightPixels;
	//	StaticValueClass.screenDensity=metric.densityDpi;
	//	StaticValueClass.density=metric.density;
	//	StaticValueClass.scaleDensity=metric.scaledDensity;
	//	StaticValueClass.buyer=sp.getString("BUYRE", "none");
		StaticValueClass.hanYiThinFont=Typeface.createFromAsset(getAssets(),"fonts/hanyi_thin_round1.ttf");
		StaticValueClass.huangKangFont=Typeface.createFromAsset(getAssets(),"fonts/huagang_girl.ttf");

		centerLayout = (HomeCenterLayout) findViewById(R.id.homeCenter);
		frontPage=(FrameLayout)findViewById(R.id.frontPage);
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
        // check login status
        StaticValueClass.currentBuyer.tel=sp.getString("BUYER", "none");
        if(StaticValueClass.currentBuyer.tel.compareTo("none")==0){
			StaticValueClass.logined=false;
        }else {
			StaticValueClass.logined=true;
        } 
        Log.d("***Buyer.tel", StaticValueClass.currentBuyer.tel);
        fragments.add(personalCenterFragment);
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
    		   Toast.makeText(MainActivity.this, "init successfully", Toast.LENGTH_LONG).show() ;
    	   }
    	   public void onFailure(int code ,String message){
    		   Toast.makeText(MainActivity.this, "init failed!"+message, Toast.LENGTH_LONG).show() ;
    		   Log.d("AlibabaSDK.asyncInit", "failed!" +
    		   		"1"+message);
    	   }
    	   
       });   
    //   initRadioButtons();
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
			StaticValueClass.firstUseed=true;
		}else StaticValueClass.firstUseed=false;
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
	
	public void hideTitleView(boolean flag){
	//	if(flag) titleView.setVisibility(View.GONE);
	//	else titleView.setVisibility(View.VISIBLE);
	}
	
	private void initRadioButtons(){
		RadioButton tab_rb_a=(RadioButton)findViewById(R.id.tab_rb_a);
		RadioButton tab_rb_b=(RadioButton)findViewById(R.id.tab_rb_b);
		RadioButton tab_rb_c=(RadioButton)findViewById(R.id.tab_rb_c);
		RadioButton tab_rb_d=(RadioButton)findViewById(R.id.tab_rb_d);
		RadioButton tab_rb_e=(RadioButton)findViewById(R.id.tab_rb_e);
		initRadiaButton(tab_rb_a,R.drawable.radio_group_selector1);
		initRadiaButton(tab_rb_b,R.drawable.radio_group_selector2);
		initRadiaButton(tab_rb_c,R.drawable.radio_group_selector3);
		initRadiaButton(tab_rb_d,R.drawable.radio_group_selector4);
		initRadiaButton(tab_rb_e,R.drawable.radio_group_selector5);
	}
	
	private void initRadiaButton(final RadioButton radioButton,final int rsID){
		int l1,width,height;
	//	Drawable drawables[];
	//	drawables=radioButton.getCompoundDrawables();
	//	Rect bound=drawables[1].getBounds();
		/*
		l1=bound.right-bound.left;
		width=StaticValueClass.dip2px(MainActivity.this, 20);
		height=StaticValueClass.dip2px(MainActivity.this, 19);
		bound.top=0;
		bound.right=bound.left+width;
		bound.bottom=bound.top+height;
		drawables[1].setBounds(bound);
		
		radioButton.setCompoundDrawables(null, drawables[1], null, null);
		*/
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
				LinearLayout.LayoutParams params=(LinearLayout.LayoutParams)rgs.getLayoutParams();
				initConcernedStatusBar();
				hintView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						hintView.setVisibility(View.GONE);
					}
				});
				//init Fragments
				initFragments();
				checkFirstTime();
				//concerned right window.
				
				betterDealDB=new BetterDealDB(MainActivity.this);
			//	testTaoBaoMesg();
				if(StaticValueClass.logined){
					loadLoginData();
				}
				shareController=new ShareController(MainActivity.this);
				StaticValueClass.ma=MainActivity.this;
				StaticValueClass.networkState=checkNetState();
			
				initMediaPlayer();
				initLocation();
				initShares();
			}
			
		});
		initTabSlidingLine();
	}
	
	private void initFragments(){
		superDiscoutFragment=new SuperDiscoutFragment();
		signFragment=new SignFragment();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		CallbackContext.onActivityResult(requestCode, resultCode, data);
		shareController.mTencent.onActivityResultData(requestCode, resultCode, data, this);
		if (weiboSsoHandler != null) {
			weiboSsoHandler.authorizeCallBack(requestCode, resultCode, data);
		    }
	
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		if(gameFragment!=null  && gameFragment.gameIsRunning()) return;
		
		if(backFlag>0){
			backFlag--;
			super.onBackPressed();
			return;
		}
		//check the current tab.
		if(tabAdapter.switchTab(0))
			return;
		
		if(backCount==0){
			backCount++;
			Toast.makeText(this, "再按一次退出剁手联盟。", Toast.LENGTH_SHORT).show();
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
			this.finish();
			android.os.Process.killProcess(android.os.Process.myPid());
			StaticValueClass.firstActiviy.finish();
		//	super.onBackPressed();
		}
	}
	public  void switchTabFragment(int pos){
		tabAdapter.switchTab(pos);
	}
	public boolean isLogined(){
		return StaticValueClass.logined;
	}
	
	public void showShareDialog(String title){
		if(title.compareTo("")!=0)
			shareController.setTitle(title);
		shareController.show();
	}
	
	public void weixinShareOperation(String text,int flag){
		game_share=true;
		shareController.weixinShare(text, flag);
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
	
	public void addBuyerBonus(int count){
		StaticValueClass.currentBuyer.bonus+=count;
	//	this.buyer_fragment.updateBuyerBonus();
		this.betterDealDB.updateBuyerInfo(StaticValueClass.currentBuyer);
	}
	
	private void initLeftWindow(){
		searchText=(EditText)findViewById(R.id.searchText);
		//+this.getStatusBarHeight(this)
		searchText.setText(""+"  "+StaticValueClass.screenWidth+"*"+StaticValueClass.screenHeight);
		ListView categoryListView=(ListView)findViewById(R.id.categoryListView);
	//	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	//	        android.R.layout.simple_list_item_1, StaticValueClass.firstLevelCategory);
		/*
		CategoryAdapter categoryAdapter = new CategoryAdapter(this,StaticValueClass.firstLevelCategory,categoryListView);
		categoryListView.setAdapter(categoryAdapter);
		categoryListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				MainActivity.this.onBackPressed();
				MainActivity.this.loadCategoryFragment(position);
			}
			
		}); */
		searchText.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MainActivity.this.loadSearchFragment();
			}
		});
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

  /*
	private  void pinkLineFloatMove(int position){
		 int distance=(position-pinkLinePosition)*(StaticValueClass.screenWidth/5);
	
		// horizontalScrollView.smoothScrollBy(-distance, 0);
		 horizontalScrollView.horizontalMove(-distance);
		 pinkLinePosition=position;
	} */
	
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

	/*
	public void getBetterCommodityInfo(){
		Log.d("*mainactivity","getBetterCommodityInfo");
		new Thread(new Runnable(){

			public void run() {
				// TODO Auto-generated method stub
		//		ArrayList<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
	              Looper.prepare(); 
	              try{
	                   HttpClient httpclient = new DefaultHttpClient();
	                   HttpPost httppost = new HttpPost("http://www.qcygl.com/get_better_commodity_info.php");
	           //        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,HTTP.UTF_8));
	                   HttpResponse response = httpclient.execute(httppost);
	                   Log.d("***get_commodity_info:", " recode:"+response.getStatusLine().getStatusCode());
	                   if(response.getStatusLine().getStatusCode()==200){
	                	  String mResult=EntityUtils.toString(response.getEntity());
	                	  Log.d("get commodity result:", mResult);
	                	   JSONObject jsonObject;

	                	   JSONArray jsonArray = new JSONArray(mResult);
	                	   for(int i=0;i<jsonArray.length();i++){
	                		   jsonObject=(JSONObject)jsonArray.opt(i);
	                	//	   Log.d("get fueling  info", ""+Float.parseFloat(jsonObject.getString("volume")));
	                		  Commodity item=new Commodity(jsonObject.getInt("item_order"),jsonObject.getInt("item_market"),
	                				  jsonObject.getLong("item_id"),jsonObject.getInt("item_bounds"));
	                		  item.loadData(jsonObject.getString("item_title"), (float)jsonObject.getDouble("item_price"), (float)jsonObject.getDouble("item_reserve_price"), jsonObject.getString("item_pic_url"));
	                		  Log.d("get commodity info", item.toString());
	                		  StaticValueClass.betterCommodities.add(item);
	                	   }
	                	//   Toast.makeText(MainActivity.this, "getCommodityInfo", Toast.LENGTH_LONG).show();
	                	//   commodityFragment.addMoreItems(20);
	                   }
	              }catch(Exception e){
	                   Log.e("log_tag", "Error in http connection :get_commodity_info "+e.toString());
	              }
			}
			
		}).start();
	} */
	/*
	public void buyerLogin(String buyerId){
		Editor editor=sp.edit();
		editor.putString("BUYER",buyerId);
		editor.putString("LOGIN_DATE", StaticValueClass.dateFormat.format(new Date()));
		editor.commit(); 
	}
	public void buyerLoginOut(){
		Editor editor=sp.edit();
		editor.putString("BUYER","none");
		editor.commit(); 
	} */
	/*
	public void getCheapCommodityInfo(){
	
		Log.d("*************","getCheapCommodityInfo");
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
		//		ArrayList<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
	              Looper.prepare(); 
	              try{
	                   HttpClient httpclient = new DefaultHttpClient();
	                   HttpPost httppost = new HttpPost("http://www.qcygl.com/get_better_commodity_info.php");
	           //        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,HTTP.UTF_8));
	                   HttpResponse response = httpclient.execute(httppost);
	                   Log.d("***get_commodity_info:", " recode:"+response.getStatusLine().getStatusCode());
	                   if(response.getStatusLine().getStatusCode()==200){
	                	  String mResult=EntityUtils.toString(response.getEntity());
	                	  Log.d("get commodity result:", mResult);
	                	   JSONObject jsonObject;

	                	   JSONArray jsonArray = new JSONArray(mResult);
	                	   for(int i=0;i<jsonArray.length();i++){
	                		   jsonObject=(JSONObject)jsonArray.opt(i);
	                	//	   Log.d("get fueling  info", ""+Float.parseFloat(jsonObject.getString("volume")));
	                		  Commodity item=new Commodity(jsonObject.getInt("item_order"),jsonObject.getInt("item_market"),
	                				  jsonObject.getLong("item_id"),jsonObject.getInt("item_bounds"));
	                		  item.loadData(jsonObject.getString("item_title"), (float)jsonObject.getDouble("item_price"), (float)jsonObject.getDouble("item_reserve_price"), jsonObject.getString("item_pic_url"));
	                		  Log.d("get commodity info", item.toString());
	                		  StaticValueClass.cheapCommodities.add(item);
	                	   }
	                	   Toast.makeText(MainActivity.this, "getCommodityInfo", Toast.LENGTH_LONG).show();
	                	//   commodityFragment.addMoreItems(20);
	                   }
	              }catch(Exception e){
	                   Log.e("log_tag", "Error in http connection :get_commodity_info "+e.toString());
	              }
			}
			
		}).start();
		
	} */
	
	
	public void replaceFragment(int idx,Fragment fragment){
		
		tabAdapter.replacefragment(idx, fragment);
	}
	
	public void loadLoginData(){
		if(loginFragment==null){
			loginFragment=new UserLoginFragment( );
		}
		loginFragment.loginIn(this,true);
	}
	
	public void loginWithTel(String tel){
		StaticValueClass.currentBuyer.tel=tel;
		StaticValueClass.logined=true;
		Editor editor=sp.edit();
		editor.putString("BUYER", tel);
		editor.putString("LOGIN_DATE", StaticValueClass.dateFormat.format(new Date()));
		editor.commit(); 
	//	this.betterDealDB.buyerLoginIn(StaticValueClass.currentBuyer);
		this.personalCenterFragment.refreashLoginStatus();
		
	}
	
	
	public void unLogin(){
		StaticValueClass.logined=false;
		Editor editor=sp.edit();
		editor.putString("BUYER", "none");
		editor.commit(); 
		//
		if(loginFragment==null){
			loginFragment=new UserLoginFragment();
		}
		this.loginFragment.loginOut();
		//
		this.personalCenterFragment.loginOut();
		this.betterDealDB.buyerLoginOut();
	}
	
	public FragmentManager getMyFragmentManager(){
		if(fragmentManager==null){
			fragmentManager=this.getSupportFragmentManager();
		}
		return fragmentManager;
	}
	
	public void loadSuperDiscountFragment(){
		/*
		if(buyer_edit_fragment==null){
			buyer_edit_fragment=new Buyer_Edit_fragment(this);
		} */
		if(fragmentManager==null){
			fragmentManager=this.getSupportFragmentManager();
		}
		FragmentTransaction ft=fragmentManager.beginTransaction();
		ft.replace(R.id.frontPage, superDiscoutFragment);
		ft.addToBackStack(null);
		ft.commit();
		backFlag++;
	}
	
	public void loadSignFragment(){
		
		if(fragmentManager==null){
			fragmentManager=this.getSupportFragmentManager();
		}
		FragmentTransaction ft=fragmentManager.beginTransaction();
		ft.replace(R.id.frontPage, signFragment);
		ft.addToBackStack(null);
		ft.commit();
		backFlag++;
	}
	
	//Buyer_Edit_fragment
	public void loadBuyer_Edit_fragment(){
		if(buyer_edit_fragment==null){
			buyer_edit_fragment=new Buyer_Edit_fragment();
		}
		if(fragmentManager==null){
			fragmentManager=this.getSupportFragmentManager();
		}
		FragmentTransaction ft=fragmentManager.beginTransaction();
		ft.replace(R.id.frontPage, buyer_edit_fragment);
		ft.addToBackStack(null);
		ft.commit();
		backFlag++;
	}
	
	public void loadLoginFragment(){
		if(loginFragment==null){
			loginFragment=new UserLoginFragment();
		}
		if(fragmentManager==null){
			fragmentManager=this.getSupportFragmentManager();
		}
		FragmentTransaction ft=fragmentManager.beginTransaction();
		ft.replace(R.id.frontPage, loginFragment);
		ft.addToBackStack(null);
		ft.commit();
		backFlag++;
	}
	
	public void loadMobileBandFragment(){
		if(mobileBandFragment==null){
			mobileBandFragment=new MobileBandFragment();
		}
		/*
		if(fragmentManager==null){
			fragmentManager=this.getSupportFragmentManager();
		} */
		FragmentTransaction ft=fragmentManager.beginTransaction();
		ft.replace(R.id.frontPage, mobileBandFragment);
		ft.addToBackStack(null);
		ft.commit();
		backFlag++;
	}
	
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
		
	}
	
	public void loadChooseAddressFragment(GameBonusRecord record){
		if(chooseAddressFragment==null){
			chooseAddressFragment=new ChooseAddressFragment();
		}else chooseAddressFragment.setConcernsBonusRecord(record);
		if(fragmentManager==null){
			fragmentManager=this.getSupportFragmentManager();
		}
		FragmentTransaction ft=fragmentManager.beginTransaction();
		ft.replace(R.id.frontPage, chooseAddressFragment);
		ft.addToBackStack(null);
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
		ft.replace(R.id.frontPage, createAddressFragment);
		ft.addToBackStack(null);
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
		ft.replace(R.id.frontPage, prizeFragment);
		ft.addToBackStack(null);
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
		ft.replace(R.id.frontPage, gameFragment);
		ft.addToBackStack(null);
		ft.commit();
		backFlag++;
	//	gameFragment.switchGame(flag);
	}
	
	public void pushFragment(){
		backFlag++;
	}
	
	public void loadCoinFragment(){
		if(coinFragment==null){
			coinFragment=new CoinFragment();
		}
		if(fragmentManager==null){
			fragmentManager=this.getSupportFragmentManager();
		}
		FragmentTransaction ft=fragmentManager.beginTransaction();
		ft.replace(R.id.frontPage, coinFragment);
		ft.addToBackStack(null);
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
		ft.replace(R.id.frontPage, favouriteFragment);
		ft.addToBackStack(null);
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
		ft.replace(R.id.frontPage, configrationFragment);
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
		ft.replace(R.id.frontPage, bonusRecordFragment);
		ft.addToBackStack(null);
		ft.commit();
		backFlag++;
	}
	
	public void loadRegisterFragment(int d){
		if(registerFragment==null){
			registerFragment=new RegisterFragment();
		}
		registerFragment.setDirect(d);
		if(fragmentManager==null){
			fragmentManager=this.getSupportFragmentManager();
		}
		FragmentTransaction ft=fragmentManager.beginTransaction();
		ft.replace(R.id.frontPage, registerFragment);
		ft.addToBackStack(null);
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
		ft.replace(R.id.frontPage, searchFragment);
		ft.addToBackStack(null);
		ft.commit();
		backFlag++;
		
	}
	
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
	}
	
	public void loadCommodityDetailFragment(Commodity commo){
		if(commodityDetailFragment==null){
			commodityDetailFragment= new CommodityDetailFragment();
		}
		commodityDetailFragment.setCurrentCommodity(commo);

		FragmentTransaction ft=fragmentManager.beginTransaction();
		ft.replace(R.id.frontPage, commodityDetailFragment);
		ft.addToBackStack(null);
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
	
	public void updateBuyerBonus(){
	//	this.buyer_fragment.updateBuyerBonus();
	}
	
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
	/*
	private void initALiMsgApi() throws Exception{
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest", StaticValueClass.taoBaoAppKey, StaticValueClass.taoBaoAppSecret, "json");  
				do{  
				long quantity = 100L;  
				TmcMessagesConsumeResponse rsp = null;  
				do {  
				TmcMessagesConsumeRequest req = new TmcMessagesConsumeRequest();  
				req.setQuantity(quantity);
				//setQuantity(quantity);  
				req.setGroupName("default");  
				try {
					rsp = client.execute(req);
				} catch (ApiException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
				
				if (rsp.isSuccess() && rsp.getMessages() != null) {  
				for (TmcMessage msg : rsp.getMessages()) {  
				// handle message  
			//	out.println(msg.getContent());  
			//	out.println(msg.getTopic());  
					MainActivity.this.showAlertDiag(msg.getTopic(), msg.getContent());
				// confirm message  
				TmcMessagesConfirmRequest cReq = new TmcMessagesConfirmRequest();  
			//	cReq.setGroupName(groupName)
				cReq.setGroupName("default");  
				cReq.setsMessageIds(String.valueOf(msg.getId()));  
				TmcMessagesConfirmResponse cRsp;
				try {
					cRsp = client.execute(cReq);
					Toast.makeText(MainActivity.this, cRsp.getBody(), Toast.LENGTH_LONG).show();
				} catch (ApiException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
			///	out.println(cRsp.getBody());  
				
				}  
				}  
			//	out.println(rsp.getBody());  
				Toast.makeText(MainActivity.this, rsp.getBody(), Toast.LENGTH_LONG).show();
				} while (rsp != null && rsp.isSuccess() && rsp.getMessages() != null && rsp.getMessages().size() == quantity);  
				try {
					Thread.sleep(3000L);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
				} while(true);
				
			}
			
		}).start();
	}
	*/
	
	
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
	/*
	private void initTaoBaoTmcClient(){
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				TmcClient client=new TmcClient(StaticValueClass.taoBaoAppKey,StaticValueClass.taoBaoAppSecret,"default");
			//	client=new TmcClient("ws://mc.api.tbsandbox.com/","1021719331","6101f09361aed9d701c633cee6ae6bded799959eff500f6182558410","default");
				client.setMessageHandler(new MessageHandler(){

					@Override
					public void onMessage(Message msg, MessageStatus arg1)
							throws Exception {
						// TODO Auto-generated method stub
						Toast.makeText(MainActivity.this, "接收到消息", Toast.LENGTH_LONG).show();
						betterDealDB.insertTaoBaoInfo(msg.getTopic(), msg.getContent());
					}
					
				});
				try {
					client.connect();
					Thread.sleep(64000000L);
				} catch (com.taobao.api.internal.toplink.LinkException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch(Exception e){
					e.printStackTrace();
				}
				
			}
			
		}).start();
		
	}  */
	
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
		//	centerLayout.setPadding(0, StaticValueClass.statusBarHeight, 0, 0);
			FrameLayout frontPage=(FrameLayout)this.findViewById(R.id.frontPage);
			FrameLayout.LayoutParams params=(FrameLayout.LayoutParams)frontPage.getLayoutParams();
			params.topMargin=-StaticValueClass.statusBarHeight;
		//    frontPage.setPadding(0, StaticValueClass.statusBarHeight, 0, 0);
		//	LinearLayout.LayoutParams params1= (LinearLayout.LayoutParams)this.titleView.getLayoutParams();
		//	params1.height=sbar+StaticValueClass.dip2px(this, 46);
			/*
			StaticValueClass.statusBarHeight=sbar;
			LinearLayout.LayoutParams params1= (LinearLayout.LayoutParams)this.titleView.getLayoutParams();
			params1.topMargin=sbar;
			RelativeLayout leftContentView=(RelativeLayout)this.findViewById(R.id.leftContentView);
			LinearLayout.LayoutParams params2= (LinearLayout.LayoutParams)leftContentView.getLayoutParams();
			params2.topMargin=sbar;
			
			LinearLayout.LayoutParams params3=(LinearLayout.LayoutParams)frontPage.getLayoutParams();
			*/
		//    params3.topMargin=sbar;
		
			
		}
	}
	
	public void raiseFrame(boolean raise){
		/*
		FrameLayout frontPage=(FrameLayout)this.findViewById(R.id.frontPage);
		LinearLayout.LayoutParams params3=(LinearLayout.LayoutParams)frontPage.getLayoutParams();
		if(raise){
		    params3.topMargin=0;
		}else params3.topMargin=StaticValueClass.statusBarHeight;
		*/
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
	
	
	/*
	private void initTaoBaoClient(){
		
		StaticValueClass.taoBaoClient=new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest", StaticValueClass.taoBaoAppKey
				, StaticValueClass.taoBaoAppSecret, "json");	
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try{
				do {  
				    long quantity = 100L;  
				    TmcMessagesConsumeResponse rsp = null;  
				    do {  
				        TmcMessagesConsumeRequest req = new TmcMessagesConsumeRequest();  
				        req.setQuantity(quantity);  
				        req.setGroupName("default");  
				        
							rsp = StaticValueClass.taoBaoClient.execute(req);
						
				        if (rsp.isSuccess() && rsp.getMessages() != null) {  
				            for (TmcMessage msg : rsp.getMessages()) {  
				                // handle message  
				           //     System.out.println(msg.getContent());  
				           //     System.out.println(msg.getTopic());  
				            	Toast.makeText(MainActivity.this, "接收到消息", Toast.LENGTH_LONG).show();
				                betterDealDB.insertTaoBaoInfo(msg.getTopic(), msg.getContent());
				                // confirm message  
				                TmcMessagesConfirmRequest cReq = new TmcMessagesConfirmRequest();  
				                cReq.setGroupName("default");  
				                cReq.setsMessageIds(String.valueOf(msg.getId()));  
				                TmcMessagesConfirmResponse cRsp = StaticValueClass.taoBaoClient.execute(cReq);  
				                System.out.println(cRsp.getBody());  
				            }  
				        }  
				        System.out.println(rsp.getBody());  
				    } while (rsp != null && rsp.isSuccess() && rsp.getMessages() != null && rsp.getMessages().size() == quantity);  
				    Thread.sleep(3000L);  
				} while (true);
				} catch (ApiException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				catch(Exception e){
					e.printStackTrace();
				}
				
			}
			
		}).start();
	}
	
	*/
	
	public void returnFromWeixin(){
		Toast.makeText(this, "分享奖励20积分！", Toast.LENGTH_LONG).show();
		this.addBuyerBonus(20);
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
		if(!StaticValueClass.logined) return;
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
		if(!StaticValueClass.logined) return;
		betterDealDB.updateBonusRecord(record);
		betterDealDB.updateBonusRecordInServer(record);
	}
	public void updateBuyerInfo(){
		if(!StaticValueClass.logined) return;
		this.betterDealDB.updateBuyerInfo(StaticValueClass.currentBuyer);
	}
	public void updateHeadIcon(Drawable icon){
	//	this.buyer_fragment.setHeadIcon(icon);
		this.personalCenterFragment.updateHeadIcon(icon);
	}
	
	
	
	public void setFullScreenColor(){
		
		clearFlag=0;
		//透明状态栏  
       getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  
        //透明导航栏  
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);  
        
    //    this.getWindow().setBackgroundDrawableResource(R.color.ds_routine_color);
	}
	
	public void clearScreenColor(){
		clearFlag=1;
	//	getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
	//	getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
	//	 this.getWindow().setBackgroundDrawableResource(R.color.translucent_background);
	//	this.getWindow().setBackgroundDrawableResource(resid)
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

	public void leaveOperation(){
		if (StaticValueClass.logined){
			if (StaticValueClass.currentBuyer.favouriteItems.size()>0){
				// update favourite info.
				new Thread(new Runnable() {
					@Override
					public void run() {
						// compile data.
						JSONArray array=new JSONArray();
						for(int i=0;i<StaticValueClass.currentBuyer.favouriteItems.size();i++){
							JSONObject object=new JSONObject();
							Commodity commodity=StaticValueClass.currentBuyer.favouriteItems.get(i);
							try{
								object.put("buyer",StaticValueClass.currentBuyer.tel);
								object.put("category",commodity.category);
								object.put("idx",commodity.order);
							}catch (Exception e){
								e.printStackTrace();
							}
							array.put(object);
						}
						// commit data.
					}
				}).start();
			}
		}
	}

}