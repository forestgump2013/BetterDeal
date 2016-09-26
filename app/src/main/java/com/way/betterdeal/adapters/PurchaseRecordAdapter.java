package com.way.betterdeal.adapters;

import java.util.ArrayList;

import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;
import com.way.betterdeal.object.PurchaseRecord;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class PurchaseRecordAdapter extends BaseAdapter {
	
	MainActivity ma;
	ArrayList<PurchaseRecord> coinRecords;
	LayoutInflater inflater;
	
	public PurchaseRecordAdapter(MainActivity mm ,ArrayList<PurchaseRecord> cs){
		ma=mm;
		coinRecords=cs;
		inflater=ma.getLayoutInflater();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return coinRecords.size();
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
			convertView=inflater.inflate(R.layout.bonus_record_layout, null);
		}
		/*
	//	ImageView mallMark=(ImageView)convertView.findViewById(R.id.mallMark);
	//	TextView info=(TextView)convertView.findViewById(R.id.commodityInfo);
		TextView coins=(TextView)convertView.findViewById(R.id.coins);
		
		CoinRecord record= coinRecords.get(position);
		switch(record.mallMark){
		case 1: mallMark.setImageDrawable(ma.getResources().getDrawable(R.drawable.taobao_icon));
		     break;
		case 2: mallMark.setImageDrawable(ma.getResources().getDrawable(R.drawable.tmall_icon));
		     break;
		}
		
		info.setText(record.commodityInfo);
		
		coins.setText(""+record.coins);
		 */
		return convertView;
	}

}
