package com.example.crowdtrials;

import java.util.ArrayList;

public abstract class ResultArr {
    public User experimenter;
    ArrayList<Double> values = new ArrayList<>();
    public User getExperimenter() {
        return experimenter;
    }

    public ResultArr(User experimenter) {
        this.experimenter = experimenter;
    }

}
