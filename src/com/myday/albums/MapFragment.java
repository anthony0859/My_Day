package com.myday.albums;



import java.util.List;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.example.my__day.R;

import com.myday.map.Map_Activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

public class MapFragment extends Fragment {
	BMapManager mBMapMan = null;
	MapView mMapView = null;
	private LocationClient mLocClient;
	/**
	 *  Բ�ľ�γ������ 
	 */
	int cLat = 39909230 ;
	int cLon = 116397428 ;
	// ���overlayitem 
	public List<OverlayItem> mGeoList;
	ImageView takePhoto;//���ఴť
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
    	 View V=inflater.inflate(R.layout.map_fragment, container, false);
	       Button b=(Button)V.findViewById(R.id.test);
	       b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(getActivity(),Map_Activity.class);
				startActivity(intent);
			}
		});
	      // takePhoto=(ImageView)V.findViewById(R.id.takephoto);
	        return V;
    }
    @Override
    public void onStop()
    {
        System.out.println("JournalFragment--->onStop");
        super.onStop();
    }
}
