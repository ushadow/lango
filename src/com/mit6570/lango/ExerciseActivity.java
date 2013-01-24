package com.mit6570.lango;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.mit6570.lango.ExerciseParser.Exercise;

public class ExerciseActivity extends Activity {
  MediaPlayer questionPlayer, responsePlayer, answerPlayer;
  
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
      List<Exercise> exes = ep.exercises();
      
      String instr = ep.instruction();
      setupText(R.id.text_instruction, instr);
      
      if (exes.size() > 0) {
        final int index = 0;
        Exercise e = exes.get(index);
        
        // Set exercise description.
        String description = String.format(Locale.US, "%d: %s", index + 1, e.description());
        setupText(R.id.text_description, description);
        
        String questionAudio = e.descriptionAudio();
        setupPlayQuestionButton(R.id.button_playquestion, questionAudio);
        
        String answerAudio = e.answerAudio();
        String answer = e.answer();
        setupAnswerButton(R.id.button_playanswer, answerAudio, answer);
        
        String imgFile = e.imageFile();
        setupDescriptionImage(imgFile);
        
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
        
        setupPlaybackButton(R.id.button_playback, audioFileAbsPath);
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
  
  private MediaPlayer createPlayer(final ToggleButton tb) {
    MediaPlayer player = new MediaPlayer();
    player.setOnCompletionListener(new OnCompletionListener() {
      @Override
      public void onCompletion(MediaPlayer mp) {
        tb.setChecked(false);
        stopPlaying(mp);
      }
    });
    return player;
  }
  
  /**
   * Plays an audio file.
   * @param file absolute path of an audio file.
   */
  private MediaPlayer startPlaying(String file, final ToggleButton tb) {
    MediaPlayer player = createPlayer(tb);
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
    return player;
  }
  
  /**
   * 
   * @param afd
   * @param tb
   * @return the new {@code MediaPlayer} created.
   */
  private MediaPlayer startPlaying(AssetFileDescriptor afd, ToggleButton tb) {
    MediaPlayer player = createPlayer(tb);
    try {
      player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
      player.prepare();
    } catch (IllegalStateException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    player.start();
    return player;
  }
  
  private void stopPlaying(MediaPlayer player) {
    if (player != null) {
      player.stop();
      player.release();
    }
  } 
  
  private void setupPlaybackButton(int buttonId, final String audioFile) {
    final ToggleButton tb = (ToggleButton) findViewById(buttonId);
    tb.setOnClickListener(new OnClickListener() {
      
      @Override
      public void onClick(View v) {
        if (tb.isChecked()) {
          responsePlayer = startPlaying(audioFile, tb);
        } else {
          stopPlaying(responsePlayer);
        }
      }
    });
  }
  
  private void setupPlayQuestionButton(int buttonId, String audioFile) {
    try {
      final AssetFileDescriptor afd = getAssets().openFd(audioFile);
      final ToggleButton tb = (ToggleButton) findViewById(buttonId);
      
      tb.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          if (tb.isChecked()) {
            questionPlayer = startPlaying(afd, tb);
          } else {
            stopPlaying(questionPlayer);
          }
        }
      });
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  private void setupAnswerButton(int buttonId, String audioFile, final String answer) {
    final ToggleButton tb = (ToggleButton) findViewById(buttonId);
    try {
      final AssetFileDescriptor afd = getAssets().openFd(audioFile);
      tb.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          if (tb.isChecked()) {
            answerPlayer = startPlaying(afd, tb);
            setupText(R.id.text_answer, answer);
          } else {
            stopPlaying(answerPlayer);
            setupText(R.id.text_answer, "");
          }
        }
      });
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  private void setupDescriptionImage(String imgFile) {
    ImageView iv = (ImageView) findViewById(R.id.image_description);
    try {   
      Bitmap image = getBitmapFromAsset(imgFile);
      iv.setImageBitmap(image);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
  }
  
  /**
   * Helper Functions
   * @throws IOException 
   */
  private Bitmap getBitmapFromAsset(String strName) throws IOException {
      AssetManager assetManager = getAssets();

      InputStream istr = assetManager.open(strName);
      Bitmap bitmap = BitmapFactory.decodeStream(istr);
      istr.close();

      return bitmap;
  }
  
  private void setupText(int id, String text) {
    TextView tv = (TextView) findViewById(id);
    tv.setText(text);
  }
}
