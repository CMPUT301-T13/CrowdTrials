package com.example.crowdtrials;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NonNegativeCountExpTest {

    private NonNegativeCountExp mockNonNegativeCountExp = new NonNegativeCountExp();
    private ContactInfo mockContact() {
        return new ContactInfo("James", "5871234567");
    }
    private User mockUser = new User("James99", mockContact());

    @Test
    void testGetRegion() {
        Location mockRegion = new Location("Antarctica");
        mockNonNegativeCountExp.setRegion(mockRegion);
        assertEquals(mockRegion, mockNonNegativeCountExp.getRegion());
    }

    @Test
    void testSetRegion() {
        Location mockRegion = new Location("Antarctica");
        mockNonNegativeCountExp.setRegion(mockRegion);
        assertEquals(mockRegion, mockNonNegativeCountExp.region);
    }

    @Test
    void testGetDescription() {
        String mockDescription = "This is a mock experiment description";
        mockNonNegativeCountExp.setDescription(mockDescription);
        assertEquals(mockDescription, mockNonNegativeCountExp.getDescription());
    }

    @Test
    void testSetDescription() {
        String mockDescription = "This is a mock experiment description";
        mockNonNegativeCountExp.setDescription(mockDescription);
        assertEquals(mockDescription, mockNonNegativeCountExp.description);
    }

    @Test
    void testGetDate() {
        Date mockDate = new Date();
        mockNonNegativeCountExp.setDate(mockDate);
        assertEquals(mockDate, mockNonNegativeCountExp.getDate());
    }

    @Test
    void testSetDate() {
        Date mockDate = new Date();
        mockNonNegativeCountExp.setDate(mockDate);
        assertEquals(mockDate, mockNonNegativeCountExp.date);
    }

    @Test
    void testGetOwner() {
        mockNonNegativeCountExp.setOwner(mockUser);
        assertEquals(mockUser, mockNonNegativeCountExp.getOwner());
    }

    @Test
    void testSetOwner() {
        mockNonNegativeCountExp.setOwner(mockUser);
        assertEquals(mockUser, mockNonNegativeCountExp.owner);
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
        mockNonNegativeCountExp.setName(mockExperimentName);
        assertEquals(mockExperimentName, mockNonNegativeCountExp.name);
    }

    @Test
    void testSetName() {
        String mockExperimentName = "Test Experiment";
        mockNonNegativeCountExp.setName(mockExperimentName);
        assertEquals(mockExperimentName, mockNonNegativeCountExp.getName());
    }
}
