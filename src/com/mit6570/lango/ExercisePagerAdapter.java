package com.mit6570.lango;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class ExercisePagerAdapter extends FragmentStatePagerAdapter {
  // Instances of this class are fragments representing a single
  // object in our collection.
  public static class ExericseFragment extends Fragment {

    private MediaPlayer questionPlayer, responsePlayer, answerPlayer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      // The last two arguments ensure LayoutParams are inflated
      // properly.
      View rootView = inflater.inflate(R.layout.fragment_exercise, container, false);
      Bundle args = getArguments();
      return createView(rootView, args);
    }

    private View createView(View rootView, Bundle b) {
      int index = b.getInt(KEY_INDEX);
      int width = b.getInt(KEY_WIDTH);

      Bundle instructionInfo = b.getBundle(KEY_META);
      String instruction = instructionInfo.getString(getString(R.string.ex_instruction));
      instruction = removeRubyMarker(instruction);
      setupText(rootView, R.id.text_instruction, instruction);

      String imgFile = instructionInfo.getString(getString(R.string.ex_instr_img));
      if (imgFile != null) {
        setupImage(rootView, imgFile, R.id.image_instruction, true, width);
      }

      // Set exercise description.
      String questionAudio = b.getString(getString(R.string.ex_description_audio));
      setupPlayQuestionButton(rootView, R.id.button_playquestion, questionAudio);

      String answerAudio = b.getString(getString(R.string.ex_answer_audio));
      String answer = removeRubyMarker(b.getString(getString(R.string.ex_answer)));
      int showButtonId = answerAudio == null ? R.id.button_answer : R.id.button_playanswer;
      int hideButtonId = answerAudio == null ? R.id.button_playanswer : R.id.button_answer;
      setupAnswerButton(rootView, showButtonId, hideButtonId, answerAudio, answer);

      imgFile = b.getString(getString(R.string.ex_image));
      if (imgFile != null) {
        setupImage(rootView, imgFile, R.id.image_description, false, width);
      }

      String question = b.getString(getString(R.string.ex_question));
      if (question != null) {
        question = removeRubyMarker(question);
        setupText(rootView, R.id.text_question, question);
      }

      boolean isRecordAnswer = instructionInfo.getBoolean(getString(R.string.ex_record_answer));
      if (isRecordAnswer) {
        setupRecordAnswerUI(rootView, instructionInfo, index);
      } else {
        View view = rootView.findViewById(R.id.group_record);
        view.setVisibility(View.GONE);
      }

      return rootView;
    }

    /**
     * Removes the ruby markers.
     * 
     * @param text cannot be null.
     * @return
     */
    private String removeRubyMarker(String text) {
      String res = text.replaceAll("\\[/?rb\\]", "");
      res = res.replace("[rt]", "[");
      res = res.replace("[/rt]", "]");
      return res;
    }

    private void setupRecordAnswerUI(View rootView, Bundle metaInfo, int index) {
      File appDir =
          new File(Environment.getExternalStorageDirectory(), getString(R.string.app_name));
      appDir.mkdirs();
      String srcBaseName = metaInfo.getString(getString(R.string.ex_basename));
      String audioFile = String.format(Locale.US, "%s_%d.3gp", srcBaseName, index);
      final String audioFileAbsPath = (new File(appDir, audioFile)).getAbsolutePath();

      final Recorder recorder = new Recorder();

      final ToggleButton recordButton = (ToggleButton) rootView.findViewById(R.id.button_record);
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
      setupPlaybackButton(rootView, R.id.button_playback, audioFileAbsPath);
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
     * 
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

    private void setupPlaybackButton(View rootView, int buttonId, final String audioFile) {
      final ToggleButton tb = (ToggleButton) rootView.findViewById(buttonId);
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

    private void setupPlayQuestionButton(View rootView, int buttonId, String audioFile) {
      final ToggleButton tb = (ToggleButton) rootView.findViewById(buttonId);
      if (audioFile == null) {
        tb.setVisibility(View.GONE);
        return;
      }
      try {
        final AssetFileDescriptor afd = getActivity().getAssets().openFd(audioFile);

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

    /**
     * 
     * @param rootView
     * @param showButtonId id of the button to be shown.
     * @param hideButtonId id of the button to be hidden.
     * @param audioFile
     * @param answer
     */
    private void setupAnswerButton(final View rootView, int showButtonId, int hideButtonId,
        final String audioFile, final String answer) {
      final ToggleButton tb = (ToggleButton) rootView.findViewById(showButtonId);
      rootView.findViewById(hideButtonId).setVisibility(View.GONE);
      tb.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          AssetFileDescriptor afd;
          if (audioFile != null) {
            try {
              afd = getActivity().getAssets().openFd(audioFile);
              if (tb.isChecked()) {
                answerPlayer = startPlaying(afd, tb);
              } else {
                stopPlaying(answerPlayer);
              }
            } catch (IOException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
          }

          if (answer != null) {
            if (tb.isChecked()) {
              setupText(rootView, R.id.text_answer, answer);
            } else {
              setupText(rootView, R.id.text_answer, "");
            }
          }
        }
      });
    }

    private void setupImage(View rootView, String imgFile, int viewId, boolean isInstructionImg,
        int screenWidthPixels) {
      ImageView iv = (ImageView) rootView.findViewById(viewId);
      int screenWidth = screenWidthPixels;
      try {
        Bitmap image = getBitmapFromAsset(imgFile);
        iv.setImageBitmap(image);

        if (! isInstructionImg) {
          int imgWidthOriginal = image.getWidth();
          int imgHeightOriginal = image.getHeight();
          int imgWidthDesired = screenWidth;
          int imgHeightDesired = imgHeightOriginal;

          if (5 * imgWidthOriginal < 2 * screenWidth) {
            imgWidthDesired = (int) (2.0 / 5 * screenWidth);
            imgWidthDesired = Math.max(imgWidthDesired, 3*imgWidthOriginal);
            
            imgHeightDesired = imgHeightOriginal * imgWidthDesired / imgWidthOriginal;
            iv.setLayoutParams(new LinearLayout.LayoutParams(imgWidthDesired, imgHeightDesired));
          }

        }

        if (isInstructionImg) {
          int imgWidthOriginal = image.getWidth();
          int imgHeightOriginal = image.getHeight();
          int imgWidthDesired = screenWidth;
          int imgHeightDesired = imgHeightOriginal;

          if (5 * imgWidthOriginal < 4 * screenWidth) {
            imgWidthDesired = screenWidth-20;
            imgHeightDesired = imgHeightOriginal * imgWidthDesired / imgWidthOriginal;
            iv.setLayoutParams(new LinearLayout.LayoutParams(imgWidthDesired, imgHeightDesired));
          }
        }
      } catch (IOException e) {
        Log.e("lango", e.getMessage());
      }
    }

    /**
     * Helper Functions
     * 
     * @throws IOException
     */
    private Bitmap getBitmapFromAsset(String strName) throws IOException {
      AssetManager assetManager = getActivity().getAssets();

      InputStream istr = assetManager.open(strName);
      Bitmap bitmap = BitmapFactory.decodeStream(istr);
      istr.close();

      return bitmap;
    }

    private void setupText(View rootView, int id, String text) {
      TextView tv = (TextView) rootView.findViewById(id);
      tv.setText(text);
    }
  }

  private static final String KEY_INDEX = "index";
  private static final String KEY_META = "meta";
  private static final String KEY_WIDTH = "width";
  private final Context context;
  private final List<Bundle> exercises;
  private final Bundle metaInfo;

  private final int screenWidthPixels;

  public ExercisePagerAdapter(FragmentActivity fa, List<Bundle> exercises, Bundle metaInfo,
      int widthPixels, int heightPixels) {
    super(fa.getSupportFragmentManager());
    context = fa;
    this.exercises = exercises;
    this.metaInfo = metaInfo;
    screenWidthPixels = widthPixels;
  }

  @Override
  public Fragment getItem(int i) {
    Fragment fragment = Fragment.instantiate(context, ExericseFragment.class.getName());
    Bundle b = exercises.get(i);
    b.putInt(KEY_INDEX, i + 1);
    b.putBundle(KEY_META, metaInfo);
    b.putInt(KEY_WIDTH, screenWidthPixels);
    fragment.setArguments(b);
    return fragment;
  }

  @Override
  public int getCount() {
    return exercises.size();
  }

  @Override
  public CharSequence getPageTitle(int position) {
    return String.format(Locale.US, "%d / %d", position + 1, exercises.size());
  }
}
