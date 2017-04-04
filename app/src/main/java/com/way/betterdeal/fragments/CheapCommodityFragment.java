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


}
