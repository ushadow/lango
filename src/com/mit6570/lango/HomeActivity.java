package com.mit6570.lango;

import com.mit6570.lango.CourseListAdapter.Course;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

/**
 * Home screen activity.
 * 
 * @author yingyin
 * 
 */
public class HomeActivity extends Activity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

    LinearLayout courseView = (LinearLayout) findViewById(R.id.course_view);
    CourseListAdapter adapter = new CourseListAdapter(this);
    for (int i = 0; i < adapter.getCount(); i++) {
      View view = adapter.getView(i, null, courseView);
      final Course course = (Course) adapter.getItem(i);
      view.setOnClickListener(new OnClickListener() {

        @Override
        public void onClick(View v) {
          if (course.src != null) {
            Intent intent = new Intent(v.getContext(), ExerciseMenuActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            intent.putExtra(getString(R.string.key_course_src), course.src);
            startActivity(intent);
          }

        }
      });
      courseView.addView(view);
    }
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
