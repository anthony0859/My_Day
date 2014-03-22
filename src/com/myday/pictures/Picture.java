package com.myday.pictures;

import com.myday.file.FileHandle;

public abstract class Picture {

	/**照片名字*/
	String picname;
	
	/**文件夹名字*/
	String dirname;//文件夹名
	
	/**图片路径*/
	String realpath;//大图片路径
	
	/**缩略图路径*/
	String thumbpath;//缩略图路径
	
	/**
	 * 带参构造
	 * @param picname 照片名字
	 * @param realpath 照片路径
	 * @param thumbpath 缩略图路径
	 */
	public Picture(String picname, String realpath, String thumbpath) {
		super();
		this.picname = picname;
		this.realpath = realpath;
		this.thumbpath = thumbpath;
	}
	public Picture()
	{
		
	}
	
	/**
	 * 带参构造
	 * @param picname 照片名字
	 */
	public Picture(String picname) {
		super();
		this.picname = picname;
		String []s=picname.split(" ");
		dirname=s[0];
	}
	
	/**
	 * 获取文件夹名字
	 * @return 文件夹名字
	 */
	public String getDirname() {
		return dirname;
	}
	
	/**
	 * 设置文件夹名字
	 * @param dirname 文件夹名字
	 */
	public void setDirname(String dirname) {
		this.dirname = dirname;
	}
	
	/**
	 * 获取照片名字
	 * @return 照片名字
	 */
	public String getPicname() {
		return picname;
	}
	
	/**
	 * 设置照片名字
	 * @param picname 照片名字
	 */
	public void setPicname(String picname) {
		this.picname = picname;
	}
	
	/**
	 * 获取照片路径
	 * @return 照片路径
	 */
	public String getRealpath() {
		return realpath;
	}
	
	/**
	 * 设置照片路径
	 * @param realpath 照片路径
	 */
	public void setRealpath(String realpath) {
		this.realpath = realpath;
	}
	
	/**
	 * 获取缩略图路径
	 * @return 缩略图路径
	 */
	public String getThumbpath() {
		return thumbpath;
	}
	
	/**
	 * 设置缩略图路径
	 * @param thumbpath 缩略图路径
	 */
	public void setThumbpath(String thumbpath) {
		this.thumbpath = thumbpath;
	}
	abstract public void setPathFromName();
	public abstract void setByRealPath(String realpath);
}
