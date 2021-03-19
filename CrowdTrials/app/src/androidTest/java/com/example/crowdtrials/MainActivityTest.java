package com.example.crowdtrials;

import android.app.Activity;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class MainActivityTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<LoginActivity> rule =
            new ActivityTestRule<>(LoginActivity.class,true,true);

    @Before
    public void setUp() {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }

    @Test
    public void start() {
        Activity activity = rule.getActivity();
    }

    @Test
    public void checkLogin() {

        solo.assertCurrentActivity("Wrong Activity",LoginActivity.class);
        solo.clickOnView(solo.getView(R.id.loginbutton));
        solo.enterText((EditText) solo.getView(R.id.username_editText),"jack12");
        solo.clickOnButton("OK");
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);

    }

    @Test
    public void checkUserProfile() {
        solo.assertCurrentActivity("Wrong Activity",LoginActivity.class);
        solo.clickOnView(solo.getView(R.id.loginbutton));
        solo.enterText((EditText) solo.getView(R.id.username_editText),"jack12");
        solo.clickOnButton("OK");
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
        solo.clickOnView(solo.getView(R.id.profileName));
        solo.assertCurrentActivity("Wrong Activity",DisplayProfileActivity.class);

    }
}