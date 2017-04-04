package com.way.betterdeal.fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorldShopingFragment extends Fragment {

    MainActivity ma;
    View view;
    Button backBtn;
    public WorldShopingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ma=(MainActivity)this.getActivity();
        view=inflater.inflate(R.layout.fragment_world_shoping, container, false);
        backBtn=(Button)view.findViewById(R.id.backBtn);
        if(StaticValueClass.isAfterKitKat)
            view.setPadding(0, StaticValueClass.statusBarHeight, 0, 0);
        init();
        return view;
    }

    private  void init(){
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
    }

}
