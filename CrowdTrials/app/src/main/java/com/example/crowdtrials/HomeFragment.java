package com.example.crowdtrials;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.google.firebase.FirebaseApp;

import java.util.ArrayList;
import java.util.Date;

public class HomeFragment extends Fragment implements MyCallback{
    ListView experimentList;
    ArrayAdapter<Experiment> experimentAdapter;
    ArrayList<Experiment> experimentDataList;
    Database database;
    public HomeFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        database = new Database();

        experimentList = (ListView)view.findViewById(R.id.experiment_list);
        experimentDataList = new ArrayList<>();
        experimentAdapter = new ExperimentList(getActivity(), experimentDataList);

        experimentList.setAdapter(experimentAdapter);
        database.readExperiments(this::onCallback);
        return view;
    }

    public ListView getList(){
        return experimentList;
    }

    public void onCallback(ArrayList<Experiment> value) {
        for (Experiment experiment:value){
            experimentDataList.add(experiment);
        }

        //Log.e("In call back", "Call back called" +" " + value);
        experimentAdapter.notifyDataSetChanged();
    }

    public void writeToDatabase(Experiment experiment){
        database.writeExperiments(experiment);
    }


        Experiment testExperimentCreation(String name, String phoneNumber,String description) {
        ContactInfo contactInfo = new ContactInfo(name,phoneNumber);
        User owner = new User("randomUserName",contactInfo);
        Date date = new Date();
        Location newRegion = new Location("");
        Experiment newExperiment = new BinomialExp(owner,newRegion,description,date,1);
        return newExperiment;
    }

}
