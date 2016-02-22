package com.pbnjeff.wot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void trackWorkout(View view) {
        Intent intent = new Intent(this, TrackActivity.class);
        startActivity(intent);
    }

    public void editWorkout(View view) {
        Intent intent = new Intent(this, EditWorkoutActivity.class);
        startActivity(intent);
    }

    public void openSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
