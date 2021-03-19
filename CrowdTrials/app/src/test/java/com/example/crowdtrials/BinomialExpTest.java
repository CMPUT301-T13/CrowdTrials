package com.example.crowdtrials;

import androidx.annotation.WorkerThread;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class BinomialExpTest {

    private BinomialExp mockBinomialExp = new BinomialExp();
    private ContactInfo mockContact() {
        return new ContactInfo("James", "5871234567");
    }
    private User mockUser = new User("James99", mockContact());


    @Test
    void testGenResult() {
        if (Math.random() > (1 - mockBinomialExp.probability)) {
            assertEquals(true, mockBinomialExp.genResult());
        } else {
            assertEquals(false, mockBinomialExp.genResult());
        }
    }

    @Test
    void testGetRegion() {
        Location mockRegion = new Location("Antarctica");
        mockBinomialExp.setRegion(mockRegion);
        assertEquals(mockRegion, mockBinomialExp.getRegion());
    }

    @Test
    void testSetRegion() {
        Location mockRegion = new Location("Antarctica");
        mockBinomialExp.setRegion(mockRegion);
        assertEquals(mockRegion, mockBinomialExp.region);
    }

    @Test
    void testGetDescription() {
        String mockDescription = "This is a mock experiment description";
        mockBinomialExp.setDescription(mockDescription);
        assertEquals(mockDescription, mockBinomialExp.getDescription());
    }

    @Test
    void testSetDescription() {
        String mockDescription = "This is a mock experiment description";
        mockBinomialExp.setDescription(mockDescription);
        assertEquals(mockDescription, mockBinomialExp.description);
    }

    @Test
    void testGetDate() {
        Date mockDate = new Date();
        mockBinomialExp.setDate(mockDate);
        assertEquals(mockDate, mockBinomialExp.getDate());
    }

    @Test
    void testSetDate() {
        Date mockDate = new Date();
        mockBinomialExp.setDate(mockDate);
        assertEquals(mockDate, mockBinomialExp.date);
    }

    @Test
    void testGetOwner() {
        mockBinomialExp.setOwner(mockUser);
        assertEquals(mockUser, mockBinomialExp.getOwner());
    }

    @Test
    void testSetOwner() {
        mockBinomialExp.setOwner(mockUser);
        assertEquals(mockUser, mockBinomialExp.owner);
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
        mockBinomialExp.setName(mockExperimentName);
        assertEquals(mockExperimentName, mockBinomialExp.name);
    }

    @Test
    void testSetName() {
        String mockExperimentName = "Test Experiment";
        mockBinomialExp.setName(mockExperimentName);
        assertEquals(mockExperimentName, mockBinomialExp.getName());
    }

}
