package com.myday.map;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;

import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.myday.albums.PicturesGridShowActivity;
import com.myday.albums.SameLocationAlbumActivity;
import com.myday.database.MyDayDatabase;
import com.myday.file.FileHandle;
import com.myday.pictures.Album;

public class Over_Item extends ItemizedOverlay<OverlayItem>{
	private List<OverlayItem> GeoList = new ArrayList<OverlayItem>();
	private Context mContext;
	
	private double mLat1 = 39.90923;//39.9022; // point1纬度
	private double mLon1 = 116.397428;//116.3822; // point1经度
	
	public Over_Item(Drawable marker, Context context) {
		super(marker);
		 
		this.mContext = context;
		 
		// 用给定的经纬度构造GeoPoint，单位是微度 (度 * 1E6)
		//GeoPoint p1 = new GeoPoint((int) (mLat1 * 1E6), (int) (mLon1 * 1E6));
		//GeoPoint p2 = new GeoPoint((int) (mLat2 * 1E6), (int) (mLon2 * 1E6));
		//GeoPoint p3 = new GeoPoint((int) (mLat3 * 1E6), (int) (mLon3 * 1E6));
		 
		//GeoList.add(new OverlayItem(p1, "P1", "point1"));
		//GeoList.add(new OverlayItem(p2, "P2", "point2"));
		//GeoList.add(new OverlayItem(p3, "P3", "point3"));
		
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
	
	double lat=GeoList.get(i).getPoint().getLatitudeE6();
	double lon=GeoList.get(i).getPoint().getLongitudeE6();
	String lati=lat/1000000+"";
	String lont=lon/1000000+"";
	//Toast.makeText(this.mContext, lati+","+lont,Toast.LENGTH_SHORT).show();
	List<String> samePointPhotoRealPaths=new ArrayList<String>();
	List<String> samePointPhotoThumbPaths=new ArrayList<String>();
	List<String[]> list=MyDayDatabase.searchPhotoFromPoint(lati, lont);
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
	Toast.makeText(this.mContext, t,Toast.LENGTH_SHORT).show();
//	Album album=new Album();
//	album.setRealpaths(samePointPhotoRealPaths);
//	album.setThumbpaths(samePointPhotoThumbPaths);
	Intent intent=new Intent();
	Bundle bundle=new Bundle();
	bundle.putStringArrayList("realpaths", (ArrayList<String>) samePointPhotoRealPaths);
	bundle.putStringArrayList("thumbpaths", (ArrayList<String>) samePointPhotoThumbPaths);
	intent.putExtras(bundle);
	intent.setClass(mContext, SameLocationAlbumActivity.class);
	System.out.println("start activity ");
	mContext.startActivity(intent);
	return true;
	}


	public List<OverlayItem> getGeoList() {
		return GeoList;
	}


	public void setGeoList(List<OverlayItem> geoList) {
		GeoList = geoList;
		populate(); //createItem(int)方法构造item。一旦有了数据，在调用其它方法前，首先调用这个方法
	}

}
