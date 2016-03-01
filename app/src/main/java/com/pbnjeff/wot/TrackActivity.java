package com.pbnjeff.wot;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TrackActivity extends AppCompatActivity {

    private List<String> myExercises = new ArrayList<String>();
    ArrayAdapter<String> adapter = null;
    WorkoutHelper myExerciseListDB;
    EditText editName;
    Button buttonAddName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
        myExerciseListDB = new WorkoutHelper(this);

        editName = (EditText) findViewById(R.id.exercise_list_name_edit);

        populateExercises();

        adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.exercise_list);
        list.setAdapter(adapter);
    }

    private void populateExercises() {
        Cursor cursor = myExerciseListDB.getAllData();

        if (cursor.getCount() == 0) {
            return;
        }
        else {
            while (cursor.moveToNext()) {
                myExercises.add(cursor.getString(1));
            }
        }
    }

    private class MyListAdapter extends ArrayAdapter<String> {

        public MyListAdapter() {
            super(TrackActivity.this, R.layout.exercise_view, myExercises);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            final Cursor cursor = myExerciseListDB.getAllData();

            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.exercise_view, parent, false);
            }

            buttonAddName = (Button) findViewById(R.id.exercise_list_add_name);
            buttonAddName.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            boolean isInserted = myExerciseListDB.insertData(editName.getText().toString());
                            if (isInserted == true) {
                                Toast.makeText(TrackActivity.this,
                                        "Exercise added", Toast.LENGTH_LONG).show();
                                cursor.moveToLast();
                                myExercises.add(cursor.getString(1));
                                adapter.notifyDataSetChanged();
                            } else
                                Toast.makeText(TrackActivity.this,
                                        "Exercise not added", Toast.LENGTH_LONG).show();
                        }
                    }
            );

            TextView exerciseName = (TextView) itemView.findViewById(R.id.list_exercise_name);
            exerciseName.setText(myExercises.get(position));

            return itemView;
        }

    }
}
