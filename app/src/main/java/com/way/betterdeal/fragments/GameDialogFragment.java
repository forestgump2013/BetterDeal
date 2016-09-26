package com.way.betterdeal.fragments;

import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;
import com.way.betterdeal.object.GameBonusRecord;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class GameDialogFragment extends DialogFragment {
	
	MainActivity ma;
	View view;
	ImageView imageView1;
	Dialog myDialog;
	GameBonusRecord concernBonusRecord;
	int flag;
	
	public GameDialogFragment(){

	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	@NonNull
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		myDialog=super.onCreateDialog(savedInstanceState);
		myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		myDialog.getWindow().setWindowAnimations(R.style.gameDialogStyle);
		return myDialog;
		
		
	//	return super.onCreateDialog(savedInstanceState);
	}

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		ma=(MainActivity)this.getActivity();
		switch (flag){
		case 1: //slot rules
		case 2: // ninepane rules
		case 3: // opportunity cost.
			view =inflater.inflate(R.layout.game_dialog_layout, container, false);
			initViewWithSingleImage(view);
			break;
		case 4: // win.
			    view=inflater.inflate(R.layout.game_win_dialog, container, false);
			    this.initGameWinView(view);
			    break;
		case 5: //lost.
			    view=inflater.inflate(R.layout.game_comeon_dialog, container, false);
			    this.initGameComeonView(view);
			    break;
		case 6: // share to get opportunity.
			view=inflater.inflate(R.layout.share_dialog, container, false);
			initShareDialog(view);
		
		}
		return view;
	//	return super.onCreateView(inflater, container, savedInstanceState);
		
	}
	
	private void initViewWithSingleImage(View parent){
		imageView1=(ImageView)parent.findViewById(R.id.imageView1);
		LinearLayout.LayoutParams params=(LinearLayout.LayoutParams)imageView1.getLayoutParams();
		switch(flag){
		case 1: imageView1.setImageResource(R.mipmap.slot_rules);
				params.width=StaticValueClass.screenWidth*7/8;
				params.height=StaticValueClass.screenWidth*74/72;
				break;
		case 2: imageView1.setImageResource(R.mipmap.ninepane_rules);
				params.width=StaticValueClass.screenWidth*7/8;
				params.height=StaticValueClass.screenWidth*5/6;
				break;
		case 3:imageView1.setImageResource(R.mipmap.game_runout);
				params.width=StaticValueClass.screenWidth*7/8;
				params.height=StaticValueClass.screenWidth/2;
				break;
		}
		imageView1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				myDialog.dismiss();
			}
		});
	}
	
	private void initShareDialog(View parent){
		LinearLayout outterView, innerView;
		ImageView image1,image2,image3,image4;
		image1=(ImageView)parent.findViewById(R.id.imageView1);
		image2=(ImageView)parent.findViewById(R.id.imageView2);
		image3=(ImageView)parent.findViewById(R.id.imageView3);
		image4=(ImageView)parent.findViewById(R.id.imageView4);
		outterView=(LinearLayout)parent.findViewById(R.id.outterView);
		innerView=(LinearLayout)parent.findViewById(R.id.innerView);
		ViewGroup.LayoutParams params1=outterView.getLayoutParams();
		params1.width=StaticValueClass.screenWidth*7/8;
		params1.height=StaticValueClass.screenWidth*56/72;
		LinearLayout.LayoutParams params2=(LinearLayout.LayoutParams)innerView.getLayoutParams();
		params2.topMargin=StaticValueClass.screenWidth/3;
		params2.leftMargin=params2.rightMargin=StaticValueClass.screenWidth/30;
		RelativeLayout.LayoutParams ps1=(RelativeLayout.LayoutParams)image1.getLayoutParams();
	    ps1.width=ps1.height=StaticValueClass.screenWidth/6;
	    RelativeLayout.LayoutParams ps2=(RelativeLayout.LayoutParams)image2.getLayoutParams();
	    ps2.width=ps2.height=StaticValueClass.screenWidth/6;
	    RelativeLayout.LayoutParams ps3=(RelativeLayout.LayoutParams)image3.getLayoutParams();
	    ps3.width=ps3.height=StaticValueClass.screenWidth/6;
	    RelativeLayout.LayoutParams ps4=(RelativeLayout.LayoutParams)image4.getLayoutParams();
	    ps4.width=ps4.height=StaticValueClass.screenWidth/6;
	    
	    image1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ma.weixinShareOperation("剁手联盟 游戏分享", 0);
				myDialog.dismiss();
			}
		});
	    
	    image2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ma.weixinShareOperation("剁手联盟 游戏分享", 1);
				myDialog.dismiss();
			}
		});
	    
	    image3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ma.tencentShare("");
				myDialog.dismiss();
			}
		});
	    
	    outterView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				myDialog.dismiss();
			}
		});
		
	}
	
	private void initGameComeonView(View view){
		int scale=StaticValueClass.screenWidth;
		ImageView imageView1=(ImageView)view.findViewById(R.id.imageView1);
		LinearLayout.LayoutParams params1=(LinearLayout.LayoutParams)imageView1.getLayoutParams();
		params1.width=scale*355/720;
		params1.height=scale*340/720;
		
		Button earnCoinsBtn=(Button)view.findViewById(R.id.earnCoinsBtn);
		LinearLayout.LayoutParams params2=(LinearLayout.LayoutParams)earnCoinsBtn.getLayoutParams();
		params2.width=scale*40/72;
		params2.height=scale*8/72;
		
		Button closeBtn=(Button)view.findViewById(R.id.closeBtn);
		LinearLayout.LayoutParams params3=(LinearLayout.LayoutParams)closeBtn.getLayoutParams();
		params3.height=params3.width=scale*10/72;
		
		closeBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				myDialog.dismiss();
			}
		});
		
		
	}
	
	private void initGameWinView(View view){
		int scaleLength,marginTop;
		scaleLength=StaticValueClass.screenWidth;
		ImageView closeMark=(ImageView)view.findViewById(R.id.closeMark);
		ImageView pinkBoard=(ImageView)view.findViewById(R.id.pinkboard);
		LinearLayout.LayoutParams params1=(LinearLayout.LayoutParams)closeMark.getLayoutParams();
		params1.width=params1.height=scaleLength*10/72;
		closeMark.startAnimation(AnimationUtils.loadAnimation(ma, R.anim.moveup1));
		FrameLayout.LayoutParams params2=(FrameLayout.LayoutParams)pinkBoard.getLayoutParams();
		params2.width=scaleLength*52/72;
		params2.height=scaleLength*486/720;
		Button checkPrizeBtn=(Button)view.findViewById(R.id.checkPrizeBtn);
		RelativeLayout.LayoutParams params3=(RelativeLayout.LayoutParams)checkPrizeBtn.getLayoutParams();
		params3.width=scaleLength*18/72;
		params3.height=scaleLength*7/80;
		marginTop=scaleLength*245/720;
		params3.topMargin=marginTop;
		
		//stars
		ImageView game_star1=(ImageView)view.findViewById(R.id.game_star1);
		RelativeLayout.LayoutParams  sParams1=(RelativeLayout.LayoutParams)game_star1.getLayoutParams();		
		sParams1.topMargin=marginTop;
		sParams1.width=sParams1.height=scaleLength*3/40;
		game_star1.startAnimation(AnimationUtils.loadAnimation(ma, R.anim.slide_star1));
	    
		ImageView game_star2=(ImageView)view.findViewById(R.id.game_star2);
		RelativeLayout.LayoutParams  sParams2=(RelativeLayout.LayoutParams)game_star2.getLayoutParams();
		sParams2.topMargin=marginTop;
		sParams2.width=sParams2.height=scaleLength*3/40;
		game_star2.startAnimation(AnimationUtils.loadAnimation(ma, R.anim.slide_star2));
		
		ImageView game_star3=(ImageView)view.findViewById(R.id.game_star3);
		RelativeLayout.LayoutParams  sParams3=(RelativeLayout.LayoutParams)game_star3.getLayoutParams();
		sParams3.topMargin=marginTop;
		sParams3.width=sParams3.height=scaleLength*3/40;
		game_star3.startAnimation(AnimationUtils.loadAnimation(ma, R.anim.slide_star3));
		
		//red stars
		ImageView game_red_star1=(ImageView)view.findViewById(R.id.game_red_star1);
		RelativeLayout.LayoutParams  rParams1=(RelativeLayout.LayoutParams)game_red_star1.getLayoutParams();
		rParams1.topMargin=marginTop;
		rParams1.width=rParams1.height=scaleLength/24;
		game_red_star1.startAnimation(AnimationUtils.loadAnimation(ma, R.anim.slide_red_star1));
		
		ImageView game_red_star2=(ImageView)view.findViewById(R.id.game_red_star2);
		RelativeLayout.LayoutParams  rParams2=(RelativeLayout.LayoutParams)game_red_star2.getLayoutParams();
		rParams2.topMargin=marginTop;
		rParams2.width=rParams2.height=scaleLength*5/72;
		game_red_star2.startAnimation(AnimationUtils.loadAnimation(ma, R.anim.slide_red_star2));
		
		ImageView game_red_star3=(ImageView)view.findViewById(R.id.game_red_star3);
		RelativeLayout.LayoutParams  rParams3=(RelativeLayout.LayoutParams)game_red_star3.getLayoutParams();
		rParams3.topMargin=marginTop;
		rParams3.width=rParams3.height=scaleLength*5/72;
		game_red_star3.startAnimation(AnimationUtils.loadAnimation(ma, R.anim.slide_red_star3));
		
		ImageView game_red_star4=(ImageView)view.findViewById(R.id.game_red_star4);
		RelativeLayout.LayoutParams  rParams4=(RelativeLayout.LayoutParams)game_red_star4.getLayoutParams();
		rParams4.topMargin=marginTop;
		rParams4.width=rParams4.height=scaleLength*85/720;
		game_red_star4.startAnimation(AnimationUtils.loadAnimation(ma, R.anim.slide_red_star4));
		
		//blue stars
		ImageView game_blue_star1=(ImageView)view.findViewById(R.id.game_blue_star1);
		RelativeLayout.LayoutParams  bParams1=(RelativeLayout.LayoutParams)game_blue_star1.getLayoutParams();
		bParams1.topMargin=marginTop;
		bParams1.width=bParams1.height=scaleLength/24;
		game_blue_star1.startAnimation(AnimationUtils.loadAnimation(ma, R.anim.slide_blue_star1));
		
		ImageView game_blue_star2=(ImageView)view.findViewById(R.id.game_blue_star2);
		RelativeLayout.LayoutParams  bParams2=(RelativeLayout.LayoutParams)game_blue_star2.getLayoutParams();
		bParams2.topMargin=marginTop;
		bParams2.width=bParams2.height=scaleLength/24;
		game_blue_star2.startAnimation(AnimationUtils.loadAnimation(ma, R.anim.slide_blue_star2));
		
		//yellow stars
		
		ImageView game_yellow_star1=(ImageView)view.findViewById(R.id.game_yellow_star1);
		RelativeLayout.LayoutParams  yParams1=(RelativeLayout.LayoutParams)game_yellow_star1.getLayoutParams();
		yParams1.topMargin=marginTop;
		yParams1.width=yParams1.height=scaleLength/36;
		game_yellow_star1.startAnimation(AnimationUtils.loadAnimation(ma, R.anim.slide_yellow_star1));
		
		ImageView game_yellow_star2=(ImageView)view.findViewById(R.id.game_yellow_star2);
		RelativeLayout.LayoutParams  yParams2=(RelativeLayout.LayoutParams)game_yellow_star2.getLayoutParams();
		yParams2.topMargin=marginTop;
		yParams2.width=yParams2.height=scaleLength/24;
		game_yellow_star2.startAnimation(AnimationUtils.loadAnimation(ma, R.anim.slide_yellow_star2));
		
		ImageView game_yellow_star3=(ImageView)view.findViewById(R.id.game_yellow_star3);
		RelativeLayout.LayoutParams  yParams3=(RelativeLayout.LayoutParams)game_yellow_star3.getLayoutParams();
		yParams3.topMargin=marginTop;
		yParams3.width=yParams3.height=scaleLength/36;
		game_yellow_star3.startAnimation(AnimationUtils.loadAnimation(ma, R.anim.slide_yellow_star3));
		
		ImageView game_yellow_star4=(ImageView)view.findViewById(R.id.game_yellow_star4);
		RelativeLayout.LayoutParams  yParams4=(RelativeLayout.LayoutParams)game_yellow_star4.getLayoutParams();
		yParams4.topMargin=marginTop;
		yParams4.width=yParams4.height=scaleLength/36;
		game_yellow_star4.startAnimation(AnimationUtils.loadAnimation(ma, R.anim.slide_yellow_star4));
		
		ImageView game_pink_bands=(ImageView)view.findViewById(R.id.game_pink_bands);
		RelativeLayout.LayoutParams  bParams=(RelativeLayout.LayoutParams)game_pink_bands.getLayoutParams();
	
		bParams.width=scaleLength;
		bParams.height=scaleLength*232/720;
		bParams.addRule(RelativeLayout.ALIGN_BOTTOM,checkPrizeBtn.getId());
		bParams.bottomMargin=-scaleLength*7/80;
		game_pink_bands.setLayoutParams(bParams);		
		game_pink_bands.startAnimation(AnimationUtils.loadAnimation(ma, R.anim.scalse_bands));
		
		Button earnCoinsBtn=(Button)view.findViewById(R.id.earnCoinsBtn);
		RelativeLayout.LayoutParams  ecParams=(RelativeLayout.LayoutParams)earnCoinsBtn.getLayoutParams();
		ecParams.width=scaleLength*35/72;
		ecParams.height=scaleLength*68/720;
		ecParams.topMargin=scaleLength*455/720;
		//----------------------
		checkPrizeBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ma.loadPrizeFragment(concernBonusRecord);
				myDialog.dismiss();
			}
		});
		//----------------------
		closeMark.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				myDialog.dismiss();
			}
		});
		
	}
	
//	private void 


	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}

	public void showGameDialog(int f){
		flag=f;
		this.show(ma.getSupportFragmentManager(), "gameDialog");
	} 
	
	public void showGameDialog(int f,GameBonusRecord record){
		flag=f;
		concernBonusRecord=record;
		this.show(ma.getSupportFragmentManager(), "gameDialog");
	} 
	
    public interface NoticeGameDialogListener{
    	public void toEarnCoinsFragment();
    	public void shareToWeixinOne();
    	public void shareToWeixinFriends();
    	public void shareToQQZone();
    }
}
