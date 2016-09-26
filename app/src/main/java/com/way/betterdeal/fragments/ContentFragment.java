package com.way.betterdeal.fragments;


import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

//import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;


import com.alibaba.sdk.android.AlibabaSDK;
import com.alibaba.sdk.android.trade.TradeConstants;
import com.alibaba.sdk.android.trade.TradeService;
import com.alibaba.sdk.android.trade.callback.TradeProcessCallback;
import com.alibaba.sdk.android.trade.model.TaokeParams;
import com.alibaba.sdk.android.trade.model.TradeResult;
import com.alibaba.sdk.android.trade.page.ItemDetailPage;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;
import com.way.betterdeal.object.Commodity;
import com.way.wasabeef.recyclerview_animation.SlideInBottomAnimationAdapter;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by moon.zhong on 2015/3/9.
 */
public class ContentFragment extends Fragment {
	
	View view;
	RecyclerView contentRecyclerView ;
	GridLayoutManager gridLayoutManager;
	MyAdapter myAdapter;
	
	ArrayList<Commodity> commos;
	Typeface typeface;  
	int space,tSize;
	TextView id_text_msg;
	String tag;
	 TradeService tradeService;

    public static Fragment instance(String msg){
        ContentFragment fragment = new ContentFragment() ;
        Bundle bundle = new Bundle() ;
        bundle.putString("msg",msg);
        fragment.setArguments(bundle);
        return fragment ;
    }
    
    public ContentFragment(){
		commos=new ArrayList<Commodity>();
    	
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    	
    	 view=inflater.inflate(R.layout.pager_item, container, false);
    	 id_text_msg=(TextView)view.findViewById(R.id.id_text_msg);
    	 contentRecyclerView=(RecyclerView)view.findViewById(R.id.contentRecyclerView);
         initRecyclerView();
         id_text_msg.setText(tag);
         getDiscoutInfo();
         typeface =Typeface.createFromAsset(this.getActivity().getAssets(),"fonts/hanyi_thin_round1.ttf");
    	return view;
    }
    

    @Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*
        Bundle bundle = getArguments() ;
        String msg = bundle.getString("msg") ;
        TextView textView = (TextView) view.findViewById(R.id.id_text_msg);
        textView.setText(msg);
        */
       
    }
    
    private void initRecyclerView(){
    	space=StaticValueClass.dip2px(ContentFragment.this.getContext() , 4);
    	 gridLayoutManager=new GridLayoutManager(this.getContext(),2);
    	 gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
 			
 			@Override
 			public int getSpanSize(int position) {
 				// TODO Auto-generated method stub
 			//	return position==0? gridLayoutmanager.getSpanCount():1;
 				if( position==commos.size()){
 					return gridLayoutManager.getSpanCount();
 				}else return 1;
 			}
 		});
         contentRecyclerView.setLayoutManager(gridLayoutManager);
         myAdapter=new MyAdapter();
         
      //   AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(myAdapter);
      //   ScaleInAnimationAdapter scaleInAdapter=new ScaleInAnimationAdapter(alphaAdapter);
         SlideInBottomAnimationAdapter slideInBottomAdapter=new SlideInBottomAnimationAdapter(myAdapter);
     
         slideInBottomAdapter.setFirstOnly(true);
         slideInBottomAdapter.setDuration(500);
        slideInBottomAdapter.setInterpolator(new OvershootInterpolator(.5f));
         contentRecyclerView.addItemDecoration(new SpaceItemDecoration());
         contentRecyclerView.setAdapter(slideInBottomAdapter);
      //   contentRecyclerView.setItemAnimator(new FadeInAnimator(new OvershootInterpolator(1f)));
    }
    
    public class MyAdapter extends RecyclerView.Adapter<ViewHolder>{

		@Override
		public int getItemCount() {
			// TODO Auto-generated method stub
			return commos.size()+1;
		}
		
		

		@Override
		public int getItemViewType(int position) {
			// TODO Auto-generated method stub
			//return super.getItemViewType(position);
			if(position==commos.size()) return 1;
			else return 0;
		}



		@Override
		public void onBindViewHolder(ViewHolder vHolder, int position) {
			// TODO Auto-generated method stub
			if(vHolder instanceof VHItem ){
				VHItem holder=(VHItem)vHolder;
			//	holder.setPosition(position%10);
				holder.loadData(commos.get(position));
			}else if(vHolder instanceof FootHolder){
				FootHolder holder=(FootHolder)vHolder;
			}
		
		}
	

		@Override
		public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
			// TODO Auto-generated method stub
			View view;
			if(type==0){
				    view=ContentFragment.this.getActivity().getLayoutInflater().inflate(R.layout.commodity_poster, viewGroup, false);
			       return new VHItem(view);
			}else {
				  view=ContentFragment.this.getActivity().getLayoutInflater().inflate(R.layout.footer_view, viewGroup, false);
			       return new FootHolder(view);
			}
		    
		}

    	
    }
    
    class VHItem extends RecyclerView.ViewHolder{
   	 TextView title,price,pre_price,marketTitle;
       ImageView poster,marketMark,divider,couponMark;
       int position;
       public VHItem(View itemView) {
           super(itemView);
           poster=(ImageView)itemView.findViewById(R.id.posterView);
           marketMark=(ImageView)itemView.findViewById(R.id.markImageView);
           divider=(ImageView)itemView.findViewById(R.id.divider);
           marketTitle=(TextView)itemView.findViewById(R.id.mallTitle);
           title=(TextView)itemView.findViewById(R.id.item_title);
           couponMark=(ImageView)itemView.findViewById(R.id.couponMark);
           price=(TextView)itemView.findViewById(R.id.item_price);
           pre_price=(TextView)itemView.findViewById(R.id.item_reserve_price);
           ViewGroup.LayoutParams params=poster.getLayoutParams();
   		params.height=StaticValueClass.screenWidth*7/15;
  
   		pre_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
   		divider.setVisibility(View.INVISIBLE);	
   		LinearLayout.LayoutParams params1=(LinearLayout.LayoutParams)divider.getLayoutParams();
   		params1.height=16;
   		//
   		title.setTypeface(typeface);
       }
       
       public void setPosition(int p){
    	   position=p;
    	//   String url="http://www.qcygl.com/betterCommoditisPic/b"+p+".jpg";
      	//	StaticValueClass.asynImageLoader.showImageAsyn(poster, url, R.drawable.blank_background);
    	   
       }
       public void loadData(final Commodity commo){
       	
		title.setText(commo.title);
    	price.setText(" ¥ "+commo.price);
    	pre_price.setText(""+commo.reserve_price);	
    	commo.picUrl="CHDiscoutInfo/"+StaticValueClass.getGB2312Code(commo.title)+".jpg";
        StaticValueClass.asynImageLoader.showImageAsyn(poster, commo.picUrl, R.mipmap.blank_background);
        if(commo.coupon==1){
        	couponMark.setVisibility(View.VISIBLE);
        }else couponMark.setVisibility(View.GONE);
   	    switch(commo.market){
   	    case 1: marketMark.setImageResource(R.mipmap.taobao_c_mark);
   	    		marketTitle.setText("淘宝");
   	    		break;
   	    case 2: marketMark.setImageResource(R.mipmap.tianmao_c_mark);
   				marketTitle.setText("天猫");
   				break;
   			
   	    	
   	    }
    	 
   		poster.setOnClickListener(new OnClickListener(){

   			@Override
				public void onClick(View v) {
   				// TODO Auto-generated method stub
   				                    //525813703784
   				showItemDetailPage(v,""+commo.itemId);
   	
   				//itemService.showItemDetailByOpenItemId(ma, tradeProcessCallback, null, "eg.AAHd5d-HAAeGwJedwSnHktBI", 1, null);
   			//	Map<String, String> exParams = new HashMap<String, String>();
   			//	exParams.put(TradeConstants.ITEM_DETAIL_VIEW_TYPE,TradeConstants.TAOBAO_H5_VIEW );
   			//	exParams.put(TradeConstants.ITEM_DETAIL_VIEW_TYPE, TradeConstants.ITEM_DETAIL_VIEW_TYPE);
   			//	itemService.showItemDetailByItemId(ma, tradeProcessCallBack, null, commo.itemId, 1, exParams);
   			}
   			
   		});
       }
       
       private void showItemDetailPage(View view ,String itemId){
       	tradeService=AlibabaSDK.getService(TradeService.class);
       	ItemDetailPage itemDetailPage=new ItemDetailPage(itemId,null);
       	TaokeParams taokeParams=new TaokeParams();
       	taokeParams.pid="mm_28xxxx4_4xxxx71_151xxxxx5";
       	tradeService.show(itemDetailPage, null, ContentFragment.this.getActivity(), null, new TradeProcessCallback(){

   			@Override
   			public void onFailure(int arg0, String arg1) {
   				// TODO Auto-generated method stub
   				
   			}

   			@Override
   			public void onPaySuccess(TradeResult arg0) {
   				// TODO Auto-generated method stub
   				
   			}
       		
       	});
       	
       	
       }
       
   }
    
    class FootHolder extends RecyclerView.ViewHolder{
    	TextView textView;
		public FootHolder(View view) {
			super(view);
			textView=(TextView)view.findViewById(R.id.textView);
			textView.setTypeface(typeface);
			// TODO Auto-generated constructor stub
		}
    	
    }
    
    public class SpaceItemDecoration extends RecyclerView.ItemDecoration{

		@Override
		public void getItemOffsets(Rect outRect, View view,
				RecyclerView parent, State state) {
			// TODO Auto-generated method stub
		//	super.getItemOffsets(outRect, view, parent, state);
		   int 	position=parent.getChildLayoutPosition(view);
		   if(position==tSize){
			   outRect.left=outRect.right=0;
		   }else  if(position%2==0){
			   outRect.right=space;
		   }else outRect.left=space;
		   outRect.bottom=2*space;
		}
    	
    }
    
    private void getDiscoutInfo(){
    	if(commos.size()>0){
			Log.d("****ContentFragment","getDiscoutInfo reload data");
			myAdapter.notifyDataSetChanged();
			return;
		}
		Log.d("****ContentFragment","getDiscoutInfo");
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
		//		ArrayList<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
	              Looper.prepare(); 
	              try{
	                   HttpClient httpclient = new DefaultHttpClient(StaticValueClass.httpParameters);
	                   HttpPost httppost = new HttpPost(StaticValueClass.serverAddress+"ch_get_discout_info.php");
	           //        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,HTTP.UTF_8));
	                   HttpResponse response = httpclient.execute(httppost);
	                   Log.d("***ch_get_discout_info:", " recode:"+response.getStatusLine().getStatusCode());
	                   if(response.getStatusLine().getStatusCode()==200){
	                	   commos.clear();
	                	  String mResult=EntityUtils.toString(response.getEntity());
	                //	  Log.d("get commodity result:", mResult);
	                	   JSONObject jsonObject;

	                	   JSONArray jsonArray = new JSONArray(mResult);
	                	   for(int i=0;i<jsonArray.length();i++){
	                		   jsonObject=(JSONObject)jsonArray.opt(i);
	                		  Commodity item=new Commodity(jsonObject.getInt("idx"),jsonObject.getInt("market"),
	                				  jsonObject.getLong("id"),0);
	                		  item.loadData(jsonObject.getString("title"), (float)jsonObject.getDouble("price"), (float)jsonObject.getDouble("reserve_price"), "",
	                				  jsonObject.getInt("coupon"));
	                	
	                		  commos.add(item);
	                	   }
	                	   
	                	   ContentFragment.this.getActivity().runOnUiThread(new Runnable(){

							@Override
							public void run() {
								// TODO Auto-generated method stub
								tSize=commos.size();
								myAdapter.notifyDataSetChanged();
								
							}
	                		   
	                	   }); 
	                   }
	              }catch(SocketTimeoutException e){
	            	//  Toast.makeText(ma, "数据刷新超时！", Toast.LENGTH_SHORT).show();
	              }
	              catch(Exception e){
	            	//  Toast.makeText(ma, "数据刷新失败！", Toast.LENGTH_SHORT).show();
	                //   Log.e("log_tag", "Error in http connection :ch_get_cheap_info "+e.toString());
	                  
	              }
			}
			
		}).start();

    }
    
    
}
