package com.example.crowdtrials;

import android.util.Log;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

            String summer =  String.valueOf(values.get(i));

            sum += Integer.parseInt(summer);
            //Log.e("GET INTEGER MEAN " , " " + summer);
                //sum += Integer.parseInt(values.get(i).toString());

            }
        String roundOff = String.valueOf(Math.round(((float)sum/values.size()) * 100.0) / 100.0);
        int i=0;
        while(i<roundOff.length()){
            if(roundOff.charAt(i)=='.'){
                if(i+2<roundOff.length()){
                    roundOff=roundOff.substring(0,i+3);
                    break;
                }
                else if(i+1<roundOff.length()){
                    roundOff=roundOff.substring(0,i+2);
                    break;
                }

            }
            i++;
        }
        Log.e("ROUNDOFF",roundOff);
        Log.e("FLOATROUND", Float.toString(Float.parseFloat(roundOff)));
        return Float.parseFloat(roundOff);

    }
    /**
     * This method calculates the float mean of an experiment
     * @param measurements
     * The numerical outcomes of the experiment
     * @return
     * The float mean of the experiment
     */
    public float  getfloatMean(ArrayList<Float> measurements){
        Float sum =0f;
        String temp;
        for(int i=0;i<measurements.size();i++){
            temp=String.valueOf(measurements.get(i));
            sum+=Float.parseFloat(temp);
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
    public Integer getIntegerMedian(ArrayList<?> values){
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
        String temp;
        ArrayList<Float> newvalues= new ArrayList<>();
        for(int i=0;i<values.size();i++){
            temp=String.valueOf(values.get(i));
            newvalues.add(Float.parseFloat(temp));

        }
        Collections.sort(newvalues);
        if(newvalues.size() % 2 == 0){
            return newvalues.get(newvalues.size()/2);
        }else{
            int size = newvalues.size();
            int p = (int)(size/2);
            return (newvalues.get(p) + newvalues.get(p+1)) /2;

        }
    }

    public Float[] getFloatQuartiles(ArrayList<Float> values){
        Collections.sort(values);
        Float[] ans = new Float[2];
        ArrayList<Float> q1 = new ArrayList<>(values.subList(0,(int) (values.size()/2)+1));
        ans[0]=getfloatMedian(q1);
        ArrayList<Float> q3 = new ArrayList<>(values.subList( (int) ((values.size()/2)+1),values.size()));
        ans[1]=getfloatMedian(q3);
        return ans;
    }

    public Integer[] getIntegerQuartiles(ArrayList<Integer> values){
        Collections.sort(values);
        Integer[] ans = new Integer[2];
        ArrayList<Integer> q1 = new ArrayList<>(values.subList(0,(int) (values.size()/2)+1));
        ans[0]=getIntegerMedian(q1);
        ArrayList<Integer> q3 = new ArrayList<>(values.subList( (int) ((values.size()/2)+1),values.size()));
        ans[1]=getIntegerMedian(q3);
        return ans;
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
        String temp;
        ArrayList<Float> newvalues= new ArrayList<>();
        for(int i=0;i<values.size();i++){
            temp=String.valueOf(values.get(i));
            newvalues.add(Float.parseFloat(temp));

        }
        for(float value:newvalues){
                sd = value - mean;
                sum = sum + (sd*sd)/newvalues.size();
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