package com.way.betterdeal.fragments;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

//import com.soundcloud.android.crop.Crop;
import com.way.betterdeal.MainActivity;
import com.way.betterdeal.R;
import com.way.betterdeal.StaticValueClass;
import com.way.betterdeal.adapters.PurchaseRecordAdapter;
import com.way.betterdeal.object.PurchaseRecord;
import com.way.betterdeal.view.CircleImageView;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
//import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Buyer_Edit_fragment extends Fragment implements InfoEditFragment.EditListener {
	MainActivity ma;
	View view;
	CircleImageView headIconImageView;
	Uri imageUri,tmpImageUri;
	String mAlbumPicturePath;
	
	//常量定义
	public static final int TAKE_A_PICTURE = 10;
	public static final int SELECT_A_PICTURE = 20;
	public static final int SET_PICTURE = 30;
	public static final int SET_ALBUM_PICTURE_KITKAT = 40;
	public static final int SELECET_A_PICTURE_AFTER_KIKAT = 50;
	
    Button backBtn,commitBtn;
	EditText nickName,birthDay,personalSign,address,telephone;
	ImageView locationImage;
	CheckBox sexCheckbox;
	InfoEditFragment infoEditFragment;
	DatePickerDialog birthDayDiag;
	String editInfo;
	int setDirect;

	
	public Buyer_Edit_fragment(){


	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,  Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.buyer_edit_fragment, container, false);
		backBtn=(Button)view.findViewById(R.id.backBtn);
		nickName=(EditText)view.findViewById(R.id.nickName);
		address=(EditText)view.findViewById(R.id.address);
		telephone=(EditText)view.findViewById(R.id.telephone);
		locationImage=(ImageView)view.findViewById(R.id.locationImage);
		birthDay=(EditText)view.findViewById(R.id.birthday);
		sexCheckbox=(CheckBox)view.findViewById(R.id.sexCheckbox);
		personalSign=(EditText)view.findViewById(R.id.personalSign);
	//	view.setBackgroundColor(Color.WHITE);
		ma=(MainActivity)this.getActivity();
		imageUri=Uri.parse("file://"+ma.getExternalFilesDir("tt").getPath()+"/head_icon.jpg");
		tmpImageUri=Uri.parse("file://"+ma.getExternalFilesDir("tt").getPath()+"/temp_head_icon.jpg");
		headIconImageView=(CircleImageView)view.findViewById(R.id.headIconImageView);

		headIconImageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
					}
				}).show();
			}
		});
		
		locationImage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				address.setTag("1");
				ma.reLocation(address);
			}
		});
		
		 
        
        initEditDirect();
        view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
        if(StaticValueClass.isAfterKitKat)
        	view.setPadding(0, StaticValueClass.statusBarHeight, 0, 0);
		return view;
	}
	
	private void initData(){
		
		if(StaticValueClass.cProvience!=null){
			if(StaticValueClass.cProvience.compareTo(StaticValueClass.cCity)==0){
				address.setText(StaticValueClass.cProvience.substring(0, StaticValueClass.cProvience.length()-1)+" "+StaticValueClass.cCity);
			} else address.setText(StaticValueClass.cProvience+" "+StaticValueClass.cCity);
		}
		//.
		ma.runOnUiThread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Bitmap bitmap=MediaStore.Images.Media.getBitmap(ma.getContentResolver(), imageUri);
					if(bitmap!=null)
						headIconImageView.setImageBitmap(bitmap);
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
        	
        }); 
	}
	
	private void initEditDirect(){
		birthDay.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			//	loadInfoEditFragment(1);
				showDateDialog();
			}
		});
		nickName.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setDirect=2;
				loadInfoEditFragment(2);
			}
		});
		personalSign.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setDirect=3;
				loadInfoEditFragment(3);
			}
		});
		telephone.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ma.loadMobileBandFragment();
			}
		});
		//
		RelativeLayout.LayoutParams params1=(RelativeLayout.LayoutParams)sexCheckbox.getLayoutParams();
		params1.width=StaticValueClass.screenWidth*100/720;
		params1.height=StaticValueClass.screenWidth*48/720;
		Bitmap backmark=BitmapFactory.decodeResource(ma.getResources(), R.mipmap.expand_icon);
    	Drawable leftDrawable=new BitmapDrawable(StaticValueClass.getBackIcon(backmark));
    	leftDrawable.setBounds(0, 0, backmark.getWidth(), backmark.getHeight());
    //	backBtn.setCompoundDrawables(leftDrawable, null, null, null);
    	backBtn.setBackground(leftDrawable);
    	backBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ma.onBackPressed();
			}
		});
	}
	
	private void showDateDialog(){
		if(birthDayDiag==null){
			final Calendar cal=Calendar.getInstance();
			final DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {
				
				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear,
						int dayOfMonth) {
					// TODO Auto-generated method stub
			//		cal.set(Calendar.YEAR, year);
			//		cal.set(Calendar.MONTH, monthOfYear);
			//		cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				}
			};
			//DatePickerDialog.THEME_DEVICE_DEFAULT_LIGHT
			birthDayDiag=new DatePickerDialog(ma,DatePickerDialog.THEME_DEVICE_DEFAULT_LIGHT,listener,
					cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
			birthDayDiag.setTitle("设置生日");
			birthDayDiag.setButton2("完成", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						birthDay.setText(birthDayDiag.getDatePicker().getYear()+"/"+
						(birthDayDiag.getDatePicker().getMonth()+1)+"/"+birthDayDiag.getDatePicker().getDayOfMonth());
					}
				});
			birthDayDiag.setButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
		}
		birthDayDiag.show();
	}
	
	public void loadInfoEditFragment(int flag){
		if(infoEditFragment==null){
			infoEditFragment=new InfoEditFragment();
			infoEditFragment.setEditListener(this);
		} ;
		infoEditFragment.setDirect(flag);
		FragmentTransaction ft=ma.getSupportFragmentManager().beginTransaction();
	
		ft.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out);
		ft.replace(R.id.frontPage, infoEditFragment);
		ft.addToBackStack(null);
		ft.commit();
		ma.pushFragment();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		
		super.onResume();
		
		switch(setDirect){
			case 2:nickName.setText(editInfo);
					break;
			case 3:personalSign.setText(editInfo);
					break;
		}
		setDirect=0;
		initData();
		
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		
		if (requestCode == SELECT_A_PICTURE) {
			
			if (resultCode == android.app.Activity.RESULT_OK && null != data) {
				//				Log.i("zou", "4.4以下的");
				Bitmap bitmap ;
				bitmap=data.getParcelableExtra("data");
				headIconImageView.setImageBitmap(bitmap);
				StaticValueClass.uploadHeadIcon("http://www.qcygl.com/upload_headicon.php", bitmap, StaticValueClass.currentBuyer.tel+".jpg");
			} else if (resultCode == android.app.Activity.RESULT_CANCELED) {
				Toast.makeText(ma, "取消头像设置", Toast.LENGTH_SHORT).show();
			}
		} else if (requestCode == SELECET_A_PICTURE_AFTER_KIKAT) {
			if (resultCode == android.app.Activity.RESULT_OK && null != data) {
//				Log.i("zou", "4.4以上的");
				mAlbumPicturePath =StaticValueClass.getPath(ma, data.getData());
				cropImageUriAfterKikat(Uri.fromFile(new File(mAlbumPicturePath)));
			} else if (resultCode == android.app.Activity.RESULT_CANCELED) {
				Toast.makeText(ma, "取消头像设置", Toast.LENGTH_SHORT).show();
			}
		} else if (requestCode == SET_ALBUM_PICTURE_KITKAT) {
	//		Log.i("zou", "4.4以上上的 RESULT_OK");
			
			Bitmap bitmap = decodeUriAsBitmap(imageUri);
			headIconImageView.setImageBitmap(bitmap);
			StaticValueClass.uploadHeadIcon("http://www.qcygl.com/upload_headicon.php", bitmap, StaticValueClass.currentBuyer.tel+".jpg");
			
		} else if (requestCode == TAKE_A_PICTURE) {
			if (resultCode == android.app.Activity.RESULT_OK) {
				cameraCropImageUri(tmpImageUri);
			} else {
				Toast.makeText(ma, "取消头像设置", Toast.LENGTH_SHORT).show();
			}
		} else if (requestCode == SET_PICTURE) {
			Bitmap bitmap = null;
			if (resultCode == android.app.Activity.RESULT_OK && null != data) {
				bitmap = decodeUriAsBitmap(imageUri);
			//	bitmap=data.getParcelableExtra("data");
				headIconImageView.setImageBitmap(bitmap);
				StaticValueClass.uploadHeadIcon("http://www.qcygl.com/upload_headicon.php", bitmap, StaticValueClass.currentBuyer.tel+".jpg");
			} else if (resultCode == android.app.Activity.RESULT_CANCELED) {
				Toast.makeText(ma, "取消头像设置", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(ma, "设置头像失败", Toast.LENGTH_SHORT).show();
			}
		}
		
	}

	//---------------------------------------------------------------------
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
			intent.putExtra("return-data", false);
			File file = new File(imageUri.getPath());
			//创建父目录
			if (!file.getParentFile().exists())
				file.getParentFile().mkdirs();
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
			intent.putExtra("noFaceDetection", true);
			startActivityForResult(intent, SET_PICTURE);
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


		

	
	public void setHeadIcon(Drawable icon){
		headIconImageView.setImageDrawable(icon);
	}



	@Override
	public void getEditInfo(String info) {
		// TODO Auto-generated method stub
		Log.d("***Buyer_edit_fragment", "getEditInfo:"+info);
		editInfo=info;
	}

	
}
