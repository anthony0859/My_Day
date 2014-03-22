package com.myday.pictures;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.myday.file.FileHandle;

public class Album implements Serializable{
	List<String> realpaths;//���ͼƬ·���б�
	List<String> thumbpaths;//����ͼ·���б�
	String realDirectory;//����Ŀ¼����·�� /mnt/sdcard/MyDay/.../����
	String thumbDirectory;//����ͼĿ¼����·��
	
	/**
	 * 
	 * @param realDirectory ���ͼƬ·���б�
	 * @param thumbDirectory ����ͼ·���б�
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
	 * ����ͼƬ·��
	 * @param samePointPhotoRealPaths ͼƬ·��
	 */
	public void setRealpaths(List<String> samePointPhotoRealPaths) {
		this.realpaths = samePointPhotoRealPaths;
	}
	
	/**
	 * ��ȡ����ͼ·���б�
	 * @return ����ͼ·���б�
	 */
	public List<String> getThumbpaths() {
		return thumbpaths;
	}
	
	/**
	 * ��ȡ���ͼƬ·���б�
	 * @return ���ͼƬ·���б�
	 */
	public List<String> getRealpaths() {
		return realpaths;
	}
	
	/**
	 * ��������ͼ·��
	 * @param samePointPhotoThumbPaths ����ͼ·���б�
	 */
	public void setThumbpaths(List<String> samePointPhotoThumbPaths) {
		this.thumbpaths = samePointPhotoThumbPaths;
	}
	
	/**
	 * ��ȡ���Ŀ¼�ľ���·��
	 * @return ���Ŀ¼�ľ���·��
	 */
	public String getRealDirectory() {
		return realDirectory;
	}
	
	/**
	 * �������Ŀ¼�ľ���·��
	 * @param realDirectory ���Ŀ¼�ľ���·��
	 */
	public void setRealDirectory(String realDirectory) {
		this.realDirectory = realDirectory;
	}
	
	/**
	 * ��ȡ����ͼĿ¼�ľ���·��
	 * @return ����ͼĿ¼�ľ���·��
	 */
	public String getThumbDirectory() {
		return thumbDirectory;
	}
	
	/**
	 * ��������ͼĿ¼�ľ���·��
	 * @param thumbDirectory ����ͼĿ¼�ľ���·��
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
	 * ��ȡ���ͼƬ·����
	 * @return ���ͼƬ·����
	 */
	public int size()
	{
		return realpaths.size();
	}
}
