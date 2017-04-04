package com.way.betterdeal.object;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Commodity {
	public float price,reserve_price,auction_price;
   public	int bounds,market,order,coupon;
   public long itemId;
   public	String title,category,lastTime,picName,webLink,couponLink,picUrl,leftTime;
	public  boolean isFavorited;
    static String pat1="MM/dd/yyyy";
	static SimpleDateFormat dateFormator= new SimpleDateFormat(pat1);
   public Commodity(){
	 //  mark=1;
   }
	
	public Commodity(int itOrder,int itMark,long itId,int itBounds){
		this.order=itOrder;
		this.itemId=itId;
		this.market=itMark;
		this.bounds=itBounds;
		lastTime="";
	}
	public void loadData(String tt,float tPrice,float tRprice,String pName,String pUrl,String cLink,String link){
		this.title=tt;
		this.price=tPrice;
		this.reserve_price=tRprice;
		this.picName=pName;
		if(pUrl!=null && !pUrl.equals("null")){
			picUrl=pUrl;
		}else  picUrl="";
		if( cLink!=null && cLink.contains("http")){
			this.coupon=1;
			couponLink=cLink;
		}else {
			this.coupon=0;
			couponLink="";
		}
        if(link!=null)
		   webLink=link.trim();
		else  webLink="";
		isFavorited=false;
	}
	public void loadExtraData(String time,String url,float pe){
		if (time!=null)
	    	lastTime=time;
		else  lastTime="";
		couponLink=url.trim();
		leftTime="";
		auction_price=pe;
	}
	@Override
	public String toString(){
		return title+price+" coupon_link "+couponLink;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Commodity){
			Commodity cc=(Commodity)obj;
			if (cc.webLink.compareTo(webLink)==0) return  true;
			else  return false;
		}
		return super.equals(obj);
	}

	public void computeLeftTime(){
         try{
			Date lastDate=dateFormator.parse(lastTime);
			 Date current=new Date();
			 long differ=(lastDate.getTime()-current.getTime())/1000;
			 if(differ>0){
                   long days=differ/(24*3600);
				   long hours=(differ%(24*3600))/3600;
				   long minutes=(differ%3600)/60;
				   long seconds= differ%60;
				 leftTime="仅剩"+days+"天  "+hours+"时"+minutes+"分"+seconds+"秒";
			 }else{
				 leftTime="已过期！";
			 }

		 }catch (Exception e){
			 leftTime="";
			 e.printStackTrace();
		 }
	}
}
