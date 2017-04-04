package com.way.betterdeal.adapters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.sdk.android.AlibabaSDK;
import com.alibaba.sdk.android.trade.ItemService;
import com.alibaba.sdk.android.trade.TradeConstants;
import com.alibaba.sdk.android.trade.TradeService;
import com.alibaba.sdk.android.trade.callback.TradeProcessCallback;
import com.alibaba.sdk.android.trade.model.TaokeParams;
import com.alibaba.sdk.android.trade.model.TradeResult;
import com.alibaba.sdk.android.trade.page.ItemDetailPage;
import com.way.betterdeal.MainActivity;
//import com.alibaba.sdk.android.trade.TradeConstants;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;
//import com.example.helloworld.R;
import com.way.betterdeal.object.Commodity;
import com.way.betterdeal.view.JazzyViewPager;
import com.way.betterdeal.view.OutlineContainer;
import com.way.betterdeal.view.JazzyViewPager.TransitionEffect;

import android.app.Activity;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

public class BetterCommodityRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	
	private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM1 = 1;
    private static final int TYPE_ITEM2 = 2;
    private static final int TYPE_FOOTER = 3;
    Typeface typeface,shortCutFont;
 
   // Header header;
    MainActivity ma;
    List<Commodity> listItems;
    int animationPosition;
    ItemService itemService=AlibabaSDK.getService(ItemService.class);
    TradeService tradeService;//=AlibabaSDK.getService(TradeService.class);
    TradeProcessCallback tradeProcessCallBack;
    
    public BetterCommodityRecyclerAdapter(MainActivity aa, List<Commodity> listItems)
    {
    	ma=aa;
    	//typeface =Typeface.createFromAsset(ma.getAssets(),"fonts/hanyi_thin_round1.ttf");
		typeface=StaticValueClass.hanYiThinFont;
        this.listItems = listItems;
        animationPosition=0;
        tradeProcessCallBack=new TradeProcessCallback(){

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPaySuccess(TradeResult arg0) {
				// TODO Auto-generated method stub
				
			}
        	
        };
    }
    
    private Commodity getItem(int position)
    {
        return listItems.get(position);
    }
 

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
	//	return super.getItemViewType(position);
		if(isPositionHeader(position))
            return TYPE_HEADER;
		else if(position==listItems.size()+1) return TYPE_FOOTER;
		else if(position%2==0) return TYPE_ITEM1;
		else return TYPE_ITEM2;
	}
	private boolean isPositionHeader(int position)
    {
        return position == 0;
    }

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
	//return 0;
		return listItems.size()+2;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		// TODO Auto-generated method stub
		if(holder instanceof VHHeader)
        {
            VHHeader vHheader = (VHHeader)holder;
          //  VHheader.txtTitle.setText(header.getHeader());
        }
        else if(holder instanceof VHItem)
        {   
        	VHItem itemHolder=(VHItem)holder;
        	itemHolder.loadData(listItems.get(position-1));
        	setHolderAnimation(itemHolder.itemView,position);
        } else if(holder instanceof FootHolder){
        	FootHolder fHolder=(FootHolder)holder;
        }
	
		
	}
	
	private void setHolderAnimation(View holderView, int position){
		if(position>animationPosition){
			Animation animaiton=AnimationUtils.loadAnimation(ma, android.R.anim.fade_in);
			holderView.startAnimation(animaiton);
			animationPosition=position;
		}
	}
	
	

	@Override
	public void onViewDetachedFromWindow(ViewHolder holder) {
		// TODO Auto-generated method stub
		holder.itemView.clearAnimation();
		super.onViewDetachedFromWindow(holder);
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		// TODO Auto-generated method stub
		
		if(viewType == TYPE_HEADER)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.better_fragment_header, parent, false);
          //  StaggeredGridLayoutManager.LayoutParams params=(StaggeredGridLayoutManager.LayoutParams)v.getLayoutParams();
         //   params.setFullSpan(true);
       //     v.setLayoutParams(new StaggeredGridLayoutManager.LayoutParams(StaggeredGridLayoutManager.LayoutParams.FILL_PARENT,40));
            return  new VHHeader(v);
        }
        else if(viewType == TYPE_ITEM1)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.commodity_poster1, parent, false);
            return new VHItem(v);
        } else if(viewType == TYPE_ITEM2)
        {
        	 View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.commodity_poster, parent, false);
             return new VHItem(v);
        } else if(viewType== TYPE_FOOTER){
        	
        	View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_view, parent, false);
         //  GridLayoutManager.LayoutParams params=(GridLayoutManager.LayoutParams)v.getLayoutParams();
         //    params.setFullSpan(true);
         //    v.setLayoutParams(new StaggeredGridLayoutManager.LayoutParams(StaggeredGridLayoutManager.LayoutParams.FILL_PARENT,40));
             
            return new FootHolder(v);
        }
		
		
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    
	//	return null;
	}
	
	
	
	class VHHeader extends RecyclerView.ViewHolder{
      //  TextView txtTitle;
		RelativeLayout movingScreen,discountView,signView,globalView,welfareView;
		JazzyViewPager jazzyViewPager;
		LinearLayout indexView;
		RelativeLayout listTitleFrame;
		TextView shortCutTitle1,shortCutTitle2,shortCutTitle3,shortCutTitle4,auxiliaryTitle1,
					auxiliaryTitle2,auxiliaryTitle3,auxiliaryTitle4,listTitle;
		ImageView specialItemImage1,specialItemImage2,specialItemImage3,
		specialItemImage4,specialItemImage5,specialItemImage6,specialItemImage7;
		ImageView[] movingImageViews;
		TextView[] indexViews;
		Animation animation;
        public VHHeader(View itemView) {
            super(itemView);
            jazzyViewPager=(JazzyViewPager)itemView.findViewById(R.id.jazzyViewPager);
            movingScreen=(RelativeLayout)itemView.findViewById(R.id.movingScreen);
    		indexView=(LinearLayout)itemView.findViewById(R.id.indexView);
			globalView=(RelativeLayout)itemView.findViewById(R.id.globalView);
			welfareView=(RelativeLayout)itemView.findViewById(R.id.welfareView);
    		discountView=(RelativeLayout)itemView.findViewById(R.id.discountView);
    		signView=(RelativeLayout)itemView.findViewById(R.id.signView);
    		init(itemView);
          
        }
        private void init(View headerView){
        	//concerned layoutParams.
        	ViewGroup.LayoutParams params=movingScreen.getLayoutParams();
    		params.height=StaticValueClass.screenWidth*10/21;
    		int dis,margin1,margin2;
    	//	headerView=this.layoutInflater.inflate(R.layout.better_fragment_header, parent, false);
    		 jazzyViewPager=(JazzyViewPager)headerView.findViewById(R.id.jazzyViewPager);
             movingScreen=(RelativeLayout)headerView.findViewById(R.id.movingScreen);
     		indexView=(LinearLayout)headerView.findViewById(R.id.indexView);
     		LinearLayout headFrame0=(LinearLayout)headerView.findViewById(R.id.headFrame0);
     		LinearLayout headFrame1=(LinearLayout)headerView.findViewById(R.id.headFrame1);
     		LinearLayout headFrame2=(LinearLayout)headerView.findViewById(R.id.headFrame2);
     		listTitleFrame=(RelativeLayout)headerView.findViewById(R.id.listTitleFrame);
     		auxiliaryTitle1=(TextView)headerView.findViewById(R.id.auxiliaryTitle1);
     		auxiliaryTitle2=(TextView)headerView.findViewById(R.id.auxiliaryTitle2);
     		auxiliaryTitle3=(TextView)headerView.findViewById(R.id.auxiliaryTitle3);
     		auxiliaryTitle4=(TextView)headerView.findViewById(R.id.auxiliaryTitle4);
     		shortCutTitle1=(TextView)headerView.findViewById(R.id.shortCutTitle1);
     		shortCutTitle2=(TextView)headerView.findViewById(R.id.shortCutTitle2);
     		shortCutTitle3=(TextView)headerView.findViewById(R.id.shortCutTitle3);
     		shortCutTitle4=(TextView)headerView.findViewById(R.id.shortCutTitle4);
     		listTitle=(TextView)headerView.findViewById(R.id.listTitle);
     		
     		specialItemImage1=(ImageView)headerView.findViewById(R.id.specialItemImage1);
     		specialItemImage2=(ImageView)headerView.findViewById(R.id.specialItemImage2);
     		specialItemImage3=(ImageView)headerView.findViewById(R.id.specialItemImage3);
     		specialItemImage4=(ImageView)headerView.findViewById(R.id.specialItemImage4);
     		specialItemImage5=(ImageView)headerView.findViewById(R.id.specialItemImage5);
     		specialItemImage6=(ImageView)headerView.findViewById(R.id.specialItemImage6);
     		specialItemImage7=(ImageView)headerView.findViewById(R.id.specialItemImage7);
     		
     		
     		// concerns function.
     		discountView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ma.loadSuperDiscountFragment();
				}
			});
     		signView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ma.loadSignFragment();
				//	ma.loadLoginFragment(true);
				}
			});

			globalView.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ma.loadWorldShppingFragment();
					//	ma.loadLoginFragment(true);
				}
			});

			welfareView.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ma.loadEarnsFragment();
					//	ma.loadLoginFragment(true);
				}
			});
     		//concern shortcut
     		LinearLayout.LayoutParams frameParams0=(LinearLayout.LayoutParams)headFrame0.getLayoutParams();
     		frameParams0.height=StaticValueClass.screenWidth*21/72;
     		dis=StaticValueClass.screenWidth/45;
     		frameParams0.bottomMargin=frameParams0.topMargin=dis;
     		frameParams0.leftMargin=frameParams0.rightMargin=dis;
     	//	headFrame0.setDividerPadding(100);
     		GradientDrawable dividerDrawable;//=new GradientDrawable();
     	//	dividerDrawable=(GradientDrawable)ma.getResources().getDrawable(R.drawable.shortcut_space);
			dividerDrawable=(GradientDrawable)headFrame0.getDividerDrawable();
     		dividerDrawable.setSize(dis, 10);
     		headFrame0.setDividerDrawable(dividerDrawable);
     		margin1=StaticValueClass.screenWidth/45;
     		margin2=StaticValueClass.screenWidth*5/72;
     		
     		RelativeLayout.LayoutParams aParams1=(RelativeLayout.LayoutParams)auxiliaryTitle1.getLayoutParams();
     		RelativeLayout.LayoutParams aParams2=(RelativeLayout.LayoutParams)auxiliaryTitle2.getLayoutParams();
     		RelativeLayout.LayoutParams aParams3=(RelativeLayout.LayoutParams)auxiliaryTitle3.getLayoutParams();
     		RelativeLayout.LayoutParams aParams4=(RelativeLayout.LayoutParams)auxiliaryTitle4.getLayoutParams();
     		aParams1.bottomMargin=aParams2.bottomMargin=aParams3.bottomMargin=aParams4.bottomMargin=margin1;
     		
     		auxiliaryTitle1.setTypeface(typeface);
     		auxiliaryTitle2.setTypeface(typeface);
     		auxiliaryTitle3.setTypeface(typeface);
     		auxiliaryTitle4.setTypeface(typeface);
     		
     		RelativeLayout.LayoutParams tParams1=(RelativeLayout.LayoutParams)shortCutTitle1.getLayoutParams();
     		RelativeLayout.LayoutParams tParams2=(RelativeLayout.LayoutParams)shortCutTitle2.getLayoutParams();
     		RelativeLayout.LayoutParams tParams3=(RelativeLayout.LayoutParams)shortCutTitle3.getLayoutParams();
     		RelativeLayout.LayoutParams tParams4=(RelativeLayout.LayoutParams)shortCutTitle4.getLayoutParams();
     		shortCutTitle1.setTypeface(typeface);
     		shortCutTitle2.setTypeface(typeface);
     		shortCutTitle3.setTypeface(typeface);
     		shortCutTitle4.setTypeface(typeface);
     		tParams1.bottomMargin=tParams2.bottomMargin=tParams3.bottomMargin=tParams4.bottomMargin=margin2;
     		//concerns special category.
     		ViewGroup.LayoutParams frameParams=headFrame1.getLayoutParams();
     		frameParams.height=StaticValueClass.screenWidth*31/72;
     		ViewGroup.LayoutParams imageParams1=specialItemImage1.getLayoutParams();
     		imageParams1.width=StaticValueClass.screenWidth*310/720;
     		ViewGroup.LayoutParams imageParams2=headFrame2.getLayoutParams();
     		imageParams2.height=StaticValueClass.screenWidth/3;
     		LinearLayout.LayoutParams imageParams7=(LinearLayout.LayoutParams)specialItemImage7.getLayoutParams();
     		imageParams7.height=StaticValueClass.screenWidth/10;
     		imageParams7.topMargin=imageParams7.bottomMargin=dis;
     		
     		LinearLayout.LayoutParams ltFrameParams=(LinearLayout.LayoutParams)listTitleFrame.getLayoutParams();
     		margin1=StaticValueClass.screenWidth/45;
     		ltFrameParams.bottomMargin=ltFrameParams.leftMargin=ltFrameParams.rightMargin=margin1;
     		listTitle.setTypeface(typeface);
        	//concerned data.
    		initMovingImageViews();
    		//others.
    		setupJazziness(TransitionEffect.CubeOut);
    		
    		//load image from server.
    		StaticValueClass.asynImageLoader.showImageAsyn(specialItemImage1, "tempImages/special_item1.jpg", R.mipmap.blank_background);
    		StaticValueClass.asynImageLoader.showImageAsyn(specialItemImage2, "tempImages/special_item2.jpg", R.mipmap.blank_background);
    		StaticValueClass.asynImageLoader.showImageAsyn(specialItemImage3, "tempImages/special_item3.jpg", R.mipmap.blank_background);
    		StaticValueClass.asynImageLoader.showImageAsyn(specialItemImage4, "tempImages/special_item4.jpg", R.mipmap.blank_background);
    		StaticValueClass.asynImageLoader.showImageAsyn(specialItemImage5, "tempImages/special_item5.jpg", R.mipmap.blank_background);
    		StaticValueClass.asynImageLoader.showImageAsyn(specialItemImage6, "tempImages/special_item6.jpg", R.mipmap.blank_background);
    	//	StaticValueClass.asynImageLoader.showImageAsyn(specialItemImage7, StaticValueClass.serverAddr+"tempImages/special_item7.jpg", R.drawable.blank_background);
        	
        }
        
        private void setupJazziness(TransitionEffect effect) {
    		jazzyViewPager.setTransitionEffect(effect);
    		jazzyViewPager.setAdapter(new MainAdapter( ));
    	//	jazzyViewPager.setPageMargin(1);
    		jazzyViewPager.setPageMargin(0);
    		jazzyViewPager.setCurrentItem(200);
    	//	loopViewPager.setOffscreenPageLimit(movingImageViews.length+2);
    	//	loopViewPager.setCurrentItem(0);
    		
    		
    	}
        private void initMovingImageViews(){
        	int length;
    		movingImageViews=new ImageView[5];
    		indexViews=new TextView[5];
    		for(int i=0;i<5;i++){
    			movingImageViews[i]=new ImageView(ma);
    			movingImageViews[i].setScaleType(ScaleType.FIT_XY);
    			//-----------
    			indexViews[i]=new TextView(ma);
    			length=StaticValueClass.screenWidth/60;
    			LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(length, length);
    			params.bottomMargin=length;
    			length=StaticValueClass.screenWidth/72;
    			params.topMargin=params.leftMargin=params.rightMargin=length/2;
    			indexView.addView(indexViews[i], params);
    		//	indexViews[i].setBackgroundColor(Color.YELLOW);
    			indexViews[i].setBackgroundResource(R.drawable.wround_pink);
    		}
    		StaticValueClass.asynImageLoader.showImageAsyn(movingImageViews[0], "BetterDeal/posterImages/movingview1.jpg", R.mipmap.blank_background);
    		StaticValueClass.asynImageLoader.showImageAsyn(movingImageViews[1], "BetterDeal/posterImages/movingview2.jpg", R.mipmap.blank_background);
    		StaticValueClass.asynImageLoader.showImageAsyn(movingImageViews[2], "BetterDeal/posterImages/movingview3.jpg", R.mipmap.blank_background);
    		StaticValueClass.asynImageLoader.showImageAsyn(movingImageViews[3], "BetterDeal/posterImages/movingview4.jpg", R.mipmap.blank_background);
    		StaticValueClass.asynImageLoader.showImageAsyn(movingImageViews[4], "BetterDeal/posterImages/movingview5.jpg", R.mipmap.blank_background);

    		//--------------------------
    	//	lastIndex=0;
    	//	indexViews[0].setBackgroundColor(Color.RED);
    		indexViews[0].setBackgroundResource(R.drawable.round_pink);
    		animation=AnimationUtils.loadAnimation(ma, R.anim.rotate);
    		animation.setFillAfter(true);
    	}
        
        private class MainAdapter extends PagerAdapter {
        	
       	 int lastIndex=199;
			int currentIndex;
       	
   		@Override
   		public Object instantiateItem(ViewGroup container, final int position) {
			currentIndex=position%movingImageViews.length;
   			container.addView(movingImageViews[currentIndex], LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
   			jazzyViewPager.setObjectForPosition(movingImageViews[currentIndex], position);
			StaticValueClass.asynImageLoader.showImageAsyn(movingImageViews[currentIndex], "BetterDeal/posterImages/movingview"+(currentIndex+1)+".jpg", R.mipmap.blank_background);
   			return movingImageViews[currentIndex];
   		}
   		@Override
   		public int getItemPosition(Object object) {
   			// TODO Auto-generated method stub
   			return super.getItemPosition(object);
   		}
   		@Override
   		public void destroyItem(ViewGroup container, int position, Object obj) {
   		//	Log.d("MainAdapter destroyItem", "position:"+position);
   			container.removeView(movingImageViews[position%movingImageViews.length]);
   		//	container.removeView(loopViewPager.findViewFromObject(position));
   		}
   		@Override
   		public int getCount() {
   			//return 5;
   			return Integer.MAX_VALUE;
   		//	return movingImageViews.length;
   		}
   		@Override
   		public boolean isViewFromObject(View view, Object obj) {
   			if (view instanceof OutlineContainer) {
   				return ((OutlineContainer) view).getChildAt(0) == obj;
   			} else {
   				return view == obj;
   			}
   		}
   		@Override
   		public void setPrimaryItem(ViewGroup container, int position,
   				Object object) {
   			// TODO Auto-generated method stub
   			super.setPrimaryItem(container, position, object);
   			if(position!=lastIndex){
   				Log.d("setPrimaryItem", "position:"+position);
   				
   				indexViews[lastIndex%5].setBackgroundResource(R.drawable.wround_pink);
   				indexViews[lastIndex%5].clearAnimation();
   				
   				indexViews[position%5].setBackgroundResource(R.drawable.round_pink);
   				indexViews[position%5].startAnimation(animation);
   				
   				lastIndex=position;
   			}
   			
   		}		
   	}
    }
 
    class VHItem extends RecyclerView.ViewHolder{
    	 TextView title,price,pre_price,marketTitle;
        ImageView poster,marketMark,couponMark;
        public VHItem(View itemView) {
            super(itemView);
            poster=(ImageView)itemView.findViewById(R.id.posterView);
            marketMark=(ImageView)itemView.findViewById(R.id.markImageView);
            couponMark=(ImageView)itemView.findViewById(R.id.couponMark);
            marketTitle=(TextView)itemView.findViewById(R.id.mallTitle);
            title=(TextView)itemView.findViewById(R.id.item_title);
            price=(TextView)itemView.findViewById(R.id.item_price);
            pre_price=(TextView)itemView.findViewById(R.id.item_reserve_price);
            ViewGroup.LayoutParams params=poster.getLayoutParams();
    		params.height=StaticValueClass.screenWidth*7/15;
    		title.setTypeface(typeface);
    		pre_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
        public void loadData(final Commodity commo){
        	
        	title.setText(commo.title);
        	price.setText(" ¥ "+commo.price);
        	pre_price.setText(" ¥ "+commo.reserve_price);
        	 if(commo.coupon==1){
             	couponMark.setVisibility(View.VISIBLE);
             }else couponMark.setVisibility(View.GONE);
			if (commo.picUrl.equals(""))
        		commo.picUrl="BetterDeal/Cheap/"+commo.picName+".jpg";
		//	title.setText(commo.picUrl);
    		StaticValueClass.asynImageLoader.showImageAsyn(poster, commo.picUrl, R.mipmap.blank_background);
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
					//	StaticValueClass.currentBuyer.addFavoriteCommodity(commo);
					StaticValueClass.currentBuyer.addTraceCommodity(commo);
    	            ma.loadCommodityDetailFragment(commo);
    			}
    			
    		});
        }
    }
    
    class VHItem1 extends RecyclerView.ViewHolder{
        TextView title,price,pre_price,marketTitle;
        ImageView poster,marketMark,couponMark;
        public VHItem1(View itemView) {
            super(itemView);
            marketMark=(ImageView)itemView.findViewById(R.id.markImageView);
            couponMark=(ImageView)itemView.findViewById(R.id.couponMark);
            marketTitle=(TextView)itemView.findViewById(R.id.mallTitle);
            poster=(ImageView)itemView.findViewById(R.id.posterView);
            title=(TextView)itemView.findViewById(R.id.item_title);
            price=(TextView)itemView.findViewById(R.id.item_price);
            pre_price=(TextView)itemView.findViewById(R.id.item_reserve_price);
            ViewGroup.LayoutParams params=poster.getLayoutParams();
    	//	params.height=(StaticValueClass.screenWidth-18)/2;
    		params.height=StaticValueClass.screenWidth*7/15;

    		
    		title.setTypeface(typeface);
        }
        public void loadData(final Commodity commo){
        	
        	title.setText(commo.title);
        	price.setText(" ¥ "+commo.price);
        	pre_price.setText(" ¥ "+commo.reserve_price);
        	if(commo.coupon==1){
             	couponMark.setVisibility(View.VISIBLE);
             }else couponMark.setVisibility(View.GONE);
			if (commo.picUrl.equals(""))
        		commo.picUrl="BetterDeal/Cheap/"+commo.picName+".jpg";
    		StaticValueClass.asynImageLoader.showImageAsyn(poster, commo.picUrl, R.mipmap.blank_background);
		//	title.setText(commo.picUrl);
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
    			//	StaticValueClass.currentBuyer.addFavoriteCommodity(commo);
					StaticValueClass.currentBuyer.addTraceCommodity(commo);
    	            ma.loadCommodityDetailFragment(commo);
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
    
    private void showItemDetailPage(View view ,String itemId){
    	tradeService=AlibabaSDK.getService(TradeService.class);
    	ItemDetailPage itemDetailPage=new ItemDetailPage(itemId,null);
    	TaokeParams taokeParams=new TaokeParams();
    	taokeParams.pid="mm_28xxxx4_4xxxx71_151xxxxx5";
    	tradeService.show(itemDetailPage, null, ma, null, new TradeProcessCallback(){

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
