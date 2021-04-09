package com.example.crowdtrials;

import java.util.ArrayList;

/**
 * This interface represent a callback action for a question
 */
public interface QuestionsCallback {

    /**
     * This method does a certain action on callback of a database request for a question
     * @param value
     * The question being queried for in the database
     * @param whichCase
     * The integer representing the use case
     */
    void onCallback(ArrayList<QnA> value, int whichCase);
}
