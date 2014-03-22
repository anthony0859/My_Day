package com.myday.map;




import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;


public class DemoApplication extends Application {
	
    private static DemoApplication mInstance = null;
    public boolean m_bKeyRight = true;
    BMapManager mBMapManager = null;

    public static final String strKey = "D7697FCE8DE7E90C1ED254662974EB0B3A7A4CFB";
	
	@Override
    public void onCreate() {
	    super.onCreate();
		mInstance = this;
		initEngineManager(this);
	}
	
	@Override
	
	public void onTerminate() {
		// TODO Auto-generated method stub
	    if (mBMapManager != null) {
            mBMapManager.destroy();
            mBMapManager = null;
        }
		super.onTerminate();
	}
	
	public void initEngineManager(Context context) {
        if (mBMapManager == null) {
            mBMapManager = new BMapManager(context);
        }

        if (!mBMapManager.init(strKey,new MyGeneralListener())) {
            Toast.makeText(DemoApplication.getInstance().getApplicationContext(), 
                    "BMapManager ", Toast.LENGTH_LONG).show();
        }
	}
	
	public static DemoApplication getInstance() {
		return mInstance;
	}
	
	
	
    static class MyGeneralListener implements MKGeneralListener {
        
        @Override
        public void onGetNetworkState(int iError) {
            if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
                Toast.makeText(DemoApplication.getInstance().getApplicationContext(), " ",
                    Toast.LENGTH_LONG).show();
            }
            else if (iError == MKEvent.ERROR_NETWORK_DATA) {
                Toast.makeText(DemoApplication.getInstance().getApplicationContext(), " ",
                        Toast.LENGTH_LONG).show();
            }
            
        }
        

        @Override
        public void onGetPermissionState(int iError) {
            if (iError ==  MKEvent.ERROR_PERMISSION_DENIED) {
                              
            	Toast.makeText(DemoApplication.getInstance().getApplicationContext(), 
                        " ", Toast.LENGTH_LONG).show();
                DemoApplication.getInstance().m_bKeyRight = false;
            }
        }
    }
}