package com.example.crowdtrials;

public class BoolResult extends Result{
    public boolean outcome;

    public BoolResult(Experimenter experimenter,boolean outcome) {
        super(experimenter);
        this.outcome=outcome;
    }
}
