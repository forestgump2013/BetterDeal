package com.way.betterdeal.fragments;

import java.util.ArrayList;

import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class WelcomeFragment extends Fragment {

	
	MainActivity ma;
	View view;
	private ViewPager mViewPager;
	ImageView indexImage1,indexImage2,indexImage3,indexImage4;
	ArrayList<WelcomePageFragment> fragments=new ArrayList<WelcomePageFragment>(); 
	ArrayList<ImageView> indexImages=new ArrayList<ImageView>();
	MyFragmentPageAdapter myFragmentPageAdapter;
	int current=0;
	Button jumpBtn;
	TextView statusbar;
	LinearLayout linearLayout1;
	Animation leaveAnimation;
	public WelcomeFragment(){

	}
	
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		ma=(MainActivity)this.getActivity();
		view=inflater.inflate(R.layout.welcome_fragment, container, false);
		mViewPager=(ViewPager)view.findViewById(R.id.viewpager);
		indexImage1=(ImageView)view.findViewById(R.id.indexImage1);
		indexImage2=(ImageView)view.findViewById(R.id.indexImage2);
		indexImage3=(ImageView)view.findViewById(R.id.indexImage3);
		statusbar=(TextView)view.findViewById(R.id.statusbar);
		jumpBtn=(Button)view.findViewById(R.id.jumpBtn);
		myFragmentPageAdapter=new MyFragmentPageAdapter(this.getChildFragmentManager());
		mViewPager.setAdapter(myFragmentPageAdapter);
		linearLayout1=(LinearLayout)view.findViewById(R.id.linearLayout1);
		jumpBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ma.removeWelcomeFragment();
			}
		});
		
		RelativeLayout.LayoutParams params=(RelativeLayout.LayoutParams)statusbar.getLayoutParams();
		params.height=StaticValueClass.statusBarHeight;
		
		mViewPager.addOnPageChangeListener(new OnPageChangeListener(){

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				// TODO Auto-generated method stub
				if(positionOffset!=0){
					if((position+1)==3){
						linearLayout1.setVisibility(View.GONE);
						jumpBtn.setVisibility(View.GONE);
					}
					if(positionOffset>0.5f){
						int comingPosition=position+1;
						if(comingPosition!=current && comingPosition<3){
							indexImages.get(current).setImageResource(R.mipmap.unselected_point);
					    	indexImages.get(comingPosition).setImageResource(R.mipmap.selected_point);
					    	current=comingPosition;
						}
					}else {
						int comingPosition=position;
						if(comingPosition!=current && comingPosition<3){
							indexImages.get(current).setImageResource(R.mipmap.unselected_point);
					    	indexImages.get(comingPosition).setImageResource(R.mipmap.selected_point);
					    	current=comingPosition;
						}
					}
				}else{
					if(position==3){
						
					}
				}
				
			}

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		initData();
		
		view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		return view;
	}
	
	private void initData(){
		WelcomePageFragment pageFragment1=new WelcomePageFragment();
		WelcomePageFragment pageFragment2=new WelcomePageFragment();
		WelcomePageFragment pageFragment3=new WelcomePageFragment();
		WelcomePageFragment pageFragment4=new WelcomePageFragment();
		fragments.add(pageFragment2);
		fragments.add(pageFragment3);
		fragments.add(pageFragment4);
		fragments.add(pageFragment1);
		pageFragment1.setBackground(R.drawable.trans_shape);
		pageFragment1.setStatusBar();
		pageFragment1.setMainImage("", 0);
		pageFragment2.setSloganImage(R.mipmap.slogan2, 88);
		pageFragment2.setMainImage("BetterDeal/welcomeImages/welchild2.jpg", 508);
		pageFragment2.setSloganImage(R.mipmap.slogan2, 88);
		pageFragment2.setSloganText("每天等你来"); 
		pageFragment3.setMainImage("BetterDeal/welcomeImages/welchild3.jpg", 496);
		pageFragment3.setSloganImage(R.mipmap.slogan3, 89);
		pageFragment3.setSloganText("性价比更高");
		pageFragment4.setMainImage("BetterDeal/welcomeImages/welchild4.jpg", 511);
		pageFragment4.setSloganImage(R.mipmap.slogan4, 83);
		pageFragment4.setSloganText("福利任你挑");
		pageFragment4.setLastPage(true);
		
		indexImages.add(indexImage1);
		indexImages.add(indexImage2);
		indexImages.add(indexImage3);
		indexImages.add(indexImage4);
		leaveAnimation=AnimationUtils.loadAnimation(ma, R.anim.slide_left_out);
		leaveAnimation.setAnimationListener(new AnimationListener(){

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				ma.removeWelcomeFragment();
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
		});
		myFragmentPageAdapter.notifyDataSetChanged();
	}
	
	public class MyFragmentPageAdapter extends FragmentPagerAdapter {
	    public MyFragmentPageAdapter(FragmentManager fm) {
	        super(fm);
	    }
	    @Override
	    public int getCount() {
	        return fragments.size();
	    }
	    @Override
	    public Fragment getItem(int position) {
			fragments.get(position).loadPosterImage();
	      return fragments.get(position);
	    		  
	    }
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			return super.instantiateItem(container, position);
		}
		@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO Auto-generated method stub
			return super.isViewFromObject(view, object);
		}
		@Override
		public void setPrimaryItem(ViewGroup container, int position,
				Object object) {
			// TODO Auto-generated method stub
			super.setPrimaryItem(container, position, object);
			
			if(position==3){
				ma.removeWelcomeFragment();
			
			}else linearLayout1.setVisibility(View.VISIBLE);
		}
	    
	    
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
	}
	
	

}
