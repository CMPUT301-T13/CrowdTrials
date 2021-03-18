package com.example.crowdtrials;

//import android.location.Location;

import java.io.Serializable;
import java.util.Date;

public class MeasurementExp extends Experiment implements Serializable {
   // String type="MeasurementExp";
    public MeasurementExp(){
        this.type = "MeasurementExp";
    }


    public MeasurementExp(User owner, Location region, String description, Date date, int minTrials,String experimentName) {
        super(owner, region, description, date, minTrials,experimentName);


        this.type = "MeasurementExp";

    }
}
