package com.way.betterdeal.object;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Commodity {
	public float price,reserve_price;
   public	int bounds,market,order,coupon;
   public long itemId;
   public	String title,category,lastTime,picName,webLink,couponLink,picUrl,leftTime;
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
	public void loadData(String tt,float tPrice,float tRprice,String pName,String cLink,String link){
		this.title=tt;
		this.price=tPrice;
		this.reserve_price=tRprice;
		this.picName=pName;
		if( cLink!=null && cLink.contains("www")){
			this.coupon=1;
			couponLink=cLink;
		}else {
			this.coupon=0;
			couponLink="";
		}
        if(link!=null)
		   webLink=link;
		else  webLink="";
	}
	public void loadExtraData(String time,String url){
		if (time!=null)
	    	lastTime=time;
		else  lastTime="";
		leftTime="";
	}
	@Override
	public String toString(){
		return title+price;
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
