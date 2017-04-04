package com.way.betterdeal.fragments;

import java.util.ArrayList;
import java.util.List;

import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;
import com.way.betterdeal.object.PagerItem;
import com.way.betterdeal.view.SlidingTabLayout;

//import demo.slidingtablayout.MainActivity.ViewPagerAdapter;

//import demo.slidingtablayout.PagerItem;

//import demo.slidingtablayout.view.SlidingTabLayout;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class SuperDiscoutFragment extends Fragment {
	
	MainActivity ma;
	SlidingTabLayout slidingTabLayout;
	View view;
	 /*viewpager*/
	RelativeLayout directView,slideView;
    private ViewPager mViewPager ;
    private ViewPagerAdapter viewPagerAdapter;
    /*自定义的 tabLayout*/
    private SlidingTabLayout mTabLayout ;
    /*每个 tab 的 item*/
    private List<PagerItem> mTab = new ArrayList<>() ;
    private HorizontalScrollView horizontalScrollView;
    Button backBtn;
    ImageView leftArrow,rightArrow;
    
    int currentTab=0,lastOne=-1;
    int scrollDiffer;
    
    ArrayList<ContentFragment> contentFragments;
	
	public SuperDiscoutFragment(){

		contentFragments=new  ArrayList<ContentFragment>();
		contentFragments.add(new ContentFragment());
		contentFragments.add(new ContentFragment());
		contentFragments.add(new ContentFragment());
		contentFragments.add(new ContentFragment());
		contentFragments.add(new ContentFragment());
		contentFragments.add(new ContentFragment());
		contentFragments.add(new ContentFragment());
		mTab.add(new PagerItem("全部","全部")) ;
        mTab.add(new PagerItem("美食","美食")) ;
        mTab.add(new PagerItem("女装","女装")) ;
        mTab.add(new PagerItem("男装","男装")) ;
        mTab.add(new PagerItem("鞋包","鞋包")) ;
        mTab.add(new PagerItem("美妆配饰","美妆配饰")) ;
        mTab.add(new PagerItem("家居","家居")) ;
	}

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		ma=(MainActivity)this.getActivity();
		view=inflater.inflate(R.layout.super_discout_fragment, container, false);
		slideView=(RelativeLayout)view.findViewById(R.id.slideView);
		directView=(RelativeLayout)view.findViewById(R.id.directView);
		backBtn=(Button)view.findViewById(R.id.backBtn);
		horizontalScrollView=(HorizontalScrollView)view.findViewById(R.id.horizontalScrollView);
        mViewPager = (ViewPager) view.findViewById(R.id.id_view_pager) ;
        mTabLayout = (SlidingTabLayout) view.findViewById(R.id.id_tab) ;
       
       
        horizontalScrollView.setHorizontalScrollBarEnabled(false);
        horizontalScrollView.setSmoothScrollingEnabled(true);
        leftArrow=(ImageView)view.findViewById(R.id.leftArrow);
        rightArrow=(ImageView)view.findViewById(R.id.rightArrow);
        initTabs();
        concernsFunction();
        reMeasure();
       if(StaticValueClass.isAfterKitKat)
        	view.setPadding(0, StaticValueClass.statusBarHeight, 0, 0);
		return view;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	//	viewPagerAdapter.notifyDataSetChanged();
	//	mTabLayout.setCurrentItem(currentTab);
		mViewPager.setCurrentItem(currentTab);
	    if (StaticValueClass.firstUseed)
			directView.setVisibility(View.VISIBLE);
		else directView.setVisibility(View.GONE);
	}
	
	private void initTabs(){
		
		viewPagerAdapter=new ViewPagerAdapter(this.getChildFragmentManager());
        mViewPager.setAdapter(viewPagerAdapter);
        /*需要先为 viewpager 设置 adapter*/
        mTabLayout.setViewPager(mViewPager);
        mTabLayout.setHorizontalScrollView(horizontalScrollView);
        mTabLayout.setCurrentItem(this.currentTab);
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		currentTab=mViewPager.getCurrentItem();
	}

	private void concernsFunction(){
		backBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ma.onBackPressed();
			}
		});

		directView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				directView.setVisibility(View.GONE);
			}
		});
	}
	
	private void reMeasure(){
		
		final RelativeLayout.LayoutParams params0=(RelativeLayout.LayoutParams)horizontalScrollView.getLayoutParams();
		
    	 FrameLayout.LayoutParams params=(FrameLayout.LayoutParams)mTabLayout.getLayoutParams();
		 final TextView sub2=(TextView)view.findViewById(R.id.sub2);
    	 final RelativeLayout.LayoutParams params1=(RelativeLayout.LayoutParams)sub2.getLayoutParams();

        sub2.post(new Runnable() {
			@Override
			public void run() {
			//	 RelativeLayout.LayoutParams params2=(RelativeLayout.LayoutParams)slideView.getLayoutParams();
				params1.height=slideView.getHeight();
				sub2.setLayoutParams(params1);
			}
		});
    	Handler handler=new Handler();
    	handler.postDelayed(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
			//	Log.d("***superDiscout", "scrollview w:"+horizontalScrollView.getWidth()+"   tablayout w:"+mTabLayout.getWidth());
				scrollDiffer=mTabLayout.getWidth()-horizontalScrollView.getWidth();
				mTabLayout.setScrollLength(scrollDiffer);

			}
    		
    	}, 1000);
    //	params.width=1200;
    	
    	//...
    	Bitmap backmark=BitmapFactory.decodeResource(ma.getResources(), R.mipmap.expand_icon);
    	Drawable leftDrawable=new BitmapDrawable(StaticValueClass.getBackIcon(backmark));
    	leftDrawable.setBounds(0, 0, backmark.getWidth(), backmark.getHeight());
    	backBtn.setCompoundDrawables(leftDrawable, null, null, null);
    	
    	Bitmap arrow=BitmapFactory.decodeResource(ma.getResources(), R.mipmap.arrow_mark);
    	Bitmap arrow1=StaticValueClass.rotateBitmap(arrow, 90);
    	leftArrow.setImageBitmap(arrow1);
    	Bitmap arrow2=StaticValueClass.rotateBitmap(arrow, 270);
    	rightArrow.setImageBitmap(arrow2);
    	
    }
	
    private class ViewPagerAdapter extends FragmentPagerAdapter implements SlidingTabLayout.TabItemName{

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }
      
        @Override
        public Fragment getItem(int position) {
        //    return mTab.get(position).createFragment();
        	Log.d("***ViewPagerAdapter", "getItem position:"+position);
        	currentTab=position%7;
        	/*
        	horizontalScrollView.post(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if(currentTab>lastOne){
		        		//move forward.
		        		if(currentTab==3 ||currentTab==4 || currentTab==5){
		        	//		horizontalScrollView.scrollBy(-scrollDiffer/3, 0);
		        		//	horizontalScrollView.setScrollX(horizontalScrollView.getScrollX()+scrollDiffer/3);
		        			horizontalScrollView.smoothScrollBy(-scrollDiffer/3, 0);
		        		}else{
		        			horizontalScrollView.fullScroll(ScrollView.FOCUS_LEFT);
		        		}
		        	}else {
		        		//move backword.
		        		if(currentTab==3 ||currentTab==2 || currentTab==1){
		        		//	horizontalScrollView.scrollBy(scrollDiffer/3, 0);
		        		//	horizontalScrollView.setScrollX(horizontalScrollView.getScrollX()-scrollDiffer/3);
		        			horizontalScrollView.smoothScrollBy(scrollDiffer/3, 0);
		        		}else {
		        			horizontalScrollView.fullScroll(ScrollView.FOCUS_RIGHT);
		        		}
		        	}
		        	lastOne=currentTab;
				}
        		
        	});
        	 */
            return contentFragments.get(position%7);
        }
     

        @Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
        	Log.d("***ViewPagerAdapter", "instantiateItem position:"+position);
			return super.instantiateItem(container, position);
       // 	return contentFragments.get(position);
		}

		@Override
        public int getCount() {
            return mTab.size();
        }

        @Override
        public String getTabName(int position) {
            return mTab.get(position%7).getTitle();
        }

		
		
    }
	
	

}
