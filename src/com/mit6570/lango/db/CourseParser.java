package com.mit6570.lango.db;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mit6570.lango.db.Course.Lesson;

import android.util.Log;

public class CourseParser {
  private static final String DRILL_URL_PATTERN_STR = "javascript:openDrill\\('(.+)'\\)";
  private static final Pattern DRILL_URL_PATTERN = Pattern.compile(DRILL_URL_PATTERN_STR);
  private static final String DRILL_URL = "http://dokkai.scripts.mit.edu/link_page.cgi?drill=";

  public static Course parseAndCreate(String urlString) {
    DigestInputStream dis = null;
    try {
      final URL url = new URL(urlString);
      final MessageDigest md = MessageDigest.getInstance("MD5");
      dis = new DigestInputStream(url.openStream(), md);
      Document doc = Jsoup.parse(dis, null, urlString);
      String courseName = doc.select("title").text();
      Collection<Course.Lesson> lessons = parseLesson(doc);
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
  
  private static Collection<Course.Lesson> parseLesson(Document doc) {
    Iterator<Element> it = doc.select("a[name^=lesson").iterator();
    Collection<Course.Lesson> lessons = new ArrayList<Course.Lesson>();
    while (it.hasNext()) {
      Element lesson = it.next();
      Collection<Course.Section> sections = parseSection(lesson);
      lessons.add(new Lesson(lesson.text(), sections));
    }
    return lessons;
  }
  
  private static Collection<Course.Section> parseSection(Element lesson) {
    Element parent = lesson.parent();
    Iterator<Element> it = parent.select("ul>li").iterator();
    Collection<Course.Section> sections = new ArrayList<Course.Section>();
    while (it.hasNext()) {
      Element anchor = it.next().select(">a").first();
      String href = anchor.attr("href");
      if (href != null) {
        Matcher m = DRILL_URL_PATTERN.matcher(href);
        if (m.matches()) {
          String id = m.group(1);
          String sectionName = anchor.text();
          // Removes rightwards arrow (-->).
          sectionName = sectionName.replaceAll("\\s?\\u2192?$", "");
          String url = DRILL_URL + id;
          sections.add(new Course.Section(sectionName, url));
        }
      }
    }
    return sections;
  }
}
