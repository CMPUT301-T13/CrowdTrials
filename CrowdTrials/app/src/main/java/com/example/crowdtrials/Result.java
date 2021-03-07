package com.example.crowdtrials;

import java.util.ArrayList;

public abstract class Result {
    public Experimenter experimenter;
    ArrayList<Object> values = new ArrayList<>();
    public Experimenter getExperimenter() {
        return experimenter;
    }

    public Result(Experimenter experimenter) {
        this.experimenter = experimenter;
    }

}
