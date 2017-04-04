package com.way.betterdeal.fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedBackFragment extends Fragment {

    MainActivity ma;
    View view;
    Button backBtn,commitBtn;
    EditText feedbackInfo,contactInfo;

    public FeedBackFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ma=(MainActivity)this.getActivity();
        view=inflater.inflate(R.layout.fragment_feed_back, container, false);
        backBtn=(Button)view.findViewById(R.id.backBtn);
        commitBtn=(Button)view.findViewById(R.id.commitBtn);
        feedbackInfo=(EditText)view.findViewById(R.id.feedbackInfo);
        contactInfo=(EditText)view.findViewById(R.id.contactInfo);
        init();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        if(StaticValueClass.isAfterKitKat)
            view.setPadding(0, StaticValueClass.statusBarHeight, 0, 0);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        clearInfo();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden) clearInfo();
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
        commitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticValueClass.removeKeyboard(ma, v);
                commitFeedbackInfo();
            }
        });
        feedbackInfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length()>0 && contactInfo.getText().toString().length()>0){
                    commitBtn.setBackgroundResource(R.drawable.routine_button_selector);
                }else commitBtn.setBackgroundResource(R.drawable.round_gray);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        contactInfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length()>0 && feedbackInfo.getText().toString().length()>0){
                    commitBtn.setBackgroundResource(R.drawable.routine_button_selector);
                }else commitBtn.setBackgroundResource(R.drawable.round_gray);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void clearInfo(){
        feedbackInfo.setText("");
        contactInfo.setText("");
        commitBtn.setBackgroundResource(R.drawable.round_gray);
    }

    private  void commitFeedbackInfo(){
        if (feedbackInfo.getText().toString().equals("")){
            Toast.makeText(ma,"请填写反馈信息!",Toast.LENGTH_SHORT).show();
            return;
        }
        if (contactInfo.getText().toString().equals("")){
            Toast.makeText(ma,"请填写联系方式!",Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(new Runnable(){

            @Override
            public void run() {
                // TODO Auto-generated method stub
                ArrayList<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("buyer",StaticValueClass.currentBuyer.tel));
                nameValuePairs.add(new BasicNameValuePair("content",feedbackInfo.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("contact",contactInfo.getText().toString()));
                Looper.prepare();
                try{
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost(StaticValueClass.serverAddress+"ch_buyer_feedback.php");
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
                    HttpResponse response = httpclient.execute(httppost);
                    Log.d("ch_buyer_feedback", " recode:"+response.getStatusLine().getStatusCode());
                    if(response.getStatusLine().getStatusCode()==200){
                        //	   Toast.makeText(context, "提交成功！", Toast.LENGTH_LONG).show();
                        String   mResult= EntityUtils.toString(response.getEntity());
                        Log.d("ch_buyer_feedback", "mResult:"+mResult);
                        //generate new Buyer.
                        ma.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ma, "提交成功！", Toast.LENGTH_SHORT).show();
                                ma.onBackPressed();
                            }
                        });


                    }
                }catch(Exception e){
                    Log.e("log_tag", "Error in http connection"+e.toString());

                }


            }

        }).start();

    }

}
