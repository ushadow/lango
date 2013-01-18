package com.mit6570.lango;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SectionAdapter extends BaseAdapter {
  public static class Section {
    private String name;
    private Class<? extends Activity> activityClass;
    private int iconId;

    public Section(String name, int iconId, Class<? extends Activity> activityClass) {
      this.name = name;
      this.activityClass = activityClass;
      this.iconId = iconId;
    }

    public String name() {
      return name;
    }

    public Class<? extends Activity> activityClass() {
      return activityClass;
    }

    public int iconId() {
      return iconId;
    }
  }

  private Context context;

  private List<Section> sections = new ArrayList<Section>();

  public SectionAdapter(Context c) {
    context = c;
    sections.add(new Section(context.getString(R.string.sec_Exercises), R.drawable.ic_exercise,
        ExerciseMenuActivity.class));
    sections.add(new Section(context.getString(R.string.sec_Flashcards), R.drawable.ic_flashcard,
        FlashcardMenuActivity.class));
    sections.add(new Section(context.getString(R.string.sec_Grammar), R.drawable.ic_grammar,
        FlashcardMenuActivity.class));
    sections.add(new Section(context.getString(R.string.sec_Vocabulary), R.drawable.ic_vocabulary,
        FlashcardMenuActivity.class));
    sections.add(new Section(context.getString(R.string.sec_Audio), R.drawable.ic_audio,
        FlashcardMenuActivity.class));
  }

  public int getCount() {
    return sections.size();
  }

  public Section getItem(int position) {
    return sections.get(position);
  }

  public long getItemId(int position) {
    return 0;
  }

  public View getView(int position, View convertView, ViewGroup parent) {
    LinearLayout ll = null;
    if (convertView == null) { // if it's not recycled, initialize some
      LayoutInflater inflater =
          (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      Section section = sections.get(position);

      ll = (LinearLayout) inflater.inflate(R.layout.section, null);
      ImageView iv = (ImageView) ll.findViewById(R.id.section_image);
      iv.setImageResource(section.iconId());
      TextView tv = (TextView) ll.findViewById(R.id.section_text);
      tv.setText(section.name());
    } else {
      ll = (LinearLayout) convertView;
    }

    return ll;
  }
}
