package com.myday.pictures;

import com.myday.file.FileHandle;

public class Journal extends Picture{

	String theme;//��־����
	public Journal(String picname) {
		super(picname);
		setPathFromName();
		
	}
	public Journal(){}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public void setByRealPath(String realpath)
	{
		this.realpath=realpath;
		String []s=realpath.split("/");
		this.dirname=s[5];
		this.picname=s[6];
		picname=picname.replace(".jpg", "");
		thumbpath=FileHandle.journalthumbpath+"/"+this.dirname+"/"+picname+".jpg";
	}
	@Override
	public void setPathFromName() {
		// TODO Auto-generated method stub
		String []s=picname.split(" ");
		realpath=FileHandle.journalpath+s[0]+"/"+picname+".jpg";//s[0]ΪͼƬ�����ļ���
		thumbpath=FileHandle.journalthumbpath+s[0]+"/"+picname+".jpg";
	}

}
