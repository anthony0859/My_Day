package com.myday.pictures;

public class PhotoAlbum extends Album {

	public PhotoAlbum(String realDirectory, String thumbDirectory) {
		super(realDirectory, thumbDirectory);
		// TODO Auto-generated constructor stub
	}
	public Photo getPhoto(String name)//ͨ����Ƭ���ֵõ���Ƭ
	{
		Photo p=new Photo(name);
		return p;
	}
}
