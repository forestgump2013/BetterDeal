package com.way.betterdeal.fragments;

import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;
import com.way.betterdeal.object.Commodity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
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
	Button backBtn;
	ImageView nullImage;
	public FavouriteFragment(){

	}
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		ma=(MainActivity)this.getActivity();
		view=inflater.inflate(R.layout.favourite_fragment, container, false);
		backBtn=(Button)view.findViewById(R.id.backBtn);
		nullImage=(ImageView)view.findViewById(R.id.nullImage);
		listView=(ListView)view.findViewById(R.id.listView);
		myAdapter=new MyAdapter();
		listView.setAdapter(myAdapter);
		listView.setCacheColorHint(0);
		 if(StaticValueClass.isAfterKitKat)
			 view.setPadding(0, StaticValueClass.statusBarHeight, 0, 0);
		listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
				AlertDialog.Builder builder=new AlertDialog.Builder(ma);
				builder.setTitle("删除！");
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						commodities.remove(position);
						myAdapter.notifyDataSetChanged();
					}
				});
				builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});
				builder.show();
				return false;
			}
		});
		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
		init();
		return view;
		
	}

	private  void init(){
		//
		Bitmap backmark= BitmapFactory.decodeResource(this.getActivity().getResources(), R.mipmap.expand_icon);
		Drawable leftDrawable=new BitmapDrawable(StaticValueClass.getBackIcon(backmark));
		leftDrawable.setBounds(0, 0, backmark.getWidth(), backmark.getHeight());
		//backBtn.setBackground(leftDrawable);
		backBtn.setCompoundDrawables(leftDrawable, null, null, null);
		backBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ma.onBackPressed();
			}
		});
		if (StaticValueClass.currentBuyer.favouriteItems.size()==0)
			nullImage.setVisibility(View.VISIBLE);
		else  nullImage.setVisibility(View.GONE);
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
		
		
		
		public void loadData(final Commodity record){
			StaticValueClass.asynImageLoader.showImageAsyn(cImage, record.picUrl, R.mipmap.blank_background);
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

			cImage.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					ma.loadCommodityDetailFragment(record);
				}
			});
			
		}
		
		public void reMeasure(){
			
		}
		
	}
	
}
