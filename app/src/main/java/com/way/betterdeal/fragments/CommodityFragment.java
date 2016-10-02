package com.way.betterdeal.fragments;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

/*
import com.alibaba.sdk.android.AlibabaSDK;
import com.alibaba.sdk.android.trade.ItemService;
import com.alibaba.sdk.android.trade.TradeConstants;
import com.alibaba.sdk.android.trade.callback.TradeProcessCallback;
import com.alibaba.sdk.android.trade.model.TradeResult;
import com.alibaba.sdk.android.webview.UiSettings;
import com.taobao.tae.sdk.webview.TaeWebViewUiSettings;
*/
import com.cmad.swipe.OnPullListener;
import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;
import com.way.betterdeal.adapters.BetterCommodityRecyclerAdapter;
import com.way.betterdeal.object.Commodity;
import com.way.betterdeal.view.CustomSwipeRefreshLayout;
import com.way.betterdeal.view.JazzyViewPager;
import com.way.betterdeal.view.OutlineContainer;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.RecyclerView.State;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CommodityFragment extends Fragment {
	
	MainActivity ma;
	protected CustomSwipeRefreshLayout mRefreshLayout;
	View shopingView,pull_to_refresh_headview;
	PullHeadViewHolder pullHeadViewHolder;
	Button toTopBtn;
	//RelativeLayout hintView;
	LinearLayout indexView;
	RecyclerView betterCommodityRecyclerView;
    TextView mTitle,textContent1;
	BetterCommodityRecyclerAdapter betterCommodityRecyclerAdapter;
	RelativeLayout signTextView;
	ArrayList<Commodity> commodities;
	JazzyViewPager jazzyViewPager;
	ImageView[] movingImageViews,indexViews;
	Animation animation;
	private RotateAnimation mRotateAnimation;
    private RotateAnimation mRotateAnimationDown;
	int lastIndex,tSize;
	private Animation rotateAnimation;
	LayoutInflater layoutInflater;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
//	long[] itemIds={45311190469l,10000008229470l,19066475128l,45441776391l,45358685822l,45499272279l};
	int urlCount=0,currentItemNum,addItemCount=0;
	Runnable addItemRunnable;
	public CommodityFragment( ){
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,  Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		ma=(MainActivity) this.getActivity();
		layoutInflater=inflater;
		shopingView=inflater.inflate(R.layout.commodity_fragment_layout, container,false);

		mTitle=(TextView)shopingView.findViewById(R.id.title);
		mRefreshLayout= (CustomSwipeRefreshLayout) shopingView.findViewById(R.id.refresh_layout);
		toTopBtn=(Button)shopingView.findViewById(R.id.toTopBtn);
		ImageView pull_refresh_background =(ImageView) shopingView.findViewById(R.id.pull_refresh_background);
		FrameLayout.LayoutParams iParams=(FrameLayout.LayoutParams)pull_refresh_background.getLayoutParams();
		iParams.height=StaticValueClass.screenWidth*9/20;
		betterCommodityRecyclerView=(RecyclerView)shopingView.findViewById(R.id.betterCommodityRecyclerView);
		signTextView=(RelativeLayout)shopingView.findViewById(R.id.signTextView);
		textContent1=(TextView)shopingView.findViewById(R.id.textContent1);

		commodities=new ArrayList<Commodity>();
		mTitle.setTypeface(StaticValueClass.huangKangFont);
		final GridLayoutManager gridLayoutmanager = new GridLayoutManager(this.getActivity(), 2);
		gridLayoutmanager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
			
			@Override
			public int getSpanSize(int position) {
				// TODO Auto-generated method stub
			//	return position==0? gridLayoutmanager.getSpanCount():1;
				if(position==0 || position==(StaticValueClass.betterCommodities.size()+1)){
					return gridLayoutmanager.getSpanCount();
				}else return 1;
			}
		});
		
		betterCommodityRecyclerAdapter = new BetterCommodityRecyclerAdapter( (MainActivity) this.getActivity() , StaticValueClass.betterCommodities);
	    betterCommodityRecyclerView.setHasFixedSize(true);
	    betterCommodityRecyclerView.setVerticalScrollBarEnabled(false);
	    betterCommodityRecyclerView.setLayoutManager(gridLayoutmanager);
	    betterCommodityRecyclerView.addItemDecoration(new SpaceItemDecoration(StaticValueClass.screenWidth/45));
		betterCommodityRecyclerView.setAdapter(betterCommodityRecyclerAdapter);
		betterCommodityRecyclerView.addOnScrollListener(new OnScrollListener(){

			@Override
			public void onScrollStateChanged(RecyclerView recyclerView,
					int newState) {
				// TODO Auto-generated method stub
				super.onScrollStateChanged(recyclerView, newState);
				switch(newState){
				case RecyclerView.SCROLL_STATE_IDLE :
				//	if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())  
	               if(recyclerView.computeVerticalScrollOffset()<160)
					toTopBtn.setVisibility(View.GONE);
					break;
				case RecyclerView.SCROLL_STATE_SETTLING:
					toTopBtn.setVisibility(View.VISIBLE);
					break;
				case RecyclerView.SCROLL_STATE_DRAGGING:
					toTopBtn.setVisibility(View.VISIBLE);
					break;
					
				}
				
			}

			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				// TODO Auto-generated method stub
				super.onScrolled(recyclerView, dx, dy);
			}
			
		});
		signTextView.setAlpha(0.8f);
		toTopBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				betterCommodityRecyclerView.smoothScrollToPosition(0);
			}
		});
		if (!StaticValueClass.currentBuyer.isLogined())
			textContent1.setText("登录用户获得多多福利！");
		else {
			if (!StaticValueClass.currentBuyer.isSigned()){
                textContent1.setText("今天还没有签到，签到可以得金币换福利哦~");
			}else {
				signTextView.setVisibility(View.GONE);
			}
		}
		signTextView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (signTextView.getVisibility()==View.VISIBLE){
					if (textContent1.getText().equals("登录用户获得多多福利！")){
						ma.switchTabFragment(4);
					}else ma.loadSignFragment();
					signTextView.setVisibility(View.GONE);
				}

			}
		});
	//	startWaitAnimation();

		pull_to_refresh_headview=shopingView.findViewById(R.id.pull_to_refresh_headview);
		this.initPullToRefresHeadView(pull_to_refresh_headview);
		mRefreshLayout.hideColorProgressBar();
		mRefreshLayout.setOnPullListener(new OnPullListener(){

			@Override
			public void onPulling(View headview) {
				// TODO Auto-generated method stub
				 Log.d("*OnPullListener", "onPulling");
				PullHeadViewHolder holder = (PullHeadViewHolder) headview.getTag();
			        holder.mRefreshImageview.clearAnimation();
			        holder.mRefreshImageview.setVisibility(View.VISIBLE);
			        holder.mRefreshProgress.setVisibility(View.GONE);
			        holder.mRefreshTitle.setText("下拉刷新");
			        holder.mRefreshImageview.startAnimation(mRotateAnimationDown);
			}

			@Override
			public void onCanRefreshing(View headview) {
				// TODO Auto-generated method stub
				 Log.d("*OnPullListener", "onCanRefreshing");
				PullHeadViewHolder holder = (PullHeadViewHolder) headview.getTag();
		        holder.mRefreshImageview.startAnimation(mRotateAnimation);
		        holder.mRefreshTitle.setText("松开刷新"+"...");
		       
			}

			@Override
			public void onRefreshing(View headview) {
				// TODO Auto-generated method stub
				 Log.d("*OnPullListener", "onRefreshing");
				PullHeadViewHolder holder = (PullHeadViewHolder) headview.getTag();
			        holder.mRefreshImageview.clearAnimation();
			        holder.mRefreshImageview.setVisibility(View.GONE);
			       holder.mRefreshProgress.setVisibility(View.VISIBLE);
			        holder.mRefreshTitle.setText("正在刷新" + "...");
			   //     getCommodityInfo(true);
			        
			        new Handler().postDelayed(new Runnable() {
			            @Override
			            public void run() {
			                mRefreshLayout.setRefreshing(false);
			            }
			        }, 200); 
				
			}
        	
        });
		getCommodityInfo(false);
		
		if(StaticValueClass.isAfterKitKat)
			shopingView.setPadding(0, StaticValueClass.statusBarHeight, 0, 0);
		shopingView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});

		return shopingView;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

	}


	
	private void startWaitAnimation(){
		ma.runOnUiThread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				rotateAnimation = new RotateAnimation(0f, 360f,Animation.RELATIVE_TO_SELF,0.5f,
						Animation.RELATIVE_TO_SELF,0.5f);  
				rotateAnimation.setRepeatMode(Animation.INFINITE);
				rotateAnimation.setDuration(2000);
		       rotateAnimation.setRepeatCount(3000);
		        rotateAnimation.start();
			}
			
		});
		
        
	}
	
	
	private void initPullToRefresHeadView(View pullRefreshView){
		ViewGroup.LayoutParams params=  pullRefreshView.getLayoutParams();
		params.height=StaticValueClass.screenWidth*9/20-100;
		pullHeadViewHolder= new PullHeadViewHolder();
		
		mRotateAnimation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateAnimation.setDuration(150);
        mRotateAnimation.setFillAfter(true);

        mRotateAnimationDown = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateAnimationDown.setDuration(150);
        mRotateAnimationDown.setFillAfter(true);
        pullHeadViewHolder.mRefreshImageview = (ImageView) pullRefreshView.findViewById(R.id.pull_to_refresh_image);
        pullHeadViewHolder.mRefreshProgress = (ProgressBar) pullRefreshView.findViewById(R.id.pull_to_refresh_progress);
        pullHeadViewHolder.mRefreshTitle = (TextView) pullRefreshView.findViewById(R.id.pull_to_refresh_text);
        pullRefreshView.setTag(pullHeadViewHolder);
		
	}
	/*
	public void setBackGround(){
	//	shopingView.setBackgroundColor(Color.rgb(252, 50, 101));
	} */
	
	public void getCommodityInfo(final boolean isPull){
		
		if(StaticValueClass.betterCommodities.size()>0){
			Log.d("****CommodityFragment","getCommodityInfo reload data");
			/*
			if(isPull){
				 ma.runOnUiThread(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						mRefreshLayout.setRefreshing(false);
					}
				 });
			}  */
			return;
		}
		Log.d("****CommodityFragment","getCommodityInfo");
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
		//		ArrayList<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
	              Looper.prepare(); 
	              try{
	                   HttpClient httpclient = new DefaultHttpClient(StaticValueClass.httpParameters);
	                   HttpPost httppost = new HttpPost(StaticValueClass.serverAddress+"ch_get_cheap_info.php");
	           //        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,HTTP.UTF_8));
	                   HttpResponse response = httpclient.execute(httppost);
	                   Log.d("***ch_get_cheap_info:", " recode:"+response.getStatusLine().getStatusCode());
	                   if(response.getStatusLine().getStatusCode()==200){
	                	   StaticValueClass.betterCommodities.clear();
	                	  String mResult=EntityUtils.toString(response.getEntity());
						   Log.d(StaticValueClass.logTag, "cheap info:"+mResult);
	                	   JSONObject jsonObject;

	                	   JSONArray jsonArray = new JSONArray(mResult);
	                	   for(int i=0;i<jsonArray.length();i++){
	                		   jsonObject=(JSONObject)jsonArray.opt(i);

	                		  Commodity item=new Commodity(jsonObject.getInt("idx"),jsonObject.getInt("market"),
	                				  0,0);
	                		  item.loadData(jsonObject.getString("title"), (float)jsonObject.getDouble("price"), (float)jsonObject.getDouble("reserve_price"),jsonObject.getString("pic_name"),
	                				  jsonObject.getString("coupon_link"),jsonObject.getString("web_link"));
	                	      item.category="1";
							  item.loadExtraData(jsonObject.getString("last_time"),jsonObject.getString("coupon_link"));
							   Log.d(StaticValueClass.logTag, "cheap item:"+item.toString());
	                		  StaticValueClass.betterCommodities.add(item);
	                	   }
	                	   
	                	   CommodityFragment.this.getActivity().runOnUiThread(new Runnable(){

							@Override
							public void run() {
								// TODO Auto-generated method stub
								tSize= StaticValueClass.betterCommodities.size();
								betterCommodityRecyclerAdapter.notifyDataSetChanged();
								if(isPull){
									mRefreshLayout.setRefreshing(false);
								}
							}
	                		   
	                	   }); 
	                   }else{
	                	   if(isPull){
								mRefreshLayout.setRefreshing(false);
							}
	                	   Toast.makeText(ma, "数据刷新失败！", Toast.LENGTH_SHORT).show();
	                   }
	              }catch(SocketTimeoutException e){
	            	//  Toast.makeText(ma, "数据刷新超时！", Toast.LENGTH_SHORT).show();
	              }
	              catch(Exception e){
	            	//  Toast.makeText(ma, "数据刷新失败！", Toast.LENGTH_SHORT).show();
	                   Log.e(StaticValueClass.logTag, "Error in http connection :ch_get_cheap_info "+e.toString());
	                   if(isPull){
	                	   ma.runOnUiThread(new Runnable(){

	       					@Override
	       					public void run() {
	       						// TODO Auto-generated method stub
	       						mRefreshLayout.setRefreshing(false);
	       					}
	       				 });
						}
	              }
			}
			
		}).start();
	}
	
	 
	 public class SpaceItemDecoration extends RecyclerView.ItemDecoration{
			private int space,position;
			public SpaceItemDecoration(int ss){
				space=ss;
			}
			@Override
			public void getItemOffsets(Rect outRect, View view,
					RecyclerView parent, State state) {
				// TODO Auto-generated method stub
			//	super.getItemOffsets(outRect, view, parent, state);
				position=parent.getChildPosition(view);
				if(position>0){
					if(position==(tSize+1)){
						outRect.left=outRect.right=space;
					}else if(position%2==1){
						outRect.left=space;
						outRect.right=space/2;
					}else {
						outRect.right=space;
						outRect.left=space/2;
					}
					outRect.bottom=space;
				}
				
			}
			
		}
	 
	  class PullHeadViewHolder{
		  ImageView mRefreshImageview;
	      TextView mRefreshTitle;
	      ProgressBar mRefreshProgress;
	 }
}
