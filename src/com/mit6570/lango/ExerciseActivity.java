package com.mit6570.lango;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

public class ExerciseActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise);

		ViewPager vp = (ViewPager) findViewById(R.id.pager_exercise);

		Bundle extras = getIntent().getExtras();
		final String src = extras.getString(getString(R.string.ex_src));
		final String srcBaseName = src.replace(".xml", "");
		
		WindowManager w = this.getWindowManager();
		Display d = w.getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		d.getMetrics(metrics);
		// since SDK_INT = 1;
		int widthPixels = metrics.widthPixels;
		int heightPixels = metrics.heightPixels;
		// includes window decorations (statusbar bar/menu bar)
		if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17)
		try {
		    widthPixels = (Integer) Display.class.getMethod("getRawWidth").invoke(d);
		    heightPixels = (Integer) Display.class.getMethod("getRawHeight").invoke(d);
		} catch (Exception ignored) {
		}
		// includes window decorations (statusbar bar/menu bar)
		if (Build.VERSION.SDK_INT >= 17)
		try {
		    Point realSize = new Point();
		    Display.class.getMethod("getRealSize", Point.class).invoke(d, realSize);
		    widthPixels = realSize.x;
		    heightPixels = realSize.y;
		} catch (Exception ignored) {
		}
		
		try {
			InputStream is = getAssets().open(src);
			InputStreamReader isr = new InputStreamReader(is);
			ExerciseParser ep = new ExerciseParser(isr, this);
			List<Bundle> exes = ep.exercises();
			Bundle metaInfo = ep.metaInfo();
			metaInfo.putString(getString(R.string.ex_basename), srcBaseName);
			ExercisePagerAdapter epa = new ExercisePagerAdapter(this, exes,
					metaInfo, widthPixels, heightPixels);
			vp.setAdapter(epa);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		String lesson = extras.getString(getString(R.string.ex_lesson));
		String drill = extras.getString(getString(R.string.ex_drill));
		actionBar.setTitle(lesson);
		actionBar.setSubtitle(drill);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_exercise, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, HomeActivity.class);
			startActivity(intent);
			return true;
		case R.id.menu_help:
			OptionMenuDialog.showHelp(this);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
