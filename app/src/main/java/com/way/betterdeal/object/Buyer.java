package com.way.betterdeal.object;

import java.util.ArrayList;
import java.util.Date;

import android.util.Log;

import com.way.betterdeal.StaticValueClass;

public class Buyer {
	public String nickName,id,personalSign,tel,password,last_sign_date,game_date,ninepane_date,express_address;
	public int bonus,consecutive_sign_days,member_type,game_count;
	public ArrayList<GameBonusRecord> bonusRecords =new ArrayList<GameBonusRecord>();
	public ArrayList<GameBonusRecord> purchaseRecords =new ArrayList<GameBonusRecord>();
	public ArrayList<GameBonusRecord> welfareRecords =new ArrayList<GameBonusRecord>();
	public ArrayList<CoinRecord> coinRecords=new ArrayList<CoinRecord>();
	public ArrayList<BuyerAddressRecord> addressRecords=new ArrayList<BuyerAddressRecord>();
	public ArrayList<Commodity> favourites=new ArrayList<Commodity>();
	public Buyer(){
		nickName="游客";
		tel="**********";
		bonus=0;
	}
	public Buyer(int mt,String tl,String psd,int bs){
		
		member_type=mt;
		tel=tl;
		password=psd;
		bonus=bs;
		nickName="昵称";
		personalSign="个性签名";
		game_count=0;
		game_date="";
		//game_date=StaticValueClass.dateFormat.format(new Date());
		
	}
	
	public void createId(int t,String num){
		member_type=t;
		tel=num;
	}
	
	public void setPersonInfo(String str1,String str2){
		nickName=str1;
		personalSign=str2;
	}
	
	public void setSignInfo(String date,int days){
		last_sign_date=date;
		consecutive_sign_days=days;
		
	}
	
	public void setGameInfo(String date1,int count,String date2){
		
		game_date=date1;
		game_count=count;
		ninepane_date=date2;
	}
	
	public String getNick(){
		if(nickName.compareTo("")==0)
			return "t_"+tel;
		else return nickName;
	}
	
	@Override
	public String toString(){
		return "tel-type:"+tel+"--"+member_type+
				"\n nickName:"+nickName+
				"\n bonus:"+bonus+
				"\n consecutive_sign_days:"+consecutive_sign_days+
				"\n game_date count:"+game_date+"-"+game_count+
				"\n ninepane_date:"+ninepane_date+
				"\n last_sign_date:"+last_sign_date;
	}
	
	public boolean checkNinePanePermission(){
		if(ninepane_date==null || ninepane_date.compareTo(StaticValueClass.today)!=0){
			ninepane_date=StaticValueClass.today;
			return true;
		}
		return false;
	}
	
	public int checkGamePermission(){
		String today=StaticValueClass.dateFormat.format(new Date());
		if(game_date==null || game_date.compareTo(today)!=0){
			game_count=1;
			game_date=today;
			return 1;
		}else {
			if(game_count<3){
				game_count++;
				return 1;
			} else if(game_count==3) return 0;
			else if(game_count==4){
				game_count++;
				return 1;
			}
			else return -1;
		}
	}
	
	public void shareToPlayGame(){
		game_count++;
	}
	public boolean playGameWithBonus(){
		if(bonus>=StaticValueClass.gameBonusMount)
			return true;
		else return false;
	}
	public void subBonus(int mount){
		bonus-=mount;
	}
	
	public void parseAddressData(String raw){
		if(raw==null) return;
		addressRecords.clear();
		express_address="*"+raw;
		String[]str=express_address.split("\\|");
		
		for(int i=1;i<str.length;i++){
			Log.d("**express_address", i+":"+str[i]);
			addressRecords.add(new BuyerAddressRecord(str[i]));
		}
	}
	
	public String compressAddressData(){
		express_address=" ";
		for(int i=0;i<addressRecords.size();i++){
			express_address+="|"+addressRecords.get(i).compress();
		}
		return express_address;
	}
	
	public void  addFavoriteCommodity(Commodity commo){
		Log.d("***addFavoriteCommodity", "title"+commo.title);
		if(favourites.size()==30){
			favourites.remove(favourites.size()-1);
		}
		favourites.add(commo);
	}

}