package com.example.crowdtrials;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents the questions and answers of an experiment
 */
public class QnA implements Serializable {
    public String question;
    public ArrayList<String> answers=new ArrayList<>();

    /**
     * This method is the constructor for a QnA object
     * @param question
     * This is the question string.
     */
    public QnA(String question) {
        this.question = question;
    }
}
