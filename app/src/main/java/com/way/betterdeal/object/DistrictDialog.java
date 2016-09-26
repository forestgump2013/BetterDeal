package com.way.betterdeal.object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amap.api.services.district.DistrictItem;
import com.amap.api.services.district.DistrictResult;
import com.amap.api.services.district.DistrictSearch;
import com.amap.api.services.district.DistrictSearchQuery;
import com.amap.api.services.district.DistrictSearch.OnDistrictSearchListener;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class DistrictDialog extends AlertDialog implements 
		OnDistrictSearchListener,OnItemClickListener{
	
	Context ct;
	TextView districtTitle;
	ListView districtListView;
	
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
	
	String cProvince,cCity,cDistrict;
	int demandLevel;
	
	public static interface DistrictListener{
		public void districtResult(String str);
	}
	DistrictListener dListener;
	protected DistrictDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		ct=context;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		initQuery();
		super.show();
		Window window=this.getWindow();
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
		
	}
	
	public void setDemandLevel(int le){
		demandLevel=le;
	}
	
	public void setDistrictListener(DistrictListener listener){
		dListener=listener;
	}
	
	private void initQuery() {
		// 设置行政区划查询监听
		DistrictSearch districtSearch = new DistrictSearch(ct);
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
	
	// 设置spinner视图
	private void setSpinnerView(List<DistrictItem> subDistrictList) {
		
		Log.d("**choose district", "setSpinnerView");
		List<String> nameList = new ArrayList<String>();
		if(subDistrictList != null && subDistrictList.size() > 0){
			for (int i = 0; i < subDistrictList.size(); i++) {
				nameList.add(subDistrictList.get(i).getName());
			}
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(ct,
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
			ArrayAdapter<String> emptyAdapter = new ArrayAdapter<String>(ct,
					android.R.layout.simple_spinner_item, emptyNameList);
			if (selectedLevel.equalsIgnoreCase(DistrictSearchQuery.KEYWORDS_COUNTRY)) {
			//	spinnerProvince.setAdapter(emptyAdapter);
			//	spinnerCity.setAdapter(emptyAdapter);
			//	spinnerDistrict.setAdapter(emptyAdapter);
			//	tv_provinceInfo.setText("");
			//	tv_cityInfo.setText("");
			//	tv_districtInfo.setText("");
			///	addrDistrict.setText("");
			}

			if (selectedLevel
					.equalsIgnoreCase(DistrictSearchQuery.KEYWORDS_PROVINCE)) {
		//		spinnerCity.setAdapter(emptyAdapter);
		//		spinnerDistrict.setAdapter(emptyAdapter);
		//		tv_cityInfo.setText("");
		//		tv_districtInfo.setText("");
			///	addrDistrict.setText(cProvince);
			}
			
			if (selectedLevel
					.equalsIgnoreCase(DistrictSearchQuery.KEYWORDS_CITY)) {
		//		spinnerDistrict.setAdapter(emptyAdapter);
		//		tv_districtInfo.setText("");
			///	addrDistrict.setText(cProvince+" "+cCity);
			}
			this.dismiss();
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
				ct);
		districtSearch.setOnDistrictSearchListener(this);
		// 异步查询行政区
		districtSearch.searchDistrictAnsy();
		DistrictSearchQuery query = new DistrictSearchQuery(
				districtItem.getName(), districtItem.getLevel(), 0);
		districtSearch.setQuery(query);
		Log.d("**choose district", "querySubDistrict");
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
			if(demandLevel==2){
				dListener.districtResult(cProvince+" "+cCity);
				this.dismiss();
			}
		} else if(selectedLevel.equalsIgnoreCase(DistrictSearchQuery.KEYWORDS_CITY)){
			selectedLevel = DistrictSearchQuery.KEYWORDS_DISTRICT;
			districtItem = districtList.get(position);
			cDistrict=districtItem.getName();
		///	addrDistrict.setText(cProvince+" "+cCity+" "+cDistrict);
			this.dismiss();
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
	
	

}
