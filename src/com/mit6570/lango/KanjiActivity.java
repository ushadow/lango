package com.mit6570.lango;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class KanjiActivity extends FragmentActivity {
	private static final String TAG = "Kanji Activity";
	private ViewPager vp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// ViewPager vp = (ViewPager) findViewById(R.id.pager_kanji);

		Bundle extras = getIntent().getExtras();
		String kanji_lesson = extras
				.getString(getString(R.string.kanji_lesson));

		String src = kanjiSrc(kanji_lesson);

		if (kanji_lesson.equalsIgnoreCase(getString(R.string.kanji_lesson_7))) {
			setContentView(R.layout.activity_kanji);

			Log.d(TAG, src);

			String srcBaseName = src.replace(".xml", "");

			try {
				InputStream is = getAssets().open(src);
				InputStreamReader isr = new InputStreamReader(is);

				KanjiParser kp = new KanjiParser(isr, this);

				List<Bundle> kanjis = kp.kanjis();
				Bundle metaInfo = new Bundle();

				metaInfo.putString(getString(R.string.kanji_lesson),
						kanji_lesson);

				KanjiPagerAdapter kpa = new KanjiPagerAdapter(this, kanjis,
						metaInfo);

				vp = (ViewPager) findViewById(R.id.pager_kanji);
				vp.setAdapter(kpa);

			} catch (IOException e) {

				e.printStackTrace();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			CharSequence text = getString(R.string.unimplemented_msg);
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(getApplicationContext(), text,
					duration);
			toast.show();
		}

		// final String srcBaseName = src.replace(".xml", "");
		//
		// try {
		// InputStream is = getAssets().open(src);
		// InputStreamReader isr = new InputStreamReader(is);
		// ExerciseParser ep = new ExerciseParser(isr, this);
		// List<Bundle> exes = ep.exercises();
		// Bundle metaInfo = ep.metaInfo();
		// metaInfo.putString(getString(R.string.ex_basename), srcBaseName);
		// ExercisePagerAdapter epa = new ExercisePagerAdapter(this, exes,
		// metaInfo);
		// vp.setAdapter(epa);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (XmlPullParserException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		 ActionBar actionBar = getActionBar();
		 actionBar.setDisplayHomeAsUpEnabled(true);
		 String kl = extras.getString(getString(R.string.kanji_lesson));

		 actionBar.setTitle("Kanji");
		 actionBar.setSubtitle(kl);
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

	public String kanjiSrc(String kanji_lesson) {

		String kanjiSrc = getString(R.string.course_name).concat("_Kanji_")
				.concat(kanji_lesson.replace(" ", "_")).concat(".xml");

		return kanjiSrc;
	}
}
