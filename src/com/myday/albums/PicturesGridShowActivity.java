package com.myday.albums;


import java.util.ArrayList;



import com.example.my__day.R;
import com.myday.database.MyDayDatabase;
import com.myday.pictures.Album;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PicturesGridShowActivity extends Activity {
	
	GridView gridView;
	ImageView editView;
	Bitmap bm;
	boolean ifJournalShow;
	Album album;
	Button edit;
	TextView photoalbum_time;
	//ArrayList<String> thumbPath=new ArrayList<String>();//缩略图
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);   
        setContentView(R.layout.pictures_grid);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.gridshow_titlebar);  //titlebar为自己标题栏的布局
        Intent intent=getIntent();
        String realdir=intent.getStringExtra("realdirpath");
        String thumbdir=intent.getStringExtra("thumbdirpath");
        System.out.println("in gridshow "+realdir+","+thumbdir);
        album=new Album(realdir,thumbdir);
        ifJournalShow=intent.getBooleanExtra("ifJournal",false);
        gridView=(GridView)findViewById(R.id.gridView);
//        editView=(ImageView)findViewById(R.id.edit);
//        editView.setOnClickListener(new EditViewListener());
       edit=(Button)findViewById(R.id.edit);
       edit.setOnClickListener(new EditViewListener());
       photoalbum_time=(TextView)findViewById(R.id.photoalbum_time);
       String[]s=realdir.split("/");
       photoalbum_time.setText("拍摄于："+s[5]);
		gridView.setAdapter(new GridViewAdapter(this));
//		Toast toast=new Toast(this);
//		toast.makeText(this, "点击图片可修改心情", 0);
//		toast.show();
   }
	class GridViewAdapter extends BaseAdapter
	{
		private Context mContext; 
		 public GridViewAdapter(Context c) {
		        mContext = c;
		    }
		@Override
		public View getView(final int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			 View view = View.inflate(mContext, R.layout.inter_gridview, null);
             RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.relaGrid);
			ImageView image=(ImageView)view.findViewById(R.id.singleImage);
			TextView tv=(TextView)view.findViewById(R.id.image_info);
			 
			if(arg1==null)
			{
				image=new ImageView(PicturesGridShowActivity.this);
				image.setAdjustViewBounds(true);
				image.setMaxWidth(200);
				image.setMaxHeight(200);
				image.setPadding(5, 5, 5, 5);//设置内边距
				
			}
//			else
//			{	
//				image=(ImageView)arg1.findViewById(R.id.singleImage);
//			
//			}
			 //String thumbpath=FilesHandle.bigpicpathToThumbpath(imagePath.get(arg0));
			 bm=null;
			//if(ifJournalShow)
				bm =BitmapFactory.decodeFile(album.getThumbpaths().get(arg0));
			//else
			//	bm =BitmapFactory.decodeFile(thumbPath.get(arg0));
			 image.setImageBitmap(bm);
		
			image.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
									            //b.getWidth(
		            Intent intent = new Intent();
		            intent.putExtra("ifCamera", false);
		            String path=album.getRealpaths().get(arg0);
		            intent.putExtra("photorealpath", path);
		          //得到图片后，我们在另一个activity中显示 
		            if(path.contains("album"))
		            	intent.setClass(PicturesGridShowActivity.this, SingleImageActivity.class); 
		            else if(path.contains("journal"))
		            	intent.setClass(PicturesGridShowActivity.this, JournalShowActivity.class);
		            startActivity(intent); 
		            //if(path.contains("album"))
		             finish();
				}
			});
			String []p=album.getRealpaths().get(arg0).split("/");
			p[6]=p[6].replace(".jpg", "");//获取查询数据库的图片名
			//System.out.println("gridshow "+p[6]);
			String s0="**";
			if(ifJournalShow)
				{
				 s0=MyDayDatabase.searchJournalTheme(p[6]);
				 //System.out.println("gridshow s0"+s0);
				 if(!s0.equals("null"))
				 tv.setText(s0);
				 else
					 tv.setText("暂无描述");
				}
			else
			{
				s0=MyDayDatabase.searchUserSay(p[6]);
				//System.out.println("gridshow s0"+s0);
				if(!s0.equals("null"))
				tv.setText(s0);
				 else
					 tv.setText("暂无描述");
			}
			return rl;
		}
		
		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}
		
		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return album.size();
		}
	}
class EditViewListener implements OnClickListener
{

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		 Intent intent = new Intent();
           //在另一个activity中编辑图片 
		 Bundle b=new Bundle();
		 b.putSerializable("album", album);
		 //b.putStringArrayList("tpaths",album.getThumbpaths());
        intent.setClass(PicturesGridShowActivity.this, AlbumEditActivity.class); 
        intent.putExtras(b); 
        startActivity(intent); 
        finish();
	}
	}
 
}
