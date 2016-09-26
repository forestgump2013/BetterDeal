package com.way.betterdeal.adapters;

import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SimpleAdapter extends BaseAdapter {
	
	MainActivity ma;
	int plotFlag=0;
	SimpleHolder holder;
	
	public SimpleAdapter(MainActivity mm){
		ma=mm;
	}
	
	public void setPlotFlag(int flag){
		plotFlag=flag;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(plotFlag==0)
			return 4;
		else if(plotFlag==1){
			return 4;
		}
		return 0;
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
			convertView=ma.getLayoutInflater().inflate(R.layout.single_item, null);
			holder =new SimpleHolder();
			holder.grayLine=(TextView)convertView.findViewById(R.id.grayLine);
			holder.textView=(TextView)convertView.findViewById(R.id.textView);
			holder.markImage=(ImageView)convertView.findViewById(R.id.markImage);
			holder.expandIcon=(ImageView)convertView.findViewById(R.id.expandIcon);
			holder.parentView=(RelativeLayout)convertView.findViewById(R.id.parentView);
			convertView.setTag(holder);
		}else holder=(SimpleHolder)convertView.getTag();
		
		holder.loadData(position,convertView);
		
		return convertView;
	}
	
	class SimpleHolder{
		public TextView grayLine,textView;
		public ImageView markImage,expandIcon;
		public RelativeLayout parentView;
		public void loadData(int position,View parent){
			if(plotFlag==0){
				textView.setTextColor(Color.BLACK);
				switch(position){
				case 1: holder.textView.setText("用户帮助");
						holder.markImage.setImageResource(R.mipmap.help_mark);
						holder.grayLine.setVisibility(View.GONE);
				        break;
				case 2: textView.setText("意见与反馈");
						markImage.setImageResource(R.mipmap.callback_mark);
						grayLine.setVisibility(View.GONE);
				         break;
				case 3: textView.setText("设置");
						markImage.setImageResource(R.mipmap.setting_mark);
						/*
						textView.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								ma.loadConfigrationFragment();
							}
						}); */
						grayLine.setVisibility(View.GONE);
						break;
				case 0: textView.setText("浏览足迹");
						markImage.setImageResource(R.mipmap.trace_mark);
						grayLine.setVisibility(View.VISIBLE);
				}
				grayLine.getLayoutParams().height=StaticValueClass.screenWidth*16/720;
				textView.getLayoutParams().height=StaticValueClass.screenWidth/8;
			}else if(plotFlag==1){
			//	ViewGroup.LayoutParams params0=(ViewGroup.LayoutParams)parentView.getLayoutParams();
			//	params0.width=StaticValueClass.screenWidth*154/720;
				textView.setTextColor(Color.WHITE);
				textView.setTextSize(14);
				textView.setPadding(0,8,0,8);
				expandIcon.setVisibility(View.GONE);
				RelativeLayout.LayoutParams params=(RelativeLayout.LayoutParams)textView.getLayoutParams();
				params.leftMargin=16;
				RelativeLayout.LayoutParams params1=(RelativeLayout.LayoutParams)markImage.getLayoutParams();
				markImage.setPadding(0,0,0,0);
				params1.leftMargin=8;
				params1.width=params1.height=26;
				markImage.setVisibility(View.GONE);
				switch(position){
				case 0: //markImage.setVisibility(View.GONE);
						grayLine.setVisibility(View.INVISIBLE);
						textView.setText("  淘    宝  ");
						break;
				case 1:textView.setText("  天    猫  ");
					//	markImage.setVisibility(View.VISIBLE);
						grayLine.setVisibility(View.INVISIBLE);
					//	markImage.setImageResource(R.drawable.taobao_icon);
			    		break;
				case 2: textView.setText("  京    东  ");
					//	markImage.setVisibility(View.VISIBLE);
						grayLine.setVisibility(View.INVISIBLE);
					//	markImage.setImageResource(R.drawable.tianmao_c_mark);
						break;
				case 3: textView.setText("苏宁易购");
			     	//	markImage.setVisibility(View.VISIBLE);
				       grayLine.setVisibility(View.INVISIBLE);
				   //	markImage.setImageResource(R.drawable.tianmao_c_mark);
				    	break;
				}
			}
		}
	}

}
