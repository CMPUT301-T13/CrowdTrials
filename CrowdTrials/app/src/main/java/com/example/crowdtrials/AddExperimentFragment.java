package com.example.crowdtrials;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
//import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Date;

public class AddExperimentFragment extends DialogFragment {
    /*
   Citation
   Most of the code  in this class was used from the LAB3 code for Listycity
   Title: LAB3 DEMO
   Author: CMPUT301TA
   Availability: ECLASS

   This Class is used to create a DialogFragment in the the middle of the main activity.
   It is only used to gather input from the user such as the name, description, and date.
   Once the user presses Ok the Dialog will callBack the Main Activity or the activity that created it
   and will then send a new Experiment to it thorough the method onOkPressed() which all Classes that
   implement OnFragmentInteractionListener must implement. The new Experiment it will send will have
   name, description properties, along with the date selected by the user . The date will be encoded as a string


    */
    private EditText experimentName;
    private EditText experimentDescription;
    private EditText experimentMinTrials;
    private String[] options  = {"Binomial Experiment", "Measurment Experiment", "NonNegative Count Experiment","Count Experiment"};
    private Spinner optionsSpinner;
    private EditText regionEditText;
    private CheckBox geolocationCheckBox;
    private OnFragmentInteractionListener listener;
    private User user;

    public interface OnFragmentInteractionListener {
        // simple interface in order to use for The DialogFragment
       public void onAddExperimentOkPressed(Experiment newExperiment);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener){
            listener = (OnFragmentInteractionListener) context;
            MainActivity mainActivity = (MainActivity)context;
            user = mainActivity.user;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_experiment_fragment, null);
        experimentName = view.findViewById(R.id.name_editText);
        experimentDescription = view.findViewById(R.id.description_editText);
        experimentMinTrials = view.findViewById(R.id.minTrialsEditText);
        optionsSpinner =view.findViewById(R.id.experiment_type_Spinner);
        regionEditText = view.findViewById(R.id.region_editText);
        geolocationCheckBox = view.findViewById(R.id.geolocation_checkbox);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, options);
        optionsSpinner.setAdapter(adapter);


        /*
            Citation
           Author:Google/Android
           Availablility:https://developer.android.com/reference/android/app/DatePickerDialog
                 */


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Add Experiment")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //When we click Ok button in the DiaglogFragment Create a new experiment with
                        //the strings entered into the EditTexts, we also get the date from the Datepicker
                        String name = experimentName.getText().toString();
                        String description = experimentDescription.getText().toString();
                        String region = regionEditText.getText().toString();
                        int minTrials = Integer.parseInt(experimentMinTrials.getText().toString());

                        String spinnerItem = (String)optionsSpinner.getSelectedItem();
                        Experiment experiment;
                        Location newRegion;
                        switch (spinnerItem){
                            case "Binomial Experiment":
                                experiment = new BinomialExp();
                                experiment.setName(name);
                                experiment.setDescription(description);
                                experiment.setOwner(user);
                                experiment.setDate(new Date());
                                experiment.minTrials = minTrials;
                                experiment.isGeoLocationEnabled = geolocationCheckBox.isEnabled();
                                newRegion = new Location("");
                                experiment.setRegion(newRegion);
                                experiment.type = spinnerItem;
                                listener.onAddExperimentOkPressed(experiment);
                                break;
                            case "Measurment Experiment":
                                experiment = new MeasurementExp();
                                experiment.setName(name);
                                experiment.setDescription(description);
                                experiment.setOwner(user);
                                experiment.setDate(new Date());
                                experiment.minTrials = minTrials;
                                experiment.isGeoLocationEnabled = geolocationCheckBox.isEnabled();
                                newRegion = new Location("");
                                experiment.setRegion(newRegion);
                                experiment.type = spinnerItem;
                                listener.onAddExperimentOkPressed(experiment);
                                break;
                            case "NonNegative Count Experiment":
                                experiment = new NonNegativeCountExp();
                                experiment.setName(name);
                                experiment.setDescription(description);
                                experiment.setOwner(user);
                                experiment.setDate(new Date());
                                experiment.minTrials = minTrials;
                                experiment.isGeoLocationEnabled = geolocationCheckBox.isEnabled();
                                newRegion = new Location("");
                                experiment.setRegion(newRegion);
                                experiment.type = spinnerItem;
                                listener.onAddExperimentOkPressed(experiment);
                                break;
                            case "Count Experiment":
                                experiment = new CountExp();
                                experiment.setName(name);
                                experiment.setDescription(description);
                                experiment.setOwner(user);
                                experiment.setDate(new Date());
                                experiment.minTrials = minTrials;
                                experiment.isGeoLocationEnabled = geolocationCheckBox.isEnabled();
                                newRegion = new Location("");
                                experiment.setRegion(newRegion);
                                experiment.type = spinnerItem;
                                listener.onAddExperimentOkPressed(experiment);
                                break;
                            default:
                                break;




                        }





                    }}).create();
    }
}
