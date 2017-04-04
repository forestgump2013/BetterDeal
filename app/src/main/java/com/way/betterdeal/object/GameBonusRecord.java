package com.way.betterdeal.object;

import com.way.betterdeal.StaticValueClass;

public class GameBonusRecord {
	
//	public Bitmap bonusImageNum;
	public String bonusNum,bonusTitle,bonusImageNum,bonusDetail,bonusObtain,bonusDate,bonusReason,imgUrl;
	public Float bonusPrice;
	public int flag,bonusWeight,gameMark,mallMark,index,quantity;
	public String name,tel,address;
	public String express,expressNum;
	public BuyerAddressRecord buyerAddressRecord=null;
	
	public GameBonusRecord(String num,String title,Float price,String detail,String obtain,int f){
		
		imgUrl="";
		bonusNum=num;
		bonusTitle=title;
		bonusPrice=price;
		bonusDetail=detail;
		bonusObtain=obtain;
		flag=f;
	}
	
	//as purchase record
	public GameBonusRecord(int mm,String iUrl,String title,float price, String date){
		mallMark=mm;
		imgUrl=iUrl;
		bonusTitle=title;
		bonusPrice=price;
		bonusDate=date;
		
	}
	// as welfare record
	public GameBonusRecord(String iUrl,String title,float price, String date,String reason){
		imgUrl=iUrl;
		bonusTitle=title;
		bonusPrice=price;
		bonusDate=date;
		bonusReason=reason;
	}
	
	//as game prize
	public GameBonusRecord(int idx,String title,float price, String obtain,int w,int quty,int mark){
	//	imgUrl=iUrl;
		index=idx;
		imgUrl= "BetterDeal/gamePics/"+mark+"/"+index+".png";
		bonusTitle=title;
		bonusPrice=price;
		bonusObtain=obtain;
		bonusWeight=w;
		quantity=quty;
		gameMark=mark;
	}
	
	
	public void setAddresInfo(BuyerAddressRecord ba){
		buyerAddressRecord=ba;
		name=ba.name;
		tel=ba.tel;
		address=ba.address;
		flag=2;
	}
	
	public void loadBonuserAddressInfo(String n,String t,String a){
		buyerAddressRecord=new BuyerAddressRecord(n,t,a);
		name=n;tel=t;address=a;
	}
	
	public boolean haveAddress(){
		return !(buyerAddressRecord==null);
	}
	
	public void loadExpress(String t,String n){
		express=t;
		expressNum=n;
	}
    

}
