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
  private boolean recording = false;
  
  public Recorder(String appDir) {
    this.appDir = new File(externalDir, appDir);
    this.appDir.mkdirs();
  }
  
  /**
   * Full filename including the application directory.
   * @param filename
   * @throws IOException
   */
  public void startRecording(String filename) throws IOException {
    if (recording)
      return;
    
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
    } catch (IOException ioe) {
      Log.e(LOG_TAG, "prepare() failed");
      Log.e(LOG_TAG, ioe.getMessage());
    }
    recorder.start();
    recording = true;
    Log.d(LOG_TAG, "started recording");
  }
  
  public void stopRecording() {
    if (recording) {
      recorder.stop();
      recorder.release();
      recording = false;
    }
  }
}
