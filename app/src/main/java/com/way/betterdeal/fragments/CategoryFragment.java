package com.way.betterdeal.fragments;

import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;
import com.way.betterdeal.adapters.CategoryAdapter;
import com.way.betterdeal.adapters.CategoryGroupAdapter;
import com.way.betterdeal.object.CategoryCell;
import com.way.betterdeal.object.CategoryGroup;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CategoryFragment extends Fragment {
	
	MainActivity ma;
	View categoryView;
	Button searchBtn;
	ListView categoryListView,subCategoryListView;
	CategoryGroupAdapter categoryGroupAdapter;
	CategoryAdapter categoryAdapter;
	int categoryIndex=0,oPosition1=0;
//	boolean firstEnter;
	TextView oldView1,background1,searchText;
	ArrayList<TextView> textViews;
	Handler handler=new Handler();
	public CategoryFragment( ){

		getCategoryData();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		ma=(MainActivity)this.getActivity();
		initTextViews();
		categoryView=inflater.inflate(R.layout.category_fragment, container, false);
		searchBtn=(Button)categoryView.findViewById(R.id.searchBtn);
		background1=(TextView)categoryView.findViewById(R.id.background1);
		searchText=(TextView)categoryView.findViewById(R.id.searchText);
		categoryListView=(ListView)categoryView.findViewById(R.id.firstCategoryListView);
		subCategoryListView=(ListView)categoryView.findViewById(R.id.secondCategoryListView);

		categoryAdapter = new CategoryAdapter(ma,StaticValueClass.firstLevelCategoryCells,textViews);
		categoryListView.setAdapter(categoryAdapter);
		categoryListView.setCacheColorHint(0);
		categoryListView.setVerticalScrollBarEnabled(false);
		categoryListView.setDividerHeight(StaticValueClass.screenWidth*32/720);
	
	    categoryGroupAdapter=new CategoryGroupAdapter(ma);
		subCategoryListView.setAdapter(categoryGroupAdapter);
	    subCategoryListView.setCacheColorHint(0);
	    searchText.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ma.loadSearchFragment();
			}
		});
	//    initMainCategoryListView();



		categoryListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Log.d("***categoryListView.setOnItemClick", "position:"+position);
				loadCategoryData(position);
				
			//	TextView item=(TextView)categoryListView.getChildAt(position);//view;
				TextView item=(TextView)textViews.get(position);
				item.setBackgroundResource(R.mipmap.category_selected_mark);
				item.setTextColor(Color.WHITE);
				if(oPosition1!=-1 && oPosition1!=position){
					oldView1.setBackgroundResource(R.drawable.trans_shape);
					oldView1.setTextColor(Color.rgb(64, 64, 64));
				}
				oldView1=item;
				oPosition1=position; 
				subCategoryListView.smoothScrollToPosition(0);
			}
			
		});
	      /*
			categoryListView.post(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					categoryListView.performItemClick(categoryListView.getChildAt(0), 0, categoryListView.getAdapter().getItemId(0));
					/
					oldView1=(TextView)categoryListView.getChildAt(0);
					oldView1.setBackgroundResource(R.drawable.category_selected_mark);
					oldView1.setTextColor(Color.WHITE);
					
				}
				
			}); 
			*/
		
		
		setParams();
		Log.d("CategoryFragment", "onCreateView");
		 if(StaticValueClass.isAfterKitKat)
			 categoryView.setPadding(0, StaticValueClass.statusBarHeight, 0, 0);
		return categoryView;
	//	return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d("CategoryFragment", "onResume");
	//	firstEnter=false;
		loadCategoryData(categoryIndex);
		
		
		
		
	}
	
	private void setParams(){
		RelativeLayout.LayoutParams params1=(RelativeLayout.LayoutParams)categoryListView.getLayoutParams();
		params1.width=StaticValueClass.screenWidth*20/72;
		params1.bottomMargin=params1.topMargin=StaticValueClass.screenWidth*32/720;
		
		RelativeLayout.LayoutParams params2=(RelativeLayout.LayoutParams)background1.getLayoutParams();
		params2.width=StaticValueClass.screenWidth*176/720;
		
//		selectMarkText.scrollBy(300, 100);
		
	}
	
	public void loadCategoryData(int index){

		categoryIndex=index;
		categoryGroupAdapter.setCategoryData(StaticValueClass.firstLevelCategoryCells.get(index).childCells);
		
		categoryGroupAdapter.setSubPath(StaticValueClass.getGB2312Code(StaticValueClass.firstLevelCategoryCells.get(index).title));
		categoryGroupAdapter.notifyDataSetChanged();
		
 }
	
	
	private void initTextViews(){
		textViews=new ArrayList<TextView>();
		int leftMargin=StaticValueClass.dip2px(this.getContext(), 16);
		for(int i=0;i<20;i++){
			TextView textView=new TextView(ma);
			ListView.LayoutParams params=new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT, ListView.LayoutParams.MATCH_PARENT);
			params.height=StaticValueClass.screenWidth*62/720;
			textView.setLayoutParams(params);
	//	   ColorStateList csl=ma.getResources().getColorStateList(R.drawable.text_color_selector);
		   textView.setTextColor(Color.rgb(64, 64, 64));
		   textView.setGravity(Gravity.CENTER_VERTICAL);
		   textView.setTextSize(15);
		   textView.setPadding(leftMargin, textView.getPaddingTop(), textView.getPaddingRight(), textView.getPaddingBottom());
		textViews.add(textView);
		}
		
		textViews.get(0).setBackgroundResource(R.mipmap.category_selected_mark);
		textViews.get(0).setTextColor(Color.WHITE);
		oldView1=textViews.get(0);
	}
	
	private void getCategoryData(){
		new Thread(new Runnable(){
           String serverUrl="http://www.qcygl.com/";
			@Override
			public void run() {
				// TODO Auto-generated method stub
				ArrayList<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
				// nameValuePairs.add(new BasicNameValuePair("demander",StaticValue.currentClientTel));
	              Looper.prepare(); 
	              try{
	                   HttpClient httpclient = new DefaultHttpClient();
	                   HttpPost httppost = new HttpPost(serverUrl+"ch_getCategoryInfo.php");
	                   httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,HTTP.UTF_8));
	                   HttpResponse response = httpclient.execute(httppost);
	                   Log.d("***ch_getCategoryInfo:", " recode:"+response.getStatusLine().getStatusCode());
	                   if(response.getStatusLine().getStatusCode()==200){
	                	   
	                	  String mResult=EntityUtils.toString(response.getEntity());
	                	//  Log.d("get ch_getCategoryInfo resultcc:", mResult);
	                	   String[] buffers=mResult.split(",");
	                	   CategoryCell root=new CategoryCell("root");
	                	   parseData(buffers,1,root);
	                	   if(categoryAdapter!=null){
								for(int i=0;i<root.childCells.size();i++){
									StaticValueClass.firstLevelCategoryCells.add(root.childCells.get(i));
								}
								 ma.runOnUiThread(new Runnable(){

										@Override
										public void run() {
											// TODO Auto-generated method stub
											
											categoryAdapter.notifyDataSetChanged();
											categoryListView.post(new Runnable(){

												@Override
												public void run() {
													// TODO Auto-generated method stub
													categoryListView.performItemClick(categoryListView.getChildAt(0), 0, categoryListView.getAdapter().getItemId(0));
												
												}
												
											}); 
											
										}
				                		   
				                	   }); 
				                	
							}else    StaticValueClass.firstLevelCategoryCells=root.childCells;
	                	   
	                	  
	                   }
	              }catch(Exception e){
	                   Log.e("log_tag", "Error in http connection ch_getCategoryInfo "+e.toString());
	                 
	              }

			}
			
		}).start();	
	}

	
	private  int  parseData(String[] words,int cur,CategoryCell root){
		CategoryCell child=null;
		for(int i=cur;i<words.length;i++){
			if(words[i].equals("V")){
				i=parseData(words,i+1,root.childCells.get(root.childCells.size()-1));
			}else if(words[i].equals("A")){
				return (i);
			} else {
				//Log.d("***parseData", root.title+":"+words[i]);
				 child=new CategoryCell(words[i]);
				root.childCells.add(child);
			}
		}
		return words.length;
	}

}
