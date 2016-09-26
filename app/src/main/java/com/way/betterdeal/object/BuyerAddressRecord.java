package com.way.betterdeal.object;

public class BuyerAddressRecord {
	public String name,tel,address;
	public BuyerAddressRecord(String s1,String s2,String s3){
		name=s1;
		tel=s2;
		address=s3;
	}
	
	public  BuyerAddressRecord(String raw){
		String[] str=raw.split("\\,");
		if(str.length>0){
			name=str[0];
			tel=str[1];
			address=str[2];
		}
	}
	
	public String compress(){
		return name+","+tel+","+address;
	}
	
}
