package com.pbnjeff.wot;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class TrackActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);

        ArrayList<Exercise> exerciseList = new ArrayList<Exercise>();
        // IT DOESN'T WORK UNLESS THERE IS SOMETHING ALREADY HERE, WHAT??
        exerciseList.add(new Exercise(""));

        ExerciseListAdapter adapter = new ExerciseListAdapter(exerciseList, this);
        ListView list = (ListView) findViewById(R.id.exercise_list);
        list.setAdapter(adapter);
    }

    private class ExerciseListAdapter extends BaseAdapter implements ListAdapter {

        private ArrayList<Exercise> exerciseList = new ArrayList<Exercise>();
        private Context context;

        public ExerciseListAdapter(ArrayList<Exercise> exerciseList, Context context) {
            this.exerciseList = exerciseList;
            this.context = context;
        }

        @Override
        public int getCount() {
            return exerciseList.size();
        }

        @Override
        public Object getItem(int position) {
            return exerciseList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View itemView = convertView;

            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.exercise_view, parent, false);
            }

            Button buttonAdd = (Button) findViewById(R.id.exercise_list_add_name);
            Button buttonDelete = (Button) itemView.findViewById(R.id.exercise_delete);
            final EditText exerciseEdit = (EditText) findViewById(R.id.exercise_list_name_edit);

            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    exerciseList.remove(position);
                    notifyDataSetChanged();
                }
            });

            buttonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    exerciseList.add(new Exercise(exerciseEdit.getText().toString()));
                    exerciseEdit.setText("");
                    notifyDataSetChanged();
                }
            });

            TextView exerciseName = (TextView) itemView.findViewById(R.id.list_exercise_name);
            exerciseName.setText(exerciseList.get(position).getName());

            return itemView;
        }

    }
}
