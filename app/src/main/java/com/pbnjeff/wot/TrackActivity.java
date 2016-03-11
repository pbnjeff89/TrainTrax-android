package com.pbnjeff.wot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TrackActivity extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    ArrayList<Exercise> exerciseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        exerciseList = new ArrayList<Exercise>();
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        prepareListData();

        // BIG ISSUE: if you try to open up an exercise without a set in it
        // it wilL CRASH THE FUCK OUT OF THE APP

        // for now, when you add, please add

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // exercise adder button
        Button addExercise = (Button) findViewById(R.id.exercise_list_add_name);
        final EditText newExercise = (EditText) findViewById(R.id.exercise_list_name_edit);

        addExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exerciseList.add(new Exercise(newExercise.getText().toString()));
                listDataHeader.add(exerciseList.get(exerciseList.size() - 1).getName());
                List<String> emptyList = new ArrayList<String>();
                emptyList.add("empty");
                listDataChild.put(exerciseList.get(exerciseList.size() - 1).getName(), emptyList);
                listAdapter.notifyDataSetChanged();
            }
        });

        // register listview for context meu
        registerForContextMenu(expListView);

        // setting list adapter
        expListView.setAdapter(listAdapter);


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        ExpandableListView.ExpandableListContextMenuInfo info =
                (ExpandableListView.ExpandableListContextMenuInfo) menuInfo;

        int type =
                ExpandableListView.getPackedPositionType(info.packedPosition);

        if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.exercise_context_menu, menu);
        }

        if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.set_context_menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        ExpandableListView.ExpandableListContextMenuInfo info =
                (ExpandableListView.ExpandableListContextMenuInfo) item.getMenuInfo();

        int childPosition = expListView.getPackedPositionChild(info.packedPosition);
        int groupPosition = expListView.getPackedPositionGroup(info.packedPosition);

        switch (item.getItemId()) {
            case R.id.add_set:
                addSet(groupPosition);
                break;
            case R.id.delete_exercise:
                /*Toast toast = new Toast(this);
                toast.makeText(this, "deleted" + String.valueOf(groupPosition),
                        Toast.LENGTH_LONG).show();*/
                deleteExercise(groupPosition);
                break;
            case R.id.exercise_cancel:
                break;
            case R.id.edit_set:
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void deleteExercise(int groupPosition) {
        listDataChild.remove(listDataHeader.get(groupPosition));
        listDataHeader.remove(groupPosition);
        exerciseList.remove(groupPosition);
        listAdapter.notifyDataSetChanged();
    }

    private void addSet(int groupPosition) {
        exerciseList.get(groupPosition).addSet(123.4f, "lbs", 1, 8.8f);
        List<String> newSet = new ArrayList<String>();

        for(int i = 0; i < exerciseList.get(groupPosition).getSets(); i++) {
            newSet.add(String.valueOf(exerciseList.get(groupPosition).getWeightLbs(i)) + " lbs x " +
                    String.valueOf(exerciseList.get(groupPosition).getReps(i)) + " @ " +
                    String.valueOf(exerciseList.get(groupPosition).getRpe(i)));
        }

        listDataChild.put(exerciseList.get(groupPosition).getName(), newSet);
        listAdapter.notifyDataSetChanged();
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {

        exerciseList.add(new Exercise("Squat"));
        exerciseList.add(new Exercise("Bench"));
        exerciseList.add(new Exercise("Deadlift"));

        exerciseList.get(0).addSet(123.4f, "lbs", 3, 8.0f);
        exerciseList.get(0).addSet(123.4f, "lbs", 3, 8.0f);
        exerciseList.get(0).addSet(123.4f, "lbs", 3, 8.0f);
        exerciseList.get(0).addSet(123.4f, "lbs", 3, 8.0f);

        exerciseList.get(1).addSet(123.4f, "lbs", 3, 8.0f);
        exerciseList.get(1).addSet(123.4f, "lbs", 3, 8.0f);
        exerciseList.get(1).addSet(123.4f, "lbs", 3, 8.0f);

        exerciseList.get(2).addSet(123.4f, "lbs", 3, 8.0f);
        exerciseList.get(2).addSet(123.4f, "lbs", 3, 8.0f);
        exerciseList.get(2).addSet(123.4f, "lbs", 3, 8.0f);
        exerciseList.get(2).addSet(123.4f, "lbs", 3, 8.0f);
        exerciseList.get(2).addSet(123.4f, "lbs", 3, 8.0f);
        exerciseList.get(2).addSet(123.4f, "lbs", 3, 8.0f);

        // Adding child data
        listDataHeader.add(exerciseList.get(0).getName());
        listDataHeader.add(exerciseList.get(1).getName());
        listDataHeader.add(exerciseList.get(2).getName());

        // Adding child data
        List<String> squat = new ArrayList<String>();
        for(int i = 0; i < exerciseList.get(0).getSets(); i++) {
            squat.add(String.valueOf(exerciseList.get(0).getWeightLbs(i)) + " lbs x " +
                        String.valueOf(exerciseList.get(0).getReps(i)) + " @ " +
                        String.valueOf(exerciseList.get(0).getRpe(i)));
        }

        List<String> bench = new ArrayList<String>();
        for(int i = 0; i < exerciseList.get(1).getSets(); i++) {
            bench.add(String.valueOf(exerciseList.get(1).getWeightLbs(i)) + " lbs x " +
                    String.valueOf(exerciseList.get(1).getReps(i)) + " @ " +
                    String.valueOf(exerciseList.get(1).getRpe(i)));
        }

        List<String> deadlift = new ArrayList<String>();
        for(int i = 0; i < exerciseList.get(2).getSets(); i++) {
            deadlift.add(String.valueOf(exerciseList.get(2).getWeightLbs(i)) + " lbs x " +
                    String.valueOf(exerciseList.get(2).getReps(i)) + " @ " +
                    String.valueOf(exerciseList.get(2).getRpe(i)));
        }

        listDataChild.put(listDataHeader.get(0), squat); // Header, Child data
        listDataChild.put(listDataHeader.get(1), bench);
        listDataChild.put(listDataHeader.get(2), deadlift);
    }
}
