package com.example.crowdtrials;

public class Experimenter extends User {

    public Experimenter(String username, ContactInfo contactInfo) {
        super(username, contactInfo);
    }
    public void addToResults(Experiment e, Result result){

        if(e.subscribers.contains(this)){
            e.results.add(result);
        }
    }
}
