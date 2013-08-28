package com.mit6570.lango;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.ActionBar;
import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;

/**
 * Activity for users to choose exercises and drills. Exercises contain drills.
 * @author yingyin
 *
 */
public class ExerciseMenuActivity extends ExpandableListActivity {
  private static final String ROOT_TAG = "course";
  private static final String LESSON_TAG = "lesson";
  private static final String DRILL_TAG = "drill";
  private static final String NAME_ATTRIBUTE = "name";
  private static final String SRC_ATTRIBUTE = "src";

  private String courseName = "";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_exercisemenu);

    final List<ExerciseMenu> exercises = parseXml();
    ExpandableMenuAdapter ema = new ExpandableMenuAdapter(this, exercises);
    setListAdapter(ema);
    getExpandableListView().setOnChildClickListener(new OnChildClickListener() {

      @Override
      public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
          int childPosition, long id) {
        Intent intent = new Intent(v.getContext(), ExerciseActivity.class);
        ExerciseMenu lesson = exercises.get(groupPosition);
        ExerciseMenu drill = lesson.getDrill(childPosition);
        String drillSrouce = drill.src();
        if (drillSrouce != null) {
          intent.putExtra(getString(R.string.ex_src), drillSrouce);
          intent.putExtra(getString(R.string.ex_lesson), lesson.name());
          intent.putExtra(getString(R.string.ex_drill), drill.name());
          startActivity(intent);
        } else {
          CharSequence text = getString(R.string.unimplemented_msg);
          int duration = Toast.LENGTH_SHORT;
          Toast toast = Toast.makeText(getApplicationContext(), text, duration);
          toast.show();
        }
        return true;
      }
    });

    ActionBar actionBar = getActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setTitle(courseName);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private List<ExerciseMenu> parseXml() {
    List<ExerciseMenu> exercises = null;
    try {
      XmlPullParser parser = this.getResources().getXml(R.xml.mit_japanese_502);
      int eventType = parser.getEventType();
      boolean done = false;
      ExerciseMenu currentExe = null;
      while (eventType != XmlPullParser.END_DOCUMENT && !done) {
        String name = null;
        switch (eventType) {
          case XmlPullParser.START_DOCUMENT:
            exercises = new ArrayList<ExerciseMenu>();
            break;
          case XmlPullParser.START_TAG:
            name = parser.getName();
            if (name.equalsIgnoreCase(LESSON_TAG)) {
              String lessonName = Utils.attributes(parser).get(NAME_ATTRIBUTE);
              currentExe = new ExerciseMenu(lessonName);
            } else if (currentExe != null) {
              if (name.equalsIgnoreCase(DRILL_TAG)) {
                String src = Utils.attributes(parser).get(SRC_ATTRIBUTE);
                currentExe.add(new ExerciseMenu(parser.nextText(), src));
              }
            } else if (name.equalsIgnoreCase(ROOT_TAG)) {
              courseName = Utils.attributes(parser).get(NAME_ATTRIBUTE);
            }
            break;
          case XmlPullParser.END_TAG:
            name = parser.getName();
            if (name.equalsIgnoreCase(LESSON_TAG) && currentExe != null) {
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
