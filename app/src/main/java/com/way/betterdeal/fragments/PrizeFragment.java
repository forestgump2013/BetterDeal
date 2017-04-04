package com.way.betterdeal.fragments;

import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;
import com.way.betterdeal.object.GameBonusRecord;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PrizeFragment extends Fragment {

	MainActivity ma;
	View view;
	Button clickFillBtn,commitBtn;
	Button backBtn;
	ImageButton expandBtn1,expandBtn2;
	TextView title,addressInfo,bonusDetail,bonusObtain,expressInfo;
	LinearLayout.LayoutParams detailParams,obtainParams;
	GameBonusRecord record;
	RelativeLayout adrView1;
	LinearLayout adrView2;
	RotateAnimation rAnimation11,rAnimation12,rAnimation21,rAnimation22;
	boolean up1=false,up2=false;
	RelativeLayout relativeLayout1,relativeLayout2;
	int textHeight;
	
	public PrizeFragment(){

	}
	
	public void setGameBonusRecord(GameBonusRecord rd){
		record=rd;
	}
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		ma=(MainActivity)this.getActivity();
		view=inflater.inflate(R.layout.prize_fragment_layout, container, false);
		adrView1=(RelativeLayout)view.findViewById(R.id.adrView1);
		adrView2=(LinearLayout)view.findViewById(R.id.adrView2);
		title=(TextView)view.findViewById(R.id.title);
		bonusDetail=(TextView)view.findViewById(R.id.bonusDetail);
		bonusObtain=(TextView)view.findViewById(R.id.bonusObtain);
		addressInfo=(TextView)view.findViewById(R.id.addressInfo);
		expressInfo=(TextView)view.findViewById(R.id.expressInfo);
		initParams(view);
	//	initView();
	//	ma.hideTitleView(true);
		if(StaticValueClass.isAfterKitKat)
        	view.setPadding(0, StaticValueClass.statusBarHeight, 0, 0);
		return view;
	//	return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	private void initParams(View parent){
		clickFillBtn=(Button)parent.findViewById(R.id.clickFillBtn);
		commitBtn=(Button)parent.findViewById(R.id.commitBtn);
		expandBtn1=(ImageButton)parent.findViewById(R.id.expandBtn1);
		expandBtn2=(ImageButton)parent.findViewById(R.id.expandBtn2);
		backBtn=(Button)parent.findViewById(R.id.backBtn);
		
		RelativeLayout.LayoutParams params0=(RelativeLayout.LayoutParams)title.getLayoutParams();
		params0.height=StaticValueClass.screenWidth*98/720;
		
		RelativeLayout.LayoutParams params1=(RelativeLayout.LayoutParams)clickFillBtn.getLayoutParams();
		params1.width=StaticValueClass.screenWidth/4;
		params1.height=StaticValueClass.screenWidth*3/40;
		clickFillBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(StaticValueClass.currentBuyer.addressRecords.size()>0)
					ma.loadChooseAddressFragment(record);
				else ma.loadcreateAddressFragment(record);
			}
		});
		detailParams=(LinearLayout.LayoutParams)bonusDetail.getLayoutParams();
		obtainParams=(LinearLayout.LayoutParams)bonusObtain.getLayoutParams();
		bonusDetail.setTextSize(14);
		bonusObtain.setTextSize(14);//13:34
		textHeight=(int)(38*StaticValueClass.density);
		obtainParams.height= detailParams.height=textHeight;
		rAnimation11=new RotateAnimation(0.0f,180.0f,Animation.RELATIVE_TO_SELF,0.5f,
				Animation.RELATIVE_TO_SELF,0.5f);
		rAnimation11.setFillAfter(true);
		rAnimation11.setDuration(300);
		
		rAnimation12=new RotateAnimation(180.0f,360.0f,Animation.RELATIVE_TO_SELF,0.5f,
				Animation.RELATIVE_TO_SELF,0.5f);
		rAnimation12.setFillAfter(true);
		rAnimation12.setDuration(300);
		relativeLayout1=(RelativeLayout)parent.findViewById(R.id.relativeLayout1);
		relativeLayout2=(RelativeLayout)parent.findViewById(R.id.relativeLayout2);
		expandBtn1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(detailParams.height==textHeight){
					detailParams.height=LayoutParams.WRAP_CONTENT;
				}else detailParams.height=textHeight;
				bonusDetail.setLayoutParams(detailParams);
				bonusDetail.postInvalidate();
				expandBtn1.clearAnimation();
			//	expandBtn2.clearAnimation();
				if(up1){
					expandBtn1.startAnimation(rAnimation12);
				}else expandBtn1.startAnimation(rAnimation11);
				up1=!up1;
				
			}
		});
		relativeLayout1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				expandBtn1.performClick();
			}
		});
		rAnimation21=new RotateAnimation(0.0f,180.0f,Animation.RELATIVE_TO_SELF,0.5f,
				Animation.RELATIVE_TO_SELF,0.5f);
		rAnimation21.setFillAfter(true);
		rAnimation21.setDuration(300);
		
		rAnimation22=new RotateAnimation(180.0f,360.0f,Animation.RELATIVE_TO_SELF,0.5f,
				Animation.RELATIVE_TO_SELF,0.5f);
		rAnimation22.setFillAfter(true);
		rAnimation22.setDuration(300);
		
		expandBtn2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(obtainParams.height==textHeight){
					obtainParams.height=LayoutParams.WRAP_CONTENT;
				}else obtainParams.height=textHeight;
				bonusObtain.setLayoutParams(obtainParams);
				bonusObtain.postInvalidate();
				expandBtn2.clearAnimation();
			//	expandBtn1.clearAnimation();
				if(up2){
					expandBtn2.startAnimation(rAnimation22);
				}else expandBtn2.startAnimation(rAnimation21);
				up2=!up2;
				
			}
		});
		relativeLayout2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				expandBtn2.performClick();
			}
		});
		commitBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(record!=null && record.flag==1){
					record.flag=2;
					initView();
					ma.updateGameBonusRecord(record);
				}
			}
		});
		//backBtn.setImageBitmap(StaticValueClass.getBackIcon(BitmapFactory.decodeResource(getResources(), R.drawable.expand_down)));
		Bitmap backmark=BitmapFactory.decodeResource(ma.getResources(), R.mipmap.expand_icon);
    	Drawable leftDrawable=new BitmapDrawable(StaticValueClass.getBackIcon(backmark));
    	leftDrawable.setBounds(0, 0, backmark.getWidth(), backmark.getHeight());
    	backBtn.setCompoundDrawables(leftDrawable, null, null, null);
		backBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ma.onBackPressed();
			}
		});
	}
	
	private int getFontHeight(float fontSize){
		Paint paint = new Paint();  
	    paint.setTextSize(fontSize);  
	    Rect rect=new Rect();
	    paint.getTextBounds("逗", 0, 1, rect);
	    FontMetrics fm = paint.getFontMetrics();  
	    return rect.height();
	//    return (int) Math.ceil(fm.descent - fm.ascent) ;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initView();
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if(!hidden){
			initView();
		}

	}

	private void initView(){
		if(record.flag==0){
			adrView1.setVisibility(View.VISIBLE);
			adrView2.setVisibility(View.GONE);
			expressInfo.setText("");
		}else if(record.flag>=1){
			adrView2.setVisibility(View.VISIBLE);
			adrView1.setVisibility(View.GONE);
			addressInfo.setText("收  货  人 : "+record.buyerAddressRecord.name+"  "+record.buyerAddressRecord.tel+"\n"+
					"详细地址 : "+record.buyerAddressRecord.address);
			expressInfo.setText("");
			if(record.flag==2){
				commitBtn.setVisibility(View.GONE);
				expressInfo.setText("请注意查看快递信息。");
			}else if(record.flag==3){
				
			}
		}
	}
	

}
