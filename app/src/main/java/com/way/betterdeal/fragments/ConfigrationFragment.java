package com.way.betterdeal.fragments;

import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.util.logging.LogRecord;

public class ConfigrationFragment extends Fragment {
	
	MainActivity ma;
	View configView;
	Button backBtn,loginOutBtn;
	TextView aboutText,cachSize,checkVersion;
	CheckBox checkBox1;
	File cachFile;
	Handler handler=new Handler() {
		@Override
		public void handleMessage(Message msg) {
		//	super.handleMessage(msg);
			switch (msg.what){
				case 1:
					ma.unLogin();
					StaticValueClass.currentBuyer.setNeedUpdate(true);
					ma.onBackPressed();
					break;
				default:
			}
		}
	};
	public ConfigrationFragment(){

	}
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		ma=(MainActivity) this.getActivity();
		configView=inflater.inflate(R.layout.configration_fragment, container, false);
		backBtn=(Button)configView.findViewById(R.id.backBtn);
		aboutText=(TextView) configView.findViewById(R.id.aboutText);
		cachSize=(TextView) configView.findViewById(R.id.cachSize);
		checkVersion=(TextView) configView.findViewById(R.id.checkVersion);
		checkBox1=(CheckBox)configView.findViewById(R.id.checkBox1);
		loginOutBtn=(Button)configView.findViewById(R.id.loginOutBtn);
		initFunction();
		 if(StaticValueClass.isAfterKitKat)
			 configView.setPadding(0, StaticValueClass.statusBarHeight, 0, 0);
		this.setConfigureActionListener(ma.personalCenterFragment);
		return configView;
	//	return super.onCreateView(inflater, container, savedInstanceState);
	}
    private void initFunction(){
    	loginOutBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!StaticValueClass.currentBuyer.isLogined()){
					Toast.makeText(ma,"已经退出登录！",Toast.LENGTH_SHORT).show();
					return;
				}
				AlertDialog.Builder builder=new AlertDialog.Builder(ma);
				builder.setTitle("退出登录");
				builder.setPositiveButton("确定", new OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					Message message=new Message();
						message.what=1;
						handler.sendMessage(message);
					}
					
				});
				builder.setNegativeButton("取消", new OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
					
				});
				builder.show();
			}
		});
		Bitmap backmark= BitmapFactory.decodeResource(this.getActivity().getResources(), R.mipmap.expand_icon);
		Drawable leftDrawable=new BitmapDrawable(StaticValueClass.getBackIcon(backmark));
		leftDrawable.setBounds(0, 0, backmark.getWidth(), backmark.getHeight());
		//backBtn.setBackground(leftDrawable);
		backBtn.setCompoundDrawables(leftDrawable, null, null, null);
		backBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ma.onBackPressed();
			}
		});

		aboutText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ma.loadAboutFragment();
			}
		});

		checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				ma.setMaskView(isChecked);
			}
		});
		//.
		//context.getExternalFilesDir("BetterDeal/"+directDir).getPath()
		cachFile=ma.getExternalFilesDir("BetterDeal");
		try {
			float mbSize= StaticValueClass.getFileSize(cachFile)/(1024*1024);
			cachSize.setText(String.format("%.2f",(mbSize-5))+"MB");
		}catch (Exception e){
			e.printStackTrace();
		}
		cachSize.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				 File[] files=cachFile.listFiles();
				File file;
				for(int i=0;i<files.length;i++){
					file=files[i];
					file.delete();
				}
				cachSize.setText("0MB");
				Toast.makeText(ma,"清空缓存。",Toast.LENGTH_SHORT).show();
			}
		});
		checkVersion.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ma.getVersionInfo();
			}
		});
    }

	public interface ConfigureActionListener{
		public void logoutAction();
	}

	ConfigureActionListener configureActionListener;
	public  void setConfigureActionListener(ConfigureActionListener listener){
		configureActionListener=listener;
	}
}
