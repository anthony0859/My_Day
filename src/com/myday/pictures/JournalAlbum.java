package com.myday.pictures;

public class JournalAlbum extends Album {

	public JournalAlbum(String realDirectory, String thumbDirectory) {
		super(realDirectory, thumbDirectory);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * ��ȡ��־
	 * @param name ��־·������
	 * @return ��־�ļ�
	 */
	public Journal getJournal(String name)//ͨ����־·�����ֵõ���־
	{
		Journal j=new Journal(name);
		return j;
	}

}
