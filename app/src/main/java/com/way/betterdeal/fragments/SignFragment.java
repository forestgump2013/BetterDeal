package com.way.betterdeal.fragments;

import java.util.ArrayList;
import java.util.Date;

import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;
import com.way.betterdeal.view.HuaKangTextView;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SignFragment extends Fragment {

	MainActivity ma;
	View view;
	RelativeLayout wheelBackground;
	ImageView structure1,skywheel,rulesImage,background;
	Button coinBtn,signBtn;
    Animation clockwise,anti_clockwise,anti_clockwise2,anti_clockwise3,anti_clockwise4
    ,anti_clockwise6,anti_clockwise5,anti_clockwise7,sign_animation,sign_out_animation;
    HuaKangTextView bucket1,bucket2,bucket3,bucket4,bucket5,bucket6,bucket7,signAnimationText;
    ArrayList<HuaKangTextView> buckets;
    TextView reference;
    int differ,moveForwards;



	public SignFragment(){

	}
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		ma=(MainActivity)this.getActivity();
		view=inflater.inflate(R.layout.sign_fragment, container, false);
		wheelBackground=(RelativeLayout)view.findViewById(R.id.wheelBackground);
		structure1=(ImageView)view.findViewById(R.id.skywheel_structure1);
		skywheel=(ImageView)view.findViewById(R.id.skywheel);
		background=(ImageView)view.findViewById(R.id.background);
		reference=(TextView)view.findViewById(R.id.reference);
		bucket1=(HuaKangTextView)view.findViewById(R.id.bucket1);
		bucket2=(HuaKangTextView)view.findViewById(R.id.bucket2);
		bucket3=(HuaKangTextView)view.findViewById(R.id.bucket3);
		bucket4=(HuaKangTextView)view.findViewById(R.id.bucket4);
		bucket5=(HuaKangTextView)view.findViewById(R.id.bucket5);
		bucket6=(HuaKangTextView)view.findViewById(R.id.bucket6);
		bucket7=(HuaKangTextView)view.findViewById(R.id.bucket7);
		signAnimationText=(HuaKangTextView)view.findViewById(R.id.signAnimationText);
		signAnimationText.setTextColor(Color.rgb(252, 51, 102));
		rulesImage=(ImageView)view.findViewById(R.id.rulesImage);
		coinBtn=(Button)view.findViewById(R.id.coinBtn);
		signBtn=(Button)view.findViewById(R.id.signBtn);
		/*
		 if(StaticValueClass.isAfterKitKat)
	        	view.setPadding(0, StaticValueClass.statusBarHeight, 0, 0);
		 */
		initAnimations();
		initFunction();
	    initSkyWheel();
		 reMeasure();
		 
	     signBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(differ==0) {
					Toast.makeText(ma, "    今日已签到 ！\n"+
							"已连续签到"+StaticValueClass.currentBuyer.consecutive_sign_days+"天。", Toast.LENGTH_SHORT).show();
					return;
				}
				else if(differ==1){
					StaticValueClass.currentBuyer.consecutive_sign_days++;
					buckets.get((StaticValueClass.currentBuyer.consecutive_sign_days-1+7-moveForwards)%7).setBackgroundResource(R.mipmap.bucket_empty);
				//	differ=0;
				}else {
					//differ>1
					StaticValueClass.currentBuyer.consecutive_sign_days=1;
					bucket1.setBackgroundResource(R.mipmap.bucket_empty);
				}
				//sign animation
				Toast.makeText(ma, "连续签到"+StaticValueClass.currentBuyer.consecutive_sign_days+"天 ！", Toast.LENGTH_SHORT).show();
				if(StaticValueClass.currentBuyer.consecutive_sign_days%7!=0){
					signAnimationText.setText("+1");
					StaticValueClass.currentBuyer.bonus+=1;
				}else {
					signAnimationText.setText("+4");
					StaticValueClass.currentBuyer.bonus+=4;
				}
				
				StaticValueClass.currentBuyer.last_sign_date=StaticValueClass.dateFormat.format(new Date());
				signAnimationText.startAnimation(sign_animation);
				startAnimation();
				differ=0;
			}
		});
		return view;
	}

	private void initAnimations(){
		buckets=new ArrayList<HuaKangTextView>();
		clockwise=AnimationUtils.loadAnimation(this.getContext(), R.anim.clockwise);
		anti_clockwise=AnimationUtils.loadAnimation(this.getContext(), R.anim.anti_clockwise);
		anti_clockwise2=AnimationUtils.loadAnimation(this.getContext(), R.anim.anti_clockwise2);
		anti_clockwise3=AnimationUtils.loadAnimation(this.getContext(), R.anim.anti_clockwise3);
		anti_clockwise4=AnimationUtils.loadAnimation(this.getContext(), R.anim.anti_clockwise4);
		anti_clockwise5=AnimationUtils.loadAnimation(this.getContext(), R.anim.anti_clockwise5);
		anti_clockwise6=AnimationUtils.loadAnimation(this.getContext(), R.anim.anti_clockwise6);
		anti_clockwise7=AnimationUtils.loadAnimation(this.getContext(), R.anim.anti_clockwise7);
		sign_animation=AnimationUtils.loadAnimation(this.getContext(), R.anim.sign_animation);
		sign_out_animation=AnimationUtils.loadAnimation(this.getContext(), R.anim.sign_out_animation);
		LinearInterpolator lin=new LinearInterpolator();
		clockwise.setInterpolator(lin);
		anti_clockwise.setInterpolator(lin);
		anti_clockwise2.setInterpolator(lin);
		anti_clockwise3.setInterpolator(lin);
		anti_clockwise4.setInterpolator(lin);
		anti_clockwise5.setInterpolator(lin);
		anti_clockwise6.setInterpolator(lin);
		anti_clockwise7.setInterpolator(lin);
		sign_animation.setAnimationListener(new AnimationListener(){

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				if(signAnimationText!=null)
					signAnimationText.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				if(signAnimationText!=null){
					//	signAnimationText.setVisibility(View.GONE);
					signAnimationText.startAnimation(sign_out_animation);
				}
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

		});
	}
	
	private void reMeasure(){
		int scale=StaticValueClass.screenWidth,topMargin,diameter,radius;
		double angle=2*Math.PI/7;	
		
		RelativeLayout.LayoutParams params0=(RelativeLayout.LayoutParams)background.getLayoutParams();
		params0.height=scale*1450/720;
		
		RelativeLayout.LayoutParams params1=(RelativeLayout.LayoutParams)structure1.getLayoutParams();
		params1.topMargin=scale*30/72+StaticValueClass.statusBarHeight;
		params1.width=scale*54/72;
		params1.height=scale*52/72-StaticValueClass.statusBarHeight;
		
		topMargin=scale*3/72;
		diameter=scale*556/720-StaticValueClass.statusBarHeight;
		RelativeLayout.LayoutParams params2=(RelativeLayout.LayoutParams)wheelBackground.getLayoutParams();
		params2.topMargin=-2*topMargin+StaticValueClass.statusBarHeight;
		params2.height=diameter+6*topMargin;
		params2.width=scale*2;
		RelativeLayout.LayoutParams params3=(RelativeLayout.LayoutParams)skywheel.getLayoutParams();
		params3.topMargin=3*topMargin;
		params3.width=params3.height=diameter;
		
		coinBtn.setTypeface(Typeface.createFromAsset(ma.getAssets(),"fonts/huagang_girl.ttf"));
		RelativeLayout.LayoutParams params31=(RelativeLayout.LayoutParams)coinBtn.getLayoutParams();
	//	params31.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
	//	params31.bottomMargin=StaticValueClass.dip2px(ma, 16);
		
		RelativeLayout.LayoutParams params4=(RelativeLayout.LayoutParams)rulesImage.getLayoutParams();
	//	params4.width=scale*68/72;
		params4.height=scale*386/720;
		
		RelativeLayout.LayoutParams params5=(RelativeLayout.LayoutParams)signBtn.getLayoutParams();
		params5.addRule(RelativeLayout.BELOW, structure1.getId());
		params5.topMargin=4;
	//	params5.topMargin=StaticValueClass.dip2px(ma, 2);
		//.
		int step=StaticValueClass.currentBuyer.consecutive_sign_days;
		

		RelativeLayout.LayoutParams bp0=(RelativeLayout.LayoutParams)reference.getLayoutParams();
		bp0.height=params3.width*110/553;
		bp0.width=params3.width*97/553;
		
		RelativeLayout.LayoutParams bp1=(RelativeLayout.LayoutParams)bucket1.getLayoutParams();
		bp1.height=params3.width*110/553;
		bp1.width=params3.width*97/553;
		bp1.topMargin=params3.topMargin;
		
		RelativeLayout.LayoutParams bp2=(RelativeLayout.LayoutParams)bucket7.getLayoutParams();
		bp2.height=bp1.height;
		bp2.width=bp1.width;
		bp2.topMargin=bp1.topMargin;
		radius=diameter/2;
		reLocation(bp2,angle,radius);
		bp2.leftMargin-=6;
		bp2.topMargin-=6;
		
		RelativeLayout.LayoutParams bp3=(RelativeLayout.LayoutParams)bucket6.getLayoutParams();
		bp3.height=bp1.height;
		bp3.width=bp1.width;
		bp3.topMargin=bp1.topMargin;
		reLocation(bp3,angle*2,radius);
		bp3.leftMargin-=6;
		bp3.topMargin-=10;
		
		RelativeLayout.LayoutParams bp4=(RelativeLayout.LayoutParams)bucket5.getLayoutParams();
		bp4.height=bp1.height;
		bp4.width=bp1.width;
		bp4.topMargin=bp1.topMargin;
		reLocation(bp4,angle*3,radius);
		bp4.leftMargin-=3;
		bp4.topMargin-=13;
		
		RelativeLayout.LayoutParams bp5=(RelativeLayout.LayoutParams)bucket4.getLayoutParams();
		bp5.height=bp1.height;
		bp5.width=bp1.width;
		bp5.topMargin=bp1.topMargin;
		reLocation(bp5,angle*4,radius);
		bp5.leftMargin+=8;
		bp5.topMargin-=14;
		
		RelativeLayout.LayoutParams bp6=(RelativeLayout.LayoutParams)bucket3.getLayoutParams();
		bp6.height=bp1.height;
		bp6.width=bp1.width;
		bp6.topMargin=bp1.topMargin;
		reLocation(bp6,angle*5,radius);
		bp6.leftMargin+=12;
		bp6.topMargin-=2;
		
		RelativeLayout.LayoutParams bp7=(RelativeLayout.LayoutParams)bucket2.getLayoutParams();
		bp7.height=bp1.height;
		bp7.width=bp1.width;
		bp7.topMargin=bp1.topMargin;
		reLocation(bp7,angle*6,radius);
		bp7.leftMargin+=4;
			
	}
	
	private void  reLocation(RelativeLayout.LayoutParams ps,double ag, float radius){
		int al,bl;
		al=(int)(radius*Math.sin(ag));
		bl=(int)(radius*Math.cos(ag));
		Log.d("***reLocation", "angle:"+ag+"    bl:"+bl);
		ps.topMargin+=radius-bl;
		ps.leftMargin=al;
		
	}
	
	private void startAnimation(){
		wheelBackground.startAnimation(clockwise);
		bucket1.startAnimation(anti_clockwise);
		bucket2.startAnimation(anti_clockwise2);
		bucket3.startAnimation(anti_clockwise3);
		bucket4.startAnimation(anti_clockwise4);
		bucket5.startAnimation(anti_clockwise5);
		bucket6.startAnimation(anti_clockwise6);
		bucket7.startAnimation(anti_clockwise7);
	}
	
	private void initFunction(){
		buckets.clear();
		buckets.add(bucket1);
		buckets.add(bucket2);
		buckets.add(bucket3);
		buckets.add(bucket4);
		buckets.add(bucket5);
		buckets.add(bucket6);
		buckets.add(bucket7);
		signAnimationText.setVisibility(View.GONE);
	}
	
	private void initSkyWheel(){
		differ=(int)StaticValueClass.getDateDifference(StaticValueClass.dateFormat.format(new Date()), StaticValueClass.currentBuyer.last_sign_date);
		if(differ>1){
			StaticValueClass.currentBuyer.consecutive_sign_days=0;
		}
		
		for(int i=0;i<StaticValueClass.currentBuyer.consecutive_sign_days;i++){
			buckets.get(i).setBackgroundResource(R.mipmap.bucket_empty);
		}
		for(int i=StaticValueClass.currentBuyer.consecutive_sign_days ;i<7;i++){
			if(i!=6)
				buckets.get(i).setBackgroundResource(R.mipmap.bucket);
			else buckets.get(i).setBackgroundResource(R.mipmap.bucket_sp);
		}
		
		int step=StaticValueClass.currentBuyer.consecutive_sign_days%7;
		moveForwards=step;
		String number;
		Drawable drawable;
		int i,j,k;
		for(i=0;i<StaticValueClass.currentBuyer.consecutive_sign_days;i++){
			number=buckets.get(i).getText().toString();
			drawable=((TextView)buckets.get(i)).getBackground();
			for(j=i,k=(i+step)%7;k!=i;j=(j+step)%7,k=(k+step)%7){
				buckets.get(j).setText(buckets.get(k).getText());
				buckets.get(j).setBackground(buckets.get(k).getBackground());
			}
			buckets.get(j).setText(number);
			buckets.get(j).setBackground(drawable);
		} 
		
	}
	
}
