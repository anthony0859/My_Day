package com.myday.map;




import com.baidu.location.*;
import com.myday.albums.AlbumFragment;

import com.myday.database.MyDayDatabase;
import com.myday.pictures.Photo;

import android.util.Log;
import android.widget.TextView;
import android.app.Application;
import android.os.Process;
import android.os.Vibrator;

public class Location extends Application {

	public LocationClient mLocationClient = null;
	private String mData;  
	public MyLocationListenner myListener = new MyLocationListenner();
	public TextView mTv;
	public NotifyLister mNotifyer=null;
	public Vibrator mVibrator01;
	public static String TAG = "LocTestDemo";
	StringBuffer myLoc = new StringBuffer(256);
	StringBuffer sb;
	public static double latitude;
	public static double longitude;
	@Override
	public void onCreate() {
		mLocationClient = new LocationClient( this );

		mLocationClient.registerLocationListener( myListener );

		
		super.onCreate(); 
		Log.d(TAG, "... Application onCreate... pid=" + Process.myPid());
	}
	
	/**
	 * 显示字符串
	 * @param str
	 */
	public void logMsg(String str) {
		try {
			mData = str;
			if ( mTv != null )
				mTv.setText(mData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 监听函数，又新位置的时候，格式化成字符串，输出到屏幕中
	 */
	public class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				{System.out.println("location==null");return ;}
			StringBuffer sb = new StringBuffer(256);
			//sb.append("time : ");
			//sb.append(location.getTime());
			//sb.append("\nerror code : ");
			//sb.append(location.getLocType());
			//sb.append("latitude:");
			sb.append(location.getLatitude());
			latitude=location.getLatitude();
			Map_Activity.lat=latitude;
			sb.append(":");
			sb.append(location.getLongitude());
			longitude=location.getLongitude();
			Map_Activity.lon=longitude;
			//sb.append("\nradius : ");
			//sb.append(location.getRadius());
//			if (location.getLocType() == BDLocation.TypeGpsLocation){
//				sb.append("\nspeed : ");
//				sb.append(location.getSpeed());
//				sb.append("\nsatellite : ");
//				sb.append(location.getSatelliteNumber());
//			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
//				sb.append("\naddr : ");
//				sb.append(location.getAddrStr());
//			}
			//sb.append("\nsdk version : ");
			//sb.append(mLocationClient.getVersion());
			//sb.append("\nisCellChangeFlag : ");
			//sb.append(location.isCellChangeFlag());
			myLoc=sb;
			//logMsg(sb.toString());
			//Log.i(TAG, sb.toString());
			//System.out.println("in onReceiveLocation "+sb.toString());
			AlbumFragment.locationInfo=location.getAddrStr();
			AlbumFragment.pointInfo=sb.toString();
			System.out.println("location.getAddrStr() "+AlbumFragment.locationInfo+"map_ac:lat"+Map_Activity.lat+",lon"+Map_Activity.lon);
//			if(AlbumFragment.locationInfo!=null)
//				{
//				
//					//AlbumFragment.photo.setLocation(AlbumFragment.locationInfo);
//					
//					return;
//				}
			//写入数据库信息
		}
		
		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null){
				return ; 
			}
			 sb = new StringBuffer(256);
			sb.append("Poi time : ");
			sb.append(poiLocation.getTime());
			sb.append("\nerror code : "); 
			sb.append(poiLocation.getLocType());
			sb.append("\nlatitude : ");
			sb.append(poiLocation.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(poiLocation.getLongitude());
			sb.append("\nradius : ");
			sb.append(poiLocation.getRadius());
			if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation){
				sb.append("\naddr : ");
				sb.append(poiLocation.getAddrStr());
			} 
			if(poiLocation.hasPoi()){
				sb.append("\nPoi:");
				sb.append(poiLocation.getPoi());
			}else{				
				sb.append("noPoi information");
			}
			logMsg(sb.toString());
		}
	}
	public StringBuffer getLocationInfo()
	{
		return sb;
	}
	public class NotifyLister extends BDNotifyListener{
		public void onNotify(BDLocation mlocation, float distance){
			mVibrator01.vibrate(1000);
		}
	}
}