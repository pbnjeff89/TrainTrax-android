package com.pbnjeff.wot;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.List;

public class ExerciseSearchActivity extends AppCompatActivity {

    List<String> suggestedExercises;
    TextView noSuggestions;
    String emptyList = "No suggestions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_search);

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            /*

            if(anything in the list of exercises contains the trimmed query) {
                set the suggestedExercises to that list
            }
            else{
                set the TextView as "no suggestions"
            }


             */

        }
    }

}
