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

    /**
     * 
     * @param name
     * @param iconId
     * @param activityClass can be null.
     */
    public Section(String name, int iconId, Class<? extends Activity> activityClass) {
      this.name = name;
      this.activityClass = activityClass;
      this.iconId = iconId;
    }

    public String name() {
      return name;
    }

    /**
     * Class of the activity for this section.
     * @return
     */
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
                             null));
    sections.add(new Section(context.getString(R.string.sec_Grammar), R.drawable.ic_grammar, null));
    sections.add(new Section(context.getString(R.string.sec_Vocabulary), R.drawable.ic_vocabulary,
                             null));
    sections.add(new Section(context.getString(R.string.sec_Audio), R.drawable.ic_audio, null));
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

      ll = (LinearLayout) inflater.inflate(R.layout.grid_item_home, null);
    } else {
      ll = (LinearLayout) convertView;
    }
    
    Section section = sections.get(position);
    ImageView iv = (ImageView) ll.findViewById(R.id.section_image);
    iv.setImageResource(section.iconId());
    TextView tv = (TextView) ll.findViewById(R.id.section_text);
    tv.setText(section.name());
    if (section.activityClass() == null) {
      tv.setTextColor(context.getResources().getColor(R.color.txt_unimplemented));
    }
    return ll;
  }
}
