package com.way.betterdeal.fragments;

import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;
import com.way.betterdeal.view.TimeButton;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.Random;

public class MobileBandFragment extends Fragment {
	
	MainActivity ma;
	Button backBtn;
	TimeButton getCheckCodeBtn;
	EditText newMobileNumber,securityAnswer,chcekCode;
	View view;
	Random ran;
	String securityStr,checkCodeStr;
	public MobileBandFragment(){
		ran=new Random();
	}
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		ma=(MainActivity)this.getActivity();
		view=inflater.inflate(R.layout.mobile_band_fragment, container, false);
		newMobileNumber=(EditText)view.findViewById(R.id.newMobileNumber);
		securityAnswer=(EditText)view.findViewById(R.id.securityAnswer);
		chcekCode=(EditText)view.findViewById(R.id.chcekCode);
		backBtn=(Button)view.findViewById(R.id.backBtn);
		getCheckCodeBtn=(TimeButton) view.findViewById(R.id.getChcekCodeBtn);
		if(StaticValueClass.isAfterKitKat)
			view.setPadding(0, StaticValueClass.statusBarHeight, 0, 0);
		reMeasure();

		initFunction();
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		generateSecurityNum();
	}

	private void reMeasure(){
		Bitmap backmark=BitmapFactory.decodeResource(ma.getResources(), R.mipmap.expand_icon);
    	Drawable leftDrawable=new BitmapDrawable(StaticValueClass.getBackIcon(backmark));
    	leftDrawable.setBounds(0, 0, backmark.getWidth(), backmark.getHeight());
    	backBtn.setCompoundDrawables(leftDrawable, null, null, null);
    //	backBtn.setBackground(leftDrawable);
	}

	private void initFunction(){
		getCheckCodeBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (newMobileNumber.getText().toString().isEmpty()){
					Toast.makeText(ma,"请填入新号码！",Toast.LENGTH_SHORT).show();
					return;
				}
				checkTel();
				StaticValueClass.removeKeyboard(ma, v);
			}
		});
		view.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		backBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ma.onBackPressed();
			}
		});
	}

	private void generateSecurityNum(){
		int num1,num2;
		num1=ran.nextInt(9);
		num2=ran.nextInt(9);
		securityAnswer.setHint(""+num1+"+"+num2+"=?");
		securityStr=""+(num1+num2);
	}

	private void checkTel(){
		new Thread(new Runnable(){
			String   mResult;
			@Override
			public void run() {
				// TODO Auto-generated method stub
				ArrayList<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
				//	 nameValuePairs.add(new BasicNameValuePair("member_type","0"));
				nameValuePairs.add(new BasicNameValuePair("buyer",newMobileNumber.getText().toString()));
				Looper.prepare();
				try{
					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost(StaticValueClass.serverAddress+"ch_register_check.php");
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
					HttpResponse response = httpclient.execute(httppost);
					Log.d("ch_register_check", " recode:"+response.getStatusLine().getStatusCode());
					if(response.getStatusLine().getStatusCode()==200){
						mResult= EntityUtils.toString(response.getEntity());
						Log.d(StaticValueClass.logTag, "ch_register_check mResult:"+mResult);
						//generate new Buyer.
						ma.runOnUiThread(new Runnable(){
							@Override
							public void run() {
								// TODO Auto-generated method stub
								if(!mResult.equals("exist")){
									sendCheckCode();
								}else{
									Toast.makeText(ma, "号码已被绑定!", Toast.LENGTH_SHORT).show();
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

	private void sendCheckCode(){
		//security check.
		if (securityAnswer.getText().toString().compareTo(securityStr)!=0){
			Toast.makeText(ma,"安全问答有误！",Toast.LENGTH_SHORT).show();
			return;
		}
		// send check code.
		checkCodeStr=ran.nextInt(9)+""+ran.nextInt(9)+""+ran.nextInt(9)+""+ran.nextInt(9)+""+ran.nextInt(9)+""+ran.nextInt(9);
		sendingCheckCode(newMobileNumber.getText().toString(),checkCodeStr);
		getCheckCodeBtn.startTimer();

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

	private void reBindNumber(){
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				ArrayList<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("buyer",StaticValueClass.currentBuyer.tel));
				nameValuePairs.add(new BasicNameValuePair("new_number",newMobileNumber.getText().toString()));
				Looper.prepare();
				try{
					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost(StaticValueClass.serverAddress+"ch_rebinding.php");
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,HTTP.UTF_8));
					HttpResponse response = httpclient.execute(httppost);
					Log.d("ch_rebinding", " recode:"+response.getStatusLine().getStatusCode());
					if(response.getStatusLine().getStatusCode()==200){
						String   mResult=EntityUtils.toString(response.getEntity());
						Log.d("ch_rebinding", "mResult:"+mResult);
						ma.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(ma,"绑定成功！",Toast.LENGTH_SHORT).show();
								ma.onBackPressed();
								StaticValueClass.currentBuyer.rebindNumber(newMobileNumber.getText().toString());
							}
						});

					}
				}catch(Exception e){
					Log.e("log_tag", "Error in http connection"+e.toString());

				}


			}

		}).start();
	}
	

}
