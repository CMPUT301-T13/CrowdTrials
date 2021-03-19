package com.example.crowdtrials;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * This class represents the fragment used to create a new User.
 */
public class CreateUserFragment extends DialogFragment {
    EditText name;
    EditText phoneNum;
    Button typeUser;
    private CreateUserFragment.OnFragmentInteractionListener listener;

    public interface OnFragmentInteractionListener {
        void onOkPressed(String phoneNum, String name);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CreateUserFragment.OnFragmentInteractionListener){
            listener = (CreateUserFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    public static CreateUserFragment getNameNumType(String phoneNum, String name){
        Bundle args= new Bundle();
        args.putSerializable("name",name);
        args.putSerializable("phoneNum",phoneNum);
        CreateUserFragment fragment = new CreateUserFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.create_user_fragment_layout, null);
        name = view.findViewById(R.id.name_editText);
        // provide instructions
        name.setError("Please enter your real name");
        phoneNum = view.findViewById(R.id.phoneNum_editText);
        phoneNum.setError("Under 10 digits please and no dashes");


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Create a Profile")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String n=name.getText().toString();
                        String num = phoneNum.getText().toString();

                        listener.onOkPressed(num,n);
                    }}).create();
    }


}