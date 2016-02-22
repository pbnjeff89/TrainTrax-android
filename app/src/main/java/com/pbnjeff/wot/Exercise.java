package com.pbnjeff.wot;

/**
 * Created by Jeff on 2/21/2016.
 */
public class Exercise {
    private String name;
    private ExerciseHistory history;
    private int index;
    private double weight;
    private int reps;
    private double rpe;

    public Exercise(String name, double weight, int reps, double rpe) {
        super();
        this.name = name;
        this.history = new ExerciseHistory();
        this.weight = weight;
        this.reps = reps;
        this.rpe = rpe;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public int getReps() {
        return reps;
    }

    public double getRPE() {
        return rpe;
    }

}
