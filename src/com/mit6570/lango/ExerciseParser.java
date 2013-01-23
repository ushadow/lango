package com.mit6570.lango;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

public class ExerciseParser {
  public static class Exercise {
    private String description;

    public void description(String description) {
      this.description = description;
    }
    
    public String description() { return description; }
  }

  private static final String EXERCISE_TAG = "exercise";
  private static final String DESCRIPTION_TAG = "description";
  private static final String ROOT_TAG = "exercises";
  private final XmlPullParser parser;

  public ExerciseParser(InputStreamReader isr) throws XmlPullParserException {
    parser = Xml.newPullParser();
    parser.setInput(isr);
  }

  public List<Exercise> parse() {
    List<Exercise> exercises = null;
    try {
      int eventType = parser.getEventType();
      boolean done = false;
      Exercise currentExe = null;
      while (eventType != XmlPullParser.END_DOCUMENT && !done) {
        String name = null;
        switch (eventType) {
          case XmlPullParser.START_DOCUMENT:
            exercises = new ArrayList<Exercise>();
            break;
          case XmlPullParser.START_TAG:
            name = parser.getName();
            if (name.equalsIgnoreCase(EXERCISE_TAG)) {
              currentExe = new Exercise();
            } else if (currentExe != null) {
              if (name.equalsIgnoreCase(DESCRIPTION_TAG)) {
                currentExe.description(parser.nextText());
              }
            }
            break;
          case XmlPullParser.END_TAG:
            name = parser.getName();
            if (name.equalsIgnoreCase(EXERCISE_TAG) && currentExe != null) {
              exercises.add(currentExe);
            } else if (name.equalsIgnoreCase(ROOT_TAG)) {
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
