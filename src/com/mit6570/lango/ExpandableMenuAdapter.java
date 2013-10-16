package com.mit6570.lango;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ExpandableMenuAdapter extends BaseExpandableListAdapter{

    private Context context;
    private List<ExerciseMenu> exercises;
    
    public ExpandableMenuAdapter(Context context, List<ExerciseMenu> exercises) {
     this.context = context;
     this.exercises = exercises;
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
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

     if (convertView == null) {
      LayoutInflater inflater =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      convertView = inflater.inflate(R.layout.list_child_item, null);
     }

     TextView tv = (TextView) convertView;
     ExerciseMenu em = exercises.get(groupPosition).getDrill(childPosition); 
     tv.setText(em.name());
     if (em.src() == null) {
       tv.setTextColor(context.getResources().getColor(R.color.txt_unimplemented));
     } else {
       tv.setTextColor(context.getResources().getColor(R.color.txt_black));
     }

     return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
     return exercises.get(groupPosition).drillCount();
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
    public View getGroupView(int groupPosition, boolean isExpanded,
      View convertView, ViewGroup parent) {

     if (convertView == null) {
      LayoutInflater inflater =  (LayoutInflater) context.getSystemService(
        Context.LAYOUT_INFLATER_SERVICE);
      convertView = inflater.inflate(R.layout.list_item, null);
     }

     TextView tv = (TextView) convertView;
     tv.setText(exercises.get(groupPosition).name());
     
     //testing by Lei Zhang
     tv.setBackgroundColor(context.getResources().getColor(R.color.red_bk));

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
