package com.mit6570.lango;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.os.Bundle;
import android.util.Xml;

public class ExerciseParser {
  private static final String EXERCISE_TAG = "exercise";
  private static final String DESCRIPTION_TAG = "description";
  private static final String ROOT_TAG = "exercises";
  private static final String ANSWER_TAG = "answer";
  private static final String INSTR_TAG = "instruction";
  private static final String QUESTION_TAG = "question";
  private static final String AUDIO_ATTRIBUTE = "audio";
  private static final String IMG_ATTRIBUTE = "img";
  private static final String RECORD_ATTRIBUTE = "record";
  
  private final XmlPullParser parser;
  private final Context context;
  private List<Bundle> exercises;
  /**
   * Meta information about he exercises.
   */
  private Bundle metaInfo = new Bundle();

  /**
   * Creates a parser for exercises.
   * @param isr the {code: InputStreamReader} is closed by this {code: ExerciseParser}
   * @throws XmlPullParserException
   * @throws IOException 
   */
  public ExerciseParser(InputStreamReader isr, Context context) 
      throws XmlPullParserException, IOException {
    this.context = context;
    parser = Xml.newPullParser();
    parser.setInput(isr);
    parse();
    isr.close();
  }
  
  public Bundle metaInfo() { return metaInfo; }
  
  public List<Bundle> exercises() { return exercises; }

  private void parse() {
    try {
      int eventType = parser.getEventType();
      boolean done = false;
      Bundle currentExe = null;
      while (eventType != XmlPullParser.END_DOCUMENT && !done) {
        String name = null;
        switch (eventType) {
          case XmlPullParser.START_DOCUMENT:
            exercises = new ArrayList<Bundle>();
            break;
          case XmlPullParser.START_TAG:
            name = parser.getName();
            if (name.equalsIgnoreCase(INSTR_TAG)) {
              Map<String, String> attributes = Utils.attributes(parser);
              metaInfo.putString(context.getString(R.string.ex_instruction), parser.nextText());
              String record = attributes.get(RECORD_ATTRIBUTE);
              if (record != null && record.equalsIgnoreCase("false")) {
                metaInfo.putBoolean(context.getString(R.string.ex_record_answer), false);
              } else {
                metaInfo.putBoolean(context.getString(R.string.ex_record_answer), true);
              }
            } else if (name.equalsIgnoreCase(EXERCISE_TAG)) {
              currentExe = new Bundle();
            } else if (currentExe != null) {
              if (name.equalsIgnoreCase(DESCRIPTION_TAG)) {
                Map<String, String> attributes = Utils.attributes(parser);
                currentExe.putString(context.getString(R.string.ex_description_audio), 
                                     attributes.get(AUDIO_ATTRIBUTE));
                currentExe.putString(context.getString(R.string.ex_image), 
                                     attributes.get(IMG_ATTRIBUTE));
                currentExe.putString(context.getString(R.string.ex_description), parser.nextText());
              } else if (name.equalsIgnoreCase(ANSWER_TAG)) {
                Map<String, String> attributes = Utils.attributes(parser);
                currentExe.putString(context.getString(R.string.ex_answer_audio), 
                                     attributes.get(AUDIO_ATTRIBUTE));
                currentExe.putString(context.getString(R.string.ex_answer), parser.nextText());
              } else if (name.equalsIgnoreCase(QUESTION_TAG)) {
                currentExe.putString(context.getString(R.string.ex_question), parser.nextText());
              }
            }
            break;
          case XmlPullParser.END_TAG:
            name = parser.getName();
            if (name.equalsIgnoreCase(EXERCISE_TAG) && currentExe != null) {
              exercises.add(currentExe);
              currentExe = null;
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
  }
}
