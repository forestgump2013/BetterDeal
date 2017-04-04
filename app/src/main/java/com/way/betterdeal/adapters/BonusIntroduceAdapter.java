package com.way.betterdeal.adapters;

import java.util.ArrayList;

//import com.taobao.munion.base.Log;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;
import com.way.betterdeal.object.GameBonusRecord;
import com.way.betterdeal.object.PrizeItem;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BonusIntroduceAdapter extends BaseAdapter{
	
	Activity ma;

	ArrayList<GameBonusRecord> prizeItems;
    boolean switchFlag;
    Handler handler;
    int[] slotBonus={R.mipmap.slot_bonus1,R.mipmap.slot_bonus2,R.mipmap.slot_bonus3,R.mipmap.slot_bonus4,R.mipmap.slot_bonus5};
    int count;
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
		count= prizeItems.size();
		if(count>5)
	 		return  count-1;
		else return count;
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
		Holder holder;
		if(convertView==null){
			 holder=new Holder();
			convertView=ma.getLayoutInflater().inflate(R.layout.bonus_detail_item, null);
			holder.subItemView =(RelativeLayout)convertView.findViewById(R.id.subItemView);
			holder.bonusImage=(ImageView)convertView.findViewById(R.id.bonusImageView);
			holder.gameBonusImage=(ImageView)convertView.findViewById(R.id.slotBonusImageView);
			holder.bonusContent=(TextView)convertView.findViewById(R.id.bonusContent);
			holder.bonusPrice=(TextView)convertView.findViewById(R.id.bonusPrice);
			holder.bonusObtainWay=(TextView)convertView.findViewById(R.id.bonusObtainWay);
			convertView.setTag(holder);
		} else {
			holder=(Holder)convertView.getTag();
		}
		holder.setParams(prizeItems.get(position));
		holder.loadData(prizeItems.get(position),position);

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
			}else if(item.gameMark==2){
			//	gameBonusImage.setVisibility(View.VISIBLE);
				gameBonusImage.setImageResource(slotBonus[index]);
			}

			Log.d(StaticValueClass.logTag,"gamePic url:"+item.imgUrl);
			bonusImage.postDelayed(new Runnable() {
				@Override
				public void run() {
					StaticValueClass.asynImageLoader.showImageAsyn(bonusImage,item.imgUrl , R.mipmap.blank_background);
				}
			},500);
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
			
			RelativeLayout.LayoutParams params2=(RelativeLayout.LayoutParams)gameBonusImage.getLayoutParams();
			RelativeLayout.LayoutParams params3=(RelativeLayout.LayoutParams)bonusContent.getLayoutParams();
			
			
			if(item.gameMark==2){
				//slot 
				gameBonusImage.setVisibility(View.VISIBLE);
				params2.width=StaticValueClass.screenWidth/8;
				params2.height=StaticValueClass.screenWidth*34/720;
				params2.addRule(RelativeLayout.ALIGN_TOP, bonusImage.getId());
				params2.addRule(RelativeLayout.RIGHT_OF, bonusImage.getId());
				params2.topMargin=4;
				params2.leftMargin=StaticValueClass.screenWidth/30;
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
