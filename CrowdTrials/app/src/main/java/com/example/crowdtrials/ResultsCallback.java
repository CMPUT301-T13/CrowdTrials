package com.example.crowdtrials;

import java.util.ArrayList;

/**
 * This interface represent a callback action for results
 *
 */

public interface ResultsCallback {

    /**
     * This method does a certain action on callback of a database request for results
     * @param value
     * The array list of results
     * @param whichCase
     * The integer associated to the use case
     */
    void onCallback(ArrayList<ResultArr> value,Experiment exp, int whichCase);
}
