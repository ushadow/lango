package com.mit6570.lango;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;

public class ExerciseActivity extends FragmentActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_exercise);
    
    ViewPager vp = (ViewPager) findViewById(R.id.pager_exercise);
    
    final String src = (String) getIntent().getExtras().get("exercise_src");
    final String srcBaseName = src.replace(".xml", "");
    
    try {
      InputStream is = getAssets().open(src);
      InputStreamReader isr = new InputStreamReader(is);
      ExerciseParser ep = new ExerciseParser(isr, this);
      List<Bundle> exes = ep.exercises();

      ExercisePagerAdapter epa = new ExercisePagerAdapter(this, exes, srcBaseName, 
                                                          ep.instruction());
      vp.setAdapter(epa);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (XmlPullParserException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.activity_question, menu);
    return true;
  }
}
