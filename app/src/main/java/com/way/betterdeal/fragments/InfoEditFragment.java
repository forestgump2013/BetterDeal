package com.way.betterdeal.fragments;

import java.text.SimpleDateFormat;

import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

public class InfoEditFragment extends Fragment {
	
	MainActivity ma;
	View editView;
	TextView title,currentEdit,noticeText;
	EditText editText;
	CalendarView calendarView1;
	Button backBtn,doneBtn;
	int direct,maxCount;
	SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
	
	public static interface EditListener {
		public void getEditInfo(String info);
	}
	EditListener editListener;
	
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		ma=(MainActivity)this.getActivity();
		editView=(View)inflater.inflate(R.layout.infoedit_fragment, container, false);
		title=(TextView)editView.findViewById(R.id.title);
		currentEdit=(TextView)editView.findViewById(R.id.currentEdit);
		noticeText=(TextView)editView.findViewById(R.id.noticeText);
		editText=(EditText)editView.findViewById(R.id.editText);
	//	calendarView1=(CalendarView)editView.findViewById(R.id.calendarView1);
		backBtn=(Button)editView.findViewById(R.id.backBtn);
		doneBtn=(Button)editView.findViewById(R.id.doneBtn);

		init();
		if(StaticValueClass.isAfterKitKat)
			editView.setPadding(0, StaticValueClass.statusBarHeight, 0, 0);
		return editView;
	//	return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		directView();
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if(!hidden){
			directView();
		}
	}

	public void setMaxCount(int c){
		maxCount=c;
	}


	public void setDirect(int d){
		direct=d;
	}
	
	private void directView(){
		if(direct==1){
		//	calendarView1.setVisibility(View.VISIBLE);
			editText.setVisibility(View.GONE);
		}else {
		//	calendarView1.setVisibility(View.GONE);
			editText.setVisibility(View.VISIBLE);
		}
		
		switch(direct){
		case 2: title.setText("编辑昵称");
				maxCount=15;
				noticeText.setText("不能超过15个字符，请注意数量哦！");
				editText.setText(StaticValueClass.currentBuyer.nickName);
				break;
		case 3: title.setText("编辑签名");
				maxCount=30;
				noticeText.setText("不能超过30个字符，请注意数量哦！");
				editText.setText(StaticValueClass.currentBuyer.personalSign);
				break;
		}
		currentEdit.setText("0/"+maxCount);
		
	}

	private  void init(){
		//
		Bitmap backmark= BitmapFactory.decodeResource(this.getActivity().getResources(), R.mipmap.expand_icon);
		Drawable leftDrawable=new BitmapDrawable(StaticValueClass.getBackIcon(backmark));
		leftDrawable.setBounds(0, 0, backmark.getWidth(), backmark.getHeight());
		backBtn.setCompoundDrawables(leftDrawable, null, null, null);
		//
		backBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ma.onBackPressed();
			}
		});
		editText.addTextChangedListener(new TextWatcher(){

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
										  int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
									  int count) {
				// TODO Auto-generated method stub
				Log.d("onTextChanged", "count:"+count);
				currentEdit.setText(s.length()+"/"+maxCount);

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String str=s.toString();
				if(str.length()>maxCount){
					s.delete(maxCount, str.length());
				}
			}

		});
		doneBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				InputMethodManager imm=(InputMethodManager)ma.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(editView.getWindowToken(), 0);

				switch(direct){
				case 2:
					//nick name.
					StaticValueClass.currentBuyer.nickName=editText.getText().toString();
					break;
				case 3:
					// personal sign.
					StaticValueClass.currentBuyer.personalSign=editText.getText().toString();
					break;
				}
				editListener.getEditInfo(editText.getText().toString());
				StaticValueClass.currentBuyer.setNeedUpdate(true);
				ma.onBackPressed();

			}
		});
	}
	public void setEditListener(EditListener l){
		editListener=l;
	}

}
