package com.example.crowdtrials;

public class FloatResult extends Result{
    public float measurement;

    public FloatResult(Experimenter experimenter, float measurement) {
        super(experimenter);
        this.measurement=measurement;
    }
}
