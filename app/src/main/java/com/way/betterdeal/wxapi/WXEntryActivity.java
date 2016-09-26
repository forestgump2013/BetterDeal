package com.way.betterdeal.wxapi;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.way.betterdeal.StaticValueClass;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
	
	public static final String APP_ID="wxe8ffa219f8bac45b";
	private IWXAPI api;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Toast.makeText(this, "WXEntryActivity", Toast.LENGTH_LONG).show();
		/*
		regToWx();
		Intent intent=this.getIntent();
		intent.getIntExtra("flag", 2);
		int flag=intent.getIntExtra("flag", 2);
		if(flag==2) this.finish();
		sendMsgToWeiXin("test",flag);
		*/
		api = WXAPIFactory.createWXAPI(this, APP_ID, false);
		api.handleIntent(getIntent(), this);
	}

	@Override
	public void onReq(BaseReq arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onResp(BaseResp resp) {
		// TODO Auto-generated method stub
	//	String result;
		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:
		//	result = R.string.errcode_success;
			if(StaticValueClass.ma.shareToPlayGame()) break;
			StaticValueClass.ma.returnFromWeixin();
			
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
		//	result = R.string.errcode_cancel;
			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
		//	result = R.string.errcode_deny;
			break;
		default:
		//	result = R.string.errcode_unknown;
			break;
		}
		/*
		 Intent intent = new Intent();
         //把返回数据存入Intent
         intent.putExtra("result", ""+resp.errCode);
         //设置返回数据
         WXEntryActivity.this.setResult(RESULT_OK, intent);
         //关闭Activity
         */
         WXEntryActivity.this.finish();
	}
	


}
