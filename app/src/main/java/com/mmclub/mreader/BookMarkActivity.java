// Leo @ 2010/10/06

package com.mmclub.mreader;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnDismissListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;


public class BookMarkActivity extends Activity{
	
	Button btnAdd;
	Button btnClear;
	Button btnGo;
	
	AddBookMarkDlg addBookMarkDlg;
	OnClickListener oclClick;
	
    public static final String DB_NAME = "bm7.db";
    public static final int VERSION = 1;
    
    BookMarkDBHelper dbHelper;
    SQLiteDatabase db;
    
    Cursor c;
    Spinner s;
    
    // Bookmark
    String bookName = "";		// Name of book
    int begin = 0;	
    int end = 0;

    int tobegin = 0;	
    int toend = 0;

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookmark);
    	
        // Get controller
        btnAdd = (Button)this.findViewById(R.id.bookmark_btn_add);
        btnClear = (Button)this.findViewById(R.id.bookmark_btn_clear);
        btnGo = (Button)this.findViewById(R.id.bookmark_btn_go);
        s = (Spinner)findViewById(R.id.bookmark_spinner_list);
        
        // Get book name and position from bundle
        Bundle b = getIntent().getExtras();
        begin = b.getInt("BEGIN");
        end = b.getInt("END");
        
        /// Set Database
        dbHelper = new BookMarkDBHelper(this, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();  
        updateSpinner();
        
		// New a dialog of add bookmark and its listener
        addBookMarkDlg = new AddBookMarkDlg(this);
        addBookMarkDlg.setOnDismissListener(new OnDismissListener() {

			public void onDismiss(DialogInterface dialog) {
				// TODO Auto-generated method stub
			    String markName = addBookMarkDlg.getMarkName();
				if (markName.length() > 0) {
					// bookmark name is NOT empty
			        ContentValues values = new ContentValues();
			        values.put(BookMarkDBHelper.BEGIN, begin);
			        values.put(BookMarkDBHelper.END, end);
			        values.put(BookMarkDBHelper.MARK_NAME, markName);
			        db.insert(BookMarkDBHelper.TB_NAME,BookMarkDBHelper.BEGIN, values);
//			        db.insert(BookMarkDBHelper.TB_NAME,BookMarkDBHelper.END, values);
				} else {
					// default untitled bookmark
					ContentValues values = new ContentValues();
			        values.put(BookMarkDBHelper.BEGIN, begin);
			        values.put(BookMarkDBHelper.END, end);
			        values.put(BookMarkDBHelper.MARK_NAME, "untitled");
			        db.insert(BookMarkDBHelper.TB_NAME,BookMarkDBHelper.BEGIN, values);
//			        db.insert(BookMarkDBHelper.TB_NAME,BookMarkDBHelper.END, values);
				}
			    
				updateSpinner();
			}});
		
        
    	// Listener on click
    	oclClick = new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
				// Add bookmark
				case R.id.bookmark_btn_add: {
					addBookMarkDlg.setDisplay();
				}
					break;
					
				// Remove bookmark
				case R.id.bookmark_btn_clear:
					db.delete(BookMarkDBHelper.TB_NAME,null,null);
					updateSpinner();
					break;
					
				// Go to bookmark
				case R.id.bookmark_btn_go:
					Bundle back = new Bundle();
					back.putInt("BEGIN", tobegin);
					back.putInt("END", toend);
					ReadBookActivity.instance.finish();
					Intent bk = new Intent(BookMarkActivity.this, ReadBookActivity.class);					
					bk.putExtras(back);
					startActivity(bk);
					BookMarkActivity.this.finish();
					//ReadBookActivity.instance.finish();
					break;
				}
			}
    		
    	};
    	
    	// Link btn with listener
    	btnAdd.setOnClickListener(oclClick);
    	btnClear.setOnClickListener(oclClick);
    	btnGo.setOnClickListener(oclClick);
    }
    
	// Get back to bookmark manager
	@Override
	public void onResume() {
		super.onResume();
		// Renew bookmark info
        Bundle b = getIntent().getExtras();
        begin = b.getInt("BEGIN");
        end = b.getInt("END");
	}
	
	
	// Release database when quit
	@Override
	public void onDestroy() {
		super.onDestroy();
		db.close();
	}
	
	
		// Renew spinner
		public void updateSpinner() {
			String sql = "SELECT * FROM "
					   + "'" + BookMarkDBHelper.TB_NAME + "'";

			c = db.rawQuery(sql, null);
	        
		      // query by cursor
	        final int Be_mark = c.getColumnIndexOrThrow(BookMarkDBHelper.BEGIN);
	        final int En_mark = c.getColumnIndexOrThrow(BookMarkDBHelper.END);

	        
	        SimpleCursorAdapter adapter = new SimpleCursorAdapter(BookMarkActivity.this,
	                android.R.layout.simple_spinner_item, 
	                c,
	                new String[] {BookMarkDBHelper.MARK_NAME}, 
	                new int[] {android.R.id.text1});
	        adapter.setDropDownViewResource(
	                android.R.layout.simple_spinner_dropdown_item);
	        
	        s.setAdapter(adapter);
	        s.setOnItemSelectedListener(new OnItemSelectedListener(){

	            public void onItemSelected(AdapterView<?> adapter,View v,
	                    int pos, long id) {
	            	
	                c.moveToPosition(pos);
	                tobegin = c.getInt(Be_mark);
	                toend = c.getInt(En_mark);
	            }
	            public void onNothingSelected(AdapterView<?> arg0) {
	            }
	        });
	        
		}
}