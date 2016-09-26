package com.way.betterdeal.adapters;

import java.util.ArrayList;

import com.way.betterdeal.R;
import com.way.betterdeal.object.BuyerAddressRecord;
//import android.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class ExpressAddressAdapter extends BaseAdapter {
	
	Context c;
	LayoutInflater inflater;
	ArrayList<BuyerAddressRecord> records;
	int select=0;
	
	public  interface ChooseAddressListener{
		public void chooseAddress(int index);
	}
	
	ChooseAddressListener chooseAddressListener;
	
	public void setChooseAddressListener(ChooseAddressListener listener){
		chooseAddressListener=listener;
	}
	
	public ExpressAddressAdapter(Context cc,ArrayList<BuyerAddressRecord> rs){
		c=cc;
		records=rs;
		inflater=((Activity)c).getLayoutInflater();
	}
	
	public int getSelect(){
		return select;
	}
	
	public void setSelectIndex(int i){
		select=i;
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
			convertView=inflater.inflate(R.layout.address_record_layout, null);
			holder=new Holder();
			holder.checkBox=(CheckBox)convertView.findViewById(R.id.checkBox);
			holder.defaultMark=(TextView)convertView.findViewById(R.id.defaultMark);
			holder.info1=(TextView)convertView.findViewById(R.id.info1);
			holder.info2=(TextView)convertView.findViewById(R.id.info2);
			convertView.setTag(holder);
		}else{
			holder=(Holder)convertView.getTag();
		}
		holder.loadData(records.get(position), position);
		return convertView;
	}
	
	class Holder{
		public CheckBox checkBox;
		public TextView info1,info2,defaultMark;
		public int index;
		public void loadData(BuyerAddressRecord record,final int position){
			info1.setText(record.name+"  "+record.tel);
			info2.setText(record.address);
			if(position!=select){
				checkBox.setChecked(false);
				defaultMark.setVisibility(View.GONE);
			} else {
				checkBox.setChecked(true);
				defaultMark.setVisibility(View.VISIBLE);
			}
			checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener(){

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					// TODO Auto-generated method stub
					if(isChecked){
						select=position;
						chooseAddressListener.chooseAddress(position);
					}else select=-1;
					ExpressAddressAdapter.this.notifyDataSetChanged();
					
				}
				
			});
		}
	}

}
