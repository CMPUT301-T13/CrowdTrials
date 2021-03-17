package com.example.crowdtrials;

import android.location.Location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public  class User implements Serializable {
    public String username;
    public ContactInfo contactInfo;
    ArrayList<Experiment> owned = new ArrayList<>();
    ArrayList<Experiment> subscribedTo = new ArrayList<>();
    public void subscribe(Experiment e){
        e.subscribers.add(this);
        this.subscribedTo.add(e);
    }


    public User(String username, ContactInfo contactInfo) {
        this.username = username;
        this.contactInfo = contactInfo;
    }
    public void createExperiment(String type,String name, Location region, String description, Date date, int minTrials){
        if (type.equalsIgnoreCase("CountExp")){
            owned.add(new CountExp(this,name,region,description,date,minTrials));
        }
        if(type.equalsIgnoreCase("BinomialExp")){
            owned.add(new BinomialExp(this,name,region,description,date,minTrials));
        }
        if(type.equalsIgnoreCase("NonNegativeCountExp")){
            owned.add(new NonNegativeCountExp(this,name,region,description,date,minTrials));
        }
        if(type.equalsIgnoreCase("MeasurementExp")){
            owned.add(new MeasurementExp(this,name,region,description,date,minTrials));
        }

    }
}
