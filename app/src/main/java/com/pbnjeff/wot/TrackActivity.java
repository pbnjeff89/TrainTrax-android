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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TrackActivity extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    Spinner unitsDisplay;

    List<Exercise> exerciseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        exerciseList = new ArrayList<>();
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

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



        unitsDisplay = (Spinner) findViewById(R.id.track_spinner_units);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.units_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitsDisplay.setAdapter(spinnerAdapter);

        unitsDisplay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                convertUnits();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

                        String addExerciseString = addExercise.getText().toString();

                        exerciseList.add(new Exercise(addExercise.getText().toString()));
                        listDataHeader.add(addExerciseString);
                        listDataChild.put(addExerciseString, new ArrayList<String>());
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

        int childPosition = ExpandableListView.getPackedPositionChild(info.packedPosition);
        int groupPosition = ExpandableListView.getPackedPositionGroup(info.packedPosition);

        switch (item.getItemId()) {
            case R.id.add_set:
                addSetInputDialog(groupPosition);
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

    private void editSet(final int groupPosition, final int childPosition) {
        // set up units to display
        final String units = unitsDisplay.getSelectedItem().toString();

        // inflate a add_set_layout into dialog
        LayoutInflater inflater = LayoutInflater.from(TrackActivity.this);
        View promptView = inflater.inflate(R.layout.add_set_layout, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TrackActivity.this);
        alertDialogBuilder.setView(promptView);

        // set edit texts to the numbers used in a set
        final EditText weight = (EditText) promptView.findViewById(R.id.setdialog_weight_edit);
        final EditText reps = (EditText) promptView.findViewById(R.id.setdialog_reps_edit);
        final EditText rpe = (EditText) promptView.findViewById(R.id.setdialog_rpe_edit);

        // set up spinner
        final Spinner spinner = (Spinner) promptView.findViewById(R.id.units_spinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.units_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        // need to sync up expandable list units with match
        if(units.equals("lbs")) {
            weight.setText(String.valueOf(exerciseList.
                    get(groupPosition).getWeightLbs(childPosition)));
            spinner.setSelection(0);
        }
        else {
            weight.setText(String.valueOf(exerciseList.
                    get(groupPosition).getWeightKg(childPosition)));
            spinner.setSelection(1);
        }

        reps.setText(String.valueOf(exerciseList.
                get(groupPosition).getReps(childPosition)));
        rpe.setText(String.valueOf(exerciseList.
                get(groupPosition).getRpe(childPosition)));

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        Boolean whitespaceWeight = weight.getText().toString().trim().length() == 0;
                        Boolean whitespaceReps = reps.getText().toString().trim().length() == 0;
                        Boolean whitespaceRpe = rpe.getText().toString().trim().length() == 0;
                        Boolean invalidInput = whitespaceWeight ||
                                whitespaceReps || whitespaceRpe;

                        String unitsDialog = spinner.getSelectedItem().toString();

                        if (invalidInput) {
                            Toast.makeText(TrackActivity.this, "Please input valid values.",
                                    Toast.LENGTH_LONG).show();
                            dialog.cancel();
                        } else {
                            String weightString = weight.getText().toString();
                            if (unitsDialog.equals("kg")) {
                                weightString = String.valueOf(Float.valueOf(weightString) *
                                        2.20462f);
                            }
                            // update exerciseList
                            exerciseList.get(groupPosition)
                                    .replaceSet(childPosition,
                                            Float.valueOf(weightString),
                                            unitsDialog, Integer.valueOf(reps.getText().toString()),
                                            Float.valueOf(rpe.getText().toString()));

                            DecimalFormat df = new DecimalFormat("0.#");

                            String addWeightString;
                            if(units.equals("lbs")) addWeightString =
                                                    String.valueOf(df.format(exerciseList.get(groupPosition)
                                                                .getWeightLbs(childPosition)));
                            else addWeightString =
                                    String.valueOf(df.format(exerciseList.get(groupPosition)
                                            .getWeightKg(childPosition)));

                            String addString = addWeightString + " lbs x " +
                                    reps.getText().toString() + " @ " + rpe.getText().toString();

                            // update listDataHeader
                            List<String> newSets =
                                    listDataChild.get(listDataHeader.get(groupPosition));
                            newSets.add(addString);
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

    private void deleteSet(int groupPosition, int childPosition) {
        List<String> originalSets = listDataChild.get(listDataHeader.get(groupPosition));
        originalSets.remove(childPosition);
        exerciseList.get(groupPosition).deleteSet(childPosition);
        listAdapter.notifyDataSetChanged();
    }

    private void deleteExercise(int groupPosition) {
        listDataChild.remove(listDataHeader.get(groupPosition));
        listDataHeader.remove(groupPosition);
        exerciseList.remove(groupPosition);
        listAdapter.notifyDataSetChanged();
    }

    protected void addSetInputDialog(final int groupPosition) {
        LayoutInflater inflater = LayoutInflater.from(TrackActivity.this);
        View promptView = inflater.inflate(R.layout.add_set_layout, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TrackActivity.this);
        alertDialogBuilder.setView(promptView);

        final EditText weight = (EditText) promptView.findViewById(R.id.setdialog_weight_edit);
        final EditText reps = (EditText) promptView.findViewById(R.id.setdialog_reps_edit);
        final EditText rpe = (EditText) promptView.findViewById(R.id.setdialog_rpe_edit);

        // spinner for units
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

                            DecimalFormat df = new DecimalFormat("0.#");
                            String weightString;

                            int justAddedPos = exerciseList.get(groupPosition)
                                                .getSets() - 1;

                            if(units.equals("lbs"))
                                weightString = String.valueOf(df.format(exerciseList.get(groupPosition)
                                                .getWeightLbs(justAddedPos)));
                            else
                                weightString = String.valueOf(df.format(exerciseList.get(groupPosition)
                                        .getWeightKg(justAddedPos)));

                            String unitsToDisplay = unitsDisplay.getSelectedItem().toString();

                            String addString = weightString + " " + unitsToDisplay + " x " +
                                    reps.getText().toString() + " @ " + rpe.getText().toString();

                            // update listDataHeader
                            List<String> newSets =
                                    listDataChild.get(listDataHeader.get(groupPosition));
                            newSets.add(addString);
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

    private void convertUnits() {
        // find out what units were selected
        String unitsConvertTo = unitsDisplay.getSelectedItem().toString();

        for(int i = 0; i < exerciseList.size(); i++)
        {
            // String weight;
            Exercise editExercise = exerciseList.get(i);
            List<String> sets =
                    listDataChild.get(listDataHeader.get(i));
            for(int j = 0; j < exerciseList.get(i).getSets(); j++) {
                String reps = String.valueOf(editExercise.getReps(j));
                String rpe = String.valueOf(editExercise.getRpe(j));
                String weight;

                DecimalFormat df = new DecimalFormat("0.#");
                String weightString;

                if (unitsConvertTo.equals("lbs"))
                    weight = String.valueOf(df.format(editExercise.getWeightLbs(j))) + " lbs x ";
                else
                    weight = String.valueOf(df.format(editExercise.getWeightKg(j))) + " kg x ";

                String addString = weight + reps + " @ " + rpe;


                sets.remove(j);
                sets.add(j, addString);
            }
        }

        // notifyDataSetChanged
        listAdapter.notifyDataSetChanged();
    }
}
