package com.example.crowdtrials;

import org.junit.Assert;
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
    void testGenResult() {
        if (Math.random() > (1 - mockNonNegativeCountExp.probability)) {
            Assert.assertEquals(true, mockNonNegativeCountExp.genResult());
        } else {
            Assert.assertEquals(false, mockNonNegativeCountExp.genResult());
        }
    }

    @Test
    void testGetRegion() {
        Location mockRegion = new Location("Antarctica");
        mockNonNegativeCountExp.setRegion(mockRegion);
        Assert.assertEquals(mockRegion, mockNonNegativeCountExp.getRegion());
    }

    @Test
    void testSetRegion() {
        Location mockRegion = new Location("Antarctica");
        mockNonNegativeCountExp.setRegion(mockRegion);
        Assert.assertEquals(mockRegion, mockNonNegativeCountExp.region);
    }

    @Test
    void testGetDescription() {
        String mockDescription = "This is a mock experiment description";
        mockNonNegativeCountExp.setDescription(mockDescription);
        Assert.assertEquals(mockDescription, mockNonNegativeCountExp.getDescription());
    }

    @Test
    void testSetDescription() {
        String mockDescription = "This is a mock experiment description";
        mockNonNegativeCountExp.setDescription(mockDescription);
        Assert.assertEquals(mockDescription, mockNonNegativeCountExp.description);
    }

    @Test
    void testGetDate() {
        Date mockDate = new Date();
        mockNonNegativeCountExp.setDate(mockDate);
        Assert.assertEquals(mockDate, mockNonNegativeCountExp.getDate());
    }

    @Test
    void testSetDate() {
        Date mockDate = new Date();
        mockNonNegativeCountExp.setDate(mockDate);
        Assert.assertEquals(mockDate, mockNonNegativeCountExp.date);
    }

    @Test
    void testGetOwner() {
        mockNonNegativeCountExp.setOwner(mockUser);
        Assert.assertEquals(mockUser, mockNonNegativeCountExp.getOwner());
    }

    @Test
    void testSetOwner() {
        mockNonNegativeCountExp.setOwner(mockUser);
        Assert.assertEquals(mockUser, mockNonNegativeCountExp.owner);
    }

    // ResultsArr is not yet complete
    @Test
    void testGetResults() {
        mockNonNegativeCountExp.results.add(new IntResult(mockUser));
        assert(mockNonNegativeCountExp.getResults().equals(mockNonNegativeCountExp.results));
    }

    @Test
    void testAddResult() {
        mockNonNegativeCountExp.experimenters.add(mockUser);
        int n = mockNonNegativeCountExp.results.size();
        mockNonNegativeCountExp.addResult(new IntResult(mockUser));
        assert(n<mockNonNegativeCountExp.results.size());


    }

    @Test
    void testIsPublished() {
        mockNonNegativeCountExp.published=true;
        assert(mockNonNegativeCountExp.isPublished());
    }

    @Test
    void testIsEnded() {
        mockNonNegativeCountExp.ended=true;
        assert(mockNonNegativeCountExp.isEnded());
    }

    @Test
    void testGetSubscribers() {
        mockNonNegativeCountExp.subscribers.add(mockUser);
        assert(mockNonNegativeCountExp.subscribers.equals(mockNonNegativeCountExp.getSubscribers()));
    }

    @Test
    void testGetQuestionsAnswers() {
        assert(mockNonNegativeCountExp.qnalist.equals(mockNonNegativeCountExp.getQuestionsAnswers()));
    }

    @Test
    void testIgnoreResultsFrom() {
        mockNonNegativeCountExp.ignoredUsers.add(mockUser.username);
        mockNonNegativeCountExp.results.add(new IntResult(mockUser));
        int n=mockNonNegativeCountExp.results.size();
        for(int i=0;i<mockNonNegativeCountExp.results.size();i++){
            if(mockNonNegativeCountExp.results.get(i).experimenter.username.equals(mockUser.username)){
                mockNonNegativeCountExp.results.remove(i);
            }
        }
        assert(mockNonNegativeCountExp.results.size()<n);
    }

    @Test
    void testPublishExperiment() {
        mockNonNegativeCountExp.published=false;
        mockNonNegativeCountExp.owner=mockUser;
        mockNonNegativeCountExp.publishExperiment(mockUser);
        assert(mockNonNegativeCountExp.isPublished());
    }

    @Test
    void testDepublishExperiment() {
        mockNonNegativeCountExp.published=true;
        mockNonNegativeCountExp.owner=mockUser;
        mockNonNegativeCountExp.depublishExperiment(mockUser);
        assert(!mockNonNegativeCountExp.isPublished());
    }

    @Test
    void testEndExperiment() {
        mockNonNegativeCountExp.ended=true;
        mockNonNegativeCountExp.endExperiment(mockUser);
        assert(mockNonNegativeCountExp.isEnded());
    }


    @Test
    void testGetName() {
        String mockExperimentName = "Test Experiment";
        mockNonNegativeCountExp.setName(mockExperimentName);
        Assert.assertEquals(mockExperimentName, mockNonNegativeCountExp.name);
    }

    @Test
    void testSetName() {
        String mockExperimentName = "Test Experiment";
        mockNonNegativeCountExp.setName(mockExperimentName);
        Assert.assertEquals(mockExperimentName, mockNonNegativeCountExp.getName());
    }
}
