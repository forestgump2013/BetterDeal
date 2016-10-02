package com.way.betterdeal;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

//import com.taobao.api.TaobaoClient;
import com.way.betterdeal.object.AsynImageLoader;
import com.way.betterdeal.object.Buyer;
import com.way.betterdeal.object.CategoryCell;
import com.way.betterdeal.object.CoinRecord;
import com.way.betterdeal.object.Commodity;
import com.way.betterdeal.object.GameBonusRecord;
import com.way.betterdeal.object.PicUtil;
import com.way.betterdeal.object.PurchaseRecord;

public  class StaticValueClass {
	public static Activity firstActiviy;
	public static boolean logined=false,firstUseed=false;
    public static String cProvience,cCity,cDistrict;
	public static int screenWidth,screenHeight,screenDensity;
	public static float density,scaleDensity;
	public static String logTag="BetterDeal";
    public static final boolean isAfterKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    public static int statusBarHeight;
	public static Typeface hanYiThinFont,huangKangFont;
	public static AsynImageLoader asynImageLoader;
	public static ArrayList<Commodity> betterCommodities=new ArrayList<Commodity>();
	public static ArrayList<Commodity> cheapCommodities=new ArrayList<Commodity>();
	public static ArrayList<PurchaseRecord> purchaseRecords=new  ArrayList<PurchaseRecord>();
	
	public static ArrayList<Bitmap> ninePanesImage=new ArrayList<Bitmap>();
	public static ArrayList<Bitmap> darkNinePanesImage =new ArrayList<Bitmap>();
	public static ArrayList<GameBonusRecord> ninePanesPrizes=new  ArrayList<GameBonusRecord>();
	public static ArrayList<GameBonusRecord> slotPrizes=new  ArrayList<GameBonusRecord>();
	public static String[] firstLevelCategory={"美食天地","服装饰品","鞋品箱包","美妆护理","家居生活"
		,"母婴玩具","户外车品","数码家电","营养保健"};
	public static String categoryRawData="V,1美食,V,1休闲零食,V,1巧克力.jpg,2坚果.jpg,3干果蜜饯.jpg,4牛肉干.jpg,5果冻布丁.jpg,6肉脯.jpg,7红枣.jpg,8膨化食品.jpg,9烤鱼片.jpg,10糖果.jpg,11鸭脖.jpg,A,2饼干糕点,V,1肉松饼.jpg,2麻薯.jpg,3马卡龙.jpg,4松塔.jpg,5蛋卷.jpg,6凤梨酥.jpg,7饼干.jpg,8面包蛋糕.jpg,A,3营养速食,V,1牛奶.jpg,2火腿肠.jpg,3蜂蜜.jpg,4柚子茶.jpg,5麦片谷物.jpg,6方便食品.jpg,A,4酒水饮料,V,1茶叶.jpg,2饮料.jpg,3酒水.jpg,A,5生鲜食品,V,1海鲜水产.jpg,2肉禽蛋奶.jpg,3水果蔬菜.jpg,A,6进口食品,V,1休闲零食.jpg,2酒水饮料.jpg,3粮油调味.jpg,A,A,2女装,V,1潮流,V,1裙装.jpg,2上衣.jpg,3衬衫.jpg,4卫衣.jpg,5打底.jpg,6T恤.jpg,7外套.jpg,8小西装.jpg,9长裤.jpg,10短裤.jpg,A,2内衣,V,1文胸.jpg,2内裤.jpg,3袜子.jpg,4塑身美体.jpg,5家居服.jpg,A,3配饰,V,1腰带.jpg,2围巾.jpg,3帽子.jpg,4太阳镜.jpg,5饰品.jpg,A,A,3鞋品,V,1女鞋,V,1休闲鞋.jpg,2高跟鞋.jpg,3平底鞋.jpg,4帆布鞋.jpg,5凉鞋.jpg,6凉拖.jpg,A,2男鞋,V,1休闲鞋.jpg,2皮鞋.jpg,3板鞋.jpg,4帆布鞋.jpg,5豆豆鞋.jpg,6人字拖.jpg,A,A,4包包,V,1时尚女包,V,1单肩包.jpg,2手提包.jpg,3双肩包.jpg,4钱包手包.jpg,A,2精品男包,V,1商务包.jpg,2休闲包.jpg,3钱包手包.jpg,A,3功能箱包,V,1旅行包.jpg,2拉杆箱.jpg,3电脑包.jpg,4登山包.jpg,5腰包.jpg,A,A,5男装,V,1型男,V,1衬衫.jpg,2T恤.jpg,3卫衣.jpg,4西服.jpg,5西裤.jpg,6牛仔裤.jpg,7休闲裤.jpg,A,2内衣,V,1内裤.jpg,2袜子.jpg,A,3装饰,V,1领带.jpg,2腰带.jpg,3太阳镜.jpg,4帽子.jpg,A,A,6美妆护理,V,1面部护理,V,1洁面.jpg,2面膜.jpg,3爽肤水.jpg,4乳液.jpg,5面霜.jpg,6防晒霜.jpg,7眼霜.jpg,8精华.jpg,A,2彩妆香水,V,1BB霜.jpg,2粉底.jpg,3腮红.jpg,4眼部.jpg,5唇部.jpg,6美甲.jpg,7香水.jpg,8工具.jpg,A,3身体护理,V,1洗护发水.jpg,2沐浴露.jpg,3身体乳.jpg,4精油.jpg,5护手霜.jpg,6脱毛膏.jpg,A,4男士专区,V,1洁面.jpg,2面霜乳液.jpg,3爽肤水.jpg,4剃须.jpg,5香水.jpg,A,A,7居家日用,V,1家纺布艺,V,1床品套件.jpg,2床垫.jpg,3春秋被.jpg,4枕头.jpg,5毛巾浴巾.jpg,6抱枕靠垫.jpg,7地垫.jpg,8居家拖鞋.jpg,A,2日用百货,V,1收纳.jpg,2浴室小件.jpg,3清洁工具.jpg,4创意家居.jpg,5烹饪用品.jpg,6水杯.jpg,7钟表.jpg,8衣架.jpg,9小摆件.jpg,10园艺.jpg,11除味.jpg,A,3清洁用品,V,1抽纸.jpg,2湿巾.jpg,3卫生纸.jpg,A,4宠物生活,V,1水族.jpg,2猫狗粮.jpg,3日用.jpg,A,A,8母婴玩具,V,1宝宝专区,V,1奶粉.jpg,2营养辅食.jpg,3纸尿裤.jpg,4喂养用品.jpg,5清洁洗护.jpg,6童车.jpg,A,2玩具系列,V,1遥控电动.jpg,2积木.jpg,3毛绒.jpg,4早教智能.jpg,5爬行垫.jpg,6模型.jpg,A,3孕妈专区,V,1孕妇装.jpg,2哺乳内衣.jpg,3待产包.jpg,4妈咪包.jpg,A,4童装童鞋,V,1上衣.jpg,2裤子.jpg,3裙子.jpg,4内衣.jpg,5童鞋.jpg,A,A,9户外车品,V,1运动,V,1运动鞋.jpg,2运动套装.jpg,3球类.jpg,A,2用品,V,1帐篷.jpg,2烧烤炉.jpg,3睡袋.jpg,4望远镜.jpg,5垂钓.jpg,A,3车品,V,1记录仪.jpg,2车贴.jpg,3头枕腰靠.jpg,4挂饰摆饰.jpg,5胎压监测.jpg,6车用香水.jpg,A,A,10数码,V,1手机电脑,V,1智能手机.jpg,2平板.jpg,3笔记本.jpg,A,2手机配件,V,1充电宝.jpg,2保护膜.jpg,3手机壳.jpg,4自拍杆.jpg,5耳机.jpg,6支架.jpg,A,3电脑周边,V,1路由器.jpg,2U盘.jpg,3鼠标.jpg,4键盘.jpg,5摄像头.jpg,A,A,11家电,V,1生活家电,V,1空气净化器.jpg,2加湿器.jpg,3饮水机.jpg,4电风扇.jpg,5空调扇.jpg,6电熨斗.jpg,A,2厨房电器,V,1电水壶.jpg,2电饭煲.jpg,3微波炉.jpg,4煮蛋器.jpg,5电热饭盒.jpg,6酸奶机.jpg,7空气炸锅.jpg,8电饼铛.jpg,9榨汁料理.jpg,10电蒸锅.jpg,11豆浆机.jpg,12电磁炉.jpg,A,3大家电,V,1电视.jpg,2洗衣机.jpg,3冰箱.jpg,4空调.jpg,5家庭影院.jpg,A,A,12营养保健,V,1成人用品,V,1避孕.jpg,2润滑.jpg,3测孕.jpg,4器具.jpg,5情趣内衣.jpg,A,2保健器械,V,1体重秤.jpg,2按摩器.jpg,3体温计.jpg,4血压计.jpg,5血糖仪.jpg,6足浴盆.jpg,A,3营养滋补,V,1维生素.jpg,2蛋白质.jpg,3阿胶.jpg,4枸杞.jpg,5玛咖.jpg,6冬虫夏草.jpg,A,A,A,";
			
	public static Map<Integer,Object> categoryGroups=new HashMap<Integer,Object>(); 
	public static ArrayList<CategoryCell> firstLevelCategoryCells=new ArrayList<CategoryCell>();
	public static Buyer currentBuyer=new Buyer();
	public static Commodity currentCommodity;
	//public static TaobaoClient taoBaoClient;
	public static String taoBaoAppKey="23206958";
	public static String taoBaoAppSecret="1f001a07624af8b985b837f855d48bfb";
	public static String tentcentAppID="1104771725";
	public static String weiboAppKey="3707828940";
	public static  String weiboREDIRECT_URL = "http://www.sina.com";// 应用的回调页 
	public static  String weiboSCOPE = "email,direct_messages_read,direct_messages_write,"
	+ "friendships_groups_read,friendships_groups_write,statuses_to_me_read," + "follow_app_official_microblog," + "invitation_write";
	// 应用申请的高级权限
	public static SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd");
	public static String today =StaticValueClass.dateFormat.format(new Date());
	public static MainActivity ma;
	public static boolean networkState;
	public static int gameBonusMount=50;
	//concerned net connections.
	public static String serverAddress="http://www.qcygl.com/";
	public static HttpParams httpParameters;
	private int timeoutConnection = 3000;  
	private int timeoutSocket = 5000;  
	//public static String headIconPath
	public static void initNetConnect(){
		httpParameters = new BasicHttpParams();// Set the timeout in milliseconds until a connection is established.  
	    HttpConnectionParams.setConnectionTimeout(httpParameters, 3000);// Set the default socket timeout (SO_TIMEOUT) // in milliseconds which is the timeout for waiting for data.  
	    HttpConnectionParams.setSoTimeout(httpParameters,5000); 
	}
	
	public static Bitmap rotateBitmap(Bitmap bitmap, int angle){
		 int width = bitmap.getWidth();
	     int height = bitmap.getHeight();
	     Matrix matrix=new Matrix();
	     matrix.postRotate(angle);
	     
	     Bitmap newBitmap=Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
	     return newBitmap;
	}
	
	public static Bitmap getBackIcon(Bitmap rawIcon){
		//获取这个图片的宽和高
        int width = rawIcon.getWidth();
        int height = rawIcon.getHeight();
        
        //定义预转换成的图片的宽度和高度
   //     int newWidth = width;
   //     int newHeight = height;
        
        //计算缩放率，新尺寸除原始尺寸
   //     float scaleWidth = ((float) newWidth) / width;
    //    float scaleHeight = ((float) newHeight) / height;
        
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        
        // 缩放图片动作
    //    matrix.postScale(scaleWidth, scaleHeight);
        
        //旋转图片 动作
        matrix.postRotate(180);
        
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(rawIcon, 0, 0,
        width, height, matrix, true);
        
        //将上面创建的Bitmap转换成Drawable对象，使得其可以使用在ImageView, ImageButton中
      //  BitmapDrawable bmd = new BitmapDrawable(resizedBitmap);
        return resizedBitmap;
	}
	
	public static Bitmap getDarkImage(Bitmap bitmap){
		
		Bitmap bmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),  
                Config.ARGB_8888);  
        int brightness = -90;  
        ColorMatrix cMatrix = new ColorMatrix();  
        cMatrix.set(new float[] { 1, 0, 0, 0, brightness, 0, 1,  
                0, 0, brightness,// 改变亮度  
                0, 0, 1, 0, brightness, 0, 0, 0, 1, 0 });  

        Paint paint = new Paint();  
        paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));  

        Canvas canvas = new Canvas(bmp);  
        // 在Canvas上绘制一个已经存在的Bitmap。这样，dstBitmap就和srcBitmap一摸一样了  
        canvas.drawBitmap(bitmap, 0, 0, paint);  
        return bmp;
		
	}
	public static long getDateDifference(String date1,String date2){
	    	try{
	    	Date d1=dateFormat.parse(date1);
	    	Date d2=dateFormat.parse(date2);
	    	long diff=d1.getTime()-d2.getTime();
	    	long diffDays=diff/(1000*3600*24);
	    	Log.d("getDateDifference", date1+"~"+date2+diffDays);
	    	return diffDays;
	    	}catch(Exception e){
	    		e.printStackTrace();
	    		return -1;
	    	}
	    	
	    	
	    }
	 public static void removeKeyboard(Activity aa,View v){
		 
		 InputMethodManager imm=(InputMethodManager)aa.getSystemService(Context.INPUT_METHOD_SERVICE);
		 imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
	 }
	 
	 
	 public static void updateBuyerInfo(final Context ct,final Buyer buyer){
		 new Thread(new Runnable(){
			 	String result;
				@Override
				public void run() {
					// TODO Auto-generated method stub
					 ArrayList<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
					 nameValuePairs.add(new BasicNameValuePair("member_type",""+buyer.member_type));
		              nameValuePairs.add(new BasicNameValuePair("buyer",buyer.tel));
		              nameValuePairs.add(new BasicNameValuePair("consecutive_sign_days",""+buyer.consecutive_sign_days));
		              nameValuePairs.add(new BasicNameValuePair("last_sign_date",""+buyer.last_sign_date));
		              nameValuePairs.add(new BasicNameValuePair("bonus",""+buyer.bonus));
		              Looper.prepare();      
		              try{
		                   HttpClient httpclient = new DefaultHttpClient();
		                   HttpPost httppost = new HttpPost("http://www.qcygl.com/update_buyer_info.php");
		                   httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,HTTP.UTF_8));
		                   HttpResponse response = httpclient.execute(httppost);
		                   Log.d("update_buyer_info", " recode:"+response.getStatusLine().getStatusCode());
		                   if(response.getStatusLine().getStatusCode()==200){
		                	   result=EntityUtils.toString(response.getEntity());
		                	   Log.d("updateBuyerInfo****", "mResult:"+result);
		                	   
		                	   }
		          
		              }catch(Exception e){
		                   Log.e("log_tag", "Error in http connection"+e.toString());
		                   Toast.makeText(ct, "网络异常，请稍后再试!", Toast.LENGTH_LONG).show();
		              }
					  
					  
				}
	    		
	    	}).start();
		 
		 
	 }
	 
	 public static void unitedLogin(final Buyer buyer,final MainActivity ma){
		 new Thread(new Runnable(){
			 	String result;
				@Override
				public void run() {
					// TODO Auto-generated method stub
					 ArrayList<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
					 nameValuePairs.add(new BasicNameValuePair("member_type",""+buyer.member_type));
		              nameValuePairs.add(new BasicNameValuePair("buyer",buyer.tel));
		              Looper.prepare();      
		              try{
		                   HttpClient httpclient = new DefaultHttpClient();
		                   HttpPost httppost = new HttpPost("http://www.qcygl.com/united_login.php");
		                   httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,HTTP.UTF_8));
		                   HttpResponse response = httpclient.execute(httppost);
		                   Log.d("unitedLogin", " recode:"+response.getStatusLine().getStatusCode());
		                   if(response.getStatusLine().getStatusCode()==200){
		                	   result=EntityUtils.toString(response.getEntity());
		                	   Log.d("unitedLogin****", "mResult:"+result);
		                	  if(result.compareTo("")==0){
		                		  // new buyer.
		                		  buyer.bonus=100;
		                	  }else{
		                		  //the buyer has been registered.
			                	   JSONObject jsonObject;
			                	   JSONArray jsonArray = new JSONArray(result);
			                	  
			                	   for(int i=0;i<jsonArray.length();){
			                		   jsonObject=(JSONObject)jsonArray.opt(i);
			                		  // StaticValueClass.currentBuyer=new Buyer(1,jsonObject.getString("buyer"),jsonObject.getString("password"),jsonObject.getInt("bonus"));
			                		   buyer.bonus=jsonObject.getInt("bonus");
			                		   buyer.setSignInfo(jsonObject.getString("last_sign_date"), jsonObject.getInt("consecutive_sign_days"));
			                		   break;
			                	   
			                	   }
		                	  }
		                	   ma.runOnUiThread(new Runnable(){

								@Override
								public void run() {
									// TODO Auto-generated method stub
							//		ma.replaceFragment(4, ma.buyer_fragment);
								}
		                		   
		                	   });
		                	   
		                	   }
		          
		              }catch(Exception e){
		                   Log.e("log_tag", "Error in http connection"+e.toString());
		              //     Toast.makeText(ma, "网络异常，请稍后再试!", Toast.LENGTH_LONG).show();
		              }
					  
					  
				}
	    		
	    	}).start();
		 
	 }
	 
	 public static void uploadHeadIcon(final String uploadUrl,final Bitmap bitmap,final String iconName)  
	  {  
		  Log.d("upload file","start");
		  new Thread(new Runnable(){

	//		@Override
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String end = "\r\n";  
			    String twoHyphens = "--";  
			    String boundary = "******";  
			    try  
			    {  
			      URL url = new URL(uploadUrl);  
			      HttpURLConnection httpURLConnection = (HttpURLConnection) url  
			          .openConnection();  
			      // 设置每次传输的流大小，可以有效防止手机因为内存不足崩溃   
			      // 此方法用于在预先不知道内容长度时启用没有进行内部缓冲的 HTTP 请求正文的流。   
			   //   httpURLConnection.setChunkedStreamingMode(128 * 1024);// 128K   
			      // 允许输入输出流   
			      httpURLConnection.setDoInput(true);  
			      httpURLConnection.setDoOutput(true);  
			      httpURLConnection.setUseCaches(false);  
			      // 使用POST方法   
			      httpURLConnection.setRequestMethod("POST");  
			      httpURLConnection.setRequestProperty("Connection", "Keep-Alive");  
			      httpURLConnection.setRequestProperty("Charset", "UTF-8");  
			      httpURLConnection.setRequestProperty("Content-Type",  
			          "multipart/form-data;boundary=" + boundary);  
			  
			      DataOutputStream dos = new DataOutputStream(  
			          httpURLConnection.getOutputStream());  
			      dos.writeBytes(twoHyphens + boundary + end);  
			      dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\"; filename=\""  
			          + iconName  //"headicon.jpg" 
			          + "\""  
			          + end);  
			      dos.writeBytes("Content-Type:image/pjpeg" + end);
			      dos.writeBytes(end);  
			  
			  //    FileInputStream fis = new FileInputStream(srcPath);  
			      
			      Bitmap bmpCompressed = Bitmap.createScaledBitmap(bitmap, 150, 150, true); 

			      ByteArrayOutputStream bos = new ByteArrayOutputStream(); 

			      // CompressFormat set up to JPG, you can change to PNG or whatever you want; 

			      bmpCompressed.compress(CompressFormat.JPEG, 100, bos); 

			      byte[] data = bos.toByteArray(); 
			      Log.d("upload file","start bitmap data"+data.length);
			      /*
			      byte[] buffer = new byte[8192]; // 8k   
			      int count = 0;  
			      // 读取文件   
			      while ((count = fis.read(buffer)) != -1)  
			      {  
			        dos.write(buffer, 0, count);  
			      }  
			      fis.close();  
			     */
			      dos.write(data);
			      dos.writeBytes(end);  
			      dos.writeBytes(twoHyphens + boundary + twoHyphens + end);  
			      dos.flush();  
			      Log.d("upload file","finish!!!");
			      InputStream is = httpURLConnection.getInputStream();  
			      InputStreamReader isr = new InputStreamReader(is, "utf-8");  
			      BufferedReader br = new BufferedReader(isr);  
			      String result="",str;
			      while( (str=br.readLine())!=null){
			    	  result+=str;
			      }
			      Log.d("upload result", "result1:"+result);
			//      Toast.makeText(this, result, Toast.LENGTH_LONG).show();  
			      is.close();  
			      
			      dos.close();  
			      
			  
			    } catch (Exception e)  
			    {  
			      e.printStackTrace();  
			   //   setTitle(e.getMessage());  
			    }  
				
				
				
				
			}
			  
		  }).start();
		  
	  }  
	 
	 public static void getNinePanesBackground(){
	    	
	    	StaticValueClass.ninePanesImage.clear();
	    	StaticValueClass.darkNinePanesImage.clear();
	    	
	    	new Thread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					//ninepane1
					String url="tempImages/game_prize",imgUrl;
					for(int i=1;i<=7;i++){
						imgUrl=url+i+".png";
						Bitmap bitmap=PicUtil.getbitmap(imgUrl);
						StaticValueClass.ninePanesImage.add(bitmap);
			    		Bitmap darkBit=StaticValueClass.getDarkImage(bitmap);
			    		StaticValueClass.darkNinePanesImage.add(darkBit);
					}
					/*
		    		synchronized(StaticValueClass.ninePanesImage){
		    			StaticValueClass.ninePanesImage.notify();
		    		} */
					
				}
	    		
	    	}).start();
	    }
	 public static OnFocusChangeListener onFocusAutoClearHintListener = new OnFocusChangeListener() {
		 @Override
		 public void onFocusChange(View v, boolean hasFocus) {
		 EditText textView = (EditText) v;
		 String hint;
		 if (hasFocus) {
		 hint = textView.getHint().toString();
		 textView.setTag(hint);
		 textView.setHint("");
		 } else {
			 hint = textView.getTag().toString();
			 textView.setHint(hint);
		 }
		 }
		 };
		 /** 
	         * 将px值转换为dip或dp值，保证尺寸大小不变 
	         *  
	         * @param pxValue 
	         * @param scale 
	         *            （DisplayMetrics类中属性density） 
	         * @return 
	         */  
	        public static int px2dip(Context context, float pxValue) {  
	            final float scale = context.getResources().getDisplayMetrics().density;  
	            return (int) (pxValue / scale + 0.5f);  
	        }  
	      
        /** 
         * 将dip或dp值转换为px值，保证尺寸大小不变 
         *  
         * @param dipValue 
         * @param scale 
         *            （DisplayMetrics类中属性density） 
         * @return 
         */  
        public static int dip2px(Context context, float dipValue) {  
            final float scale = context.getResources().getDisplayMetrics().density;  
            return (int) (dipValue * scale + 0.5f);  
        }  
	      
	        /** 
	         * 将px值转换为sp值，保证文字大小不变 
	         *  
	         * @param pxValue 
	         * @param fontScale 
	         *            （DisplayMetrics类中属性scaledDensity） 
	         * @return 
	         */  
	        public static int px2sp(Context context, float pxValue) {  
	            final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;  
	            return (int) (pxValue / fontScale + 0.5f);  
	        }  
	      
    /** 
     * 将sp值转换为px值，保证文字大小不变 
     *  
     * @param spValue 
     * @param fontScale 
     *            （DisplayMetrics类中属性scaledDensity） 
     * @return 
     */  
    public static int sp2px(Context context, float spValue) {  
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;  
        return (int) (spValue * fontScale + 0.5f);  
    }  
    
    public static  String getGB2312Code(String str){
	 try {
		 char cc=str.charAt(0);
		 if(cc<48 || cc>57){
			 return URLEncoder.encode(str, "gb2312");
		 }
		 cc=str.charAt(1);
	    	if(cc>=48 && cc<=57){
	    		return str.substring(0, 2)+URLEncoder.encode(str.substring(2), "gb2312");
	    	}else return str.substring(0,1)+URLEncoder.encode(str.substring(1), "gb2312");
	
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 return "";
 }
    public static String getPureWord(String str){
    	char cc=str.charAt(1);
    	if(cc>=48 && cc<=57){
    		return str.substring(2);
    	}else return str.substring(1);
    }
    
    public static String getNumberStr(String str){
    	
    	String regEx="[^0-9]";   
    	Pattern p = Pattern.compile(regEx);   
    	Matcher m = p.matcher(str);   
    	return m.replaceAll("").trim();
    //	System.out.println( m.replaceAll("").trim());
    }
    
	/** 
	 * <br>功能简述:4.4及以上获取图片的方法
	 * <br>功能详细描述:
	 * <br>注意:
	 * @param context
	 * @param uri
	 * @return
	 */
	@TargetApi(Build.VERSION_CODES.KITKAT)
	public static String getPath(final Context context, final Uri uri) {

		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

		// DocumentProvider
		if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
			// ExternalStorageProvider
			if (isExternalStorageDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				if ("primary".equalsIgnoreCase(type)) {
					return Environment.getExternalStorageDirectory() + "/" + split[1];
				}
			}
			// DownloadsProvider
			else if (isDownloadsDocument(uri)) {

				final String id = DocumentsContract.getDocumentId(uri);
				final Uri contentUri = ContentUris.withAppendedId(
						Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

				return getDataColumn(context, contentUri, null, null);
			}
			// MediaProvider
			else if (isMediaDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				Uri contentUri = null;
				if ("image".equals(type)) {
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				} else if ("video".equals(type)) {
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				} else if ("audio".equals(type)) {
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				}

				final String selection = "_id=?";
				final String[] selectionArgs = new String[] { split[1] };

				return getDataColumn(context, contentUri, selection, selectionArgs);
			}
		}
		// MediaStore (and general)
		else if ("content".equalsIgnoreCase(uri.getScheme())) {

			// Return the remote address
			if (isGooglePhotosUri(uri))
				return uri.getLastPathSegment();

			return getDataColumn(context, uri, null, null);
		}
		// File
		else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}

		return null;
	}

	public static String getDataColumn(Context context, Uri uri, String selection,
			String[] selectionArgs) {

		Cursor cursor = null;
		final String column = "_data";
		final String[] projection = { column };

		try {
			cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
					null);
			if (cursor != null && cursor.moveToFirst()) {
				final int index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(index);
			}
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return null;
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is Google Photos.
	 */
	public static boolean isGooglePhotosUri(Uri uri) {
		return "com.google.android.apps.photos.content".equals(uri.getAuthority());
	}
}
