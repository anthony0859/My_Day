package com.myday.albums;



import java.io.File;



import com.example.my__day.R;
import com.myday.database.MyDayDatabase;
import com.myday.file.FileHandle;
import com.myday.pictures.Journal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class JournalShowActivity extends Activity{
	String journalpath;//日志路径
	Journal journal;
	Button share;
	TextView theme;
	Button edit_theme;
	 protected void onCreate(Bundle savedInstanceState) 
	  { 
		 super.onCreate(savedInstanceState); 
		 requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);   
	        setContentView(R.layout.single_journal);
	        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.journal_titlebar);  //titlebar为自己标题栏的布局
	        ImageView view = (ImageView) findViewById(R.id.journal_show);
	        edit_theme=(Button)findViewById(R.id.edit_theme);
	        
	        Intent intent=getIntent();
	        journalpath=intent.getStringExtra("photorealpath");
	        journal=new Journal();
	        System.out.println("in journal journalpath:"+journalpath);
	        journal.setByRealPath(journalpath);
	        
	        theme=(TextView)findViewById(R.id.theme);
	        String temp=MyDayDatabase.searchJournalTheme(journal.getPicname());
	        if(!temp.equals("null")&&temp!=null)
	        	theme.setText(temp);
	        else 
	        	theme.setText("暂无标题");
	        Bitmap image = BitmapFactory.decodeFile(journalpath);
            view.setImageBitmap(image); 
            
            edit_theme.setOnClickListener(new EditThemeListener());
	        share=(Button)findViewById(R.id.share);
		    share.setOnClickListener(new OnClickListener() 
		      {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(Intent.ACTION_SEND);   
					intent.setType("image/*");   
					intent.putExtra(Intent.EXTRA_SUBJECT,"subject");   
					intent.putExtra(Intent.EXTRA_TEXT,  "extratext");    
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
					File f=new File(journalpath);
					Uri u=Uri.fromFile(f);
					intent.putExtra(Intent.EXTRA_STREAM,u);
					startActivity(Intent.createChooser(intent, "分享"));  
				}
		      });
	 }
	 class EditThemeListener implements OnClickListener
	 {
        
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			AlertDialog alert=new AlertDialog.Builder(JournalShowActivity.this).create();
			alert.setTitle("标题");
			alert.setMessage("请输入此日志的标题");
			final EditText say=new EditText(JournalShowActivity.this);
			alert.setView(say);
			alert.setButton(DialogInterface.BUTTON_POSITIVE,"确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
							String jour_theme=say.getText().toString();
							
							//将更新的描述显示在界面上
							theme.setText(jour_theme);
							//if(!ifCamera)//如果不是照相，说明该图片以保存过，直接更新
							
							MyDayDatabase.updateJournalTheme(journal.getPicname(),jour_theme);
//							Intent intent=new Intent();
//							intent.putExtra("realdirpath", FileHandle.albumpath+"/"+photo.getDirname());
//							intent.putExtra("thumbdirpath", FileHandle.thumbnailpath+"/"+photo.getDirname());
//							intent.setClass(SingleImageActivity.this, PicturesGridShowActivity.class);
//							startActivity(intent);
//							finish();
						
				}
				
			});
			alert.show();
		}
		 
	 }
	 @Override
		public void onBackPressed() {
			// TODO Auto-generated method stub
			super.onBackPressed();
			Intent intent=new Intent();
			intent.putExtra("realdirpath", FileHandle.journalpath+"/"+journal.getDirname());
			intent.putExtra("thumbdirpath", FileHandle.journalthumbpath+"/"+journal.getDirname());
			intent.putExtra("ifJournal", true);
			intent.setClass(JournalShowActivity.this, PicturesGridShowActivity.class);
			startActivity(intent);
	        finish();	
		}
		
}

