package com.example.crowdtrials;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class CountExpTest {
    private CountExp mockCountExp = new CountExp();
    private ContactInfo mockContact() {
        return new ContactInfo("James", "5871234567");
    }
    private User mockUser = new User("James99", mockContact());

    @Test
    void testGetRegion() {
        Location mockRegion = new Location("Antarctica");
        mockCountExp.setRegion(mockRegion);
        assertEquals(mockRegion, mockCountExp.getRegion());
    }

    @Test
    void testSetRegion() {
        Location mockRegion = new Location("Antarctica");
        mockCountExp.setRegion(mockRegion);
        assertEquals(mockRegion, mockCountExp.region);
    }

    @Test
    void testGetDescription() {
        String mockDescription = "This is a mock experiment description";
        mockCountExp.setDescription(mockDescription);
        assertEquals(mockDescription, mockCountExp.getDescription());
    }

    @Test
    void testSetDescription() {
        String mockDescription = "This is a mock experiment description";
        mockCountExp.setDescription(mockDescription);
        assertEquals(mockDescription, mockCountExp.description);
    }

    @Test
    void testGetDate() {
        Date mockDate = new Date();
        mockCountExp.setDate(mockDate);
        assertEquals(mockDate, mockCountExp.getDate());
    }

    @Test
    void testSetDate() {
        Date mockDate = new Date();
        mockCountExp.setDate(mockDate);
        assertEquals(mockDate, mockCountExp.date);
    }

    @Test
    void testGetOwner() {
        mockCountExp.setOwner(mockUser);
        assertEquals(mockUser, mockCountExp.getOwner());
    }

    @Test
    void testSetOwner() {
        mockCountExp.setOwner(mockUser);
        assertEquals(mockUser, mockCountExp.owner);
    }

    // ResultsArr is not yet complete
    //@Test
    //void testGetResults() {

    //}

    //@Test
    //void testAddResult() {

    //}

    //@Test
    //void testIsPublished() {
    //}

    //@Test
    //void testIsEnded() {
    //}

    //@Test
    //void testGetSubscribers() {
    //}

    //@Test
    //void testGetQuestionsAnswers() {
    //}

    //@Test
    //void testIgnoreResultsFrom() {
    // }

    //@Test
    //void testPublishExperiment() {
    //}

    //@Test
    //void testDepublishExperiment() {
    //}

    //@Test
    //void testEndExperiment() {
    //}
    
    @Test
    void testGetName() {
        String mockExperimentName = "Test Experiment";
        mockCountExp.setName(mockExperimentName);
        assertEquals(mockExperimentName, mockCountExp.name);
    }

    @Test
    void testSetName() {
        String mockExperimentName = "Test Experiment";
        mockCountExp.setName(mockExperimentName);
        assertEquals(mockExperimentName, mockCountExp.getName());
    }
    
}
