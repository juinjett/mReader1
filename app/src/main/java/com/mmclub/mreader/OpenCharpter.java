package com.mmclub.mreader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class OpenCharpter extends Activity{
	
	
	private ListView mylv;
	private FileListAdapter myfa;
	
	String[] slist= {"Prologue","Chapter 1","Chapter 2","Chapter 3","Chapter 4","Chapter 5","Chapter 6","Chapter 7","Chapter 8","Chapter 9","Chapter 10","Chapter 11","Chapter 12","Chapter 13","Chapter 14","Chapter 15"};
	String[] sslist = {"Prologue","Chapter 1","Chapter 2","Chapter 3","Chapter 4","Chapter 5","Chapter 6","Chapter 7","Chapter 8","Chapter 9","Chapter 10","Chapter 11","Chapter 12","Chapter 13","Chapter 14","Chapter 15"};
 
    int charpter;
    
	final int MENU_EXIT = Menu.FIRST;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		overridePendingTransition(R.anim.anim, R.anim.openfile);
		setContentView(R.layout.openpassay);
		
		mylv = (ListView)findViewById(R.id.listview);
		myfa = new FileListAdapter(this);
		mylv.setAdapter(myfa);
		
        try {  
        	//Return an AssetManager instance for your application's package  
            InputStream is = getAssets().open("a.txt");  
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);   
            
//            String text = new String(buffer, "UTF8");
//            Log.e("lulululululi",text);
//            System.out.println(text); 
            
            File f = new File("/sdcard", "/1.txt");
            if (!f.exists()) {
                 f.createNewFile();
             }
             FileOutputStream os = new FileOutputStream(f);
             os.write(buffer);
             os.close();
              is.close();
           
        } catch (IOException e) {  
            // Should never happen!  
            //throw new RuntimeException(e);  
        }  
		
        
		for(int i=0;i<16;i++)
		{
			FileListItem item = new FileListItem();
			item.name = slist[i];
			item.sname = sslist[i];
			myfa.addItem(item);
		}
		
		OnItemClickListener itemlistener = new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				Intent goread = new Intent(OpenCharpter.this,ReadBookActivity.class);
				Bundle tp = new Bundle();
				
				switch(arg2){
				case 0:
				tp.putInt("charpter", 0);
				break;
				
				case 1:	
				tp.putInt("charpter", 1);
				break;
				
				case 2:	
				tp.putInt("charpter", 2);
				break;
				
				case 3:	
				tp.putInt("charpter", 3);
				break;
				
				case 4:	
				tp.putInt("charpter", 4);
				break;
				
				case 5:	
				tp.putInt("charpter", 5);
				break;
				
				case 6:	
				tp.putInt("charpter", 6);
				break;
				
				case 7:	
				tp.putInt("charpter", 7);
				break;
				
				case 8:	
				tp.putInt("charpter", 8);
				break;
				
				case 9:	
				tp.putInt("charpter", 9);
				break;
				
				case 10:	
				tp.putInt("charpter", 10);
				break;
				
				case 11:	
				tp.putInt("charpter", 11);
				break;
				
				case 12:	
				tp.putInt("charpter", 12);
				break;
				
				case 13:	
				tp.putInt("charpter", 13);
				break;
				
				case 14:	
				tp.putInt("charpter", 14);
				break;
					
				case 15:	
				tp.putInt("charpter", 15);
				break;
				}
				OpenCharpter.this.finish();
				goread.putExtras(tp);
				startActivity(goread);
				//OpenCharpter.this.finish();
			}
		};
		
		mylv.setOnItemClickListener(itemlistener);
	}
	
	
	
	public boolean onKeyDown(int keyCode,KeyEvent event) {

		 if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
		//Overwrite Exit
		        new AlertDialog.Builder(this)          
		        .setTitle("Exit")
		        .setMessage("Are you sure to exit?")
		        .setNegativeButton("Cancel",
		                new DialogInterface.OnClickListener() {  		
		                public void onClick(DialogInterface dialog, int which) {  
		                            // TODO Auto-generated method stub  		                              
		                    }  
		                })  		  
		        .setPositiveButton("Exit",
		                new DialogInterface.OnClickListener() {  		  
		                    public void onClick(DialogInterface dialog, int whichButton) {  		  
		                        finish();  		  
		                    }  		  
		                }).show();  
		return true;
		}
		return false;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_EXIT, 0, "Exit");
		return true;
	}	
		
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case MENU_EXIT:
			finish();
			break;
		}
		return false;
	    }
}

