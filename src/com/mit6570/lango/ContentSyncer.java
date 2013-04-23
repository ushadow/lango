package com.mit6570.lango;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.mit6570.lango.db.CourseContract;
import com.mit6570.lango.db.CourseDbHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Synchs with the course web site asynchronously.
 * @author yingyin
 *
 */
public class ContentSyncer extends AsyncTask<String, Void, Document>{
  private CourseDbHelper dbHelpter;
  
  public ContentSyncer(Context context) {
    dbHelpter = new CourseDbHelper(context);
    dbHelpter.createDatabase();
  }
  
  @Override
  protected Document doInBackground(String... args) {
    URL url;
    Document doc = null;
    DigestInputStream dis = null;
    try {
      SQLiteDatabase db = dbHelpter.getReadableDatabase();
      Cursor c = dbHelpter.queryCourse(db);
      c.moveToFirst();
      String urlString = c.getString(
        c.getColumnIndexOrThrow(CourseContract.Course.COLUMN_NAME_URL));
      url = new URL(urlString);
      final MessageDigest md = MessageDigest.getInstance("MD5");
      dis = new DigestInputStream(url.openStream(), md);
      byte [] digest = md.digest();
      Log.i("lango", Arrays.toString(digest));
      doc = Jsoup.parse(dis, null, urlString);
    } catch (MalformedURLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (NoSuchAlgorithmException nsae) {
      Log.e("lango", nsae.getMessage());
      System.exit(-1);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } finally {
      closeQuietly(dis);
    }
    return doc;
  }

  private void closeQuietly(InputStream is) {
    try {
      if (is != null)
        is.close();
    } catch(IOException ioe) {
      Log.e("lango", ioe.getMessage());
      System.exit(-1);
    }
  }
}
