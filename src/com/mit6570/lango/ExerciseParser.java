package com.mit6570.lango;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

public class ExerciseParser {
  public static class Exercise {
    private String description;
    private String descriptionAudioFileName;
    private String answerAudioFileName;
    private String imgFileName;

    public String description() { return description; }

    public String descriptionAudio() { return descriptionAudioFileName; }

    public String answerAudio() { return answerAudioFileName; }
    
    public String imageFile() { return imgFileName; }
    
    public void description(String description) { this.description = description; }
    
    public void descriptionAudio(String audioFilename) {
      descriptionAudioFileName = audioFilename;
    }
    
    public void answerAudio(String audioFilename) {
      answerAudioFileName = audioFilename;
    }
    
    public void imageFile(String filename) { imgFileName = filename; }
  }

  private static final String EXERCISE_TAG = "exercise";
  private static final String DESCRIPTION_TAG = "description";
  private static final String ROOT_TAG = "exercises";
  private static final String ANSWER_TAG = "answer";
  private static final String AUDIO_ATTRIBUTE = "audio";
  private static final String IMG_ATTRIBUTE = "img";
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
                Map<String, String> attributes = Utils.attributes(parser);
                currentExe.descriptionAudio(attributes.get(AUDIO_ATTRIBUTE));
                currentExe.imageFile(attributes.get(IMG_ATTRIBUTE));
                currentExe.description(parser.nextText());
              } else if (name.equalsIgnoreCase(ANSWER_TAG)) {
                Map<String, String> attributes = Utils.attributes(parser);
                currentExe.answerAudio(attributes.get(AUDIO_ATTRIBUTE));
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
