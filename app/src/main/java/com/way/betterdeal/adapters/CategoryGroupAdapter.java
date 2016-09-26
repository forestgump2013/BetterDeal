package com.way.betterdeal.adapters;

//import com.example.helloworld.R;

import java.util.ArrayList;

import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;
import com.way.betterdeal.object.CategoryCell;
import com.way.betterdeal.object.CategoryGroup;
import com.way.betterdeal.view.NotScrolledGridView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CategoryGroupAdapter extends BaseAdapter {
	
	MainActivity ma;
	LayoutInflater layoutInflater;
	ArrayList<CategoryCell> categoryGroups;
	String subPath;
	static final String[] numbers = new String[] { 
		"A", "B", "C", "D", "E",
		"F", "G", "H", "I", "J",
		"K", "L", "M", "N", "O",
		"P", "Q", "R", "S", "T",
		"U", "V", "W", "X", "Y", "Z"};
	
	public CategoryGroupAdapter(MainActivity mm){
		ma=mm;
		layoutInflater=ma.getLayoutInflater();
		categoryGroups=new ArrayList<CategoryCell>();
	}
	
	public void setCategoryData(ArrayList<CategoryCell> groups){
		categoryGroups=groups;
	}
	
	public void setSubPath(String str){
		subPath=str;
	}

//	@Override
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return categoryGroups.size();
	}

//	@Override
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

//	@Override
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView==null){
			convertView=layoutInflater.inflate(R.layout.category_group , null);
		}
		TextView title=(TextView)convertView.findViewById(R.id.groupName);
		NotScrolledGridView gridView=(NotScrolledGridView)convertView.findViewById(R.id.gridView);
		CategoryCell group=categoryGroups.get(position);
		CategoryCellAdapter categoryCellAdapter=new CategoryCellAdapter(group.childCells,ma);
		
		categoryCellAdapter.setSubPath(subPath+"/"+StaticValueClass.getGB2312Code(group.title));
		gridView.setAdapter(categoryCellAdapter);
		
		title.setText(StaticValueClass.getPureWord(group.title));
		return convertView;
	}

}
