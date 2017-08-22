
// Leo @ 2010/09/23

package com.mmclub.mreader;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class ReadBookActivity extends Activity{
	/** Called when the activity is first created. */
	
	public static ReadBookActivity instance = null;
	
	private PageWidget mPageWidget;
	Bitmap mCurPageBitmap, mNextPageBitmap;
	Canvas mCurPageCanvas, mNextPageCanvas;
	BookPageFactory pagefactory;
	
	int textColor;

	final int MENU_BOOKMARK = Menu.FIRST;
	final int MENU_BACK = Menu.FIRST+1;
	final int MENU_VERSION = Menu.FIRST+2;
	final int MENU_SETTING = Menu.FIRST+3;
	
	int charpter;
	int charpter_begin = 0;
	int charpter_end = 0;


	String strSelection = "";		// string chosen by user
	String strTxt = "";				// string for visualization
	String strPath = "";			// full file path
	int position = 0;				// current reading position
	int markPos = 0;				// position of bookmark
	
	boolean isInSearching = false;
	boolean hasBookMark = false;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		instance = this;
		
		WindowManager windowManager = getWindowManager();   
		Display display = windowManager.getDefaultDisplay(); 
		
		int width = display.getWidth();   
		int height = display.getHeight();
		
		SharedPreferences spSetting = getSharedPreferences(SettingActivity.m_READER_PREF , 0);
		String fontColor = spSetting.getString(SettingActivity.PREF_TAG_FONT_COLOR, "Black");
		int fontSize = (int) spSetting.getFloat(SettingActivity.PREF_TAG_FONT_SIZE, 25f);
		float scrBrightness = spSetting.getFloat(SettingActivity.PREF_TAG_SCREEN_BRIGHTNESS, 1.0f);     
		
		if (fontColor == "White")
			textColor = Color.WHITE;
		else if (fontColor == "Black")
			textColor = Color.BLACK;
		else if (fontColor == "Red")
			textColor = Color.RED;
		else if (fontColor == "Green")
			textColor = Color.GREEN;
		else if (fontColor == "Blue")
			textColor = Color.BLUE;
		
        //Screen brightness
		WindowManager.LayoutParams lp = getWindow().getAttributes();  
		lp.screenBrightness = scrBrightness;  
		
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		mPageWidget = new PageWidget(this);
		mPageWidget.getPix(width, height);
		setContentView(mPageWidget);
		
		mCurPageBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		mNextPageBitmap = Bitmap
				.createBitmap(width, height, Bitmap.Config.ARGB_8888);

		mCurPageCanvas = new Canvas(mCurPageBitmap);
		mNextPageCanvas = new Canvas(mNextPageBitmap);
		pagefactory = new BookPageFactory(width, height, fontSize, textColor);

		
		pagefactory.setBgBitmap(BitmapFactory.decodeResource(
			this.getResources(), R.drawable.bg2));

		try {
//			pagefactory.openbook(strPath);
			try{
			Bundle tp = getIntent().getExtras();
			charpter = tp.getInt("charpter");
			
			Bundle back = getIntent().getExtras();
			charpter_begin = back.getInt("BEGIN");
			charpter_end = back.getInt("END");
			}catch(Exception e){
				
			}
			if((charpter_begin == 0)&&(charpter_end == 0))
				pagefactory.opencharpter("/sdcard/1.txt",charpter);
			else
				pagefactory.openbymark("/sdcard/1.txt", charpter_begin, charpter_end);
			pagefactory.onDraw(mCurPageCanvas);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			Toast.makeText(this, "Ebook does not exist",
					Toast.LENGTH_SHORT).show();
		}
		
        Intent q = new Intent();
        try{
        if (q.getAction().equals("activity.close")) {
        	 	this.finish();
        	}
        }catch(Exception e){	
        }
		
		mPageWidget.setBitmaps(mCurPageBitmap, mCurPageBitmap);

		
		mPageWidget.setOnTouchListener(new OnTouchListener() {
			//@Override
			public boolean onTouch(View v, MotionEvent e) {
				// TODO Auto-generated method stub
				
				boolean ret=false;
				if (v == mPageWidget) {
					if (e.getAction() == MotionEvent.ACTION_DOWN) {
						mPageWidget.abortAnimation();
						mPageWidget.calcCornerXY(e.getX(), e.getY());

						pagefactory.onDraw(mCurPageCanvas);
						if (mPageWidget.DragToRight()) {
							try {
								pagefactory.prePage();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}						
							if(pagefactory.isfirstPage())return false;
							pagefactory.onDraw(mNextPageCanvas);
						} else {
							try {
								pagefactory.nextPage();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							if(pagefactory.islastPage())return false;
							pagefactory.onDraw(mNextPageCanvas);
						}
						mPageWidget.setBitmaps(mCurPageBitmap, mNextPageBitmap);
					}
                 
					 ret = mPageWidget.doTouchEvent(e);
					return ret;
				}
				return false;
			}

		});
	}
	
	public boolean onKeyDown(int keyCode,KeyEvent event) {

		 if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
		//overwrite exit
			Intent turnback = new Intent(ReadBookActivity.this, OpenCharpter.class); 
			startActivity(turnback);
			ReadBookActivity.this.finish();
		return true;
		}
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_BOOKMARK, 0, "Bookmark");
		menu.add(0, MENU_BACK, 0, "Contents");
		menu.add(0, MENU_VERSION, 0, "About");
		menu.add(0, MENU_SETTING, 0, "Settings");
		return true;
	}
	
	// Main menu activities
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case MENU_BOOKMARK:
			// Go to bookmark manager Activity
			Intent i = new Intent(ReadBookActivity.this, BookMarkActivity.class);
			Bundle b = new Bundle();
			int begin = pagefactory.sendBegin();
			int end = pagefactory.sendEnd();
			b.putInt("BEGIN",begin);
			b.putInt("END",end);
	        i.putExtras(b);
	        startActivityForResult(i, 1);
			break;
			
		case MENU_BACK:
			Intent c = new Intent(ReadBookActivity.this, OpenCharpter.class);
			startActivityForResult(c, 0);
			this.finish();
			break;
			
		case MENU_VERSION:
			Intent v = new Intent(ReadBookActivity.this, Version.class);
			startActivityForResult(v, 0);
		    break;
		
		case MENU_SETTING:
//			Intent s = new Intent(ReadBookActivity.this, SettingActivity.class);
//			startActivityForResult(s, 1);
			Bundle t = new Bundle();
			Intent s = new Intent(ReadBookActivity.this, SettingActivity.class);
			int s_begin = pagefactory.sendBegin();
			int s_end = pagefactory.sendEnd();
			t.putInt("BEGIN", s_begin);
			t.putInt("END", s_end);			
			s.putExtras(t);
			startActivity(s);
			break;
			
		default:
			break;
		}
		
		
		return false;
	    }
	}