package com.way.betterdeal.object;

import android.graphics.Bitmap;
import android.os.Looper;
import android.util.Log;

import com.way.betterdeal.StaticValueClass;
import com.way.betterdeal.fragments.ContentFragment;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by zhouchenglin on 2016/10/26.
 */

public class GameController {

    public static ArrayList<Bitmap> ninePanesImage=new ArrayList<Bitmap>();
    public static ArrayList<Bitmap> darkNinePanesImage =new ArrayList<Bitmap>();
    public static ArrayList<GameBonusRecord> ninePanesPrizes=new  ArrayList<GameBonusRecord>();
    public static ArrayList<GameBonusRecord> slotPrizes=new  ArrayList<GameBonusRecord>();
    public static  int  finshLevel;
    public static int[] panesLottery={0,10,20,30,40,50,60,70,100},slotLottery={0,10,20,34,45,100};

    public GameController(){
        finshLevel=0;
    }

    public void getGamePrize(){
        new Thread(new Runnable(){

            @Override
            public void run() {
                // TODO Auto-generated method stub
                //		ArrayList<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
                Looper.prepare();
                try{
                    HttpClient httpclient = new DefaultHttpClient(StaticValueClass.httpParameters);
                    HttpPost httppost = new HttpPost(StaticValueClass.serverAddress+"ch_get_game_prizes.php");
                    //        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,HTTP.UTF_8));
                    HttpResponse response = httpclient.execute(httppost);
                    Log.d("***ch_get_game_prizes:", " recode:"+response.getStatusLine().getStatusCode());
                    if(response.getStatusLine().getStatusCode()==200){
                        ninePanesPrizes.clear();
                        slotPrizes.clear();
                        String mResult= EntityUtils.toString(response.getEntity());
                        JSONObject jsonObject;
                        JSONArray jsonArray = new JSONArray(mResult);
                        for(int i=0;i<jsonArray.length();i++){
                            jsonObject=(JSONObject)jsonArray.opt(i);
                            GameBonusRecord prize=new GameBonusRecord(jsonObject.getInt("idx"),jsonObject.getString("title"),
                                    (float)jsonObject.getDouble("price"),"",jsonObject.getInt("probability"),
                                    jsonObject.getInt("quantity"),jsonObject.getInt("type"));
                           if (prize.gameMark==1){
                               ninePanesPrizes.add(prize);
                           }else slotPrizes.add(prize);
                        }
                        // compute lotteries.
                        generateLottery();
                        if (finishLoadListener!=null){
                            finishLoadListener.finishPrizeLoad();
                        }

                    }
                }catch(SocketTimeoutException e){
                    //  Toast.makeText(ma, "数据刷新超时！", Toast.LENGTH_SHORT).show();
                }
                catch(Exception e){
                    //  Toast.makeText(ma, "数据刷新失败！", Toast.LENGTH_SHORT).show();
                    //   Log.e("log_tag", "Error in http connection :ch_get_cheap_info "+e.toString());

                }
            }

        }).start();
    }

    private  void getDarkImages(){
        String url=StaticValueClass.serverAddress+"/htdocs/BetterDeal/gamePics/1/",imgUrl;
        for(int i=1;i<=8;i++){
            imgUrl=url+i+".png";
            Bitmap bitmap=PicUtil.getbitmap(imgUrl);
            ninePanesImage.add(bitmap);
            Bitmap darkBit=StaticValueClass.getDarkImage(bitmap);
            darkNinePanesImage.add(darkBit);
        }
        if (finishLoadListener!=null){
            finishLoadListener.finishImageDarken();
        }
    }

    private  void generateLottery(){
        for(int i=1;i<= StaticValueClass.ninePanesPrizes.size();i++){
            panesLottery[i]=panesLottery[i-1]+StaticValueClass.ninePanesPrizes.get(i-1).bonusWeight;
        }
        for(int i=1;i<= StaticValueClass.slotPrizes.size();i++){
            slotLottery[i]=slotLottery[i-1]+StaticValueClass.slotPrizes.get(i-1).bonusWeight;
        }
    }

    public int getNinePanesNext( int flag){
        int index=0,value=0;
        Random r=new Random();
        while (value==0){
            value=(int)(r.nextDouble()*100);
        }

        if (flag==1){
            // ninePanes.
            for (int i=1;i<panesLottery.length;i++){
                if (value<=panesLottery[i]){
                    index=i;
                    // check quantity ==0;
                    if (ninePanesPrizes.get(index).quantity==0)
                        index=ninePanesPrizes.size();
                    break;
                }
            }
        }else {
            // slot.
            for (int i=1;i<slotLottery.length;i++){
                if (value<=slotLottery[i]){
                    index=i;
                    // check quantity ==0;
                    if (slotPrizes.get(index).quantity==0)
                        index=slotPrizes.size();
                    break;
                }
            }
        }

        return index;
    }

    public interface  FinishLoadListener{
        public void finishPrizeLoad();
        public void finishImageDarken();
    }
    private FinishLoadListener finishLoadListener;
    public void setFinishLoadListener(FinishLoadListener listener){
        finishLoadListener=listener;
    }

}
