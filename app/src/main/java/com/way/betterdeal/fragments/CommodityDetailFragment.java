package com.way.betterdeal.fragments;

import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;
import com.way.betterdeal.object.Commodity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CommodityDetailFragment extends Fragment {
	
	MainActivity ma;
	
	View view;
	Button backBtn,drawBtn;
	WebView webView;
	String webUrl="";
	Commodity currentCommodity;
	private CheckBox favouriteCheck;
	TextView partText11,partText12,partText13,timeText;
	RelativeLayout part2,part1;
	public CommodityDetailFragment(){

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		ma=(MainActivity)this.getActivity();
		view=inflater.inflate(R.layout.commodity_detail_fragment, container, false);
		backBtn=(Button)view.findViewById(R.id.backBtn);
		drawBtn=(Button)view.findViewById(R.id.drawBtn);
		webView=(WebView)view.findViewById(R.id.webView);
		part2=(RelativeLayout)view.findViewById(R.id.part2);
		part1=(RelativeLayout)view.findViewById(R.id.part1);
		timeText=(TextView)view.findViewById(R.id.timeText);
		partText11=(TextView) view.findViewById(R.id.partText11);
		partText12=(TextView) view.findViewById(R.id.partText12);
		partText13=(TextView) view.findViewById(R.id.partText13);
		favouriteCheck=(CheckBox)view.findViewById(R.id.favouriteCheck);
		//
		Bitmap backmark= BitmapFactory.decodeResource(this.getActivity().getResources(), R.mipmap.expand_icon);
		Drawable leftDrawable=new BitmapDrawable(StaticValueClass.getBackIcon(backmark));
		leftDrawable.setBounds(0, 0, backmark.getWidth(), backmark.getHeight());
		backBtn.setBackground(leftDrawable);
		//
		WebSettings webSettings=webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		webView.loadUrl(currentCommodity.webLink);
		if(StaticValueClass.isAfterKitKat)
			view.setPadding(0, StaticValueClass.statusBarHeight, 0, 0);
		initFunction();
		return view;
	}

	public void setCurrentCommodity(Commodity commo){
		currentCommodity=commo;
	}

	private void initFunction(){
		ma=(MainActivity)this.getActivity();
		backBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ma.onBackPressed();
			}
		});
		if(currentCommodity!=null){

			//check coupon.
			if (!currentCommodity.couponLink.equals("")){
				drawBtn.setVisibility(View.VISIBLE);
				RelativeLayout.LayoutParams params=(RelativeLayout.LayoutParams)drawBtn.getLayoutParams();
				params.width=StaticValueClass.dip2px(ma,80);
				partText13.setVisibility(View.GONE);
				partText11.setText("领券后购买");
				partText12.setText("仅"+currentCommodity.price+"元");
			}else {
				drawBtn.setVisibility(View.INVISIBLE);
				RelativeLayout.LayoutParams params=(RelativeLayout.LayoutParams)drawBtn.getLayoutParams();
				params.width=1;
				// check price differ.
				if(currentCommodity.price!=currentCommodity.reserve_price){
					partText11.setText("拍下自动变成");
					partText13.setVisibility(View.VISIBLE);
					partText12.setText(currentCommodity.price+"元");
				}
			}
			//check left time.
			currentCommodity.computeLeftTime();
			if(currentCommodity.leftTime.equals("")){
				part2.setVisibility(View.GONE);
				RelativeLayout.LayoutParams params=(RelativeLayout.LayoutParams)part1.getLayoutParams();
				params.removeRule(RelativeLayout.ALIGN_PARENT_TOP);
				params.addRule(RelativeLayout.CENTER_VERTICAL);
				part1.setLayoutParams(params);

			}else {
				part2.setVisibility(View.VISIBLE);
				timeText.setText(currentCommodity.leftTime);
				RelativeLayout.LayoutParams params=(RelativeLayout.LayoutParams)part1.getLayoutParams();
				params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
				params.removeRule(RelativeLayout.CENTER_VERTICAL);
				part1.setLayoutParams(params);
				RelativeLayout.LayoutParams params1=(RelativeLayout.LayoutParams)part2.getLayoutParams();
				params1.addRule(RelativeLayout.ALIGN_LEFT,part1.getId());
				params1.addRule(RelativeLayout.ALIGN_RIGHT,part1.getId());
			}
		}
		favouriteCheck.setChecked(false);
		favouriteCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked) {
					StaticValueClass.currentBuyer.favouriteItems.add(currentCommodity);
					favouriteCheck.startAnimation(AnimationUtils.loadAnimation(ma, R.anim.moveup1));
				}else{
					StaticValueClass.currentBuyer.favouriteItems.remove(currentCommodity);
				//	favouriteCheck.startAnimation(AnimationUtils.loadAnimation(ma, R.anim.slide_top_out));
				}
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		favouriteCheck.setChecked(false);
	}
}
