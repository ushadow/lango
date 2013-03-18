package com.mit6570.lango.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CourseDbHelper extends SQLiteOpenHelper{
  public static final int DATABASE_VERSION = 1;
  public static final String DB_PATH = "/data/data/com.mit6570.lango/databases/";
  public static final String DB_NAME = "course.db";
  
  private static final String SQL_DELETE_TABLES = 
      "DELETE FROM sqlite_master WHERE TYPE in ('table', 'index', 'triger')";
  
  private Context context;
  
  public CourseDbHelper(Context context) {
    super(context, DB_NAME, null, DATABASE_VERSION);
    this.context = context;
  }
  
  public void createDatabase() {
    boolean dbExist = checkDatabase();
    if (!dbExist) {
      getReadableDatabase();
      close();
      try {
        copyDatabase();
      } catch (IOException ioe) {
        ioe.printStackTrace();
      }
    }
  }
  
  public void onCreate(SQLiteDatabase db) {}

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL(SQL_DELETE_TABLES);
    onCreate(db);
  }
  
  public Cursor queryCourse(SQLiteDatabase db) {
    String[] columns = {
       CourseContract.Course.COLUMN_NAME_TITLE,
       CourseContract.Course.COLUMN_NAME_URL
    };
    
    return db.query(CourseContract.Course.TABLE_NAME, columns, null, null, null, null, null);
  }
  
  private boolean checkDatabase() {
    String path = DB_PATH + DB_NAME;
    File f = new File(path);
    return f.exists();
  }
  
  private void copyDatabase() throws IOException {
    InputStream is = context.getAssets().open(DB_NAME);
    String path = DB_PATH + DB_NAME;
    OutputStream os = new FileOutputStream(path);
    
    byte[] buffer = new byte[1024];
    int length;
    while ((length = is.read(buffer)) > -1) {
      os.write(buffer, 0, length);
    }
    os.flush();
    os.close();
    is.close();
  }
}
