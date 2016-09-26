package com.way.betterdeal.adapters;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;
import com.way.betterdeal.object.CategoryCell;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoryCellAdapter extends BaseAdapter {
	
	MainActivity ma;
	ArrayList<CategoryCell> datas;
	LayoutInflater inflater;
	CellHolder holder;
	String subPath;
	
	public CategoryCellAdapter(ArrayList<CategoryCell> ts,MainActivity mm){
		ma=mm;
		datas=ts;
		inflater=ma.getLayoutInflater();
	}
	
	public void setSubPath(String str){
		subPath=str;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datas.size();
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
			convertView=inflater.inflate(R.layout.category_cell, null);
			holder=new CellHolder();
			holder.cellImage=(ImageView)convertView.findViewById(R.id.cellImage);
			holder.cellName=(TextView)convertView.findViewById(R.id.cellName);
			convertView.setTag(holder);
		}else holder=(CellHolder)convertView.getTag();
		CategoryCell cell=datas.get(position);
	//	TextView cellName=(TextView)convertView.findViewById(R.id.cellName);
		String name=  cell.title.substring(0,cell.title.length()-4 );
		holder.cellName.setText(StaticValueClass.getPureWord(name));
		String url;
		/*
		try {
		//	url = URLEncoder.encode(subGroupIndex+"/"+(position+1)+".jpg","gb2312");
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
		
		//Log.d("***name", name);
		url=subPath+"/"+StaticValueClass.getGB2312Code(name)+".jpg";
		holder.loadImage(url);
		
		return convertView;
	}
	
	class CellHolder{
		public ImageView cellImage;
		public TextView cellName;
		
		public void reMeasure(){
			ViewGroup.LayoutParams params=cellImage.getLayoutParams();
			params.height=params.width=StaticValueClass.screenWidth*100/720;
		}
		
		public void loadImage(String subPath){
			reMeasure();
			Log.d("category pic path", subPath);
			StaticValueClass.asynImageLoader.showImageAsyn(cellImage, "CHCategoryInfo/"+subPath, R.mipmap.blank_background);
    		
		}
	}
	
	 private String getEncode(String str){
		 try {
			return URLEncoder.encode(str, "gb2312");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return "";
	 }

}
