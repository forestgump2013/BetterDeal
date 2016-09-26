package com.way.betterdeal;

import java.util.ArrayList;
import java.util.List;

//import com.way.miniqq.R;
import com.way.betterdeal.adapters.FragmentTabAdapter;
import com.way.betterdeal.fragments.MultiViewsFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.RadioGroup;

public class TestTabHostActivity extends FragmentActivity {
	 private RadioGroup rgs;
	    public List<Fragment> fragments = new ArrayList<Fragment>();

	    public String hello = "hello ";

	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.main_tabhost);

	        fragments.add(new MultiViewsFragment());
	     //   fragments.add(new CheapCommodityFragment());
	    //    fragments.add(new RegisterFragment());


	        rgs = (RadioGroup) findViewById(R.id.tabs_rg);

	        FragmentTabAdapter tabAdapter = new FragmentTabAdapter(this, fragments, R.id.tab_content, rgs);
	        tabAdapter.setOnRgsExtraCheckedChangedListener(new FragmentTabAdapter.OnRgsExtraCheckedChangedListener(){
	            @Override
	            public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index) {
	                System.out.println("Extra---- " + index + " checked!!! ");
	            }
	        });

	    }
}
