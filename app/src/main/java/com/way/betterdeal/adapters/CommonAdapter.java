package com.way.betterdeal.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.sdk.android.AlibabaSDK;
import com.alibaba.sdk.android.trade.TradeService;
import com.alibaba.sdk.android.trade.callback.TradeProcessCallback;
import com.alibaba.sdk.android.trade.model.TaokeParams;
import com.alibaba.sdk.android.trade.model.TradeResult;
import com.alibaba.sdk.android.trade.page.ItemDetailPage;
import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;
import com.way.betterdeal.fragments.ContentFragment;
import com.way.betterdeal.object.Commodity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/13.
 */

public class CommonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    MainActivity activity;
    ArrayList<Commodity> commos;

    public  CommonAdapter(MainActivity at){
        activity=at;
    }

    public  void LoadData(ArrayList<Commodity> cs){
        commos=cs;
    }

    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        Log.d("CommonAdapter","size:"+(commos.size()+1));
        return commos.size();
    }



    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        //return super.getItemViewType(position);
        if(position==commos.size()) return 1;
        else return 0;
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vHolder, int position) {
        // TODO Auto-generated method stub
        if(vHolder instanceof VHItem){
            VHItem holder=(VHItem)vHolder;
            //	holder.setPosition(position%10);
            holder.loadData(commos.get(position));
        }else if(vHolder instanceof FootHolder){
            FootHolder holder=(FootHolder)vHolder;
        }

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
        // TODO Auto-generated method stub
        View view;
        if(type==0){
            view=activity.getLayoutInflater().inflate(R.layout.commodity_poster, viewGroup, false);
            return new VHItem(view);
        }else {
            view=activity.getLayoutInflater().inflate(R.layout.footer_view, viewGroup, false);
            return new FootHolder(view);
        }

    }

    class VHItem extends RecyclerView.ViewHolder{
        TextView title,price,pre_price,marketTitle;
        ImageView poster,marketMark,divider,couponMark;
        int position;
        public VHItem(View itemView) {
            super(itemView);
            poster=(ImageView)itemView.findViewById(R.id.posterView);
            marketMark=(ImageView)itemView.findViewById(R.id.markImageView);
            divider=(ImageView)itemView.findViewById(R.id.divider);
            marketTitle=(TextView)itemView.findViewById(R.id.mallTitle);
            title=(TextView)itemView.findViewById(R.id.item_title);
            couponMark=(ImageView)itemView.findViewById(R.id.couponMark);
            price=(TextView)itemView.findViewById(R.id.item_price);
            pre_price=(TextView)itemView.findViewById(R.id.item_reserve_price);
            ViewGroup.LayoutParams params=poster.getLayoutParams();
            params.height= StaticValueClass.screenWidth*7/15;

            pre_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            divider.setVisibility(View.INVISIBLE);
            LinearLayout.LayoutParams params1=(LinearLayout.LayoutParams)divider.getLayoutParams();
            params1.height=16;
            //
            title.setTypeface(StaticValueClass.hanYiThinFont);
        }

        public void setPosition(int p){
            position=p;
            //   String url="http://www.qcygl.com/betterCommoditisPic/b"+p+".jpg";
            //	StaticValueClass.asynImageLoader.showImageAsyn(poster, url, R.drawable.blank_background);

        }
        public void loadData(final Commodity commo){

            title.setText(commo.title);
            price.setText(" ¥ "+commo.price);
            pre_price.setText(""+commo.reserve_price);
            //  commo.picUrl="BetterDeal/Discout/"+commo.picName+".jpg";
            StaticValueClass.asynImageLoader.showImageAsyn(poster, commo.picUrl, R.mipmap.blank_background);
            if(commo.coupon==1){
                couponMark.setVisibility(View.VISIBLE);
            }else couponMark.setVisibility(View.GONE);
            switch(commo.market){
                case 1: marketMark.setImageResource(R.mipmap.taobao_c_mark);
                    marketTitle.setText("淘宝");
                    break;
                case 2: marketMark.setImageResource(R.mipmap.tianmao_c_mark);
                    marketTitle.setText("天猫");
                    break;

            }

            poster.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    //   showItemDetailPage(v,""+commo.itemId);
                    activity.loadCommodityDetailFragment(commo);
                }

            });
        }

    }

    class FootHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public FootHolder(View view) {
            super(view);
            textView=(TextView)view.findViewById(R.id.textView);
            textView.setTypeface(StaticValueClass.hanYiThinFont);
            // TODO Auto-generated constructor stub
        }

    }

}


