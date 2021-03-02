package main.java.com.example.crowdtrials;

import java.util.ArrayList;

public abstract class User {
    private String username;
    private ContactInfo contactInfo;
    private ArrayList<Experiment> subscribed;
    public void editContact(ContactInfo newContact){
        this.contactInfo=newContact;
    }
    public User getProfile(String username){
        // return user object whos username matches
    }
    public void subscribe(Experiment e){
        subscribed.add(e);
    }
    public void reply(Experiment e, String answer, String question){

    }
    public void displayMap(Experiment e){

    }

}
