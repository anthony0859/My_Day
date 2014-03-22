package com.myday.albums;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;




import com.example.my__day.R;
import com.myday.database.MyDayDatabase;
import com.myday.file.FileHandle;
import com.myday.pictures.Album;
import com.myday.pictures.Journal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

/**��ʾ������*/
public class AlbumEditActivity extends Activity{
	long[]indexs;//��ѡ�е�ͼƬ�±�
	private GridView gridView;
	int textSize=20;
	private int gap=10;
	Album album;
	boolean ifFromMap=false;
	protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); 
	        setContentView(R.layout.pictures_grid_edit);
	        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.album_edit_titlebar);  //titlebarΪ�Լ��������Ĳ���
	        
	        Bundle b=this.getIntent().getExtras();
	        album=(Album) b.getSerializable("album");
	        ifFromMap=b.getBoolean("ifFromMap");
	        gridView=(GridView)findViewById(R.id.gridView);      
			gridView.setAdapter(new GridViewEditAdaper());
			//���ö�ѡģʽ
			gridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
			// ���ö�ѡ ģʽ��������
			gridView.setMultiChoiceModeListener(new MyMultiChoiceModeListener());
//			Toast toast=new Toast(this);
//			toast.setText("����ͼƬ���ж�ѡ");
//			toast.show();
	    }
        
	class GridViewEditAdaper extends BaseAdapter
	{
		@Override
			public View getView(final int arg0, View arg1, ViewGroup arg2) {
				// TODO Auto-generated method stub
				ImageView image;
				 final CheckableLayout mCheckableLayout; 
				if(arg1==null)
				{
					image=new ImageView(AlbumEditActivity.this);
					image.setAdjustViewBounds(true);
					image.setMaxWidth(250);
					image.setMaxHeight(210);
					image.setPadding(5, 5, 5, 5);//�����ڱ߾�
					mCheckableLayout = new CheckableLayout(AlbumEditActivity.this);  
	                mCheckableLayout.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.WRAP_CONTENT, GridView.LayoutParams.WRAP_CONTENT));  
	                mCheckableLayout.addView(image);  
				}
				else
				{	mCheckableLayout = (CheckableLayout) arg1;  
					//image=(ImageView)arg1;
				image=(ImageView) mCheckableLayout.getChildAt(0);
				}
				 Bitmap bm=BitmapFactory.decodeFile(album.getThumbpaths().get(arg0));
				//setPhotoOption(bm,imagePath.get(arg0));
				 

		         
				image.setImageBitmap(bm);
				return mCheckableLayout;
			}
			
			@Override
			public long getItemId(int arg0) {
				// TODO Auto-generated method stub
				return arg0;
			}
			
			@Override
			public Object getItem(int arg0) {
				// TODO Auto-generated method stub
				return arg0;
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return album.size();
			}

			@Override
			public boolean hasStableIds() {
				// TODO Auto-generated method stub
				//return super.hasStableIds();
				return true;
			}
	}
	
public class CheckableLayout extends FrameLayout implements Checkable {  
        
        private boolean mChecked;  
  
        public CheckableLayout(Context context) {  
            super(context);  
            mChecked=false;
        }  
  
        /** 
         * �ڱ�ѡ��ʱ������ɫ������ 
         * @param check true ����Ϊ��ɫ, false ����Ϊ��ɫ
         */  
        public void setChecked(boolean checked) {  
            mChecked = checked;  
            if(mChecked)
            	setBackgroundColor(Color.BLUE);
            else
            	setBackgroundColor(Color.BLACK);
            //setBackgroundDrawable(checked ? getResources().getDrawable(R.drawable.blue) : null);  
        }  
  
        public boolean isChecked() {  
            return mChecked;  
        }  
  
        public void toggle() {  
            setChecked( ! mChecked);  
        }  
	}
	public class MyMultiChoiceModeListener implements GridView.MultiChoiceModeListener {  
    
	    /** 
	     * ����ActionMode�ɰ塣 
	     */  
	    public boolean onCreateActionMode(ActionMode mode, Menu menu) {  
	        mode.setTitle("Select Items");  
	        mode.setSubtitle("1 item selected");  
	        getMenuInflater().inflate(R.menu.action_menu, menu); 
	        return true;  
	    }  
	
	    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {  
	        return true;  
	    }  
	
	    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {  
	    	if(item.getItemId()==R.id.menu_1)
	    	{
	    		deletePhotos();
	    	}
	    	else if(item.getItemId()==R.id.menu_2)
	    	{
	    		composeJournal();
	    	}
	        return true;  
	    }  
	
	    public void onDestroyActionMode(ActionMode mode) {  
	          
	    }  
	
	    /** 
	     * ����ActionMode���⡣ 
	     */  
	    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {  
	        int selectCount = gridView.getCheckedItemCount();  
	          indexs= gridView.getCheckedItemIds();
	          String s="";
	          for(long l:indexs)
	        	  s+=l+",";
	          System.out.println(indexs.length+" ; "+s);
	         // Toast.makeText(AlbumEditActivity.this,s, Toast.LENGTH_LONG).show();

	        switch ( selectCount ) {  
	            case 1 :  
	                mode.setSubtitle("1 item selected");  
	                break;  
	            default :  
	                mode.setSubtitle("" + selectCount + " items selected");  
	                break;  
	        }  
	    }  
	}  
	/**��������Ƭ�ϳ�һ�Ŵ�ͼƬ*/
	void composeJournal()//����ͼƬ�ϳ�Ϊһ��
	{
		if(indexs.length!=0)
		{
			 Bitmap b=FileHandle.getResizeImage(album.getRealpaths().get(0),FileHandle.photoSize);
			 if(b.getHeight()<b.getWidth())
			  b=FileHandle.rotatePhoto(b);
			int height=b.getHeight();
			int width=b.getWidth();
			
			System.out.println(width+",,"+height);
			Bitmap newb=Bitmap.createBitmap( width, (height+textSize*2+gap)*indexs.length, Config.ARGB_8888); 
			
			Canvas cv = new Canvas( newb );
			String  temp_path;
			String say;
			String location;
			int temp_height=0;
			String []s;
			//draw bitmaps into
			for(int i=0;i<indexs.length;i++)
				{
					
					temp_path=album.getRealpaths().get(i);
					s=temp_path.split("/");
					s[6]=s[6].replace(".jpg", "");
					String []info=MyDayDatabase.searchUserInfo(s[6]);
					say=info[0];
					location=info[1];
					System.out.println(temp_path+" say "+say);
					b=FileHandle.getResizeImage(album.getRealpaths().get(i),FileHandle.photoSize);
					 if(b.getHeight()<b.getWidth())
						 b=FileHandle.rotatePhoto(b);
					cv.drawBitmap( b, 0, temp_height, null );//�� 0��0���꿪ʼ����src
					
					Paint paint = new Paint();

					paint.setTextSize(textSize);//���������С
					//paint.s
					Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
					paint.setTypeface( font );

					paint.setColor(Color.WHITE);
					paint.setTypeface(null);//������������
					temp_height+=height+textSize;
					cv.drawText(location, 0, temp_height, paint);//ʹ�û���paint
					temp_height+=textSize;
					cv.drawText(say, 0, temp_height, paint);//ʹ�û���paint
					temp_height+=gap;
					//cv.drawText(say, 0, say.length()-1, 0, temp_height, null);
					//temp_height+=15;
				}
			cv.save(Canvas.ALL_SAVE_FLAG);
			
			cv.restore();
			System.out.println("in compose");
			writeJournalPhoto(newb);
		}
	}
	/**
	 * �������ɵ���־��Ƭ
	 * @param b �ϳɵ���־��Ƭ
	 */
	void writeJournalPhoto(Bitmap b)
	{
		Date date=new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
	    String dirname=sdf.format(date);  //����Ϊ�ļ����� 
	    sdf = new SimpleDateFormat("yyyy-MM-dd  HH-mm-ss");   
	    String photoname=sdf.format(date);  //����+ʱ��Ϊ�ļ���
	    File destDir = new File(FileHandle.journalpath+dirname);
	    if (!destDir.exists())
	    {
	     destDir.mkdirs();
	    }
		File file=new File(FileHandle.journalpath+dirname+"/"+photoname+".jpg");//�����ļ�����
		String photopath=FileHandle.journalpath+dirname+"/"+photoname+".jpg";
		try {
			file.createNewFile();//�������ļ�
			//System.out.println("after creat journal file");
			FileOutputStream fileOS=new FileOutputStream(file);//�����ļ����������
			System.out.println("in write journal "+b.getHeight());
			b.compress(Bitmap.CompressFormat.JPEG, 100,fileOS);//��ͼƬ����ѹ��ΪJPEG��ʽ��������������
			fileOS.flush();//������������ȫ��д���������
			fileOS.close();
			//��������ͼ
			FileHandle.saveFileAsThumb(FileHandle.journalthumbpath, dirname, photoname, FileHandle.getResizeImage(photopath, 100));
			//д�����ݿ�
			Journal j=new Journal();
			j.setPicname(photoname);
			MyDayDatabase.saveJournal(j);
			Intent intent=new Intent();
			intent.setClass(AlbumEditActivity.this, JournalShowActivity.class);
			intent.putExtra("photorealpath", FileHandle.journalpath+dirname+"/"+photoname+".jpg");
			//intent.putExtra("ifJournal", true);
			startActivity(intent);
			finish();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	void deletePhotos()
	{

		if(indexs.length!=0)
		{
			File defile;
			String[]s;
			for(int i=0;i<indexs.length;i++)
			{
				//ɾ����ͼƬ 
				 defile=new File(album.getRealpaths().get(i));
				 defile.delete();
				 //ɾ������ͼ
				 defile=new File(album.getThumbpaths().get(i));
				 defile.delete();
				 s=album.getRealpaths().get(i).split("/");
				 s[6]=s[6].replace(".jpg", "");
				 if(s[4].contains("album"))
					 MyDayDatabase.deletePhoto(s[6]);
				 if(s[4].contains("journal"))
					 MyDayDatabase.deleteJournal(s[6]);
				 
			}
		
		Intent intent=new Intent();
		 //String []s=imagePath.get(0).split("/");
		// String path="/"+s[0]+"/"+s[1]+"/"+s[2]+"/"+s[3]+"/"+s[4]+"/"+s[5]+"/";
		// System.out.println("dele "+path);
		if(ifFromMap)
		{
			intent.putStringArrayListExtra("realpaths",(ArrayList<String>) album.getRealpaths() );
			 intent.putStringArrayListExtra("thumbpaths",(ArrayList<String>) album.getThumbpaths());
			 intent.setClass(AlbumEditActivity.this, SameLocationAlbumActivity.class);
		}
		else
		{
		 intent.putExtra("realdirpath",album.getRealDirectory() );
		 intent.putExtra("thumbdirpath",album.getThumbDirectory() );
		 intent.setClass(AlbumEditActivity.this, PicturesGridShowActivity.class);
		}
		 startActivity(intent);
		 finish();
		}
	}
}
