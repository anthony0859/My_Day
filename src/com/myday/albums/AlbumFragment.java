package com.myday.albums;



import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.my__day.R;



import com.myday.database.MyDayDatabase;
import com.myday.file.FileHandle;
import com.myday.map.Location;
import com.myday.map.Map_Activity;
import com.myday.pictures.Photo;

import android.app.Fragment;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class AlbumFragment extends Fragment {
	int TAKE_BIG_PICTURE=0;
	ImageView takePhoto;//照相按钮
	//Button takePhoto;//照相按钮
	List<String> realDirectorys;//ablum目录下的文件夹
	List<String> thumbDirectorys;//thumbnail目录下的文件夹
	List<String> directorysShow;//显示在列表上的日期
	public static Photo photo;
	public static  LocationClient mLocClient;
	public static String locationInfo; 
	public static String pointInfo; 
	 String photoname;//照相的照片名
	 @Override
	    public void onCreate(Bundle savedInstanceState)
	    {
	        
	        super.onCreate(savedInstanceState);
	    }

	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	    {
	        
	        View V=inflater.inflate(R.layout.album_list, container, false);
	        mLocClient = ((Location)getActivity().getApplication()).mLocationClient;
	        takePhoto=(ImageView)V.findViewById(R.id.takephoto);
	        ListView listDirectory=(ListView)V.findViewById(R.id.albumlist);
	        realDirectorys=new ArrayList<String>();
	        thumbDirectorys=new ArrayList<String>();
			initialDir();
			FileHandle.getDirectorys(FileHandle.albumpath,realDirectorys);//获得album目录下的文件夹路径,存入realDirectorys
			FileHandle.getDirectorys(FileHandle.thumbnailpath,thumbDirectorys);//获得thumbnail目录下的文件夹路径,存入thumbDirectorys
			System.out.println("in albumlist "+realDirectorys.size());
			directorysShow=FileHandle.realDirectorysToDirectoryShow(realDirectorys);
			//显示列表的适配器
			ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), R.layout.list_item, directorysShow);
	        listDirectory.setAdapter(adapter);
	        listDirectory.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
				{
					// TODO Auto-generated method stub
					Intent intent=new Intent();
					intent.putExtra("realdirpath", realDirectorys.get(arg2));//把所在目录传到相册图片显示的activity
					intent.putExtra("thumbdirpath", thumbDirectorys.get(arg2));//把缩略图所在目录传到相册图片显示的activity
					intent.setClass(getActivity(), PicturesGridShowActivity.class);
					startActivity(intent);
				}
			});
	        //点击按钮启动照相机
	        takePhoto.setOnClickListener(new CallCamera());
	        new Thread(new LocationThread()).start();
	        return V;
	    }
		/**按下按钮 调用照相机程序*/
	    class CallCamera implements OnClickListener
	    {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 try { 
			            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
			            
			            Date date=new Date();					
					    //String sdpath=Environment.getExternalStorageDirectory()+"/";//获得sd卡路径
					    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
					    String  dirname=sdf.format(date);  //日期为文件夹名 
					    //sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
					    sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");   
					     photoname=sdf.format(date);  //日期+时间为文件名
					    //Toast.makeText(getActivity(), photoname,Toast.LENGTH_SHORT).show();
					    File destDir = new File(FileHandle.albumpath+dirname);
					    //File destDir = new File("storage/sdcard0/DCIM/Camera/"+dirname);
					    if (!destDir.exists())
					    {
					     destDir.mkdirs();
					    }
					    destDir = new File(FileHandle.thumbnailpath+dirname);
					    if (!destDir.exists())
					    {
					     destDir.mkdirs();
					    }
					    String photopath=FileHandle.albumpath+dirname+"/"+photoname+".jpg";
					    //String photopath=FileHandle.albumpath+dirname+"/2.jpg";
					    //String photopath="storage/sdcard0/DCIM/Camera/"+dirname+"/"+photoname+".jpg";
					    File file=new File(photopath);//创建文件对象
					    //照片保存到photopath路径下
			           intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(file) );  			            
			            startActivityForResult(intent, TAKE_BIG_PICTURE); 
			       } catch (Exception e) { 
			          System.out.println("wrong "+e);
			       } 
			}
	    	
	    }
	    //返回照片
	    public void onActivityResult(int requestCode, int resultCode, Intent data) 
	    { 
	    	
	        try { 
	            if (requestCode == 0 && resultCode!=0)//resultCode==0表示没照相 照相机页面直接按了取消
	            { 
	            	super.onActivityResult(requestCode, resultCode, data);
	                System.out.println("recode ==0 "+resultCode); 
	                if(data==null)
	                	System.out.println("data==null ");
	                else
	                	System.out.println("data.toString():"+data.toString());
	                //保存到数据库
		            photo=new Photo(photoname);
		            MyDayDatabase.savePhoto(photo);
		            FileHandle.saveFileAsThumb(FileHandle.thumbnailpath, photo.getDirname(), photo.getPicname(), FileHandle.getResizeImage(photo.getRealpath(), 120));
		            //getLocationInfo();
		            photo.setLocation(AlbumFragment.locationInfo);
					photo.setPointinfo(pointInfo);
					MyDayDatabase.updateLocInfo(AlbumFragment.photo.getPicname(), AlbumFragment.locationInfo, pointInfo);
		            
		            
//	            } 
//	     		
//	            else
//	            {
//	            	System.out.println("before super. in onactivityResult");
//	            
//	            Log.d(getTag(), "before super. in onactivityResul");
	            	
//	            System.out.println("in onactivityResult");
	            Intent intent = new Intent();
//	            //得到图片后，我们在另一个activity中显示 
	            intent.setClass(getActivity(), SingleImageActivity.class); 
	            //intent.putExtra("image",b); 
	            intent.putExtra("ifCamera", true);
	            intent.putExtra("photoName", photo.getPicname());
	            intent.putExtra("photorealpath",photo.getRealpath());//photo.getRealpath()
	            System.out.println("before save thumb "+photo.getDirname()+" ,"+photo.getPicname()+","+photo.getRealpath());
	           
	           
	            this.startActivity(intent); 
	            }
	            
	        } catch (Exception e) { 
	            // TODO: handle exception 
	        	 System.out.println("onActivityResult wrong "+e);
	        } 
	    }
		
	    @Override
	    public void onStop()
	    {
	        System.out.println("ConputerFragment--->onStop");
	        super.onStop();
	    }
	    public void initialDir()
		{
			FileHandle.makeFileDir(FileHandle.mydaypath);
			
			FileHandle.makeFileDir(FileHandle.albumpath);
			FileHandle.makeFileDir(FileHandle.thumbnailpath);
			
			FileHandle.makeFileDir(FileHandle.journalpath);
			FileHandle.makeFileDir(FileHandle.journalthumbpath);
			SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(FileHandle.mydaypath+"myday_lastGeoPoint.db", null);
			db.close();
		}
	    
	    /**
	     * 获取地点信息
	     */
	    public  void getLocationInfo()
		{
			
			setLocationOption();			
			mLocClient.start();
			mLocClient.requestLocation();	
			System.out.println("after request:");
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
					MyDayDatabase.saveGeoPoint(Map_Activity.lat, Map_Activity.lon);
					  System.out.println("location "+locationInfo);
//					  photo.setLocation(AlbumFragment.locationInfo);
//					  photo.setPointinfo(pointInfo);
//					  MyDayDatabase.updateLocInfo(AlbumFragment.photo.getPicname(), AlbumFragment.locationInfo, pointInfo);
						mLocClient.stop();
				}
				
			}
}
