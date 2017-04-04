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

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class ShareController extends Dialog {

	Context ct;

	public static final String APP_ID="wxe8ffa219f8bac45b";
	private IWXAPI api;
	private String tentcentAppID="1104771725";
	private String shareTitle,shareSubTitle,shareWebLink,shareTitlePicLink;
	public Tencent mTencent;
	
	//MainActivity mm;
	View shareView;
	
	boolean registerWeiXin=false;
	int WX_THUMB_SIZE = 120;
	ImageButton weixinBtn,weixinFriendsBtn;

	public ShareController(Context ma) {
		super(ma);
		// TODO Auto-generated constructor stub
		ct=ma;
		regToWx();
		initTencent();
		
	}

	/**
	 * 自定义主题及布局的构造方法
	 * @param context
	 * @param theme
	 * @param resLayout
	 */
	public ShareController(Context ma, int theme){
		super(ma, theme);
		ct=ma;
	//	this.context = context;
		regToWx();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		shareView=((Activity)ct).getLayoutInflater().inflate(R.layout.share_fragment , null);
		this.getWindow().setGravity(Gravity.BOTTOM);
		this.setContentView(shareView);
		weixinBtn=(ImageButton)shareView.findViewById(R.id.weixinBtn);
		weixinFriendsBtn=(ImageButton)shareView.findViewById(R.id.weixinFriendsBtn);
		weixinBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				weixinShareInfo(0);
				ShareController.this.dismiss();
			}
		});

		weixinFriendsBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				weixinShareInfo(1);
				ShareController.this.dismiss();
			}
		});


	}

	private void regToWx(){
		api=WXAPIFactory.createWXAPI(ct, APP_ID, true);
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

}

	public void weixinShareInfo(final int flag){
		new Thread(new Runnable() {
			@Override
			public void run() {
				WXWebpageObject webpage = new WXWebpageObject();
				webpage.webpageUrl = shareWebLink;
				WXMediaMessage msg = new WXMediaMessage(webpage);
				msg.title =shareTitle;// "WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long";
				msg.description = shareSubTitle;//" Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description Very Long Very Long Very Long Very Long Very Long Very Long Very Long";

				Bitmap bmp,thumb;
				if (shareTitlePicLink.equals("")){
					bmp =  BitmapFactory.decodeResource(ct.getResources(), R.mipmap.app_icon);
					thumb = Bitmap.createScaledBitmap(bmp, WX_THUMB_SIZE, WX_THUMB_SIZE, true);
					bmp.recycle();
				}	else{
					bmp=PicUtil.getbitmap(shareTitlePicLink);
					float bt=(float)bmp.getHeight()/(float)bmp.getWidth();
					Log.i("bitmap ","height:"+bmp.getHeight());
					Log.i("bitmap ","width:"+bmp.getWidth());
					Log.i("bitmap ","bt:"+bt);
					int height=(int)(WX_THUMB_SIZE*bt);
					Log.i("bitmap ","new  height:"+height);
					thumb = Bitmap.createScaledBitmap(bmp, WX_THUMB_SIZE, height, true);

				}
				msg.thumbData = Util.bmpToByteArray(thumb, true);
				SendMessageToWX.Req req = new SendMessageToWX.Req();
				req.transaction = buildTransaction("webpage");
				req.message = msg;
				req.scene =flag;// isTimelineCb.isChecked() ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
				api.sendReq(req);
			}
		}).start();


	}

	public void setShareInfo(String s1,String s2,String l1,String l2){
		if (s1==null || s1.equals(""))
			shareTitle="我的分享";
		else shareTitle=s1;
		if (s2==null || s2.equals(""))
			shareSubTitle="";
		else shareSubTitle=s2;
		if(l1!=null && !l1.equals(""))
			shareTitlePicLink=l1;
		else  shareTitlePicLink="";
		if (l2==null || l2.equals(""))
			shareWebLink=StaticValueClass.serverAddress+"BetterDeal/share_page.php?sharer="+"13581675438";
		else shareWebLink=l2;

	}
  public void weixinShare(String str,int flag){
	  
	    regToWx();
		sendMsgToWeiXin(str,flag);
		ShareController.this.dismiss();
  }
  private void initTencent(){
	  mTencent = Tencent.createInstance(tentcentAppID, ct);
  }
	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
	}
  

}
