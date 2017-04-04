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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;
import com.way.betterdeal.view.MarkImageView;
import com.way.betterdeal.view.NotScrolledListView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpFragment extends Fragment implements View.OnClickListener {

    MainActivity ma;
    View view;
    Button backBtn;
    ScrollView scrollView;
    RelativeLayout part11,part12,part13,part14,part15;
    MarkImageView imageView1,imageView2,imageView3,imageView4,imageView5;
    RelativeLayout part21,part22,part23,part24;
    MarkImageView imageView6,imageView7,imageView8,imageView9;
    MarkImageView imageView35,imageView34,imageView33,imageView32,imageView31;
    RelativeLayout part31,part32,part33,part34,part35;
    MarkImageView imageView45,imageView44,imageView43,imageView42,imageView41;
    RelativeLayout part41,part42,part43,part44,part45;
    TextView answer1,answer2,answer3,answer4,answer5;
    TextView answer6,answer7,answer8,answer9;
    TextView answer31,answer32,answer33,answer34,answer35;
    TextView answer41,answer42,answer43,answer44,answer45;
    TextView moreQuestions1,moreQuestions2,moreQuestions3,moreQuestions4;
    LinearLayout moreContent1,moreContent2,moreContent3,moreContent4;
    Bitmap downExpand,upExpand;
    int dp12,dp24;
    public HelpFragment() {
        // Required empty public constructor

        initItems();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ma=(MainActivity)this.getActivity();
        dp12=StaticValueClass.dip2px(ma,12);
        dp24=StaticValueClass.dip2px(ma,24);
        view=inflater.inflate(R.layout.fragment_help, container, false);
        backBtn=(Button)view.findViewById(R.id.backBtn);
        scrollView=(ScrollView)view.findViewById(R.id.scrollView);
        //
        part11=(RelativeLayout)view.findViewById(R.id.part11);
        part12=(RelativeLayout)view.findViewById(R.id.part12);
        part13=(RelativeLayout)view.findViewById(R.id.part13);
        part14=(RelativeLayout)view.findViewById(R.id.part14);
        part15=(RelativeLayout)view.findViewById(R.id.part15);
        imageView1=(MarkImageView) view.findViewById(R.id.imageView1);
        imageView2=(MarkImageView) view.findViewById(R.id.imageView2);
        imageView3=(MarkImageView) view.findViewById(R.id.imageView3);
        imageView4=(MarkImageView) view.findViewById(R.id.imageView4);
        imageView5=(MarkImageView) view.findViewById(R.id.imageView5);
        //
        part21=(RelativeLayout)view.findViewById(R.id.part21);
        part22=(RelativeLayout)view.findViewById(R.id.part22);
        part23=(RelativeLayout)view.findViewById(R.id.part23);
        part24=(RelativeLayout)view.findViewById(R.id.part24);
        imageView6=(MarkImageView) view.findViewById(R.id.imageView6);
        imageView7=(MarkImageView) view.findViewById(R.id.imageView7);
        imageView8=(MarkImageView) view.findViewById(R.id.imageView8);
        imageView9=(MarkImageView) view.findViewById(R.id.imageView9);
        //
        part31=(RelativeLayout)view.findViewById(R.id.part31);
        part32=(RelativeLayout)view.findViewById(R.id.part32);
        part33=(RelativeLayout)view.findViewById(R.id.part33);
        part34=(RelativeLayout)view.findViewById(R.id.part34);
        part35=(RelativeLayout)view.findViewById(R.id.part35);
        imageView31=(MarkImageView) view.findViewById(R.id.imageView31);
        imageView32=(MarkImageView) view.findViewById(R.id.imageView32);
        imageView33=(MarkImageView) view.findViewById(R.id.imageView33);
        imageView34=(MarkImageView) view.findViewById(R.id.imageView34);
        imageView35=(MarkImageView) view.findViewById(R.id.imageView35);
        //
        imageView41=(MarkImageView) view.findViewById(R.id.imageView41);
        imageView42=(MarkImageView) view.findViewById(R.id.imageView42);
        imageView43=(MarkImageView) view.findViewById(R.id.imageView43);
        imageView44=(MarkImageView) view.findViewById(R.id.imageView44);
        imageView45=(MarkImageView) view.findViewById(R.id.imageView45);
        part41=(RelativeLayout)view.findViewById(R.id.part41);
        part42=(RelativeLayout)view.findViewById(R.id.part42);
        part43=(RelativeLayout)view.findViewById(R.id.part43);
        part44=(RelativeLayout)view.findViewById(R.id.part44);
        part45=(RelativeLayout)view.findViewById(R.id.part45);
        //
        moreQuestions1=(TextView)view.findViewById(R.id.moreQuestions1);
        moreQuestions2=(TextView)view.findViewById(R.id.moreQuestions2);
        moreQuestions3=(TextView)view.findViewById(R.id.moreQuestions3);
        moreQuestions4=(TextView)view.findViewById(R.id.moreQuestions4);
        //
        moreContent1=(LinearLayout)view.findViewById(R.id.moreContent1);
        moreContent2=(LinearLayout)view.findViewById(R.id.moreContent2);
        moreContent3=(LinearLayout)view.findViewById(R.id.moreContent3);
        moreContent4=(LinearLayout)view.findViewById(R.id.moreContent4);
        //
        answer1=(TextView)view.findViewById(R.id.answer1);
        answer2=(TextView)view.findViewById(R.id.answer2);
        answer3=(TextView)view.findViewById(R.id.answer3);
        answer4=(TextView)view.findViewById(R.id.answer4);
        answer5=(TextView)view.findViewById(R.id.answer5);
        answer6=(TextView)view.findViewById(R.id.answer6);
        answer7=(TextView)view.findViewById(R.id.answer7);
        answer8=(TextView)view.findViewById(R.id.answer8);
        answer9=(TextView)view.findViewById(R.id.answer9);
        //.
        answer31=(TextView)view.findViewById(R.id.answer31);
        answer32=(TextView)view.findViewById(R.id.answer32);
        answer33=(TextView)view.findViewById(R.id.answer33);
        answer34=(TextView)view.findViewById(R.id.answer34);
        answer35=(TextView)view.findViewById(R.id.answer35);
        //
        answer41=(TextView)view.findViewById(R.id.answer41);
        answer42=(TextView)view.findViewById(R.id.answer42);
        answer43=(TextView)view.findViewById(R.id.answer43);
        answer44=(TextView)view.findViewById(R.id.answer44);
        answer45=(TextView)view.findViewById(R.id.answer45);

        if(StaticValueClass.isAfterKitKat)
            view.setPadding(0, StaticValueClass.statusBarHeight, 0, 0);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        initFunction();
        return view;
    }

    private void initIcon(){
        downExpand=BitmapFactory.decodeResource(ma.getResources(),R.mipmap.expand_down);
        upExpand=StaticValueClass.rotateBitmap(downExpand,180);
    }

    private  void initItems(){
            //.
        /*
            items1.add(new HelpItem("1、在剁手联盟里购买商品安全吗？有保障吗？","绝对安全。剁手联盟APP仅和大型知名购物商城平台合作（如天猫，淘宝，京东等等），对性价比高的商品进行推荐与展示，您的购买流程均在商品对应的上述平台进行和完成，购买完成后交易订单可随时查询，享有全部售后保障。"));
            items1.add(new HelpItem("2、为什么有的商品可以领到现金抵扣优惠券，而直接在天猫或淘宝购买却没有优惠？","剁手联盟会组织天猫、淘宝商家进行限时限量的特卖，独家发布优惠券，下单即减，以低廉的价格购买到您心仪的商品，是独享的特权哦。"));
            items1.add(new HelpItem("3、购买商品前为什么还要在对应平台进行登录？","为了保障您的购买权益，请在购买时按照提示进行对应平台的账号登录或注册，以便生成有效订单，订单状态及物流进度可在对应平台进行查询。"));
            items1.add(new HelpItem("4、为什么天猫淘宝有的商品优惠券领取时会告知已领取完？","所有的商品优惠券都是独家限量供应，同一商品每人限领一张，领完即止，领取后即可使用，请注意优惠券有效时限，不要浪费哦。"));
            items1.add(new HelpItem("5、收到商品不满意或需要退换怎么办？","您购买的商品均来自大型购物商城平台，严格遵守对应的平台购买交易规则，有完善的售后保障，如有咨询、售后或退换货问题，建议优先联系商家处理，也可联系商品对应平台客服进行处理，双重保障，购买更安心。"));
            //.
            items2.add(new HelpItem("1、游戏活动是免费的吗？如何参与？","游戏完全免费，请您登陆后进入游戏页面，按照游戏规则进行操作，即可有机会获得相应奖品。"));
            items2.add(new HelpItem("2、中奖后奖品如何领取？","中奖后请您按照页面提示填写正确地址和收件人电话，奖品会在您填好地址后的2-3个工作日寄出，快递单号会上传至中奖记录页面，您可在快递公司网站进行查询。"));
            items2.add(new HelpItem("3、游戏活动是每天都可以参与吗？","每天都可以参与，不同的游戏有对应的次数限制规则，您每天都有获得免费奖品的机会。"));
            items2.add(new HelpItem("4、这个奖品我已经中过了，这次又中了，可以更换吗？","游戏中奖之后的奖品不能更换和折现，奖品种类会不定期更新，敬请关注和参与。"));
            //.
            items3.add(new HelpItem("1、福利社是什么？","福利社是为广大剁友提供的福利兑换平台，内有虚拟、实物商品及生活类服务体验，不定期更换。"));
            items3.add(new HelpItem("2、福利社里的活动如何参与？各项福利如何兑换？","福利社里的活动和福利均需要相应的金币方可参与或兑换，具体活动规则和兑换流程请参照对应页面说明。"));
            items3.add(new HelpItem("3、如何获取金币？","每日签到、幸运游戏、完成小任务，均可获得相应数量金币。"));
            items3.add(new HelpItem("4、已经兑换完的福利可以取消退还金币吗？","兑换成功的福利不支持取消，不能退还金币，请仔细确定后再行兑换。"));
            items3.add(new HelpItem("5、兑换的商品和服务有保障吗？","同正常商品和服务一样享有售后保障，如有问题可直接联系福利提供方客服进行处理。"));
            //.
            items4.add(new HelpItem("1、如何注册剁手联盟账号？","在个人中心页面点击注册按钮，输入手机号，获取验证码，设置登录密码，即可注册成功，也可通过第三方账号（微信、QQ、淘宝、新浪微博）进行登录（首次需绑定手机号，以后可直接登录）。"));
            items4.add(new HelpItem("2、忘记密码如何找回？","在个人中心页面点击忘记密码按钮，进行密码重置，按提示设置新的密码，成功后重新登录即可，旧密码自动失效。"));
            items4.add(new HelpItem("3、注册时收不到验证码怎么办？","您可点击重新发送验证码，如还未收到，建议您检查下手机信号和有无短信拦截软件，也可重启手机后再次获取。"));
            items4.add(new HelpItem("4、注册或绑定手机时碰到手机号码被占用的情况怎么办？","如您第一次注册或绑定手机提示号码已注册，有可能您的手机号码被之前的主人注册过，您只需点击忘记密码，按提示进行密码重置即可。"));
            items4.add(new HelpItem("5、使用中遇到页面打不开、提示错误或卡顿情况怎么办？","这种情况有可能是网络问题等，建议您清除缓存关闭客户端稍后重试。"));
                  */
    }

    private  void initFunction(){
        //
      //  initIcon();
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

        scrollView.setVerticalScrollBarEnabled(false);
        scrollView.fullScroll(ScrollView.FOCUS_UP);
        //..
        part11.setOnClickListener(this);
        part12.setOnClickListener(this);
        part13.setOnClickListener(this);
        part14.setOnClickListener(this);
        part15.setOnClickListener(this);
        //
        part21.setOnClickListener(this);
        part22.setOnClickListener(this);
        part23.setOnClickListener(this);
        part24.setOnClickListener(this);
        //
        part31.setOnClickListener(this);
        part32.setOnClickListener(this);
        part33.setOnClickListener(this);
        part34.setOnClickListener(this);
        part35.setOnClickListener(this);
        //
        part41.setOnClickListener(this);
        part42.setOnClickListener(this);
        part43.setOnClickListener(this);
        part44.setOnClickListener(this);
        part45.setOnClickListener(this);
        //
        moreQuestions1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (moreContent1.getVisibility()==View.VISIBLE)
                    moreContent1.setVisibility(View.GONE);
                else  moreContent1.setVisibility(View.VISIBLE);
            }
        });

        moreQuestions2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (moreContent2.getVisibility()==View.VISIBLE)
                    moreContent2.setVisibility(View.GONE);
                else  moreContent2.setVisibility(View.VISIBLE);
            }
        });
        moreQuestions3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (moreContent3.getVisibility()==View.VISIBLE)
                    moreContent3.setVisibility(View.GONE);
                else  moreContent3.setVisibility(View.VISIBLE);
            }
        });
        moreQuestions4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (moreContent4.getVisibility()==View.VISIBLE)
                    moreContent4.setVisibility(View.GONE);
                else  moreContent4.setVisibility(View.VISIBLE);
            }
        });


    }

    @Override
    public void onClick(View v) {
        RelativeLayout vv=(RelativeLayout)v;
        if (vv.equals(part21)){
            expandView(answer6,imageView6);
        }else if (vv.equals(part22)){
            expandView(answer7,imageView7);
        }else if (vv.equals(part23)){
            expandView(answer8,imageView8);
        }else if (vv.equals(part24)){
            expandView(answer9,imageView9);
        }else if (vv.equals(part31)){
            expandView(answer31,imageView31);
        }else if (vv.equals(part32)){
            expandView(answer32,imageView32);
        }else if (vv.equals(part33)){
            expandView(answer33,imageView33);
        }else if (vv.equals(part34)){
            expandView(answer34,imageView34);
        }else if (vv.equals(part35)){
            expandView(answer35,imageView35);
        } else if (vv.equals(part41)){
            expandView(answer41,imageView41);
        }else if (vv.equals(part42)){
            expandView(answer42,imageView42);
        }else if (vv.equals(part43)){
            expandView(answer43,imageView43);
        }else if (vv.equals(part44)){
            expandView(answer44,imageView44);
        }else if (vv.equals(part45)){
            expandView(answer45,imageView45);
        } else if (vv.equals(part11)){
            expandView(answer1,imageView1);
        }else if (vv.equals(part12)){
            expandView(answer2,imageView2);
        }else if (vv.equals(part13)){
            expandView(answer3,imageView3);
        }else if (vv.equals(part14)){
            expandView(answer4,imageView4);
        }else if (vv.equals(part15)){
            expandView(answer5,imageView5);
        }
    }

    private void expandView(TextView content,ImageView mark){
        if(content.getVisibility()==View.VISIBLE){
            content.setVisibility(View.GONE);
            mark.setImageResource(R.mipmap.expand_icon);
            mark.getLayoutParams().height=dp24;
            mark.getLayoutParams().width=dp12;
        }else {
            content.setVisibility(View.VISIBLE);
            mark.setImageResource(R.mipmap.expand_down);
            mark.getLayoutParams().height=dp12;
            mark.getLayoutParams().width=dp24;
        }
    }
}
