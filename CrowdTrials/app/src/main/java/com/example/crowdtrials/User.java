package com.example.crowdtrials;

//import android.location.Location;

import com.google.firebase.firestore.CollectionReference;

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


    public User(String username) {
        this.username = username;
        //this.contactInfo = contactInfo;
    }
    public User(String username, ContactInfo contactInfo) {
        this.username = username;
        this.contactInfo = contactInfo;
        //this.contactInfo = contactInfo;
    }


    public void createExperiment(String type, Location region, String description, Date date, int minTrials,String experimentName){
        if (type.equalsIgnoreCase("CountExp")){
            owned.add(new CountExp(this,region,description,date,minTrials,experimentName));
        }
        if(type.equalsIgnoreCase("BinomialExp")){
            owned.add(new BinomialExp(this,region,description,date,minTrials,experimentName));
        }
        if(type.equalsIgnoreCase("NonNegativeCountExp")){
            owned.add(new NonNegativeCountExp(this,region,description,date,minTrials,experimentName));
        }
        if(type.equalsIgnoreCase("MeasurementExp")){
            owned.add(new MeasurementExp(this,region,description,date,minTrials,experimentName));


        }

    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }
}
