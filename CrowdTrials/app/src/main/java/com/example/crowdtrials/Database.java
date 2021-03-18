package com.example.crowdtrials;

import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Database {
    private static Database staticInstanceOfDatabase;
    FirebaseFirestore db;
    final CollectionReference collectionReference;
    final CollectionReference userCollectionReference;
    ArrayList<Experiment> subscribedListFromDataBase = new ArrayList<Experiment>();
    ArrayList<Experiment> searchedExperiments ;


    private Database() {
        db = FirebaseFirestore.getInstance();// Access a Cloud Firestore instance from your Activity
        collectionReference = db.collection("Experiments");
        userCollectionReference = db.collection("Users");
    }

    public static Database getSingleDatabaseInstance() {
        if (staticInstanceOfDatabase == null) {
            staticInstanceOfDatabase = new Database();
        }
        return staticInstanceOfDatabase;
    }
    public void searchExperiments(MyCallback myCallback,String query) {
            collectionReference.orderBy("name").startAt(query).endAt(query + "\uf8ff").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    searchedExperiments = new ArrayList<Experiment>();
                    Log.e("In Search Experiments","Document snapshot" + "I was called");
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                       Log.e("In Search Experiments","Document snapshot" + document);
                       parseDocument(document,searchedExperiments);
                        myCallback.onCallback(searchedExperiments,0);

                   }

                    // ...
                }
            });


    }
    public void readAllExperiments(MyCallback myCallback) {
        getAllExperiments(collectionReference,myCallback);

    }
    public void readSubscribedExperiments(MyCallback myCallback,User user){

        userCollectionReference.document(user.username).collection("Subscriptions")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                String experimentName;

                if (task.isSuccessful()) {



                    for (QueryDocumentSnapshot document : task.getResult()) {
                         experimentName = (String)document.get("name");
                         getSingleExperiment(myCallback,experimentName);

                         Log.d("this was called", document.getId() + " => " + document.getData());


                        Log.d("My Actvitiy", document.getId() + " => " + document.getData());


                    }




                } else {
                    Log.w("My Actvitiy", "Error getting documents.", task.getException());
                }
            }


        });
    }
    private void parseDocument(DocumentSnapshot document,ArrayList<Experiment> value) {
        Experiment experiment;
        ContactInfo contactInfo;
        Location newRegion;
        User owner;

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
                experiment.setName((String) document.get("name"));
                ///experiment.isGeoLocationEnabled = (String document.get())
                value.add(experiment);



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
                experiment.setName((String) document.get("name"));
                value.add(experiment);
                //myCallback.onCallback(subscribedListFromDataBase,1);

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
                experiment.setName((String) document.get("name"));
                value.add(experiment);
                //myCallback.onCallback(subscribedListFromDataBase,1);

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
                experiment.setName((String) document.get("name"));
                value.add(experiment);
                //myCallback.onCallback(subscribedListFromDataBase,1);

                break;
        }
    }


    public void getSingleExperiment(MyCallback myCallback,String experimentName) {

        DocumentReference docRef = collectionReference.document(experimentName);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {


                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("Get single experiment", "DocumentSnapshot data ex: " + document.getData());
                        parseDocument(document,subscribedListFromDataBase);


                    }else {
                        Log.d("ERROR", "No such document");
                    }

                } else {
                    Log.d("My Activity", "No such document");
                }
                myCallback.onCallback(subscribedListFromDataBase,1);
            }

        });

    }

    public void getAllExperiments(CollectionReference collectionReference,MyCallback myCallback){
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
                                parseDocument(document,ListFromDataBase);


                            }
                            myCallback.onCallback(ListFromDataBase,0);
                        } else {
                            Log.w("My Activity", "Error getting documents.", task.getException());
                        }
                    }
                });


    }
    public void readUser(String username,UserCallback myUserCallback){
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
                        myUserCallback.userCallback(new User(username));

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
        data.put("experimentName",experiment.name);
        data.put("userName", experiment.getOwner().username);
        //data.put("contactInfo",experiment.getOwner().contactInfo.getPhoneNumber());
        //data.put("Owner Name",experiment.getOwner().contactInfo.getName());
        data.put("Experiment Type",experiment.type);
        data.put("min trials", experiment.minTrials);
        data.put("published",experiment.isPublished());
        data.put("ended",experiment.isEnded());
        data.put("name",experiment.getName());
        data.put("isGeoLocationEnabled",experiment.isGeoLocationEnabled);



        // Add a new document with a generated ID
        collectionReference.document(experiment.getName())
                .set(data);

                /*
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("My Activity", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("My Activity", "Error adding document", e);
                    }
                });


                 */

    }



    public void subscribeTo(Experiment experiment,User user){
        DocumentReference docRef = collectionReference.document(experiment.getName());
        Map<String, Object> data = new HashMap<>();

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("My Actvitiy", "DocumentSnapshot data: " + document.getData());
                        String name = (String)document.get("name");
                        data.put("name",name);
                        userCollectionReference.document(user.username).collection("Subscriptions").document(name).set(data);


                    } else {
                        Log.d("My Actvitiy", "No such document");
                    }
                } else {
                    Log.d("My Actvitiy", "get failed with ", task.getException());

                }
            }
        });



    }
}
