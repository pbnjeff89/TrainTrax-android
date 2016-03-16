package com.pbnjeff.wot;

import java.util.ArrayList;

/**
 * Created by Jeff on 2/21/2016.
 */
public class Exercise {

    private String name;
    private ArrayList<Float> weightLbs;
    private ArrayList<Integer> reps;
    private ArrayList<Float> rpe;

    public Exercise(String name) {
        this.name = name;
        this.weightLbs = new ArrayList<Float>();
        this.reps = new ArrayList<Integer>();
        this.rpe = new ArrayList<Float>();
    }

    public String getName() { return name; }

    public float getWeightLbs(int pos) { return this.weightLbs.get(pos); }

    public float getWeightKg(int pos) { return this.weightLbs.get(pos) / 2.20462f; }

    public int getReps(int pos) { return this.reps.get(pos); }

    public float getRpe(int pos) { return this.rpe.get(pos); }

    public int getSets() { return this.reps.size(); }

    public int addSet(float weight, String units, int reps, float rpe) {
        if(units == "lbs") {
            this.weightLbs.add(weight);
            this.reps.add(reps);
            this.rpe.add(rpe);
            return 1;
        }
        else if (units == "kg") {
            this.weightLbs.add(weight / 2.20462f);
            this.reps.add(reps);
            this.rpe.add(rpe);
            return 1;
        }
        else return -1;
    }

    public int deleteSet(int position) {
        this.weightLbs.remove(position);
        this.reps.remove(position);
        this.rpe.remove(rpe);
        return 1;
    }

    public void removeSet(int position) {
        this.weightLbs.remove(position);
        this.reps.remove(position);
        this.rpe.remove(position);
    }

}
