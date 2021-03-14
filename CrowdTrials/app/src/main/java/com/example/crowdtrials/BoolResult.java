package com.example.crowdtrials;

import java.util.ArrayList;

public class BoolResult extends ResultArr{
    public ArrayList<Boolean> outcomes = new ArrayList<>();

    public BoolResult(User experimenter) {
        super(experimenter);

    }
}
