package com.example.crowdtrials;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * This class models the statistics of an experiment
 */
public class Statistics {

    public void Statistics(){

    }

    /**
     * This method calculates the binomial mean of a binomial experiment
     * @param outcomes
     * The boolean outcomes of the experiment
     * @return
     * The binomial mean of the experiment
     */
    public float getBinomialMean(ArrayList<Boolean> outcomes){
        int sum =0;
        for(Boolean outcome:outcomes){
            if(outcome){
                sum+=1;
            }
        }
        return (float)sum/outcomes.size();
    }

    /**
     * This method calculates the integer mean of an experiment
     * @param values
     * The numerical outcomes of the experiment
     * @return
     * The integer mean of the experiment
     */
    public float  getIntegerMean(ArrayList<?> values){
        int sum =0;
        for(int i =0; i<values.size(); i++){

            //Log.e("GET INTEGER MEAN " , " " + values.get(i));
                Long temp = (Long)values.get(i);
                sum += temp.intValue();
            }
        return (float)sum/values.size();

    }
    /**
     * This method calculates the float mean of an experiment
     * @param measurements
     * The numerical outcomes of the experiment
     * @return
     * The float mean of the experiment
     */
    public float  getfloatMean(ArrayList<Float> measurements){
        int sum =0;
        for(Float value:measurements){
            sum+=value;
        }
        return (float)sum/measurements.size();

    }

    /**
     * This method calculates the boolean median of an experiment
     * @param values
     * The boolean outcomes of the experiment
     * @return
     * The boolean median of the experiment
     */
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

    /**
     * This method calculates the numerical median of an experiment
     * @param values
     * The numerical outcomes of the experiment
     * @return
     * The numerical median of the experiment
     */
    public float getIntegerMedian(ArrayList<?> values){
        ArrayList<Integer> tempArray= new ArrayList<Integer>();
        for(int i =0; i<values.size(); i++){
            Long temp = (Long)values.get(i);
            tempArray.add(temp.intValue());
        }

        Collections.sort(tempArray);
        if(tempArray.size() % 2 == 0){
            return tempArray.get(tempArray.size()/2);
        }else{
            int size = tempArray.size();
            int p = (int)(size/2);
            return (tempArray.get(p) + tempArray.get(p+1)) /2;

        }
    }
    /**
     * This method calculates the numerical median of an experiment
     * @param values
     * The numerical outcomes of the experiment
     * @return
     * The numerical median of the experiment
     */
    public float getfloatMedian(ArrayList<Float> values){

        Collections.sort(values);
        if(values.size() % 2 == 0){
            return values.get(values.size()/2);
        }else{
            int size = values.size();
            int p = (int)(size/2);
            return (values.get(p) + values.get(p+1)) /2;

        }
    }

    /**
     * This method calculates the std dev of a binomial experiment
     * @param values
     * The boolean outcomes of the experiment
     * @param mean
     * The mean value of the outcomes
     * @return
     * The std dev of a binomial experiment
     */
    public float getbooleanStandardDeviation(ArrayList<Boolean> values,float mean){
        double sum =0;
        double sd = 0;
        for(Boolean value:values){
            if(value){
                sd = 1-mean;
                sum = sum + (sd *sd)/values.size();

            }else{
                sd = 0-mean;
                sum = sum + (sd*sd)/values.size();
            }

        }
        return (float)Math.sqrt(sum);

    }

    /**
     * This method calculates the std dev of an experiment
     * @param values
     * The numerical outcomes of the experiment
     * @param mean
     * The mean value of the outcomes
     * @return
     * The std dev of an experiment
     */
    public float getStandardDeviation(ArrayList<Float> values,float mean){
        double sum =0;
        double sd = 0;
        for(float value:values){
                sd = value - mean;
                sum = sum + (sd*sd)/values.size();
        }


        return (float)Math.sqrt(sum);

    }

    /**
     * This method calculates the std dev of an experiment
     * @param values
     * The numerical outcomes of the experiment
     * @param mean
     * The mean value of the outcomes
     * @return
     * The std dev of an experiment
     */
    public float getIntegerStandardDeviation(ArrayList<?> values,float mean){
        double sum =0;
        double sd = 0;

        ArrayList<Integer> tempArray= new ArrayList<Integer>();
        for(int i =0; i<values.size(); i++){
            Long temp = (Long)values.get(i);
            tempArray.add(temp.intValue());
        }
        for(float value:tempArray){
            sd = value - mean;
            sum = sum + (sd*sd)/values.size();
        }


        return (float)Math.sqrt(sum);

    }


}
