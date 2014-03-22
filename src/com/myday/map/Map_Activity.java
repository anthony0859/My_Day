package com.myday.map;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MKMapViewListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.BDNotifyListener;
import com.example.my__day.R;

import com.myday.albums.SameLocationAlbumActivity;

import com.myday.database.MyDayDatabase;
import com.myday.file.FileHandle;

public class Map_Activity extends Activity {
	
	BMapManager mBMapMan = null;
	MapView mMapView = null;
	MapController mMapController;
	private LocationClient mLocClient;
	/**
	 *  圆心经纬度坐标 
	 */
	int cLat = 39909230 ;
	int cLon = 116397428 ;
	// 存放overlayitem 
	public List<OverlayItem> mGeoList;
	public static double lat;
	public static double lon;
	List<String[]> list;//存放某个地方所照照片路径
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBMapMan=new BMapManager(this);
		mBMapMan.init("D7697FCE8DE7E90C1ED254662974EB0B3A7A4CFB",null);
		
		setContentView(R.layout.map);
		mMapView=(MapView)findViewById(R.id.bmapsView);
		mMapView.setBuiltInZoomControls(true);
		//mLocClient = ((Location)this.getApplication()).mLocationClient;
		
		 mMapController=mMapView.getController();
		 new Thread(new StartMapThread()).start();
		 
//		 while(true)
//		 {
//			 if(lat!=0&&lon!=0)
//				 break;
//			 try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		 }
//		 if(lat!=0&&lon!=0)
//		 {
//			  GeoPoint point=new GeoPoint((int) (lat*1E6), (int)(lon*1E6));
//			  mMapController.setCenter(point);
//				mMapController.setZoom(12);
//				mMapView.refresh();
//				initGeoList();
//		 }
//		 double[] points=MyDayDatabase.getLastGeopoint();
//		 if(points[0]!=0&&points[1]!=0){
//		GeoPoint point=new GeoPoint((int) (points[0]*1E6), (int)(points[1]*1E6));
//		//GeoPoint point=new GeoPoint((int)(39*1E6),(int) (116*1E6));
//		Toast.makeText(this, Location.latitude+","+Location.longitude,Toast.LENGTH_SHORT).show();
//		mMapController.setCenter(point);
//		mMapController.setZoom(12);
//		mMapView.refresh();
//		 }
		//initGeoList();
		
	}
	class StartMapThread implements Runnable
	{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			 while(true)
			 {
				 				 
				 if(lat!=0&&lon!=0)
				 {
					  GeoPoint point=new GeoPoint((int) (lat*1E6), (int)(lon*1E6));
					  mMapController.setCenter(point);
						mMapController.setZoom(12);
						mMapView.refresh();
						initGeoList();
						break;
				 }
				 try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				 
			 }
		}
		
	}
    public void initGeoList()
    {
    	mGeoList = new ArrayList<OverlayItem>();
    	List<String[]> list=MyDayDatabase.getHistoryLoc();
    	double latitude,lontitude;
    	String[]s;
    	Set<String> set=new HashSet<String>();
    	Drawable marker = getResources().getDrawable(R.drawable.icon_marka); //得到需要标在地图上的资源
    	for(int i=0;i<list.size();i++)
    	{
    		System.out.println("get loc list:"+list.get(i)[0]+","+list.get(i)[1]);
    		String temp=list.get(i)[1];
    		
    		if(!temp.equals("null"))
    		{
    			System.out.println("list.get(i)[1]!=null:"+list.get(i)[0]+","+list.get(i)[1]+",");
    			set.add(list.get(i)[1]);
    			
//	    		if(i==list.size()-1)
//	    		{
//	    			MapController mMapController=mMapView.getController();
//	    	    	GeoPoint point=new GeoPoint((int) latitude, (int)lontitude);
//	    			mMapController.setCenter(point);
//	    			mMapController.setZoom(12);
//	    		}
    		}
    	}

		for(String info:set)
		{
			s=info.split(":");
    		latitude=Double.parseDouble(s[0]);
    		lontitude=Double.parseDouble(s[1]);
    		System.out.println("for set "+latitude+","+lontitude);
    		GeoPoint p= new GeoPoint((int)(latitude*1000000),(int)(lontitude*1000000));
    		//p.setLatitudeE6((int)(latitude*1E6));
    		//p.setLongitudeE6((int)(lontitude*1E6));
    		//System.out.println(latitude*1E6+", *1000000="+latitude*1000000+" set get "+p.getLatitudeE6()+","+p.toString());
    		mGeoList.add(new OverlayItem(p, "P1", "point1"));
		}
    	OverItem overitem=new OverItem(marker, Map_Activity.this);
		overitem.setGeoList(mGeoList);
		for(int j=0;j<mGeoList.size();j++)
    		mMapView.getOverlays().add(overitem); //添加ItemizedOverlay实例到mMapView
		//Toast.makeText(this,"list size "+ mGeoList.size(), Toast.LENGTH_SHORT).show();
		mMapView.refresh();//刷新地图
    	//GeoPoint p1 = new GeoPoint((int) (mLat1 * 1E6), (int) (mLon1 * 1E6));
		//mGeoList.add(new OverlayItem(p1, "P1", "point1"));
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
	protected void onDestroy(){
	        mMapView.destroy();
	        if(mBMapMan!=null){
	                mBMapMan.destroy();
	                mBMapMan=null;
	        }
	        super.onDestroy();
	}
	
	@Override
	protected void onPause(){
	        mMapView.onPause();
	        if(mBMapMan!=null){
	                mBMapMan.stop();
	        }
	        super.onPause();
	}
	
	@Override
	protected void onResume(){
	        mMapView.onResume();
	        if(mBMapMan!=null){
	                mBMapMan.start();
	        }
	        super.onResume();
	}
	public class OverItem extends ItemizedOverlay<OverlayItem>{
		private List<OverlayItem> GeoList = new ArrayList<OverlayItem>();
		private Context mContext;
		PopupOverlay pop = null;
		private double mLat1 = 39.90923;//39.9022; // point1纬度
		private double mLon1 = 116.397428;//116.3822; // point1经度
		List<String> samePointPhotoRealPaths=new ArrayList<String>();
		List<String> samePointPhotoThumbPaths=new ArrayList<String>();
		public OverItem(Drawable marker, Context context) {
			super(marker);
			 
			this.mContext = context;
			pop = new PopupOverlay( mMapView,new PopupClickListener() {
				
				@Override
				public void onClickedPopup() {
					
					 //Toast.makeText(mContext, "click lati:"+lati+","+lont, Toast.LENGTH_SHORT).show();
//					 list=MyDayDatabase.searchPhotoFromPoint(lati, lont);
//					 System.out.println("after searchPhotoFromPoint "+lati+","+lont+" "+list.size());
					 samePointPhotoRealPaths=new ArrayList<String>();
					 samePointPhotoThumbPaths=new ArrayList<String>();
						for(String []s:list)
						{
							System.out.println("on tap "+s[0]+","+s[1]);
							String s2[]=s[0].split(" ");
							samePointPhotoRealPaths.add(FileHandle.albumpath+s2[0]+"/"+s[0]+".jpg");
						}
						samePointPhotoThumbPaths=FileHandle.realPathsToThumbPaths(samePointPhotoRealPaths);
						String t = "";
						for(String s:samePointPhotoThumbPaths)
						{
							t+=s+";";
						}
						Intent intent=new Intent();
						Bundle bundle=new Bundle();
						bundle.putStringArrayList("realpaths", (ArrayList<String>) samePointPhotoRealPaths);
						bundle.putStringArrayList("thumbpaths", (ArrayList<String>) samePointPhotoThumbPaths);
						
						intent.putExtras(bundle);
						intent.setClass(Map_Activity.this, SameLocationAlbumActivity.class);
						System.out.println("start activity ");
						Map_Activity.this.startActivity(intent);
						//pop.hidePop();
				}
			});
		    populate();
			
			// 用给定的经纬度构造GeoPoint，单位是微度 (度 * 1E6)
			//GeoPoint p1 = new GeoPoint((int) (mLat1 * 1E6), (int) (mLon1 * 1E6));
     		//GeoList.add(new OverlayItem(p1, "P1", "point1"));
			
			}


		@Override
		protected OverlayItem createItem(int arg0) {
			// TODO Auto-generated method stub
			return GeoList.get(arg0);
		}

		@Override
		public int size() {
			// TODO Auto-generated method stub
			return GeoList.size();
		}
		@Override
		// 处理当点击事件
		protected boolean onTap(int i) {
		
		
		//Toast.makeText(this.mContext, lati+","+lont,Toast.LENGTH_SHORT).show();
		
		
		//.makeText(Map_Activity.this, t,Toast.LENGTH_SHORT).show();
//		Album album=new Album();
//		album.setRealpaths(samePointPhotoRealPaths);
//		album.setThumbpaths(samePointPhotoThumbPaths);
	    lat=GeoList.get(i).getPoint().getLatitudeE6();
	    lon=GeoList.get(i).getPoint().getLongitudeE6();
		Drawable marker = this.mContext.getResources().getDrawable(R.drawable.pop);  //得到需要标在地图上的资源
		String lati=lat/1000000+"";
		String lont=lon/1000000+"";
		list=MyDayDatabase.searchPhotoFromPoint(lati, lont);
		BitmapDrawable bd = (BitmapDrawable) marker;
        Bitmap popbitmap = bd.getBitmap();
		 popbitmap = drawPopBitmap(list.size()+"张照片");
		 
//		 Bitmap b=Bitmap.createBitmap(100, 100, Config.ARGB_8888);
//		 Canvas canvas=new Canvas(b);
//			Paint paint=new Paint();
//			paint.setColor(Color.BLACK);
//			canvas.drawText(list.size()+"张照片", 0, 0, paint);
//			canvas.save(Canvas.ALL_SAVE_FLAG);
//			
//			canvas.restore();
	    pop.showPopup(popbitmap, GeoList.get(i).getPoint(), 32);
	    //System.out.println("click get "+GeoList.get(i).getPoint().getLatitudeE6());
		// int latspan = this.getLatSpanE6();
		// int lonspan = this.getLonSpanE6();
		
		super.onTap(i);
		return false;
		

//		return true;
		}
		public boolean onTap(GeoPoint pt, MapView mapView){
			if (pop != null){
				pop.hidePop();
			}
			super.onTap(pt,mapView);
			return false;
		}

		public List<OverlayItem> getGeoList() {
			return GeoList;
		}


		public void setGeoList(List<OverlayItem> geoList) {
			GeoList = geoList;
			populate(); //createItem(int)方法构造item。一旦有了数据，在调用其它方法前，首先调用这个方法
		}

	}
	public Bitmap drawPopBitmap(String text)
	{
		//Bitmap b=Bitmap.createBitmap(20, 20, Config.ARGB_8888);
		//this.mContext.getResources().getDrawable(R.drawable.pop);
		Bitmap b=BitmapFactory.decodeResource(getResources(), R.drawable.pop).copy(Bitmap.Config.ARGB_8888, true);
		//Bitmap b=Bitmap.createBitmap(bm);
		Canvas canvas=new Canvas(b);
		Paint paint=new Paint();
		paint.setTextSize(20);
		paint.setColor(Color.BLACK);
		//canvas.drawColor(Color.BLACK);
		canvas.drawText(text, 15, 35, paint);
		canvas.save(Canvas.ALL_SAVE_FLAG);
		
		canvas.restore();

		return b;
	}
    public  void getLocationInfo()
		{
			
			setLocationOption();			
			mLocClient.start();
			mLocClient.requestLocation();	
			System.out.println("inmap after request:");
			//mLocClient.stop();
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
			class LocationThread implements Runnable
			{

				@Override
				public void run() {
					// TODO Auto-generated method stub
					getLocationInfo();
					  System.out.println("lat  lon "+lat+","+lon);
					  MyDayDatabase.saveGeoPoint(lat, lon);
					  GeoPoint point=new GeoPoint((int) (lat*1E6), (int)(lon*1E6));
					  mMapController.setCenter(point);
						mMapController.setZoom(12);
						mMapView.refresh();
						initGeoList();
				}
				
			}
}
