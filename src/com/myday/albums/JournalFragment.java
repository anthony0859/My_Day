package com.myday.albums;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;




import com.example.my__day.R;
import com.myday.database.MyDayDatabase;
import com.myday.file.FileHandle;
import com.myday.pictures.Journal;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.*;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class JournalFragment extends Fragment {
//	ImageView write;
//	ImageView auto_creat;
	Button write;
	Button auto_creat;;
	List<String> journalDirectorys;
	List<String> directorysShow;//��ʾ���б��ϵ�����
	String new_path;
	ImageView takePhoto;//���ఴť
	 @Override
	    public void onCreate(Bundle savedInstanceState)
	    {
	        
	        super.onCreate(savedInstanceState);
	    }

	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	    {
	        
	        View V=inflater.inflate(R.layout.journal_list, container, false);
	        ListView listDirectory=(ListView)V.findViewById(R.id.journallist);
	        write=(Button)V.findViewById(R.id.write_journal);
	        write.setAlpha(0.7f);//���ð�ť͸����
	        
	        auto_creat=(Button)V.findViewById(R.id.auto_create);
	        auto_creat.setAlpha( 0.7f);
	        journalDirectorys=new ArrayList<String>();
			//�õ�journalĿ¼��������Ƭ·��
		 	FileHandle.getDirectorys(FileHandle.journalpath,journalDirectorys);
		 	//�õ��б�����
		 	directorysShow=FileHandle.realDirectorysToDirectoryShow(journalDirectorys);
		 	ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), R.layout.list_item, directorysShow);
	        listDirectory.setAdapter(adapter);
	        listDirectory.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					// TODO Auto-generated method stub
					Intent intent=new Intent();
					intent.putExtra("realdirpath", journalDirectorys.get(arg2));
					String []s=journalDirectorys.get(arg2).split("/");
					String thumbpath=FileHandle.journalthumbpath+s[5];
					intent.putExtra("thumbdirpath", thumbpath);
					intent.putExtra("ifJournal", true);
					intent.setClass(getActivity(), PicturesGridShowActivity.class);
					startActivity(intent);
				}
			});
	        write.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					
					 Date date=new Date();					
					 //String sdpath=Environment.getExternalStorageDirectory()+"/";//���sd��·��
					 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
					 String  dirname=sdf.format(date);  //����Ϊ�ļ����� 
					 
					
					File destDir = new File(FileHandle.albumpath+dirname);
				    if (destDir.exists())
				    {
				    	Intent intent=new Intent();
				    	intent.putExtra("realdirpath", FileHandle.albumpath+dirname);
						intent.putExtra("thumbdirpath", FileHandle.thumbnailpath+dirname);
				    	intent.setClass(getActivity(), PicturesGridShowActivity.class);
						startActivity(intent);
				    }
				    else
				    {
				    	AlertDialog alert=new AlertDialog.Builder(getActivity()).create();
				    	alert.setTitle("��ʾ");
						alert.setMessage("���컹û������  ��������");
						alert.setButton(DialogInterface.BUTTON_POSITIVE,"ȷ��", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
					
					
							}
							
						});
						alert.show();
				    }
					
				}
			});

		auto_creat.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 Date date=new Date();					
					 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
					 String  dirname=sdf.format(date);  		//��ȡ��ǰ������
					 
					 MyDayDatabase database = new MyDayDatabase();
					 
					 File destDir = new File(FileHandle.albumpath+dirname);
					    if (destDir.exists())
					    {
					    	List list = new ArrayList<String>();
							 list = database.searchPhotoByDate(dirname);
							 composeJournal(list,dirname);
							 Intent intent = new Intent();
							 //String path=;
					         intent.putExtra("photorealpath", new_path);
							 intent.setClass(getActivity(), JournalShowActivity.class);
							 startActivity(intent);
					    }
					    else
					    {
					    	AlertDialog alert=new AlertDialog.Builder(getActivity()).create();
					    	alert.setTitle("��ʾ");
							alert.setMessage("���컹û������  ��������");
							alert.setButton(DialogInterface.BUTTON_POSITIVE,"ȷ��", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
						
						
								}
								
							});
							alert.show();
					    }
					 
				}
	        	
	        });
	        return V;
	    }
	    @Override
	    public void onStop()
	    {
	        System.out.println("JournalFragment--->onStop");
	        super.onStop();
	    }

	/**
	 * ��������Ƭ�ϳ�Ϊһ�Ŵ�ͼƬ
	 * @param list ��Ҫ�ϳɵ���Ƭ�б�
	 * @param date ����
	 */
	void composeJournal(List<String> list,String date)//����ͼƬ�ϳ�Ϊһ��
		{
	    	System.out.println("enter journal compose");
	    	int textSize=20;
	    	int gap=10;
			if(list.size()>0)
			{
				String path = FileHandle.albumpath+date+"/";
				System.out.println("path is "+path);
				 Bitmap b=FileHandle.getResizeImage(path+list.get(0)+".jpg",FileHandle.photoSize);
				 System.out.println("path is "+path+list.get(0));
				 if(b != null) System.out.println("b is not null");
				 if(b.getHeight()<b.getWidth()){
				 System.out.println("before rotatePhoto");
				  b=FileHandle.rotatePhoto(b);
				 }
				
				int height=b.getHeight();
				int width=b.getWidth();
				
				System.out.println(width+",,"+height);
				Bitmap newb=Bitmap.createBitmap( width, (height+textSize*2+gap)*list.size(), Config.ARGB_8888); 
				
				Canvas cv = new Canvas( newb );
				String  temp_path;
				String say;
				String location;
				int temp_height=0;
				String []s;
				//draw bitmaps into
				System.out.println("list size is "+list.size());
				for(int i=0;i<list.size();i++)
					{
						temp_path=path+list.get(i)+".jpg";
						s=temp_path.split("/");
						s[6]=s[6].replace(".jpg", "");
						String []info=MyDayDatabase.searchUserInfo(s[6]);
						say=info[0];
						location=info[1];
						System.out.println(temp_path+" say "+say);
						b=FileHandle.getResizeImage(temp_path,FileHandle.photoSize);
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
				System.out.println("finish compose");
				writeJournalPhoto(newb);
			}
		}
	    
	    void writeJournalPhoto(Bitmap b)
		{
	    	System.out.println("enter writeJournalPhoto");
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
			new_path=photopath;
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
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
