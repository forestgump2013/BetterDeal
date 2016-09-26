package com.way.betterdeal.object;

import android.widget.ImageView;
import android.widget.TextView;

public class CommodityPoster {
	public TextView details,price1,price2,bounds;
	public ImageView scaleImage,markImage;
	
	public CommodityPoster(TextView tDetails,TextView tPrice1,TextView tPrice2,ImageView tScaleImage){
		details=tDetails;
		price1=tPrice1;
		price2=tPrice2;
		scaleImage=tScaleImage;
		
	}

}
