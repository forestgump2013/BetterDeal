package com.way.betterdeal.adapters;

import java.util.ArrayList;

//import com.taobao.munion.base.Log;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;
import com.way.betterdeal.object.GameBonusRecord;
import com.way.betterdeal.object.PrizeItem;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BonusIntroduceAdapter extends BaseAdapter{
	
	Activity ma;
	Holder holder;
	ArrayList<GameBonusRecord> prizeItems;
    boolean switchFlag;
    Handler handler;
    int[] slotBonus={R.mipmap.slot_bonus1,R.mipmap.slot_bonus2,R.mipmap.slot_bonus3,R.mipmap.slot_bonus4,R.mipmap.slot_bonus5};
    
    public  static interface LoadFinishListener{
    	void finishLoad();
    }
    
    LoadFinishListener loadFinishListener;
    public void setLoadFinishListener(LoadFinishListener listener){
    	loadFinishListener=listener;
    }
	
	public BonusIntroduceAdapter(Activity aa){
		ma=aa;
		handler=new Handler();
	}
	
	public void setDataSource(ArrayList<GameBonusRecord> data){
		prizeItems=data;
		switchFlag=true;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(prizeItems==null)
			return 0;
	 	return  prizeItems.size();
	//	return 3;
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
			
			holder=new Holder();
			convertView=ma.getLayoutInflater().inflate(R.layout.bonus_detail_item, null);
			holder.subItemView =(RelativeLayout)convertView.findViewById(R.id.subItemView);
			holder.bonusImage=(ImageView)convertView.findViewById(R.id.bonusImageView);
			holder.gameBonusImage=(ImageView)convertView.findViewById(R.id.slotBonusImageView);
			holder.bonusContent=(TextView)convertView.findViewById(R.id.bonusContent);
			holder.bonusPrice=(TextView)convertView.findViewById(R.id.bonusPrice);
			holder.bonusObtainWay=(TextView)convertView.findViewById(R.id.bonusObtainWay);
			holder.setParams(prizeItems.get(position));
			convertView.setTag(holder);
		} else {
			holder=(Holder)convertView.getTag();
		}
	//	if(switchFlag){
			holder.setParams(prizeItems.get(position));
	//		switchFlag=false;
	//	}
	
		holder.loadData(prizeItems.get(position),position);
	//	holder.setParams(prizeItems.get(position));
	//	ViewGroup.LayoutParams params=convertView.getLayoutParams();
	//	params.height=ViewGroup.LayoutParams.WRAP_CONTENT;
		if(position==prizeItems.size()-1){
			loadFinishListener.finishLoad();
		}
		return convertView;
	}
	
	private class Holder {
		
		RelativeLayout subItemView;
		ImageView bonusImage,gameBonusImage;
		TextView bonusContent,bonusPrice,bonusObtainWay;
		
		public void loadData(final GameBonusRecord item,int index){
			if(item.gameMark==1){
			//	gameBonusImage.setVisibility(View.GONE);
				gameBonusImage.setImageResource(0);
			}else if(item.gameMark==0){
			//	gameBonusImage.setVisibility(View.VISIBLE);
				gameBonusImage.setImageResource(slotBonus[index]);
			}
			
			handler.postDelayed(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					StaticValueClass.asynImageLoader.showImageAsyn(bonusImage, item.imgUrl, R.mipmap.blank_background);
				}
				
			}, 300);
			bonusPrice.setText("价格 : ¥ "+item.bonusPrice);
			bonusObtainWay.setText("领取方式 : "+item.bonusObtain);
			
		}
		
		public void setParams(GameBonusRecord item){
			//Log.d(arg0, arg1)
			LinearLayout.LayoutParams params0=(LinearLayout.LayoutParams)subItemView.getLayoutParams();
			params0.height=StaticValueClass.screenWidth/8+StaticValueClass.screenWidth/15;
			
			RelativeLayout.LayoutParams params1=(RelativeLayout.LayoutParams)bonusImage.getLayoutParams();
			params1.height=params1.width=StaticValueClass.screenWidth/8;
			params1.addRule(RelativeLayout.CENTER_VERTICAL);
		//	params1.topMargin=StaticValueClass.screenWidth/30+5;
		//	params1.bottomMargin=params1.topMargin-5;
		//	bonusImage.setId(101);
			
			RelativeLayout.LayoutParams params2=(RelativeLayout.LayoutParams)gameBonusImage.getLayoutParams();
			RelativeLayout.LayoutParams params3=(RelativeLayout.LayoutParams)bonusContent.getLayoutParams();
			
			
			if(item.gameMark==0){
				//slot 
				gameBonusImage.setVisibility(View.VISIBLE);
				params2.width=StaticValueClass.screenWidth/8;
				params2.height=StaticValueClass.screenWidth*34/720;
				params2.addRule(RelativeLayout.ALIGN_TOP, bonusImage.getId());
				params2.addRule(RelativeLayout.RIGHT_OF, bonusImage.getId());
				params2.topMargin=4;
				params2.leftMargin=StaticValueClass.screenWidth/30;
			//	gameBonusImage.setId(102);
				params3.removeRule(RelativeLayout.RIGHT_OF);
				params3.addRule(RelativeLayout.ALIGN_BOTTOM, gameBonusImage.getId());
				params3.addRule(RelativeLayout.RIGHT_OF, gameBonusImage.getId());
			//	if(item.gameMark==0)
			    	params3.leftMargin=15;
			//	else params3.leftMargin=2;
			}else if(item.gameMark==1){
				//nine panes
				gameBonusImage.setVisibility(View.GONE);
				params3.removeRule(RelativeLayout.RIGHT_OF);
				params3.addRule(RelativeLayout.ALIGN_TOP, bonusImage.getId());
				params3.addRule(RelativeLayout.RIGHT_OF, bonusImage.getId());
				params3.topMargin=4;
				params3.leftMargin=StaticValueClass.screenWidth/30;
			//	params2.width=1;
			} 
			bonusContent.postInvalidate();
			RelativeLayout.LayoutParams params4=(RelativeLayout.LayoutParams)bonusPrice.getLayoutParams();
			
			params4.addRule(RelativeLayout.RIGHT_OF, bonusImage.getId());
			params4.addRule(RelativeLayout.ALIGN_BOTTOM, bonusImage.getId());
			params4.leftMargin=StaticValueClass.screenWidth/30;
			params4.bottomMargin=4;
			
			
		}
		
	}

}
