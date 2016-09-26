package com.way.betterdeal.fragments;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;
import com.way.betterdeal.adapters.CoinsAdapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class CoinFragment extends Fragment {
	
	View view;
	TextView coinNumber;
	ListView coinListView;
	CoinsAdapter coinsAdapter;
	Button backBtn;
	public CoinFragment(){
		
	}

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.coin_fragment, container, false);
		coinNumber=(TextView)view.findViewById(R.id.coinNumber);
		coinListView=(ListView)view.findViewById(R.id.coinListView);
		backBtn=(Button)view.findViewById(R.id.backBtn);
		coinsAdapter=new CoinsAdapter(this.getActivity(),StaticValueClass.currentBuyer.coinRecords);
		coinListView.setAdapter(coinsAdapter);
		coinListView.setCacheColorHint(0);
		//
		Bitmap backmark=BitmapFactory.decodeResource(this.getResources(), R.mipmap.expand_icon);
    	Drawable leftDrawable=new BitmapDrawable(StaticValueClass.getBackIcon(backmark));
    	leftDrawable.setBounds(0, 0, backmark.getWidth(), backmark.getHeight());
    	backBtn.setCompoundDrawables(leftDrawable, null, null, null);
    	if(StaticValueClass.isAfterKitKat)
        	view.setPadding(0, StaticValueClass.statusBarHeight, 0, 0);
		return view;
		//return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		coinNumber.setText(""+StaticValueClass.currentBuyer.bonus);
	}
}
