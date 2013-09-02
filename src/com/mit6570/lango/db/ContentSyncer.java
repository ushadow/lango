package com.mit6570.lango.db;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;

import net.epsilonlabs.datamanagementefficient.library.DataManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Syncs with the course web site asynchronously.
 * @author yingyin
 *
 */
public class ContentSyncer extends AsyncTask<String, Void, Void>{
  private DataManager dm;
  
  public ContentSyncer(Context context) {
    dm = DataManager.getInstance(context);
    dm.open();
  }
  
  @Override
  protected Void doInBackground(String... args) {
    Collection<Course> courses = dm.find(Course.class, "title", args[0]);
    //if (courses.isEmpty()) {
      CourseParser.parseAndCreate(args[1]);
    //}
    dm.close();
    return null;
  }

 
  
  private boolean needUpdate(byte[] digest, byte[] oldDigest) {
    Log.i("langlo", Arrays.toString(digest));
    Log.i("lango", Arrays.toString(oldDigest));
    return oldDigest == null || Arrays.equals(digest, oldDigest);
  }
  
  private void updateCourse(Document doc) {
    Log.i("lango", "update course");
  }
}
