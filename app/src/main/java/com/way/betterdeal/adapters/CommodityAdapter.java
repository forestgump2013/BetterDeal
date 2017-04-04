package com.way.betterdeal.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/*
import com.alibaba.sdk.android.AlibabaSDK;
import com.alibaba.sdk.android.trade.ItemService;
import com.alibaba.sdk.android.trade.TradeConstants;
import com.alibaba.sdk.android.trade.callback.TradeProcessCallback;
import com.alibaba.sdk.android.trade.model.TradeResult;
*/
import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;
import com.way.betterdeal.object.Commodity;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CommodityAdapter extends BaseAdapter {
	MainActivity ma;
	ArrayList<Commodity> commodities;
	LayoutInflater inflater;
	FragmentManager fm;
	FragmentTransaction ft;
//	ItemService itemService=null;
//	TradeProcessCallback tradeProcessCallback;
	//Commodity one;
	
	public CommodityAdapter(MainActivity mm,ArrayList<Commodity> commos){
		ma=mm;
		commodities=commos;
		inflater=ma.getLayoutInflater();
		fm=ma.getSupportFragmentManager();
	//	itemService=AlibabaSDK.getService(ItemService.class);
		/*
		tradeProcessCallback=new TradeProcessCallback(){

			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}

			public void onPaySuccess(TradeResult result) {
				// TODO Auto-generated method stub
	              
				
			}
			
		}; */
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return commodities.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
	//	if(convertView==null){
			if(position%2==0)
			convertView=inflater.inflate(R.layout.commodity_poster, null);
			else convertView=inflater.inflate(R.layout.commodity_poster1, null);
	//	}
		ImageView poster=(ImageView)convertView.findViewById(R.id.posterView);
		TextView cost=(TextView)convertView.findViewById(R.id.item_price);
		TextView detail=(TextView)convertView.findViewById(R.id.item_title);
		final Commodity commo=commodities.get(position);
		//	poster.setBackgroundResource(commo.posterResId);
		//	poster.setImageResource(R.drawable.picture);
		//commo.picUrl="http://www.qcygl.com/betterCommoditisPic/b"+commo.order+".jpg";
		StaticValueClass.asynImageLoader.showImageAsyn(poster, commo.picUrl, R.mipmap.blank_background);
			
		ViewGroup.LayoutParams params=poster.getLayoutParams();
		params.height=(StaticValueClass.screenWidth-18)/2;
		poster.setLayoutParams(params);
		poster.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			ma.loadCommodityDetailFragment(commo);
			}
			
		});
		Log.d("commodityAdapter", "position:"+position);
		return convertView;
	}
	
	static class ViewHolder {
		  TextView name;
		  TextView price1,price2,mallName;
		  ImageView image,mallMark;
		  int position;
		}

}
