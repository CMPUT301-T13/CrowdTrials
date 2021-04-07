package com.example.crowdtrials;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Statistics {

    public float getBinomialMean(ArrayList<Boolean> outcomes){
        int sum =0;
        for(Boolean outcome:outcomes){
            if(outcome){
                sum+=1;
            }
        }
        return (float)sum/outcomes.size();
    }

    public float  getIntegerMean(ArrayList<Integer> values){
        int sum =0;
        for(Integer value:values){
                sum+=value;
            }
        return (float)sum/values.size();

    }
    public float  getfloatMean(ArrayList<Float> measurements){
        int sum =0;
        for(Float value:measurements){
            sum+=value;
        }
        return (float)sum/measurements.size();

    }

    public Boolean getMedian(ArrayList<Boolean> values){

        Collections.sort(values);
        if(values.size() % 2 == 0){
           return values.get(values.size()/2);
        }else{
            int size = values.size();
            int p = (int)(size/2);
            return values.get(p) || values.get(p+1);

        }
    }



}
