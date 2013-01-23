package com.mit6570.lango;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.mit6570.lango.ExerciseParser.Exercise;

public class ExerciseActivity extends Activity {
  private MediaPlayer player;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_exercise);
    
    final String src = (String) getIntent().getExtras().get("exercise_src");
    final String srcBaseName = src.replace(".xml", "");
    
    try {
      InputStream is = getAssets().open(src);
      InputStreamReader isr = new InputStreamReader(is);
      ExerciseParser ep = new ExerciseParser(isr);
      List<Exercise> exes = ep.parse();
      
      if (exes.size() > 0) {
        final int index = 0;
        Exercise e = exes.get(index);
        
        // Set exercise description.
        String description = String.format(Locale.US, "%d: %s", index + 1, e.description());
        TextView tv = (TextView) findViewById(R.id.text_description);
        tv.setText(description);
        
        File appDir = new File(Environment.getExternalStorageDirectory(), 
                               getString(R.string.app_name));
        appDir.mkdirs();
        String audioFile = String.format(Locale.US, "%s_%d.3gp", srcBaseName, index);
        final String audioFileAbsPath = (new File(appDir, audioFile)).getAbsolutePath();

        final Recorder recorder = new Recorder();
        
        final ToggleButton recordButton = (ToggleButton) findViewById(R.id.button_record);
        recordButton.setChecked(false);
        recordButton.setOnClickListener(new OnClickListener() {
          
          @Override
          public void onClick(View button) {
            if (recordButton.isChecked()) {
              recorder.startRecording(audioFileAbsPath);
            } else {
              recorder.stopRecording();
            }
          }
        });
        
        final ToggleButton playbackButton = (ToggleButton) findViewById(R.id.button_playback);
        playbackButton.setOnClickListener(new OnClickListener() {
          
          @Override
          public void onClick(View v) {
            if (playbackButton.isChecked()) {
              startPlaying(audioFileAbsPath);
            } else {
              stopPlaying();
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
  
  private void startPlaying(String file) {
    player = new MediaPlayer();
    try {
      player.setDataSource(file);
      player.prepare();
    } catch (IllegalStateException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    player.start();
  }
  
  private void stopPlaying() {
    if (player != null) {
      player.stop();
      player.release();
      player = null;
    }
  }

}
