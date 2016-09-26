package com.way.betterdeal.adapters;

import java.util.ArrayList;

import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;
import com.way.betterdeal.object.CategoryCell;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CategoryAdapter extends BaseAdapter {
	MainActivity ma;
	ArrayList<CategoryCell> categories;
	 LayoutInflater inflater;
	 ArrayList<TextView> textViews;
	 TextView textView;
	 int leftMargin;
	public CategoryAdapter(MainActivity mm, ArrayList<CategoryCell> da, ArrayList<TextView> tViews){
		ma=mm;
		categories=da;
		inflater=ma.getLayoutInflater();
		
		textViews=tViews;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return categories.size();
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
		/*
		if(convertView==null){
	
		}else  */
		textView=(TextView)textViews.get(position);
		textView.setText(StaticValueClass.getPureWord(categories.get(position).title));
			return textView;
	}

}
