package com.myday.pictures;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.myday.file.FileHandle;

public class Album implements Serializable{
	List<String> realpaths;//相册图片路径列表
	List<String> thumbpaths;//缩略图路径列表
	String realDirectory;//相册的目录绝对路径 /mnt/sdcard/MyDay/.../日期
	String thumbDirectory;//缩略图目录绝对路径
	
	/**
	 * 
	 * @param realDirectory 相册图片路径列表
	 * @param thumbDirectory 缩略图路径列表
	 */
	public Album(String realDirectory, String thumbDirectory) {
		super();
		this.realDirectory = realDirectory;
		this.thumbDirectory = thumbDirectory;
		realpaths=new ArrayList<String>();
		thumbpaths=new ArrayList<String>();
		getBothPath();
	}
	public Album()
	{
		realpaths=new ArrayList<String>();
		thumbpaths=new ArrayList<String>();
	}
	
	/**
	 * 设置图片路径
	 * @param samePointPhotoRealPaths 图片路径
	 */
	public void setRealpaths(List<String> samePointPhotoRealPaths) {
		this.realpaths = samePointPhotoRealPaths;
	}
	
	/**
	 * 获取缩略图路径列表
	 * @return 缩略图路径列表
	 */
	public List<String> getThumbpaths() {
		return thumbpaths;
	}
	
	/**
	 * 获取相册图片路径列表
	 * @return 相册图片路径列表
	 */
	public List<String> getRealpaths() {
		return realpaths;
	}
	
	/**
	 * 设置缩略图路径
	 * @param samePointPhotoThumbPaths 缩略图路径列表
	 */
	public void setThumbpaths(List<String> samePointPhotoThumbPaths) {
		this.thumbpaths = samePointPhotoThumbPaths;
	}
	
	/**
	 * 获取相册目录的绝对路径
	 * @return 相册目录的绝对路径
	 */
	public String getRealDirectory() {
		return realDirectory;
	}
	
	/**
	 * 设置相册目录的绝对路径
	 * @param realDirectory 相册目录的绝对路径
	 */
	public void setRealDirectory(String realDirectory) {
		this.realDirectory = realDirectory;
	}
	
	/**
	 * 获取缩略图目录的绝对路径
	 * @return 缩略图目录的绝对路径
	 */
	public String getThumbDirectory() {
		return thumbDirectory;
	}
	
	/**
	 * 设置缩略图目录的绝对路径
	 * @param thumbDirectory 缩略图目录的绝对路径
	 */
	public void setThumbDirectory(String thumbDirectory) {
		this.thumbDirectory = thumbDirectory;
	}
	
	/**
	 * 
	 */
	public void getBothPath()
	{
		FileHandle.getFiles(realDirectory, realpaths);
		FileHandle.getFiles(thumbDirectory, thumbpaths);
	}
	
	/**
	 * 获取相册图片路径数
	 * @return 相册图片路径数
	 */
	public int size()
	{
		return realpaths.size();
	}
}
