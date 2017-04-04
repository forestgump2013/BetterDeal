package com.way.betterdeal.fragments;
//.com.way.betterdeal.fragments.RegisterFragment.SmsReciver

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

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

/*
import com.taobao.api.ApiException;
import com.taobao.api.Constants;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
*/
import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;
import com.way.betterdeal.object.Buyer;
import com.way.betterdeal.view.TimeButton;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.telephony.SmsMessage;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:wangjie@cyyun.com
 * Date: 13-6-14
 * Time: 下午2:39
 */
public class RegisterFragment extends Fragment{
	MainActivity ma;
	View registerView;
	RelativeLayout protocalView;
	Button commitBtn,backBtn;
	TimeButton getCheckCodeBtn;
	EditText buyerTel,checkCode,password,checkPassword,securityCode,inviteCode;
    TextView title,protocalText;
	String imagePath,mAlbumPicturePath,checkCodeStr,securityStr;
	File imageFile;
	Uri imageUri,tmpImageUri;
	int direct;
	boolean specialPath=false;
	//常量定义
	public static final int TAKE_A_PICTURE = 10;
	public static final int SELECT_A_PICTURE = 20;
	public static final int SET_PICTURE = 30;
	public static final int SET_ALBUM_PICTURE_KITKAT = 40;
	public static final int SELECET_A_PICTURE_AFTER_KIKAT = 50;
	
	//版本比较：是否是4.4及以上版本
	final boolean mIsKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
	private SmsObserver smsObserver;
	Random ran;

	public RegisterFragment(){

		imagePath=Environment.getExternalStorageDirectory().getPath() +"/head_icon.jpg";
		imageUri=Uri.parse("file://"+imagePath);
		tmpImageUri=Uri.parse("file://"+Environment.getExternalStorageDirectory().getPath()+"/temp_head_icon.jpg");
		 ran=new Random();

	}
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        System.out.println("CCCCCCCCCC____onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("CCCCCCCCCC____onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("CCCCCCCCCC____onCreateView");
		ma=(MainActivity)this.getActivity();
		smsObserver = new SmsObserver(this.getContext(), smsHandler);
		this.getActivity(). getContentResolver().registerContentObserver(SMS_INBOX, true,
				smsObserver);
		//...
		registerView=inflater.inflate(R.layout.register_layout , container, false);
        title=(TextView)registerView.findViewById(R.id.titleText);
		protocalText=(TextView)registerView.findViewById(R.id.protocalText);
        buyerTel=(EditText)registerView.findViewById(R.id.buyerTel);
        checkCode=(EditText)registerView.findViewById(R.id.checkCode);
        password=(EditText)registerView.findViewById(R.id.password);
        checkPassword=(EditText)registerView.findViewById(R.id.checkPassword);
		securityCode=(EditText)registerView.findViewById(R.id.securityCode);
        inviteCode=(EditText)registerView.findViewById(R.id.inviteCode);
        backBtn=(Button)registerView.findViewById(R.id.backBtn);
        commitBtn=(Button)registerView.findViewById(R.id.commitBtn);
        getCheckCodeBtn=(TimeButton)registerView.findViewById(R.id.getCheckCodeBtn);
        protocalView=(RelativeLayout)registerView.findViewById(R.id.protocalView);
        backBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ma.onBackPressed();
			}
		});
        getCheckCodeBtn.setLenght(60*1000);
        
        getCheckCodeBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(buyerTel.getText().toString().compareTo("")==0 ){
					Toast.makeText(ma, "请填写电话号码!", Toast.LENGTH_SHORT).show();
					return;
				}

				switch(direct){
					case 1://register new buyer
						checkTel();
						break;
					case 2://security band tel.
						sendCheckCode();
						break;
					case 3://set password.
						sendCheckCode();
				}
				StaticValueClass.removeKeyboard(ma, v);
			}
		});
        
        commitBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				StaticValueClass.removeKeyboard(ma, v);

				switch(direct){
				case 1://register new buyer
					commitRegisterInfo();
					break;
				case 2://security band tel.
					unitedBandRegister();
					break;
				case 3://set password.
					reSetPassword();
				}
			
			}
		});
        
        initParams();
        registerView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
        InputFilter[] filterArray=new InputFilter[1];
		filterArray[0]=new InputFilter.LengthFilter(11);
		buyerTel.setFilters(filterArray);
        buyerTel.setOnFocusChangeListener(StaticValueClass.onFocusAutoClearHintListener);
        checkCode.setOnFocusChangeListener(StaticValueClass.onFocusAutoClearHintListener);
        password.setOnFocusChangeListener(StaticValueClass.onFocusAutoClearHintListener);
        checkPassword.setOnFocusChangeListener(StaticValueClass.onFocusAutoClearHintListener);
		securityCode.setOnFocusChangeListener(StaticValueClass.onFocusAutoClearHintListener);
        inviteCode.setOnFocusChangeListener(StaticValueClass.onFocusAutoClearHintListener);
		protocalText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ma.loadProtocolFragment();
			}
		});
        if(StaticValueClass.isAfterKitKat)
        	registerView.setPadding(0, StaticValueClass.statusBarHeight, 0, 0);
        return registerView;
    }

	public  void setSpecialPath(boolean f){
		specialPath=f;
	}
    
    private void commitRegisterInfo(){
    	//check info.
    	if(buyerTel.getText().toString().compareTo("")==0 ||password.getText().toString().compareTo("")==0){
			Toast.makeText(ma, "请填写必要信息!", Toast.LENGTH_LONG).show();
			return;
		}
    	
    	if(buyerTel.getText().toString().length()<11){
    		Toast.makeText(ma, "请输入有效电话号码!", Toast.LENGTH_LONG).show();
			return;
    	}

		if(!checkCode.getText().toString().equals(checkCodeStr)){
			Toast.makeText(ma, "验证码错误!", Toast.LENGTH_LONG).show();
			return;
		}
		if(password.getText().toString().compareTo(checkPassword.getText().toString())!=0){
			Toast.makeText(ma, "密码校验有误!", Toast.LENGTH_LONG).show();
			return;
		}
		if(password.getText().toString().length()<6){
			Toast.makeText(ma, "密码最少6位!", Toast.LENGTH_LONG).show();
			return;
		}
		 commitInfo();
    }
    
    private void sendingCheckCode(final String tel,final String code){
    	
    	new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
			   ArrayList<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
		       nameValuePairs.add(new BasicNameValuePair("tel",tel));
		       nameValuePairs.add(new BasicNameValuePair("checkCode",code));
	           Looper.prepare();      
	              try{
	                   HttpClient httpclient = new DefaultHttpClient();
	                   HttpPost httppost = new HttpPost(StaticValueClass.serverAddress+"alidayu_sdk_php/BetterDealSmsSend.php");
	                   httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,HTTP.UTF_8));
	                   HttpResponse response = httpclient.execute(httppost);
	                   Log.d("BetterDealSmsSend", " recode:"+response.getStatusLine().getStatusCode());
	                   if(response.getStatusLine().getStatusCode()==200){
	                //	   Toast.makeText(context, "提交成功,请等待接收！", Toast.LENGTH_LONG).show();
	                	   String   mResult=EntityUtils.toString(response.getEntity());
	                	   Log.d("BetterDealSmsSend", "mResult:"+mResult);
	                	   //generate new Buyer.
						
	                   }
	              }catch(Exception e){
	                   Log.e("log_tag", "Error in http connection"+e.toString());
	               
	              }
				  
				  
			}
    		
    	}).start();
    }

	private void sendCheckCode(){
		//security check.
		if (securityCode.getText().toString().compareTo(securityStr)!=0){
			Toast.makeText(ma,"安全问答有误！",Toast.LENGTH_SHORT).show();
			return;
		}
		// send check code.
		checkCodeStr=ran.nextInt(9)+""+ran.nextInt(9)+""+ran.nextInt(9)+""+ran.nextInt(9)+""+ran.nextInt(9)+""+ran.nextInt(9);
		sendingCheckCode(buyerTel.getText().toString(),checkCodeStr);
		getCheckCodeBtn.startTimer();

	}
    
    private void checkTel(){
    	new Thread(new Runnable(){
			String   mResult;
			@Override
			public void run() {
				// TODO Auto-generated method stub
				 ArrayList<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
			//	 nameValuePairs.add(new BasicNameValuePair("member_type","0"));
	              nameValuePairs.add(new BasicNameValuePair("buyer",buyerTel.getText().toString()));
	           //   nameValuePairs.add(new BasicNameValuePair("nick_name",buyerTel.getText().toString()));
	          //    nameValuePairs.add(new BasicNameValuePair("last_sign_date",StaticValueClass.dateFormat.format(new Date())));
	          //    nameValuePairs.add(new BasicNameValuePair("personal_sign",""));
	              Looper.prepare();      
	              try{
	                   HttpClient httpclient = new DefaultHttpClient();
	                   HttpPost httppost = new HttpPost(StaticValueClass.serverAddress+"ch_register_check.php");
	                   httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,HTTP.UTF_8));
	                   HttpResponse response = httpclient.execute(httppost);
	                   Log.d("ch_register_check", " recode:"+response.getStatusLine().getStatusCode());
	                   if(response.getStatusLine().getStatusCode()==200){
						   mResult=EntityUtils.toString(response.getEntity());
	                	   Log.d(StaticValueClass.logTag, "ch_register_check mResult:"+mResult);
	                	   //generate new Buyer.
						   ma.runOnUiThread(new Runnable(){
							   @Override
							   public void run() {
								   // TODO Auto-generated method stub
								   if(!mResult.equals("exist")){
									   sendCheckCode();
								   }else{
									   Toast.makeText(ma, "号码已被注册!", Toast.LENGTH_SHORT).show();
								   }
							   }
						   });
	                   }
	              }catch(Exception e){
	                   Log.e("log_tag", "Error in http connection"+e.toString());
	               
	              }
				  
				  
			}
    		
    	}).start();
    }
    

    
    private void commitInfo(){
		//commit info.
    	new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				 ArrayList<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
				 nameValuePairs.add(new BasicNameValuePair("flag","1"));
				 nameValuePairs.add(new BasicNameValuePair("member_type","0"));
	              nameValuePairs.add(new BasicNameValuePair("buyer",buyerTel.getText().toString()));
	              nameValuePairs.add(new BasicNameValuePair("nick_name",buyerTel.getText().toString()));
	              nameValuePairs.add(new BasicNameValuePair("password",password.getText().toString()));
	              nameValuePairs.add(new BasicNameValuePair("last_sign_date",StaticValueClass.dateFormat.format(new Date())));
	              nameValuePairs.add(new BasicNameValuePair("personal_sign",""));
	              Looper.prepare();      
	              try{
	                   HttpClient httpclient = new DefaultHttpClient();
	                   HttpPost httppost = new HttpPost(StaticValueClass.serverAddress+"ch_buyer_register.php");
	                   httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,HTTP.UTF_8));
	                   HttpResponse response = httpclient.execute(httppost);
	                   Log.d("buyer_register", " recode:"+response.getStatusLine().getStatusCode());
	                   if(response.getStatusLine().getStatusCode()==200){
	                //	   Toast.makeText(context, "提交成功,请等待接收！", Toast.LENGTH_LONG).show();
	                	   String   mResult=EntityUtils.toString(response.getEntity());
	                	   Log.d("buyer_register", "mResult:"+mResult);
	                	   //generate new Buyer.
	                	   Buyer newBuyer=new Buyer(0,buyerTel.getText().toString(),password.getText().toString(),100);
	       			    	newBuyer.setSignInfo(StaticValueClass.dateFormat.format(new Date()), 1);
	       			    	StaticValueClass.currentBuyer=newBuyer;
	       				
	                	   ma.runOnUiThread(new Runnable(){

							@Override
							public void run() {
								// TODO Auto-generated method stub
								clearInputData();
								ma.loginWithTel(buyerTel.getText().toString());
								ma.onBackPressed();
								if (!specialPath)
									ma.toHomePage();
								else  {
									specialPath=false;
								//	ma.onBackPressed();
								}
							}
	                		   
	                	   });
						
	                   }
	              }catch(Exception e){
	                   Log.e("log_tag", "Error in http connection"+e.toString());
	                   Toast.makeText(ma, "网络异常，请稍后再试!", Toast.LENGTH_LONG).show();
	              }
				  
				  
			}
    		
    	}).start();
    }
    
    private void initParams(){
    	LinearLayout.LayoutParams params=(LinearLayout.LayoutParams)this.commitBtn.getLayoutParams();
    	params.height=StaticValueClass.screenWidth/8;
    	params.width=StaticValueClass.screenWidth*50/72;
    	
    	//
    	Bitmap backmark=BitmapFactory.decodeResource(ma.getResources(), R.mipmap.expand_icon);
    	Drawable leftDrawable=new BitmapDrawable(StaticValueClass.getBackIcon(backmark));
    	leftDrawable.setBounds(0, 0, backmark.getWidth(), backmark.getHeight());
    //	backBtn.setCompoundDrawables(leftDrawable, null, null, null);
    	backBtn.setBackground(leftDrawable);
    }
    
    private void clearInputData(){
		password.setText("");
		checkPassword.setText("");
		buyerTel.setText("");
		checkCode.setText("");
		securityCode.setText("");
    }
    
    private void unitedBandRegister(){
    	//check info.
    	if(buyerTel.getText().toString().compareTo("")==0 ){
			Toast.makeText(ma, "请填写必要信息!", Toast.LENGTH_SHORT).show();
			return;
		}
    	// check tel_check

		if(!checkCode.getText().toString().equals(checkCodeStr)){
			Toast.makeText(ma, "验证码错误!", Toast.LENGTH_SHORT).show();
			return;
		}
		// stop timer.
		getCheckCodeBtn.clearTimer();
    	//commit info
	//	Toast.makeText(ma, "ready commit band info!", Toast.LENGTH_SHORT).show();
    	new Thread(new Runnable(){
			String   mResult;
			@Override
			public void run() {
				// TODO Auto-generated method stub
				 ArrayList<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
				 nameValuePairs.add(new BasicNameValuePair("member_type",""+StaticValueClass.currentBuyer.member_type));
				 nameValuePairs.add(new BasicNameValuePair("buyer",buyerTel.getText().toString()));
				 nameValuePairs.add(new BasicNameValuePair("id",StaticValueClass.currentBuyer.id));
	           //   nameValuePairs.add(new BasicNameValuePair("last_sign_date",one.last_sign_date));
	              Looper.prepare();      
	              try{
	                   HttpClient httpclient = new DefaultHttpClient();
					   String url=StaticValueClass.serverAddress+"ch_united_binding.php";
					  Log.i(StaticValueClass.logTag, "ch_united_band url:"+url);
	                   HttpPost httppost = new HttpPost(url);
	                   httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,HTTP.UTF_8));
	                   HttpResponse response = httpclient.execute(httppost);
	                   Log.i(StaticValueClass.logTag, "ch_united_band recode:"+response.getStatusLine().getStatusCode());
	                   if(response.getStatusLine().getStatusCode()==200){
	                	    mResult=EntityUtils.toString(response.getEntity());
	                	   Log.i(StaticValueClass.logTag, "ch_united_band mResult:"+mResult);
	                	   ma.runOnUiThread(new Runnable(){

							@Override
							public void run() {
								// TODO Auto-generated method stub
						//		Toast.makeText(ma, "信息已提交！ :"+mResult, Toast.LENGTH_SHORT).show();
								if(mResult.equals("banded")){
									   Toast.makeText(ma, "号码已绑定！", Toast.LENGTH_SHORT).show();
								    	return;
								}
								ma.loginWithTel(buyerTel.getText().toString());
								ma.onBackPressed();
								if(!specialPath)
									ma.toHomePage();
								else specialPath=false;
							}
	                		   
	                	   });
						
	                   }
	              }catch(Exception e){
	                   Log.i("log_tag", "Error in http connection"+e.toString());
	                 //  Toast.makeText(ma, "网络异常，请稍后再试!", Toast.LENGTH_LONG).show();
	              }
				  
				  
			}
    		
    	}).start();
    }
    
    
    private void reSetPassword(){

		//check info.
		if(buyerTel.getText().toString().compareTo("")==0 ||password.getText().toString().compareTo("")==0){
			Toast.makeText(ma, "请填写必要信息!", Toast.LENGTH_LONG).show();
			return;
		}

		if(buyerTel.getText().toString().length()<11){
			Toast.makeText(ma, "请输入有效电话号码!", Toast.LENGTH_LONG).show();
			return;
		}

		if(!checkCode.getText().toString().equals(checkCodeStr)){
			Toast.makeText(ma, "验证码错误!", Toast.LENGTH_LONG).show();
			return;
		}

		//password.getText().toString().contentEquals(checkPassword.getText().toString())
		if(password.getText().toString().compareTo(checkPassword.getText().toString())!=0){
			Toast.makeText(ma, "密码校验有误!", Toast.LENGTH_LONG).show();
			return;
		}
		if(password.getText().toString().length()<6){
			Toast.makeText(ma, "密码最少6位!", Toast.LENGTH_LONG).show();
			return;
		}
    	
    	new Thread(new Runnable(){
    		 String   mResult;
			@Override
			public void run() {
				// TODO Auto-generated method stub
				 ArrayList<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
				 nameValuePairs.add(new BasicNameValuePair("flag","2"));
			//	 nameValuePairs.add(new BasicNameValuePair("member_type","0"));
	              nameValuePairs.add(new BasicNameValuePair("buyer",buyerTel.getText().toString()));
	        //      nameValuePairs.add(new BasicNameValuePair("nick_name",buyerTel.getText().toString()));
	              nameValuePairs.add(new BasicNameValuePair("password",password.getText().toString()));
	        //      nameValuePairs.add(new BasicNameValuePair("last_sign_date",StaticValueClass.dateFormat.format(new Date())));
	        //      nameValuePairs.add(new BasicNameValuePair("personal_sign",""));
	              Looper.prepare();      
	              try{
	                   HttpClient httpclient = new DefaultHttpClient();
	                   HttpPost httppost = new HttpPost(StaticValueClass.serverAddress+"ch_buyer_register.php");
	                   httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,HTTP.UTF_8));
	                   HttpResponse response = httpclient.execute(httppost);
	                   Log.d("buyer reset password", " recode:"+response.getStatusLine().getStatusCode());
	                   if(response.getStatusLine().getStatusCode()==200){
	                	    mResult=EntityUtils.toString(response.getEntity());
	                	   Log.d("buyer reset password", "mResult:"+mResult);
	                	   //generate new Buyer.
	                	   Buyer newBuyer=new Buyer(0,buyerTel.getText().toString(),password.getText().toString(),100);
	       			    	newBuyer.setSignInfo(StaticValueClass.dateFormat.format(new Date()), 1);
	       			    	StaticValueClass.currentBuyer=newBuyer;
	       				
	                	   ma.runOnUiThread(new Runnable(){

							@Override
							public void run() {
								// TODO Auto-generated method stub
							   if(mResult.equals("用户不存在")){
								   Toast.makeText(ma, "用户不存在！", Toast.LENGTH_SHORT).show();
							   }else{
								   //. get data.
								   JSONObject jsonObject;
			                	   JSONArray jsonArray;
								try {
									jsonArray = new JSONArray(mResult);
									 for(int i=0;i<jsonArray.length();){
				                		   jsonObject=(JSONObject)jsonArray.opt(i);
				                	//	   StaticValueClass.currentBuyer.password=jsonObject.getString("password");
				                		   StaticValueClass.currentBuyer.bonus=jsonObject.getInt("bonus");
				                		   StaticValueClass.currentBuyer.setSignInfo(jsonObject.getString("last_sign_date"), jsonObject.getInt("consecutive_sign_days"));
				                		   StaticValueClass.currentBuyer.setGameInfo(jsonObject.getString("slot_date"), jsonObject.getInt("slot_count"), jsonObject.getString("ninepane_date"));
				                		   StaticValueClass.currentBuyer.parseAddressData(jsonObject.getString("express_address"));
				                		   StaticValueClass.currentBuyer.setPersonInfo(jsonObject.getString("nick_name"), jsonObject.getString("personal_sign"),jsonObject.getString("birthday"),jsonObject.getString("address"),jsonObject.getString("sex"));
				                		   break;
				                	   
				                	   }
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							   //.
							   clearInputData();
								ma.loginWithTel(buyerTel.getText().toString());
								ma.onBackPressed();
								if (!specialPath)
									   ma.toHomePage();
								   else {
									specialPath=false;
								}
							   }
								
							}
	                		   
	                	   });
						
	                   }
	              }catch(Exception e){
	                   Log.e("log_tag", "Error in http connection"+e.toString());
	                   Toast.makeText(ma, "网络异常，请稍后再试!", Toast.LENGTH_LONG).show();
	              }
				  
				  
			}
    		
    	}).start();
    	
    }
    
    
    public void setDirect(int d){
    	direct=d;
    }
    private void directView(){
		password.setText("");
		checkPassword.setText("");
	//	buyerTel.setText("");
		checkCode.setText("");
		securityCode.setText("");
    	switch(direct){
    	case 1: //register.
    		title.setText("注册");
    		password.setVisibility(View.VISIBLE);
    		checkPassword.setVisibility(View.VISIBLE);
    		protocalView.setVisibility(View.VISIBLE);
    		break;
    	case 2: //band tel.
    		title.setText("安全绑定");
    		password.setVisibility(View.GONE);
    		checkPassword.setVisibility(View.GONE);
    		commitBtn.setText("提交信息");
    		protocalView.setVisibility(View.GONE);
    		break;
    	case 3: //set password.
    		title.setText("密码重置");
    		commitBtn.setText("提交信息");
    		protocalView.setVisibility(View.GONE);
    		break;
    	}
		int num1,num2;
		num1=ran.nextInt(9);
		num2=ran.nextInt(9);
		securityCode.setHint(""+num1+"+"+num2+"=?");
		securityStr=""+(num1+num2);

    }

	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("CCCCCCCCCC____onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("CCCCCCCCCC____onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        directView();
        System.out.println("CCCCCCCCCC____onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        getCheckCodeBtn.clearTimer();
        System.out.println("CCCCCCCCCC____onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        System.out.println("CCCCCCCCCC____onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.out.println("CCCCCCCCCC____onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("CCCCCCCCCC____onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        System.out.println("CCCCCCCCCC____onDetach");
    }
    
    public Handler smsHandler = new Handler() {  
        //这里可以进行回调的操作  
        //TODO  
  
    };  
    
    class SmsObserver extends ContentObserver {  
    	  
        public SmsObserver(Context context, Handler handler) {  
            super(handler);  
        }  
  
        @Override  
        public void onChange(boolean selfChange) {  
            super.onChange(selfChange);  
            //每当有新短信到来时，使用我们获取短消息的方法  
            Log.d("***new msg", "is coming!");
            getSmsFromPhone();  
        }  
    }  
    
    private Uri SMS_INBOX = Uri.parse("content://sms/");  
    public void getSmsFromPhone() {  
        ContentResolver cr = ma.getContentResolver();  
        String[] projection = new String[] { "body" };//"_id", "address", "person",, "date", "type  
        String where = " date > "  //body like '剁手联盟' and
                + (System.currentTimeMillis() - 10 * 60 * 1000);  
        Cursor cur = cr.query(SMS_INBOX, projection, where, null, "date desc");  
        if (null == cur)  
            return;  
        if (cur.moveToNext()) {  
         //   String number = cur.getString(cur.getColumnIndex("address"));//手机号  
         //   String name = cur.getString(cur.getColumnIndex("person"));//联系人姓名列表  
            String body = cur.getString(cur.getColumnIndex("body"));  
            Log.d("***new msg", "body:"+body);
            //这里我是要获取自己短信服务号码中的验证码~~  
            if (body.contains(checkCodeStr)) {  
                checkCode.setText(checkCodeStr);
                getCheckCodeBtn.clearTimer();
                return;
            }  
            /*
            Pattern pattern = Pattern.compile(" [a-zA-Z0-9]{10}");  
            Matcher matcher = pattern.matcher(body);  
            if (matcher.find()) {  
                String res = matcher.group().substring(1, 11);  
                mobileText.setText(res);  
            }  
            */
        }  
    } 
    
    
    
   /*
    public class SmsReciver extends BroadcastReceiver {  
    	  
        @Override  
        public void onReceive(Context context, Intent intent) {  
            Bundle bundle = intent.getExtras();  
            SmsMessage msg = null;  
            if (null != bundle) {  
                Object[] smsObj = (Object[]) bundle.get("pdus");  
                for (Object object : smsObj) {  
                    msg = SmsMessage.createFromPdu((byte[]) object);  
            //    Date date = new Date(msg.getTimestampMillis());//时间  
             //       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
            //        String receiveTime = format.format(date);  
                    String msgBody=msg.getDisplayMessageBody();
                    //在这里写自己的逻辑  
                    Log.d("*** receive msg", msgBody);
                    if (msgBody.contains(checkCodeStr)) {  
                        checkCode.setText(checkCodeStr);
                          
                    }  
                      
                }  
            }  
        }  
      
    }  */

}
