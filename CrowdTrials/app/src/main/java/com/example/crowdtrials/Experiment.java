package main.java.com.example.crowdtrials;

import java.io.Serializable;
import java.util.ArrayList;

/*This class is our program's representation of an experiment object.
It contains user assigned inputs for the details of the experiment and contains results
of the experiment. It uses private attributes as we do not want any unethical changes to data
and we want a secure environment.
 */
public abstract class Experiment implements Serializable {
    Owner owner;
    ArrayList<Experimenter> experimenters;

}