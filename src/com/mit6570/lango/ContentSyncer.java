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

import android.os.AsyncTask;
import android.util.Log;

public class ContentSyncer extends AsyncTask<String, Void, Document>{
  @Override
  protected Document doInBackground(String... urls) {
    URL url;
    Document doc = null;
    DigestInputStream dis = null;
    try {
      url = new URL(urls[0]);
      final MessageDigest md = MessageDigest.getInstance("MD5");
      dis = new DigestInputStream(url.openStream(), md);
      byte [] digest = md.digest();
      Log.i("lango", Arrays.toString(digest));
      doc = Jsoup.parse(dis, null, urls[0]);
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
