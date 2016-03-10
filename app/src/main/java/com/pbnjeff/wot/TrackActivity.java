package com.pbnjeff.wot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
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

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

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
