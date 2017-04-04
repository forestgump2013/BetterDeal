package com.way.betterdeal.fragments;

//import com.way.miniqq.R;



import com.way.betterdeal.R;
import com.way.betterdeal.adapters.TabsPagerAdapter;
import com.way.betterdeal.view.TabHorizontalScrollView;
//import com.way.miniqq.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 13-6-14
 * Time: 下午2:39
 */
public class MultiViewsFragment extends Fragment{
	private static String TAG_ONE = "one";
	private static String TAG_TWO = "two";
	private static String TAG_THREE="three";
	private static String TAG_FOUR = "four";
	private static String TAG_FIVE = "five";
	private ScreenFragment mFragmentOne;
	private ScreenFragment mFragmentTwo;
	private ScreenFragment mFragmentThree; 
	private ScreenFragment mFragmentFour;
	private ScreenFragment mFragmentFive; 
	TabHost mTabHost;
	TabHorizontalScrollView  tabHorizontalScrollView ;
	View fragmentView;
	


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("AAAAAAAAAA____onCreateView");
        fragmentView=inflater.inflate(R.layout.tab_a, container, false);
      //concerned subfragment.
        initFragments();
		
		tabHorizontalScrollView=(TabHorizontalScrollView) fragmentView.findViewById(R.id.tabHorizontalScrollView);
		ViewPager viewPager = (ViewPager) fragmentView.findViewById(R.id.pager);
		if (viewPager != null) {
			// Portrait. Fragments as 2 tabs.
			mTabHost = (TabHost) fragmentView.findViewById(R.id.tabhost);
			mTabHost.setup();
		//	mTabHost.getTabWidget().getChildCount();
			TabsPagerAdapter adapter = new TabsPagerAdapter(this.getActivity(), mTabHost, viewPager,tabHorizontalScrollView);
			adapter.addTab(mTabHost.newTabSpec("one").setIndicator(initTabView(inflater,"one")), mFragmentOne, TAG_ONE);
			adapter.addTab(mTabHost.newTabSpec("two").setIndicator(initTabView(inflater,"two")), mFragmentTwo, TAG_TWO);
			adapter.addTab(mTabHost.newTabSpec("three").setIndicator(initTabView(inflater,"three")), mFragmentThree, TAG_THREE);
			adapter.addTab(mTabHost.newTabSpec("four").setIndicator(initTabView(inflater,"four")), mFragmentFour, TAG_FOUR);
			adapter.addTab(mTabHost.newTabSpec("five").setIndicator(initTabView(inflater,"five")), mFragmentFive, TAG_FIVE);
			
			
		} else {
			// Landscape. Same fragments side by side.
		}
		
		// 设置窗口的宽度   
		DisplayMetrics dm = new DisplayMetrics();   
		this.getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);   
		int screenWidth = dm.widthPixels;   
		int count=mTabHost.getTabWidget().getChildCount();
	    for (int i = 0; i < count; i++) {   
		        // 设置每个选项卡的宽度   
		    	mTabHost.getTabWidget().getChildTabViewAt(i).setMinimumWidth(screenWidth / 3);
	//	    	TextView text=(TextView)mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
	//	    text.setBackgroundColor(Color.BLUE);
	    }
		tabHorizontalScrollView.initTab(3, screenWidth / 3);
        
        return fragmentView;
    }
    
    private View initTabView(LayoutInflater inflater,String title){
    	View tabView=inflater.inflate(R.layout.sub_tabview, null);
		TextView tabText=(TextView)tabView.findViewById(R.id.tabtext);
	//	tabText.setBackgroundResource(R.drawable.tab_indicator);
		tabText.setText(title);
		tabText.setGravity(Gravity.CENTER);
		return tabView;
	}

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("AAAAAAAAAA____onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("AAAAAAAAAA____onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("AAAAAAAAAA____onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("AAAAAAAAAA____onPause");
    }


    
private void initFragments() {
		//this.getActivity().getSupportFragmentManager()
	//this.getChildFragmentManager();
		FragmentManager fragmentManager = this.getChildFragmentManager();//this.getActivity().getSupportFragmentManager();

		mFragmentOne = (ScreenFragment) fragmentManager.findFragmentByTag(TAG_ONE);
		mFragmentTwo = (ScreenFragment) fragmentManager.findFragmentByTag(TAG_TWO);
		mFragmentThree= (ScreenFragment) fragmentManager.findFragmentByTag(TAG_THREE);
		mFragmentFour= (ScreenFragment) fragmentManager.findFragmentByTag(TAG_FOUR);
		mFragmentFive= (ScreenFragment) fragmentManager.findFragmentByTag(TAG_FIVE);

		FragmentTransaction remove = fragmentManager.beginTransaction();
		if (mFragmentOne == null) {
			mFragmentOne = ScreenFragment.newInstance(R.layout.fragment_one);
		}
		else {
			remove.remove(mFragmentOne);
		}
		if (mFragmentTwo == null) {
			mFragmentTwo = ScreenFragment.newInstance(R.layout.fragment_two);
		}
		else {
			remove.remove(mFragmentTwo);
		}
		if(mFragmentThree==null){
			mFragmentThree=ScreenFragment.newInstance(R.layout.fragment_three);
		}else{
			remove.remove(mFragmentThree);
		}
		if(mFragmentFour==null){
			mFragmentFour=ScreenFragment.newInstance(R.layout.fragment_two);
		}else{
			remove.remove(mFragmentFour);
		}
		if(mFragmentFive==null){
			mFragmentFive=ScreenFragment.newInstance(R.layout.fragment_one);
		}else{
			remove.remove(mFragmentFive);
		}
		
		if (!remove.isEmpty()) {
			remove.commit();
			fragmentManager.executePendingTransactions();
		}
	}
    
}
