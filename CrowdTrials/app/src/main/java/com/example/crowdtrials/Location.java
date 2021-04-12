package com.example.crowdtrials;

import java.io.Serializable;

/**
 * This class represents the location object of an experiment
 */
public class Location implements Serializable {
    String name;

    /**
     * This is a constructor that initialises the location object
     * @param name
     * The name of the location
     */
    public Location(String name) {
        this.name = name;
    }
}
