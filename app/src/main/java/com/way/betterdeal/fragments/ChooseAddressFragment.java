package com.way.betterdeal.fragments;

import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;
import com.way.betterdeal.adapters.ExpressAddressAdapter;
import com.way.betterdeal.object.BuyerAddressRecord;
import com.way.betterdeal.object.GameBonusRecord;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ChooseAddressFragment extends Fragment implements ExpressAddressAdapter.ChooseAddressListener {
	
	View view,checkView;
	MainActivity ma;
	AlertDialog alertDialog;
	Button createBtn,reFillBtn,confirmBtn;
	Button backBtn;
	TextView hung1,hung2;
	ListView listView;
	ExpressAddressAdapter expressAddressAdapter;
	GameBonusRecord record;
	BuyerAddressRecord addrRecord;
	
	public ChooseAddressFragment(){

	}
	
	public void setConcernsBonusRecord(GameBonusRecord rd){
		record=rd;
	}

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.choose_address_fragment, container, false);
		checkView=inflater.inflate(R.layout.address_check_dialog, container, false);
		createBtn=(Button)view.findViewById(R.id.createBtn);
		backBtn=(Button)view.findViewById(R.id.backBtn);
		hung1=(TextView)view.findViewById(R.id.hung1);
		hung2=(TextView)view.findViewById(R.id.hung2);
	//	addrAddr=(EditText)view.findViewById(R.id.addrAddr);
		listView=(ListView)view.findViewById(R.id.adrListView);
		init();
		if(StaticValueClass.isAfterKitKat)
        	view.setPadding(0, StaticValueClass.statusBarHeight, 0, 0);
		return view;
	//	return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	private void init(){
		ma=(MainActivity)this.getActivity();
//		ArrayList<ExpressAddressRecord> addressRecords=new ArrayList<ExpressAddressRecord>();
		expressAddressAdapter=new ExpressAddressAdapter(this.getActivity(),StaticValueClass.currentBuyer.addressRecords);
		expressAddressAdapter.setChooseAddressListener(this);
		listView.setAdapter(expressAddressAdapter);
		listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
			}
			
		});
		listView.setOnItemLongClickListener(new OnItemLongClickListener(){

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				return false;
			}
			
		});
		createBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ma.loadcreateAddressFragment(record);
			}
		});
		/*
		doneBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				int select=expressAddressAdapter.getSelect();
				if(select!=-1){
					addrRecord=StaticValueClass.currentBuyer.addressRecords.get(select);
				}else{
					if(addrName.getText().toString().compareTo("")==0||addrTel.getText().toString().compareTo("")==0
							||addrAddr.getText().toString().compareTo("")==0){
						Toast.makeText(getActivity(), "请填入必要信息！", Toast.LENGTH_SHORT).show();
						return;
					}else addrRecord=new BuyerAddressRecord(addrName.getText().toString(),addrTel.getText().toString(),
							addrAddr.getText().toString());
					StaticValueClass.currentBuyer.addressRecords.add(0, addrRecord);
					if(StaticValueClass.currentBuyer.addressRecords.size()>3){
						StaticValueClass.currentBuyer.addressRecords.remove(3);
					}
					
				}
				StaticValueClass.removeKeyboard(getActivity(), v);
				initDialog();
			}
		}); */
	//	backBtn.setImageBitmap(StaticValueClass.getBackIcon(BitmapFactory.decodeResource(getResources(), R.drawable.expand_down)));
		Bitmap backmark=BitmapFactory.decodeResource(this.getResources(), R.mipmap.expand_icon);
    	Drawable leftDrawable=new BitmapDrawable(StaticValueClass.getBackIcon(backmark));
    	leftDrawable.setBounds(0, 0, backmark.getWidth(), backmark.getHeight());
    	backBtn.setCompoundDrawables(leftDrawable, null, null, null);
		backBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((MainActivity)ChooseAddressFragment.this.getActivity()).onBackPressed();
			}
		});
		
		LinearLayout.LayoutParams params1=(LinearLayout.LayoutParams)hung1.getLayoutParams();
		LinearLayout.LayoutParams params2=(LinearLayout.LayoutParams)hung2.getLayoutParams();
		params1.height=137*StaticValueClass.screenWidth/720;
		params2.height=103*StaticValueClass.screenWidth/720;
	}

	@Override
	public void chooseAddress(int index) {
		// TODO Auto-generated method stub
		addrRecord=StaticValueClass.currentBuyer.addressRecords.get(index);
		showDialog();
	}
	
	private void showDialog(){
		AlertDialog.Builder checkBuilder=new AlertDialog.Builder(getContext());
	    alertDialog=checkBuilder.create();
	    alertDialog.show();
	    Window window=alertDialog.getWindow();
	    window.setContentView(R.layout.address_check_dialog);
	    TextView textName=(TextView)window.findViewById(R.id.textName);
	    TextView textTel=(TextView)window.findViewById(R.id.textTel);
	    TextView textAddr=(TextView)window.findViewById(R.id.textAddr);
	    textName.setText(addrRecord.name);
	    textTel.setText(addrRecord.tel);
	    textAddr.setText(addrRecord.address);
	    reFillBtn=(Button)window.findViewById(R.id.reFillBtn);
		confirmBtn=(Button)window.findViewById(R.id.confirmBtn);
	    WindowManager.LayoutParams lp=window.getAttributes();
	    lp.width=StaticValueClass.screenWidth*62/72;
	    lp.height=StaticValueClass.screenWidth*46/72;
	    reFillBtn.setText("取消");
	    window.setAttributes(lp);
	    reFillBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				expressAddressAdapter.setSelectIndex(-1);
				expressAddressAdapter.notifyDataSetChanged();
				alertDialog.dismiss();
			}
		});
	    confirmBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				record.setAddresInfo(addrRecord);
				alertDialog.dismiss();
				ma.onBackPressed();
			}
		});
	}

	/*
	public static void main(String[] args) {
		 Log.d("***** attr height", ""+a.getDimension(attr, 20));
	}
	
	*/
	

}
