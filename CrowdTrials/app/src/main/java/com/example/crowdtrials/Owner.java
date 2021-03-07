package com.example.crowdtrials;

import android.location.Location;

import java.util.ArrayList;
import java.util.Date;

public class Owner extends User{
    ArrayList<Experiment> owned = new ArrayList<>();

    public Owner(String username, ContactInfo contactInfo) {
        super(username, contactInfo);
    }


    public void publishExperiment(Experiment e){
        if(owned.contains(e)){
            e.setPublished(true);
        }
    }
    public void depublishExperiment(Experiment e){
        if(owned.contains(e)){
            e.setPublished(false);
        }
    }
    public void endExperiment(Experiment e){
        if(owned.contains(e)){
            e.setEnded(true);
        }
    }
    public void ignoreResultsFrom(Experiment e, Experimenter experimenter){
        if(e.experimenters.contains(experimenter)){
            for(int i=0;i<e.results.size();i++){
                if (e.results.get(i).experimenter==experimenter){
                    e.results.remove(i);
                }
            }
        }
    }
    public void createExperiment(String type,Location region, String description, Date date, int minTrials){
        if (type.equalsIgnoreCase("CountExp")){
            owned.add(new CountExp(this,region,description,date,minTrials));
        }
        if(type.equalsIgnoreCase("BinomialExp")){
            owned.add(new BinomialExp(this,region,description,date,minTrials));
        }
        if(type.equalsIgnoreCase("NonNegativeCountExp")){
            owned.add(new NonNegativeCountExp(this,region,description,date,minTrials));
        }
        if(type.equalsIgnoreCase("MeasurementExp")){
            owned.add(new MeasurementExp(this,region,description,date,minTrials));
        }

    }

}
