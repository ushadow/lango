package com.mit6570.lango;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SectionAdapter extends BaseAdapter {
  public static class Section {
    private String name;
    private Class<? extends Activity> activityClass;
    
    public Section(String name, Class<? extends Activity> activityClass) {
      this.name = name;
      this.activityClass = activityClass;
    }
    
    public String name() { return name; }
    
    public Class<? extends Activity> activityClass() { return activityClass; }
  }
  
  private Context context;
  private List<Section> sections = new ArrayList<Section>();

  public SectionAdapter(Context c) {
    context = c;
    sections.add(new Section(context.getString(R.string.sec_Exercises), ExerciseMenuActivity.class));
    sections.add(new Section(context.getString(R.string.sec_Flashcards), FlashcardMenuActivity.class));
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
    TextView textView;
    if (convertView == null) { // if it's not recycled, initialize some
      // attributes
      textView = new TextView(context);
      textView.setText(sections.get(position).name());
    } else {
      textView = (TextView) convertView;
    }

    return textView;
  }
}
