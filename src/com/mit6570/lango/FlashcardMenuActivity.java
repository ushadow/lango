package com.mit6570.lango;

import java.util.ArrayList;
import java.util.List;

import com.mit6570.lango.SectionAdapter.Section;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioButton;

public class FlashcardMenuActivity extends Activity {

	private ListView listview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flashcardmenu);

		listview = (ListView) findViewById(R.id.list_flashcard);

		// Instanciating an array list (you don't need to do this, you already
		// have yours)
		ArrayList<String> flashcard_lesson_list = new ArrayList<String>();
		flashcard_lesson_list.add("Lesson 7");
		flashcard_lesson_list.add("Lesson 8");
		flashcard_lesson_list.add("Lesson 9");
		flashcard_lesson_list.add("Lesson 10");
		flashcard_lesson_list.add("Lesson 11");
		flashcard_lesson_list.add("Lesson 12");

		// This is the array adapter, it takes the context of the activity as a
		// first
		// parameter, the type of list view as a second parameter and your array
		// as a third parameter
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, flashcard_lesson_list);
		listview.setAdapter(arrayAdapter);
	}

	public void onRadioButtonClicked(View view) {
		// Is the button now checked?
		boolean checked = ((RadioButton) view).isChecked();

		// Check which radio button was clicked
		switch (view.getId()) {
		case R.id.radiobn_eng2jap:
			if (checked)
				// Pirates are the best
				break;
		case R.id.radiobn_jap2eng:
			if (checked)
				// Ninjas rule
				break;
		}
	}

	public void onCheckboxClicked(View view) {
		boolean checked = ((CheckBox) view).isChecked();

		// Check which checkbox was clicked
		switch (view.getId()) {
		case R.id.chb_flashcardaudio:
			if (checked)
				// Put some meat on the sandwich
				// Remove the meat
				break;

		}
	}

}
