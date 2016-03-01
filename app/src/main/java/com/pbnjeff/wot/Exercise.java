package com.pbnjeff.wot;

/**
 * Created by Jeff on 2/21/2016.
 */
public class Exercise {
    private String name;
    private double weightLBS;
    private double weightKG;
    private int reps;
    private double rpe;


    public Exercise(String name, double weightLBS, int reps, double rpe) {
        super();
        this.name = name;
        this.weightLBS = weightLBS;
        this.weightKG = weightLBS / 2.20462;
        this.reps = reps;
        this.rpe = rpe;
    }

    public String getName() {
        return name;
    }

    public double getWeightLBS() {
        return weightLBS;
    }

    public double getWeightKG() { return weightKG; }

    public int getReps() {
        return reps;
    }

    public double getRPE() {
        return rpe;
    }

}
