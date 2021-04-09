package com.example.crowdtrials;

//import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is an interface for the database.
 */
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

    /**
     * This class returns the static instance of the database.
     * @return
     * The static instance of the database
     */
    public static Database getSingleDatabaseInstance() {
        if (staticInstanceOfDatabase == null) {
            staticInstanceOfDatabase = new Database();
        }
        return staticInstanceOfDatabase;
    }

    /**
     * This class updates the database with the results.
     * @param result
     * This is the result of the updating the database - success if it successfully updated and failure, if the update failed
     * @param experimentName
     * This is the name of the experiment being updated
     */
    public void updateWithResults(ResultArr result,String experimentName){
        Map<String, Object> data = new HashMap<>();
        data.put("Result",result);
        collectionReference.document(experimentName).collection("Trials").add(data)
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
    }

    /**
     * This method adds a question of a certain experiment to the database
     * @param question
     * This is the question object to be added to the database
     * @param experimentName
     * This is the name of the experiment for which the question is asked
     */
    public void addQuestion(QnA question,String experimentName){
        Map<String, Object> data = new HashMap<>();
        data.put("Question",question);
        collectionReference.document(experimentName).collection("Questions").document(question.question).set(data);

    }

    /**
     * This method adds an answer to a question of a certain experiment to the database.
     * @param question
     * This is the question object for which the answer is entered.
     * @param experimentName
     * This is the name of the experiment for which the answer is entered
     * @param answer
     * This is the answer String to be entered.
     */
    public void addAnswer(QnA question,String experimentName,String answer){
        Map<String, Object> data = new HashMap<>();


        DocumentReference docRef = collectionReference.document(experimentName).collection("Questions").document(question.question);
        FieldPath fieldPath = FieldPath.of("Question","answers");
        docRef.update(fieldPath, FieldValue.arrayUnion(question.answers.toArray()));


        /*
        data.put("Question",question);
        collectionReference.document(experimentName).collection("Questions").document(question.question).collection("Answers").add(data)
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

    /**
     * This method is used to search for an experiment within the using a query.
     * @param myCallback
     * This is the interface to handle the experiment list on callback.
     * @param query
     * The search term entered by the user.
     */
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

    /**
     * This gets the reference to the collection of all experiments in the database
     * @param myCallback
     * This is the interface to handle the experiment list on callback
     */
    public void readAllExperiments(MyCallback myCallback) {
        getAllExperiments(collectionReference,myCallback);

    }

    /**
     * This gets the reference to the collection of all subscribed experiments of an user
     * @param myCallback
     * This is the interface to handle the experiment list on callback
     * @param user
     * The user who subscribed to experiments
     */
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

                         Log.d("RETURNING", document.getId() + " => " + subscribedListFromDataBase);



                    }





                } else {
                    Log.w("My Activity", "Error getting documents.", task.getException());
                }
            }


        });
    }

    /**
     * This method parses through a document to identify the experiment.
     * @param document
     * The document being parsed through
     * @param value
     * The array list that holds the experiments
     */
    private void parseDocument(DocumentSnapshot document,ArrayList<Experiment> value) {
        Experiment experiment;
        ContactInfo contactInfo;
        Location newRegion;
        User owner;

        switch (document.getString("Experiment Type")) {
            case "Binomial Experiment":
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
                experiment.type = document.getString("Experiment Type");
                ///experiment.isGeoLocationEnabled = (String document.get())
                value.add(experiment);



                break;

            case "Measurement Experiment":
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
                experiment.type = document.getString("Experiment Type");
               // Log.d("My exp", "get failed with " + experiment.type);
                value.add(experiment);
                //myCallback.onCallback(subscribedListFromDataBase,1);

                break;
            case "NonNegative Count Experiment":
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
                experiment.type = document.getString("Experiment Type");
                value.add(experiment);
                //myCallback.onCallback(subscribedListFromDataBase,1);

                break;
            case "Count Experiment":
                experiment = new CountExp();
                contactInfo = new ContactInfo((String) document.get("Owner Name"), (String) document.get("contactInfo"));
                owner = new User((String) document.get("userName"), contactInfo);
                experiment.setOwner(owner);
                newRegion = new Location("");
                experiment.setRegion(newRegion);
                experiment.setDescription((String) document.get("description"));
                experiment.published = document.getBoolean("published");
                experiment.ended = document.getBoolean("ended");
                experiment.setName((String) document.get("experimentName"));
                experiment.type = document.getString("Experiment Type");
                value.add(experiment);
                //myCallback.onCallback(subscribedListFromDataBase,1);

                break;
        }
    }


    /**
     * This method handles a single experiment.
     * @param myCallback
     * This is the interface to handle the experiment list on callback
     * @param experimentName
     * This is the name of the experiment
     */
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
                        myCallback.onCallback(subscribedListFromDataBase,1);
                        subscribedListFromDataBase.clear();


                    }else {
                        Log.d("ERROR", "No such document");
                    }

                } else {
                    Log.d("My Activity", "No such document");
                }


            }

        });

    }

    /**
     * This method gets the collection Reference of all Experiments
     * @param collectionReference
     * The reference to the Collection of all experiments in the database
     * @param myCallback
     * This is the interface to handle the experiment list on callback
     */
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


                                Log.d("My Activity", document.getId() + " => " + document.getData());
                                parseDocument(document,ListFromDataBase);


                            }
                            myCallback.onCallback(ListFromDataBase,0);
                        } else {
                            Log.w("My Activity", "Error getting documents.", task.getException());
                        }
                    }
                });


    }

    /**
     * This method gets all the outcomes of an experiment from the database
     * @param exp
     * The experiment whose outcomes have to be found
     * @param myCallback
     * This is the interface to handle the results list on callback
     *
     */
    public void getAllResults(Experiment exp,ResultsCallback myCallback){
        collectionReference.document(exp.getName()).collection("Trials")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                       // Log.d("GET ALL RESULTS","before");
                        if (task.isSuccessful()) {
                            ArrayList<ResultArr> ListFromDataBase = new ArrayList<ResultArr>();
                            //Log.d("GET ALL RESULTS","couldnt get documents");

                            ContactInfo contactInfo;
                            Location newRegion;
                            User experimenter;
                            ResultArr result;
                            String username;
                            FieldPath fieldPath;
                            String name;
                            String phoneNumber;
                            FieldPath secondFieldPath;

                            for (QueryDocumentSnapshot document : task.getResult()) {


                                switch (exp.type) {
                                    case "Binomial Experiment":

                                        fieldPath =  FieldPath.of("Result","outcomes");


                                        ArrayList<Boolean> outcomes = (ArrayList<Boolean>) document.get(fieldPath);

                                        fieldPath = FieldPath.of("Result","experimenter","username");

                                        username = (String)document.get(fieldPath);
                                        experimenter = new User(username);



                                        fieldPath = FieldPath.of("Result","experimenter","name");
                                        secondFieldPath =  FieldPath.of("Result","experimenter","phoneNumber");
                                        if (document.get(fieldPath) != null && document.get(secondFieldPath) != null){
                                            name = (String)document.get(fieldPath);
                                            phoneNumber = (String)document.get(secondFieldPath);
                                            contactInfo = new ContactInfo(name,phoneNumber);
                                            experimenter.setContactInfo(contactInfo);
                                        }

                                        result = new BoolResult(experimenter);
                                        ((BoolResult) result).outcomes = outcomes;








                                        //Log.d("GET ALL RESULTS","" + outcomes);
                                        ListFromDataBase.add(result);



                                        break;

                                    case "Measurement Experiment":


                                         fieldPath =  FieldPath.of("Result","measurements");

                                        ArrayList<Float> values = (ArrayList<Float>) document.get(fieldPath);

                                        fieldPath = FieldPath.of("Result","experimenter","username");

                                        username = (String)document.get(fieldPath);
                                        experimenter = new User(username);

                                        fieldPath = FieldPath.of("Result","experimenter","name");
                                        secondFieldPath =  FieldPath.of("Result","experimenter","phoneNumber");
                                        if (document.get(fieldPath) != null && document.get(secondFieldPath) != null){
                                            name = (String)document.get(fieldPath);
                                            phoneNumber = (String)document.get(secondFieldPath);
                                            contactInfo = new ContactInfo(name,phoneNumber);
                                            experimenter.setContactInfo(contactInfo);
                                        }

                                        result = new FloatResult(experimenter);
                                        ((FloatResult) result).measurements = values;

                                        ListFromDataBase.add(result);

                                        break;
                                    case "NonNegative Count Experiment":
                                    case "Count Experiment":

                                        fieldPath =  FieldPath.of("Result","values");

                                        ArrayList<Integer> count = (ArrayList<Integer>) document.get(fieldPath);

                                        fieldPath = FieldPath.of("Result","experimenter","username");

                                        username = (String)document.get(fieldPath);
                                        experimenter = new User(username);

                                        fieldPath = FieldPath.of("Result","experimenter","name");
                                        secondFieldPath =  FieldPath.of("Result","experimenter","phoneNumber");
                                        if (document.get(fieldPath) != null && document.get(secondFieldPath) != null){
                                            name = (String)document.get(fieldPath);
                                            phoneNumber = (String)document.get(secondFieldPath);
                                            contactInfo = new ContactInfo(name,phoneNumber);
                                            experimenter.setContactInfo(contactInfo);
                                        }

                                        result = new IntResult(experimenter);
                                        ((IntResult) result).values = count;

                                        ListFromDataBase.add(result);

                                        break;


                                    default:
                                        Log.d("GET ALL RESULTS",exp.type);
                                }


                            }
                            myCallback.onCallback(ListFromDataBase,0);
                        } else {
                            Log.w("My Activity", "Error getting documents.", task.getException());
                        }
                    }
                });


    }

    /**
     * This methods gets all the answers of a question in a certain experiment from the database
     * @param exp
     * The experiment whose answers are being searched for
     * @param question
     * The question whose answers are being searched for
     * @param myCallback
     * This is the interface to handle the answer and question list on callback
     */
    public void getAnswers(Experiment exp,QnA question, QuestionsCallback myCallback){
        DocumentReference docRef = collectionReference.document(exp.getName()).collection("Questions").document(question.question);
        ArrayList<QnA> questionsList = new ArrayList<QnA>();
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    FieldPath fieldPath = FieldPath.of("Question","question");
                    FieldPath secondFieldPath= FieldPath.of("Question","answers");

                    String questionString;
                    QnA question;
                    ArrayList<String> answers = new ArrayList<>();
                    questionString = (String)document.get(fieldPath);
                    if (document.get(secondFieldPath) != null) {
                        answers = (ArrayList<String>) document.get(secondFieldPath);

                    }
                    question = new QnA(questionString);
                    question.answers = answers;
                    questionsList.add(question);
                    myCallback.onCallback(questionsList, 0);
                }
            }
        });
    }


    /**
     * This methods gets all the questions in a certain experiment from the database
     * @param exp
     * The experiment whose questions are being searched for
     * @param myCallback
     * This is the interface to handle the question list on callback
     */
    public void getQuestions(Experiment exp, QuestionsCallback myCallback){

        ArrayList<QnA> questionsList = new ArrayList<QnA>();
        collectionReference.document(exp.getName()).collection("Questions")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        // Log.d("GET ALL RESULTS","before");
                        String questionString;
                        QnA  question;
                        ArrayList<String> answers=new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                FieldPath fieldPath = FieldPath.of("Question","question");
                                questionString = (String)document.get(fieldPath);
                                if(document.get("answers")!=null){
                                    answers = (ArrayList<String>)document.get("answers");

                                }
                                Log.d("My Activity", "get Questions " + questionString);
                                question = new QnA(questionString);
                                question.answers = answers;
                                questionsList.add(question);
                            }

                        myCallback.onCallback(questionsList,0);

                        }
                    }
                });
    }

    /**
     * This reads a single user from the database
     * @param username
     * This is the username of the user
     * @param myUserCallback
     * This is the interface to handle the user on callback
     */
    public void readUser(String username,UserCallback myUserCallback){
        Map<String, Object> data = new HashMap<>();
        data.put("Username",username);
        DocumentReference docRef = db.collection("Users").document(username);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    User user = new User(username);
                    ContactInfo contactInfo;
                    if (document.exists()) {
                        Log.d("My Activity", "DocumentSnapshot data: " + document.getData());

                        if(document.contains("Name") && document.contains("Phonenum")){
                            contactInfo = new ContactInfo((String) document.get("Name"),(String)document.get("Phonenum"));
                            user.setContactInfo(contactInfo);
                            myUserCallback.userCallback(user);

                        }else{
                            myUserCallback.userCallback(user);
                        }
                    } else {
                        Log.d("My Activity", "No such document");

                        userCollectionReference.document(username).set(data);
                        myUserCallback.userCallback(user);




                    }
                } else {
                    Log.d("My Activity", "get failed with ", task.getException());

                }
            }
        });

    }


    /**
     * This method is used to write an experiment object in the database
     * @param experiment
     * The current experiment object that is being inserted in the database
     */

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


    /**
     * This method allows a user to subscribe to the selected experiment
     * @param experiment
     * The experiment that is being subscribed to
     * @param user
     * The user that is subscribing to an experiment
     */
    public void subscribeTo(Experiment experiment,User user){
        DocumentReference docRef = collectionReference.document(experiment.getName());
        Map<String, Object> data = new HashMap<>();

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("My Activity", "DocumentSnapshot data: " + document.getData());
                        String name = (String)document.get("name");
                        data.put("name",name);
                        userCollectionReference.document(user.username).collection("Subscriptions").document(name).set(data);


                    } else {
                        Log.d("My Activity", "No such document");
                    }
                } else {
                    Log.d("My Activity", "get failed with ", task.getException());

                }
            }
        });



    }

    /**
     * This method updates the user object in the database
     * @param user
     * This is the the user object being updated in the database
     */
    public void updateUser(User user){
        Map<String, Object> data = new HashMap<>();
        //TODO: Check for when theres no contactinfo object
        data.put("Name",user.contactInfo.getName());
        data.put("Phonenum",user.contactInfo.getPhoneNumber());
        DocumentReference docRef = userCollectionReference.document(user.username);
        docRef.set(data);

    }
    public void  updatePub(Experiment exp,boolean tf){
        Map<String, Object> data = new HashMap<>();
        //TODO: Check for when theres no contactinfo object
        data.put("published",tf);
        DocumentReference docRef = collectionReference.document(exp.name);
        docRef.set(data);

    }
    public void  updateEnd(Experiment exp,boolean tf){
        Map<String, Object> data = new HashMap<>();
        //TODO: Check for when theres no contactinfo object
        data.put("ended",tf);
        DocumentReference docRef = collectionReference.document(exp.name);
        docRef.set(data);

    }

}
