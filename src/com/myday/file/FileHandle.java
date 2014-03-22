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
/**文件操作辅助类*/
public class FileHandle {
	
	static String[] imageFormatSet=new String[]{"jpg","gif","png"};
	public static String sdpath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/";//获得sd卡路径
	public static String mydaypath=sdpath+"MyDay/";
	public static String journalpath=mydaypath+"journal/";
	public static String albumpath=mydaypath+"album/";
	public static String thumbnailpath=mydaypath+"thumbnail/";
	public static String journalthumbpath=mydaypath+"journalthumb/";
	public static int photoSize=258;
	
	/**
	 * 获得文件目录
	 * @param url 文件的URL
	 * @param imageDirectorys 文件目录列表
	 */
	public static void getDirectorys(String url,List<String> imageDirectorys)//得到url路径下所有图片目录
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
	 * 获得图片文件
	 * @param url 文件RL
	 * @param imagePath 图片文件路径列表
	 */
	public static void getFiles(String url, List<String> imagePath)//得到url路径下所有图片文件
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
	 * 判断是否是图片文件
	 * @param path 文件路径
	 * @return true 是图片文件, false 不是图片文件
	 */
	public static boolean isImageFile(String path)//判断是不是图片文件，根据后缀名
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
	 * 将文件以缩略图的形式保存
	 * @param path 文件路径
	 * @param dirname 名字
	 * @param photoname 照片名字
	 * @param image 图片
	 */
	public static void saveFileAsThumb(String path,String dirname,String photoname,Bitmap image)
	{
		 File destDir = new File(path+dirname);
		    if (!destDir.exists())
		    {
		     destDir.mkdirs();
		    }
					    String name=path+dirname+"/"+photoname+".jpg";
		File file=new File(path+dirname+"/"+photoname+".jpg");//创建文件对象
		try {
			file.createNewFile();//创建新文件
			System.out.println("after creat file:"+name);
			FileOutputStream fileOS=new FileOutputStream(file);//创建文件输出流对象	
			image=rotatePhoto(image);
			image.compress(Bitmap.CompressFormat.JPEG, 100,fileOS);//把图片内容压缩为JPEG格式输出到输出流对象
			fileOS.flush();//将缓冲区数据全部写道输出流中
			fileOS.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 旋转图片
	 * @param bm 需要旋转的图片
	 * @return 旋转之后的图片
	 */
	 public static Bitmap rotatePhoto(Bitmap bm)
	  {
		  Matrix m = new Matrix();  
        int width = bm.getWidth();  
        int height = bm.getHeight();  
        m.setRotate(90); // 旋转angle度  
        bm = Bitmap.createBitmap(bm, 0, 0, width, height,  
                m, true);// 从新生成图片
        System.out.println("rotate");
        return bm;
	  }
	 
	 /**
	  * 改变图片大小
	  * @param imagePath 图片路径
	  * @param maxH 最大高度
	  * @return 改变大小后的图片
	  */
	 public static Bitmap getResizeImage(String imagePath,int maxH){  
	        BitmapFactory.Options options = new BitmapFactory.Options();  
	                options.inJustDecodeBounds = true;  
	                // 获取这个图片的宽和高  
	               Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options); //此时返回bm为空  
	                    //计算缩放比  
	                int be = (int)(options.outHeight / (float)maxH);  
	                int ys = options.outHeight % maxH;//求余数  
	                float fe = ys / (float)maxH;  
	                if(fe>=0.5)be = be + 1;  
	                if (be <= 0)  
	                    be = 1;  
	                options.inSampleSize = be;  
	             
	                //重新读入图片，注意这次要把options.inJustDecodeBounds 设为 false  
	                options.inJustDecodeBounds = false;  
	                bitmap=BitmapFactory.decodeFile(imagePath,options);  
	                return bitmap;
	               // img.setImageBitmap(bitmap);  
	              //  img.setVisibility(View.VISIBLE);  
	    }
	 
	 /**
	  * 将真实路径转换成缩略图的路径
	  * @param realPaths 图片的真实路径
	  * @return 与真实路径对应的缩略图路径
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
