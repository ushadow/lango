package com.mit6570.lango;

import java.util.List;

import org.jsoup.nodes.Entities.EscapeMode;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class ExpandableMenuAdapter extends BaseExpandableListAdapter {

  private Context context;
  private List<ExerciseMenu> exercises;
  private ExpandableListView[] listViewCache;
  
  public ExpandableMenuAdapter(Context context, List<ExerciseMenu> exercises) {
    this.context = context;
    this.exercises = exercises;
    listViewCache = new ExpandableListView[exercises.size()];
  }

  @Override
  public Object getChild(int groupPosition, int childPosition) {
    return exercises.get(groupPosition).getDrill(childPosition);
  }

  @Override
  public long getChildId(int groupPosition, int childPosition) {
    return 0;
  }

  @Override
  public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
      View convertView, ViewGroup parent) {

    if (listViewCache[groupPosition] != null) {
      convertView = listViewCache[groupPosition];
    } else {
      List<ExerciseMenu> drills = exercises.get(groupPosition).drills();
      ExpandableMenuView ev = new ExpandableMenuView(context, drills);
      ev.setRows(exercises.get(groupPosition).drillCount());
      ev.setAdapter(new ExpandableMenuAdapter(context, drills));
      listViewCache[groupPosition] = ev;
      convertView = ev;
    }
//    if (em.isDrill()) {
//      TextView tv = (TextView) convertView;
//      tv.setText(em.name());
//      if (em.src() == null) {
//        tv.setTextColor(context.getResources().getColor(R.color.txt_unimplemented));
//      } else {
//        tv.setTextColor(context.getResources().getColor(R.color.txt_black));
//      }
//    }

    return convertView;
  }

  @Override
  public int getChildrenCount(int groupPosition) {
    ExerciseMenu em = exercises.get(groupPosition);
    if (em.drillCount() > 0)
      return 1;
    else 
      return 0;
  }

  @Override
  public Object getGroup(int groupPosition) {
    return exercises.get(groupPosition);
  }

  @Override
  public int getGroupCount() {
    return exercises.size();
  }

  @Override
  public long getGroupId(int groupPosition) {
    return 0;
  }

  @Override
  public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

    if (convertView == null) {
      LayoutInflater inflater =
          (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      ExerciseMenu em = exercises.get(groupPosition);
      if (em.parent() == null)
        convertView = inflater.inflate(R.layout.list_item, parent, false);
      else
        convertView = inflater.inflate(R.layout.list_child_item, parent, false);
    }

    TextView tv = (TextView) convertView;
    tv.setText(exercises.get(groupPosition).name());
    return convertView;
  }

  @Override
  public boolean hasStableIds() {
    return false;
  }

  @Override
  public boolean isChildSelectable(int groupPosition, int childPosition) {
    return true;
  }
}
