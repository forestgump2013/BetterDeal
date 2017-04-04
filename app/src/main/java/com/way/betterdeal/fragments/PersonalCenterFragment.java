package com.way.betterdeal.fragments;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;
import com.way.betterdeal.adapters.SimpleAdapter;
import com.way.betterdeal.view.CircleImageView;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class PersonalCenterFragment extends Fragment implements ConfigrationFragment.ConfigureActionListener {
	
	MainActivity ma;
	View view;


	ScrollView scrollView1;
	CircleImageView headIcon;
	Button registerBtn,loginBtn,inviteFriendsBtn;
	TextView buyerName,personalSign;
	LinearLayout linearlayout1;
	SimpleAdapter simpleAdapter;
	ListView listView;
	Uri imageUri,tmpImageUri;
	String mAlbumPicturePath;
	Handler handler;
	
	//常量定义
		public static final int TAKE_A_PICTURE = 10;
		public static final int SELECT_A_PICTURE = 20;
		public static final int SET_PICTURE = 30;
		public static final int SET_ALBUM_PICTURE_KITKAT = 40;
		public static final int SELECET_A_PICTURE_AFTER_KIKAT = 50;
	
	public PersonalCenterFragment(){

	}

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.d("PersonalCenterFragment", "onCreateView");
		ma=(MainActivity)this.getActivity();
		imageUri=Uri.parse("file://"+this.getContext().getExternalFilesDir("tt").getPath()+"/head_icon.jpg");
		tmpImageUri=Uri.parse("file://"+this.getContext().getExternalFilesDir("tt").getPath()+"/temp_head_icon.jpg");
		view=inflater.inflate(R.layout.layout, container, false);
		scrollView1=(ScrollView)view.findViewById(R.id.scrollView1);
		registerBtn=(Button)view.findViewById(R.id.registerBtn);
		loginBtn=(Button)view.findViewById(R.id.loginBtn);
		buyerName=(TextView)view.findViewById(R.id.buyerName);
		personalSign=(TextView)view.findViewById(R.id.personalSign);
		inviteFriendsBtn=(Button)view.findViewById(R.id.inviteFriendsBtn);
		listView=(ListView)view.findViewById(R.id.listView);
		simpleAdapter=new SimpleAdapter(ma);
		initParams(view);
		buyerName.getPaint().setFakeBoldText(true);
		scrollView1.setVerticalScrollBarEnabled(false);
		initFunction();
		return view;
	}
	
	private void initParams(View parent){
		int scale=StaticValueClass.screenWidth;
		ImageView pinkCloud=(ImageView)parent.findViewById(R.id.pinkCloud);
		RelativeLayout.LayoutParams params1=(RelativeLayout.LayoutParams)pinkCloud.getLayoutParams();
		params1.height=scale*453/720;
		
		headIcon=(CircleImageView)parent.findViewById(R.id.headIconImageView);
		RelativeLayout.LayoutParams hParams=(RelativeLayout.LayoutParams)headIcon.getLayoutParams();
		hParams.topMargin=scale/15;
		hParams.width=hParams.height=scale*15/72;
		
		ImageView orderSheetImage=(ImageView)parent.findViewById(R.id.orderSheetImage);
		RelativeLayout.LayoutParams params2=(RelativeLayout.LayoutParams)orderSheetImage.getLayoutParams();
		params2.width=params2.height=scale*10/72;
		params2.topMargin=scale/24;
		
		ImageView coinsImage=(ImageView)parent.findViewById(R.id.coinsImage);
		RelativeLayout.LayoutParams params3=(RelativeLayout.LayoutParams)coinsImage.getLayoutParams();
		params3.width=params3.height=scale*10/72;
		params3.topMargin=scale/24;
		
		ImageView bonusImage=(ImageView)parent.findViewById(R.id.bonusImage);
		RelativeLayout.LayoutParams params4=(RelativeLayout.LayoutParams)bonusImage.getLayoutParams();
		params4.width=params4.height=scale*10/72;
		params4.topMargin=scale/24;
		//--------
		orderSheetImage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!StaticValueClass.currentBuyer.isLogined()){
					Toast.makeText(ma, "您未登录!", Toast.LENGTH_SHORT).show();
					return;
				}
				ma.loadFavouriteFragment(1);
			//	ma.loadBonusRecordFragment(2);
			}
		});
		coinsImage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!StaticValueClass.currentBuyer.isLogined()){
					Toast.makeText(ma, "您未登录!", Toast.LENGTH_SHORT).show();
					return;
				}
				ma.loadCoinFragment();
			}
		});
		
		bonusImage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!StaticValueClass.currentBuyer.isLogined()){
					Toast.makeText(ma, "您未登录!", Toast.LENGTH_SHORT).show();
					return;
				}
				ma.loadBonusRecordFragment(3);
			
			}
		});
		
		//-----------------------------------
		RelativeLayout.LayoutParams nParams1=(RelativeLayout.LayoutParams)buyerName.getLayoutParams();
		buyerName.setTypeface(Typeface.createFromAsset(ma.getAssets(),"fonts/huagang_girl.ttf"));
	//	nParams1.topMargin=scale*3/72;
		
		 if(StaticValueClass.isAfterKitKat){
			 LinearLayout linearLayout=(LinearLayout)parent.findViewById(R.id.linearLayout);
			 linearLayout.setPadding(0, StaticValueClass.statusBarHeight, 0, 0);
		 }
		linearlayout1=(LinearLayout)parent.findViewById(R.id.linearLayout1);
		GradientDrawable dividerDrawable;//=new GradientDrawable();
 		dividerDrawable=(GradientDrawable)ma.getResources().getDrawable(R.drawable.vertical_divider);
 		dividerDrawable.setSize(scale*6/80, 10);
 	//	RectShape rectShape=new RectShape(); 
 	//	ShapeDrawable shapeDrawable=new ShapeDrawable(rectShape);
 	//	shapeDrawable.getBounds().set(0, 0, 20, 20);
 		linearlayout1.setDividerDrawable(dividerDrawable); 
 		
 	    LinearLayout.LayoutParams bParams1=(LinearLayout.LayoutParams)loginBtn.getLayoutParams();
 	    LinearLayout.LayoutParams bParams2=(LinearLayout.LayoutParams)registerBtn.getLayoutParams();
    	LinearLayout.LayoutParams bParams3=(LinearLayout.LayoutParams)inviteFriendsBtn.getLayoutParams();
 	    bParams1.width=bParams2.width=scale*14/72;
 	    bParams1.height=bParams2.height=scale*5/72;
 	    bParams1.topMargin=bParams2.topMargin=scale*3/72;
 	    bParams3.width=scale*315/720;
 	    bParams3.height=scale*95/720;
 	    //----------------------------------------
 	   LinearLayout linearLayout2=(LinearLayout)parent.findViewById(R.id.linearLayout2);
		GradientDrawable dividerDrawable2;//=new GradientDrawable();
		dividerDrawable2=(GradientDrawable)ma.getResources().getDrawable(R.drawable.vertical_divider);
		dividerDrawable2.setSize(scale*10/72, 10);
		linearLayout2.setDividerDrawable(dividerDrawable2); 
	}
	
	private void initFunction(){
		
		refreashLoginStatus();
		listView.setAdapter(simpleAdapter);
		listView.setCacheColorHint(0);
		/*
		listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				switch(position){
					case  0:
						ma.loadFavouriteFragment(2);
						break;
					case 3:
						ma.loadConfigrationFragment();
						break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});  */

		listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch(position){
				case  0:
					if(!StaticValueClass.currentBuyer.isLogined()){
						Toast.makeText(ma, "您未登录!", Toast.LENGTH_SHORT).show();
						return;
					}
					ma.loadFootTraceFragment();
					break;
					case  1:
						ma.loadHelpFragment();
						break;
					case  2:
						ma.loadFeedBackFragment();
						break;
				case 3:
					ma.loadConfigrationFragment();
					break;
				}
			}
			
		});
		loginBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ma.loadLoginFragment(false);
			}
		});
		registerBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ma.loadRegisterFragment(1,false);
			}
		});
		headIcon.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*
				if(!ma.isLogined()){
					Toast.makeText(ma, "您未登录!", Toast.LENGTH_SHORT).show();
					return;
				} */
				ma.loadBuyer_Edit_fragment();
				/*
				new AlertDialog.Builder(ma).setTitle("设置头像")
				.setNegativeButton("相册", new DialogInterface.OnClickListener() {
				//	@Override
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						if (StaticValueClass.isAfterKitKat) {
							selectImageUriAfterKikat();
						} else {
							cropImageUri();
						} 
					//	Crop.pickImage( Buyer_fragment.this);
					}
				}).setPositiveButton("相机", new DialogInterface.OnClickListener() {
			//		@Override
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						File file = new File(tmpImageUri.getPath());
						//创建父目录
						if (!file.getParentFile().exists())
							file.getParentFile().mkdirs();
						Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
						intent.putExtra(MediaStore.EXTRA_OUTPUT,
								tmpImageUri);
						startActivityForResult(intent, TAKE_A_PICTURE);
					//	Log.i("zou", "TAKE_A_PICTURE");
					}
				}).show(); */
			}
		});
		
	}
	
	public void loginOut(){
		headIcon.setImageResource(R.mipmap.defaul_head_icon);
		refreashLoginStatus();
	}
	
	/**
	 *  <br>功能简述:4.4以上裁剪图片方法实现---------------------- 相册
	 * <br>功能详细描述:
	 * <br>注意:
	 */
	@TargetApi(Build.VERSION_CODES.KITKAT)
	private void selectImageUriAfterKikat() {
		Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("image/*");
		startActivityForResult(intent, SELECET_A_PICTURE_AFTER_KIKAT);
	}
	
	/** <br>功能简述:裁剪图片方法实现---------------------- 相册
	 * <br>功能详细描述:
	 * <br>注意:
	 */
	private void cropImageUri() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setType("image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("scale", false);
		intent.putExtra("return-data", true);
		intent.putExtra("circleCrop", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT,
				imageUri);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true); // no face detection
		startActivityForResult(intent, SELECT_A_PICTURE);
		
	}
	
	/** 
	 * <br>功能简述:裁剪图片方法实现----------------------相机
	 * <br>功能详细描述:
	 * <br>注意:
	 * @param uri
	 */
	private void cameraCropImageUri(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/jpeg");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("scale", true);
		intent.putExtra("scaleUpIfNeeded", true);
		//		if (mIsKitKat) {
		//			intent.putExtra("return-data", true);
		//			intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		//		} else {
		intent.putExtra("return-data", false);
		File file = new File(imageUri.getPath());
		//创建父目录
		if (!file.getParentFile().exists())
			file.getParentFile().mkdirs();
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		//		}
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true);
		startActivityForResult(intent, SET_PICTURE);
	}
	
	/** 
	 * <br>功能简述:
	 * <br>功能详细描述:
	 * <br>注意:
	 * @param uri
	 * @return
	 */
	private Bitmap decodeUriAsBitmap(Uri uri) {
		Bitmap bitmap = null;
		try {
		//	bitmap = BitmapFactory.decodeStream(ma.getContentResolver().openInputStream(uri));
			
			bitmap=MediaStore.Images.Media.getBitmap(ma.getContentResolver(), uri);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bitmap;
	}
	/** 
	 * <br>功能简述:4.4及以上获取图片的方法
	 * <br>功能详细描述:
	 * <br>注意:
	 * @param context
	 * @param uri
	 * @return
	 */
	@TargetApi(Build.VERSION_CODES.KITKAT)
	public static String getPath(final Context context, final Uri uri) {

		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

		// DocumentProvider
		if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
			// ExternalStorageProvider
			if (isExternalStorageDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				if ("primary".equalsIgnoreCase(type)) {
					return Environment.getExternalStorageDirectory() + "/" + split[1];
				}
			}
			// DownloadsProvider
			else if (isDownloadsDocument(uri)) {

				final String id = DocumentsContract.getDocumentId(uri);
				final Uri contentUri = ContentUris.withAppendedId(
						Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

				return getDataColumn(context, contentUri, null, null);
			}
			// MediaProvider
			else if (isMediaDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				Uri contentUri = null;
				if ("image".equals(type)) {
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				} else if ("video".equals(type)) {
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				} else if ("audio".equals(type)) {
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				}

				final String selection = "_id=?";
				final String[] selectionArgs = new String[] { split[1] };

				return getDataColumn(context, contentUri, selection, selectionArgs);
			}
		}
		// MediaStore (and general)
		else if ("content".equalsIgnoreCase(uri.getScheme())) {

			// Return the remote address
			if (isGooglePhotosUri(uri))
				return uri.getLastPathSegment();

			return getDataColumn(context, uri, null, null);
		}
		// File
		else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}

		return null;
	}

	public static String getDataColumn(Context context, Uri uri, String selection,
			String[] selectionArgs) {

		Cursor cursor = null;
		final String column = "_data";
		final String[] projection = { column };

		try {
			cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
					null);
			if (cursor != null && cursor.moveToFirst()) {
				final int index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(index);
			}
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return null;
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is Google Photos.
	 */
	public static boolean isGooglePhotosUri(Uri uri) {
		return "com.google.android.apps.photos.content".equals(uri.getAuthority());
	}
	
	/** 
	 * <br>功能简述: 4.4及以上改动版裁剪图片方法实现 --------------------相机
	 * <br>功能详细描述:
	 * <br>注意:
	 * @param uri
	 */
	private void cropImageUriAfterKikat(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/jpeg");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("scale", true);
		intent.putExtra("scaleUpIfNeeded", true);
		//		intent.putExtra("return-data", true);
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT,
				imageUri);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true);
		startActivityForResult(intent, SET_ALBUM_PICTURE_KITKAT);
	}
	
	private void refreashLoginStatus(){
		if(StaticValueClass.currentBuyer.isLogined()){
			linearlayout1.setVisibility(View.GONE);
			buyerName.setVisibility(View.VISIBLE);
			personalSign.setVisibility(View.VISIBLE);
			buyerName.setText(StaticValueClass.currentBuyer.nickName);
			personalSign.setText(StaticValueClass.currentBuyer.personalSign);
			ma.runOnUiThread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						Bitmap bitmap=MediaStore.Images.Media.getBitmap(ma.getContentResolver(), imageUri);
						if(bitmap!=null)
							headIcon.setImageBitmap(bitmap);
						//-----------
					
						
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
	        	
	        }); 
		}else{
			linearlayout1.post(new Runnable() {
				@Override
				public void run() {
					linearlayout1.setVisibility(View.VISIBLE);
					buyerName.setVisibility(View.GONE);
					personalSign.setVisibility(View.GONE);
					headIcon.setImageResource(R.mipmap.defaul_head_icon);
				}
			});

		}
	}
	
	public void updateHeadIcon(Drawable icon){
		if(headIcon!=null && icon!=null)
			headIcon.setImageDrawable(icon);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(handler==null) handler=new Handler();
		handler.post(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				scrollView1.fullScroll(View.FOCUS_UP);
			}
			
		});
		refreashLoginStatus();
		Log.d("PersonalCenterFragment", "onResume");
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		Log.d(StaticValueClass.logTag, "personal center  onHiddenChanged hidden:"+hidden);
		super.onHiddenChanged(hidden);
		if (!hidden){
			//check login info.
			Log.d(StaticValueClass.logTag, "personal center  onHiddenChanged update:"+StaticValueClass.currentBuyer.isNeedUpdate());
           if (StaticValueClass.currentBuyer.isNeedUpdate()){
			   refreashLoginStatus();
			   StaticValueClass.currentBuyer.setNeedUpdate(false);
		   }
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == android.app.Activity.RESULT_OK){
			Bitmap bitmap ;
			switch(requestCode){
			case SELECT_A_PICTURE:
				//= decodeUriAsBitmap(imageUri);
				bitmap=data.getParcelableExtra("data");
				headIcon.setImageBitmap(bitmap);
				StaticValueClass.uploadHeadIcon("http://www.qcygl.com/upload_headicon.php", bitmap, StaticValueClass.currentBuyer.tel+".jpg");
			    break;
			case TAKE_A_PICTURE:
				cameraCropImageUri(tmpImageUri);
				break;
			case SET_PICTURE:
				 bitmap = decodeUriAsBitmap(imageUri);
				//	bitmap=data.getParcelableExtra("data");
				headIcon.setImageBitmap(bitmap);
				StaticValueClass.uploadHeadIcon("http://www.qcygl.com/upload_headicon.php", bitmap, StaticValueClass.currentBuyer.tel+".jpg");
				break;
			case SELECET_A_PICTURE_AFTER_KIKAT:
				mAlbumPicturePath = getPath(ma, data.getData());
				cropImageUriAfterKikat(Uri.fromFile(new File(mAlbumPicturePath)));
			case SET_ALBUM_PICTURE_KITKAT:
				 bitmap = decodeUriAsBitmap(imageUri);
				headIcon.setImageBitmap(bitmap);
				StaticValueClass.uploadHeadIcon("http://www.qcygl.com/upload_headicon.php", bitmap, StaticValueClass.currentBuyer.tel+".jpg");
				
				
			}
		}
	}

	@Override
	public void logoutAction() {
		refreashLoginStatus();
	}



}
