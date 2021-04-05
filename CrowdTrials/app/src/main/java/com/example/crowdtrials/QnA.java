package com.example.crowdtrials;

import java.util.ArrayList;

/**
 * This class represents the questions and answers of an experiment
 */
public class QnA {
    String question;
    ArrayList<String> answers=new ArrayList<>();

    public QnA(String question) {
        this.question = question;
    }
}
