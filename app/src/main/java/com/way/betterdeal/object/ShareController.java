package com.way.betterdeal.object;

//import android.R;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;

import android.app.Dialog;
import android.view.View;
import android.widget.ImageButton;

public class ShareController extends Dialog {
	
	public static final String APP_ID="wxe8ffa219f8bac45b";
	private IWXAPI api;
	private String tentcentAppID="1104771725";
	
	public Tencent mTencent;
	
	MainActivity mm;
	View shareView;
	
	boolean registerWeiXin=false;
	
	ImageButton weixinBtn,weixinFriendsBtn;

	public ShareController(MainActivity ma) {
		super(ma);
		// TODO Auto-generated constructor stub
		mm=ma;
		shareView=ma.getLayoutInflater().inflate(R.layout.share_fragment , null);
		this.setTitle("好友分享");
		this.setContentView(shareView);
		weixinBtn=(ImageButton)shareView.findViewById(R.id.weixinBtn);
		weixinFriendsBtn=(ImageButton)shareView.findViewById(R.id.weixinFriendsBtn);
		weixinBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			//	startWeiXinCallBackActivity(0);
				regToWx();
				sendMsgToWeiXin("分享 好友 👌",0);
				ShareController.this.dismiss();
			}
		});
		weixinFriendsBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			//	startWeiXinCallBackActivity(1);
				regToWx();
				sendMsgToWeiXin("分享 朋友圈，👬",1);
				ShareController.this.dismiss();
			}
		});
		
		initTencent();
		
	}
	
	private void regToWx(){
		api=WXAPIFactory.createWXAPI(mm, APP_ID, true);
		api.registerApp(APP_ID);
		
}
	private void sendMsgToWeiXin(String text,int flag){
	//flag 0:one freind  1:friend circle
	WXWebpageObject webpage = new WXWebpageObject();  
    webpage.webpageUrl = StaticValueClass.serverAddress+"BetterDeal/share_page.php?sharer="+"13581675438";  
    WXMediaMessage msg = new WXMediaMessage(webpage);  
  
    msg.title = "title";  
    msg.description = text;
 //   Bitmap thumb = BitmapFactory.decodeResource(getResources(),  
  //          R.drawable.weixin_share);  
 //   msg.setThumbImage(thumb);  
    SendMessageToWX.Req req = new SendMessageToWX.Req();  
    req.transaction = String.valueOf(System.currentTimeMillis());  
    req.message = msg;  
    req.scene = flag;  
    api.sendReq(req);  
	/*
	WXTextObject textObj=new WXTextObject();
	textObj.text=text;
	
	WXWebpageObject webpage = new WXWebpageObject();  
    webpage.webpageUrl = "这里填写链接url";
	
	
	WXMediaMessage msg=new WXMediaMessage(webpage);
	msg.title="title";
//	msg.mediaObject=textObj;
	msg.description=text;
	
	SendMessageToWX.Req reg=new SendMessageToWX.Req();
	reg.transaction=String.valueOf(System.currentTimeMillis());
	reg.message=msg;
	reg.scene=0;
	api.sendReq(reg);
	*/
}
  public void weixinShare(String str,int flag){
	  
	    regToWx();
		sendMsgToWeiXin(str,flag);
		ShareController.this.dismiss();
  }
  private void initTencent(){
	  mTencent = Tencent.createInstance(tentcentAppID, mm);
  }
  

}
