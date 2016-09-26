package com.way.betterdeal.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ScreenFragment extends Fragment {
	public static ScreenFragment newInstance(int layout) {
    	ScreenFragment fragment = new ScreenFragment();
    	Bundle args = new Bundle();
    	args.putInt("layout", layout);
    	fragment.setArguments(args);
        return fragment;
    }

	private int mLayout;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	mLayout = getArguments().getInt("layout");
    	super.onCreate(savedInstanceState);
    }
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(mLayout, container, false);
	}
}
