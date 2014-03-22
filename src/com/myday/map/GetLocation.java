package com.myday.map;

import java.util.regex.Pattern;


import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class GetLocation extends Service {
	String location;
	private LocationClient mLocClient;
	public GetLocation() {
		super();
		// TODO Auto-generated constructor stub
		mLocClient = ((Location)getApplication()).mLocationClient;
	}
	public String getLocation() {
		
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	public void start()
	{
		setLocationOption();
		mLocClient.start();
		mLocClient.requestLocation();	
	}
	public void stop()
	{
		mLocClient.stop();
	}
	//设置相关参数
		private void setLocationOption(){
			LocationClientOption option = new LocationClientOption();
			//option.setOpenGps(mGpsCheck.isChecked());				//打开gps
			//option.setCoorType(mCoorEdit.getText().toString());		//设置坐标类型
			option.setServiceName("com.baidu.location.service_v2.9");
			//返回地址信息
			option.setAddrType("all");
	
			option.setScanSpan(3000);
			

			//.setPoiNumber(10);
			//option.disableCache(true);		
			mLocClient.setLocOption(option);
		}

		protected boolean isNumeric(String str) {   
			Pattern pattern = Pattern.compile("[0-9]*");   
			return pattern.matcher(str).matches();   
		}
		@Override
		public IBinder onBind(Intent arg0) {
			// TODO Auto-generated method stub
			return null;
		}  
	

}
