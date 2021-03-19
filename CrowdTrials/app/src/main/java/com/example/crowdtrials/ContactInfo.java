package com.example.crowdtrials;

import java.io.Serializable;

/**
 * This class holds the contact info of an user
 */
public class ContactInfo implements Serializable {
    String name="";
    String phoneNumber="";

    /**
     * This updates contact info of the user and sets the name attribute to the name entered by the user
     * @param name
     * This is the name of the user entered
     * @param phoneNumber
     * The phone number of the user entered
     */
    public ContactInfo(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    /**
     * This returns the name of the user
     * @return
     * The name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * This sets the name attribute to the name entered by the user
     * @param name
     * The name entered by the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This returns the phone number of the user
     * @return
     * The phone number of the user
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * This sets the phoneNumber attribute to the phone number entered by the user
     * @param phoneNumber
     * The phone number entered by the user
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
