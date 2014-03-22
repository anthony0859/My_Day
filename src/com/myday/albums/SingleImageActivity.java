package com.myday.albums;




import com.myday.database.MyDayDatabase;
import com.example.my__day.R;
import com.myday.file.FileHandle;
import com.myday.pictures.Photo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class SingleImageActivity extends Activity{
	String photopath;//��ͼƬ·��
	Photo photo;
	Bitmap bm;
	ImageView view;
	//Button edit;
	TextView photo_say;
	TextView location;
	TextView photo_time;
	@Override 
    protected void onCreate(Bundle savedInstanceState) 
  { 
        super.onCreate(savedInstanceState); 
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); 
        setContentView(R.layout.single_photo);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.single_photo_titlebar);  //titlebarΪ�Լ��������Ĳ���
        
        view = (ImageView) findViewById(R.id.view);
        photo_say=(TextView)findViewById(R.id.photo_say);
        location=(TextView)findViewById(R.id.location);
        Intent intent=getIntent();
        photopath=intent.getStringExtra("photorealpath");
        photo=new Photo();
        System.out.println("getStringExtra photorealpath:"+photopath);
        photo.setByRealPath(photopath);
        bm=FileHandle.getResizeImage(photopath, FileHandle.photoSize);
        if(bm.getHeight()<bm.getWidth())
        	bm=FileHandle.rotatePhoto(bm);
        view.setImageBitmap(bm);
        photo_time=(TextView)findViewById(R.id.photo_time);
        photo_time.setText("�����ڣ�"+photo.getPicname());
        photo_say.setOnClickListener(new EditButtonListener());
        //String s=MyDayDatabase.searchUserSay(photo.getPicname());
        String[]s=MyDayDatabase.searchUserInfo(photo.getPicname());
        System.out.println(s[0]+"*");
        if(!s[0].equals("null")&&s[0]!=null)
        	photo_say.setText(s[0]);
        else
        	photo_say.setText("�ڴ˴�д���������");
        if(!s[1].equals("null")&&s[1]!=null)
        	location.setText(s[1]);
        else
        	location.setText("��ַ��ȡ��");
  }
	class EditButtonListener implements OnClickListener
	 {
		String user_say;
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			AlertDialog alert=new AlertDialog.Builder(SingleImageActivity.this).create();
			alert.setTitle("����");
			alert.setMessage("���������Ƭ����˵�Ļ�");
			final EditText say=new EditText(SingleImageActivity.this);
			alert.setView(say);
			alert.setButton(DialogInterface.BUTTON_POSITIVE,"ȷ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
							user_say=say.getText().toString();
							
							//�����µ�������ʾ�ڽ�����
							photo_say.setText(user_say);
							//if(!ifCamera)//����������࣬˵����ͼƬ�Ա������ֱ�Ӹ���
							System.out.println("photo.getPicname() "+photo.getPicname());
							MyDayDatabase.updateUserSay(photo.getPicname(),user_say);
		
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
		intent.putExtra("realdirpath", FileHandle.albumpath+"/"+photo.getDirname());
		intent.putExtra("thumbdirpath", FileHandle.thumbnailpath+"/"+photo.getDirname());
		intent.setClass(SingleImageActivity.this, PicturesGridShowActivity.class);
		startActivity(intent);
        finish();	
	}
	
	
}
