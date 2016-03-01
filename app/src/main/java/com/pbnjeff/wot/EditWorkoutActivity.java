package com.pbnjeff.wot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EditWorkoutActivity extends AppCompatActivity {

    private List<Exercise> myExercises = new ArrayList<Exercise>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_workout);

        populateExerciseList();
        populateListView();
    }

    /*

    Future goal:
    - Set up OnClickListener to

     */

    private void populateExerciseList() {
        myExercises.add(new Exercise("Squat", 132.5, 10, 1));
        myExercises.add(new Exercise("Bench", 100, 10, 2));
        myExercises.add(new Exercise("Deadlift", 150, 10, 3));
        myExercises.add(new Exercise("Snatch", 150, 10, 3));
        myExercises.add(new Exercise("Clean & Jerk", 150, 10, 3));
        myExercises.add(new Exercise("Strict Curl", 150, 10, 3));
        myExercises.add(new Exercise("Squat", 132.5, 10, 1));
        myExercises.add(new Exercise("Bench", 100, 10, 2));
        myExercises.add(new Exercise("Deadlift", 150, 10, 3));
        myExercises.add(new Exercise("Snatch", 150, 10, 3));
        myExercises.add(new Exercise("Clean & Jerk", 150, 10, 3));
        myExercises.add(new Exercise("Strict Curl", 150, 10, 3));
        myExercises.add(new Exercise("Squat", 132.5, 10, 1));
        myExercises.add(new Exercise("Bench", 100, 10, 2));
        myExercises.add(new Exercise("Deadlift", 150, 10, 3));
        myExercises.add(new Exercise("Snatch", 150, 10, 3));
        myExercises.add(new Exercise("Clean & Jerk", 150, 10, 3));
        myExercises.add(new Exercise("Strict Curl", 150, 10, 3));
        myExercises.add(new Exercise("Squat", 132.5, 10, 1));
        myExercises.add(new Exercise("Bench", 100, 10, 2));
        myExercises.add(new Exercise("Deadlift", 150, 10, 3));
        myExercises.add(new Exercise("Snatch", 150, 10, 3));
        myExercises.add(new Exercise("Clean & Jerk", 150, 10, 3));
        myExercises.add(new Exercise("Strict Curl", 150, 10, 3));
    }

    private void populateListView() {
        ArrayAdapter<Exercise> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.edit_workout_list);
        list.setAdapter(adapter);
    }

    private class MyListAdapter extends ArrayAdapter<Exercise> {

        public MyListAdapter() {
            super(EditWorkoutActivity.this, R.layout.weight_rep_rpe_layout, myExercises);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;

            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.weight_rep_rpe_layout, parent, false);
            }

            Exercise currentExercise = myExercises.get(position);

            TextView exerciseWeight = (TextView) itemView.findViewById(R.id.edit_workout_weight);
            exerciseWeight.setText(String.valueOf(currentExercise.getWeight()));

            TextView exerciseReps = (TextView) itemView.findViewById(R.id.edit_workout_reps);
            exerciseReps.setText(String.valueOf(currentExercise.getReps()));

            TextView exerciseRPE = (TextView) itemView.findViewById(R.id.edit_workout_rpe);
            exerciseRPE.setText(String.valueOf(currentExercise.getRPE()));

            return itemView;
        }

    }
}
