package com.example.crowdtrials;

public class FloatResult extends ResultArr{
    public float measurement;

    public FloatResult(User experimenter, float measurement) {
        super(experimenter);
        this.measurement=measurement;
    }
}
