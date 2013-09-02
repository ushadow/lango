package com.mit6570.lango.db;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

public class CourseParser {
  
  public static Course parseAndCreate(String urlString) {
    DigestInputStream dis = null;
    try {
      final URL url = new URL(urlString);
      final MessageDigest md = MessageDigest.getInstance("MD5");
      dis = new DigestInputStream(url.openStream(), md);
      Document doc = Jsoup.parse(dis, null, urlString);
      parse(doc);
    } catch (MalformedURLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (NoSuchAlgorithmException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } finally {
      closeQuietly(dis);
    }
    return null;
  }
  
  private static void closeQuietly(InputStream is) {
    try {
      if (is != null)
        is.close();
    } catch(IOException ioe) {
      Log.e("lango", ioe.getMessage());
      System.exit(-1);
    }
  }
  
  private static void parse(Document doc) {
    Elements elements = doc.select("a[name^=lesson");
    Iterator<Element> it = elements.iterator();
    while (it.hasNext()) {
      Log.d("lango", it.next().text());
    }
  }
}
