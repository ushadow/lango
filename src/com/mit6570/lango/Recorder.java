package com.mit6570.lango;

import java.io.File;
import java.io.IOException;

import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

public class Recorder {
  private static final String LOG_TAG = Recorder.class.getName();
  private static final File externalDir = Environment.getExternalStorageDirectory();
  private final File appDir;
  private File audiofile;
  private MediaRecorder recorder;
  
  public Recorder(String appDir) {
    this.appDir = new File(externalDir, appDir);
    this.appDir.mkdirs();
  }
  
  /**
   * Full filename including the application directory.
   * @param filename
   * @throws IOException
   */
  public void startRecording(String filename) {
    try {
      audiofile = File.createTempFile(filename, ".3gp", appDir);
    } catch (IOException e) {
      Log.e(LOG_TAG, e.getMessage());
      return;
    }
    recorder = new MediaRecorder();
    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
    recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
    recorder.setOutputFile(audiofile.getAbsolutePath());
    try {
      recorder.prepare();
      recorder.start();
    } catch (IOException ioe) {
      Log.e(LOG_TAG, "prepare() failed");
      Log.e(LOG_TAG, ioe.getMessage());
    } catch (IllegalStateException ise) {
    }
    Log.d(LOG_TAG, "started recording");
  }
  
  public void stopRecording() {
    Log.d(LOG_TAG, "stopping recording");
    recorder.stop();
    recorder.reset();
    recorder.release();
    Log.d(LOG_TAG, "stopped recording");
  }
}
