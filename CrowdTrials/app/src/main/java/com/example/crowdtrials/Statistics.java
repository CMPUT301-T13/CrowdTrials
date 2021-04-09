package com.example.crowdtrials;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Statistics {

    public void Statistics(){

    }

    public float getBinomialMean(ArrayList<Boolean> outcomes){
        int sum =0;
        for(Boolean outcome:outcomes){
            if(outcome){
                sum+=1;
            }
        }
        return (float)sum/outcomes.size();
    }

    public float  getIntegerMean(ArrayList<?> values){
        int sum =0;
        for(int i =0; i<values.size(); i++){

            Log.e("GET INTEGER MEAN " , " " + values.get(i));
                Long temp = (Long)values.get(i);
                sum += temp.intValue();
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
    public float getStandardDeviation(ArrayList<Float> values,float mean){
        double sum =0;
        double sd = 0;
        for(float value:values){
                sd = value - mean;
                sum = sum + (sd*sd)/values.size();
        }


        return (float)Math.sqrt(sum);

    }
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
