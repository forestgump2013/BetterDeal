package com.way.betterdeal;

import cn.jpush.android.api.JPushInterface;
import android.app.Application;

public class BetterDealApplation extends Application {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
	   JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
	}
	
}
