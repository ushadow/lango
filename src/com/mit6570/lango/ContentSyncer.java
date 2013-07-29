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
 * Syncs with the course web site asynchronously.
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
      Log.i("lango", Arrays.toString(c.getColumnNames()));
      String urlString = c.getString(
        c.getColumnIndexOrThrow(CourseContract.Course.COLUMN_NAME_URL));
      url = new URL(urlString);
      final MessageDigest md = MessageDigest.getInstance("MD5");
      dis = new DigestInputStream(url.openStream(), md);
      
      // Gets old hash.
      int hashColumnIndex = c.getColumnIndexOrThrow(CourseContract.Course.COLUMN_NAME_HASH);
      byte[] oldDigest = c.getBlob(hashColumnIndex);          
      byte [] digest = md.digest();
      Log.i("lango", Utils.bytesToHex(digest));
        
      if (needUpdate(digest, oldDigest)) {
        doc = Jsoup.parse(dis, null, urlString);
        updateCourse(doc);
      }
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (NoSuchAlgorithmException nsae) {
      Log.e("lango", nsae.getMessage());
      System.exit(-1);
    } catch (IOException e) {
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
  
  private boolean needUpdate(byte[] digest, byte[] oldDigest) {
    Log.i("langlo", Arrays.toString(digest));
    Log.i("lango", Arrays.toString(oldDigest));
    return oldDigest == null || Arrays.equals(digest, oldDigest);
  }
  
  private void updateCourse(Document doc) {
    Log.i("lango", "update course");
  }
}
