package com.way.betterdeal.adapters;

import java.util.ArrayList;

import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;
import com.way.betterdeal.object.GameBonusRecord;

import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GameBonusRecordsAdapter extends BaseAdapter {
	
	MainActivity ma;
	ArrayList<GameBonusRecord> records;
	LayoutInflater inflater;
	Holder holder;
	int direct;
	Typeface typeface;
	
	public GameBonusRecordsAdapter(MainActivity mm,ArrayList<GameBonusRecord> rs){
		ma=mm;
		records=rs;
		inflater=ma.getLayoutInflater();
		typeface =Typeface.createFromAsset(ma.getAssets(),"fonts/hanyi_thin_round1.ttf");
	}
	
	public void loadData(int d,ArrayList<GameBonusRecord> rs){
		direct=d;
		records=rs;
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
		if(convertView==null){
			convertView=inflater.inflate(R.layout.bonus_record_layout, null);
			holder=new Holder();
			holder.topView=(RelativeLayout)convertView.findViewById(R.id.topView);
			holder.background=(ImageView)convertView.findViewById(R.id.background);
			holder.bonusImage=(ImageView)convertView.findViewById(R.id.bonusImageView);
			holder.mallMark=(ImageView)convertView.findViewById(R.id.mallMark);
			holder.mallTitle=(TextView)convertView.findViewById(R.id.mallTitle);
			holder.bonusTitle=(TextView)convertView.findViewById(R.id.bonusTitle);
			holder.priceText=(TextView)convertView.findViewById(R.id.priceText);
			holder.bonusPrice=(TextView)convertView.findViewById(R.id.bonusPrice);
			holder.bonusPriceFloat=(TextView)convertView.findViewById(R.id.bonusPriceFloat);
			holder.bonusDate=(TextView)convertView.findViewById(R.id.bonusDate);
			holder.bonusReason=(TextView)convertView.findViewById(R.id.bonusReason);
			holder.bonusDetailBtn=(Button)convertView.findViewById(R.id.bonusDetailBtn);
			holder.grayLine=(TextView)convertView.findViewById(R.id.grayLine);
		//	holder.setFont();
			holder.view=convertView;
			convertView.setTag(holder);
			
		} else {
			holder=(Holder)convertView.getTag();
		}
		 holder.measure();
		 holder.loadData(records.get(position));
		return convertView;
	}
	
	public class Holder{
		public View view;
		public RelativeLayout topView;
		public ImageView background,bonusImage,mallMark;
		public TextView bonusTitle,priceText,bonusPrice,bonusPriceFloat,bonusDate,bonusReason,grayLine,mallTitle;
		public Button bonusDetailBtn;
		
		public void setFont(){
			bonusTitle.setTypeface(typeface);
			bonusPrice.setTypeface(typeface);
			bonusPriceFloat.setTypeface(typeface);
		}
		public void loadData(final GameBonusRecord record){
			
			bonusTitle.setText(record.bonusTitle);
			bonusPrice.setText(" "+record.bonusPrice);
			bonusDetailBtn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(direct==1){
					ma.loadPrizeFragment(record);
					Log.d("bonusDetailBtn", "pressed");
					}else if(direct==2){
						// purchase details 
					}
				}
			});
			StaticValueClass.asynImageLoader.showImageAsyn(bonusImage, record.imgUrl, R.mipmap.blank_background);
			switch(direct){
			case 1:
				  bonusDate.setText("");
				  break;
			case 2:bonusDate.setText(record.bonusDate);
					switch(record.mallMark){
					case 1: mallMark.setImageResource(R.mipmap.taobao_c_mark);
							mallTitle.setText("淘宝网");
							break;
					case 2: mallMark.setImageResource(R.mipmap.tianmao_c_mark);
							mallTitle.setText("天猫商城");
							break;
					}
					priceText.setText("¥");
					String price=""+record.bonusPrice;
					String[] prices=price.split(".");
					bonusPrice.setText("35.");
					bonusPriceFloat.setText("00");
					
					break;
			case 3://bonusDate.setText(record.bonusDate); 
				   bonusReason.setText(record.bonusDate);
				   priceText.setText(record.bonusReason);
				   bonusReason.setTextSize(14);
				   priceText.setTextSize(14);
			}
			
		}
		public void measure(){
			
			RelativeLayout.LayoutParams params0=(RelativeLayout.LayoutParams)background.getLayoutParams();
			
			RelativeLayout.LayoutParams params1=(RelativeLayout.LayoutParams)bonusImage.getLayoutParams();
		//	params1.addRule(RelativeLayout.CENTER_VERTICAL);
		//	bonusImage.setVisibility(View.INVISIBLE);
			RelativeLayout.LayoutParams params2=(RelativeLayout.LayoutParams)bonusTitle.getLayoutParams();
			params2.addRule(RelativeLayout.ALIGN_TOP, bonusImage.getId());
			params2.addRule(RelativeLayout.RIGHT_OF, bonusImage.getId());
			params2.leftMargin=StaticValueClass.screenWidth*5/72;
			
			RelativeLayout.LayoutParams params3=(RelativeLayout.LayoutParams)priceText.getLayoutParams();
			params3.addRule(RelativeLayout.ALIGN_BOTTOM, bonusImage.getId());
			params3.addRule(RelativeLayout.RIGHT_OF, bonusImage.getId());
			params3.leftMargin=StaticValueClass.screenWidth*5/72;
			
			RelativeLayout.LayoutParams params31=(RelativeLayout.LayoutParams)bonusPrice.getLayoutParams();
			params31.addRule(RelativeLayout.ALIGN_BOTTOM, bonusImage.getId());
			params31.addRule(RelativeLayout.RIGHT_OF, priceText.getId());
			
			RelativeLayout.LayoutParams params32=(RelativeLayout.LayoutParams)bonusPriceFloat.getLayoutParams();
			
			
			RelativeLayout.LayoutParams params4=(RelativeLayout.LayoutParams)bonusDetailBtn.getLayoutParams();
			
			switch(direct){
			case 1: //  as game bonus records
					int padding=StaticValueClass.dip2px(ma, 8);
					view.setPadding(padding, 0, padding, 0);
					int backWidth=StaticValueClass.screenWidth-2*padding;
				    params0.height=backWidth*318/688;
				    params1.leftMargin=params1.topMargin=backWidth*50/688;
				    params1.width=params1.height=backWidth*200/688;
				    params2.rightMargin=StaticValueClass.dip2px(ma, 18);
				    background.setVisibility(View.VISIBLE);
					bonusImage.setBackgroundResource(0);
				//	bonusImage.setPadding(bonusImage.getPaddingLeft(), bonusImage.getPaddingTop(), bonusImage.getPaddingRight(), bonusImage.getPaddingBottom());
					bonusTitle.setTextColor(Color.WHITE);
					priceText.setTextColor(Color.WHITE);
					bonusDetailBtn.setVisibility(View.VISIBLE);
					bonusDetailBtn.setText("");
					topView.setVisibility(View.GONE);
					params4.width=params4.height=StaticValueClass.screenWidth/6;
					params4.removeRule(RelativeLayout.ALIGN_BOTTOM);
					params4.addRule(RelativeLayout.ALIGN_BOTTOM,background.getId());
					params4.bottomMargin=StaticValueClass.screenWidth*46/720;
					bonusReason.setVisibility(View.GONE);
					grayLine.setVisibility(View.GONE);
					break;
			case 2: topView.setVisibility(View.VISIBLE);
				    params0.height=StaticValueClass.screenWidth*211/720;
				    params1.topMargin=StaticValueClass.screenWidth*24/720;
				    params1.width=params1.height=StaticValueClass.screenWidth*164/720;
				    background.setVisibility(View.INVISIBLE);
					bonusImage.setBackgroundResource(R.drawable.gray_shade);
					bonusImage.setPadding(4, 4, 4, 4);
					bonusTitle.setTextColor(Color.rgb(45, 45, 45));
					priceText.setTextSize(14f);
					priceText.setTextColor(Color.rgb(45, 45, 45));
					bonusPrice.setTextColor(Color.rgb(45, 45, 45));
					bonusPriceFloat.setTextColor(Color.rgb(45, 45, 45));
					bonusDetailBtn.setBackgroundResource(R.drawable.round_pink);
					bonusDetailBtn.setTextColor(Color.WHITE);
					bonusDetailBtn.setText("查看详情");
					bonusDetailBtn.setVisibility(View.VISIBLE);
					
					params31.bottomMargin=4;
					params31.leftMargin=4;
					params32.addRule(RelativeLayout.ALIGN_BOTTOM, bonusImage.getId());
					params32.addRule(RelativeLayout.RIGHT_OF, bonusPrice.getId());
					params32.bottomMargin=8;
					params4.width=StaticValueClass.screenWidth*15/72;
					params4.height=StaticValueClass.screenWidth*44/720;
					params4.bottomMargin=4;
					params4.addRule(RelativeLayout.ALIGN_BOTTOM, bonusImage.getId());
					bonusReason.setVisibility(View.GONE);
					grayLine.setVisibility(View.GONE);
				//	bonusDetailBtn
					break;
			case 3: // as welfare record.
				    params0.height=StaticValueClass.screenWidth*318/720-30; 
					params1.topMargin=(StaticValueClass.screenWidth*318/720-30-StaticValueClass.screenWidth*20/72)/2;
					params1.width=params1.height=StaticValueClass.screenWidth*20/72;
				    bonusDetailBtn.setVisibility(View.INVISIBLE);
				    topView.setVisibility(View.GONE);
					bonusImage.setBackgroundResource(R.drawable.gray_shade);
				//	bonusImage.setPadding(bonusImage.getPaddingLeft()+4, bonusImage.getPaddingTop()+4, bonusImage.getPaddingRight()+4, bonusImage.getPaddingBottom()+4);
					bonusImage.setPadding(4, 4, 4, 4);
					background.setVisibility(View.INVISIBLE);
					bonusTitle.setTextColor(Color.rgb(45, 45, 45));
					priceText.setTextColor(Color.rgb(45, 45, 45));
					bonusReason.setVisibility(View.VISIBLE);
					bonusDate.setVisibility(View.GONE);
					bonusPrice.setVisibility(View.INVISIBLE);
					RelativeLayout.LayoutParams params5=(RelativeLayout.LayoutParams)bonusReason.getLayoutParams();
					params5.addRule(RelativeLayout.ALIGN_BOTTOM, priceText.getId());
					grayLine.setVisibility(View.VISIBLE);
			}
		}
		
	}

}
