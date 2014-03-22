package com.myday.pictures;

public class JournalAlbum extends Album {

	public JournalAlbum(String realDirectory, String thumbDirectory) {
		super(realDirectory, thumbDirectory);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 获取日志
	 * @param name 日志路径名字
	 * @return 日志文件
	 */
	public Journal getJournal(String name)//通过日志路径名字得到日志
	{
		Journal j=new Journal(name);
		return j;
	}

}
