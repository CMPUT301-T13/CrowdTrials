package com.example.crowdtrials;

import android.location.Location;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public abstract class Experiment {
    Owner owner;
    ArrayList<Experimenter> experimenters = new ArrayList<>();
    Location region;
    String description;
    Date date;
    ArrayList<Result> results;
    int minTrials;
    boolean published;
    boolean ended;
    HashSet<User> subscribers = new HashSet<>();
    QnA questionsAnswers;

    public Experiment(Owner owner, Location region, String description, Date date, int minTrials) {
        this.owner = owner;
        this.region = region;
        this.description = description;
        this.date = date;
        this.minTrials = minTrials;
    }
    public void setPublished(boolean status){
        this.published=status;
    }

    public ArrayList<Experimenter> getExperimenters() {
        return experimenters;
    }

    public void setExperimenters(ArrayList<Experimenter> experimenters) {
        this.experimenters = experimenters;
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

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public ArrayList<Result> getResults() {
        return results;
    }

    public void setResults(ArrayList<Result> results) {
        this.results = results;
    }

    public boolean isPublished() {
        return published;
    }

    public boolean isEnded() {
        return ended;
    }

    public void setEnded(boolean status) {
        this.ended = status;
    }

    public HashSet<User> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(HashSet<User> subscribers) {
        this.subscribers = subscribers;
    }

    public QnA getQuestionsAnswers() {
        return questionsAnswers;
    }

}
