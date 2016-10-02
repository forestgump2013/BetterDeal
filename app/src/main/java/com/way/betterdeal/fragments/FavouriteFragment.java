package com.way.betterdeal.fragments;

import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;
import com.way.betterdeal.object.Commodity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class FavouriteFragment extends Fragment {

	View view;
	ListView listView;
	MainActivity ma;
	MyAdapter myAdapter;
	int direct;
	ArrayList<Commodity> commodities;
	public FavouriteFragment(){

	}
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		ma=(MainActivity)this.getActivity();
		view=inflater.inflate(R.layout.favourite_fragment, container, false);
		listView=(ListView)view.findViewById(R.id.listView);
		myAdapter=new MyAdapter();
		listView.setAdapter(myAdapter);
		 if(StaticValueClass.isAfterKitKat)
			 view.setPadding(0, StaticValueClass.statusBarHeight, 0, 0);
		return view;
		
	}

	public void setDirect(int d){
		direct=d;
		if(direct==1){
			commodities=StaticValueClass.currentBuyer.favouriteItems;
		}else commodities=StaticValueClass.currentBuyer.tracingItems;
	}
    
	public class MyAdapter extends BaseAdapter{

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
			if(convertView==null){
				convertView=ma.getLayoutInflater().inflate(R.layout.search_result_item, null);
			//	ma.getLayoutInflater().inflate(resource, root, attachToRoot)
				holder=new ViewHolder();
				holder.cImage=(ImageView)convertView.findViewById(R.id.commodityImage);
				holder.title=(TextView)convertView.findViewById(R.id.commodityTitle);
				holder.price=(TextView)convertView.findViewById(R.id.commodityPrice);
				holder.oldPrice=(TextView)convertView.findViewById(R.id.oldPrice);
				holder.mallTitle=(TextView)convertView.findViewById(R.id.mallTitle);
				holder.mImage=(ImageView)convertView.findViewById(R.id.mallMark);
				convertView.setTag(holder);
			}else holder=(ViewHolder)convertView.getTag();
			holder.loadData(commodities.get(position));
			return convertView;
		}
		
	}
	ViewHolder holder;
	public class ViewHolder{
		
		public TextView title,price,oldPrice,mallTitle;
		public ImageView cImage,mImage;
		
		
		
		public void loadData(Commodity record){
			title.setText(record.title);
			price.setText("¥"+record.price);
			oldPrice.setText(""+record.reserve_price);
			switch(record.market){
			case 1: mallTitle.setText("淘宝");
					mImage.setImageResource(R.mipmap.taobao_c_mark);
					break;
			case 2: mallTitle.setText("天猫");
					mImage.setImageResource(R.mipmap.tianmao_c_mark);
					break;
			}
			
		}
		
		public void reMeasure(){
			
		}
		
	}
	
}
