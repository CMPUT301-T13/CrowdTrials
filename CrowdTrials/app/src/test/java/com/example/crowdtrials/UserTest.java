package com.example.crowdtrials;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private Date mockDate = new Date();

    private ContactInfo mockContact() {
        return new ContactInfo("Jill", "5871234567");
    }

    private Location mockLocation() {
        return new Location("Edmonton");
    }

    private CountExp mockExperiment() {
        CountExp experiment = new CountExp(mockUser, mockLocation(), "Testing if function works", mockDate, 10, "Test");
        return experiment;
    }

    private User mockUser = new User("Jill101", mockContact());

    @Test
    void testSubscribe() {
        ArrayList<Experiment> mockSubscribedTo = new ArrayList<>();
        assertEquals(0, mockSubscribedTo.size());
        mockSubscribedTo.add(mockExperiment());
        assertEquals(1, mockSubscribedTo.size());
        //assertTrue(mockSubscribedTo.contains(mockExperiment()));
    }

    @Test
    void testCreateExperiment() {
        ArrayList<CountExp> mockOwned = new ArrayList<>();
        assertEquals(0, mockOwned.size());
        mockOwned.add(mockExperiment());
        assertEquals(1, mockOwned.size());
        //assertTrue(mockOwned.contains(mockExperiment()));
    }

    @Test
    void testGetContactInfo() {
        ContactInfo newContact = new ContactInfo("Jake", "6049876543");
        User newUser = new User("Jake101", newContact);
        assertEquals(newContact, newUser.getContactInfo());
    }

    @Test
    void testSetContactInfo() {
        ContactInfo newContact = new ContactInfo("Mark", "5879876543");
        mockUser.setContactInfo(newContact);
        assertEquals(newContact, mockUser.getContactInfo());
        //User newUser = new User("Mark100", newContact());
    }

}
