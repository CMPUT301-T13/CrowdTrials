package com.example.crowdtrials;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class User implements Serializable {
    public String username;
    public ContactInfo contactInfo;
    ArrayList<Experiment> subscribedTo = new ArrayList<>();
    public void subscribe(Experiment e){
        e.subscribers.add(this);
        this.subscribedTo.add(e);
    }
    public void reply(String s, Experiment e){
        // fill in later
    }

    public User(String username, ContactInfo contactInfo) {
        this.username = username;
        this.contactInfo = contactInfo;
    }
    
}
