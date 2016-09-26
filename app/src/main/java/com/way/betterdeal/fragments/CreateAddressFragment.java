package com.way.betterdeal.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amap.api.services.district.DistrictItem;
import com.amap.api.services.district.DistrictResult;
import com.amap.api.services.district.DistrictSearch;
import com.amap.api.services.district.DistrictSearchQuery;
import com.amap.api.services.district.DistrictSearch.OnDistrictSearchListener;
import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;
import com.way.betterdeal.object.BuyerAddressRecord;
import com.way.betterdeal.object.GameBonusRecord;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class CreateAddressFragment extends Fragment implements
				OnDistrictSearchListener, OnItemSelectedListener,OnItemClickListener {
	
	MainActivity ma;
	View view;
	EditText addrName,addrTel,addrAddr,addrDistrict;
	Button backBtn,doneBtn,reFillBtn,confirmBtn,locationBtn;
	BuyerAddressRecord addrRecord;
	AlertDialog alertDialog,districtDialog;
	GameBonusRecord record;
	
	//
	//当前选中的级别
	private String selectedLevel = DistrictSearchQuery.KEYWORDS_COUNTRY;
	
	// 当前行政区划
	private DistrictItem currentDistrictItem = null;

	// 下级行政区划集合
	private Map<String, List<DistrictItem>> subDistrictMap = new HashMap<String, List<DistrictItem>>();

	// 省级列表
	private List<DistrictItem> provinceList = new ArrayList<DistrictItem>();

	// 市级列表
	private List<DistrictItem> cityList = new ArrayList<DistrictItem>();

	// 区县级列表
	private List<DistrictItem> districtList = new ArrayList<DistrictItem>();

	// 是否已经初始化
	private boolean isInit = false;

//	private Spinner spinnerProvince;
//	private Spinner spinnerCity;
//	private Spinner spinnerDistrict;	
	TextView districtTitle;
	ListView districtListView;
	AlertDialog.Builder checkBuilder,districtBuilder;
	String cProvince,cCity,cDistrict;
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.create_address_fragment, container, false);
		backBtn=(Button)view.findViewById(R.id.backBtn);
		doneBtn=(Button)view.findViewById(R.id.doneBtn);
		locationBtn=(Button)view.findViewById(R.id.locationBtn);
		addrName=(EditText)view.findViewById(R.id.addrName);
		addrTel=(EditText)view.findViewById(R.id.addrTel);
		addrDistrict=(EditText)view.findViewById(R.id.addrDistrict);
		addrAddr=(EditText)view.findViewById(R.id.addrAddr);
		//
	//	spinnerProvince = (Spinner)view.findViewById(R.id.spinner_province);
	//	spinnerCity = (Spinner)view.findViewById(R.id.spinner_city);
	//	spinnerDistrict = (Spinner)view.findViewById(R.id.spinner_district);

	//	spinnerProvince.setOnItemSelectedListener(this);
	//	spinnerCity.setOnItemSelectedListener(this);
	//	spinnerDistrict.setOnItemSelectedListener(this);
		ma=(MainActivity)this.getActivity();
		initFunction();
		if(StaticValueClass.isAfterKitKat)
        	view.setPadding(0, StaticValueClass.statusBarHeight, 0, 0);
		return view;
	//	return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		if(addrRecord!=null){
			addrName.setText(addrRecord.name);
			addrTel.setText(addrRecord.tel);
			addrAddr.setText(addrRecord.address);
		} else clearInput();
	//	initQuery();
		if(StaticValueClass.cProvience!=null){
			addrDistrict.setText(StaticValueClass.cProvience+" "+StaticValueClass.cCity+" "+StaticValueClass.cDistrict);
		}
	}

	
	
	
	public void setGameBonusRecord(GameBonusRecord rd){
		record=rd;
	}


	public void setAddressRecord(BuyerAddressRecord record){
		addrRecord=record;
	} 
	
	private void initFunction(){
		doneBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(addrName.getText().toString().compareTo("")==0||addrTel.getText().toString().compareTo("")==0
						||addrAddr.getText().toString().compareTo("")==0){
					Toast.makeText(getActivity(), "请填入必要信息！", Toast.LENGTH_SHORT).show();
					return;
				};
				
				StaticValueClass.removeKeyboard(getActivity(), v);
				initDialog();
	
			}
		});
		addrDistrict.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				initDistrictBuilder();
			}
		});
		
		locationBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ma.reLocation(addrDistrict);
			}
		});
		
	}
	
	private void initQuery() {
		// 设置行政区划查询监听
		DistrictSearch districtSearch = new DistrictSearch(ma);
		districtSearch.setOnDistrictSearchListener(this);
		// 异步查询行政区
		districtSearch.searchDistrictAnsy();

		// 查询中国的区划
		DistrictSearchQuery query = new DistrictSearchQuery("中国",
				DistrictSearchQuery.KEYWORDS_COUNTRY, 0);
		selectedLevel = DistrictSearchQuery.KEYWORDS_COUNTRY;
		districtSearch.setQuery(query);
		Log.d("**choose district", "initQuery");
	}
	
	private void initDialog(){
		
		checkBuilder=new AlertDialog.Builder(getContext());
	    alertDialog=checkBuilder.create();
	    alertDialog.show();
	    final String addressInfo=addrDistrict.getText().toString()+"  "+addrAddr.getText().toString();
	    Window window=alertDialog.getWindow();
	    window.setContentView(R.layout.address_check_dialog);
	    TextView textName=(TextView)window.findViewById(R.id.textName);
	    TextView textTel=(TextView)window.findViewById(R.id.textTel);
	    TextView textAddr=(TextView)window.findViewById(R.id.textAddr);
	    textName.setText(addrName.getText().toString());
	    textTel.setText(addrTel.getText().toString());
	    textAddr.setText(addressInfo);
	    reFillBtn=(Button)window.findViewById(R.id.reFillBtn);
		confirmBtn=(Button)window.findViewById(R.id.confirmBtn);
	    WindowManager.LayoutParams lp=window.getAttributes();
	    lp.width=StaticValueClass.screenWidth*62/72;
	    lp.height=StaticValueClass.screenWidth*46/72;
	    window.setAttributes(lp);
	    /*
	    RelativeLayout.LayoutParams params1=(RelativeLayout.LayoutParams)reFillBtn.getLayoutParams();
	    RelativeLayout.LayoutParams params2=(RelativeLayout.LayoutParams)confirmBtn.getLayoutParams();
	    params1.width=params2.width=StaticValueClass.screenWidth*16/72;
	    params1.height=params2.height=StaticValueClass.screenWidth*7/72;
	    params1.topMargin=params2.topMargin=StaticValueClass.screenWidth*56/720;
	    params1.leftMargin=params2.rightMargin=StaticValueClass.screenWidth/6;
	    */
	    reFillBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				alertDialog.dismiss();
			}
		});
	    
	    confirmBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				alertDialog.dismiss();
				
				addrRecord=new BuyerAddressRecord(addrName.getText().toString(),addrTel.getText().toString(),
						addressInfo);
				
				StaticValueClass.currentBuyer.addressRecords.add(0, addrRecord);
				if(StaticValueClass.currentBuyer.addressRecords.size()>3){
					StaticValueClass.currentBuyer.addressRecords.remove(3);
				}
				
				if(StaticValueClass.currentBuyer.addressRecords.size()==1){
					// direct create new one.
					record.setAddresInfo(addrRecord);
					
				}else{
					// create new one and select.
				}
				clearInput();
				ma.onBackPressed();
				/*

				if(expressAddressAdapter.getSelect()==-1){
					((MainActivity)ChooseAddressFragment.this.getActivity()).updateBuyerInfo();
				}
				((MainActivity)ChooseAddressFragment.this.getActivity()).onBackPressed();
				*/
			}
		});
	  
	}
	private void initDistrictBuilder(){
		if(districtDialog!=null){
			districtTitle.setText("加载中..");
			districtListView.setAdapter(null);
			
			districtDialog.show();
		//	initQuery();
		}else{
			districtBuilder=new AlertDialog.Builder(getContext());
			districtDialog=districtBuilder.create();
			districtDialog.show();
			Window window=districtDialog.getWindow();
			window.setContentView(R.layout.choose_district_dialog);
			districtTitle=(TextView)window.findViewById(R.id.districtTitle);
			districtListView=(ListView)window.findViewById(R.id.districtListView);
			districtListView.setCacheColorHint(0);
		//	districtListView.setOnItemSelectedListener(this);
			districtListView.setOnItemClickListener(this);
			WindowManager.LayoutParams lp=window.getAttributes();
			lp.width=StaticValueClass.screenWidth*62/72;
		    lp.height=StaticValueClass.screenWidth*92/72;
		    window.setAttributes(lp);
		//    initQuery();
		}
		initQuery();
	}
	
	private void clearInput(){
	addrName.setText("");
	addrTel.setText("");
	addrAddr.setText("");
	}
	

	// 设置spinner视图
	private void setSpinnerView(List<DistrictItem> subDistrictList) {
		
		Log.d("**choose district", "setSpinnerView");
		List<String> nameList = new ArrayList<String>();
		if(subDistrictList != null && subDistrictList.size() > 0){
			for (int i = 0; i < subDistrictList.size(); i++) {
				nameList.add(subDistrictList.get(i).getName());
			}
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(ma,
					R.layout.textview_item, nameList);
			if (selectedLevel.equalsIgnoreCase(DistrictSearchQuery.KEYWORDS_COUNTRY)) {
				provinceList = subDistrictList;
				districtTitle.setText("选择省份");
			//	spinnerProvince.setAdapter(adapter);
			}

			if (selectedLevel
					.equalsIgnoreCase(DistrictSearchQuery.KEYWORDS_PROVINCE)) {
				cityList = subDistrictList;
				districtTitle.setText("选择城市");
			//	spinnerCity.setAdapter(adapter);
			}

			if (selectedLevel.equalsIgnoreCase(DistrictSearchQuery.KEYWORDS_CITY)) {
				districtList = subDistrictList;
				//如果没有区县，将区县说明置空
				if(null == nameList || nameList.size() <= 0){
				//	tv_districtInfo.setText("");
				}
				districtTitle.setText("选择区县");
			//	spinnerDistrict.setAdapter(adapter);
			}
			districtListView.setAdapter(adapter);
		} else {
			List<String> emptyNameList = new ArrayList<String>();
			ArrayAdapter<String> emptyAdapter = new ArrayAdapter<String>(ma,
					android.R.layout.simple_spinner_item, emptyNameList);
			if (selectedLevel.equalsIgnoreCase(DistrictSearchQuery.KEYWORDS_COUNTRY)) {
			//	spinnerProvince.setAdapter(emptyAdapter);
			//	spinnerCity.setAdapter(emptyAdapter);
			//	spinnerDistrict.setAdapter(emptyAdapter);
			//	tv_provinceInfo.setText("");
			//	tv_cityInfo.setText("");
			//	tv_districtInfo.setText("");
				addrDistrict.setText("");
			}

			if (selectedLevel
					.equalsIgnoreCase(DistrictSearchQuery.KEYWORDS_PROVINCE)) {
		//		spinnerCity.setAdapter(emptyAdapter);
		//		spinnerDistrict.setAdapter(emptyAdapter);
		//		tv_cityInfo.setText("");
		//		tv_districtInfo.setText("");
				addrDistrict.setText(cProvince);
			}
			
			if (selectedLevel
					.equalsIgnoreCase(DistrictSearchQuery.KEYWORDS_CITY)) {
		//		spinnerDistrict.setAdapter(emptyAdapter);
		//		tv_districtInfo.setText("");
				addrDistrict.setText(cProvince+" "+cCity);
			}
			districtDialog.dismiss();
		//	Toast.makeText(getContext(), "网络不佳，请重试！", Toast.LENGTH_SHORT).show();
		}
	}


	/**
	 * 查询下级区划
	 * 
	 * @param districtItem
	 *            要查询的区划对象
	 */
	private void querySubDistrict(DistrictItem districtItem) {
	//	Log.d("**choose district", "initQuery");
		DistrictSearch districtSearch = new DistrictSearch(
				ma);
		districtSearch.setOnDistrictSearchListener(this);
		// 异步查询行政区
		districtSearch.searchDistrictAnsy();
		DistrictSearchQuery query = new DistrictSearchQuery(
				districtItem.getName(), districtItem.getLevel(), 0);
		districtSearch.setQuery(query);
		Log.d("**choose district", "querySubDistrict");
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		DistrictItem districtItem = null;
		/*
		switch (parent.getId()) {
		case R.id.spinner_province:
			districtItem = provinceList.get(position);
			selectedLevel = DistrictSearchQuery.KEYWORDS_PROVINCE;
	//		tv_provinceInfo.setText(getDistrictInfoStr(districtItem));
			break;
		case R.id.spinner_city:
			selectedLevel = DistrictSearchQuery.KEYWORDS_CITY;
			districtItem = cityList.get(position);
		//	tv_cityInfo.setText(getDistrictInfoStr(districtItem));
			break;
		case R.id.spinner_district:
			selectedLevel = DistrictSearchQuery.KEYWORDS_DISTRICT;
			districtItem = districtList.get(position);
		//	tv_districtInfo.setText(getDistrictInfoStr(districtItem));
			break;
		default:
			break;
		} */
		
		if(selectedLevel.compareTo(DistrictSearchQuery.KEYWORDS_COUNTRY)==0){
			districtItem = provinceList.get(position);
			cProvince=districtItem.getName();
			selectedLevel = DistrictSearchQuery.KEYWORDS_PROVINCE;
		}else if(selectedLevel.compareTo(DistrictSearchQuery.KEYWORDS_PROVINCE)==0){
			selectedLevel = DistrictSearchQuery.KEYWORDS_CITY;
			districtItem = cityList.get(position);
			cCity=districtItem.getName();
		} else if(selectedLevel.compareTo(DistrictSearchQuery.KEYWORDS_CITY)==0){
			selectedLevel = DistrictSearchQuery.KEYWORDS_DISTRICT;
			districtItem = districtList.get(position);
			cDistrict=districtItem.getName();
		} else if(selectedLevel.compareTo(DistrictSearchQuery.KEYWORDS_DISTRICT)==0){
			addrDistrict.setText(cProvince+" "+cCity+" "+cDistrict);
			districtDialog.dismiss();
		}

		if (districtItem != null) {
			currentDistrictItem = districtItem;
			// 先查缓存如果缓存存在则直接从缓存中查找，无需再执行查询请求
			List<DistrictItem> cache = subDistrictMap.get(districtItem
					.getAdcode());
			if (null != cache) {
				setSpinnerView(cache);
			} else {
				querySubDistrict(districtItem);
			}
		}
	}




	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onDistrictSearched(DistrictResult result) {
		// TODO Auto-generated method stub
		Log.d("**choose district", "onDistrictSearched");
		List<DistrictItem> subDistrictList  = null;
		if (result != null) {
			if (result.getAMapException().getErrorCode() == 1000) {

				List<DistrictItem> district = result.getDistrict();
                /*
				if (!isInit) {
					isInit = true;
					currentDistrictItem = district.get(0);
				//	tv_countryInfo.setText(getDistrictInfoStr(currentDistrictItem));
				} */
				
				currentDistrictItem = district.get(0);

				// 将查询得到的区划的下级区划写入缓存
				for (int i = 0; i < district.size(); i++) {
					DistrictItem districtItem = district.get(i);
					subDistrictMap.put(districtItem.getAdcode(),
							districtItem.getSubDistrict());
				}
				// 获取当前区划的下级区划列表
				subDistrictList = subDistrictMap
						.get(currentDistrictItem.getAdcode());
			} else {
		//		ToastUtil.showerror(this, result.getAMapException().getErrorCode());
			}
		}
		setSpinnerView(subDistrictList);
		
	}



	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		DistrictItem districtItem = null;
		
		if(selectedLevel.equalsIgnoreCase(DistrictSearchQuery.KEYWORDS_COUNTRY)){
			districtItem = provinceList.get(position);
			cProvince=districtItem.getName();
			selectedLevel = DistrictSearchQuery.KEYWORDS_PROVINCE;
		}else if(selectedLevel.equalsIgnoreCase(DistrictSearchQuery.KEYWORDS_PROVINCE)){
			selectedLevel = DistrictSearchQuery.KEYWORDS_CITY;
			districtItem = cityList.get(position);
			cCity=districtItem.getName();
		} else if(selectedLevel.equalsIgnoreCase(DistrictSearchQuery.KEYWORDS_CITY)){
			selectedLevel = DistrictSearchQuery.KEYWORDS_DISTRICT;
			districtItem = districtList.get(position);
			cDistrict=districtItem.getName();
			addrDistrict.setText(cProvince+" "+cCity+" "+cDistrict);
			districtDialog.dismiss();
			return;
		} else if(selectedLevel.compareTo(DistrictSearchQuery.KEYWORDS_DISTRICT)==0){
		//	addrDistrict.setText(cProvince+" "+cCity+" "+cDistrict);
		///	districtDialog.dismiss();
		}

		if (districtItem != null) {
			currentDistrictItem = districtItem;
			// 先查缓存如果缓存存在则直接从缓存中查找，无需再执行查询请求
			List<DistrictItem> cache = subDistrictMap.get(districtItem
					.getAdcode());
			if (null != cache) {
				setSpinnerView(cache);
			} else {
				querySubDistrict(districtItem);
			}
		}
	}
}
