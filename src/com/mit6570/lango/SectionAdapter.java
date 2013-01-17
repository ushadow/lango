package com.mit6570.lango;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SectionAdapter extends BaseAdapter {
	    private Context mContext;
	    private String[] mSectionNames = {"Exercise", "Flashcards"};

	    public SectionAdapter(Context c) {
	        mContext = c;
	    }

	    public int getCount() {
	        return mSectionNames.length;
	    }

	    public Object getItem(int position) {
	        return null;
	    }

	    public long getItemId(int position) {
	        return 0;
	    }

	    // create a new ImageView for each item referenced by the Adapter
	    public View getView(int position, View convertView, ViewGroup parent) {
	        TextView textView;
	        if (convertView == null) {  // if it's not recycled, initialize some attributes
	            textView = new TextView(mContext);
	            textView.setText(mSectionNames[position]);
	        } else {
	            textView = (TextView) convertView;
	        }

	        return textView;
	    }

	    
	}
