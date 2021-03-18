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
 * This class represent the fragment for login functionality of the application.
 */
public class LoginFragment extends DialogFragment {
    // Based on AddCityFragment made by CMPUT 301 TAs in lab 3
    // this is to get the username from the user
    private EditText username;
    private LoginFragment.OnFragmentInteractionListener listener;

    public interface OnFragmentInteractionListener {
        void onOkPressed(String username);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LoginFragment.OnFragmentInteractionListener){
            listener = (LoginFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    public static LoginFragment UserInstance(String username){
        Bundle args= new Bundle();
        args.putSerializable("username",username);
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.login_fragment_layout, null);
        username = view.findViewById(R.id.username_editText);

        // tell user what happens if username does not exist
       username.setError("If username does not exist within database, new account will be created for you.");

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Login")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // on ok click we need to query the database back in login activity to see if username is in database
                        String user=username.getText().toString();
                        listener.onOkPressed(user);
                    }}).create();
    }


}