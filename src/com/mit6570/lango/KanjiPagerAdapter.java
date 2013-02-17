package com.mit6570.lango;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

public class KanjiPagerAdapter extends FragmentStatePagerAdapter {
	// Instances of this class are fragments representing a single
	// object in our collection.
	private static final String KEY_INDEX = "index";
	private static final String KEY_META = "meta";
	private final Context context;
	private final List<Bundle> kanjis;
	private final Bundle metaInfo;
	

	public KanjiPagerAdapter(FragmentActivity fa, List<Bundle> kanjis,
			Bundle metaInfo) {
		super(fa.getSupportFragmentManager());
		context = fa;
		this.kanjis = kanjis;
		this.metaInfo = metaInfo;
		Log.d("KANJI PAGER ADAPTER", "entered");
	}

	public static class KanjiFragment extends Fragment {

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// The last two arguments ensure LayoutParams are inflated
			// properly.
			
			Log.d("KANJI PAGER ADAPTER", "entered onCreateView");
			
			View rootView = inflater.inflate(R.layout.fragment_kanji,
					container, false);
			Bundle args = getArguments();
			return createView(rootView, args);
			
			
		}

		private View createView(View rootView, Bundle b) {

			Log.d("KANJI PAGER ADAPTER", "entered createView of onCreateView");
			
			Bundle metaInfo = b.getBundle(KEY_META);
//			String instruction = metaInfo
//					.getString(getString(R.string.ex_instruction));
//			
//			Log.d("KANJI PAGER ADAPTER", instruction.concat("BEFORE is instruction"));
			
			setupText(rootView, R.id.kanji_kanji, b.getString(getString(R.string.kanji_kanji)));
			setupText(rootView, R.id.kanji_meaning, b.getString(getString(R.string.kanji_meaning)));
			setupText(rootView, R.id.kanji_on_yomi, b.getString(getString(R.string.kanji_on_yomi)));
			setupText(rootView, R.id.kanji_kun_yomi, b.getString(getString(R.string.kanji_kun_yomi)));
			setupText(rootView, R.id.kanji_compound1, b.getString(getString(R.string.kanji_compound1)));
			setupText(rootView, R.id.kanji_compound2, b.getString(getString(R.string.kanji_compound2)));
			setupText(rootView, R.id.kanji_compound3, b.getString(getString(R.string.kanji_compound3)));
			
			if (b.containsKey(getString(R.string.kanji_compound4))){
				setupText(rootView, R.id.kanji_compound4, b.getString(getString(R.string.kanji_compound4)));
			}
			if (b.containsKey(getString(R.string.kanji_compound5))){
				setupText(rootView, R.id.kanji_compound5, b.getString(getString(R.string.kanji_compound5)));
			}
			if (b.containsKey(getString(R.string.kanji_compound6))){
				setupText(rootView, R.id.kanji_compound6, b.getString(getString(R.string.kanji_compound6)));
			}

//			setupText(rootView, R.id.kanji_kanji, b.getString(getString(R.string.kanji_kanji)));
//			setupText(rootView, R.id.kanji_kanji, b.getString(getString(R.string.kanji_kanji)));
//			setupText(rootView, R.id.kanji_kanji, b.getString(getString(R.string.kanji_kanji)));
//			setupText(rootView, R.id.kanji_kanji, b.getString(getString(R.string.kanji_kanji)));
//			setupText(rootView, R.id.kanji_kanji, b.getString(getString(R.string.kanji_kanji)));
//			setupText(rootView, R.id.kanji_kanji, b.getString(getString(R.string.kanji_kanji)));
//			setupText(rootView, R.id.kanji_kanji, b.getString(getString(R.string.kanji_kanji)));

			// String question = b.getString(getString(R.string.ex_question));
			// if (question != null) {
			// setupText(rootView, R.id.text_question, question);
			// }
			


			return rootView;
		}

		private void setupText(View rootView, int id, String text) {
			TextView tv = (TextView) rootView.findViewById(id);
			tv.setText(text);
		}
	}

	@Override
	public Fragment getItem(int i) {
		Log.d("KANJI PAGER ADAPTER", "entered getItem");
		Fragment fragment = Fragment.instantiate(context,
				KanjiFragment.class.getName());
//		Fragment fragment=new KanjiFragment();
		Bundle b = kanjis.get(i);
		b.putInt(KEY_INDEX, i + 1);
		b.putBundle(KEY_META, metaInfo);
		fragment.setArguments(b);
		return fragment;
	}

	@Override
	public int getCount() {
		Log.d("KANJI PAGER ADAPTER", "entered getCount");
		return kanjis.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		Log.d("KANJI PAGER ADAPTER", "entered getPageTitle");
		return String.format(Locale.US, "%d / %d", position + 1, kanjis.size());
	}
}
