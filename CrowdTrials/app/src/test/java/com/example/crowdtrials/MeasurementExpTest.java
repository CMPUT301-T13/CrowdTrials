package com.example.crowdtrials;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MeasurementExpTest {
    private MeasurementExp mockMeasurementExp = new MeasurementExp();
    private ContactInfo mockContact() {
        return new ContactInfo("James", "5871234567");
    }
    private User mockUser = new User("James99", mockContact());

    @Test
    void testGetRegion() {
        Location mockRegion = new Location("Antarctica");
        mockMeasurementExp.setRegion(mockRegion);
        assertEquals(mockRegion, mockMeasurementExp.getRegion());
    }

    @Test
    void testSetRegion() {
        Location mockRegion = new Location("Antarctica");
        mockMeasurementExp.setRegion(mockRegion);
        assertEquals(mockRegion, mockMeasurementExp.region);
    }

    @Test
    void testGetDescription() {
        String mockDescription = "This is a mock experiment description";
        mockMeasurementExp.setDescription(mockDescription);
        assertEquals(mockDescription, mockMeasurementExp.getDescription());
    }

    @Test
    void testSetDescription() {
        String mockDescription = "This is a mock experiment description";
        mockMeasurementExp.setDescription(mockDescription);
        assertEquals(mockDescription, mockMeasurementExp.description);
    }

    @Test
    void testGetDate() {
        Date mockDate = new Date();
        mockMeasurementExp.setDate(mockDate);
        assertEquals(mockDate, mockMeasurementExp.getDate());
    }

    @Test
    void testSetDate() {
        Date mockDate = new Date();
        mockMeasurementExp.setDate(mockDate);
        assertEquals(mockDate, mockMeasurementExp.date);
    }

    @Test
    void testGetOwner() {
        mockMeasurementExp.setOwner(mockUser);
        assertEquals(mockUser, mockMeasurementExp.getOwner());
    }

    @Test
    void testSetOwner() {
        mockMeasurementExp.setOwner(mockUser);
        assertEquals(mockUser, mockMeasurementExp.owner);
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
        mockMeasurementExp.setName(mockExperimentName);
        assertEquals(mockExperimentName, mockMeasurementExp.name);
    }

    @Test
    void testSetName() {
        String mockExperimentName = "Test Experiment";
        mockMeasurementExp.setName(mockExperimentName);
        assertEquals(mockExperimentName, mockMeasurementExp.getName());
    }

}
