package com.pbnjeff.wot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TrackActivity extends AppCompatActivity {

    private List<Exercise> myExercises = new ArrayList<Exercise>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);

        populateExerciseList();
        populateListView();

        final ListView listview = (ListView) findViewById(R.id.exercise_list);

    }

    private void populateExerciseList() {
        myExercises.add(new Exercise("Squat", 132.5, 10, 1));
        myExercises.add(new Exercise("Bench", 100, 10, 2));
        myExercises.add(new Exercise("Deadlift", 150, 10, 3));
    }

    private void populateListView() {
        ArrayAdapter<Exercise> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.exercise_list);
        list.setAdapter(adapter);
    }

    private class MyListAdapter extends ArrayAdapter<Exercise> {

        public MyListAdapter() {
            super(TrackActivity.this, R.layout.exercise_view, myExercises);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;

            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.exercise_view, parent, false);
            }

            Exercise currentExercise = myExercises.get(position);

            TextView exerciseName = (TextView) itemView.findViewById(R.id.list_exercise_name);
            exerciseName.setText(currentExercise.getName());

            TextView weightUsed = (TextView) itemView.findViewById(R.id.exercise_weight_used);
            String weight = String.valueOf(currentExercise.getWeight());
            weightUsed.setText(weight);

            TextView repsPerformed = (TextView) itemView.findViewById(R.id.list_exercise_reps);
            String reps = String.valueOf(currentExercise.getReps());
            repsPerformed.setText(reps);

            TextView rpe = (TextView) itemView.findViewById(R.id.list_exercise_RPE);
            String rpeString = String.valueOf(currentExercise.getRPE());
            rpe.setText(rpeString);

            return itemView;
        }

    }

}
