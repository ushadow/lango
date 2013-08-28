package com.mit6570.lango;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Bridge between course list view and course data.
 * @author yingyin
 *
 */
public class CourseListAdapter extends BaseAdapter {
  public static class Course {
    public String name, subTitle, src;
    public Course(String name, String subTitle, String src) {
      this.name = name;
      this.subTitle = subTitle;
      this.src = src;
    }
  }
  
  private static final String COURSE_TAG = "course";
  private static final String NAME_ATTR = "name";
  private static final String SUBTITLE_ATTR = "sub";
  private static final String SRC_ATTR = "src";
  
  private List<Course> courses;
  private Context context;
  private List<Integer> colors;
  private List<Drawable> bgList = new ArrayList<Drawable>();
  
  public CourseListAdapter(Context c) {
    context = c;
    courses = parseXml();
    colors = parseColor();
    TypedArray bgArray = context.getResources().obtainTypedArray(R.array.course_bg);
    for (int i = 0; i < bgArray.length(); i++)
      bgList.add(bgArray.getDrawable(i));
    bgArray.recycle();
  }
  
  @Override
  public int getCount() {
    return courses.size();
  }

  @Override
  public Object getItem(int position) {
    return courses.get(position);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (convertView == null) {
      LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      convertView = inflater.inflate(R.layout.course_item, null);
    }
    
    Course course = (Course) getItem(position);
    LinearLayout view = (LinearLayout) convertView;
    if (bgList.size() > 0)
      view.setBackground(bgList.get(position % bgList.size()));
    ((TextView) view.findViewById(R.id.course_name)).setText(course.name);
    ((TextView) view.findViewById(R.id.course_sub)).setText(course.subTitle);
    
    if (colors.size() > 0)
      ((TextView) view.findViewById(R.id.course_color_tv)).setBackgroundColor(
          colors.get(position % colors.size()));
    return convertView;
  }

  @Override
  public boolean isEmpty() {
    return courses.isEmpty();
  }


  @Override
  public long getItemId(int position) {
    return position;
  }
  
  private List<Course> parseXml() {
    List<Course> courses = new ArrayList<Course>();
    try {
      XmlPullParser parser = context.getResources().getXml(R.xml.mit_japanese_courses);
      int eventType = parser.getEventType();
      while (eventType != XmlPullParser.END_DOCUMENT) {
        switch (eventType) {
          case XmlPullParser.START_TAG:
            if (parser.getName().equalsIgnoreCase(COURSE_TAG)) {
              String name = Utils.attributes(parser).get(NAME_ATTR);
              String subTitle = Utils.attributes(parser).get(SUBTITLE_ATTR);
              String src = Utils.attributes(parser).get(SRC_ATTR);
              courses.add(new Course(name, subTitle, src));
            }
        }
        eventType = parser.next();
      }
    } catch  (XmlPullParserException e){
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return courses;
  }

  private List<Integer> parseColor() {
    String[] colorStr = context.getResources().getStringArray(R.array.course_colors);
    List<Integer> colors = new ArrayList<Integer>();
    for (String color : colorStr) {
      colors.add(Color.parseColor(color));
    }
    return colors;
  }

}
