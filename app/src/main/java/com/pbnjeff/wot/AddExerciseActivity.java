package com.pbnjeff.wot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class AddExerciseActivity extends AppCompatActivity {

    AlertDialog.Builder addExerciseAlertBuilder;
    ListView addExerciseList;
    EditText searchExercise;

    Button addConfirm;
    Button addCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        // configure EditText

        searchExercise = (EditText) findViewById(R.id.edit_add_exercise);

        // configure buttons

        addConfirm = (Button) findViewById(R.id.add_confirm);

        addConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                intent.putExtra("addExercise", searchExercise.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        addCancel = (Button) findViewById(R.id.add_cancel);

        addCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                 setResult(RESULT_CANCELED, intent);
                finish();
            }
        });

    }
}
