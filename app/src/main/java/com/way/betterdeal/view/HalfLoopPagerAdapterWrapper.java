package com.way.betterdeal.view;

import android.support.v4.view.PagerAdapter;

public class HalfLoopPagerAdapterWrapper extends LoopPagerAdapterWrapper {

	HalfLoopPagerAdapterWrapper(LoopViewPager paramLoopViewPager,PagerAdapter adapter) {
		super(paramLoopViewPager,adapter);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int getCount()
	  {
	    return 1 + mAdapter.getCount();
	  }

	  @Override
	public int getRealCount()
	  {
	    return 1 + this.mAdapter.getCount();
	  }

	  protected int getRealFirstPosition()
	  {
	    return 0;
	  }

	  protected int getRealLastPosition()
	  {
	    return -2 + (getRealFirstPosition() + getRealCount());
	  }

	  @Override
	public int toInnerPosition(int paramInt)
	  {
	    return paramInt;
	  }
/*
	  public int toRealPosition(int paramInt)
	  {
	    int i = getRealCount();
	    int j=0;
	    if (i == 0)
	      j = 0;
	    do{
	    	
	    }while(i>0);
	    do
	    {
	      return j;
	      j = paramInt%(i-1);
	    }
	    while (j >= 0);
	    return j + i;
	  } */

}
