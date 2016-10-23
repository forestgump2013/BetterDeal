package com.way.betterdeal.fragments;

import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class ConfigrationFragment extends Fragment {
	
	MainActivity ma;
	View configView;
	Button loginOutBtn;
	public ConfigrationFragment(){

	}
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		ma=(MainActivity) this.getActivity();
		configView=inflater.inflate(R.layout.configration_fragment, container, false);
		loginOutBtn=(Button)configView.findViewById(R.id.loginOutBtn);
		initFunction();
		 if(StaticValueClass.isAfterKitKat)
			 configView.setPadding(0, StaticValueClass.statusBarHeight, 0, 0);
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
						ma.onBackPressed();
						ma.unLogin();
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
    }
}
