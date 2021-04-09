package com.example.crowdtrials;

//import android.location.Location;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

/**
 * This abstract class represents an Experiment object
 */
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
    HashSet<String> ignoredUsers = new HashSet<>();
    HashSet<User> subscribers = new HashSet<>();
    //QnA questionsAnswers;
    ArrayList<QnA> qnalist = new ArrayList<>();
    String type;
    boolean isGeoLocationEnabled;


    /**
     * This is a default constructor for an experiment object
     */
    public Experiment(){
        this.date = new Date();

    }

    /**
     * This is a constructor initialises an experiment object
     * @param owner
     * The owner of the experiment
     * @param region
     * The region in which experiment takes place
     * @param description
     * The description of the experiment
     * @param date
     * The date when experiment is done
     * @param minTrials
     * The minimum number of trials to be done for the experiment
     * @param experimentName
     * The name of the experiment
     */
    public Experiment(User owner, Location region, String description, Date date, int minTrials,String experimentName) {

        this.owner = owner;
        this.region = region;
        this.description = description;
        this.date = date;
        this.minTrials = minTrials;
        //this.type=type;

        this.name = experimentName;

    }



    /**
     * This returns an array list of experimenters
     * @return
     * The array list of users who are experimenters
     */
    public ArrayList<User> getExperimenters() {
        return experimenters;
    }

    /**
     * This returns the region of the experiment
     * @return
     * The region of the experiment
     */
    public Location getRegion() {
        return region;
    }

    /**
     * This sets the region of the experiment
     * @param region
     * The region of the experiment
     */
    public void setRegion(Location region) {
        this.region = region;
    }

    /**
     * This returns the description of the experiment
     * @return
     * The description of the experiment
     */
    public String getDescription() {
        return description;
    }

    /**
     * This sets the description of the experiment
     * @param description
     * The description of the experiment
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This returns the date of the experiment
     * @return
     * The published date of the experiment
     */
    public Date getDate() {
        return date;
    }

    /**
     * This sets the date of the experiment
     * @param date
     * The date of the experiment
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * This returns the owner of the experiment
     * @return
     * The owner of the current experiment
     */
    public User getOwner() {
        return this.owner;
    }

    /**
     * This sets the owner of the experiment
     * @param owner
     * The given owner of the experiment
     */
    public void setOwner(User owner) {
        this.owner = owner;
    }

    /**
     * This returns the results of the experiment
     * @return
     * The results of the experiments
     */
    public ArrayList<ResultArr> getResults() {
        return this.results;
    }

    /**
     * This method adds results for the experiment
     * @param results
     * The results being entered for the experiment
     */
    public void addResult(ResultArr results) {

        if(owner.username.equals(results.experimenter.username) || this.experimenters.contains(results.experimenter)){
            Log.d("ADD RESULT","IVE BEEN RUN ");
            this.results.add(results);
        }

    }

    /**
     * This method returns true if the experiment is published and false if it is not published
     * @return
     * True if the experiment is published and False if it is not published
     */
    public boolean isPublished() {
        return this.published;
    }

    /**
     * This method returns true if the experiment has ended, false if it is still active
     * @return
     * True if the experiment has ended and False if it is active
     */
    public boolean isEnded() {
        return this.ended;
    }

    /**
     * This returns the hash set of subscribers to the current experiment
     * @return
     * The hash set of the subscribers to the current experiment
     */
    public HashSet<User> getSubscribers() {
        return subscribers;
    }

    /**
     * This returns the object representing questions and answers of the current experiment
     * @return
     * The object representing questions and answers of the current experiment.
     */
    public ArrayList<QnA> getQuestionsAnswers() {
        return qnalist;
    }

    /**
     * This method filters out results from a certain user.
     * @param user
     * The user who owns the experiment
     * @param experimenter
     * The experimenter whose results are being filtered out from the experiment
     */
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

    /**
     * This method sets the published status of the current experiment to true
     * @param user
     * The user who publishes the current experiment
     */
    public void publishExperiment(User user){
        if(owner.username.equals(user.username)){
            this.published=true;
        }
    }

    /**
     * This method sets the published status of the current experiment to false
     * @param user
     * The user who de-publishes the current experiment
     */
    public void depublishExperiment(User user){
        if(owner.username.equals(user.username)){
            this.published=false;
        }
    }

    /**
     * This methods allows the user to end their current experiment
     * @param user
     * The owner of the current experiment
     */
    public void endExperiment(User user){
        if(owner.username.equals(user.username)){
            this.ended=true;
        }
    }

    /**
     * This methods returns the name of the current experiment
     * @return
     * The name of the current experiment
     */
    public String getName() {
        return name;
    }

    /**
     * This method sets the name of the current experiment
     * @param name
     * The name being set to the current experiment
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
