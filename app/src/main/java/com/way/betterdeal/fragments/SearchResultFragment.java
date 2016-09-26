package com.way.betterdeal.fragments;

import java.util.ArrayList;

import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;
import com.way.betterdeal.object.Commodity;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchResultFragment extends Fragment {
	
	
	MainActivity ma;
	
	View view;
	
	RecyclerView resultRecyclerView;
	RecyclerViewAdapter recyclerViewAdapter;
	
	LinearLayoutManager linearLayoutManager;
	ArrayList<Commodity> resultCommodities;
	
	public SearchResultFragment(){

		resultCommodities=new ArrayList<Commodity>();
		
	}
	
	
	
	
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		ma=(MainActivity)this.getActivity();
		view=inflater.inflate(R.layout.search_result_fragment, container, false);
		initRecyclerView();
		return view;
	//	return super.onCreateView(inflater, container, savedInstanceState);
	}




	private void initRecyclerView(){
		resultRecyclerView=(RecyclerView)view.findViewById(R.id.resultRecyclerView);
		recyclerViewAdapter=new RecyclerViewAdapter();
		linearLayoutManager=new LinearLayoutManager(ma,LinearLayoutManager.VERTICAL,false);
		resultRecyclerView.setLayoutManager(linearLayoutManager);
		resultRecyclerView.setAdapter(recyclerViewAdapter);
		resultRecyclerView.addItemDecoration(new SpaceItemDecoration());
		searchResults();
		
	}
	
	private void searchResults(){
//		hotSearchWordsGridView.setVisibility(View.INVISIBLE);
		Commodity commodity=new Commodity();
		commodity.loadData("国行Apple/苹果 iPod 64GB 迷你4平板电脑7.9英寸2", 1256.68f, 1689.69f, "",0);
		commodity.market=1;
		resultCommodities.add(commodity);
		
		Commodity commodity1=new Commodity();
		commodity1.loadData("花花公子男鞋春夏季网布跑步鞋镂空透气休闲鞋男士运动鞋子男单鞋", 96.6f, 125.36f, "",0);
		commodity.market=2;
		resultCommodities.add(commodity1);
		
		Commodity commodity2=new Commodity();
		commodity2.loadData("新款儿童太阳镜男女童潮墨镜 偏光镜猫咪太阳镜 软质小孩眼镜", 19.6f, 36.69f, "",0);
		commodity.market=1;
		resultCommodities.add(commodity2);
		
		recyclerViewAdapter.notifyDataSetChanged();
	//	showSearchResults();
		
	}
	
	public class SpaceItemDecoration extends ItemDecoration{

		@Override
		public void getItemOffsets(Rect outRect, View view,
				RecyclerView parent, State state) {
			// TODO Auto-generated method stub
		//	super.getItemOffsets(outRect, view, parent, state);
			outRect.bottom=StaticValueClass.dip2px(ma, 8);
		}
		
	}
	
	public  class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

		@Override
		public int getItemCount() {
			// TODO Auto-generated method stub
			return resultCommodities.size()  ;
		}

		@Override
		public void onBindViewHolder(ViewHolder viewHolder, int position) {
			// TODO Auto-generated method stub
			TViewHolder holder=(TViewHolder)viewHolder;
			holder.loadDate(resultCommodities.get(position));
			
		}

		@Override
		public ViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
			// TODO Auto-generated method stub
			View  view=ma.getLayoutInflater().inflate(R.layout.search_result_item, null);
			TViewHolder holder=new TViewHolder(view);
			return holder;
		//	return null;
		}
		
		public class TViewHolder extends RecyclerView.ViewHolder{
			
			public TextView title,price,oldPrice,mallTitle;
			public ImageView cImage,mImage;
			
			public TViewHolder(View view) {
				super(view);
				// TODO Auto-generated constructor stub
				cImage=(ImageView)view.findViewById(R.id.commodityImage);
				title=(TextView)view.findViewById(R.id.commodityTitle);
				price=(TextView)view.findViewById(R.id.commodityPrice);
				oldPrice=(TextView)view.findViewById(R.id.oldPrice);
				mallTitle=(TextView)view.findViewById(R.id.mallTitle);
				mImage=(ImageView)view.findViewById(R.id.mallMark);
			}
			
			public void loadDate(Commodity record){
				title.setText(record.title);
				price.setText("¥"+record.price);
				oldPrice.setText(""+record.reserve_price);
				switch(record.market){
				case 1: mallTitle.setText("淘宝");
						mImage.setImageResource(R.mipmap.taobao_c_mark);
						break;
				case 2: mallTitle.setText("天猫");
						mImage.setImageResource(R.mipmap.tianmao_c_mark);
						break;
				}
				
			}
			
			public void reMeasure(){
				
			}
			
		}
		
	}

}
