// Leo @ 2010/10/06

package com.mmclub.mreader;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BookMarkDBHelper extends SQLiteOpenHelper {
	
    public static final String TB_NAME = "tablename";
    public static final String MARK_NAME = "markname";
    public static final String BEGIN = "begin";
    public static final String END = "end";
    public static final String ID = "_id";

    
    
    public BookMarkDBHelper(Context context, String name, 
            CursorFactory factory,int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        
        // Create countrycode
        db.execSQL("CREATE TABLE IF NOT EXISTS " 
                + TB_NAME + " (" 
                + ID + " INTEGER PRIMARY KEY,"
        		+ BEGIN + " INTEGER,"     		
        	    + END + " INTERGER,"
        		+ MARK_NAME + " VARCHAR)"
        	   );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, 
            int oldVersion, int newVersion) {
        
        //Delete old table and create a new one
        db.execSQL("DROP TABLE IF EXISTS " + TB_NAME);
        onCreate(db);
    }
}


/*
public class BookMarkDBHelper extends SQLiteOpenHelper {
	
    public static final String TB_NAME = "tablename";
    public static final String BOOK_NAME = "bookname";
    public static final String MARK_NAME = "markname";
    public static final String POSITION = "_id";
    
    
    public BookMarkDBHelper(Context context, String name, 
            CursorFactory factory,int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        
        // ??????countrycode
        db.execSQL("CREATE TABLE IF NOT EXISTS " 
                + TB_NAME + " (" 
        		+ POSITION + " INTEGER PRIMARY KEY," 
        	    + MARK_NAME + " VARCHAR,"
        	    + BOOK_NAME + " VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, 
            int oldVersion, int newVersion) {
        
        //??????????????????????
        db.execSQL("DROP TABLE IF EXISTS " + TB_NAME);
        onCreate(db);
    }
}
*/