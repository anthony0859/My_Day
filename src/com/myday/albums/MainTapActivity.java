package com.myday.albums;







import com.example.my__day.R;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;

public class MainTapActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//parent.removeView(child);
		  //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);   
		setContentView(R.layout.main_tap);
		 // getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.gridshow_titlebar);  //titlebar为自己标题栏的布局
		 // 得到Activity的ActionBar
        ActionBar actionBar = getActionBar();
        // 设置AcitonBar的操作模型
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        // 将Activity的头部去掉
        actionBar.setDisplayShowTitleEnabled(false);
        // 生成Tab
        Tab album = actionBar.newTab().setText("相册");
        Tab map = actionBar.newTab().setText("地图");
        Tab journal = actionBar.newTab().setText("图志");
        // 为每个Tab添加Listener
        MyTabListener albumListener = new MyTabListener(new AlbumFragment());
        album.setTabListener(albumListener);
        
        MyTabListener mapListener = new MyTabListener(new MapFragment());
        map.setTabListener(mapListener);
        
        MyTabListener journalListener = new MyTabListener(new JournalFragment());
        journal.setTabListener(journalListener);
        // 将Tab加入ActionBar中
        
        actionBar.addTab(album);
        actionBar.addTab(journal);
        actionBar.addTab(map);
       
    }

    @Override
    protected void onStop()
    {
        System.out.println("MainActivity--->onStop");
        super.onStop();
    }

    /**
     * 实现ActionBar.TabListener接口
     */
    class MyTabListener implements TabListener
    {
        // 接收每个Tab对应的Fragment，操作
        private Fragment fragment;

        public MyTabListener(Fragment fragment)
        {
            this.fragment = fragment;
        }

        public void onTabReselected(Tab tab, FragmentTransaction ft)
        {

        }

        // 当Tab被选中的时候添加对应的Fragment
        public void onTabSelected(Tab tab, FragmentTransaction ft)
        {
            //ft.add(R.id.context, fragment, null);
        	ft.replace(R.id.fragment_container, fragment);
        }

        // 当Tab没被选中的时候删除对应的此Tab对应的Fragment
        public void onTabUnselected(Tab tab, FragmentTransaction ft)
        {
            ft.remove(fragment);
        }

	
		
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}

