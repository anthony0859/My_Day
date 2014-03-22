package com.myday.pictures;

import com.myday.file.FileHandle;

public abstract class Picture {

	/**��Ƭ����*/
	String picname;
	
	/**�ļ�������*/
	String dirname;//�ļ�����
	
	/**ͼƬ·��*/
	String realpath;//��ͼƬ·��
	
	/**����ͼ·��*/
	String thumbpath;//����ͼ·��
	
	/**
	 * ���ι���
	 * @param picname ��Ƭ����
	 * @param realpath ��Ƭ·��
	 * @param thumbpath ����ͼ·��
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
	 * ���ι���
	 * @param picname ��Ƭ����
	 */
	public Picture(String picname) {
		super();
		this.picname = picname;
		String []s=picname.split(" ");
		dirname=s[0];
	}
	
	/**
	 * ��ȡ�ļ�������
	 * @return �ļ�������
	 */
	public String getDirname() {
		return dirname;
	}
	
	/**
	 * �����ļ�������
	 * @param dirname �ļ�������
	 */
	public void setDirname(String dirname) {
		this.dirname = dirname;
	}
	
	/**
	 * ��ȡ��Ƭ����
	 * @return ��Ƭ����
	 */
	public String getPicname() {
		return picname;
	}
	
	/**
	 * ������Ƭ����
	 * @param picname ��Ƭ����
	 */
	public void setPicname(String picname) {
		this.picname = picname;
	}
	
	/**
	 * ��ȡ��Ƭ·��
	 * @return ��Ƭ·��
	 */
	public String getRealpath() {
		return realpath;
	}
	
	/**
	 * ������Ƭ·��
	 * @param realpath ��Ƭ·��
	 */
	public void setRealpath(String realpath) {
		this.realpath = realpath;
	}
	
	/**
	 * ��ȡ����ͼ·��
	 * @return ����ͼ·��
	 */
	public String getThumbpath() {
		return thumbpath;
	}
	
	/**
	 * ��������ͼ·��
	 * @param thumbpath ����ͼ·��
	 */
	public void setThumbpath(String thumbpath) {
		this.thumbpath = thumbpath;
	}
	abstract public void setPathFromName();
	public abstract void setByRealPath(String realpath);
}
