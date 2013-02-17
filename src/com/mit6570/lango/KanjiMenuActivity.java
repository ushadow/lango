package com.mit6570.lango;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class KanjiMenuActivity extends Activity {

	private String courseName = "Kangji";
	
	private static final String TAG="Kanji Menu";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kanjimenu);
		ListView lv = (ListView) findViewById(R.id.kanjimenulist);

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long row_id) {
			
				String itemText = parent.getItemAtPosition(position).toString();
//				int kanji_lesson_number=0;
//				
//				if (itemText.equalsIgnoreCase(getString(R.string.kanji_lesson_7))){
//					kanji_lesson_number=7;
//				}else if (itemText.equalsIgnoreCase(getString(R.string.kanji_lesson_8))){
//					kanji_lesson_number=8;
//				}else if (itemText.equalsIgnoreCase(getString(R.string.kanji_lesson_9))){
//					kanji_lesson_number=9;
//				}else if (itemText.equalsIgnoreCase(getString(R.string.kanji_lesson_10))){
//					kanji_lesson_number=10;
//				}else if (itemText.equalsIgnoreCase(getString(R.string.kanji_lesson_11))){
//					kanji_lesson_number=11;
//				}else if (itemText.equalsIgnoreCase(getString(R.string.kanji_lesson_12))){
//					kanji_lesson_number=12;
//				}
				
				Intent intent = new Intent(v.getContext(), KanjiActivity.class);
				
				intent.putExtra(getString(R.string.kanji_lesson), itemText);
				Log.d(TAG, itemText);
				Log.d(TAG, Integer.toString(position));
				startActivity(intent);

			}
		});

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(courseName);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, HomeActivity.class);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
//
// private List<ExerciseMenu> parseXml() {
// List<ExerciseMenu> exercises = null;
// try {
// XmlPullParser parser = this.getResources().getXml(R.xml.mit_japanese_kanji);
// int eventType = parser.getEventType();
// boolean done = false;
// ExerciseMenu currentExe = null;
// while (eventType != XmlPullParser.END_DOCUMENT && !done) {
// String name = null;
// switch (eventType) {
// case XmlPullParser.START_DOCUMENT:
// exercises = new ArrayList<ExerciseMenu>();
// break;
// case XmlPullParser.START_TAG:
// name = parser.getName();
// if (name.equalsIgnoreCase(LESSON_TAG)) {
// String lessonName = Utils.attributes(parser).get(NAME_ATTRIBUTE);
// currentExe = new ExerciseMenu(lessonName);
// } else if (currentExe != null) {
//
// } else if (name.equalsIgnoreCase(ROOT_TAG)) {
// courseName = Utils.attributes(parser).get(NAME_ATTRIBUTE);
// }
// break;
// case XmlPullParser.END_TAG:
// name = parser.getName();
// if (name.equalsIgnoreCase(LESSON_TAG) && currentExe != null) {
// exercises.add(currentExe);
// } else if (name.equalsIgnoreCase(ROOT_TAG)) {
// done = true;
// }
// break;
// }
// eventType = parser.next();
// }
// } catch (XmlPullParserException e) {
// // TODO Auto-generated catch block
// e.printStackTrace();
// } catch (IOException e) {
// // TODO Auto-generated catch block
// e.printStackTrace();
// }
// return exercises;
// }
// }
