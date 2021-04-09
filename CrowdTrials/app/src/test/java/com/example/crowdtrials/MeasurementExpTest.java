package com.example.crowdtrials;

import org.junit.Assert;
import org.junit.Test;
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
        Assert.assertEquals(mockRegion, mockMeasurementExp.getRegion());
    }

    @Test
    void testSetRegion() {
        Location mockRegion = new Location("Antarctica");
        mockMeasurementExp.setRegion(mockRegion);
        Assert.assertEquals(mockRegion, mockMeasurementExp.region);
    }

    @Test
    void testGetDescription() {
        String mockDescription = "This is a mock experiment description";
        mockMeasurementExp.setDescription(mockDescription);
        Assert.assertEquals(mockDescription, mockMeasurementExp.getDescription());
    }

    @Test
    void testSetDescription() {
        String mockDescription = "This is a mock experiment description";
        mockMeasurementExp.setDescription(mockDescription);
        Assert.assertEquals(mockDescription, mockMeasurementExp.description);
    }

    @Test
    void testGetDate() {
        Date mockDate = new Date();
        mockMeasurementExp.setDate(mockDate);
        Assert.assertEquals(mockDate, mockMeasurementExp.getDate());
    }

    @Test
    void testSetDate() {
        Date mockDate = new Date();
        mockMeasurementExp.setDate(mockDate);
        Assert.assertEquals(mockDate, mockMeasurementExp.date);
    }

    @Test
    void testGetOwner() {
        mockMeasurementExp.setOwner(mockUser);
        Assert.assertEquals(mockUser, mockMeasurementExp.getOwner());
    }

    @Test
    void testSetOwner() {
        mockMeasurementExp.setOwner(mockUser);
        Assert.assertEquals(mockUser, mockMeasurementExp.owner);
    }

    // ResultsArr is not yet complete
    @Test
    void testGetResults() {
        mockMeasurementExp.results.add(new FloatResult(mockUser));
        assert(mockMeasurementExp.getResults().equals(mockMeasurementExp.results));
    }

    @Test
    void testAddResult() {
        mockMeasurementExp.experimenters.add(mockUser);
        int n = mockMeasurementExp.results.size();
        mockMeasurementExp.addResult(new FloatResult(mockUser));
        assert(n<mockMeasurementExp.results.size());


    }

    @Test
    void testIsPublished() {
        mockMeasurementExp.published=true;
        assert(mockMeasurementExp.isPublished());
    }

    @Test
    void testIsEnded() {
        mockMeasurementExp.ended=true;
        assert(mockMeasurementExp.isEnded());
    }

    @Test
    void testGetSubscribers() {
        mockMeasurementExp.subscribers.add(mockUser);
        assert(mockMeasurementExp.subscribers.equals(mockMeasurementExp.getSubscribers()));
    }

    @Test
    void testGetQuestionsAnswers() {
        assert(mockMeasurementExp.qnalist.equals(mockMeasurementExp.getQuestionsAnswers()));
    }

    @Test
    void testIgnoreResultsFrom() {
        mockMeasurementExp.ignoredUsers.add(mockUser.username);
        mockMeasurementExp.results.add(new FloatResult(mockUser));
        int n=mockMeasurementExp.results.size();
        for(int i=0;i<mockMeasurementExp.results.size();i++){
            if(mockMeasurementExp.results.get(i).experimenter.username.equals(mockUser.username)){
                mockMeasurementExp.results.remove(i);
            }
        }
        assert(mockMeasurementExp.results.size()<n);
    }

    @Test
    void testPublishExperiment() {
        mockMeasurementExp.published=false;
        mockMeasurementExp.owner=mockUser;
        mockMeasurementExp.publishExperiment(mockUser);
        assert(mockMeasurementExp.isPublished());
    }

    @Test
    void testDepublishExperiment() {
        mockMeasurementExp.published=true;
        mockMeasurementExp.owner=mockUser;
        mockMeasurementExp.depublishExperiment(mockUser);
        assert(!mockMeasurementExp.isPublished());
    }

    @Test
    void testEndExperiment() {
        mockMeasurementExp.ended=true;
        mockMeasurementExp.endExperiment(mockUser);
        assert(mockMeasurementExp.isEnded());
    }


    @Test
    void testGetName() {
        String mockExperimentName = "Test Experiment";
        mockMeasurementExp.setName(mockExperimentName);
        Assert.assertEquals(mockExperimentName, mockMeasurementExp.name);
    }

    @Test
    void testSetName() {
        String mockExperimentName = "Test Experiment";
        mockMeasurementExp.setName(mockExperimentName);
        Assert.assertEquals(mockExperimentName, mockMeasurementExp.getName());
    }
}
