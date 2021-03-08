package com.example.crowdtrials;

import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Database {

    FirebaseFirestore db;
    final CollectionReference collectionReference;
    public Database(){
        db = FirebaseFirestore.getInstance();// Access a Cloud Firestore instance from your Activity
        collectionReference= db.collection("Experiments");
    }

    public ArrayList<Experiment> readExperiments(){
        ArrayList<Experiment> ListFromDataBase = new ArrayList<Experiment>();
         collectionReference
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("My Actvitiy", document.getId() + " => " + document.getData());
                                ListFromDataBase.add(new BinomialExp(new Owner(document.getData().get("userName"),document.get("contactInfo")),new Location(""),document.get("description"),new Date(),1));
                            }
                        } else {
                            Log.w("My Actvitiy", "Error getting documents.", task.getException());
                        }
                    }
                });

    }
    public void writeExperiments(Experiment experiment){
        Map<String, Object> data = new HashMap<>();
        data.put("description", experiment.getDescription());
        data.put("date", experiment.getDate());
        data.put("userName", experiment.getOwner().username);
        data.put("contactInfo",experiment.getOwner().contactInfo.getPhoneNumber());
        data.put("Owner Name",experiment.getOwner().contactInfo.getName());


        // Add a new document with a generated ID
        collectionReference
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("My Actvitiy", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("My Activity", "Error adding document", e);
                    }
                });

    }
}
