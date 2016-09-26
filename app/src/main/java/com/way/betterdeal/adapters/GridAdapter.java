package com.way.betterdeal.adapters;

import java.util.ArrayList;

import com.way.betterdeal.StaticValueClass;
import com.way.betterdeal.object.PanesStartBtn;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class GridAdapter extends BaseAdapter {
	
	ArrayList<ImageView> imageViews;
//	ArrayList<TextView> textViews;
	PanesStartBtn startBtn;
	int column,totalNum,outter,itemHeight;
	
	public GridAdapter(ArrayList<ImageView> is,PanesStartBtn btn,int cn,int out){
		imageViews=is;
	//	textViews=ts;
		column=cn;
		outter=out;
		if(outter==0){
			//int margin=StaticValueClass.screenWidth/15;
			itemHeight=StaticValueClass.screenWidth/4;
		}else  itemHeight=(StaticValueClass.screenWidth-20-column-1)/column;
		totalNum=2*column+(column-2)*2;
		startBtn=btn;
	}

	//@Override
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		//return imageViews.size();
		return column*column;
	}

	//@Override
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	//@Override
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	//@Override
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		int index=-1,mul,mod;
		if(position<column){
			index=position;
		}else if(position%column==0){
			mul=position/column;
			index=totalNum-mul;
		} else if(position>=this.getCount()-column){
			mod=position%column;
			index=totalNum-column-mod+1;
		}else if((position+1)%column==0){
			mul=(position+1)/column;
			index=column+mul-2;
		}
		ViewGroup.LayoutParams params;
		if(index==-1){
			params=startBtn.getLayoutParams();
			params.height=itemHeight;
			return startBtn;
		}
		//	return textViews.get(totalNum);
		 params=imageViews.get(index).getLayoutParams();
	    params.height=itemHeight;
	    
		return imageViews.get(index);
	}

}
