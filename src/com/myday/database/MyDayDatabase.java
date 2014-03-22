package com.myday.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.myday.file.FileHandle;
import com.myday.pictures.Journal;
import com.myday.pictures.Photo;

/**�����װ�����ݿ����*/
public class MyDayDatabase {

	/**����Ƭ�������� ���С�
	 * @param p ��Ƭ������
	 * */
	
	public static void savePhoto(Photo p)
	{
		
		
		SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(FileHandle.mydaypath+"myday_photo.db", null);
		try
		{	
			db.execSQL("insert into photoinfo values('"+p.getPicname()+"','"+p.getUser_say()+"','"+p.getLocation()+"','"+p.getPointinfo()+"')");
			//System.out.println("insert "+name+" , "+info+" ,"+address);
			db.close();
		}
		catch(SQLException se)
		{
			db.execSQL("create table photoinfo (name varchar(255), user_say varchar(255), location varchar(255),point_info varchar(255))");
			db.execSQL("insert into photoinfo values('"+p.getPicname()+"','"+p.getUser_say()+"','"+p.getLocation()+"','"+p.getPointinfo()+"')");
			db.close();
		}
	}
	/**
	 * ɾ����Ƭ��
	 * @param photoname ��Ƭ�ļ�������
	 */
	public static void deletePhoto(String photoname)
	{
		SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(FileHandle.mydaypath+"myday_photo.db", null);
		db.execSQL("delete from photoinfo where name='"+photoname+"'");
		db.close();
	}
	
	/**
	 * ɾ����־��
	 * @param journalname ��־�ļ�������
	 */
	public static void deleteJournal(String journalname)
	{
		//System.out.println("before delete journal");
		databaseShow();
		SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(FileHandle.mydaypath+"myday_journal.db", null);
		db.execSQL("delete from journalinfo where name='"+journalname+"'");
		db.close();
		//System.out.println("after delete journal");
		databaseShow();
	}
	
	/**
	 * ������־
	 * @param j ��־�ļ�������
	 */
	public static void saveJournal(Journal j)
	{
		SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(FileHandle.mydaypath+"myday_journal.db", null);
		try
		{	
			db.execSQL("insert into journalinfo values('"+j.getPicname()+"','"+j.getTheme()+"')");
			//System.out.println("insert "+name+" , "+info+" ,"+address);
			db.close();
		}
		catch(SQLException se)
		{
			db.execSQL("create table journalinfo (name varchar(255), theme varchar(255))");
			db.execSQL("insert into journalinfo values('"+j.getPicname()+"','"+j.getTheme()+"')");
			db.close();
		}
	}
	
	/**
	 * �����û�������Ƭ������
	 * @param photoname ��Ƭ�ļ�������
	 * @param say �û�������Ƭ������
	 */
	public static void updateUserSay(String photoname,String say)
	{
		SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(FileHandle.mydaypath+"myday_photo.db", null);
		db.execSQL("update photoinfo set user_say = '"+say+"' where name = '"+photoname+"' ");
		//System.out.println("update photoinfo set user_say = '"+say+"' where name = '"+photoname+"' ");
		//databaseShow();
		db.close();
	}
	
	/**
	 * 
	 * @param picname
	 * @param jour_theme
	 */
	public static void updateJournalTheme(String picname, String jour_theme) {
		// TODO Auto-generated method stub
		SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(FileHandle.mydaypath+"myday_journal.db", null);
		db.execSQL("update journalinfo set theme = '"+jour_theme+"' where name = '"+picname+"' ");
		//System.out.println("update photoinfo set user_say = '"+say+"' where name = '"+photoname+"' ");
		//databaseShow();
		db.close();
	}
	
	/**
	 * 
	 * @param journalname
	 * @return
	 */
	static public String searchJournalTheme(String journalname)
	{
		String s="";
		SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(FileHandle.mydaypath+"myday_journal.db", null);
		//db.execSQL("select info from myday.db where name ='"+photoname+"'");
		Cursor cursor=db.rawQuery("select theme from journalinfo where name ='"+journalname+"'", null);		
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
		{
			int i=cursor.getColumnIndex("theme");
			//System.out.println("in cursor "+i);
			s=cursor.getString(i);
		}		
		cursor.close();
		//System.out.println("***"+s);
		db.close();
		//databaseShow();
		return s;
	}
	
	/**
	 * ���µص���Ϣ
	 * @param photoname ��Ƭ�ļ�����
	 * @param loc �ص�
	 * @param point_info ��γ��
	 */
	public static void updateLocInfo(String photoname,String loc,String point_info)
	{
		SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(FileHandle.mydaypath+"myday_photo.db", null);
		db.execSQL("update photoinfo set location = '"+loc+"' where name = '"+photoname+"' ");
		db.execSQL("update photoinfo set point_info = '"+point_info+"' where name = '"+photoname+"' ");
		//System.out.println("update photoinfo set user_say = '"+say+"' where name = '"+photoname+"' ");
		//databaseShow();
		db.close();
	}
	
	/**
	 * �����û�����Ƭ������
	 * @param photoname ��Ƭ�ļ�����
	 * @return ����Ƭ������
	 */
	static public String searchUserSay(String photoname)
	{
		String s="";
		SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(FileHandle.mydaypath+"myday_photo.db", null);
		//db.execSQL("select info from myday.db where name ='"+photoname+"'");
		Cursor cursor=db.rawQuery("select user_say from photoinfo where name ='"+photoname+"'", null);
		//System.out.println("in search "+photoname+" cursor num "+cursor.getColumnCount());
		/*
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
		{
			int i=cursor.getColumnIndex("user_say");
			System.out.println("in cursor "+i);
			s=cursor.getString(i);
		}
		*/
		while(cursor.moveToNext())
		{
			int i=cursor.getColumnIndex("user_say");
			//System.out.println("in cursor "+i);
			s=cursor.getString(i);
		}
		cursor.close();
		//System.out.println("***"+s);
		db.close();
		//databaseShow();
		return s;
	}
	
	/**
	 * ��������Ƭ���û������Լ��ص���Ϣ
	 * @param photoname ��Ƭ�ļ�������
	 * @return ����͵ص�
	 */
		static public String[] searchUserInfo(String photoname)//���ظ���Ƭ���û�����͵ص�
	{
		String[] s=new String[2];
		SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(FileHandle.mydaypath+"myday_photo.db", null);
		//db.execSQL("select info from myday.db where name ='"+photoname+"'");
		Cursor cursor=db.rawQuery("select user_say,location from photoinfo where name ='"+photoname+"'", null);
		//System.out.println("in search "+photoname+" cursor num "+cursor.getColumnCount());
		int i,j=0;
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
		{
			i=cursor.getColumnIndex("user_say");
			j=cursor.getColumnIndex("location");
			//System.out.println("in cursor "+i);
			s[0]=cursor.getString(i);
			s[1]=cursor.getString(j);
		}
		
		cursor.close();
		//System.out.println("***"+s);
		db.close();
		//databaseShow();
		return s;
	}
		
		/**
		 * �����Ƭ�ĵص㾭γ���б�
		 * @return 
		 */
	public static List<String[]> getHistoryLoc()//mapΪ ͼƬ��  ͼƬ��point_info
	{
		List<String[]> set=new ArrayList<String[]>(); 
		SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(FileHandle.mydaypath+"myday_photo.db", null);
		Cursor cursor=db.rawQuery("select name,point_info from photoinfo ", null);
		int i,j=0;
		
		
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
		{
			String[]s=new String[2];
			i=cursor.getColumnIndex("name");
			j=cursor.getColumnIndex("point_info");
			//System.out.println("in cursor "+i);
			s[0]=cursor.getString(i);
			s[1]=cursor.getString(j);
			set.add(s);
			
		}
		
		cursor.close();
		//System.out.println("***"+s);
		db.close();
		return set;
	}
	
	/**
	 * ������ָ����γ���������Ƭ
	 * @param lat ����
	 * @param lon γ��
	 * @return ��ָ����γ���������Ƭ�б�
	 */
	public static List<String[]> searchPhotoFromPoint(String lat,String lon)
	{
		List<String[]> list = new ArrayList<String[]>();
		String point_info=lat+":"+lon;
		SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(FileHandle.mydaypath+"myday_photo.db", null);
		Cursor cursor=db.rawQuery("select name,location,point_info from photoinfo ", null);
		int i,j,k=0;
		String temp;
		
		String new_lat="";
		for(int m=0;m<lat.length()-1;m++)
			new_lat+=lat.charAt(m);
		//System.out.println("select name,location,point_info from photoinfo "+new_lat);
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
		{
			String[]s=new String[2];
			i=cursor.getColumnIndex("name");
			j=cursor.getColumnIndex("location");
			k=cursor.getColumnIndex("point_info");
			//System.out.println("in cursor "+i);
			s[0]=cursor.getString(i);
			s[1]=cursor.getString(j);
			temp=cursor.getString(k);
			System.out.println("getString(k) "+temp);
			if(temp!=null)
			if(temp.contains(new_lat))
				{
					list.add(s);
					System.out.println("add "+s[0]+","+s[1]);
				}
			
		}
		
		cursor.close();
		//System.out.println("***"+s);
		db.close();
		return list;
	}
	/**
	 * ����ĳ���ĵ���Ƭ������ѡ��ķ���������
	 * @param date ����
	 * @return ����Ƭ�ص㶼��ͬ���򷵻ز�����������Ƭ������ÿ���ص����ѡ��һ����Ƭ����������Ƭ�������б�
	 */
	public List<String> searchPhotoByDate(String date)
	{
		List resultList = new ArrayList<String>();
		SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(FileHandle.mydaypath+"myday_photo.db", null);
		Cursor cursor = db.rawQuery("select * from photoinfo where name like "+"'%"+date+"%'", null);
		List<LocationSet> list = new ArrayList<LocationSet>();
		String location;
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
			boolean tag = false;
			//System.out.println("hello----");
			System.out.println(cursor.getString(cursor.getColumnIndex("name")));
			location = cursor.getString(cursor.getColumnIndex("location"));
			for(int i=0;i<list.size();i++){
				if(list.get(i).compaire(location)){
					list.get(i).add(cursor.getString(cursor.getColumnIndex("name")));
					tag = true;
					break;
				}
			}
			if(tag == false){
				LocationSet ls = new LocationSet(location);
				ls.add(cursor.getString(cursor.getColumnIndex("name")));
				list.add(ls);
				
			}	
		}
		if(list.size() == 1){
			int a1 = 4;
			int num = 0;
			if(a1 > list.get(0).getSize())
				num = list.get(0).getSize();
			else num = a1;
			for(int s=0;s<num;s++){
				resultList.add(list.get(0).getName(s));
			}
			System.out.println("---1---size is "+num);
			
			
		}
		else{
			for(int j=0;j<list.size();j++){
				int size = list.get(j).getSize();
				int temp = (int)(Math.random()*size);
				resultList.add(list.get(j).getName(temp));
			}
			System.out.println("---2----size is "+resultList.size());
		}
			
		
		return resultList;
		
		
	}
	
	/**
	 * ���澭γ��
	 * @param lat ����
	 * @param lon γ��
	 */
	public static void saveGeoPoint(double lat,double lon)
	{
		SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(FileHandle.mydaypath+"myday_lastGeoPoint.db", null);
		try
		{	
			db.execSQL("update lastpoint set latitude='"+lat+"',longitude='"+lon+"' where mid ='"+1+"')");
			//System.out.println("insert "+name+" , "+info+" ,"+address);
			db.close();
		}
		
		catch(SQLException se)
		{
			
			db.execSQL("create table if not exists lastpoint (mid int,latitude varchar(255), longitude varchar(255))");
			db.execSQL("insert into lastpoint values('"+1+"','"+0+"','"+0+"')");
			//db.execSQL("insert into lastpoint values('"+p.getPicname()+"','"+p.getUser_say()+"','"+p.getLocation()+"','"+p.getPointinfo()+"')");
			db.close();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public static double[]  getLastGeopoint()
	{
		double[] points=new double[2]; 
		SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(FileHandle.mydaypath+"myday_lastGeoPoint.db", null);
		Cursor cursor=db.rawQuery("select latitude,longitude from lastpoint ", null);
		int i,j=0;
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
		{
			String[]s=new String[2];
			i=cursor.getColumnIndex("latitude");
			j=cursor.getColumnIndex("longitude");
			//System.out.println("in cursor "+i);
			s[0]=cursor.getString(i);
			s[1]=cursor.getString(j);
			points[0]=Double.parseDouble(s[0]);
			points[1]=Double.parseDouble(s[1]);
		}
		
		cursor.close();
		//System.out.println("***"+s);
		db.close();
		return points;
	}
	
	/**
	 * 
	 */
	public static  void databaseShow()
	{
		SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(FileHandle.mydaypath+"myday_photo.db", null);
		//db.execSQL("select info from myday.db where name ='"+photoname+"'");
		Cursor cursor=db.rawQuery("select * from photoinfo ", null);
		//System.out.println("cursor num "+cursor.getColumnCount());
		String s="";
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
		{   int i0=cursor.getColumnIndex("name");
			int i1=cursor.getColumnIndex("user_say");
			int i2=cursor.getColumnIndex("location");
			int i3=cursor.getColumnIndex("point_info");
			s=cursor.getString(i0);
			s+=","+cursor.getString(i1)+","+cursor.getString(i2)+","+cursor.getString(i3);
			//System.out.println(s);
			s="";
		}
		cursor.close();
		//System.out.println("***"+s);
		db.close();
	}
	
	
	public class LocationSet{
		private List<String> namelist;
		private String location;
		
		public LocationSet(String location){
			this.location = location;
			namelist = new ArrayList<String>();
		}
		
		public void add(String name){
			namelist.add(name);
			
		}
		
		public int getSize(){
			return namelist.size();
		}
		
		public String getName(int index){
			return namelist.get(index);
		}
		
		public boolean compaire(String l){
			if(location.equals(l)) return true;
			else return false;
		}
	}
	

}
