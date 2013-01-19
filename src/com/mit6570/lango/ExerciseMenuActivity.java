package com.mit6570.lango;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.ExpandableListActivity;
import android.os.Bundle;

public class ExerciseMenuActivity extends ExpandableListActivity {
  private static final String ROOT_TAG = "course";
  private static final String LESSON_TAG = "lesson";
  private static final String DRILL_TAG = "drill";
  private static final String NAME_ATTRIBUTE="name";
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_exercisemenu);
    
    List<Exercise> exercises = parseXml();
    ExpandableMenuAdapter ema = new ExpandableMenuAdapter(this, exercises);
    setListAdapter(ema);
  }
  
  private List<Exercise> parseXml() {
    List<Exercise> exercises = null;
    try {
      XmlPullParser parser = this.getResources().getXml(R.xml.japanese_502);
      int eventType = parser.getEventType();
      boolean done = false;
      Exercise currentExe = null;
      while (eventType != XmlPullParser.END_DOCUMENT && !done){
          String name = null;
          switch (eventType){
              case XmlPullParser.START_DOCUMENT:
                  exercises = new ArrayList<Exercise>();
                  break;
              case XmlPullParser.START_TAG:
                  name = parser.getName();
                  if (name.equalsIgnoreCase(LESSON_TAG)){
                    for (int i = 0; i < parser.getAttributeCount(); i++) {
                      String attrName = parser.getAttributeName(i);
                      if (attrName.equalsIgnoreCase(NAME_ATTRIBUTE)) {
                        currentExe = new Exercise(parser.getAttributeValue(i));
                        break;
                      }
                    }
                  } else if (currentExe != null){
                    if (name.equalsIgnoreCase(DRILL_TAG)){
                        currentExe.add(new Exercise(parser.nextText()));
                    }
                  }
                  break;
              case XmlPullParser.END_TAG:
                  name = parser.getName();
                  if (name.equalsIgnoreCase(LESSON_TAG) && currentExe != null){
                      exercises.add(currentExe);
                  } else if (name.equalsIgnoreCase(ROOT_TAG)){
                      done = true;
                  }
                  break;
          }
          eventType = parser.next();
      }
    } catch (XmlPullParserException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return exercises;
  }
}
