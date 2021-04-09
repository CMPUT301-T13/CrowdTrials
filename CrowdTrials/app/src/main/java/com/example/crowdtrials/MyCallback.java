package com.example.crowdtrials;

import java.util.ArrayList;

/**
 * This interface represents a callback action
 */
interface MyCallback {

    /**
     * This method does a certain action on callback of a database request
     * @param value
     * The Experiment queried for in the database
     * @param whichCase
     * The integer representing the use case
     */
    void onCallback(ArrayList<Experiment> value,int whichCase);

}
