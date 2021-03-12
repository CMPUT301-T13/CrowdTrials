package com.example.crowdtrials;

public class BoolResult extends ResultArr{
    public boolean outcome;

    public BoolResult(User experimenter,boolean outcome) {
        super(experimenter);
        this.outcome=outcome;
    }
}
