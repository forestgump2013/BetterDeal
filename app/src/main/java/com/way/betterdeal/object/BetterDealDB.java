package com.way.betterdeal.object;

import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.way.betterdeal.StaticValueClass;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Looper;
import android.util.Log;

public class BetterDealDB extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME="BETTERDEAL_DB";
	private static final int DATABASE_VERSION=9;
	//-------------------------
	public final static String BUYER_TABLE_NAME="buyer_table";
	public final static String BUYER_TYPE = "buyer_type";
    public final static String BUYER_TEL = "buyer_tel";
    public final static String BUYER_NICK = "buyer_nick";
    public final static String BUYER_PASSWORD = "buyer_password";
    public final static String BUYER_BONUS="buyer_bonus";
    public final static String BUYER_CONSECUTIVE_SIGN_DAYS = "buyer_consecutive_sign_days";
    public final static String BUYER_LAST_SIGN_DATE="buyer_last_sign_date";
    public final static String BUYER_GAME_DATE="buyer_game_date";
    public final static String BUYER_NINEPANE_DATE="buyer_ninepane_date";
    public final static String BUYER_GAME_COUNT="buyer_game_count";
    public final static String BUYER_EXPRESS_ADDRESS="buyer_express_address";
	//-------------------------------------------------------
    public final static String PURCHASE_TABLE_NAME="purchase_record_table";
    public final static String PURCHASE_ID = "purchase_id";
    public final static String PURCHASE_MALL = "purchase_mall";
    public final static String PURCHASE_COMMODITY = "purchase_commodity";
    public final static String PURCHASE_EXCHANGE_COINS ="purchase_exchange_coins";
    public final static String PURCHASE_COST ="purchase_cost";
    public final static String PURCHASE_DATE = "purchase_date";
    //--------------------------------------------------------------
    public final static String BONUS_TABLE_NAME="bonus_record_table";
    public final static String BONUS_ID = "bonus_id";
    public final static String BONUS_TITLE = "bonus_title";
    public final static String BONUS_PRICE = "bonus_price";
    public final static String BONUS_DETAIL = "bonus_detail";
    public final static String BONUS_OBTAIN ="bonus_obtain";
    public final static String BONUS_DATE ="bonus_date";
    public final static String BONUSER_NAME = "bonuser_name";
    public final static String BONUSER_TEL = "bonuser_tel";
    public final static String BONUSER_ADDRESS = "bonuser_address";
    public final static String BONUS_EXPRESS = "bonus_express";
    public final static String BONUS_EXPRESSNUM = "bonus_express_num";
    public final static String BONUS_FLAG = "bonus_flag";
    
    
    public final static String TAOBAOINFO_TABLE_NAME="taobao_info_table";
    public final static String TAOBAOINFO_TOPIC = "taobaoinfo_topic";
    public final static String TAOBAOINFO_CONTENT = "taobaoinfo_content";
    
    public BetterDealDB(Context context){
    	super(context, DATABASE_NAME, null, DATABASE_VERSION);
    	
    }
	public BetterDealDB(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql="";
		sql = "CREATE TABLE IF NOT EXISTS " 
			    + BUYER_TABLE_NAME + " ( " 
			                    + BUYER_TYPE+" INTEGER, "
					            + BUYER_TEL+" TEXT, "
					            + BUYER_NICK+" TEXT, "
					            + BUYER_PASSWORD+" TEXT, "
					            + BUYER_BONUS+" INTEGER, "
					            + BUYER_GAME_DATE+" TEXT, "
					            + BUYER_NINEPANE_DATE+" TEXT, "
					            + BUYER_GAME_COUNT+" INTEGER, "
					            + BUYER_CONSECUTIVE_SIGN_DAYS + " INTEGER, "
					            + BUYER_EXPRESS_ADDRESS+" TEXT, "
					            + BUYER_LAST_SIGN_DATE+" TEXT ) ";
			    db.execSQL(sql); 
		
		
		//------------------
			   		    
		sql = "CREATE TABLE IF NOT EXISTS " 
			    + PURCHASE_TABLE_NAME + " ( " 
			                    + PURCHASE_ID+" INTEGER AUTOINCRIMENT , "
					            + PURCHASE_MALL+ " INTEGER , "
					            + PURCHASE_COMMODITY+" TEXT, "
					            + PURCHASE_COST + " REAL, "
					            + PURCHASE_EXCHANGE_COINS + " INTEGER , "
					            + PURCHASE_DATE+" TEXT ) ";
			    db.execSQL(sql); 
	   //-------------------
	
	    sql = "CREATE TABLE IF NOT EXISTS " 
			    + BONUS_TABLE_NAME + " ( " 
                + BONUS_ID+" TEXT , "
                + BONUS_FLAG+" INTEGER , "
	            + BONUS_TITLE+ " TEXT , "
	            + BONUS_PRICE+ " REAL , "
	            + BONUS_DETAIL+" TEXT, "
	            + BONUS_OBTAIN + " TEXT, "
	            + BONUS_DATE + " TEXT , "
	            + BONUSER_NAME + " TEXT , "
	            + BONUSER_TEL + " TEXT , "
	            + BONUSER_ADDRESS + " TEXT , "
	            + BONUS_EXPRESS + " TEXT , "
	            + BONUS_EXPRESSNUM+" TEXT ) ";
			    db.execSQL(sql); 	    

		//------------------------------------
			    sql = "CREATE TABLE IF NOT EXISTS " 
					    + TAOBAOINFO_TABLE_NAME + " ( " 
					                    + TAOBAOINFO_TOPIC+" TEXT , "
							            + TAOBAOINFO_CONTENT+" TEXT ) ";
					    db.execSQL(sql); 

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		//delete old tables
		String sql="";
		sql = "DROP TABLE IF EXISTS " + BUYER_TABLE_NAME;
		db.execSQL(sql);
		
	    sql = "DROP TABLE IF EXISTS " + BONUS_TABLE_NAME;
		db.execSQL(sql);
		this.onCreate(db);
		

	}
	
	public void buyerLoginIn(Buyer buyer){
		//clear data.
	//	buyerLoginOut();
	//	clearBonusData();
		//save new data.
		SQLiteDatabase db = this.getWritableDatabase();
	    ContentValues cv = new ContentValues();
	    cv.put(BUYER_TYPE,buyer.member_type);
	    cv.put(BUYER_TEL,buyer.tel);
	    cv.put(BUYER_NICK,buyer.nickName);
	    cv.put(BUYER_PASSWORD,buyer.password);
	    cv.put(BUYER_BONUS,buyer.bonus);
	    cv.put(BUYER_CONSECUTIVE_SIGN_DAYS,buyer.consecutive_sign_days);
	    cv.put(BUYER_LAST_SIGN_DATE,buyer.last_sign_date);
	    cv.put(BUYER_GAME_DATE,buyer.game_date);
	    cv.put(BUYER_NINEPANE_DATE,buyer.ninepane_date);
	    cv.put(BUYER_GAME_COUNT,buyer.game_count);
	    cv.put(BUYER_EXPRESS_ADDRESS,buyer.express_address);
	    long row = db.insert(BUYER_TABLE_NAME, null, cv); 
	
	    db.close();
	//    Log.d("***buyerLoginIn", "ContentValues:"+cv.toString()+"\n insert row:"+row);
	}
	
	public void loadBuyerInfo(String tel,Buyer buyer){
		SQLiteDatabase db = this.getReadableDatabase();
	    String where = "buyer_tel = ?";
	    String[] whereValue = { tel }; 
	    Cursor cursor = db.query(BUYER_TABLE_NAME, null, where, whereValue, null, null, null); //FIELD_applymark+" DESC , "+FIELD_safeMiles+" ASC"
	    if (cursor.moveToNext()) {   
	    	Log.d("loadBuyerInfo", "read buyer:"+cursor.getString(cursor.getColumnIndex(BUYER_TEL)));
	    	buyer.member_type = cursor.getInt(cursor.getColumnIndex(BUYER_TYPE));
	    	buyer.nickName = cursor.getString(cursor.getColumnIndex(BUYER_NICK)); 
	    	buyer.password = cursor.getString(cursor.getColumnIndex(BUYER_PASSWORD));
	    	buyer.bonus = cursor.getInt(cursor.getColumnIndex(BUYER_BONUS));
	    	buyer.consecutive_sign_days = cursor.getInt(cursor.getColumnIndex(BUYER_CONSECUTIVE_SIGN_DAYS));
	    	buyer.last_sign_date = cursor.getString(cursor.getColumnIndex(BUYER_LAST_SIGN_DATE));
	    	buyer.game_count = cursor.getInt(cursor.getColumnIndex(BUYER_GAME_COUNT));
	    	buyer.game_date = cursor.getString(cursor.getColumnIndex(BUYER_GAME_DATE));
	    	buyer.ninepane_date = cursor.getString(cursor.getColumnIndex(BUYER_NINEPANE_DATE));
	   // 	buyer.express_address = cursor.getString(cursor.getColumnIndex(BUYER_EXPRESS_ADDRESS));
	    	buyer.parseAddressData(cursor.getString(cursor.getColumnIndex(BUYER_EXPRESS_ADDRESS)));
	    }
	//    Log.d("loadBuyerInfo", "cursor column count:"+cursor.getColumnCount());
	    db.close();
	    Log.d("***loadBuyerInfo", "buyer info:"+buyer.toString());
	}
	
	public void updateBuyerInfo(Buyer buyer){
		Log.d("**updateBuyerInfo", buyer.toString());
		SQLiteDatabase db = this.getWritableDatabase(); 
		String where = " buyer_tel = ? ";
	    String[] whereValue = {buyer.tel}; 
	    ContentValues cv = new ContentValues(); 
	    cv.put(BUYER_NICK,buyer.nickName);
	    cv.put(BUYER_PASSWORD,buyer.password);
	    cv.put(BUYER_BONUS,buyer.bonus);
	    cv.put(BUYER_CONSECUTIVE_SIGN_DAYS,buyer.consecutive_sign_days);
	    cv.put(BUYER_LAST_SIGN_DATE,buyer.last_sign_date);
	    cv.put(BUYER_GAME_DATE,buyer.game_date);
	    cv.put(BUYER_GAME_COUNT,buyer.game_count);
	    cv.put(BUYER_NINEPANE_DATE,buyer.ninepane_date);
	    buyer.compressAddressData();
	    cv.put(BUYER_EXPRESS_ADDRESS,buyer.express_address);
	    
	    int row=db.update(BUYER_TABLE_NAME, cv, null, null); 
	    db.close();
	  //  db.update(table, values, whereClause, whereArgs)
	    Log.d("**updateBuyerInfo", "rows:"+row);
	    updateBuyerInfoAtServer(buyer);
	}
	
	
	public void buyerLoginOut(){
		SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(BUYER_TABLE_NAME, null, null); 
	    db.delete(PURCHASE_TABLE_NAME, null, null); 
	    db.delete(BONUS_TABLE_NAME, null, null); 
	    db.close();
	}
	
	public void clearBonusData(){
		SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(BONUS_TABLE_NAME, null, null); 
	    db.close();
	}
	
	public void insertBonusRecord(GameBonusRecord record){
	   
		SQLiteDatabase db = this.getWritableDatabase();
	    ContentValues cv = new ContentValues();

	    cv.put(BONUS_ID,record.bonusNum);
	    cv.put(BONUS_TITLE,record.bonusTitle);
	    cv.put(BONUS_PRICE,record.bonusPrice);
	    cv.put(BONUS_DETAIL,record.bonusDetail);
	    cv.put(BONUS_OBTAIN,record.bonusObtain);
	    cv.put(BONUS_DATE,record.bonusDate);
	    cv.put(BONUSER_NAME,record.name);
	    cv.put(BONUSER_TEL,record.tel);
	    cv.put(BONUSER_ADDRESS,record.address);
	    cv.put(BONUS_EXPRESS,record.express);
	    cv.put(BONUS_EXPRESSNUM,record.expressNum);
	    cv.put(BONUS_FLAG,record.flag);
	    long row = db.insert(BONUS_TABLE_NAME, null, cv); 
	     db.close();
	     Log.d("*DB insertBonusRecord", record.bonusNum);
	}
	
	public void updateBonusRecord(GameBonusRecord record){
		SQLiteDatabase db = this.getWritableDatabase();
	    ContentValues cv = new ContentValues();
	    String where = " BONUS_ID = ? ";
	    String[] whereValue = {record.bonusNum}; 
	    cv.put(BONUSER_NAME,record.name);
	    cv.put(BONUSER_TEL,record.tel);
	    cv.put(BONUSER_ADDRESS,record.address);
	    cv.put(BONUS_FLAG,record.flag);
	    db.update(BONUS_TABLE_NAME, cv, where, whereValue);
	    db.close();
	}
	
	public void insertPurchaseRecord(PurchaseRecord record){
		SQLiteDatabase db = this.getWritableDatabase();
	    ContentValues cv = new ContentValues();

	    cv.put(PURCHASE_MALL,record.mallMark);
	    cv.put(PURCHASE_COMMODITY,record.commodityInfo);
	    cv.put(PURCHASE_EXCHANGE_COINS,record.coins);
	    cv.put(PURCHASE_DATE,record.date);
	    long row = db.insert(PURCHASE_TABLE_NAME, null, cv); 
	}
	
	public void getBonusRecords(ArrayList<GameBonusRecord> records){
		 /*
	     * public final static String BONUS_TABLE_NAME="bonus_record_table";
public final static String BONUS_ID = "bonus_id";
public final static String BONUS_TITLE = "bonus_title";
public final static String BONUS_DETAIL = "bonus_detail";
public final static String BONUS_OBTAIN ="bonus_obtain";
public final static String BONUS_DATE ="bonus_date";
public final static String BONUSER_NAME = "bonuser_name";
public final static String BONUSER_TEL = "bonuser_tel";
public final static String BONUSER_ADDRESS = "bonuser_address";
public final static String BONUS_EXPRESS = "bonus_express";
public final static String BONUS_EXPRESSNUM = "bonus_express_num";
	    */
		records.clear();
		SQLiteDatabase db = this.getReadableDatabase();
	    Cursor cursor = db.query(BONUS_TABLE_NAME, null, null, null, null, null, null); 
		Log.d("**DB getBonusRecords", "rows:"+cursor.getCount());
	    while (cursor.moveToNext()) {   
	    
	    	GameBonusRecord record=new GameBonusRecord(cursor.getString(cursor.getColumnIndex(BONUS_ID)),cursor.getString(cursor.getColumnIndex(BONUS_TITLE)),
	    			cursor.getFloat(cursor.getColumnIndex(BONUS_PRICE)),cursor.getString(cursor.getColumnIndex(BONUS_DETAIL)),cursor.getString(cursor.getColumnIndex(BONUS_OBTAIN))
	    			,cursor.getInt(cursor.getColumnIndex(BONUS_FLAG)));
	      	record.loadBonuserAddressInfo(cursor.getString(cursor.getColumnIndex(BONUSER_NAME)),cursor.getString(cursor.getColumnIndex(BONUSER_TEL)),
	    			cursor.getString(cursor.getColumnIndex(BONUSER_ADDRESS)));
	    	record.loadExpress(cursor.getString(cursor.getColumnIndex(BONUS_EXPRESS)), cursor.getString(cursor.getColumnIndex(BONUS_EXPRESSNUM)));
	    	records.add(record);
	    }
	    db.close();
	}
	
	public void getPurchaseRecords(ArrayList<PurchaseRecord> records){
		records.clear();
		SQLiteDatabase db = this.getReadableDatabase();
	 //   String where = "buyer_tel = ?";
	 //   String[] whereValue = { tel }; 
	    Cursor cursor = db.query(BUYER_TABLE_NAME, null, null, null, null, null, null); //FIELD_applymark+" DESC , "+FIELD_safeMiles+" ASC"
	    while (cursor.moveToNext()) {   
	    //	Log.d("loadBuyerInfo", "read buyer:"+cursor.getString(cursor.getColumnIndex(BUYER_TEL)));
	    	PurchaseRecord record=new PurchaseRecord(cursor.getInt(cursor.getColumnIndex(PURCHASE_MALL)),cursor.getString(cursor.getColumnIndex(PURCHASE_COMMODITY)),
	    			cursor.getInt(cursor.getColumnIndex(PURCHASE_EXCHANGE_COINS)),cursor.getString(cursor.getColumnIndex(PURCHASE_DATE)));
	    	records.add(record);
	    }
	}
	
	public void insertTaoBaoInfo(String topic,String content){
		SQLiteDatabase db = this.getWritableDatabase();
	    ContentValues cv = new ContentValues();
	    
	    cv.put(TAOBAOINFO_TOPIC,topic);
	    cv.put(TAOBAOINFO_CONTENT,content);
	    long row = db.insert(TAOBAOINFO_TABLE_NAME, null, cv); 
	}
	public void showTaoBaoInfo(){
		
		Log.d("**TAOBAO INFO showTaoBaoInfo", "Topic  content:");
		SQLiteDatabase db = this.getReadableDatabase();
	 //   String where = "buyer_tel = ?";
	 //   String[] whereValue = { tel }; 
		String topic,content;
	    Cursor cursor = db.query(TAOBAOINFO_TABLE_NAME, null, null, null, null, null, null);
	    while(cursor.moveToNext() ){
	    	topic = cursor.getString(cursor.getColumnIndex(TAOBAOINFO_TOPIC)); 
	    	content = cursor.getString(cursor.getColumnIndex(TAOBAOINFO_CONTENT)); 
	    	Log.d("**TAOBAO INFO", "Topic:"+topic+"  content:"+content);
	    }
		
	}
	
	private void updateBuyerInfoAtServer(final Buyer buyer){
		new Thread(new Runnable(){
		 	String result;
			@Override
			public void run() {
				// TODO Auto-generated method stub
				 ArrayList<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
				 nameValuePairs.add(new BasicNameValuePair("member_type",""+buyer.member_type));
	              nameValuePairs.add(new BasicNameValuePair("buyer",buyer.tel));
	              nameValuePairs.add(new BasicNameValuePair("nick_name",buyer.nickName));
	              nameValuePairs.add(new BasicNameValuePair("consecutive_sign_days",""+buyer.consecutive_sign_days));
	              nameValuePairs.add(new BasicNameValuePair("last_sign_date",""+buyer.last_sign_date));
	              nameValuePairs.add(new BasicNameValuePair("bonus",""+buyer.bonus));
	              nameValuePairs.add(new BasicNameValuePair("slot_date",""+buyer.game_date));
	              nameValuePairs.add(new BasicNameValuePair("slot_count",""+buyer.game_count));
	              nameValuePairs.add(new BasicNameValuePair("ninepane_date",""+buyer.ninepane_date));
	              nameValuePairs.add(new BasicNameValuePair("express_address",""+buyer.express_address));
	              nameValuePairs.add(new BasicNameValuePair("personal_sign",buyer.personalSign));
	              Looper.prepare();      
	              try{
	                   HttpClient httpclient = new DefaultHttpClient();
	                   HttpPost httppost = new HttpPost(StaticValueClass.serverAddress+"ch_update_buyer_info.php");
	                   httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,HTTP.UTF_8));
	                   HttpResponse response = httpclient.execute(httppost);
	                   Log.d("update_buyer_info", " recode:"+response.getStatusLine().getStatusCode());
	                   if(response.getStatusLine().getStatusCode()==200){
	                	   result=EntityUtils.toString(response.getEntity());
	                	   Log.d("updateBuyerInfo****", "mResult:"+result);
	                	   
	                	   }
	          
	              }catch(Exception e){
	                   Log.e("log_tag", "Error in http connection"+e.toString());
	                //   Toast.makeText(ct, "网络异常，请稍后再试!", Toast.LENGTH_LONG).show();
	              }
				  
				  
			}
    		
    	}).start();
		
	}
	
     public  void addNewGameBonusRecordToServer(final GameBonusRecord record){
    	 new Thread(new Runnable(){
 		 	String result;
 			@Override
			public void run() {
 				// TODO Auto-generated method stub
 				 ArrayList<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
 				 nameValuePairs.add(new BasicNameValuePair("buyer_number",""+StaticValueClass.currentBuyer.tel));
 	              nameValuePairs.add(new BasicNameValuePair("bonus_number",record.bonusNum));
 	              nameValuePairs.add(new BasicNameValuePair("bonus_price",""+record.bonusPrice));
 	              nameValuePairs.add(new BasicNameValuePair("bonus_title",""+record.bonusTitle));
 	              nameValuePairs.add(new BasicNameValuePair("bonus_detail",""+record.bonusDetail));
 	              nameValuePairs.add(new BasicNameValuePair("bonus_obtain",""+record.bonusObtain));
 	             nameValuePairs.add(new BasicNameValuePair("bonus_flag",""+record.flag));
 	              Looper.prepare();      
 	              try{
 	                   HttpClient httpclient = new DefaultHttpClient();
 	                   HttpPost httppost = new HttpPost(StaticValueClass.serverAddress+"ch_addGameBonusRecord.php");
 	                   httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,HTTP.UTF_8));
 	                   HttpResponse response = httpclient.execute(httppost);
 	                   Log.d("addGameBonusRecord", " recode:"+response.getStatusLine().getStatusCode());
 	                   if(response.getStatusLine().getStatusCode()==200){
 	                	   result=EntityUtils.toString(response.getEntity());
 	                	   Log.d("addGameBonusRecord****", "mResult:"+result);
 	                	   
 	                	   }
 	          
 	              }catch(Exception e){
 	                   Log.e("log_tag", "Error in http connection addGameBonusRecord"+e.toString());
 	                //   Toast.makeText(ct, "网络异常，请稍后再试!", Toast.LENGTH_LONG).show();
 	              }
 				  
 				  
 			}
     		
     	}).start();
     }
     
     public void updateBonusRecordInServer(final GameBonusRecord record){
    	 new Thread(new Runnable(){
  		 	String result;
  			@Override
			public void run() {
  				// TODO Auto-generated method stub
  				 ArrayList<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
  				  nameValuePairs.add(new BasicNameValuePair("buyer_number",""+StaticValueClass.currentBuyer.tel));
  	              nameValuePairs.add(new BasicNameValuePair("bonus_number",record.bonusNum));
  	              nameValuePairs.add(new BasicNameValuePair("bonuser_name",""+record.name));
  	              nameValuePairs.add(new BasicNameValuePair("bonuser_tel",""+record.tel));
  	              nameValuePairs.add(new BasicNameValuePair("bonuser_address",""+record.address));
  	              nameValuePairs.add(new BasicNameValuePair("bonus_flag",""+record.flag));
  	              Looper.prepare();      
  	              try{
  	                   HttpClient httpclient = new DefaultHttpClient();       //       ch_updateGameBonusRecord 
  	                   HttpPost httppost = new HttpPost(StaticValueClass.serverAddress+"ch_updateGameBonusRecord.php");
  	                   httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,HTTP.UTF_8));
  	                   HttpResponse response = httpclient.execute(httppost);
  	                   Log.d("updateGameBonusRecord", " recode:"+response.getStatusLine().getStatusCode());
  	                   if(response.getStatusLine().getStatusCode()==200){
  	                	   result=EntityUtils.toString(response.getEntity());
  	                	   Log.d("updateGameBonusRecord****", "mResult:"+result);
  	                	   
  	                	   }
  	          
  	              }catch(Exception e){
  	                   Log.e("log_tag", "Error in http connection updateGameBonusRecord"+e.toString());
  	                //   Toast.makeText(ct, "网络异常，请稍后再试!", Toast.LENGTH_LONG).show();
  	              }
  				  
  				  
  			}
      		
      	}).start();
     }

}
