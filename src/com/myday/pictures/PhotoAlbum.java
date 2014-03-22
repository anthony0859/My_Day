package com.myday.pictures;

public class PhotoAlbum extends Album {

	public PhotoAlbum(String realDirectory, String thumbDirectory) {
		super(realDirectory, thumbDirectory);
		// TODO Auto-generated constructor stub
	}
	public Photo getPhoto(String name)//通过照片名字得到照片
	{
		Photo p=new Photo(name);
		return p;
	}
}
