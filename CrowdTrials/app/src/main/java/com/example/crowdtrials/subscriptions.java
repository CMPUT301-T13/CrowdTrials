package com.example.crowdtrials;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * This class represents the fragment used to subscribe to an experiment
 */
public class subscriptions extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ListView subscribedList;
    ArrayAdapter<Experiment> subscribedAdapter;
    ArrayList<Experiment> subsribedDataList;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public subscriptions() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static subscriptions newInstance(String param1, String param2) {
        subscriptions fragment = new subscriptions();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_subscriptions, container, false);




        subscribedList = (ListView)view.findViewById(R.id.subscribed_list);
        subsribedDataList = new ArrayList<>();
        subscribedAdapter = new ExperimentList(getActivity(), subsribedDataList);

        subscribedList.setAdapter(subscribedAdapter);

        return view;

    }



    public void getList(ArrayList<Experiment> value){

        for (Experiment experiment:value){

            subsribedDataList.add(experiment);
        }
        subscribedAdapter.notifyDataSetChanged();
    }
}