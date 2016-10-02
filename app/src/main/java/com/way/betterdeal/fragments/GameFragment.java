package com.way.betterdeal.fragments;

//import com.way.miniqq.R;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelAdapter;

//import com.example.helloworld.GridAdapter;
//import com.minggo.game.SlotMachineActivity.SlotMachineAdapter;
import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;
import com.way.betterdeal.adapters.BonusIntroduceAdapter;
import com.way.betterdeal.adapters.GridAdapter;
import com.way.betterdeal.object.GameBonusRecord;
import com.way.betterdeal.object.PanesStartBtn;
import com.way.betterdeal.object.PicUtil;
import com.way.betterdeal.object.PrizeItem;
import com.way.betterdeal.view.NotScrolledListView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:wangjie@cyyun.com
 * Date: 13-6-14
 * Time: 下午2:39
 */
public class GameFragment extends Fragment implements BonusIntroduceAdapter.LoadFinishListener{
	
	MainActivity ma;
	 SoundPool sp;
	 Map<String,String> musicMap;
	 int musicId;
//	private TextView resultTipstv; 
	ScrollView scrollView;
	TextView blackStatusBar;
	ImageView titleImageView;
	Button backBtn,rulesBtn;
	AnimationDrawable nineAnimationDrawable,slotAnimationDrawable;
	WheelView wheelView1,wheelView2,wheelView3;
	// 车轮滚动标志
    private boolean wheelScrolled = false;
    OnWheelScrollListener scrolledListener;
    OnWheelChangedListener changedListener;
    private MediaPlayer slotMediaPlayer,paneMediaPlayer;
    private AssetFileDescriptor musicFileDescriptor1,musicFileDescriptor2;
    GameDialogFragment gameDialogFragment;
    AlertDialog popDialog;
	View gameView;
	
	RelativeLayout slot_machine_game,ninePanesGame;
//	LinearLayout turnPlateView;
	NotScrolledListView bonusDetailListView;
	BonusIntroduceAdapter bonusIntroduceAdapter;//,slotAdapter,paneAdapter;
	ImageButton slotStartBtn,bonusRecordsBtn,switchGameBtn;
	PanesStartBtn ninePaneStartBtn;
	//---------------
	GridView gridView,outGridView;
	GridAdapter gridAdapter,outGridAdapter;
	ArrayList<ImageView> imageViews;
	ArrayList<TextView> textViews,outMarkViews;
    int startNum=0,slop_picWidth=0,slop_picHeight,gameFlag,moves=0;
    Handler mHandler;
    boolean slotStarted=false,ninePanesRunning=false,playGame=false;
    int[] randomRate={1,10,11,15,
    		          16,32,33,49,
    		          50,80,81,85,
    		          86,90,91,100};
    int[] randomRate2={1,10,11,18,
    		           19,20,21,33,
    		           34,100};
   // int[] ninePanes={R.drawable.ninepane0,R.drawable.ninepane1,R.drawable.ninepane2,
    //		R.drawable.ninepane3,R.drawable.ninepane4,R.drawable.ninepane5,
    //		R.drawable.ninepane6,R.drawable.ninepane7};
	
	public GameFragment(){

	//	gameFlag=fg;
		sp=new SoundPool(10,AudioManager.STREAM_MUSIC,5);
		musicMap=new HashMap<String,String>();
	}
	
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        System.out.println("EEEEEEEEEEEE____onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("EEEEEEEEEEEE____onCreate");
    }
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("gameFragment____onCreateView");
        gameView=inflater.inflate(R.layout.game_fragment_layout , container, false);

		ma=(MainActivity) this.getActivity();
        initCommonView(gameView);
        initSlotMachine(gameView);
        initTurnPlateView(gameView);
        initGamePrizesImage();
        Log.d("**GameFragment", "on create view");
        if(StaticValueClass.isAfterKitKat)
        	gameView.setPadding(0, StaticValueClass.statusBarHeight, 0, 0);
        return gameView;
    }
    
    private void initCommonView(View parent){
    	
    	scrollView=(ScrollView)parent.findViewById(R.id.parentScrollView);
    	scrollView.setVerticalScrollBarEnabled(false);
    //	scrollView.setEnabled(false);
    	scrollView.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(slotStarted||ninePanesRunning)
			    	return true;
				else return false;
			}
    		
    	});
    	titleImageView=(ImageView)parent.findViewById(R.id.titleImageView);
    	RelativeLayout.LayoutParams params=(RelativeLayout.LayoutParams)titleImageView.getLayoutParams();
    	params.height=StaticValueClass.screenWidth*273/720;
    	
    	backBtn=(Button)parent.findViewById(R.id.backBtn);
    	rulesBtn=(Button)parent.findViewById(R.id.rulesBtn);
    	Bitmap backmark=BitmapFactory.decodeResource(ma.getResources(), R.mipmap.expand_icon);
    	Drawable leftDrawable=new BitmapDrawable(StaticValueClass.getBackIcon(backmark));
    	leftDrawable.setBounds(0, 0, backmark.getWidth(), backmark.getHeight());
    	backBtn.setCompoundDrawables(leftDrawable, null, null, null);
    	RelativeLayout.LayoutParams bparams1=(RelativeLayout.LayoutParams)backBtn.getLayoutParams();	
    	bparams1.width=StaticValueClass.screenWidth*100/720;
    	bparams1.topMargin=bparams1.leftMargin=StaticValueClass.screenWidth/24;
    	
    	RelativeLayout.LayoutParams bparams2=(RelativeLayout.LayoutParams)rulesBtn.getLayoutParams();	
    	bparams2.width=StaticValueClass.screenWidth*129/720;
    	bparams2.topMargin=bparams2.rightMargin=StaticValueClass.screenWidth/24;
    	bparams1.height=bparams2.height=StaticValueClass.screenWidth*38/720;
    	
    	TextView prizeInturduceText=(TextView)parent.findViewById(R.id.prizeInturduceText);
    	bonusRecordsBtn=(ImageButton)parent.findViewById(R.id.bonusRecordsBtn);
    	LinearLayout subView1=(LinearLayout)parent.findViewById(R.id.subView1);
    	bonusDetailListView=(NotScrolledListView)parent.findViewById(R.id.bonusDetailListView);
    	switchGameBtn=(ImageButton)parent.findViewById(R.id.switchGameBtn);
    	
    	bonusIntroduceAdapter=new BonusIntroduceAdapter(this.getActivity());
    	bonusIntroduceAdapter.setLoadFinishListener(this);
    	bonusDetailListView.setAdapter(bonusIntroduceAdapter);
    	bonusDetailListView.setCacheColorHint(0);
    	
    	backBtn.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		           if(slotStarted||ninePanesRunning){
		        	   return;
		           }
		           ma.onBackPressed();
			}
    		
    	});
    	bonusRecordsBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ma.loadBonusRecordFragment(1);
			}
		});
    	switchGameBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setGameFlag((gameFlag+1)%2);
				switchGame();
			}
		});
    	
    	//concerns params
    	LinearLayout.LayoutParams sParams=(LinearLayout.LayoutParams)subView1.getLayoutParams();
    	sParams.leftMargin=sParams.rightMargin=StaticValueClass.screenWidth/24;
    	
    	LinearLayout.LayoutParams params1=(LinearLayout.LayoutParams)switchGameBtn.getLayoutParams();
    	params1.width=StaticValueClass.screenWidth*364/720;
    	params1.height=StaticValueClass.screenWidth*102/720;
    	
    	RelativeLayout.LayoutParams tParams=(RelativeLayout.LayoutParams)prizeInturduceText.getLayoutParams();
    	tParams.leftMargin=sParams.leftMargin+5;
    	RelativeLayout.LayoutParams params2=(RelativeLayout.LayoutParams)bonusRecordsBtn.getLayoutParams();
    	params2.width=StaticValueClass.screenWidth*181/720;
    	params2.height=StaticValueClass.screenWidth*42/720;
    	params2.rightMargin=sParams.rightMargin;
    	blackStatusBar=(TextView)parent.findViewById(R.id.blackStatusBar);
    	/*
    	if(StaticValueClass.isAfterKitKat){
    		LinearLayout.LayoutParams barParams=(LinearLayout.LayoutParams)blackStatusBar.getLayoutParams();
    		barParams.height=StaticValueClass.statusBarHeight;
    		//android.R.drawable.status_bar_item_background
    	//	Drawable bg=ma.getResources().getDrawable(android.R.attr.statusBarColor);
    	//	Color bColor=ma.getResources().getColor(android.R.attr.statusBarColor);
    	//	blackStatusBar.setBackground(ma.getTheme().getDrawable(android.R.attr.statusBarColor));
    	//	blackStatusBar.setBackgroundColor(ma.getResources().getColor(android.R.attr.statusBarColor));
    	    /*
    		TypedArray array=ma.getTheme().obtainStyledAttributes(new int[]{android.R.attr.statusBarColor,android.R.attr.colorBackground});
    		int statusColor=array.getColor(0, 0xffffff);
    		array.recycle();
    		blackStatusBar.setBackgroundColor(statusColor); 
    		 
    	} */
    	rulesBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 if(slotStarted||ninePanesRunning){
		        	   return;
		           }
				if(gameFlag==0)
					gameDialogFragment.showGameDialog(2);
				else if(gameFlag==1)
					gameDialogFragment.showGameDialog(1);
			}
		});
    	
    	this.initPopDialog();
    //	intPrizeItems();
    	//initBackgroundMusic();
    }
    
    private void initSlotMachine(View parent){
    	slot_machine_game=(RelativeLayout)gameView.findViewById(R.id.slot_machine_game);
    	
    	slop_picWidth=StaticValueClass.screenWidth*10/72;
    	slop_picHeight=StaticValueClass.screenWidth/6;
    
        //车轮滚动的监听器
        scrolledListener = new OnWheelScrollListener() {
        	public int wheelNum=0;
              @Override
			public void onScrollingStarted(WheelView wheel) {
                  wheelScrolled = true;
                  wheelNum++;
              }
              @Override
			public void onScrollingFinished(WheelView wheel) {
              	wheelScrolled = false;
           //   	System.out.println("轮子---->"+wheel.getCurrentItem());
              	playGameLose(R.raw.slot_end_music);
              	if(--wheelNum==0)
                     updateStatus();
              }
          };
          
          // 车轮item改变的监听器
          changedListener = new OnWheelChangedListener() {
              @Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
                  if (!wheelScrolled) {
                  //	System.out.println("轮子item---->"+wheel.getCurrentItem());
                   //   updateStatus();
                  }
              }
          };
     //  setSlotMachineFrame(gameView);
        wheelView1=(WheelView)gameView.findViewById(R.id.slot_1);
        wheelView2=(WheelView)gameView.findViewById(R.id.slot_2);
        wheelView3=(WheelView)gameView.findViewById(R.id.slot_3);
        initWheel(wheelView1);
        initWheel(wheelView2);
        initWheel(wheelView3);
  //      resultTipstv = (TextView) gameView.findViewById(R.id.pwd_status);
        
        RelativeLayout.LayoutParams vParams1=(RelativeLayout.LayoutParams)wheelView1.getLayoutParams();
        vParams1.height=StaticValueClass.screenWidth/6;
        vParams1.width=StaticValueClass.screenWidth*10/72;
        vParams1.topMargin=StaticValueClass.screenWidth*307/720;
        vParams1.leftMargin=StaticValueClass.screenWidth*146/720;
        
        RelativeLayout.LayoutParams vParams2=(RelativeLayout.LayoutParams)wheelView2.getLayoutParams();
        vParams2.height=StaticValueClass.screenWidth/6;
        vParams2.width=StaticValueClass.screenWidth*10/72;
        vParams2.topMargin=StaticValueClass.screenWidth*307/720;
        
        RelativeLayout.LayoutParams vParams3=(RelativeLayout.LayoutParams)wheelView3.getLayoutParams();
        vParams3.height=StaticValueClass.screenWidth/6;
        vParams3.width=StaticValueClass.screenWidth*10/72;
        vParams3.topMargin=StaticValueClass.screenWidth*307/720;
        vParams3.rightMargin=StaticValueClass.screenWidth*146/720;
        
    	slotStartBtn = (ImageButton)gameView.findViewById(R.id.slotStartBtn);
    	
    	RelativeLayout.LayoutParams ssParams=(RelativeLayout.LayoutParams)slotStartBtn.getLayoutParams();
    	ssParams.width=StaticValueClass.screenWidth*209/720;
    	ssParams.height=StaticValueClass.screenWidth*76/720;
    	
        slotStartBtn.setOnClickListener(new OnClickListener() {
            @Override
			public void onClick(View v) {
            	if(slotStarted) return;
            	scrollToTop();
            	int result=StaticValueClass.currentBuyer.checkGamePermission();
            	if(result==0){
            	//	ma.showShareDialog("已玩三次，分享可再玩一次");
            		gameDialogFragment.showGameDialog(6);
            		return;
            	}else if(result==-1){
            		gameDialogFragment.showGameDialog(3);
            		playGameLose(R.raw.game_runout);
            		return;
            	}
            	playTigerMachine();
            //	ma.updateBuyerInfo();
            	playGame=true;
            }
        });
    	
    	
    }
    
    private void initTurnPlateView(View parent){
    	
    	ninePanesGame=(RelativeLayout)gameView.findViewById(R.id.ninePanesGame);
    	
    	gridView=(GridView)gameView.findViewById(R.id.inGridView);
   // 	gridView.setBackgroundColor(Color.GREEN);
    	gridView.setHorizontalSpacing(StaticValueClass.screenWidth/60);
    	gridView.setVerticalSpacing(StaticValueClass.screenWidth/60);
    	RelativeLayout.LayoutParams gParams=(RelativeLayout.LayoutParams)gridView.getLayoutParams();
    	
    	int margin=StaticValueClass.screenWidth/15;
    //	gParams.leftMargin=gParams.rightMargin=20;
    	gParams.height=gParams.width=StaticValueClass.screenWidth*57/72;
   // 	int edgeLength=(StaticValueClass.screenWidth-40-2*gridView.getHorizontalSpacing())/3;
    	ninePaneStartBtn=new PanesStartBtn(ma);
    //	initNinePanesImage();
		

    	ninePaneStartBtn.setOnClickListener(new View.OnClickListener() {
			
		//	@Override
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			//	gameDialogFragment.showGameDialog();
				scrollToTop();
				Log.d("***nine panes", "step1"+ninePanesRunning);
				if(ninePanesRunning) return;
				
				if(StaticValueClass.currentBuyer.checkNinePanePermission()){
					
					ma.updateBuyerInfo();
					playGame=true;
				}else{
					gameDialogFragment.showGameDialog(3);
					playGameLose(R.raw.game_runout);
					return;
				}
				ninePanesRunning=true;
				Log.d("***nine panes", "step2  start "+ninePanesRunning);
				new Thread(new Runnable(){
					int randomNum;
					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						int i,trueSteps,position=0;
						randomNum=(int)((Math.random()*10)*10+(Math.random()*10+1));
						position=getRandomPosition(randomNum);
						
						Log.d("***nine panes", "step3  postion "+position);
					//	if(position!=0){
							trueSteps=position+24-startNum;
					//	} else return;
					//	Toast.makeText(ma, "position:"+position,Toast.LENGTH_SHORT).show();
							
						moves=trueSteps+48;
						Looper.prepare();
						for(i=0;i<8;i++){
								try {
									Thread.sleep(300);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								movePane();
						}
						for(i=0;i<16;i++){
								try {
									Thread.sleep(150);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								movePane();
							
						}
						for(i=0;i<trueSteps;i++){
								try {
									Thread.sleep(70);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								movePane();
							
							
						}
						for(i=0;i<16;i++){
							
								try {
									Thread.sleep(150);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								movePane();
						}
						for(i=0;i<8;i++){
							
								try {
									Thread.sleep(300);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								movePane();
							
						}
						
					}
					
				}).start();
				
				
				
			}
		});
		
		
		mHandler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
	            switch (msg.what) {
	            case 1:
	           // 	imageViews.get(startNum).setImageResource(R.drawable.wait_rotate);
	            	
	            	startNum=(startNum+1)%9;
					imageViews.get(startNum).setImageResource(0);
					break;
	            case 2:
	            	/*
	            	ma.runOnUiThread(new Runnable(){

						@Override
						public void run() {
							// TODO Auto-generated method stub
							textViews.get(startNum).setBackgroundColor(Color.YELLOW);
			            	startNum=(startNum+1)%8;
			            	textViews.get(startNum).setBackgroundColor(Color.RED);
						}
	            		
	            	}); */
	            	Log.d("***nine panes", "startNum:"+startNum);
	            	((ImageView)imageViews.get(startNum)).setImageBitmap(StaticValueClass.darkNinePanesImage.get(startNum));
    		    	startNum=(startNum+1)%8;
    		    	((ImageView)imageViews.get(startNum)).setImageBitmap(StaticValueClass.ninePanesImage.get(startNum));
    		    	if(--moves==0){
    		    		ninePanesRunning=false;
    					gameDialogFragment.showGameDialog(5);
    					playGameLose(R.raw.game_lose);
    		    	}
    		    	gridAdapter.notifyDataSetChanged();
	            	
	            	break;
	            case 3:
	            //	gameDialogFragment.showGameDialog();
	            	break;
	            }
			}
			
		};
    	 
    	
    }
    private void scrollToTop(){
    	if(scrollView.getScrollY()>=5)
    		scrollView.fullScroll(View.FOCUS_UP);
    }
    private Runnable paneMoveRunnable=new Runnable(){
        private Object lock=new Object();
		@Override
		public void run() {
			// TODO Auto-generated method stub
			synchronized(lock){
				imageViews.get(startNum).setImageBitmap(StaticValueClass.darkNinePanesImage.get(startNum));
		    	startNum=(startNum+1)%8;
		    	imageViews.get(startNum).setImageBitmap(StaticValueClass.ninePanesImage.get(startNum));
		    	if(--moves==0){
		    		ninePanesRunning=false;
					gameDialogFragment.showGameDialog(5);
					playGameLose(R.raw.game_lose);
		    	}
			}
		}
    	
    };
    
    
    private void getGamePrize(){
    	//ninePanes prize
    	StaticValueClass.ninePanesPrizes.clear();
    	StaticValueClass.ninePanesPrizes.add(new GameBonusRecord("tempImages/game_prize1.png","多彩趣味花颜墙贴强力无痕挂钩",25.00f,"快递送达",1,1));
    	StaticValueClass.ninePanesPrizes.add(new GameBonusRecord("tempImages/game_prize2.png","多彩趣味花颜墙贴强力无痕挂钩",25.00f,"快递送达",5,1));
    	StaticValueClass.ninePanesPrizes.add(new GameBonusRecord("tempImages/game_prize3.png","多彩趣味花颜墙贴强力无痕挂钩",25.00f,"快递送达",6,1));
    	StaticValueClass.ninePanesPrizes.add(new GameBonusRecord("tempImages/game_prize4.png","多彩趣味花颜墙贴强力无痕挂钩",25.00f,"快递送达",5,1));
    	StaticValueClass.ninePanesPrizes.add(new GameBonusRecord("tempImages/game_prize5.png","多彩趣味花颜墙贴强力无痕挂钩",25.00f,"快递送达",3,1));
    	StaticValueClass.ninePanesPrizes.add(new GameBonusRecord("tempImages/game_prize6.png","多彩趣味花颜墙贴强力无痕挂钩",25.00f,"快递送达",6,1));
    	StaticValueClass.ninePanesPrizes.add(new GameBonusRecord("tempImages/game_prize7.png","多彩趣味花颜墙贴强力无痕挂钩",25.00f,"快递送达",7,1));
    	//slot prize
    	StaticValueClass.slotPrizes.clear();
    	StaticValueClass.slotPrizes.add(new GameBonusRecord("tempImages/game_prize1.png","多彩趣味花颜墙贴强力无痕挂钩",25.00f,"快递送达",3,0));
    	StaticValueClass.slotPrizes.add(new GameBonusRecord("tempImages/game_prize2.png","多彩趣味花颜墙贴强力无痕挂钩",25.00f,"快递送达",1,0));
    	StaticValueClass.slotPrizes.add(new GameBonusRecord("tempImages/game_prize3.png","多彩趣味花颜墙贴强力无痕挂钩",25.00f,"快递送达",5,0));
    	StaticValueClass.slotPrizes.add(new GameBonusRecord("tempImages/game_prize4.png","多彩趣味花颜墙贴强力无痕挂钩",25.00f,"快递送达",7,0));
    	StaticValueClass.slotPrizes.add(new GameBonusRecord("tempImages/game_prize5.png","多彩趣味花颜墙贴强力无痕挂钩",25.00f,"快递送达",2,0));
       
    }
    
    private void initGamePrizesImage(){
    	
    	getGamePrize();
    	
    	new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				if(StaticValueClass.ninePanesImage.size()<8){
					String url=StaticValueClass.serverAddress+"tempImages/game_prize",imgUrl;
					for(int i=1;i<=8;i++){
						imgUrl=url+i+".png";
						Bitmap bitmap=PicUtil.getbitmap(imgUrl);
						StaticValueClass.ninePanesImage.add(bitmap);
			    		Bitmap darkBit=StaticValueClass.getDarkImage(bitmap);
			    		StaticValueClass.darkNinePanesImage.add(darkBit);
					} 
				} 
				
				
				
				//
				ma.runOnUiThread(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						imageViews=new ArrayList<ImageView>();
						
						for(int i=0;i<8;i++){
							ImageView imageView=new ImageView(ma);
							GridView.LayoutParams params=new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT,GridView.LayoutParams.MATCH_PARENT);
							
							imageView.setLayoutParams(params);
						//	imageView.setImageResource(R.drawable.wait_rotate);
							imageView.setImageBitmap(StaticValueClass.darkNinePanesImage.get(i));
							imageViews.add(imageView);
						} 
						
						ninePaneStartBtn.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT,GridView.LayoutParams.MATCH_PARENT));
						gridAdapter=new GridAdapter(imageViews,ninePaneStartBtn,3,0);
						gridView.setAdapter(gridAdapter);
						ninePaneStartBtn.setBackgroundResource(R.drawable.panes_start_btn);
						imageViews.get(startNum).setImageBitmap(StaticValueClass.ninePanesImage.get(startNum));
					}
					
				});

				
			}
    		
    	}).start(); 
    }

    public boolean gameIsRunning(){
    	return (slotStarted||ninePanesRunning);
    }
    
    public void setGameFlag(int f){
    	gameFlag=f;
    }
    
    public void switchGame(){
    //	gameFlag=flag;
    	if(gameFlag==0){
    		if(slotStarted) return;
    		ninePanesGame.setVisibility(View.VISIBLE);
    		slot_machine_game.setVisibility(View.INVISIBLE);
    		this.titleImageView.setImageResource(R.mipmap.ninepanes_title);
    		this.switchGameBtn.setBackgroundResource(R.mipmap.switch_slot_machine);
    	//	bonusDetailListView.setAdapter(paneAdapter);
    		bonusIntroduceAdapter.setDataSource(StaticValueClass.ninePanesPrizes);
    		try {
    			if(slotMediaPlayer!=null){
    				slotMediaPlayer.pause();
    			}
    			//slotMediaPlayer.reset();
    		//	paneMediaPlayer.setDataSource(musicFileDescriptor1.getFileDescriptor(),musicFileDescriptor1.getStartOffset(),musicFileDescriptor1.getLength());
    		//	paneMediaPlayer.setLooping(true);
    		//	paneMediaPlayer.prepare();
    		//	paneMediaPlayer.start();
				
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
    		
    	}else{
    		if(ninePanesRunning) return;
    		ninePanesGame.setVisibility(View.INVISIBLE);
    		slot_machine_game.setVisibility(View.VISIBLE);
    		titleImageView.setImageResource(R.mipmap.slot_title);
    		this.switchGameBtn.setBackgroundResource(R.mipmap.switch_nine_panes);
    		
    	//	bonusDetailListView.setAdapter(slotAdapter);
    		bonusIntroduceAdapter.setDataSource(StaticValueClass.slotPrizes);
    		try {    			
    		//	playSlotMachineMusic();
    			
			} catch (IllegalStateException  e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
    	}
    	bonusIntroduceAdapter.notifyDataSetChanged();
    	Handler handler=new Handler();
    	handler.postDelayed(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				scrollView.fullScroll(View.FOCUS_UP);
			}
    		
    	}, 200);
    }
    
    private void intPrizeItems(){
    	/*
    	slotPrizeItems=new ArrayList<PrizeItem>();
    	panePrizeItems=new ArrayList<PrizeItem>();
    	for(int i=0;i<5;i++){
    		slotPrizeItems.add(new PrizeItem(0));
    	}
    	for(int i=0;i<8;i++){
    		panePrizeItems.add(new PrizeItem(1));
    	} */
    //	slotAdapter.setDataSource(StaticValueClass.slotPrizes);
    //	slotAdapter.notifyDataSetChanged();
    //	paneAdapter.setDataSource(StaticValueClass.ninePanesPrizes);
    //	paneAdapter.notifyDataSetChanged();
    }
    
    
    private void playTigerMachine(){
    	//resultTipstv.setText("期待哦。。。");
    	playGameLose(R.raw.slot_start_music);
    	slotStarted=true;
    	int  item1=wheelView1.getCurrentItem()%5,item2=wheelView2.getCurrentItem()%5,item3=wheelView3.getCurrentItem()%5;
    	int randomNum,position=5,count1=10,count2=15,count3=20,num1,num2;
    	num1=(int)(Math.random()*10);
    	num2=(int)(Math.random()*10);
		randomNum=(num1)*10+(num2+1);
		
		if(randomNum%21==0){
			position=randomNum/21;
			//if(wheelView1==null) Log.d("*&8&**", "wheelView1==null");
			count1=position-item1+10+5*(num1+1);
			count2=position-item2+10+5*(num2+1);
			count3=position-item3+10+5*(num1+1);
			
		}else{
			count1=randomNum;
			count2=(num2)*10+(num1+1);
			count3=count1;
			if((item1-item2)%5==0)
				count3++;
		}
		
		while(count3>55){
			count3-=5;
		}
		while(count3<55){
			count3+=5;
		}
		while(count2>45){
			count2-=5;
		}
		while(count2<45){
			count2+=5;
		}
		while(count1>35){
			count1-=5;
		}
		while(count1<35){
			count1+=5;
		}
		Toast.makeText(ma, position+":"+count1+":"+count2+":"+count3, Toast.LENGTH_SHORT).show();
		
    //   mixWheel(R.id.slot_1,count1,3000);
		wheelView1.scroll(count1, 7500);
     //   mixWheel(R.id.slot_2,count2,5000);
		wheelView2.scroll(count2, 9500);
      //  mixWheel(R.id.slot_3,count3,7000);
		wheelView3.scroll(count3, 11500);
        ma.updateBuyerInfo();
    }
   
  //  private  Object lock=new Object();
    private  Runnable paneRun=new Runnable(){
  //   private  Object lock=new Object();
    	private int count=1;
		@Override
		public void run() {
			// TODO Auto-generated method stub
			synchronized(this){
				count--;
				if(count<0){
					try {
						Log.d("***ninePane", " enter  wait count: "+count);
						this.wait();
						Log.d("***ninePane", " enter  pass ");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
				ma.runOnUiThread(new Runnable(){

	    			@Override
	    			public void run() {
	    				// TODO Auto-generated method stub
	    				((ImageView)imageViews.get(startNum)).setImageBitmap(StaticValueClass.darkNinePanesImage.get(startNum));
	    		    	startNum=(startNum+1)%8;
	    		    	((ImageView)imageViews.get(startNum)).setImageBitmap(StaticValueClass.ninePanesImage.get(startNum));
	    		    	if(--moves==0){
	    		    		ninePanesRunning=false;
	    		    		if(startNum==7){
	    		    			gameDialogFragment.showGameDialog(5);
		    					playGameLose(R.raw.game_lose);
	    		    		}else {
	    		    			 playGameLose(R.raw.game_win);
	    		                 GameBonusRecord record=new GameBonusRecord(""+System.currentTimeMillis(),"多彩趣味花颜墙贴强力无痕挂钩",35.00f
	    		                 		,"1.减轻了增大腹部压迫感。孕妇枕使胳膊和腿部都有了支撑，填充了腹部与床面的空隙。\n"+
	    		                          "2.支撑孕妇脆弱的腰部。可随孕妇不同时期，不同腰围，枕距随意调节，更加贴合孕妇腰身，不各腰。\n"+
	    		                          "3.满足孕妇垫高头部，垫腰，抬腿的需求，可以让四肢舒服放松。",
	    		                          "1.中奖用户需48小时之内填写正确的收货地址以及联系方式，如因特殊情况未填写地址，48小时内可自行添加，过期将视为自动放弃。\n"+
	    		                         "2.福利奖品将在中奖后5-7个工作日内由快递送货上门，请耐心等待并保持电话畅通。\n"+
	    		                 		"3.该活动仅限北京用户参与。",0);
	    		                 ma.addNewGameBonusRecord(record,true);
	    		                 gameDialogFragment.showGameDialog(4,record);
	    		    		}
	    					
	    		    	}
	    			}
	        		
	        	}); 
				
				synchronized(this){
					
				count++;
				Log.d("***ninePane", " enter  notifiy ");
				this.notify();
			}
		}
    	
    };
    
    
    private void movePane(){
    	Message msg=new Message();
    	msg.what=2;
    	mHandler.sendMessage(msg);
    	/*
        	ma.runOnUiThread(new Runnable(){

    			@Override
    			public void run() {
    				// TODO Auto-generated method stub
    				((ImageView)imageViews.get(startNum)).setImageBitmap(StaticValueClass.darkNinePanesImage.get(startNum));
    		    	startNum=(startNum+1)%8;
    		    	((ImageView)imageViews.get(startNum)).setImageBitmap(StaticValueClass.ninePanesImage.get(startNum));
    		    	if(--moves==0){
    		    		ninePanesRunning=false;
    					gameDialogFragment.showGameDialog(5);
    					playGameLose(R.raw.game_lose);
    		    	}
    			}
        		
        	}); 
    	
      */
    }
    
     private int getRandomPosition(int rNum){
    	 if(rNum>100 || rNum<1){
    		 return 0;
    	 }
    	 for(int i=0;i<randomRate.length;i+=2){
    		 if( rNum<randomRate[i]){
    			 return (i-2)/2;
    		 }
    	 }
    	    return 0;	 
    	 
     }
    
    
    private void setSlotMachineFrame(View parent){
    	int scaleLength;
    	
    }
    
    /**
     * 更新状态
     */
    private void updateStatus() {
    	slotStarted=false;
        if (!test()) {
        //	resultTipstv.setText("运气不错哦！");
        	 playGameLose(R.raw.game_win);
             GameBonusRecord record=new GameBonusRecord(""+System.currentTimeMillis(),"多彩趣味花颜墙贴强力无痕挂钩",35.00f
             		,"1.减轻了增大腹部压迫感。孕妇枕使胳膊和腿部都有了支撑，填充了腹部与床面的空隙。\n"+
                      "2.支撑孕妇脆弱的腰部。可随孕妇不同时期，不同腰围，枕距随意调节，更加贴合孕妇腰身，不各腰。\n"+
                      "3.满足孕妇垫高头部，垫腰，抬腿的需求，可以让四肢舒服放松。",
                      "1.中奖用户需48小时之内填写正确的收货地址以及联系方式，如因特殊情况未填写地址，48小时内可自行添加，过期将视为自动放弃。\n"+
                     "2.福利奖品将在中奖后5-7个工作日内由快递送货上门，请耐心等待并保持电话畅通。\n"+
             		"3.该活动仅限北京用户参与。",0);
             ma.addNewGameBonusRecord(record,true);
             gameDialogFragment.showGameDialog(4,record);
        } else {
          //
        	playGameLose(R.raw.game_lose);
        	gameDialogFragment.showGameDialog(5);
        }
     //   popDialog.show();
       
    }

    /**
     * 初始化轮子
     * @param wheel the wheel widget Id
     */
    private void initWheel(WheelView wheel) {
     //   wheel = getWheel(id);
        wheel.setViewAdapter(new SlotMachineAdapter(ma));
     //   wheel.setCurrentItem((int)(Math.random() * 10));
        wheel.setCurrentItem(0);
        wheel.addChangingListener(changedListener);
        wheel.addScrollingListener(scrolledListener);
        wheel.setCyclic(true);
        wheel.setEnabled(false);
        Log.d("&*&*&*initWheel", "current item:"+wheel.getCurrentItem());
    }
    
    /**
     * 根据id获取轮子
     * @param id the wheel Id
     * @return the wheel with passed Id
     */
    private WheelView getWheel(int id) {
        return (WheelView) gameView.findViewById(id);
    }
    
    /**
     * 测试轮子转动位置
     * @return true 
     */
    private boolean test() {
        int value = getWheel(R.id.slot_1).getCurrentItem();
        return testWheelValue(R.id.slot_2, value) && testWheelValue(R.id.slot_3, value);
    }
    
    /**
     * 根据轮子id获取当前item值
     * @param id the wheel Id
     * @param value the value to test
     * @return true if wheel value is equal to passed value
     */
    private boolean testWheelValue(int id, int value) {
        return getWheel(id).getCurrentItem() == value;
    }
    
    /**
     * 转动轮子
     * @param id the wheel id
     */
    private void mixWheel(int id,int round,int time) {
        WheelView wheel = getWheel(id);
    //    wheel.scroll(round, time);
        wheel.scroll((int)(Math.random() * 50)+round, time);
        //wheel.scroll(-350 + (int)(Math.random() * 50), 2000);
    }
    
    /**
     * 老虎机适配器
     */
    private class SlotMachineAdapter extends AbstractWheelAdapter {
        // 图片的大小
        final int IMAGE_WIDTH = slop_picWidth;
        final int IMAGE_HEIGHT = slop_picHeight;
        
        // 图片的数组
        private final int items[] = new int[] {
        		R.mipmap.fruit1,
        		R.mipmap.fruit2,
        		R.mipmap.fruit3,
        		R.mipmap.fruit4,
        		R.mipmap.fruit5,
        };
        
        // 对图片的缓存
        private List<SoftReference<Bitmap>> images;
        
        // 布局膨胀器
        private Context context;
        
        /**
         * 构造函数
         */
        public SlotMachineAdapter(Context context) {
            this.context = context;
            images = new ArrayList<SoftReference<Bitmap>>(items.length);
            for (int id : items) {
                images.add(new SoftReference<Bitmap>(loadImage(id)));
            }
         //   IMAGE_WIDTH=IMAGE_HEIGHT=slop_picWidth;
        }
        
        /**
         * 从资源加载图片
         */
        private Bitmap loadImage(int id) {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id);
            Bitmap scaled = Bitmap.createScaledBitmap(bitmap, IMAGE_WIDTH, IMAGE_HEIGHT, true);
            bitmap.recycle();
            return scaled;
        }

     
        //@Override
        @Override
		public int getItemsCount() {
            return items.length;
        }

        // 设置图片布局的参数
     //   final ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(IMAGE_WIDTH, IMAGE_HEIGHT);
       
        
        
   //     @Override
        @Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
            ImageView img;
            if (cachedView != null) {
                img = (ImageView) cachedView;
            } else {
                img = new ImageView(context);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(IMAGE_HEIGHT, IMAGE_HEIGHT);
       //     params.gravity=Gravity.CENTER;
          //  params.leftMargin=params.rightMargin=5;
        //    params.bottomMargin=10;
            params.gravity=Gravity.CENTER_HORIZONTAL;
            img.setLayoutParams(params);
            img.setBackgroundColor(Color.WHITE);
          //  img.setScaleType(ScaleType.FIT_XY);
            SoftReference<Bitmap> bitmapRef = images.get(index);
            Bitmap bitmap = bitmapRef.get();
            if (bitmap == null) {
                bitmap = loadImage(items[index]);
                images.set(index, new SoftReference<Bitmap>(bitmap));
            }
            img.setImageBitmap(bitmap);
            
            return img;
        }
    }


    

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("EEEEEEEEEEEE____onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
      //  playMusic();
     //   ma.playGameBackgroundMusic();
    //    System.out.println("EEEEEEEEEEEE____onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        switchGame();
        /*
    	if(StaticValueClass.isAfterKitKat){
    		ma.raiseFrame(true);
    		LinearLayout.LayoutParams barParams=(LinearLayout.LayoutParams)blackStatusBar.getLayoutParams();
    		barParams.height=StaticValueClass.statusBarHeight;
    	
    		 
    	} */
        System.out.println("GameFragment____onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        
    //    System.out.println("EEEEEEEEEEEE____onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        if(slotMediaPlayer!=null){
        	  this.slotMediaPlayer.release();
              slotMediaPlayer=null;
        }
        if(playGame){
        	playGame=false;
      //  	ma.updateBuyerInfo();
        }
      
        System.out.println("gameFragment ____onStop");
    }

    @Override
    public void onDestroyView() {
    	ma.raiseFrame(false);
        super.onDestroyView();
        System.out.println("EEEEEEEEEEEE____onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
     //   slotMediaPlayer.stop();
     //   slotMediaPlayer.release();
    //    slotMediaPlayer.reset();
    //    paneMediaPlayer.stop();
     //   paneMediaPlayer.release();
     //   paneMediaPlayer.reset();
        System.out.println("EEEEEEEEEEEE____onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        System.out.println("EEEEEEEEEEEE____onDetach");
    }
    
    private void initPopDialog(){
    	popDialog=new AlertDialog.Builder(ma).create();
    	popDialog.setTitle("dialog_animation");
    	popDialog.setMessage("test");
		Window window=popDialog.getWindow();
		window.setGravity(Gravity.CENTER);
		window.setWindowAnimations(R.style.gameDialogStyle);
	//	dialog.show();
		gameDialogFragment=new GameDialogFragment();
    }
    
    private void initBackgroundMusic(){
    	  if(slotMediaPlayer!=null && paneMediaPlayer!=null ) return;
    	try{
    		musicFileDescriptor1 = ma.getAssets().openFd("music/game_music2.mp3");
    		paneMediaPlayer = new MediaPlayer();
    		/*
    		paneMediaPlayer.setOnCompletionListener(new OnCompletionListener(){

				@Override
				public void onCompletion(MediaPlayer mp) {
					// TODO Auto-generated method stub
					paneMediaPlayer.start();
				}
				
			}); */
    	//	paneMediaPlayer.setDataSource(fileDescriptor1.getFileDescriptor(),fileDescriptor1.getStartOffset(),fileDescriptor1.getLength());
    		//paneMediaPlayer.prepareAsync();
    	//	paneMediaPlayer.prepare();
    	//	paneMediaPlayer.setLooping(true); 
    	//	file:///android_asset/文件名
    		musicFileDescriptor2 = ma.getAssets().openFd("music/game_background_music.mp3");
    		slotMediaPlayer = new MediaPlayer();
    		/*
    		slotMediaPlayer.setOnCompletionListener(new OnCompletionListener(){

				@Override
				public void onCompletion(MediaPlayer mp) {
					// TODO Auto-generated method stub
					slotMediaPlayer.start();
				}
				
			}); */
    	//	slotMediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),fileDescriptor.getStartOffset(),fileDescriptor.getLength());
    	//	slotMediaPlayer.prepareAsync();
    	//	slotMediaPlayer.prepare();
    	//	slotMediaPlayer.setLooping(true);
    		
    		
    		
    	}catch(Exception e){
    		
    	}
    }

     private void playSlotMachineMusic(){
		
		try{
			
			if(slotMediaPlayer==null){
				AssetFileDescriptor fileDescriptor = ma.getAssets().openFd("music/game_background_music.mp3");
				slotMediaPlayer = new MediaPlayer();
				slotMediaPlayer.setVolume(0.382f, 0.382f);
				slotMediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),fileDescriptor.getStartOffset(),fileDescriptor.getLength());
				slotMediaPlayer.prepare();
				slotMediaPlayer.setLooping(true);
				slotMediaPlayer.start();
			}else{
				slotMediaPlayer.start();
			}
	
		} catch(Exception e){
			e.printStackTrace();
		}

	}
     
     
     private void playGameLose(int rawId){
    	
    	 /*
    	 if(musicMap.containsKey(""+rawId)){
    		 musicId=Integer.parseInt(musicMap.get(""+rawId));
    	 }else {
    		 musicId= sp.load(ma, rawId, 1);
    		 musicMap.put(""+rawId, ""+musicId);
    	 } */
    	
    	 musicId= sp.load(ma, rawId, 1);
    	 sp.setOnLoadCompleteListener(new OnLoadCompleteListener(){

			@Override
			public void onLoadComplete(SoundPool soundPool, int sampleId,
					int status) {
				// TODO Auto-generated method stub
				sp.play(musicId, 0.01f, 0.01f, 0, 0, 1);
			}
    		 
    	 });
    	 
    	 
     }

	@Override
	public void finishLoad() {
		// TODO Auto-generated method stub
		Handler handler=new Handler();
    	handler.postDelayed(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				scrollView.fullScroll(View.FOCUS_UP);
			}
    		
    	}, 100);
	}




}
