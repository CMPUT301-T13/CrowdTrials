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
import com.google.firebase.firestore.DocumentSnapshot;
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
    final CollectionReference userCollectionReference;

    public Database() {
        db = FirebaseFirestore.getInstance();// Access a Cloud Firestore instance from your Activity
        collectionReference = db.collection("Experiments");
        userCollectionReference = db.collection("Users");
    }

    public void readExperiments(MyCallback myCallback) {

        collectionReference
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Experiment> ListFromDataBase = new ArrayList<Experiment>();
                            Experiment experiment;
                            ContactInfo contactInfo;
                            Location newRegion;
                            User owner;

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Log.d("My Activity", document.getId() + " => " + document.getData());


                                Log.d("My Actvitiy", document.getId() + " => " + document.getData());
                                switch (document.getString("Experiment Type")) {
                                    case "Binomial Exp":
                                        experiment = new BinomialExp();
                                        contactInfo = new ContactInfo((String) document.get("Owner Name"), (String) document.get("contactInfo"));
                                        owner = new User((String) document.get("userName"), contactInfo);
                                        experiment.setOwner(owner);
                                        newRegion = new Location("");
                                        experiment.setRegion(newRegion);
                                        experiment.setDescription((String) document.get("description"));
                                        experiment.published = document.getBoolean("published");
                                        experiment.ended = document.getBoolean("ended");

                                        ListFromDataBase.add(experiment);

                                        break;

                                    case "MeasurementExp":
                                        experiment = new MeasurementExp();
                                        contactInfo = new ContactInfo((String) document.get("Owner Name"), (String) document.get("contactInfo"));
                                        owner = new User((String) document.get("userName"), contactInfo);
                                        experiment.setOwner(owner);
                                        newRegion = new Location("");
                                        experiment.setRegion(newRegion);
                                        experiment.setDescription((String) document.get("description"));
                                        experiment.published = document.getBoolean("published");
                                        experiment.ended = document.getBoolean("ended");
                                        ListFromDataBase.add(experiment);
                                        break;
                                    case "NonNegativeCountExp":
                                        experiment = new NonNegativeCountExp();
                                        contactInfo = new ContactInfo((String) document.get("Owner Name"), (String) document.get("contactInfo"));
                                        owner = new User((String) document.get("userName"), contactInfo);
                                        experiment.setOwner(owner);
                                        newRegion = new Location("");
                                        experiment.setRegion(newRegion);
                                        experiment.setDescription((String) document.get("description"));
                                        experiment.published = document.getBoolean("published");
                                        experiment.ended = document.getBoolean("ended");
                                        ListFromDataBase.add(experiment);
                                        break;
                                    case "CountType":
                                        experiment = new CountExp();
                                        contactInfo = new ContactInfo((String) document.get("Owner Name"), (String) document.get("contactInfo"));
                                        owner = new User((String) document.get("userName"), contactInfo);
                                        experiment.setOwner(owner);
                                        newRegion = new Location("");
                                        experiment.setRegion(newRegion);
                                        experiment.setDescription((String) document.get("description"));
                                        experiment.published = document.getBoolean("published");
                                        experiment.ended = document.getBoolean("ended");
                                        ListFromDataBase.add(experiment);
                                        break;
                                }


                            }
                            myCallback.onCallback(ListFromDataBase);
                        } else {
                            Log.w("My Actvitiy", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
    public void readUser(String username,MyCallback myCallback){
        Map<String, Object> data = new HashMap<>();
        data.put("Username",username);
        DocumentReference docRef = db.collection("Users").document(username);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("My Actvitiy", "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d("My Actvitiy", "No such document");

                        userCollectionReference.document(username).set(data);

                    }
                } else {
                    Log.d("My Actvitiy", "get failed with ", task.getException());

                }
            }
        });

    }



    public void writeExperiments(Experiment experiment){
        Map<String, Object> data = new HashMap<>();
        data.put("description", experiment.getDescription());

        data.put("userName", experiment.getOwner().username);
        data.put("contactInfo",experiment.getOwner().contactInfo.getPhoneNumber());
        data.put("Owner Name",experiment.getOwner().contactInfo.getName());
        data.put("Experiment Type",experiment.type);
        data.put("min trials", experiment.minTrials);
        data.put("published",experiment.isPublished());
        data.put("ended",experiment.isEnded());


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
