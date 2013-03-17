package com.mit6570.lango.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CourseDbHelper extends SQLiteOpenHelper{
  public static final int DATABASE_VERSION = 1;
  public static final String DATABASE_NAME = "course.db";
  
  private static final String TEXT_TYPE = " TEXT";
  private static final String COMMA_SEP = ",";
  private static final String SQL_CREATE_COURSE_TABLE = 
      "CREATE TABLE IF NOT EXISTS" + CourseContract.Course.TABLE_NAME + " (" +
      CourseContract.Course._ID + " INTEGER PRIMARY KEY," +
      CourseContract.Course.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
      CourseContract.Course.COLUMN_NAME_HASH + " BLOB" + COMMA_SEP +
      " )";
  public CourseDbHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }
  
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(SQL_CREATE_COURSE_TABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // TODO Auto-generated method stub
    
  }
  
  
}
