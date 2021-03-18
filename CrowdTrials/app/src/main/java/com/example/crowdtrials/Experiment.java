package com.example.crowdtrials;

//import android.location.Location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public abstract class Experiment implements Serializable {
    User owner;
    ArrayList<User> experimenters = new ArrayList<>();
    String name;
    Location region;
    String description;
    Date date;
    ArrayList<ResultArr> results=new ArrayList<>();
    int minTrials;
    boolean published=false;
    boolean ended=false;
    HashSet<User> subscribers = new HashSet<>();
    QnA questionsAnswers;
    String type;
    boolean isGeoLocationEnabled;


    public Experiment(){
        this.date = new Date();

    }



    public Experiment(User owner, Location region, String description, Date date, int minTrials,String experimentName) {

        this.owner = owner;
        this.region = region;
        this.description = description;
        this.date = date;
        this.minTrials = minTrials;
        //this.type=type;

        this.name = experimentName;

    }


    public ArrayList<User> getExperimenters() {
        return experimenters;
    }



    public Location getRegion() {
        return region;
    }

    public void setRegion(Location region) {
        this.region = region;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getOwner() {
        return this.owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public ArrayList<ResultArr> getResults() {
        return this.results;
    }

    public void addResult(ResultArr results) {
        if(owner.username.equals(results.experimenter.username) || experimenters.contains(results.experimenter.username)){
            this.results.add(results);
        }

    }

    public boolean isPublished() {
        return this.published;
    }

    public boolean isEnded() {
        return this.ended;
    }



    public HashSet<User> getSubscribers() {
        return subscribers;
    }


    public QnA getQuestionsAnswers() {
        return questionsAnswers;
    }

    public void ignoreResultsFrom(User user, User experimenter){
        if(owner.username.equals(user.username)) {


            if (experimenters.contains(experimenter)) {
                for (int i = 0; i < results.size(); i++) {
                    if (results.get(i).experimenter == experimenter) {
                        results.remove(i);
                    }
                }
            }
        }

    }
    public void publishExperiment(User user){
        if(owner.username.equals(user.username)){
            this.published=true;
        }
    }
    public void depublishExperiment(User user){
        if(owner.username.equals(user.username)){
            this.published=false;
        }
    }
    public void endExperiment(User user){
        if(owner.username.equals(user.username)){
            this.ended=true;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
