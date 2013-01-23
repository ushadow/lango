package com.mit6570.lango;

import java.io.IOException;

import android.media.MediaRecorder;
import android.util.Log;

public class Recorder {
  private static final String LOG_TAG = Recorder.class.getName();
  private MediaRecorder recorder;
  
  /**
   * Starts audio recording.
   * @param filename Absolute file path to save the recorded file.
   * @throws IOException
   */
  public void startRecording(String filename) {
    recorder = new MediaRecorder();
    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
    recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
    recorder.setOutputFile(filename);
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
    recorder.stop();
    recorder.reset();
    recorder.release();
    Log.d(LOG_TAG, "stopped recording");
  }
}
