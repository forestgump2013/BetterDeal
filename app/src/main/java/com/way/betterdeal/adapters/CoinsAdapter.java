package com.way.betterdeal.adapters;

import java.util.ArrayList;

import com.way.betterdeal.R;
import com.way.betterdeal.object.CoinRecord;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CoinsAdapter extends BaseAdapter {
	
	ArrayList<CoinRecord> records;
	LayoutInflater inflater;
   public CoinsAdapter(Activity aa,ArrayList<CoinRecord> rs){
	   inflater=aa.getLayoutInflater();
	   records=rs;
	//   R.layout
   }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return records.size();
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
		Holder holder;
		if(convertView==null){
			holder=new Holder();
			convertView=inflater.inflate(R.layout.coin_record_layout, null);
			holder.coinReason=(TextView)convertView.findViewById(R.id.coinReason);
			holder.coinNumber=(TextView)convertView.findViewById(R.id.coinNumber);
			holder.coinDate=(TextView)convertView.findViewById(R.id.coinDate);
			convertView.setTag(holder);
		}else holder=(Holder)convertView.getTag();
		holder.loadData(records.get(position));
		return convertView;
	}
	
	class Holder{
		public TextView coinReason,coinNumber,coinDate;
		public void loadData(CoinRecord record){
			coinReason.setText(record.reason);
			coinNumber.setText("+"+record.count);
			coinDate.setText(record.date);
		}
	}

}
