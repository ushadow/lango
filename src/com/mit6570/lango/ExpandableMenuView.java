package com.mit6570.lango;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

public class ExpandableMenuView extends ExpandableListView {
  private int rows;
  private List<ExerciseMenu> exercises;
  private Context context;

  public ExpandableMenuView(Context c, List<ExerciseMenu> exes) {
    super(c);
    this.exercises = exes;
    this.context = c;
    
    setOnChildClickListener(new OnChildClickListener() {

      @Override
      public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
          int childPosition, long id) {
        
        ExerciseMenu lesson = exercises.get(groupPosition);
        ExerciseMenu drill = lesson.getDrill(childPosition);
        String drillSource = drill.src();
        startDrillActivity(drillSource, lesson.name(), drill.name());
        return true;
      }
    });
    
    setOnGroupClickListener(new OnGroupClickListener() {
      @Override
      public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        ExerciseMenu lesson = exercises.get(groupPosition);
        if (lesson.isDrill()) {
          startDrillActivity(lesson.src(), lesson.name(), lesson.parent().name());
          return true;
        } else {
          return false;
        }
      }
    });
  }
  
  public void setRows(int rows) {
    this.rows = rows;
    Log.d("lango", "rows set: " + rows);
  }
  
  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    Log.d("lango", "measured height: " + getMeasuredHeight());
    setMeasuredDimension(getMeasuredWidth(), rows * getMeasuredHeight());
  }
  
  private void startDrillActivity(String drillSource, String lessonName, String drillName) {
    if (drillSource != null) {
      Intent intent = new Intent(context, ExerciseActivity.class);
      intent.putExtra(context.getString(R.string.ex_src), drillSource);
      intent.putExtra(context.getString(R.string.ex_lesson), lessonName);
      intent.putExtra(context.getString(R.string.ex_drill), drillName);
      context.startActivity(intent);
    } else {
      CharSequence text = context.getString(R.string.unimplemented_msg);
      int duration = Toast.LENGTH_SHORT;
      Toast toast = Toast.makeText(context.getApplicationContext(), text, duration);
      toast.show();
    }
  }
}
