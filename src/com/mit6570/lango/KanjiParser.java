package com.mit6570.lango;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;

public class KanjiParser {
	// private static final String EXERCISE_TAG = "exercise";
	// private static final String ROOT_TAG = "exercises";
	// private static final String ANSWER_TAG = "answer";
	// private static final String INSTR_TAG = "instruction";
	// private static final String QUESTION_TAG = "question";
	// private static final String AUDIO_ATTRIBUTE = "audio";
	// private static final String IMG_ATTRIBUTE = "img";
	// private static final String RECORD_ATTRIBUTE = "record";
	private static final String ROOT_TAG = "Root";
	private static final String ROW_TAG = "Row";
	private static final String SERIALN_TAG = "SerialN";
	private static final String KANJI_TAG = "kanji";

	private static final String ONYOMI_TAG = "on_yomi";
	private static final String KUNYOMI_TAG = "kun_yomi";

	private static final String MEANING_TAG = "meaning";
	private static final String COMPOUND1_TAG = "compound1";
	private static final String COMPOUND2_TAG = "compound2";

	private static final String COMPOUND3_TAG = "compound3";

	private static final String COMPOUND4_TAG = "compound4";
	private static final String COMPOUND5_TAG = "compound5";

	private static final String COMPOUND6_TAG = "compound6";

	private final XmlPullParser parser;
	private final Context context;
	private List<Bundle> kanjis;

	/**
	 * Meta information about the kanjis.
	 */
//	private Bundle metaInfo = new Bundle();

	/**
	 * Creates a parser for kanjis.
	 * 
	 * @param isr
	 *            the {code: InputStreamReader} is closed by this {code:
	 *            ExerciseParser}
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	public KanjiParser(InputStreamReader isr, Context context)
			throws XmlPullParserException, IOException {
		this.context = context;
		parser = Xml.newPullParser();
		parser.setInput(isr);
		parse();
		isr.close();
	}

//	public Bundle metaInfo() {
//		return metaInfo;
//	}

	public List<Bundle> kanjis() {
		return kanjis;
	}

	private void parse() {
		try {
			int eventType = parser.getEventType();
			boolean done = false;
			Bundle currentKanji = null;
			while (eventType != XmlPullParser.END_DOCUMENT && !done) {
				String name = null;
				String debug=null;
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					kanjis = new ArrayList<Bundle>();
					
					break;
				case XmlPullParser.START_TAG:
					name = parser.getName();

					if (name.equalsIgnoreCase(ROW_TAG)) {

						currentKanji = new Bundle();

					} else if (currentKanji != null) {
						if (name.equalsIgnoreCase(SERIALN_TAG)) {
							currentKanji.putString(context
									.getString(R.string.kanji_serial_number),
									parser.nextText());
						} else if (name.equalsIgnoreCase(KANJI_TAG)) {
							debug=parser.nextText();
							currentKanji.putString(
									context.getString(R.string.kanji_kanji),
									debug);
							Log.d("KANJI PARSER", debug);
//							currentKanji.putString(
//									context.getString(R.string.kanji_kanji),
//									parser.nextText());
						
						} else if (name.equalsIgnoreCase(ONYOMI_TAG)) {

							currentKanji.putString(
									context.getString(R.string.kanji_on_yomi),
									parser.nextText());

						} else if (name.equalsIgnoreCase(KUNYOMI_TAG)) {
							currentKanji.putString(
									context.getString(R.string.kanji_kun_yomi),
									parser.nextText());
						} else if (name.equalsIgnoreCase(MEANING_TAG)) {
							currentKanji.putString(
									context.getString(R.string.kanji_meaning),
									parser.nextText());
						} else if (name.equalsIgnoreCase(COMPOUND1_TAG)) {
							currentKanji.putString(context
									.getString((R.string.kanji_compound1)),
									parser.nextText());

						} else if (name.equalsIgnoreCase(COMPOUND2_TAG)) {
							currentKanji.putString(context
									.getString((R.string.kanji_compound2)),
									parser.nextText());

						} else if (name.equalsIgnoreCase(COMPOUND3_TAG)) {
							currentKanji.putString(context
									.getString((R.string.kanji_compound3)),
									parser.nextText());

						} else if (name.equalsIgnoreCase(COMPOUND4_TAG)) {
							currentKanji.putString(context
									.getString((R.string.kanji_compound4)),
									parser.nextText());

						} else if (name.equalsIgnoreCase(COMPOUND5_TAG)) {
							currentKanji.putString(context
									.getString((R.string.kanji_compound5)),
									parser.nextText());

						} else if (name.equalsIgnoreCase(COMPOUND6_TAG)) {
							currentKanji.putString(context
									.getString((R.string.kanji_compound6)),
									parser.nextText());
						}

					}
					break;
					
				case XmlPullParser.END_TAG:
					name = parser.getName();
					if (name.equalsIgnoreCase(ROW_TAG)
							&& currentKanji != null) {
						kanjis.add(currentKanji);
						currentKanji = null;
					} else if (name.equalsIgnoreCase(ROOT_TAG)) {
						done = true;
					}
					break;
				}
				eventType = parser.next();
			}
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
