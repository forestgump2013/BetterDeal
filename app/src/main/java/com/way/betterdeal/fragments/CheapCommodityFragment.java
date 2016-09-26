package com.way.betterdeal.fragments;

import java.util.ArrayList;

import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;
import com.way.betterdeal.adapters.CommodityAdapter;
import com.way.betterdeal.object.Commodity;
import com.way.betterdeal.view.HeaderGridView;
//import com.way.miniqq.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:wangjie@cyyun.com
 * Date: 13-6-14
 * Time: 下午2:39
 */
public class CheapCommodityFragment extends Fragment{
	
	MainActivity ma;
	//MultiColumnListView commodityList;
	HeaderGridView commodityList;
	View ccView;
	CommodityAdapter commodityAdapter;
	ArrayList<Commodity> cheapCommodities;
	Handler handler;
	
	public CheapCommodityFragment(){

	}
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        System.out.println("BBBBBBBBBBB____onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("BBBBBBBBBBB____onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("BBBBBBBBBBB____onCreateView");
        ccView=inflater.inflate(R.layout.cheaper_commodity_fragment , container, false);
        commodityList=(HeaderGridView)ccView.findViewById(R.id.commodityList);
        ma=(MainActivity)this.getActivity();
        cheapCommodities=new ArrayList<Commodity>();
        addCommodity(11);
        commodityAdapter=new CommodityAdapter(ma,cheapCommodities);
        View headView=inflater.inflate(R.layout.cheap_commodity_specials_layout, null);
        commodityList.addHeaderView(headView);
        commodityList.setAdapter(commodityAdapter);
        commodityList.setOnScrollListener(new OnScrollListener(){

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				if ((firstVisibleItem + visibleItemCount == totalItemCount)  
		                && (totalItemCount != 0)) {  
					ma.runOnUiThread(new Runnable(){

						@Override
						public void run() {
							// TODO Auto-generated method stub
							Message message = new Message();  
			                message.what = 1;  
			                handler.sendMessage(message);
						}
						
					});
				}
			}


        	
        });
        /*
        commodityList.setOnScrollListener(new OnScrollListener(){

			public void onScrollStateChanged(PLA_AbsListView view,
					int scrollState) {
				// TODO Auto-generated method stub
				
			}

			public void onScroll(PLA_AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				if ((firstVisibleItem + visibleItemCount == totalItemCount)  
		                && (totalItemCount != 0)) {  
					ma.runOnUiThread(new Runnable(){

						public void run() {
							// TODO Auto-generated method stub
							Message message = new Message();  
			                message.what = 1;  
			                handler.sendMessage(message);
						}
						
					});
				}
				
			}
        	
        }); */
       // commodityList
        /*
        commodityList.setOnTouchListener(new OnTouchListener(){

			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				int action = event.getAction();
	            switch (action) {
	            case MotionEvent.ACTION_DOWN:
	                // Disallow ScrollView to intercept touch events.
	                v.getParent().requestDisallowInterceptTouchEvent(true);
	                break;

	            case MotionEvent.ACTION_UP:
	                // Allow ScrollView to intercept touch events.
	                v.getParent().requestDisallowInterceptTouchEvent(false);
	                break;
	            }

	            // Handle ListView touch events.
	            v.onTouchEvent(event);
	            return true;
			}
        	
        }); */
        
        initHandler();
        return ccView;
    }
    
    private void addCommodity(int count){
    	
        for(int i=0;i<count;i++){
        	cheapCommodities.add(new Commodity());
        } 
    }
    private void initHandler(){
    	handler=new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch(msg.what){
				case 1:
					addCommodity(10);
					commodityAdapter.notifyDataSetChanged();
					commodityList.setSelection(commodityList.getCount()-1);
					break;
				case 2:
					break;
				default:
						break;
					
				}
			}
    		
    	};
    		
    	
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("BBBBBBBBBBB____onActivityCreated");
        
        
        
        /*
        this.getView().findViewById(R.id.clickme).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // 获得绑定的FragmentActivity
            	MainActivity activity = ((MainActivity)getActivity());
                // 获得TabAFm的控件
                EditText editText = (EditText) activity.fragments.get(2).getView().findViewById(R.id.edit);

                Toast.makeText(activity, activity.hello + editText.getText(), Toast.LENGTH_SHORT).show();
            }
        }); */
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("BBBBBBBBBBB____onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("BBBBBBBBBBB____onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("BBBBBBBBBBB____onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        System.out.println("BBBBBBBBBBB____onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.out.println("BBBBBBBBBBB____onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("BBBBBBBBBBB____onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        System.out.println("BBBBBBBBBBB____onDetach");
    }




}
