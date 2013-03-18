package com.mit6570.lango;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class HomeActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

    GridView gridview = (GridView) findViewById(R.id.home_gridview);
    final SectionAdapter sectionAdapter = new SectionAdapter(this);
    gridview.setAdapter(sectionAdapter);
    gridview.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Class<? extends Activity> c = sectionAdapter.getItem(position).activityClass();
        if (c != null) {
          Intent intent = new Intent(view.getContext(), c);
          intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
          startActivity(intent);
        } else {
          CharSequence text = getString(R.string.unimplemented_msg);
          int duration = Toast.LENGTH_SHORT;
          Toast toast = Toast.makeText(getApplicationContext(), text, duration);
          toast.show();
        }
      }
    });
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
