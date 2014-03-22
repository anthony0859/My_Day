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
		 // getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.gridshow_titlebar);  //titlebarΪ�Լ��������Ĳ���
		 // �õ�Activity��ActionBar
        ActionBar actionBar = getActionBar();
        // ����AcitonBar�Ĳ���ģ��
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        // ��Activity��ͷ��ȥ��
        actionBar.setDisplayShowTitleEnabled(false);
        // ����Tab
        Tab album = actionBar.newTab().setText("���");
        Tab map = actionBar.newTab().setText("��ͼ");
        Tab journal = actionBar.newTab().setText("ͼ־");
        // Ϊÿ��Tab���Listener
        MyTabListener albumListener = new MyTabListener(new AlbumFragment());
        album.setTabListener(albumListener);
        
        MyTabListener mapListener = new MyTabListener(new MapFragment());
        map.setTabListener(mapListener);
        
        MyTabListener journalListener = new MyTabListener(new JournalFragment());
        journal.setTabListener(journalListener);
        // ��Tab����ActionBar��
        
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
     * ʵ��ActionBar.TabListener�ӿ�
     */
    class MyTabListener implements TabListener
    {
        // ����ÿ��Tab��Ӧ��Fragment������
        private Fragment fragment;

        public MyTabListener(Fragment fragment)
        {
            this.fragment = fragment;
        }

        public void onTabReselected(Tab tab, FragmentTransaction ft)
        {

        }

        // ��Tab��ѡ�е�ʱ����Ӷ�Ӧ��Fragment
        public void onTabSelected(Tab tab, FragmentTransaction ft)
        {
            //ft.add(R.id.context, fragment, null);
        	ft.replace(R.id.fragment_container, fragment);
        }

        // ��Tabû��ѡ�е�ʱ��ɾ����Ӧ�Ĵ�Tab��Ӧ��Fragment
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

