package com.way.betterdeal.fragments;

import java.util.ArrayList;
import java.util.List;

import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;
import com.way.betterdeal.adapters.SimpleAdapter;
import com.way.betterdeal.object.Commodity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class SearchFragment extends Fragment {
	
	MainActivity ma;
	View searchView;
	String[] hotWords={"上装","手机壳","充电宝","女士皮包","韩版裙子","打火机","水杯","夹克外套","鞋子"};
	ArrayAdapter<String> hotWordsAdapter;
	SimpleAdapter simpleAdapter;
	Spinner chooseSpinner;
	RelativeLayout surroundView,centerView,outTab3,directView;
	EditText searchContent;
	Button backBtn,searchBtn,finishBtn;
	ViewPager viewPager;
	FragmentAdapter fragmentAdapter;
	TextView tab1,tab2,tab3,tab4,preTab;
	ImageView arrowImage;
	LinearLayout hintView,filterView;
	GridView hotSearchWordsGridView;
	LinearLayout searchResultView;
	ArrayList<TextView> tabs;
	int oldPosition=-1,priceUp,tab3Press;
	int[] arrows={R.mipmap.s_gray_arraw,R.mipmap.s_arrow1,R.mipmap.s_gray_arrow1,R.mipmap.s_arrow};
	TextView oldView,pinkBackground;
	
	boolean fresh;
	
	public SearchFragment(){
		tabs=new ArrayList<TextView>();
		priceUp=0;
		tab3Press=0;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		ma=(MainActivity)this.getActivity();
		searchView=inflater.inflate(R.layout.search_fragment, container, false);
		chooseSpinner=(Spinner)searchView.findViewById(R.id.chooseSpinner);
		surroundView=(RelativeLayout)searchView.findViewById(R.id.surroundView);
		centerView=(RelativeLayout)searchView.findViewById(R.id.centerView);
		directView=(RelativeLayout)searchView.findViewById(R.id.directView);
		searchContent=(EditText)searchView.findViewById(R.id.searchContent);
		backBtn=(Button)searchView.findViewById(R.id.backBtn);
		searchBtn=(Button)searchView.findViewById(R.id.searchBtn);
		finishBtn=(Button)searchView.findViewById(R.id.finishBtn);
	//	pinkBackground=(TextView)searchView.findViewById(R.id.pinkBackground);
		hintView=(LinearLayout)searchView.findViewById(R.id.hintView);
		filterView=(LinearLayout)searchView.findViewById(R.id.filterView);
		hotSearchWordsGridView=(GridView)searchView.findViewById(R.id.hotSearchWords);
		
		hotWordsAdapter=new ArrayAdapter<String>(ma,R.layout.hotword_textview,hotWords);
		hotSearchWordsGridView.setAdapter(hotWordsAdapter);
		//mallAdapter.setDropDownViewResource(R.drawable.round_pink);
		simpleAdapter=new SimpleAdapter(ma);
		simpleAdapter.setPlotFlag(1);
		chooseSpinner.setAdapter(simpleAdapter);
		chooseSpinner.setPopupBackgroundResource(R.drawable.round_pink);
		if(StaticValueClass.isAfterKitKat)
			searchView.setPadding(0, StaticValueClass.statusBarHeight, 0, 0);
		initSearchResultView();
		reMeasure();
		initFunction();
		return searchView;
	//	return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		hintView.setVisibility(View.VISIBLE);
		searchResultView.setVisibility(View.GONE);
		fresh=true;
	}
	
	private void initSearchResultView(){
		
		searchResultView=(LinearLayout)searchView.findViewById(R.id.searchResultView);
		viewPager=(ViewPager)searchView.findViewById(R.id.viewPager);
		
		tab1=(TextView)searchView.findViewById(R.id.tab1);
		tab2=(TextView)searchView.findViewById(R.id.tab2);
		tab3=(TextView)searchView.findViewById(R.id.tab3);
		tab4=(TextView)searchView.findViewById(R.id.tab4);
		arrowImage=(ImageView)searchView.findViewById(R.id.arrowImage);
		outTab3=(RelativeLayout)searchView.findViewById(R.id.outTab3);
		tabs.clear();
		tabs.add(tab1);
		tabs.add(tab2);
		tabs.add(tab3);
		tabs.add(tab4);
		preTab=tab1;
		List<SearchResultFragment> fragments=new ArrayList<SearchResultFragment>();
		fragments.add(new SearchResultFragment());
		fragments.add(new SearchResultFragment());
		fragments.add(new SearchResultFragment());
		fragmentAdapter=new FragmentAdapter(this.getChildFragmentManager(),fragments);
		viewPager.setAdapter(fragmentAdapter);
		viewPager.addOnPageChangeListener(new OnPageChangeListener(){

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				// TODO Auto-generated method stub
				if(positionOffset==0){
					TextView textView=tabs.get(position);
					if(textView==preTab) return;
					if(position==2){
						tab3Press=1;
					}else tab3Press=0;
					arrowImage.setImageResource(arrows[tab3Press+2*priceUp]);
					textView.setTextColor(Color.rgb(252, 51, 102));
					preTab.setTextColor(Color.BLACK);
					preTab=textView;
					
				}
			}

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		outTab3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				RelativeLayout outView=(RelativeLayout)v;
				  if(outView==outTab3){
					preTab.setTextColor(Color.BLACK);
					tab3.setTextColor(Color.rgb(252, 51, 102));
					viewPager.setCurrentItem(2);
					if(tab3Press==1){
						priceUp=(priceUp+1)%2;
						
					}else{
						tab3Press=1;
					}
					arrowImage.setImageResource(arrows[tab3Press+2*priceUp]);
					preTab=tab3;
					}
			}
		});
		
		View.OnClickListener onClickListener=new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TextView view=(TextView)v;
				
				preTab.setTextColor(Color.BLACK);
				view.setTextColor(Color.rgb(252, 51, 102));
				if(view==tab1){
					viewPager.setCurrentItem(0);
					tab3Press=0;
				} else if(view==tab2){
					viewPager.setCurrentItem(1);
					tab3Press=0;
				}
				preTab=view;
			}
		};
		
		tab1.setTextColor(Color.rgb(252, 51, 102));
		tab1.setOnClickListener(onClickListener);
		tab2.setOnClickListener(onClickListener);
		arrowImage.setImageResource(arrows[tab3Press+2*priceUp]);
		tab4.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(filterView.getVisibility()==View.VISIBLE)
					filterView.setVisibility(View.GONE);
				else filterView.setVisibility(View.VISIBLE);
			}
		});
		directView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				directView.setVisibility(View.GONE);
			}
		});
		
	}


	
	private void initFunction(){
		/*
		hotSearchWordsGridView.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Log.d("** hotSearchWordsGridView", "setOnItemSelectedListener:"+position);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			
		}); */
		hotSearchWordsGridView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
			//	Log.d("** hotSearchWordsGridView", "setOnItemClickListener:"+position);
				TextView item=(TextView)view;
				item.setBackgroundResource(R.drawable.wround_pink_border);
			//	item.setTextColor(Color.WHITE);
				
				searchContent.setText(hotWords[position]);
				if(oldPosition!=-1 && oldPosition!=position){
					oldView.setBackgroundResource(R.drawable.wround_white);
				}
				oldView=item;
				oldPosition=position; 
			}
			
		});
		chooseSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				/*
				if(fresh) {
					fresh=false;
					return;
				}
			    	searchResults();
			    	*/
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		searchBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showSearchResults();
			}
		});
		
		finishBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				filterView.setVisibility(View.GONE);
			}
		});
		
	}
	private void reMeasure(){
		//.
		int scale=StaticValueClass.screenWidth;
		Bitmap backmark=BitmapFactory.decodeResource(ma.getResources(), R.mipmap.expand_icon);
    	Drawable leftDrawable=new BitmapDrawable(StaticValueClass.getBackIcon(backmark));
    	leftDrawable.setBounds(0, 0, scale*25/720, scale/18);
    	backBtn.setCompoundDrawables(leftDrawable, null, null, null);
		//
    	RelativeLayout.LayoutParams params0=(RelativeLayout.LayoutParams)surroundView.getLayoutParams();
    	params0.height=70*scale/720;
    	params0.width=StaticValueClass.screenWidth*61/72;
		RelativeLayout.LayoutParams params1=(RelativeLayout.LayoutParams)centerView.getLayoutParams();
		params1.height=scale*62/720;
		params1.width=StaticValueClass.screenWidth*336/720;
		RelativeLayout.LayoutParams params2=(RelativeLayout.LayoutParams)chooseSpinner.getLayoutParams();
		params2.width=StaticValueClass.screenWidth*154/720;
	//	RelativeLayout.LayoutParams params3=(RelativeLayout.LayoutParams)pinkBackground.getLayoutParams();
	//	params3.rightMargin=StaticValueClass.screenWidth*6/72;
	//	params3.width=params2.width;
		//.
		
		Bitmap transBitmap=BitmapFactory.decodeResource(getResources(), R.mipmap.dark_bg);
		
		Drawable backDrawable= new BitmapDrawable( StaticValueClass.getDarkImage(transBitmap));
		backDrawable.setAlpha(160);
	//	backDrawable.set
	//	backDrawable.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
		filterView.setBackground(backDrawable);	
	//	filterView.setAlpha(0.7f);
	}
	
	
	
	public void showSearchResults(){
		this.hintView.setVisibility(View.GONE);
		this.searchResultView.setVisibility(View.VISIBLE);
		filterView.setVisibility(View.GONE);
	}
	
   
	/**
	 * 功能：主页引导栏的三个Fragment页面设置适配器
	 */
	public class FragmentAdapter extends FragmentPagerAdapter{
		private List<SearchResultFragment> fragments;
		
		public FragmentAdapter(FragmentManager fm,List<SearchResultFragment> fragments) {
			super(fm);
			this.fragments=fragments;
		}

		public Fragment getItem(int fragment) {
			return fragments.get(fragment);
		}

		public int getCount() {
			return fragments.size();
		}

	}
	

}
