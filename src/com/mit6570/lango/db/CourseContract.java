package com.mit6570.lango.db;

import android.provider.BaseColumns;

public class CourseContract {
  public static abstract class Course implements BaseColumns {
    public static final String TABLE_NAME = "course";
    public static final String COLUMN_NAME_TITLE = "title";
    public static final String COLUMN_NAME_HASH = "hash"; // Hash of the web page for the course.
    public static final String COLUMN_NAME_URL = "url";
  }
  
  public static abstract class Lesson implements BaseColumns {
    public static final String TABLE_NAME = "lesson";
    public static final String COLUMN_NAME_TITLE = "title";
    public static final String COLUMN_NAME_NUM = "num";
  }
  
  public static abstract class Drill implements BaseColumns {
    public static final String TABLE_NAME = "drill";
    public static final String COLUMN_NAME_TITLE = "title";
    public static final String COLUMN_NAME_INSTR = "instr";
    public static final String COLUMN_NAME_HASH = "hash";
  }
  
  private CourseContract() {}
}
