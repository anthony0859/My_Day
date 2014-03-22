package com.myday.pictures;

import com.myday.file.FileHandle;

public class Photo extends Picture{
	/**用户描述*/
	String user_say;
	
	/**地点信息*/
	String location;
	
	/**经纬度*/
	String pointinfo;
	
	/**
	 * 获取经纬度
	 * @return 经纬度
	 */
	public String getPointinfo() {
		return pointinfo;
	}
	
	/**
	 * 设置经纬度
	 * @param pointinfo 经纬度
	 */
	public void setPointinfo(String pointinfo) {
		this.pointinfo = pointinfo;
	}
	
	/**
	 * 带参构造
	 * @param photoname 照片文件名字
	 */
	public Photo(String photoname)
	{
		super(photoname);
		setPathFromName();
	}
	public Photo ()
	{
		
	}
	
	/**
	 * 获取用户描述信息
	 * @return 用户描述信息
	 */
	public String getUser_say() {
		return user_say;
	}
	
	/**
	 * 设置用户描述信息
	 * @param user_say 用户描述信息
	 */
	public void setUser_say(String user_say) {
		this.user_say = user_say;
	}
	
	/**
	 * 获取地点信息
	 * @return 地点信息
	 */
	public String getLocation() {
		return location;
	}
	
	/**
	 * 设置地点信息
	 * @param location 地点信息
	 */
	public void setLocation(String location) {
		System.out.println("in set loc "+location);
		this.location = location;
	}
	
	/**
	 * 设置实际路径
	 * @param realpath 实际路径
	 */
	public void setByRealPath(String realpath)
	{
		this.realpath=realpath;
		//System.out.println("setByRealPath "+realpath);
		String []s=realpath.split("/");
		
		this.dirname=s[5];
		this.picname=s[6];
		System.out.println("in setbyrealpath "+picname);
		picname=picname.replace(".jpg", "");
		thumbpath=FileHandle.thumbnailpath+"/"+this.dirname+"/"+picname+".jpg";
	}
	
	/**
	 * 从照片名字设置路径
	 */
	public void setPathFromName()
	{
		String []s=picname.split(" ");
		realpath=FileHandle.albumpath+s[0]+"/"+picname+".jpg";//s[0]为图片所在文件夹
		thumbpath=FileHandle.thumbnailpath+s[0]+"/"+picname+".jpg";
	}
}
