package com.way.betterdeal.fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;

import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;
import com.way.betterdeal.adapters.CommonAdapter;
import com.way.betterdeal.object.Commodity;
import com.way.wasabeef.recyclerview_animation.SlideInBottomAnimationAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FootTraceFragment extends Fragment {

    MainActivity ma;
    View view;
    Button backBtn;
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    CommonAdapter commonAdapter;
    ArrayList<Commodity> commos;
    int space;

    public FootTraceFragment() {
        // Required empty public constructor
        commos=StaticValueClass.currentBuyer.tracingItems;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ma=(MainActivity)getActivity();
        view=inflater.inflate(R.layout.fragment_foot_trace, container, false);
        backBtn=(Button)view.findViewById(R.id.backBtn);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
        init();
        if(StaticValueClass.isAfterKitKat)
            view.setPadding(0, StaticValueClass.statusBarHeight, 0, 0);
        return view;
    }

    private void init(){
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
        initRecyclerView();
    }

    private void initRecyclerView(){
        space= StaticValueClass.dip2px(FootTraceFragment.this.getContext() , 4);
        gridLayoutManager=new GridLayoutManager(this.getContext(),2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

            @Override
            public int getSpanSize(int position) {
                // TODO Auto-generated method stub
                //	return position==0? gridLayoutmanager.getSpanCount():1;
                if( position==commos.size()){
                    return gridLayoutManager.getSpanCount();
                }else return 1;
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        commonAdapter=new CommonAdapter(ma);
        commonAdapter.LoadData(StaticValueClass.currentBuyer.tracingItems);
        recyclerView.setAdapter(commonAdapter);
        commonAdapter.notifyDataSetChanged();
        recyclerView.addItemDecoration(new SpaceItemDecoration());
        /*
        SlideInBottomAnimationAdapter slideInBottomAdapter=new SlideInBottomAnimationAdapter(myAdapter);
        slideInBottomAdapter.setFirstOnly(true);
        slideInBottomAdapter.setDuration(500);
        slideInBottomAdapter.setInterpolator(new OvershootInterpolator(.5f));
        contentRecyclerView.addItemDecoration(new ContentFragment.SpaceItemDecoration());
        contentRecyclerView.setAdapter(slideInBottomAdapter);
        */
    }

    public class SpaceItemDecoration extends RecyclerView.ItemDecoration{

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            // TODO Auto-generated method stub
            //	super.getItemOffsets(outRect, view, parent, state);
            int 	position=parent.getChildLayoutPosition(view);
            if(position==commos.size()){
                outRect.left=outRect.right=0;
            }else  if(position%2==0){
                outRect.right=space;
            }else outRect.left=space;
            outRect.bottom=2*space;
        }

    }

}
