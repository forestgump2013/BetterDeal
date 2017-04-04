package com.way.betterdeal.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.way.betterdeal.R;

import org.w3c.dom.Text;

/**
 * Created by Administrator on 2016/12/27.
 */

public class MarkImageView extends ImageView {
    TextView content;
    public MarkImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
      //  init();
    }

    private void init(){
        MarkImageView.this.setImageResource(R.mipmap.expand_icon);
        MarkImageView.this.getLayoutParams().height=48;
        MarkImageView.this.getLayoutParams().width=24;
    }
    public void setContent(TextView tv){
        content=tv;
        this.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if(content.getVisibility()==View.VISIBLE){
                    content.setVisibility(View.GONE);
                    MarkImageView.this.setImageResource(R.mipmap.expand_icon);
                    MarkImageView.this.getLayoutParams().height=48;
                    MarkImageView.this.getLayoutParams().width=24;
                }else {
                    content.setVisibility(View.VISIBLE);
                    MarkImageView.this.setImageResource(R.mipmap.expand_down);
                    MarkImageView.this.getLayoutParams().height=24;
                    MarkImageView.this.getLayoutParams().width=48;
                }
            }
        });
    }


}
