package com.myday.pictures;

import com.myday.file.FileHandle;

public class Photo extends Picture{
	/**�û�����*/
	String user_say;
	
	/**�ص���Ϣ*/
	String location;
	
	/**��γ��*/
	String pointinfo;
	
	/**
	 * ��ȡ��γ��
	 * @return ��γ��
	 */
	public String getPointinfo() {
		return pointinfo;
	}
	
	/**
	 * ���þ�γ��
	 * @param pointinfo ��γ��
	 */
	public void setPointinfo(String pointinfo) {
		this.pointinfo = pointinfo;
	}
	
	/**
	 * ���ι���
	 * @param photoname ��Ƭ�ļ�����
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
	 * ��ȡ�û�������Ϣ
	 * @return �û�������Ϣ
	 */
	public String getUser_say() {
		return user_say;
	}
	
	/**
	 * �����û�������Ϣ
	 * @param user_say �û�������Ϣ
	 */
	public void setUser_say(String user_say) {
		this.user_say = user_say;
	}
	
	/**
	 * ��ȡ�ص���Ϣ
	 * @return �ص���Ϣ
	 */
	public String getLocation() {
		return location;
	}
	
	/**
	 * ���õص���Ϣ
	 * @param location �ص���Ϣ
	 */
	public void setLocation(String location) {
		System.out.println("in set loc "+location);
		this.location = location;
	}
	
	/**
	 * ����ʵ��·��
	 * @param realpath ʵ��·��
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
	 * ����Ƭ��������·��
	 */
	public void setPathFromName()
	{
		String []s=picname.split(" ");
		realpath=FileHandle.albumpath+s[0]+"/"+picname+".jpg";//s[0]ΪͼƬ�����ļ���
		thumbpath=FileHandle.thumbnailpath+s[0]+"/"+picname+".jpg";
	}
}
