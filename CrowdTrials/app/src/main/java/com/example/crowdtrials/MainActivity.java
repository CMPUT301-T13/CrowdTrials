package com.example.crowdtrials;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
//import android.location.Location;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * This class represents the main activity of the application.
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,CreateUserFragment.OnFragmentInteractionListener, MyCallback,UserCallback, AddExperimentFragment.OnFragmentInteractionListener {
// add waiting signal
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    NavigationView navigationView;
    ListView experimentList;
    ArrayAdapter<Experiment> experimentAdapter;
    ArrayList<Experiment> experimentDataList;
    Database database;
    FirebaseFirestore db;
    CollectionReference collectionReference;
    User user;
    String username;
    PagerAdapter pagerAdapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.main_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        drawerLayout=findViewById(R.id.drawer);
        navigationView=findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_fragment, new HomeFragment());
        fragmentTransaction.commit();
        FirebaseApp.initializeApp(this);


        database =  Database.getSingleDatabaseInstance();
        experimentDataList = new ArrayList<>();

        // get user from intent
        username = (String) getIntent().getSerializableExtra("user");
        if(username.equals("USER_DOES_NOT_HAVE_ACCOUNT")){
            Random random = new Random();
            int num = random.nextInt(4000000 - 1) + 1;
            username="nameless"+Integer.toString(num);
            sharedPreferences = this.getPreferences(MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.putString("user",username);
            editor.commit();



        }
        user = new User(username);


        // query database to see if username exists
        // query database with the passed in username

        database.readUser(username,this::userCallback);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        TabItem tabHome = findViewById(R.id.tab1);
        TabItem tabSubsriptions = findViewById(R.id.tab2);

        ViewPager viewPager = findViewById(R.id.ViewPager);

        pagerAdapter = new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        /*

        Task<DocumentSnapshot> usersRef = database.userCollectionReference.document(username).get();
        user = (User) usersRef.getResult().getData();
        if(user!=null){
            // get experiments

        }
        else{

        }

        */

        /*
        experimentDataList.add(testExperimentCreation("John","58712342123","First Experiment Added to database","Experiment 1"));
        writeToDatabase(experimentDataList.get(0));
        experimentDataList.add(testExperimentCreation("Jack","78012342123","Second Experiment Added to database","Experiment 2"));
        writeToDatabase(experimentDataList.get(1));
        experimentDataList.add(testExperimentCreation("Adam","7801234566","Third Experiment Added to database","Experiment 3"));
        writeToDatabase(experimentDataList.get(2));

        */

        database.readAllExperiments(this::onCallback);
        database.readSubscribedExperiments(this::onCallback,user);

        final FloatingActionButton addCityButton = findViewById(R.id.add_experiment_button);
        addCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddExperimentFragment().show(getSupportFragmentManager(), "ADD_EXPERIMENT");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drawer_menu, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        TextView textView = (TextView) menu.findItem(R.id.profileName).getActionView();
        textView.setText(user.username);


        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.profileName);
        item.setTitle(user.username);
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.profileName:

            goToProfileActivity(true,this.user);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void goToProfileActivity(boolean isMyUsername,User user) {
        //This is where you start the Profile activity
        Log.e("Called","In go to profile activity");
        Intent intent = new Intent(this,DisplayProfileActivity.class);
        intent.putExtra("user",user);
        intent.putExtra("isMyUsername",isMyUsername);
        startActivityForResult(intent,1);
    }

    public void userCallback(User userFromDatabase) {
        if(userFromDatabase.username == this.user.username) {

            if (userFromDatabase.contactInfo != null) {
                Log.e("Called", "In usercallback" + userFromDatabase.contactInfo.getName());
                this.user.setContactInfo(userFromDatabase.contactInfo);
            } else {
                Log.e("Called", "In usercallback" + "error");
                this.user.username = userFromDatabase.username;
            }
            //do something
        }else{
            goToProfileActivity(false,userFromDatabase);
        }
    }

    public void userNameWasPressed(String username){

        database.readUser(username,this::userCallback);
    }
    public void onCallback(ArrayList<Experiment> value,int whichCase) {
        Log.e("My Activity", "I've been called" + whichCase);
        switch (whichCase){
            case 0:
                Log.d("My Activity", "get failed with 0" + value);
                pagerAdapter.homeFragment.getList(value);
                break;
            case 1:
                Log.d("My Activity", "get failed with 1" + value);
                pagerAdapter.subscriptionsFragment.getList(value);
                break;
            default:

                //do nothing
        }


    }

    public void writeToDatabase(Experiment experiment){
        database.writeExperiments(experiment);
    }



    public Experiment testExperimentCreation(String contactName, String phoneNumber,String description,String experimentName) {
        ContactInfo contactInfo = new ContactInfo(contactName,phoneNumber);
        User owner = new User("randomUserName",contactInfo);
        Date date = new Date();
        Location newRegion = new Location("");

        Experiment newExperiment = new BinomialExp(owner,newRegion,description,date,1,experimentName);
        return newExperiment;

    }

    @Override
    public void onOkPressed(String phoneNum,String name) {
        //user = new User(username,new ContactInfo(name,phoneNum));
    }
    @Override
    public void onAddExperimentOkPressed(Experiment newExperiment) {
        database.writeExperiments(newExperiment);
    }

    public void subscribedButtonPressed(Experiment experiment) {
        Log.d("My Activity", "get failed with " + experiment.getDescription());

        database.subscribeTo(experiment,user);

    }

    public void addResultPressed(Experiment experiment,int pos){
        Log.d("My Activity", "get failed with " + experiment.getDescription());
        if(experiment instanceof BinomialExp) {
            Intent intent = new Intent(this, BinomialActivity.class);
            intent.putExtra("exp", experiment);
            intent.putExtra("user", user);
            intent.putExtra("pos", pos);
            startActivityForResult(intent,2);
        }
        else if(experiment instanceof MeasurementExp){
            Intent intent = new Intent(this, MeasurementActivity.class);
            intent.putExtra("exp", experiment);
            intent.putExtra("user", user);
            intent.putExtra("pos", pos);
            startActivityForResult(intent,3);
        }
        else if(experiment instanceof CountExp){
            Intent intent = new Intent(this, CountActivity.class);
            intent.putExtra("exp", experiment);
            intent.putExtra("user", user);
            intent.putExtra("pos", pos);
            startActivityForResult(intent,4);
        }
        else if(experiment instanceof NonNegativeCountExp){
            Intent intent = new Intent(this, CountActivity.class);
            intent.putExtra("exp", experiment);
            intent.putExtra("user", user);
            intent.putExtra("pos", pos);
            startActivityForResult(intent,5);
        }
    }
    // This method is called when the profile activity finishes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // get the modified experiment data modify it in the list so change stays
        super.onActivityResult(requestCode, resultCode, data);

        // Check that it is the SecondActivity with an OK result
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                user = (User) data.getSerializableExtra("user");
            }
        }
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                Experiment e = (Experiment) data.getSerializableExtra("exp");
                //int ind = (Integer) data.getSerializableExtra("pos");
                //experimentDataList.set(ind,e);
                //database.updateWithResults(e.results.get(e.results.size()-1),e.name);
            }
        }
        if (requestCode == 3) {
            if (resultCode == RESULT_OK) {
                Experiment e = (Experiment) data.getSerializableExtra("exp");
                //int ind = (Integer) data.getSerializableExtra("pos");
                //experimentDataList.set(ind,e);
            }
        }
        if (requestCode == 4) {
            if (resultCode == RESULT_OK) {
                Experiment e = (Experiment) data.getSerializableExtra("exp");
                //int ind = (Integer) data.getSerializableExtra("pos");
                //experimentDataList.set(ind,e);
            }
        }
        if (requestCode == 5) {
            if (resultCode == RESULT_OK) {
                Experiment e = (Experiment) data.getSerializableExtra("exp");
                //int ind = (Integer) data.getSerializableExtra("pos");
                //experimentDataList.set(ind,e);
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.i("MyApp", "onNavigationItemSelected: -----------------------------------------------------");
        switch(item.getItemId())
        {
            case R.id.subscribe:
                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment,new subscriptions()).commit();
                break;
            case R.id.profileName:
                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment,new CreateUserFragment()).commit();
                break;
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment,new HomeFragment()).commit();
                break;

        }
        /*if(item.getItemId()==R.id.home)
        {
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new HomeFragment());
            fragmentTransaction.commit();
        }
        if(item.getItemId()==R.id.subscribe)
        {
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new subscriptions());
            fragmentTransaction.commit();
                  }
        if(item.getItemId()==R.id.profileName)
        {
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new CreateUserFragment());
            fragmentTransaction.commit();
        }*/
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}