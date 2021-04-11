package com.example.crowdtrials;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * This class is used to create a Dialog fragment to add a question to an experiment
 */
public class AddQuestionFragment extends DialogFragment {
    // Based on AddCityFragment made by CMPUT 301 TAs in lab 3
    // this is to get the username from the user
    private EditText question;
    private AddQuestionFragment.OnFragmentInteractionListener listener;

    public interface OnFragmentInteractionListener {
        void onOkPressed(String username);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AddQuestionFragment.OnFragmentInteractionListener){
            listener = (AddQuestionFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    public static AddQuestionFragment QuestionInstance(String username){
        Bundle args= new Bundle();
        args.putSerializable("username",username);
        AddQuestionFragment fragment = new AddQuestionFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.addquestion_fragment, null);
        question = view.findViewById(R.id.newquestion_editText);

        // tell user what happens if username does not exist
        question.setError("Please be appropriate and constructive in your answer.");

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Add question")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // on ok click we need to query the database back in login activity to see if username is in database
                        String newquestion=question.getText().toString();
                        listener.onOkPressed(newquestion);
                    }}).create();
    }



}
