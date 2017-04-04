package com.way.betterdeal.fragments;

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
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
	boolean  specialPath=false;
	Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
		//	super.handleMessage(msg);
			switch (msg.what){
				case 1:
					clearInput();
					if (!specialPath)
						ma.toHomePage();
					else specialPath = false;
					ma.onBackPressed();
					ma.loginWithTel(StaticValueClass.currentBuyer.tel);
					break;
				case 2:
					ma.onBackPressed();
					ma.loadRegisterFragment(2,specialPath);
					break;
				default:
			}
		}
	};
	public UserLoginFragment(){
	//	ma=(MainActivity)this.getActivity();

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
		mTencent = Tencent.createInstance(StaticValueClass.tentcentAppID, this.getContext());
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
				taobaoLoginService.showLogin(ma, new LoginCallback(){

					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub
						Log.d(StaticValueClass.logTag,"taobao login error!");
						Toast.makeText(ma, "淘宝联合登录无效!", Toast.LENGTH_SHORT).show();
					}

					public void onSuccess(Session session) {
						// TODO Auto-generated method stub
						Log.d(StaticValueClass.logTag,"taobao login successful!:"+session.getUser().id);
					//	ma.loadRegisterFragment(2);
						StaticValueClass.currentBuyer.member_type=1;
						unitedLogin(1,session.getUser().id);
					//	new DownloadImageTask().execute(session.getUser().avatarUrl);
					//	StaticValueClass.currentBuyer.tel=session.getUserId();
					//	StaticValueClass.currentBuyer.nickName=session.getUser().nick;
					//	StaticValueClass.currentBuyer.member_type=2;  //淘宝用户
					//	StaticValueClass.unitedLogin(StaticValueClass.currentBuyer, ma);
						
						Toast.makeText(ma, "淘宝联合登录成功!", Toast.LENGTH_SHORT).show();
						
						
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
				loginIn(ma,false);
				new DownloadImageTask().execute(StaticValueClass.serverAddress+"upload_headicon/"+buyerTel.getText().toString()+".jpg"); 
				
			}
		});
    	registerBuyer.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				StaticValueClass.currentBuyer.member_type=1;
				ma.onBackPressed();
				ma.loadRegisterFragment(1,specialPath);
			}
		});
    	
    	forgetPassword.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ma.onBackPressed();
				ma.loadRegisterFragment(3,specialPath);
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
		parent.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
		parent.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return true;
			}
		});
    }

	public void setSpecialPath(boolean flag){
		specialPath=flag;
	}
    
    public void loginIn(MainActivity mm ,boolean justLoadData){
    	buyerLogin(justLoadData);
		getBonusRecords(mm);
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
	              try {
					  HttpClient httpclient = new DefaultHttpClient();
					  HttpPost httppost = new HttpPost(StaticValueClass.serverAddress + "ch_buyer_login.php");
					  httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
					  HttpResponse response = httpclient.execute(httppost);
					  Log.d("buyer_login", " recode:" + response.getStatusLine().getStatusCode());
					  if (response.getStatusLine().getStatusCode() == 200) {
						  String mResult = EntityUtils.toString(response.getEntity(),HTTP.UTF_8);
						  Log.d("buyer_login", "mResult:" + mResult);
						  JSONObject jsonResult=new JSONObject(mResult);
						  String status=jsonResult.getString("status");
						  if (status.equals("0")){
							  String rawData= jsonResult.getString("result");
							  rawData=rawData.replace("\\\"","\"");
							  Log.d("buyer_login", "rawData:" + rawData);
							  JSONObject jsonData=new JSONObject(rawData);
							  if (situation.equals("2") ||jsonData.getString("password").equals(tpassword)){
								  StaticValueClass.currentBuyer.bonus=jsonData.getInt("bonus");
								  StaticValueClass.currentBuyer.setSignInfo(jsonData.getString("last_sign_date"), jsonData.getInt("consecutive_sign_days"));
								  StaticValueClass.currentBuyer.setGameInfo(jsonData.getString("slot_date"), jsonData.getInt("slot_count"), jsonData.getString("ninepane_date"));
								  StaticValueClass.currentBuyer.parseAddressData(jsonData.getString("express_address"));
								  StaticValueClass.currentBuyer.setPersonInfo(jsonData.getString("nick_name"), jsonData.getString("personal_sign"),jsonData.getString("birthday"),jsonData.getString("address"),jsonData.getString("sex"));

							   }else {
								  ma.runOnUiThread(new Runnable() {
									  @Override
									  public void run() {
										  Toast.makeText(ma, "密码有误！", Toast.LENGTH_SHORT).show();
									  }
								  });
								  return;
							  }


						  }else {
							  ma.runOnUiThread(new Runnable() {
								  @Override
								  public void run() {
									  Toast.makeText(ma, "用户不存在!", Toast.LENGTH_SHORT).show();
								  }
							  });
							  return;
						  }
						  if (justLoadData) return;
						  StaticValueClass.currentBuyer.setNeedUpdate(true);
						  Message message=new Message();
						  message.what=1;
						  handler.sendMessage(message);
						  /*
						  ma.runOnUiThread(new Runnable() {

							  @Override
							  public void run() {
								  // TODO Auto-generated method stub
								  clearInput();
								  if (!specialPath)
									  ma.toHomePage();
								  else specialPath = false;
								  ma.onBackPressed();
								  ma.loginWithTel(StaticValueClass.currentBuyer.tel);
							  }

						  });  */
					  }
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
	              Looper.prepare();      
	              try{
	                   HttpClient httpclient = new DefaultHttpClient();
	                   HttpPost httppost = new HttpPost(StaticValueClass.serverAddress+"ch_united_login.php");
	                   httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,HTTP.UTF_8));
	                   HttpResponse response = httpclient.execute(httppost);
					  Log.d(StaticValueClass.logTag,"ch_united_login statusCode:"+response.getStatusLine().getStatusCode());
	                   if(response.getStatusLine().getStatusCode()==200){
	                	   String   mResult=EntityUtils.toString(response.getEntity());
	                	   Log.d(StaticValueClass.logTag, "ch_united_login:mResult:"+mResult);
						   JSONObject jsonResult=new JSONObject(mResult);
						   String status=jsonResult.getString("status");
						   if (status.equals("0")){
							   String rawData= jsonResult.getString("result");
							   rawData=rawData.replace("\\\"","\"");
							   Log.d("buyer_login", "rawData:" + rawData);
							   JSONObject jsonData=new JSONObject(rawData);
							       StaticValueClass.currentBuyer.tel=jsonData.getString("buyer");
								   StaticValueClass.currentBuyer.bonus=jsonData.getInt("bonus");
								   StaticValueClass.currentBuyer.setSignInfo(jsonData.getString("last_sign_date"), jsonData.getInt("consecutive_sign_days"));
								   StaticValueClass.currentBuyer.setGameInfo(jsonData.getString("slot_date"), jsonData.getInt("slot_count"), jsonData.getString("ninepane_date"));
								   StaticValueClass.currentBuyer.parseAddressData(jsonData.getString("express_address"));
								   StaticValueClass.currentBuyer.setPersonInfo(jsonData.getString("nick_name"), jsonData.getString("personal_sign"),jsonData.getString("birthday"),jsonData.getString("address"),jsonData.getString("sex"));
							       StaticValueClass.currentBuyer.setNeedUpdate(true);
							   // concerns view.
							   ma.runOnUiThread(new Runnable(){

								   @Override
								   public void run() {
									   // TODO Auto-generated method stub
									   if(!specialPath)
										   ma.toHomePage();
									   else specialPath=false;
									   ma.onBackPressed();
									   ma.loginWithTel(StaticValueClass.currentBuyer.tel);
								   }

							   });
						   }else {
							   // no user to band id.
							   StaticValueClass.currentBuyer.id=id;
							   StaticValueClass.currentBuyer.member_type=type;
							   Message message=new Message();
							   message.what=2;
							   handler.sendMessage(message);
							   /*
							   ma.runOnUiThread(new Runnable(){

								   @Override
								   public void run() {
									   // TODO Auto-generated method stub
									   ma.onBackPressed();
									   ma.loadRegisterFragment(2,specialPath);
								   }

							   }); */
							   return;
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
    
    private void getBonusRecords( MainActivity mm){
		if (ma==null) ma=mm;
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
				if (out!=null) {
					bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
					out.flush();
					out.close();
					out=null;
				}

			} catch (Exception e) {
			e.printStackTrace();
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
