// ��è���õ�Actvity
// Leo @ 2010/10/05

package com.mmclub.mreader;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

public class SettingActivity extends Activity {
	final int MENU_SAVE_AND_RETURN = 1;
	
	public final static String m_READER_PREF = "com.mmclub.mreader";	// PREF mark
	
	public final static String PREF_TAG_FONT_SIZE = "tagFontSize";
	public final static String PREF_TAG_FONT_COLOR = "tagFontColor";
	public final static String PREF_TAG_BACKGROUND_COLOR = "tagBgColor";
	public final static String PREF_TAG_SCREEN_BRIGHTNESS = "tagScrBrightness";
	
	SharedPreferences spSetting;													// User Settings
	
	String fontColor;
	String bgColor;
	float fontSize;
	float scrBrightness;
	
	int begin;
	int end;
	
	TextView tvSettingPrev;
	
	Spinner spFontColor;
	Spinner spBgColor;
	SeekBar sbFontSize;
	SeekBar sbScrBrightness;
	
	ArrayAdapter<String> adapterFontColor;
	ArrayAdapter<String> adapterBgColor;
	OnSeekBarChangeListener osbl;
	
	@Override  
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.setting);  
        
        Bundle t = getIntent().getExtras();
        begin = t.getInt("BEGIN");
        end = t.getInt("END");
        
        tvSettingPrev = (TextView) findViewById(R.id.setting_textview_setting_prev);
        sbFontSize = (SeekBar) findViewById(R.id.setting_seekbar_font_size);
        sbScrBrightness = (SeekBar) findViewById(R.id.setting_seekbar_scr_brightness);
        spFontColor = (Spinner) findViewById(R.id.setting_spinner_font_color);
//        spBgColor = (Spinner) findViewById(R.id.setting_spinner_bg_color);
        
        initFromPref();
		
    	   	
    	// font color
		adapterFontColor = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item);  
		adapterFontColor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);   
		adapterFontColor.add("Black");
		adapterFontColor.add("Red");
		adapterFontColor.add("Green");
		adapterFontColor.add("Blue");
		
		spFontColor.setAdapter(adapterFontColor);   
		spFontColor.setOnItemSelectedListener(new OnItemSelectedListener() {  

			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Spinner sp = (Spinner) parent;  
				fontColor = sp.getSelectedItem().toString();
		    	if (fontColor == "Black")
		    		tvSettingPrev.setTextColor(Color.BLACK);
		    	else if (fontColor == "Red")
		    		tvSettingPrev.setTextColor(Color.RED);
		    	else if (fontColor == "Green")
		    		tvSettingPrev.setTextColor(Color.GREEN);
		    	else if (fontColor == "Blue")
		    		tvSettingPrev.setTextColor(Color.BLUE);
		    	
				fontColor = spFontColor.getSelectedItem().toString();
			}

			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}  }); 
/*		
		// ������ɫѡ����
		adapterBgColor = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item);  
		adapterBgColor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
		adapterBgColor.add("��ɫ");  
		adapterBgColor.add("��ɫ");  
		adapterBgColor.add("��ɫ");  
		adapterBgColor.add("��ɫ");  
		adapterBgColor.add("��ɫ");  
		
		spBgColor.setAdapter(adapterBgColor);   
		spBgColor.setOnItemSelectedListener(new OnItemSelectedListener() {  

			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Spinner sp = (Spinner) parent;  
				bgColor = sp.getSelectedItem().toString();
		    	if (bgColor == "��ɫ")
		    		tvSettingPrev.setBackgroundColor(Color.WHITE);
		    	else if (bgColor == "��ɫ") 
		    		tvSettingPrev.setBackgroundColor(Color.BLACK);
		    	else if (bgColor == "��ɫ") 	
		    		tvSettingPrev.setBackgroundColor(Color.RED);
		    	else if (bgColor == "��ɫ") 
		    		tvSettingPrev.setBackgroundColor(Color.GREEN);
		    	else if (bgColor == "��ɫ") 
		    		tvSettingPrev.setBackgroundColor(Color.BLUE);
		    	
				bgColor = spBgColor.getSelectedItem().toString();
			}

			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
	
			}  }); 
*/	
		// ���ֱ�����ɫԤ�����룬�˶δ�����
		if (fontColor == "Black") {
    		spFontColor.setSelection(0);
    		tvSettingPrev.setTextColor(Color.BLACK);
    	}
    	else if (fontColor == "Red") {
    		spFontColor.setSelection(1);
    		tvSettingPrev.setTextColor(Color.RED);
    	} 	
    	else if (fontColor == "Green") {
    		spFontColor.setSelection(2);
    		tvSettingPrev.setTextColor(Color.GREEN);
    	} 
    	else if (fontColor == "Blue") {
    		spFontColor.setSelection(3);
    		tvSettingPrev.setTextColor(Color.BLUE);
    	} 
/*  			
    	if (bgColor == "��ɫ") {
    		spBgColor.setSelection(0);
    		tvSettingPrev.setBackgroundColor(Color.WHITE);
    	}
    	else if (bgColor == "��ɫ") {
    		spBgColor.setSelection(1);
    		tvSettingPrev.setBackgroundColor(Color.BLACK);
    	} 
    	else if (bgColor == "��ɫ") {
    		spBgColor.setSelection(2);
    		tvSettingPrev.setBackgroundColor(Color.RED);
    	}	
    	else if (bgColor == "��ɫ") {
    		spBgColor.setSelection(3);
    		tvSettingPrev.setBackgroundColor(Color.GREEN);
    	}
    	else if (bgColor == "��ɫ") {
    		spBgColor.setSelection(4);
    		tvSettingPrev.setBackgroundColor(Color.BLUE);
    	}
*/ 	
    	tvSettingPrev.setTextSize(fontSize);
    	
    	// Ԥ��������???
    	tvSettingPrev.setMovementMethod(ScrollingMovementMethod.getInstance());
    	
    	WindowManager.LayoutParams lp = getWindow().getAttributes();  
    	lp.screenBrightness = scrBrightness;  
    	getWindow().setAttributes(lp); 
    	
    	// �������ı�ļ�����
		osbl = new OnSeekBarChangeListener() {
			
			// �϶���
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				switch (seekBar.getId()) {
				case R.id.setting_seekbar_font_size:
					tvSettingPrev.setTextSize((progress+1) * 5.0f);
					fontSize = (progress+1) * 5.0f;
					break;
				case R.id.setting_seekbar_scr_brightness:
			    	WindowManager.LayoutParams lp = getWindow().getAttributes();  
			    	lp.screenBrightness = progress / 10.0f;  
			    	getWindow().setAttributes(lp); 
			    	scrBrightness = progress / 10.0f;
					break;
				}
			}

			// ��ʼ�϶�
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				switch (seekBar.getId()) {
				case R.id.setting_seekbar_font_size:
					break;
				case R.id.setting_seekbar_scr_brightness:
					break;
				}
			}

			// �����϶�
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				switch (seekBar.getId()) {
				case R.id.setting_seekbar_font_size:
					break;
				case R.id.setting_seekbar_scr_brightness:
					break;
				}
			}
			
		};
		
		sbFontSize.setOnSeekBarChangeListener(osbl);
		sbScrBrightness.setOnSeekBarChangeListener(osbl);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_SAVE_AND_RETURN, 0,"Save and return")
		.setIcon(android.R.drawable.ic_menu_save);
		
		return true;
	}
	
	// ���˵�����¼�
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case MENU_SAVE_AND_RETURN:
			saveToPref();
			Bundle back = new Bundle();
			back.putInt("BEGIN", begin);
			back.putInt("END", end);
			ReadBookActivity.instance.finish();
			Intent bk = new Intent(SettingActivity.this, ReadBookActivity.class);					
			bk.putExtras(back);
			startActivity(bk);
			this.finish();
			//ReadBookActivity.instance.finish();
			break;
		}
		return false;
	}
	
    private void initFromPref() {
    	spSetting = getSharedPreferences(m_READER_PREF, 0);
    	fontColor = spSetting.getString(PREF_TAG_FONT_COLOR, "Black");
//    	bgColor = spSetting.getString(PREF_TAG_BACKGROUND_COLOR, "��ɫ");
    	fontSize = spSetting.getFloat(PREF_TAG_FONT_SIZE, 25.0f);
    	scrBrightness = spSetting.getFloat(PREF_TAG_SCREEN_BRIGHTNESS, 1.0f);
    }
    
    private void saveToPref() {
    	spSetting = getSharedPreferences(m_READER_PREF, 0);
    	SharedPreferences.Editor ed = spSetting.edit();
    	
    	ed.putString(PREF_TAG_FONT_COLOR, fontColor);
//    	ed.putString(PREF_TAG_BACKGROUND_COLOR, bgColor);
    	ed.putFloat(PREF_TAG_FONT_SIZE, fontSize);
    	ed.putFloat(PREF_TAG_SCREEN_BRIGHTNESS, scrBrightness);
    	ed.commit();
    }
}