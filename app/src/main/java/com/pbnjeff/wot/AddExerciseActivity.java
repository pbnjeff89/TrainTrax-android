package com.pbnjeff.wot;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AddExerciseActivity extends AppCompatActivity {

    ListView addExerciseList;
    EditText searchExercise;

    Button addConfirm;

    Resources res;
    String[] exerciseDB;

    ArrayAdapter<String> adapter;

    String exerciseToReturn;
    TextView preview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        exerciseToReturn = "";
        preview = (TextView) findViewById(R.id.add_preview);
        preview.setText("Exercise to add: " + exerciseToReturn);

        // set up exercise list array

        res = getResources();
        exerciseDB = res.getStringArray(R.array.exercises);

        // configure EditText

        searchExercise = (EditText) findViewById(R.id.edit_add_exercise);

        // configure buttons

        addConfirm = (Button) findViewById(R.id.add_confirm);

        addConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(exerciseToReturn.equals("")) {
                    Toast.makeText(AddExerciseActivity.this,
                            "Please select a an exercise",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent = getIntent();
                    intent.putExtra("addExercise", exerciseToReturn);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        // configure listview

        addExerciseList = (ListView) findViewById(R.id.lv_add_exercise);

        // configure listview adapter

        adapter = new ArrayAdapter<String>(AddExerciseActivity.this,
                R.layout.add_exercise_layout_row,
                R.id.add_exercise_row,
                exerciseDB);
        addExerciseList.setAdapter(adapter);

        // set EditText behavior

        searchExercise.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // set up listener for listview

        addExerciseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String exerciseSelected = adapter.getItem(position).toString();
                confirmAdd(exerciseSelected);
            }

        });

    }

    private void confirmAdd(final String exerciseSelected) {

        AlertDialog.Builder addAlertBuilder = new AlertDialog.Builder(AddExerciseActivity.this)
                .setMessage("Do you want to add " + exerciseSelected + "?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        exerciseToReturn = exerciseSelected;
                        preview.setText("Exercise to add: " + exerciseToReturn);
                    }
                })
                .setNegativeButton("Cancel", null);
        AlertDialog addAlert = addAlertBuilder.create();
        addAlert.show();

    }
}
