package com.example.crowdtrials;

/**
 * This interface represents a callback action for a user
 *
 */
public interface UserCallback {

    /**
     * This method does a certain action on callback of a database request of a user
     * @param user
     * The user being queried for
     */
    void userCallback(User user) ;
}
