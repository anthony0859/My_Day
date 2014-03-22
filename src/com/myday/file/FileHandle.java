package com.myday.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
/**�ļ�����������*/
public class FileHandle {
	
	static String[] imageFormatSet=new String[]{"jpg","gif","png"};
	public static String sdpath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/";//���sd��·��
	public static String mydaypath=sdpath+"MyDay/";
	public static String journalpath=mydaypath+"journal/";
	public static String albumpath=mydaypath+"album/";
	public static String thumbnailpath=mydaypath+"thumbnail/";
	public static String journalthumbpath=mydaypath+"journalthumb/";
	public static int photoSize=258;
	
	/**
	 * ����ļ�Ŀ¼
	 * @param url �ļ���URL
	 * @param imageDirectorys �ļ�Ŀ¼�б�
	 */
	public static void getDirectorys(String url,List<String> imageDirectorys)//�õ�url·��������ͼƬĿ¼
	{
		File files=new File(url);
		File[] file=files.listFiles();
		if(file!=null)
		for(File f:file)
		{
			if(f.isDirectory())
			{
				imageDirectorys.add(f.getPath());
				//getFiles(f.getAbsolutePath());
			}
		}
	}
	
	/**
	 * ���ͼƬ�ļ�
	 * @param url �ļ�RL
	 * @param imagePath ͼƬ�ļ�·���б�
	 */
	public static void getFiles(String url, List<String> imagePath)//�õ�url·��������ͼƬ�ļ�
	{
		File files=new File(url);
		File[] file=files.listFiles();
		for(File f:file)
		{
			if(f.isDirectory())
			{
				getFiles(f.getAbsolutePath(), imagePath);
			}
			else
			{
				System.out.println("f path"+f.getPath());
				if(isImageFile(f.getPath()))
					imagePath.add(f.getPath());
			}
		}
	}
	
	/**
	 * �ж��Ƿ���ͼƬ�ļ�
	 * @param path �ļ�·��
	 * @return true ��ͼƬ�ļ�, false ����ͼƬ�ļ�
	 */
	public static boolean isImageFile(String path)//�ж��ǲ���ͼƬ�ļ������ݺ�׺��
	{
		for(String format:imageFormatSet)
			if(path.contains(format))
				return true;
		return false;
	}
	
	
	public static void makeFileDir(String path)
	{
		File destDir = new File(path);
	    if (!destDir.exists())
	    {
	     destDir.mkdirs();
	    }
	}
	
	/**
	 * ���ļ�������ͼ����ʽ����
	 * @param path �ļ�·��
	 * @param dirname ����
	 * @param photoname ��Ƭ����
	 * @param image ͼƬ
	 */
	public static void saveFileAsThumb(String path,String dirname,String photoname,Bitmap image)
	{
		 File destDir = new File(path+dirname);
		    if (!destDir.exists())
		    {
		     destDir.mkdirs();
		    }
					    String name=path+dirname+"/"+photoname+".jpg";
		File file=new File(path+dirname+"/"+photoname+".jpg");//�����ļ�����
		try {
			file.createNewFile();//�������ļ�
			System.out.println("after creat file:"+name);
			FileOutputStream fileOS=new FileOutputStream(file);//�����ļ����������	
			image=rotatePhoto(image);
			image.compress(Bitmap.CompressFormat.JPEG, 100,fileOS);//��ͼƬ����ѹ��ΪJPEG��ʽ��������������
			fileOS.flush();//������������ȫ��д���������
			fileOS.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * ��תͼƬ
	 * @param bm ��Ҫ��ת��ͼƬ
	 * @return ��ת֮���ͼƬ
	 */
	 public static Bitmap rotatePhoto(Bitmap bm)
	  {
		  Matrix m = new Matrix();  
        int width = bm.getWidth();  
        int height = bm.getHeight();  
        m.setRotate(90); // ��תangle��  
        bm = Bitmap.createBitmap(bm, 0, 0, width, height,  
                m, true);// ��������ͼƬ
        System.out.println("rotate");
        return bm;
	  }
	 
	 /**
	  * �ı�ͼƬ��С
	  * @param imagePath ͼƬ·��
	  * @param maxH ���߶�
	  * @return �ı��С���ͼƬ
	  */
	 public static Bitmap getResizeImage(String imagePath,int maxH){  
	        BitmapFactory.Options options = new BitmapFactory.Options();  
	                options.inJustDecodeBounds = true;  
	                // ��ȡ���ͼƬ�Ŀ�͸�  
	               Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options); //��ʱ����bmΪ��  
	                    //�������ű�  
	                int be = (int)(options.outHeight / (float)maxH);  
	                int ys = options.outHeight % maxH;//������  
	                float fe = ys / (float)maxH;  
	                if(fe>=0.5)be = be + 1;  
	                if (be <= 0)  
	                    be = 1;  
	                options.inSampleSize = be;  
	             
	                //���¶���ͼƬ��ע�����Ҫ��options.inJustDecodeBounds ��Ϊ false  
	                options.inJustDecodeBounds = false;  
	                bitmap=BitmapFactory.decodeFile(imagePath,options);  
	                return bitmap;
	               // img.setImageBitmap(bitmap);  
	              //  img.setVisibility(View.VISIBLE);  
	    }
	 
	 /**
	  * ����ʵ·��ת��������ͼ��·��
	  * @param realPaths ͼƬ����ʵ·��
	  * @return ����ʵ·����Ӧ������ͼ·��
	  */
	public  static List<String> realPathsToThumbPaths(List<String> realPaths)
	{
		List<String> thumbPaths=new ArrayList<String>();
		//String[]s;
		String temp;
		for(String realpath:realPaths)
		{
			//s=realpath.split("/");
			temp=realpath.replace("album", "thumbnail");
			thumbPaths.add(temp);
		}
		return thumbPaths;
	}
	
	/**
	 * 
	 * @param realPaths
	 * @return 
	 */
	public static List<String> realDirectorysToDirectoryShow(List<String> realPaths)
	{
		List<String> dirShow=new ArrayList<String>();
		String []temp;
		for(String s:realPaths)
		{
			temp=s.split("/");
			dirShow.add(temp[5]);
		}
		return dirShow;
	}
}
