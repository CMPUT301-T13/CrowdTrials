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
 * This class represents the fragment used to respond to a question
 */
public class RespondQuestionFragment extends DialogFragment {
    // Based on AddCityFragment made by CMPUT 301 TAs in lab 3
    // this is to get the username from the user
    private EditText answer;
    private RespondQuestionFragment.OnFragmentInteractionListener listener;

    public interface OnFragmentInteractionListener {
        void onAddAnswerOkPressed(String username);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RespondQuestionFragment.OnFragmentInteractionListener){
            listener = (RespondQuestionFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    public static RespondQuestionFragment QuestionInstance(String username){
        Bundle args= new Bundle();
        args.putSerializable("username",username);
        RespondQuestionFragment fragment = new RespondQuestionFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.respond_question_fragment_layout, null);
        answer = view.findViewById(R.id.newanswer_editText);

        // tell user what happens if username does not exist
        answer.setError("Please be appropriate and constructive in your questions.");

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Add answer")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // on ok click we need to query the database back in login activity to see if username is in database
                        String newanswer=answer.getText().toString();
                        listener.onAddAnswerOkPressed(newanswer);
                    }}).create();
    }



}
