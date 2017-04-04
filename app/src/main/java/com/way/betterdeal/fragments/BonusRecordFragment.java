package com.way.betterdeal.fragments;

import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;
import com.way.betterdeal.adapters.GameBonusRecordsAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BonusRecordFragment extends Fragment {

	MainActivity ma;
	View view=null;
	ImageView nullImage,welfare_headerIamge;
	TextView titleText,titleCurtain,nullText,titleDivederLine1;
	Button backBtn,welfareBtn;
	ListView recordsListView;
	GameBonusRecordsAdapter gameBonusRecordsAdapter;
	LinearLayout nullView;
	Button nullBtn1,nullBtn2;
	int direct;
	
	public BonusRecordFragment(){

	}
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
			ma=(MainActivity)this.getActivity();
			view=inflater.inflate(R.layout.bonus_record_fragment, container, false);
			backBtn=(Button)view.findViewById(R.id.backBtn);
			welfareBtn=(Button)view.findViewById(R.id.welfareBtn);
			nullView=(LinearLayout)view.findViewById(R.id.nullView);
			nullImage=(ImageView)view.findViewById(R.id.nullImage);
			nullText=(TextView)view.findViewById(R.id.nullText);
			titleDivederLine1=(TextView)view.findViewById(R.id.titleDivederLine1);
			nullBtn1=(Button)view.findViewById(R.id.nullBtn1);
			nullBtn2=(Button)view.findViewById(R.id.nullBtn2);
			recordsListView=(ListView)view.findViewById(R.id.recordsListView);
			gameBonusRecordsAdapter=new GameBonusRecordsAdapter(ma,StaticValueClass.currentBuyer.bonusRecords);
			recordsListView.setAdapter(gameBonusRecordsAdapter);
			recordsListView.setCacheColorHint(0);
			recordsListView.setVerticalScrollBarEnabled(false);
			initParams(view);
	    view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	    if(StaticValueClass.isAfterKitKat)
        	view.setPadding(0, StaticValueClass.statusBarHeight, 0, 0);
		return view;
	}
	
	public void setDirect(int d){
		direct=d;
	}
	
	public void switchView(){
		switch(direct){
		case 1: titleText.setText("我的中奖记录");
		        titleDivederLine1.setVisibility(View.GONE);
			    titleCurtain.setText("好运连连    惊喜不断");
			    titleCurtain.setVisibility(View.VISIBLE);
			    welfareBtn.setVisibility(View.GONE);
			    recordsListView.setDividerHeight(16);
			  //  recordsListView.setDivider(null);
			    recordsListView.setDivider(ma.getResources().getDrawable(R.drawable.white_space));
				gameBonusRecordsAdapter.loadData(direct, StaticValueClass.currentBuyer.bonusRecords);
				if(StaticValueClass.currentBuyer.bonusRecords.size()==0){
					nullView.setVisibility(View.VISIBLE);
					nullImage.setImageResource(R.mipmap.null_welfare);
					nullText.setText("你还没有中奖哦~");
					nullBtn1.setVisibility(View.GONE);
					nullBtn2.setVisibility(View.GONE);
				}else nullView.setVisibility(View.GONE);
				recordsListView.removeHeaderView(welfare_headerIamge);
				break;
		case 2: titleText.setText("全部订单"); 
			  //  titleCurtain.setText("您的购买    绽放精彩");
			    titleCurtain.setVisibility(View.GONE);
			    welfareBtn.setVisibility(View.GONE);
				gameBonusRecordsAdapter.loadData(direct, StaticValueClass.currentBuyer.purchaseRecords);
				recordsListView.setDivider(ma.getResources().getDrawable(R.drawable.double_gray_line));
				if(StaticValueClass.currentBuyer.purchaseRecords.size()==0){
					nullView.setVisibility(View.VISIBLE);
					nullImage.setImageResource(R.mipmap.null_purchase);
					nullText.setText("你还没有相关订单哦~");
					nullBtn1.setVisibility(View.GONE);
					nullBtn2.setVisibility(View.GONE);
				}else nullView.setVisibility(View.GONE);
				recordsListView.removeHeaderView(welfare_headerIamge);
				break;
		case 3: titleText.setText("到手福利"); 
				titleDivederLine1.setVisibility(View.GONE);
			  //  titleCurtain.setText("拿福利    不手软    赚金币换更多福利");
			    titleCurtain.setVisibility(View.GONE);
			    welfareBtn.setVisibility(View.VISIBLE);
			    if(recordsListView.getHeaderViewsCount()==0){
			    	recordsListView.addHeaderView(welfare_headerIamge);
			    	recordsListView.setHeaderDividersEnabled(false);
			    }
				gameBonusRecordsAdapter.loadData(direct, StaticValueClass.currentBuyer.welfareRecords);
				recordsListView.setDivider(null);
			//	recordsListView.setDivider(ma.getResources().getDrawable(R.drawable.horizontal_gray_divider_line));
				if(StaticValueClass.currentBuyer.welfareRecords.size()==0){
					nullView.setVisibility(View.VISIBLE);
					nullImage.setImageResource(R.mipmap.null_welfare);
					nullText.setText("你还没有得到福利哦~");
					nullBtn1.setVisibility(View.VISIBLE);
					nullBtn2.setVisibility(View.VISIBLE);
				}else nullView.setVisibility(View.GONE);
			//
			welfareBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					ma.loadEarnsFragment();
				}
			});
		}
		gameBonusRecordsAdapter.notifyDataSetChanged();
	}
	
	private void initParams(View view){
		
		Bitmap backmark=BitmapFactory.decodeResource(ma.getResources(), R.mipmap.expand_icon);
    	Drawable leftDrawable=new BitmapDrawable(StaticValueClass.getBackIcon(backmark));
    	leftDrawable.setBounds(0, 0, backmark.getWidth(), backmark.getHeight());
    	backBtn.setCompoundDrawables(leftDrawable, null, null, null);
		
		int scale=StaticValueClass.screenWidth;
		
		welfare_headerIamge=new ImageView(ma);
		welfare_headerIamge.setLayoutParams(new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT, scale*217/720));
		welfare_headerIamge.setScaleType(ScaleType.FIT_XY);
		welfare_headerIamge.setImageResource(R.mipmap.welfare_header);
		
		FrameLayout.LayoutParams wbparams=(FrameLayout.LayoutParams)welfareBtn.getLayoutParams();
		wbparams.height=scale*52/720;
		wbparams.width=scale*232/720;
		wbparams.topMargin=scale*34/720;
		wbparams.leftMargin=scale*386/720;		
		
		titleText=(TextView)view.findViewById(R.id.titleText);
		titleCurtain=(TextView)view.findViewById(R.id.titleCurtain);
		titleCurtain.setTypeface(Typeface.createFromAsset(ma.getAssets(),"fonts/huagang_girl.ttf"));
		
		RelativeLayout.LayoutParams tParams=(RelativeLayout.LayoutParams)titleText.getLayoutParams();
		tParams.height=scale*98/720;
		
		LinearLayout.LayoutParams params=(LinearLayout.LayoutParams)titleCurtain.getLayoutParams();
		params.height=scale*106/720;
		
		LinearLayout.LayoutParams params1=(LinearLayout.LayoutParams)nullImage.getLayoutParams();
		params1.width=params1.height=scale*20/72;
		backBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ma.onBackPressed();
			}
		});
	}
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
	@Override
	public void onAttach(Context context) {
		// TODO Auto-generated method stub
		super.onAttach(context);
	}
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d(StaticValueClass.logTag,"BonusRecordFragment onHiddenChanged ");
		switchView();
	}


	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		Log.d(StaticValueClass.logTag,"BonusRecordFragment onHiddenChanged ");
		if(!hidden) switchView();
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
	}
}
