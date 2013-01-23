package com.mit6570.lango;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.acl.LastOwnerException;
import java.util.List;
import java.util.Locale;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.mit6570.lango.ExerciseParser.Exercise;

public class ExerciseActivity extends Activity {
  private static final String LOG_TAG = ExerciseActivity.class.getName();
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_exercise);
    String src = (String) getIntent().getExtras().get("exercise_src");
    try {
      InputStream is = getAssets().open(src);
      InputStreamReader isr = new InputStreamReader(is);
      ExerciseParser ep = new ExerciseParser(isr);
      List<Exercise> exes = ep.parse();
      if (exes.size() > 0) {
        int index = 0;
        Exercise e = exes.get(index);
        String description = String.format(Locale.US, "%d: %s", index + 1, e.description());
        TextView tv = (TextView) findViewById(R.id.text_description);
        tv.setText(description);
        
        final Recorder recorder = new Recorder(getString(R.string.app_name));
        final ToggleButton tb = (ToggleButton) findViewById(R.id.button_record);
        tb.setChecked(false);
        tb.setOnClickListener(new OnClickListener() {
          
          @Override
          public void onClick(View button) {
            Log.d(LOG_TAG, "toggle button clicked");
            if (tb.isChecked()) {
              recorder.startRecording("test");
            } else {
              Log.d(LOG_TAG, "not checked");
              recorder.stopRecording();
            }
          }
        });
      }
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (XmlPullParserException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.activity_question, menu);
    return true;
  }

}
