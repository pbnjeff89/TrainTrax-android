package com.pbnjeff.wot;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.Toast;

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

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // exercise adder button
        Button addExercise = (Button) findViewById(R.id.exercise_list_add_name);
        // final EditText newExercise = (EditText) findViewById(R.id.exercise_list_name_edit);

        addExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addExerciseInputDialog();
            }


        });

        // register listview for context meu
        registerForContextMenu(expListView);

        // setting list adapter
        expListView.setAdapter(listAdapter);


    }

    protected void addExerciseInputDialog() {

        LayoutInflater inflater = LayoutInflater.from(TrackActivity.this);
        View promptView = inflater.inflate(R.layout.add_exercise_layout, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TrackActivity.this);
        alertDialogBuilder.setView(promptView);

        final EditText addExercise = (EditText) promptView.findViewById(R.id.add_exercise_input);

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        exerciseList.add(new Exercise(addExercise.getText().toString()));
                        listDataHeader.add(exerciseList.get(exerciseList.size() - 1).getName());
                        List<String> emptyList = new ArrayList<String>();
                        listDataChild.put(exerciseList.get(exerciseList.size() - 1).getName(), emptyList);
                        listAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
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
                addSetInputDialog(groupPosition, childPosition);
                break;
            case R.id.delete_exercise:
                deleteExercise(groupPosition);
                break;
            case R.id.exercise_cancel:
                break;
            case R.id.delete_set:
                deleteSet(groupPosition, childPosition);
                break;
            case R.id.edit_set:
                editSet(groupPosition, childPosition);
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void editSet(int groupPosition, int childPosition) {

    }

    private void deleteSet(int groupPosition, int childPosition) {
        List<String> originalSets = listDataChild.get(listDataHeader.get(groupPosition));
        List<String> newSets = originalSets;
        String removed = newSets.remove(childPosition);
        listDataChild.put(listDataHeader.get(groupPosition), newSets);
        exerciseList.get(groupPosition).deleteSet(childPosition);
        listAdapter.notifyDataSetChanged();
    }

    private void deleteExercise(int groupPosition) {
        listDataChild.remove(listDataHeader.get(groupPosition));
        listDataHeader.remove(groupPosition);
        exerciseList.remove(groupPosition);
        listAdapter.notifyDataSetChanged();
    }

    protected void addSetInputDialog(final int groupPosition, final int childPosition) {
        LayoutInflater inflater = LayoutInflater.from(TrackActivity.this);
        View promptView = inflater.inflate(R.layout.add_set_layout, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TrackActivity.this);
        alertDialogBuilder.setView(promptView);

        final EditText weight = (EditText) promptView.findViewById(R.id.setdialog_weight_edit);
        final EditText reps = (EditText) promptView.findViewById(R.id.setdialog_reps_edit);
        final EditText rpe = (EditText) promptView.findViewById(R.id.setdialog_rpe_edit);

        // create spinner for units
        final Spinner spinner = (Spinner) promptView.findViewById(R.id.units_spinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.units_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        Boolean whitespaceWeight = weight.getText().toString().trim().length() == 0;
                        Boolean whitespaceReps = reps.getText().toString().trim().length() == 0;
                        Boolean whitespaceRpe = rpe.getText().toString().trim().length() == 0;
                        Boolean invalidInput = whitespaceWeight ||
                                whitespaceReps || whitespaceRpe;
                        String units = spinner.getSelectedItem().toString();

                        if (invalidInput) {
                            Toast.makeText(TrackActivity.this, "Please input valid values.",
                                    Toast.LENGTH_LONG).show();
                            dialog.cancel();
                        } else {
                            // update exerciseList
                            exerciseList.get(groupPosition)
                                    .addSet(Float.valueOf(weight.getText().toString()),
                                            units, Integer.valueOf(reps.getText().toString()),
                                            Float.valueOf(rpe.getText().toString()));

                            String addString = weight.getText().toString() + " " + units + " x " +
                                    reps.getText().toString() + " @ " + rpe.getText().toString();

                            // update listDataHeader
                            List<String> newSets =
                                    listDataChild.get(listDataHeader.get(groupPosition));
                            newSets.add(addString);
                            Toast.makeText(TrackActivity.this, addString, Toast.LENGTH_SHORT).show();
                            listAdapter.notifyDataSetChanged();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

}
