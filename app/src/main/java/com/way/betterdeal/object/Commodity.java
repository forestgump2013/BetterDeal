package com.way.betterdeal.object;

public class Commodity {
	public float price,reserve_price;
   public	int bounds,market,order,coupon;
   public long itemId;
   public	String title,category,details,posterName,picUrl;
   
   public Commodity(){
	 //  mark=1;
   }
	
	public Commodity(int itOrder,int itMark,long itId,int itBounds){
		this.order=itOrder;
		this.itemId=itId;
		this.market=itMark;
		this.bounds=itBounds;
	}
	public void loadData(String tt,float tPrice,float tRprice,String itPicUrl,int cpn){
		this.title=tt;
		this.price=tPrice;
		this.reserve_price=tRprice;
		this.picUrl=itPicUrl;
		this.coupon=cpn;
	}
	@Override
	public String toString(){
		return title+price;
	}
}
