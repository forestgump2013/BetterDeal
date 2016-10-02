package com.way.betterdeal.object;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.ref.SoftReference;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.way.betterdeal.StaticValueClass;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;


public class AsynImageLoader {
	private static final String TAG = "AsynImageLoader";
	Context context;
	// 缓存下载过的图片的Map
//	private Map<String, SoftReference<Bitmap>> caches;
	private LruCache<String,Bitmap> mMemoryCache;
	private static  ExecutorService DEFAULT_TASK_EXECUTOR;
	// 任务队列
	private List<Task> taskQueue;
	private boolean isRunning = false;
	private int imageWidth,imageHeight;
	private Uri imageUri;
	String imagePath;
	
	public AsynImageLoader(Context cc){
		context=cc;
		// 初始化变量
	//	caches = new HashMap<String, SoftReference<Bitmap>>();
		taskQueue = new ArrayList<AsynImageLoader.Task>();
		
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		// Use 1/8th of the available memory for this memory cache. 
		final int cacheSize = maxMemory / 8;
		mMemoryCache = new LruCache<String, Bitmap>(cacheSize) { 
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
			// The cache size will be measured in kilobytes rather than // number of items.
			return bitmap.getByteCount() / 1024;
			} };
			
		// 启动图片下载线程
		isRunning = true;	
		new Thread(runnable).start();
		DEFAULT_TASK_EXECUTOR=Executors.newCachedThreadPool();
	}
	
	/**
	 * 
	 * @param imageView 需要延迟加载图片的对象
	 * @param url 图片的URL地址
	 * @param resId 图片加载过程中显示的图片资源
	 */
	public void showImageAsyn(ImageView imageView, String url, int resId){
		if(imageView!=null)
			imageView.setTag(url);
		Bitmap bitmap = loadImageAsyn(url,0,0, getImageCallback(imageView, resId));
		if(imageView==null) return;
		if(bitmap == null){
		//	imageView.setImageResource(resId);
		}else{
			imageView.setImageBitmap(bitmap);
		}
	}

	
	
	public Bitmap loadImageAsyn(String path,int needWidth,int needHeight, ImageCallback callback){
		// 判断缓存中是否已经存在该图片
		
		Bitmap bitmap=mMemoryCache.get(path);
		if(bitmap!=null){
			return bitmap;
		}
		Task task = new Task();
		task.path = path;
		task.needHeight=needHeight;
		task.needWidth=needWidth; 
		task.callback = callback;
		task.bitmap=null;
		Log.i(TAG, "new Task ," + path);
		taskQueue.add(task);
			// 唤醒任务下载队列
			synchronized (runnable) {
				runnable.notify();
			}
		return null;
	}
	
	/**
	 * 
	 * @param imageView 
	 * @param resId 图片加载完成前显示的图片资源ID
	 * @return
	 */
	private ImageCallback getImageCallback(final ImageView imageView, final int resId){
		return new ImageCallback() {
			
		//	@Override
			@Override
			public void loadImage(String path, Bitmap bitmap) {
				if(imageView ==null ){
					//just download bitmap.
					return ;
				}
				
				if(path.equals(imageView.getTag().toString())){
					imageView.setImageBitmap(bitmap);
				}else{
				//	imageView.setImageResource(resId);
				}
			}

		};
	}
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// 子线程中返回的下载完成的任务
			Task task = (Task)msg.obj;
			// 调用callback对象的loadImage方法，并将图片路径和图片回传给adapter
			task.callback.loadImage(task.path, task.bitmap);
			
			if(!task.cardPath.equals("")){
				PicUtil.saveBitmapToDisk(task.bitmap, task.cardPath);
			} 
		}
		
	};
	
	private Runnable runnable = new Runnable() {
		
	//	@Override
		@Override
		public void run() {
			while(isRunning){
				// 当队列中还有未处理的任务时，执行下载任务
				while(taskQueue.size() > 0){
					// 获取第一个任务，并将之从任务队列中删除
					Task task = taskQueue.remove(0);
					// 将下载的图片添加到缓存
					try {
					//   String decodePath=URLDecoder.decode(task.path, "gb2312");
					//   Log.d("****decodepath", decodePath);
					   String directDir,name="";
						String paths[]=task.path.split("/");
						directDir=paths[0];
						if (directDir.compareTo("BetterDeal")==0){
							directDir=paths[1];
						}
						/*
						for(int i=1;i<paths.length;i++){
							name+=paths[i];
						} */
						name=paths[paths.length-1];
						imageUri=Uri.parse("file://"+context.getExternalFilesDir("BetterDeal/"+directDir).getPath()+"/"+name);
						imagePath=StaticValueClass.getPath(context, imageUri);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if(PicUtil.isSaveToDisk(imagePath)){
						try {
							Log.d("*** load pic from disk", "come ,come.come");
							task.bitmap=MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);

						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						task.cardPath="";
					}else{
						task.bitmap = PicUtil.getbitmap(StaticValueClass.serverAddress+task.path);
						task.cardPath=imagePath;
					}
					if(task.path!=null && task.bitmap!=null)
						mMemoryCache.put(task.path, task.bitmap);
					if(handler != null){
						// 创建消息对象，并将完成的任务添加到消息对象中
						Message msg = handler.obtainMessage();
						msg.obj = task;
						// 发送消息回主线程
						handler.sendMessage(msg);
					}
				}
				
				//如果队列为空,则令线程等待
				synchronized (this) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	};
	
	//回调接口
	public interface ImageCallback{
		void loadImage(String path, Bitmap bitmap);
	}
	
	class Task{
		// 下载任务的下载路径
		int type; //1 download and load bitmap;  2 just load bitmap
		String path,cardPath;
		// 下载的图片
		Bitmap bitmap;
		int needWidth,needHeight;
		// 回调对象
		ImageCallback callback;
		
		@Override
		public boolean equals(Object o) {
			Task task = (Task)o;
			return task.path.equals(path);
		}
	}
}