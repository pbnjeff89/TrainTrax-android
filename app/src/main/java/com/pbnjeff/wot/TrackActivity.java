package com.pbnjeff.wot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class TrackActivity extends AppCompatActivity {

    private LinearLayout newLayout;
    private EditText weightText, repText, rpeText;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);

        Spinner spinner = (Spinner) findViewById(R.id.exercise_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.exercises_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        newLayout = (LinearLayout) findViewById(R.id.exercise_sets);
        weightText = (EditText) findViewById(R.id.weight_input);
        repText = (EditText) findViewById(R.id.reps_input);
        rpeText = (EditText) findViewById(R.id.rpe_input);
        addButton = (Button) findViewById(R.id.add_button);
        addButton.setOnClickListener(onClick());
        TextView textView = new TextView(this);
        textView.setText("lol");
    }

    private View.OnClickListener onClick() {

        // Please clear the EditText each time the button is pressed

        return new View.OnClickListener() {

            @Override
            public void onClick (View view) {
                newLayout.addView(createNewTextView(weightText.getText().toString() +
                        " x " + repText.getText().toString() +
                        " @ " + rpeText.getText().toString()));
            }
        };
    }

    private TextView createNewTextView(String text) {
        final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final TextView textView = new TextView(this);

        textView.setLayoutParams(lparams);
        textView.setText(text);
        return textView;
    }

}
