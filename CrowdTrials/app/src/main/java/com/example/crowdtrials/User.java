package com.example.crowdtrials;

//import android.location.Location;

import com.google.firebase.firestore.CollectionReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class represents an user object.
 */

public  class User implements Serializable {
    public static User staticInstanceOfUser;
    public String username;
    public ContactInfo contactInfo;
    ArrayList<Experiment> owned = new ArrayList<>();
    ArrayList<Experiment> subscribedTo = new ArrayList<>();

    /**
     * This method enables the user to subscribe to a certain experiment
     * @param e
     * The experiment that the user is subscribing to.
     */
    public void subscribe(Experiment e){
        e.subscribers.add(this);
        this.subscribedTo.add(e);
    }


    /**
     * The constructor for a user object
     * @param username
     * The username of the user
     */
    public User(String username) {
        this.username = username;
        //this.contactInfo = contactInfo;
    }

    /**
     * The second constructor for a user object
     * @param username
     * The username of the user
     * @param contactInfo
     * The contactInfo of the user
     */
    public User(String username, ContactInfo contactInfo) {
        this.username = username;
        this.contactInfo = contactInfo;
    }

    /**
     * This method is used to create a new experiment
     * @param type
     * This is the type of the experiment
     * @param region
     * This is the region in which the experiment will take place
     * @param description
     * This is the description of the experiment
     * @param date
     * This is the published date of the experiment
     * @param minTrials
     * This is the minimum trials required for an experiment
     * @param experimentName
     * This is the name of the experiment
     */
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

    /**
     * This returns the contact info of the user
     * @return
     * This is the contact Info of the user
     */
    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    /**
     * This method sets the contact Info of the user
     * @param contactInfo
     * This is the given contact Info of the user
     */
    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }
}
