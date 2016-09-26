package com.way.betterdeal.object;

public class PurchaseRecord {
	
	Commodity commodity;
	public  int mallMark,coins;
	public String commodityInfo;
	String date; 
	
	public PurchaseRecord(int mark,String info,int cs,String dt){
		mallMark=mark;
		commodityInfo=info;
		coins=cs;
		date=dt;
		
	}
	public PurchaseRecord(Commodity commo,String da){
		commodity=commo;
		date=da;
		
	}
	
	@Override
	public String toString(){
		//return commodity.title+
		return "商品 金额 日期";
	}

}
