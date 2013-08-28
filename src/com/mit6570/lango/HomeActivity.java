package com.mit6570.lango;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class HomeActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

    LinearLayout courseView = (LinearLayout) findViewById(R.id.course_view);
    CourseListAdapter adapter = new CourseListAdapter(this);
    for (int i = 0; i < adapter.getCount(); i++) {
      courseView.addView(adapter.getView(i, null, courseView));
    }
    //  	LinearLayout jap3 = (LinearLayout) findViewById(R.id.jap3_box);
//  	 
//  	jap3.setOnClickListener(new OnClickListener() {
//         	@Override
//         	public void onClick(View view) {
//               	// TODO Auto-generated method stub
//               	Intent intent = new Intent(view.getContext(),
//                             	ExerciseMenuActivity.class);
//               	intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//               	startActivity(intent);
//         	}
//  	});
    
//    GridView gridview = (GridView) findViewById(R.id.home_gridview);
//    final SectionAdapter sectionAdapter = new SectionAdapter(this);
//    gridview.setAdapter(sectionAdapter);
//    gridview.setOnItemClickListener(new OnItemClickListener() {
//      @Override
//      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Class<? extends Activity> c = sectionAdapter.getItem(position).activityClass();
//        if (c != null) {
//          Intent intent = new Intent(view.getContext(), c);
//          intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//          startActivity(intent);
//        } else {
//          CharSequence text = getString(R.string.unimplemented_msg);
//          int duration = Toast.LENGTH_SHORT;
//          Toast toast = Toast.makeText(getApplicationContext(), text, duration);
//          toast.show();
//        }
//      }
//    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.activity_home, menu);
    return true;
  }
  
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.menu_about:
        OptionMenuDialog.showAbout(this);
        return true;
      case R.id.menu_sync:
        (new ContentSyncer(this)).execute();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
