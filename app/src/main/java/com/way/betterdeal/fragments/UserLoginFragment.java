package com.way.betterdeal.fragments;

//import com.way.miniqq.R;

//import com.alibaba.sdk.android.AlibabaSDK;
//import com.alibaba.sdk.android.callback.CallbackContext;
//import com.alibaba.sdk.android.AlibabaSDK;
//import com.alibaba.sdk.android.callback.CallbackContext;
//import com.alibaba.sdk.android.login.LoginService;
//import com.alibaba.sdk.android.login.callback.LoginCallback;
//import com.alibaba.sdk.android.session.model.Session;
//import com.alibaba.sdk.android.session.model.Session;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

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

import com.alibaba.sdk.android.AlibabaSDK;
import com.alibaba.sdk.android.callback.CallbackContext;
import com.alibaba.sdk.android.login.LoginService;
import com.alibaba.sdk.android.login.callback.LoginCallback;
import com.alibaba.sdk.android.login.callback.LogoutCallback;
import com.alibaba.sdk.android.session.model.Session;

import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;
import com.way.betterdeal.object.CoinRecord;
import com.way.betterdeal.object.GameBonusRecord;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:wangjie@cyyun.com
 * Date: 13-6-14
 * Time: 下午2:39
 */
public class UserLoginFragment extends Fragment implements IUiListener{
	
	MainActivity ma;
	View loginView;
	Button loginBtn,backBtn;
	TextView registerBuyer,forgetPassword;
	EditText buyerTel,password;
	ImageButton taobaoLoginBtn;
	ImageView taobaoUnited,qqUnited,weixinUnited,weiboUnited;
	LoginService taobaoLoginService=null;
	public Tencent mTencent;
	
	public UserLoginFragment(){
		ma=(MainActivity)this.getActivity();
		mTencent = Tencent.createInstance(StaticValueClass.tentcentAppID, this.getContext());
	}
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        System.out.println("DDDDDDDDD____onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("DDDDDDDDD____onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
     //   System.out.println("DDDDDDDDD____onCreateView");
    	loginView=inflater.inflate(R.layout.login_layout , container, false);
		ma=(MainActivity)this.getActivity();
		taobaoLoginBtn=(ImageButton)loginView.findViewById(R.id.taobaoLoginBtn);
    	loginBtn=(Button)loginView.findViewById(R.id.loginBtn);
    	backBtn=(Button)loginView.findViewById(R.id.backBtn);
    	registerBuyer=(TextView)loginView.findViewById(R.id.registerBuyer);
    	forgetPassword=(TextView)loginView.findViewById(R.id.forgetPassword);
    	buyerTel=(EditText)loginView.findViewById(R.id.buyerTel);
        password=(EditText)loginView.findViewById(R.id.password);
        taobaoUnited=(ImageView)loginView.findViewById(R.id.taobaoUnited);
        qqUnited=(ImageView)loginView.findViewById(R.id.qqUnited);
        weixinUnited=(ImageView)loginView.findViewById(R.id.weixinUnited);
        weiboUnited=(ImageView)loginView.findViewById(R.id.weiboUnited);
    	//taobaoLoginBtn.setBackgroundResource(R.drawable.taobao_icon);
        backBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ma.onBackPressed();
			}
		});
        taobaoUnited.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(taobaoLoginService==null)
					taobaoLoginService=AlibabaSDK.getService(LoginService.class);
				ma.onBackPressed();
				taobaoLoginService.showLogin(ma, new LoginCallback(){

					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub
						Log.i("taobao login", "fail");
						Toast.makeText(ma, "淘宝联合登录无效!", Toast.LENGTH_SHORT).show();
					}

					public void onSuccess(Session session) {
						// TODO Auto-generated method stub
						Log.i("taobao login", "success!"+session.getUser().nick+session.getUser().id);
					//	ma.loadRegisterFragment(2);
						StaticValueClass.currentBuyer.member_type=1;
						unitedLogin(1,session.getUser().id);
						new DownloadImageTask().execute(session.getUser().avatarUrl); 
						
					//	StaticValueClass.currentBuyer.tel=session.getUserId();
					//	StaticValueClass.currentBuyer.nickName=session.getUser().nick;
					//	StaticValueClass.currentBuyer.member_type=2;  //淘宝用户
					//	StaticValueClass.unitedLogin(StaticValueClass.currentBuyer, ma);
						
						Toast.makeText(ma, "淘宝联合登录成功!", Toast.LENGTH_LONG).show();
						
						
					} 
					
				}); 
			
			}
		});
        
        qqUnited.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			 	mTencent.login(UserLoginFragment.this, "", UserLoginFragment.this);
			}
		});
        
        weiboUnited.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ma.weiboAuth();
			}
		});
        
    	loginBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(buyerTel.getText().toString().compareTo("")==0 ||password.getText().toString().compareTo("")==0){
					Toast.makeText(ma, "请填写必要信息!", Toast.LENGTH_SHORT).show();
				}
				StaticValueClass.removeKeyboard(ma, v);
				StaticValueClass.currentBuyer.createId(1, buyerTel.getText().toString());
				loginIn(false);
				new DownloadImageTask().execute(StaticValueClass.serverAddress+"upload_headicon/"+buyerTel.getText().toString()+".jpg"); 
				
			}
		});
    	registerBuyer.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				StaticValueClass.currentBuyer.member_type=1;
				ma.loadRegisterFragment(1);
			}
		});
    	
    	forgetPassword.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ma.loadRegisterFragment(3);
			}
		});
    	
    	buyerTel.setOnFocusChangeListener(StaticValueClass.onFocusAutoClearHintListener);
    	password.setOnFocusChangeListener(StaticValueClass.onFocusAutoClearHintListener);
    	initParams(loginView);
    	 if(StaticValueClass.isAfterKitKat)
    		 loginView.setPadding(0, StaticValueClass.statusBarHeight, 0, 0);
    	return loginView;
    }
    
    private void initParams(View parent){
    	int scale=StaticValueClass.screenWidth;
    	
    	LinearLayout.LayoutParams params0=(LinearLayout.LayoutParams)loginBtn.getLayoutParams();
    	params0.width=scale*50/72;
    	params0.height=scale*9/72;
    	
    	LinearLayout linearLayout=(LinearLayout)parent.findViewById(R.id.linearLayout);
    	LinearLayout.LayoutParams params=(LinearLayout.LayoutParams)linearLayout.getLayoutParams();
    	params.leftMargin=params.rightMargin=scale*66/720;
    	GradientDrawable dividerDrawable2;//=new GradientDrawable();
		dividerDrawable2=(GradientDrawable)ma.getResources().getDrawable(R.drawable.vertical_divider2);
		dividerDrawable2.setSize(scale*47/720, 10);
		linearLayout.setDividerDrawable(dividerDrawable2); 
    	
    	LinearLayout.LayoutParams params1=(LinearLayout.LayoutParams)taobaoUnited.getLayoutParams();
    	LinearLayout.LayoutParams params2=(LinearLayout.LayoutParams)weixinUnited.getLayoutParams();
    	LinearLayout.LayoutParams params3=(LinearLayout.LayoutParams)weiboUnited.getLayoutParams();
    	LinearLayout.LayoutParams params4=(LinearLayout.LayoutParams)qqUnited.getLayoutParams();
    	params1.width=params1.height=scale*11/72;
    	params2.width=params2.height=scale*11/72;
    	params3.width=params3.height=scale*11/72;
    	params4.width=params4.height=scale*11/72;
    	//
    	Bitmap backmark=BitmapFactory.decodeResource(ma.getResources(), R.mipmap.expand_icon);
    	Drawable leftDrawable=new BitmapDrawable(StaticValueClass.getBackIcon(backmark));
    	leftDrawable.setBounds(0, 0, backmark.getWidth(), backmark.getHeight());
    //	backBtn.setCompoundDrawables(leftDrawable, null, null, null);
    	
    	backBtn.setBackground(leftDrawable);
    }
    
    public void loginIn(boolean justLoadData){
    	buyerLogin(justLoadData);
		getBonusRecords();
		getCoinRecords();
		getPurchaseRecords();
		getWelfareRecords();
		getConcernsData();
    }

    public void loginOut(){
    //	LoginService loginService = AlibabaSDK.getService(LoginService.class);
    	switch(StaticValueClass.currentBuyer.member_type){
    	case 0: //common
    	case 1: //taobao
    		if(taobaoLoginService==null)
    			taobaoLoginService=AlibabaSDK.getService(LoginService.class);
        	taobaoLoginService.logout(ma, new LogoutCallback(){

    			@Override
    			public void onFailure(int arg0, String arg1) {
    				// TODO Auto-generated method stub
    				Toast.makeText(ma, "淘宝登出失败", Toast.LENGTH_SHORT)
                    .show();
    			}

    			@Override
    			public void onSuccess() {
    				// TODO Auto-generated method stub
    				 Toast.makeText(ma, "淘宝登出成功", Toast.LENGTH_SHORT)
                     .show();
    			}
        		
        	});

    		break;
    	case 2:
    	}
    	    
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("DDDDDDDDD____onActivityCreated");
    }

    @Override
    public void onStart() {
    	Log.d("UserLoginFragment", "onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
    	Log.d("UserLoginFragment", "onResume");
        super.onResume();
     //   System.out.println("DDDDDDDDD____onResume");
    }

    @Override
    public void onPause() {
    	Log.d("UserLoginFragment", "onPause");
        super.onPause();
      //  System.out.println("DDDDDDDDD____onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        System.out.println("DDDDDDDDD____onStop");
    }

    @Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
		if(isVisibleToUser){
			Log.d("UserLogiFragment", "isVisible");
		} else Log.d("UserLogiFragment", "NotIsVisible");
	}
    
	@Override
    public void onDestroyView() {
        super.onDestroyView();
        System.out.println("DDDDDDDDD____onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("DDDDDDDDD____onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        System.out.println("DDDDDDDDD____onDetach");
    }
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		  if(requestCode == Constants.REQUEST_API) {
				if(resultCode == Constants.REQUEST_LOGIN) {
					mTencent.handleResultData(data, this);
				}
			    }
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private void clearInput(){
		buyerTel.setText("");
		password.setText("");
	}

    private void buyerLogin(final boolean justLoadData){
    	new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String situation,buyer,tpassword;
				if(justLoadData){
					situation="2";
					buyer=StaticValueClass.currentBuyer.tel;
					tpassword="";
				}else {
					//user login.
					situation="1";
					buyer=buyerTel.getText().toString();
					tpassword=password.getText().toString();
				}
				 ArrayList<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
				 nameValuePairs.add(new BasicNameValuePair("situation",situation));
	             nameValuePairs.add(new BasicNameValuePair("buyer",buyer));
	             nameValuePairs.add(new BasicNameValuePair("password",tpassword));
	           //   nameValuePairs.add(new BasicNameValuePair("mac",StaticValueClass.macAddr));
	              Looper.prepare();      
	              try{
	                   HttpClient httpclient = new DefaultHttpClient();
	                   HttpPost httppost = new HttpPost(StaticValueClass.serverAddress+"ch_buyer_login.php");
	                   httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,HTTP.UTF_8));
	                   HttpResponse response = httpclient.execute(httppost);
	                   Log.d("buyer_login", " recode:"+response.getStatusLine().getStatusCode());
	                   if(response.getStatusLine().getStatusCode()==200){
	                	   String   mResult=EntityUtils.toString(response.getEntity());
	                	   Log.d("buyer_login", "mResult:"+mResult);
	                	   if(mResult.compareTo("")==0){
	                		   Toast.makeText(ma, "密码有误!", Toast.LENGTH_LONG).show();
	                		   return;
	                	   }
	                	   JSONObject jsonObject;
	                	   JSONArray jsonArray = new JSONArray(mResult);
	                	  
	                	   for(int i=0;i<jsonArray.length();){
	                		   jsonObject=(JSONObject)jsonArray.opt(i);
	                		//   StaticValueClass.currentBuyer=new Buyer(jsonObject.getInt("member_type"),jsonObject.getString("buyer"),jsonObject.getString("password"),jsonObject.getInt("bonus"));
	                		   StaticValueClass.currentBuyer.password=jsonObject.getString("password");
	                		   StaticValueClass.currentBuyer.bonus=jsonObject.getInt("bonus");
	                		   StaticValueClass.currentBuyer.setSignInfo(jsonObject.getString("last_sign_date"), jsonObject.getInt("consecutive_sign_days"));
	                		   StaticValueClass.currentBuyer.setGameInfo(jsonObject.getString("slot_date"), jsonObject.getInt("slot_count"), jsonObject.getString("ninepane_date"));
	                		   StaticValueClass.currentBuyer.parseAddressData(jsonObject.getString("express_address"));
	                		   StaticValueClass.currentBuyer.setPersonInfo(jsonObject.getString("nick_name"), jsonObject.getString("personal_sign"));
	                		   break;
	                	   
	                	   }
	                		if(justLoadData) return;
	                	   ma.runOnUiThread(new Runnable(){

							@Override
							public void run() {
								// TODO Auto-generated method stub
								clearInput();
							//	ma.replaceFragment(4, ma.buyer_fragment);
								ma.toHomePage();
								ma.onBackPressed();
								ma.loginWithTel(StaticValueClass.currentBuyer.tel);
							}
	                		   
	                	   });
						
	                   }
	              } catch(JSONException e){
	            	  Toast.makeText(ma, "密码有误!", Toast.LENGTH_LONG).show();
	              }
	              catch(Exception e){
	                   Log.e("log_tag", "Error in http connection"+e.toString());
	                   Toast.makeText(ma, "网络异常，请稍后再试!", Toast.LENGTH_LONG).show();
	              }
				  
				  
			}
    		
    	}).start();
    	
    }
    private void unitedLogin(final int type,final String id){
    	
    	new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				 ArrayList<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
				 nameValuePairs.add(new BasicNameValuePair("member_type",""+type));
	             nameValuePairs.add(new BasicNameValuePair("id",id));
	          //   nameValuePairs.add(new BasicNameValuePair("mac",StaticValueClass.macAddr));
	              Looper.prepare();      
	              try{
	                   HttpClient httpclient = new DefaultHttpClient();
	                   HttpPost httppost = new HttpPost(StaticValueClass.serverAddress+"ch_united_login.php");
	                   httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,HTTP.UTF_8));
	                   HttpResponse response = httpclient.execute(httppost);
	                   Log.d("ch_united_login", " recode:"+response.getStatusLine().getStatusCode());
	                   if(response.getStatusLine().getStatusCode()==200){
	                	   String   mResult=EntityUtils.toString(response.getEntity());
	                	   Log.d("ch_united_login", "mResult:"+mResult);
	                	  Toast.makeText(ma,"mResult:"+mResult, Toast.LENGTH_LONG).show();
	                	   if(mResult.compareTo("no user")==0){
	                		   // no user to band id.
	                		 
	                		   StaticValueClass.currentBuyer.id=id;
	                		   StaticValueClass.currentBuyer.member_type=type;
	                		   ma.runOnUiThread(new Runnable(){

								@Override
								public void run() {
									// TODO Auto-generated method stub
									   ma.loadRegisterFragment(2);
								}
	                			   
	                		   });
	                		   return;
	                	   }
	                	   JSONObject jsonObject;
	                	   JSONArray jsonArray = new JSONArray(mResult);
	                	  
	                	   for(int i=0;i<jsonArray.length();){
	                		   jsonObject=(JSONObject)jsonArray.opt(i);
	                		//   StaticValueClass.currentBuyer=new Buyer(jsonObject.getInt("member_type"),jsonObject.getString("buyer"),jsonObject.getString("password"),jsonObject.getInt("bonus"));
	                		   StaticValueClass.currentBuyer.tel=jsonObject.getString("buyer");
	                		   StaticValueClass.currentBuyer.password=jsonObject.getString("password");
	                		   StaticValueClass.currentBuyer.bonus=jsonObject.getInt("bonus");
	                		   StaticValueClass.currentBuyer.setSignInfo(jsonObject.getString("last_sign_date"), jsonObject.getInt("consecutive_sign_days"));
	                		   StaticValueClass.currentBuyer.setGameInfo(jsonObject.getString("slot_date"), jsonObject.getInt("slot_count"), jsonObject.getString("ninepane_date"));
	                		   StaticValueClass.currentBuyer.parseAddressData(jsonObject.getString("express_address"));
	                		   break;
	                	   
	                	   }
	                		
	                	   ma.runOnUiThread(new Runnable(){

							@Override
							public void run() {
								// TODO Auto-generated method stub
							//	clearInput();
							//	ma.replaceFragment(4, ma.buyer_fragment);
							//	ma.toHomePage();
							//	ma.onBackPressed();
								ma.loginWithTel(StaticValueClass.currentBuyer.tel);
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
    
    private void getCoinRecords(){
    	StaticValueClass.currentBuyer.coinRecords.clear();
    	StaticValueClass.currentBuyer.coinRecords.add(new CoinRecord("签到",5,"2016-01-05"));
    	StaticValueClass.currentBuyer.coinRecords.add(new CoinRecord("购物",10,"2016-01-05"));
    	StaticValueClass.currentBuyer.coinRecords.add(new CoinRecord("欣赏广告",5,"2016-01-05"));
    	StaticValueClass.currentBuyer.coinRecords.add(new CoinRecord("分享",15,"2016-01-05"));
    	StaticValueClass.currentBuyer.coinRecords.add(new CoinRecord("完成积分任务",5,"2016-01-05"));
    	StaticValueClass.currentBuyer.coinRecords.add(new CoinRecord("签到",5,"2016-01-05"));
    	StaticValueClass.currentBuyer.coinRecords.add(new CoinRecord("购物",10,"2016-01-05"));
    	StaticValueClass.currentBuyer.coinRecords.add(new CoinRecord("欣赏广告",5,"2016-01-05"));
    	StaticValueClass.currentBuyer.coinRecords.add(new CoinRecord("分享",15,"2016-01-05"));
    	StaticValueClass.currentBuyer.coinRecords.add(new CoinRecord("完成积分任务",5,"2016-01-05"));
    }
    
    private void getPurchaseRecords(){
    	StaticValueClass.currentBuyer.purchaseRecords.add(new GameBonusRecord(1,"tempImages/img1.jpg","4K网络高清播放器增强版无线电视机顶盒",35.00f,"2016/03/16"));
    	StaticValueClass.currentBuyer.purchaseRecords.add(new GameBonusRecord(2,"tempImages/img2.jpg","化妆品专柜宣姿SPA09玫瑰水嫩精华霜",35.00f,"2016/03/16"));
    	StaticValueClass.currentBuyer.purchaseRecords.add(new GameBonusRecord(1,"tempImages/img3.jpg","苹果iPhone6s plus 移动联通电信版",3500.00f,"2016/03/16"));
    	StaticValueClass.currentBuyer.purchaseRecords.add(new GameBonusRecord(2,"tempImages/img4.jpg","爱国者移动电源超薄便携充电宝",75.00f,"2016/03/16"));
    	      
    
    
    }
    
    private void getWelfareRecords(){
     	StaticValueClass.currentBuyer.welfareRecords.add(new GameBonusRecord("tempImages/img1.jpg","4K网络高清播放器增强版无线电视机顶盒",35.00f,"2016/03/16","游戏中奖"));
    	StaticValueClass.currentBuyer.welfareRecords.add(new GameBonusRecord("tempImages/img2.jpg","化妆品专柜宣姿SPA09玫瑰水嫩精华霜",35.00f,"2016/03/16","金币兑换"));
    	StaticValueClass.currentBuyer.welfareRecords.add(new GameBonusRecord("tempImages/img4.jpg","爱国者移动电源超薄便携充电宝",75.00f,"2016/03/16","游戏中奖"));
    	StaticValueClass.currentBuyer.welfareRecords.add(new GameBonusRecord("tempImages/img1.jpg","4K网络高清播放器增强版无线电视机顶盒",35.00f,"2016/03/16","游戏中奖"));
    	StaticValueClass.currentBuyer.welfareRecords.add(new GameBonusRecord("tempImages/img2.jpg","化妆品专柜宣姿SPA09玫瑰水嫩精华霜",35.00f,"2016/03/16","金币兑换"));
    	StaticValueClass.currentBuyer.welfareRecords.add(new GameBonusRecord("tempImages/img4.jpg","爱国者移动电源超薄便携充电宝",75.00f,"2016/03/16","游戏中奖"));
    	
    }
    
    
    
    public void getConcernsData(){
    	 //get concerns image.
    	StaticValueClass.asynImageLoader.showImageAsyn(null, "tempImages/game_prize1.png", R.mipmap.blank_background);
    	StaticValueClass.asynImageLoader.showImageAsyn(null, "tempImages/game_prize2.png", R.mipmap.blank_background);
    	StaticValueClass.asynImageLoader.showImageAsyn(null, "tempImages/game_prize3.png", R.mipmap.blank_background);
    	StaticValueClass.asynImageLoader.showImageAsyn(null, "tempImages/game_prize4.png", R.mipmap.blank_background);
    	StaticValueClass.asynImageLoader.showImageAsyn(null, "tempImages/game_prize5.png", R.mipmap.blank_background);
    	StaticValueClass.asynImageLoader.showImageAsyn(null, "tempImages/game_prize6.png", R.mipmap.blank_background);
    	StaticValueClass.asynImageLoader.showImageAsyn(null, "tempImages/game_prize7.png", R.mipmap.blank_background);
    	
    }
    
    private void getBonusRecords(){
    	ma.clearGameBonusData();
    	new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				 ArrayList<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
				// nameValuePairs.add(new BasicNameValuePair("member_type","1"));
	             nameValuePairs.add(new BasicNameValuePair("buyer_number",StaticValueClass.currentBuyer.tel));
	           //   nameValuePairs.add(new BasicNameValuePair("mac",StaticValueClass.macAddr));
	              Looper.prepare();      
	              try{
	                   HttpClient httpclient = new DefaultHttpClient();
	                   HttpPost httppost = new HttpPost(StaticValueClass.serverAddress+"ch_getGameBonusRecord.php");
	                   httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,HTTP.UTF_8));
	                   HttpResponse response = httpclient.execute(httppost);
	                   Log.d("ch_getGameBonusRecord", " recode:"+response.getStatusLine().getStatusCode());
	                   if(response.getStatusLine().getStatusCode()==200){
	                	   String   mResult=EntityUtils.toString(response.getEntity());
	                	   Log.d("ch_getGameBonusRecord", "mResult:"+mResult);
	                	   if(mResult.compareTo("")==0){
	                		 //  Toast.makeText(ma, "密码有误!", Toast.LENGTH_LONG).show();
	                		   return;
	                	   }
	                	   JSONObject jsonObject;
	                	   JSONArray jsonArray = new JSONArray(mResult);
	                	  
	                	   StaticValueClass.currentBuyer.bonusRecords.clear();
	                	   for(int i=0;i<jsonArray.length();i++){
	                		   jsonObject=(JSONObject)jsonArray.opt(i);
	                		   GameBonusRecord record=new GameBonusRecord(jsonObject.getString("bonus_number"),jsonObject.getString("bonus_title"),
	                				   (Float.parseFloat(jsonObject.getString("bonus_price"))),jsonObject.getString("bonus_detail")
	                				   ,jsonObject.getString("bonus_obtain"),jsonObject.getInt("flag"));
	                		   record.loadBonuserAddressInfo(jsonObject.getString("bonuser_name"), jsonObject.getString("bonuser_tel")
	                				   , jsonObject.getString("bonuser_address"));
	                	//	   StaticValueClass.currentBuyer.bonusRecords.add(record);
	                	       ma.addNewGameBonusRecord(record,false);
	                	   }
	                	   
						
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
    
    
    
    private  class DownloadImageTask extends AsyncTask<String,Void,Drawable>{

		@Override
		protected Drawable doInBackground(String... urls) {
			// TODO Auto-generated method stub
			return loadImageFromNetwork(urls[0]);
		}
		
		public  Bitmap drawableToBitmap(Drawable drawable) {    
		       int width = drawable.getIntrinsicWidth();    
		       int height = drawable.getIntrinsicHeight();    
		       Bitmap bitmap = Bitmap.createBitmap(width, height, drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);    
		       Canvas canvas = new Canvas(bitmap);    
		       drawable.setBounds(0, 0, width, height);    
		       drawable.draw(canvas);    
		       return bitmap;    
		        
		    }    
		
		@Override
		protected void onPostExecute(Drawable result) {
			//imageView.setImageDrawable(result);
			if(result==null) return;
			ma.updateHeadIcon(result);
			Bitmap bmp = drawableToBitmap(result);
			 
			File SpicyDirectory = new File(ma.getExternalFilesDir("tt").getPath());
			SpicyDirectory.mkdirs();
			
			String filename=ma.getExternalFilesDir("tt").getPath()+"/head_icon.jpg";
			FileOutputStream out = null;
			try {
			out = new FileOutputStream(filename);
			bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
			} catch (Exception e) {
			e.printStackTrace();
			} finally {
			try {
			out.flush();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
			try {
			out.close();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
			out=null;
	     }
		 
	 }
		private Drawable loadImageFromNetwork(String imageUrl){
		 Drawable drawable = null;
		 try {
		  // 可以在这里通过文件名来判断，是否本地有此图片
		  drawable = Drawable.createFromStream(
		    new URL(imageUrl).openStream(), "image.jpg");
		 } catch (IOException e) {
		  Log.d("test", e.getMessage());
		 }
		 if (drawable == null) {
		  Log.d("test", "null drawable");
		 } else {
		  Log.d("test", "not null drawable");
		 }
		 
		 return drawable ;
		}

    }



	@Override
	public void onCancel() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onComplete(Object data) {
		// TODO Auto-generated method stub
		Log.d("***Tencent User log", "data:"+data.toString());
		
	}
	@Override
	public void onError(UiError arg0) {
		// TODO Auto-generated method stub
		
	}
}
