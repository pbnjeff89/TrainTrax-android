package com.pbnjeff.wot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TrackActivity extends AppCompatActivity {

    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    ArrayList<Exercise> exerciseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);

        exerciseList = new ArrayList<Exercise>();
        // IT DOESN'T WORK UNLESS THERE IS SOMETHING ALREADY HERE, WHAT??

        prepareExerciseData();

        /*ExerciseListAdapter adapter = new ExerciseListAdapter(exerciseList, this);
        ListView list = (ListView) findViewById(R.id.exercise_list);
        list.setAdapter(adapter);*/

        expandableListView = (ExpandableListView) findViewById(R.id.exercise_list);
        prepareListData();
        expandableListAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        expandableListView.setAdapter(expandableListAdapter);
    }

    private void prepareExerciseData() {
        exerciseList.add(new Exercise("Squat"));
        exerciseList.add(new Exercise("Bench Press"));
        exerciseList.add(new Exercise("Deadlift"));
        populateData(0);
        populateData(1);
        populateData(2);

    }

    private void populateData(int i) {
        exerciseList.get(i).addSet(123.4f, "lbs", 3, 8.0f);
        exerciseList.get(i).addSet(123.4f, "lbs", 3, 8.0f);
        exerciseList.get(i).addSet(123.4f, "lbs", 3, 8.0f);
        exerciseList.get(i).addSet(123.4f, "lbs", 3, 8.0f);
        exerciseList.get(i).addSet(123.4f, "lbs", 3, 8.0f);
        exerciseList.get(i).addSet(123.4f, "lbs", 3, 8.0f);
        exerciseList.get(i).addSet(123.4f, "lbs", 3, 8.0f);
        exerciseList.get(i).addSet(123.4f, "lbs", 3, 8.0f);
        exerciseList.get(i).addSet(123.4f, "lbs", 3, 8.0f);
        exerciseList.get(i).addSet(123.4f, "lbs", 3, 8.0f);
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        //headers
        listDataHeader.add(exerciseList.get(0).getName());
        listDataHeader.add(exerciseList.get(1).getName());
        listDataHeader.add(exerciseList.get(2).getName());

        //children data
        List<String> childSquat = new ArrayList<String>();
        childSquat.add("1");
        childSquat.add("2");
        childSquat.add("3");

        List<String> childBench = new ArrayList<String>();
        childBench.add("1");
        childBench.add("2");
        childBench.add("3");

        List<String> childDead = new ArrayList<String>();
        childDead.add("1");
        childDead.add("2");
        childDead.add("3");

        listDataChild.put(listDataHeader.get(0), childSquat);
        listDataChild.put(listDataHeader.get(1), childBench);
        listDataChild.put(listDataHeader.get(2), childDead);

        /*for(int i = 0; i < exerciseList.size(); i++) {
            child = new ArrayList<String>();
            for(int j = 0; j < exerciseList.get(i).getSets(); j++) {
                child.add(String.valueOf(exerciseList.get(i).getWeightLbs(j)) +
                            " lbs x " + String.valueOf(exerciseList.get(i).getReps(j)) +
                            " @ " + String.valueOf(exerciseList.get(i).getRpe(j)));
            }
            listDataChild.put(listDataHeader.get(i), child);
        }*/

    }

    /*private class ExerciseListAdapter extends BaseAdapter implements ListAdapter {

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

    }*/
}
