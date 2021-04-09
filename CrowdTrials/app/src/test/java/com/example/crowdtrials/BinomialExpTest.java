package com.example.crowdtrials;

import androidx.annotation.WorkerThread;

import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
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
    @Test
    void testGetResults() {
        mockBinomialExp.results.add(new BoolResult(mockUser));
        assert(mockBinomialExp.getResults().equals(mockBinomialExp.results));
    }

    @Test
    void testAddResult() {
        mockBinomialExp.experimenters.add(mockUser);
        int n = mockBinomialExp.results.size();
        mockBinomialExp.addResult(new BoolResult(mockUser));
        assert(n<mockBinomialExp.results.size());


    }

    @Test
    void testIsPublished() {
        mockBinomialExp.published=true;
        assert(mockBinomialExp.isPublished());
    }

    @Test
    void testIsEnded() {
        mockBinomialExp.ended=true;
        assert(mockBinomialExp.isEnded());
    }

    @Test
    void testGetSubscribers() {
        mockBinomialExp.subscribers.add(mockUser);
        assert(mockBinomialExp.subscribers.equals(mockBinomialExp.getSubscribers()));
    }

    @Test
    void testGetQuestionsAnswers() {
        assert(mockBinomialExp.qnalist.equals(mockBinomialExp.getQuestionsAnswers()));
    }

    @Test
    void testIgnoreResultsFrom() {
        mockBinomialExp.ignoredUsers.add(mockUser.username);
        mockBinomialExp.results.add(new BoolResult(mockUser));
        int n=mockBinomialExp.results.size();
        for(int i=0;i<mockBinomialExp.results.size();i++){
            if(mockBinomialExp.results.get(i).experimenter.username.equals(mockUser.username)){
                mockBinomialExp.results.remove(i);
            }
        }
        assert(mockBinomialExp.results.size()<n);
   }

    @Test
    void testPublishExperiment() {
        mockBinomialExp.published=false;
        mockBinomialExp.owner=mockUser;
        mockBinomialExp.publishExperiment(mockUser);
        assert(mockBinomialExp.isPublished());
    }

    @Test
    void testDepublishExperiment() {
        mockBinomialExp.published=true;
        mockBinomialExp.owner=mockUser;
        mockBinomialExp.depublishExperiment(mockUser);
        assert(!mockBinomialExp.isPublished());
    }

    @Test
    void testEndExperiment() {
        mockBinomialExp.ended=true;
        mockBinomialExp.endExperiment(mockUser);
        assert(mockBinomialExp.isEnded());
    }


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
